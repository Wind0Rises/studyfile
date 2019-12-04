package com.liu.boot.auto.boot.auto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public StudentBean studentBean() {
        StudentBean studentBean = new StudentBean();
        studentBean.setAge(19);
        studentBean.setName("admin");
        return studentBean;
    }

}
