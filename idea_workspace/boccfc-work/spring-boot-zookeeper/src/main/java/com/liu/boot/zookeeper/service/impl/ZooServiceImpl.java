package com.liu.boot.zookeeper.service.impl;

import com.liu.boot.zookeeper.service.ZooService;
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
    private ZooKeeper zkClient;

    @Override
    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            LOG.info("{}节点exist查询异常，异常信息为：{}", path, e);
        }
        return null;
    }
}
