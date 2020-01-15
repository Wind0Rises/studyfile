package com.liu.shiro.basis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.shiro.basis.shiro.model.SysRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liuweian
 * @version 1.0.0
 * @desc
 * @createTime 2020/1/14 17:09
 */

@Controller
public class HtmlController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("indexHtml")
    public String htmlIndex() {
        System.out.println("this is shiro indexHtml");
        return "index";
    }

    @RequestMapping("mav")
    public ModelAndView mav() {
        System.out.println("This is mav");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("liu", "asdf");
        modelAndView.addObject("asd", "addd");
        modelAndView.setViewName("mav");
        return  modelAndView;
    }
}