package com.liu.boot.auto.boot.auto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StudentBean.class)
public class AppConfig {

    @Bean
    public ConfigBean configBean(StudentBean studentBean) {
        return new ConfigBean(studentBean);
    }

}
