package com.liu.boot.zookeeper.config;

import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper配置类，用于初始化Zookeeper。
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperConfiguration.class);

    private String address;

    private int timeout;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;

        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);

            LOG.info("Zookeeper初始化地址为：{}，超时时间为：{}", address, timeout);
            zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });

            // 等待countDownLatch.countDown()被调用，才往下执行。
            countDownLatch.await();

            LOG.info("初始化Zookeeper成功，zookeeper的状态为：{}", zooKeeper.getState());
        } catch (Exception e) {
            LOG.error("初始化Zookeeper失败，失败信息为：{}", e);
        }
        return zooKeeper;
    }
}
