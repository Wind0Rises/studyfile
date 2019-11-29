package com.liu.boot.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 使用zookeeper的Curator框架实现分布式锁。
 * Curator已经有一套很完整的zookeeper锁的实现框架。
 */
public class ZooCuratorLock {

    public static final Logger LOG = LoggerFactory.getLogger(ZooCuratorLock.class);

    public static final String LOCK_ROOT = "/lock/kinds/";

    /**
     * 可重入排它锁
     */
    private InterProcessMutex interProcessMutex;

    /**
     * 竞争资源的标注。
     */
    private String lockName;

    @Autowired
    private CuratorFramework curatorFramework;

    public ZooCuratorLock(String lockName) {
        this.lockName = lockName;
        interProcessMutex = new InterProcessMutex(curatorFramework, LOCK_ROOT + lockName);
    }

    /**
     * 获取锁。
     */
    public void acquireLock() {
        int flag = 0;
        try {
            // 重试2次，每次最大等待2s，也就是最大等待4s
            while (!interProcessMutex.acquire(2, TimeUnit.SECONDS)){
                flag++;

                if(flag > 1){  //重试两次
                    break;
                }
            }
        } catch (Exception e) {
            LOG.error("distributed lock acquire exception="+e);
        }

        if(flag > 1){
            LOG.info("Thread:"+Thread.currentThread().getId() + " acquire distributed lock busy");
        }else{
            LOG.info("Thread:"+Thread.currentThread().getId() + " acquire distributed lock  success");
        }
    }

    /**
     * 释放锁
     */
    public void releaseLock(){
        try {
            if(interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()){
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(LOCK_ROOT + lockName);
                LOG.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  success");
            }
        }catch (Exception e){
            LOG.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  exception="+e);
        }
    }
}
