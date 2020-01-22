package com.liu.shiro.basis.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/21 16:19
 */
public class TestAbstractController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("#########################################################");
        return new ModelAndView("liu");
    }
}
