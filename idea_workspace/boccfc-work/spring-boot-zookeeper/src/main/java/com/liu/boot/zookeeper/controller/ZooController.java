package com.liu.boot.zookeeper.controller;

import com.liu.boot.zookeeper.lock.ZookeeperLock;
import com.liu.boot.zookeeper.service.ZooService;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class ZooController {

    private static final Logger LOG = LoggerFactory.getLogger(ZooController.class);

    @Autowired
    private ZooService zooService;

    @Autowired
    private ZookeeperLock zookeeperLock;

    @RequestMapping("/exist")
    public String isExist() {
        String path = "/persist";
        Stat stat = zooService.exists(path, false);
        LOG.info("zookeeper节点：{}，存在状态：{}", path, stat.toString());
        return "success";
    }

    @RequestMapping("/test")
    public String test() {

        new Thread(() -> {
            zookeeperLock.lock("");
            try {
                System.out.println("#########################");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁");
                zookeeperLock.unLock();
            }
        }, "线程一").start();

        new Thread(() -> {
            zookeeperLock.lock("");
            try {
                System.out.println("22222222222222222222222");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁");
                zookeeperLock.unLock();
            }
        }, "线程二").start();


        new Thread(() -> {
            zookeeperLock.lock("");
            try {
                System.out.println("333333333333333333333333333333");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁");
                zookeeperLock.unLock();
            }
        }, "线程三").start();
        return "success";
    }

}
