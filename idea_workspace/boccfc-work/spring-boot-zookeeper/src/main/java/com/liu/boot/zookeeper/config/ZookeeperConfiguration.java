package com.liu.boot.zookeeper.config;

import lombok.Data;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
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

    private int sessionTimeout;

    private int connectionTimeout;

    /**
     *  Zookeeper原生的客户端。
     */
    @Bean(name = "zookeeper")
    public ZooKeeper zookeeper() {
        ZooKeeper zooKeeper = null;

        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);

            LOG.info("Zookeeper初始化地址为：{}，回话超时时间为：{}", address, sessionTimeout);
            zooKeeper = new ZooKeeper(address, sessionTimeout, new Watcher() {
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

            LOG.info("【ZooKeeper】初始化Zookeeper成功，zookeeper的状态为：{}", zooKeeper.getState());
        } catch (Exception e) {
            LOG.error("初始化Zookeeper失败，失败信息为：{}", e);
        }
        return zooKeeper;
    }

    /**
     * 使用zkClien的客户端
     */
    @Bean(name = "zkClient")
    public ZkClient zkClient() {
        ZkClient zkClient = new ZkClient(address, sessionTimeout, connectionTimeout);
        LOG.info("【ZkClint】的客户端创建完成，地址为：{}，回话超时时间：{}，连接超市时间：{}", address, sessionTimeout, connectionTimeout);
        return zkClient;
    }

    /**
     * 使用CuratorFramework
     */
    @Bean(name = "curatorFramework")
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(address, sessionTimeout, connectionTimeout, retryPolicy);
        curatorFramework.start();
        LOG.info("【CuratorFramework】的客户端创建完成，地址为：{}，回话超时时间：{}，连接超市时间：{}", address, sessionTimeout, connectionTimeout);
        return curatorFramework;
    }
}
