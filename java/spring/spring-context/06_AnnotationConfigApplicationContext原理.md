# AnnotationConfigApplicationContext原理解析
## 一、基本的使用流程
### 1.1 代码示例
``` java
@Configuration
public class AnnotationApplicationContextAsIocContainerDemo {

    public static void main(String[] args) {

        /**
         * 创建ApplicationContext。
         */
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        /**
         * 将当前类AnnotationApplicationContextAsIocContainerDemo作为配置类（Configuration Class）
         */
        applicationContext.register(AnnotationApplicationContextAsIocContainerDemo.class);

        /**
         * 刷新操作。
         */
        applicationContext.refresh();

        /**
         * 依赖查找。
         */
        lookupCollect(applicationContext);
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId(1100L);
        user.setUsername("AnnotationApplicationContext");
        return user;
    }

    /**
     * 查询多个。
     * @param beanFactory
     */
    private static void lookupCollect(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(beansOfType);
        }
    }

}
```

### 1.2 AnnotationConfigApplicationContext内容
```java
/**
 * AnnotationConfigApplicationContext构造函数
 * 创建了AnnotatedBeanDefinitionReader、ClassPathBeanDefinitionScanner。
 */
public AnnotationConfigApplicationContext() {
  this.reader = new AnnotatedBeanDefinitionReader(this);
  this.scanner = new ClassPathBeanDefinitionScanner(this);
}


public void register(Class<?>... annotatedClasses) {
	Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
  this.reader.register(annotatedClasses);
}

/**
 * 初始化AnnotatedBeanDefinitionReader，并创建内置Bean
 */
 public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
	this(registry, getOrCreateEnvironment(registry));
}

/**
 *
 */
public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
	Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
	Assert.notNull(environment, "Environment must not be null");
	this.registry = registry;
	this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
	AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
}

/**
 * AnnotationConfigUtils
 *
 *
 */
public static void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
	registerAnnotationConfigProcessors(registry, null);
}

/**
 * 注册内置Bean Definition：
 *    org.springframework.context.annotation.internalConfigurationAnnotationProcessor   ConfigurationClassPostProcessor
 *    org.springframework.context.annotation.internalAutowiredAnnotationProcessor     AutowiredAnnotationBeanPostProcessor
 *    org.springframework.context.annotation.internalRequiredAnnotationProcessor  RequiredAnnotationBeanPostProcessor
 *    org.springframework.context.annotation.internalCommonAnnotationProcessor  CommonAnnotationBeanPostProcessor
 *    org.springframework.context.annotation.internalPersistenceAnnotationProcessor  
 *    org.springframework.context.event.internalEventListenerProcessor   EventListenerMethodProcessor || DefaultEventListenerFactory
 *    
 *    
 */
public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
		BeanDefinitionRegistry registry, Object source) {

	DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
	if (beanFactory != null) {
		if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
			beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
		}
		if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
			beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
		}
	}

	Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<BeanDefinitionHolder>(4);

	if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
	}

	if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
	}

	if (!registry.containsBeanDefinition(REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(RequiredAnnotationBeanPostProcessor.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
	}

	// Check for JSR-250 support, and if present add the CommonAnnotationBeanPostProcessor.
	if (jsr250Present && !registry.containsBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
	}

	// Check for JPA support, and if present add the PersistenceAnnotationBeanPostProcessor.
	if (jpaPresent && !registry.containsBeanDefinition(PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition();
		try {
			def.setBeanClass(ClassUtils.forName(PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME,
					AnnotationConfigUtils.class.getClassLoader()));
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Cannot load optional framework class: " + PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME, ex);
		}
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME));
	}

	if (!registry.containsBeanDefinition(EVENT_LISTENER_PROCESSOR_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
	}
	if (!registry.containsBeanDefinition(EVENT_LISTENER_FACTORY_BEAN_NAME)) {
		RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
		def.setSource(source);
		beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
	}

	return beanDefs;
}
```

### 1.3 注解形式注入BeanDefinition
```java
//  package org.springframework.context.annotation;
public void register(Class<?>... annotatedClasses) {
	for (Class<?> annotatedClass : annotatedClasses) {
		registerBean(annotatedClass);
	}
}

public void registerBean(Class<?> annotatedClass) {
	registerBean(annotatedClass, null, (Class<? extends Annotation>[]) null);
}

/**
 *
 */
public void registerBean(Class<?> annotatedClass, String name, Class<? extends Annotation>... qualifiers) {
  AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(annotatedClass);
	if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
		return;
	}

	ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
	abd.setScope(scopeMetadata.getScopeName());
	String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));
	AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
	if (qualifiers != null) {
		for (Class<? extends Annotation> qualifier : qualifiers) {
			if (Primary.class == qualifier) {
				abd.setPrimary(true);
			}
			else if (Lazy.class == qualifier) {
				abd.setLazyInit(true);
			}
			else {
				abd.addQualifier(new AutowireCandidateQualifier(qualifier));
			}
		}
	}

	BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
	definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
	BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
}


public static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd) {
	processCommonDefinitionAnnotations(abd, abd.getMetadata());
}

static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd, AnnotatedTypeMetadata metadata) {
	if (metadata.isAnnotated(Lazy.class.getName())) {
		abd.setLazyInit(attributesFor(metadata, Lazy.class).getBoolean("value"));
	}
	else if (abd.getMetadata() != metadata && abd.getMetadata().isAnnotated(Lazy.class.getName())) {
		abd.setLazyInit(attributesFor(abd.getMetadata(), Lazy.class).getBoolean("value"));
	}

	if (metadata.isAnnotated(Primary.class.getName())) {
		abd.setPrimary(true);
	}
	if (metadata.isAnnotated(DependsOn.class.getName())) {
		abd.setDependsOn(attributesFor(metadata, DependsOn.class).getStringArray("value"));
	}

	if (abd instanceof AbstractBeanDefinition) {
		AbstractBeanDefinition absBd = (AbstractBeanDefinition) abd;
		if (metadata.isAnnotated(Role.class.getName())) {
			absBd.setRole(attributesFor(metadata, Role.class).getNumber("value").intValue());
		}
		if (metadata.isAnnotated(Description.class.getName())) {
			absBd.setDescription(attributesFor(metadata, Description.class).getString("value"));
		}
	}
}
```
