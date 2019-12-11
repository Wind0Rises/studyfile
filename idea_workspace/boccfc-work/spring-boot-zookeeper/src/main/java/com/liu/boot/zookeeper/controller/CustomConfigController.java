package com.liu.boot.zookeeper.controller;

import com.liu.boot.auto.boot.auto.ConfigBean;
import com.liu.boot.auto.boot.auto.StudentBean;
import com.liu.boot.zookeeper.config.CustomConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomConfigController {

    @Autowired
    private CustomConfiguration configuration;

    @Autowired
    private StudentBean studentBean;

    @Autowired
    private ConfigBean configBean;

    @RequestMapping("/config")
    public String config() {
        String like = configuration.getLike();
        return like;
    }

    @RequestMapping("/student")
    public String student() {
        String name = studentBean.getName();
        return name;
    }

    @RequestMapping("/configBean")
    public String configBean() {
        String name = configBean.getStudentBean().getName();
        return name;
    }

}
