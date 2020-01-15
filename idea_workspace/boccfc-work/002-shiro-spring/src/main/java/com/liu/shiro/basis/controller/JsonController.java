package com.liu.shiro.basis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.shiro.basis.shiro.model.SysRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liuweian
 * @version 1.0.0
 * @desc
 * @createTime 2020/1/14 16:43
 */
@Controller
public class JsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping("jsonTest")
    public String getJsonData() throws JsonProcessingException {
        SysRole sysRole = new SysRole();
        sysRole.setDescription("ads");
        sysRole.setRole_Name("asdf");
        return objectMapper.writeValueAsString(sysRole);
    }
}
