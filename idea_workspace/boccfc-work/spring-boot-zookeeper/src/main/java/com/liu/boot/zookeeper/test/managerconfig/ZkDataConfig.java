package com.liu.boot.zookeeper.test.managerconfig;

import com.liu.boot.zookeeper.service.ZooService;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 这是一个独立的Config配置。
 * 项目启动时，从数据库读取配置，并把配置以zookeeper节点的方式注册到zookeeper上，
 * 如果后面修改数据后，会更新对应的配置，并把配置同步到zookeeper上。
 *
 * 当配置信息注册到zookeeper上时，需要这些的配置的应用服务需要需要监听这些节点，
 * 当节点发生改变时，监听这些节点的应用服务期会做出对应的反应，
 */
public class ZkDataConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ZkDataConfig.class);

    private DataConfigModel dataConfigModel;

    @Autowired
    private ZooService zooService;

    /**
     * 从数据库加载文件也可以从网络加载文件。
     */
    public DataConfigModel downLoadConfigFromDB() {
        DataConfigModel configModel = new DataConfigModel("DB", "DB", "DB", "DB");
        LOG.info("从数据加载配置完成，配置信息为：{}", configModel.toString());
        return configModel;
    }


    /**
     * 把配置文件信息同步到zookeeper上。
     * 初始化时 ---> 直接把数据库的配置文件或者网络上的配置文件同步到zookeeper上，
     * 修改配置是  ---> 修改后出发同步操作，把配置文件同步到zookeeper上。
     */
    public void SynchronizedToZookeeper() {
        if (zooService.exists("/dataConfig", false) == null) {

        }
    }
}
