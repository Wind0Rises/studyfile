package com.liu.boot.zookeeper.service;

import org.apache.zookeeper.data.Stat;

/**
 * 操作Zookeeper的服务接口。
 */
public interface ZooService {

    /**
     * 判断节点是否存在。
     * @param path 节点路径
     * @param needWatch
     * @return
     */
    Stat exists(String path, boolean needWatch);
}
