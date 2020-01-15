package com.liu.shiro.basis.controller;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class ShiroController {

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @RequestMapping("login")
    public String login(HttpServletRequest request) {

        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (exceptionClassName != null) {

        }
        return "login";
    }

    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        System.out.println(request.getContextPath());
        System.out.println("this is shiro index");
        return "index";
    }

    @RequiresPermissions("sys:shiro")
    @RequiresRoles("sys")
    @RequestMapping("index2")
    public String index2() {
        return "index2";
    }

    @RequiresGuest
    @RequestMapping("index3")
    public String index3() {
        return "index3";
    }

    @RequestMapping("index4")
    public String index4() {
        return "index4Shiro";
    }
}
