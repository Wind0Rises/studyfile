# Configuration原理

## 一、核心内容

* ConfigurationClassPostProcessor



## 二、ConfigurationClassPostProcessor后置处理器如何注入Spring Container中
### 2.1 内置Bean Definition注入Spring
在Application初始化的时候，会把对应的内置Bean Definition注入，以AnnotationConfigApplicationContext为例，在初始化AnnotationConfigApplicationContext的时候会把对应的内置Bean注入的Bean Factory中；
```java
public AnnotationConfigApplicationContext() {
	this.reader = new AnnotatedBeanDefinitionReader(this);
	this.scanner = new ClassPathBeanDefinitionScanner(this);
}

public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
	this(registry, getOrCreateEnvironment(registry));
}

/**
 * AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
 * 在这个方法进行内置的Bean的注入。
 */
public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
	Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
	Assert.notNull(environment, "Environment must not be null");
	this.registry = registry;
	this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
	AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
}
```

### 2.2 调用BeanDefinitionRegistryPostProcessor的postProcessBeanDefinitionRegistry方法
在Context的refresh()方法中回调用invokeBeanFactoryPostProcessors()方法，在这个方法会调用符合条件得BeanDefinitionRegistryPostProcessor的postProcessBeanDefinitionRegistry方法。


### 2.2 ConfigurationClassPostProcessor做了哪些内容
```java
public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
	int registryId = System.identityHashCode(registry);
	if (this.registriesPostProcessed.contains(registryId)) {
		throw new IllegalStateException("postProcessBeanDefinitionRegistry already called on this post-processor against " + registry);
	}
	if (this.factoriesPostProcessed.contains(registryId)) {
		throw new IllegalStateException("postProcessBeanFactory already called on this post-processor against " + registry);
	}

	this.registriesPostProcessed.add(registryId);

	processConfigBeanDefinitions(registry);
}


/**
 * 核心内容。
 */
 public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
	List<BeanDefinitionHolder> configCandidates = new ArrayList<BeanDefinitionHolder>();
	String[] candidateNames = registry.getBeanDefinitionNames();

	for (String beanName : candidateNames) {
		BeanDefinition beanDef = registry.getBeanDefinition(beanName);
		if (ConfigurationClassUtils.isFullConfigurationClass(beanDef) || ConfigurationClassUtils.isLiteConfigurationClass(beanDef)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
			}
		} else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
			configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
		}
	}

	//
	if (configCandidates.isEmpty()) {
		return;
	}

	//
	Collections.sort(configCandidates, new Comparator<BeanDefinitionHolder>() {
		@Override
		public int compare(BeanDefinitionHolder bd1, BeanDefinitionHolder bd2) {
			int i1 = ConfigurationClassUtils.getOrder(bd1.getBeanDefinition());
			int i2 = ConfigurationClassUtils.getOrder(bd2.getBeanDefinition());
			return (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
		}
	});

	// Detect any custom bean name generation strategy supplied through the enclosing application context
	SingletonBeanRegistry sbr = null;
	if (registry instanceof SingletonBeanRegistry) {
		sbr = (SingletonBeanRegistry) registry;
		if (!this.localBeanNameGeneratorSet && sbr.containsSingleton(CONFIGURATION_BEAN_NAME_GENERATOR)) {
			BeanNameGenerator generator = (BeanNameGenerator) sbr.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
			this.componentScanBeanNameGenerator = generator;
			this.importBeanNameGenerator = generator;
		}
	}

	// Parse each @Configuration class
	ConfigurationClassParser parser = new ConfigurationClassParser(this.metadataReaderFactory, this.problemReporter, this.environment,
			this.resourceLoader, this.componentScanBeanNameGenerator, registry);

	Set<BeanDefinitionHolder> candidates = new LinkedHashSet<BeanDefinitionHolder>(configCandidates);
	Set<ConfigurationClass> alreadyParsed = new HashSet<ConfigurationClass>(configCandidates.size());
	do {
		parser.parse(candidates);
		parser.validate();

		Set<ConfigurationClass> configClasses = new LinkedHashSet<ConfigurationClass>(parser.getConfigurationClasses());
		configClasses.removeAll(alreadyParsed);

		if (this.reader == null) {
			this.reader = new ConfigurationClassBeanDefinitionReader(
					registry, this.sourceExtractor, this.resourceLoader, this.environment,
					this.importBeanNameGenerator, parser.getImportRegistry());
		}
		this.reader.loadBeanDefinitions(configClasses);
		alreadyParsed.addAll(configClasses);

		candidates.clear();
		if (registry.getBeanDefinitionCount() > candidateNames.length) {
			String[] newCandidateNames = registry.getBeanDefinitionNames();
			Set<String> oldCandidateNames = new HashSet<String>(Arrays.asList(candidateNames));
			Set<String> alreadyParsedClasses = new HashSet<String>();
			for (ConfigurationClass configurationClass : alreadyParsed) {
				alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
			}
			for (String candidateName : newCandidateNames) {
				if (!oldCandidateNames.contains(candidateName)) {
					BeanDefinition bd = registry.getBeanDefinition(candidateName);
					if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) &&
							!alreadyParsedClasses.contains(bd.getBeanClassName())) {
						candidates.add(new BeanDefinitionHolder(bd, candidateName));
					}
				}
			}
			candidateNames = newCandidateNames;
		}
	}
	while (!candidates.isEmpty());

	if (sbr != null) {
		if (!sbr.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
			sbr.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
		}
	}

	if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
		((CachingMetadataReaderFactory) this.metadataReaderFactory).clearCache();
	}
}

/**
 * location：ConfigurationClassParser
 *
 */
public void parse(Set<BeanDefinitionHolder> configCandidates) {
	this.deferredImportSelectors = new LinkedList<DeferredImportSelectorHolder>();

	for (BeanDefinitionHolder holder : configCandidates) {
		BeanDefinition bd = holder.getBeanDefinition();
		try {
			if (bd instanceof AnnotatedBeanDefinition) {
				parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
			} else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
				parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
			} else {
				parse(bd.getBeanClassName(), holder.getBeanName());
			}
		} catch (BeanDefinitionStoreException ex) {
			throw ex;
		} catch (Throwable ex) {
			throw new BeanDefinitionStoreException("Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
		}
	}

	processDeferredImportSelectors();
}

/**
 * location：ConfigurationClassParser
 *
 */
protected final void parse(AnnotationMetadata metadata, String beanName) throws IOException {
	processConfigurationClass(new ConfigurationClass(metadata, beanName));
}

/**
 * location：ConfigurationClassParser
 *
 */
protected void processConfigurationClass(ConfigurationClass configClass) throws IOException {
	if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
		return;
	}

	ConfigurationClass existingClass = this.configurationClasses.get(configClass);
	if (existingClass != null) {
		if (configClass.isImported()) {
			if (existingClass.isImported()) {
				existingClass.mergeImportedBy(configClass);
			}

			return;
		} else {
			this.configurationClasses.remove(configClass);
			for (Iterator<ConfigurationClass> it = this.knownSuperclasses.values().iterator(); it.hasNext();) {
				if (configClass.equals(it.next())) {
					it.remove();
				}
			}
		}
	}

	SourceClass sourceClass = asSourceClass(configClass);
	do {
		sourceClass = doProcessConfigurationClass(configClass, sourceClass);
	}
	while (sourceClass != null);

	this.configurationClasses.put(configClass, configClass);
}


/**
 *
 */
protected final SourceClass doProcessConfigurationClass(ConfigurationClass configClass, SourceClass sourceClass) throws IOException {

	processMemberClasses(configClass, sourceClass);

	for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(sourceClass.getMetadata(), PropertySources.class,
			org.springframework.context.annotation.PropertySource.class)) {

		if (this.environment instanceof ConfigurableEnvironment) {
			processPropertySource(propertySource);
		} else {
			logger.warn("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() + "]. Reason: Environment must implement ConfigurableEnvironment");
		}
	}

	Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
	if (!componentScans.isEmpty() && !this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
		for (AnnotationAttributes componentScan : componentScans) {
			Set<BeanDefinitionHolder> scannedBeanDefinitions = this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
			for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
				if (ConfigurationClassUtils.checkConfigurationClassCandidate(holder.getBeanDefinition(), this.metadataReaderFactory)) {
					parse(holder.getBeanDefinition().getBeanClassName(), holder.getBeanName());
				}
			}
		}
	}

	processImports(configClass, sourceClass, getImports(sourceClass), true);

	if (sourceClass.getMetadata().isAnnotated(ImportResource.class.getName())) {
		AnnotationAttributes importResource = AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
		String[] resources = importResource.getStringArray("locations");
		Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
		for (String resource : resources) {
			String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
			configClass.addImportedResource(resolvedResource, readerClass);
		}
	}

	Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
	for (MethodMetadata methodMetadata : beanMethods) {
		configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
	}

	processInterfaces(configClass, sourceClass);

	if (sourceClass.getMetadata().hasSuperClass()) {
		String superclass = sourceClass.getMetadata().getSuperClassName();
		if (!superclass.startsWith("java") && !this.knownSuperclasses.containsKey(superclass)) {
			this.knownSuperclasses.put(superclass, configClass);
			return sourceClass.getSuperClass();
		}
	}

	return null;
}
```

## 三、@Configuration类相关注解
### 3.1 @PropertySources注解

### 3.2 @ComponentScans注解

### 3.3 @Import

### 3.4 @ImportResource

### 3.5 @Bean

### 3.6 @
