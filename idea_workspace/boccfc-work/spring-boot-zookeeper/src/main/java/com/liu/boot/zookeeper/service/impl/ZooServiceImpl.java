package com.liu.boot.zookeeper.service.impl;

import com.liu.boot.zookeeper.service.ZooService;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作Zookeeper的服务接口实现。
 */
@Service
public class ZooServiceImpl implements ZooService {

    private static final Logger LOG = LoggerFactory.getLogger(ZooServiceImpl.class);

    @Autowired
    private ZooKeeper zookeeper;

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private CuratorFramework curatorFramework;

    @Override
    public Stat exists(String path, boolean needWatch) {
        try {
            return zookeeper.exists(path, needWatch);
        } catch (Exception e) {
            LOG.info("{}节点exist查询异常，异常信息为：{}", path, e);
        }
        return null;
    }

    /**
     * 创建临时顺序节点。
     */
    public void createTempSortNode(String path, byte[] bytes) {
        try {
            curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
