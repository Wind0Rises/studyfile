package com.liu.boot.zookeeper.controller;

import com.liu.boot.zookeeper.service.ZooService;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZooController {

    private static final Logger LOG = LoggerFactory.getLogger(ZooController.class);

    @Autowired
    private ZooService zooService;

    @RequestMapping("/exist")
    public String isExist() {
        String path = "/persist";
        Stat stat = zooService.exists(path, false);
        LOG.info("zookeeper节点：{}，存在状态：{}", path, stat.toString());
        return "success";
    }

}
