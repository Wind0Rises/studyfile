package com.liu.boot.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用原生Zookeeper实现分布式锁。
 */
@Service
public class ZookeeperLock {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperLock.class);

    private static final ThreadLocal<ZkLockerWatcher> lockerWatcherMap = new ThreadLocal<>();

    @Autowired
    private ZooKeeper zookeeper;

    /**
     * 锁的根节点
     */
    private static final String LOCK_ROOT = "/testLock";

    /**
     * 分隔符。
     */
    private static final String SEPARATOR = "/";

    /**
     * 加锁操作。
     * 每一个线程都new一个ZkLockerWatcher对象。
     * @param nodeData
     * @return
     */
    public boolean lock(String nodeData) {
        ZkLockerWatcher zkLockerWatcher = new ZkLockerWatcher();
        lockerWatcherMap.set(zkLockerWatcher);
        boolean flag = false;
        try {
            zkLockerWatcher.getLock(nodeData);
            flag = true;
        } catch (Exception e) {
            // TODO 如果创建锁失败，要怎么处理，怎么把创建成功的节点删除掉。如果一直删除等待线程自动终结，是否会印象性能。
            String childLockPath = zkLockerWatcher.getChildLockPath();
            if (childLockPath != null) {
                try {
                    Stat stat =zookeeper.exists(childLockPath, false);
                    if (stat != null) {
                        // TODO 如果这个时候childLockPath后面又有新的节点添加进去以后，这个时候把childLockPath节点
                        // TODO 删除会引起后续节点的Watch事件，这个时候怎么处理？
                        zookeeper.delete(childLockPath, stat.getVersion());
                    }
                } catch (Exception ex) {
                    LOG.info("");
                }
            }
        }
        return flag;
    }

    /**
     * 解锁操作。
     * @return
     */
    public boolean unLock() {
        boolean flag = false;
        ZkLockerWatcher zkLockerWatcher = lockerWatcherMap.get();
        try {
            zkLockerWatcher.releaseLock();
            flag = true;
        } catch (Exception e) {
            LOG.info("{}" + e);
        }
        return flag;
    }


    class ZkLockerWatcher implements Watcher {
        Thread thread;
        String childLockPath;

        public boolean getLock(String data) throws Exception {
            // 1、创建一个临时有序节点。
            System.out.println(Thread.currentThread().getName() + "  准备创建节点");
            childLockPath = zookeeper.create(LOCK_ROOT + SEPARATOR, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "  创建节点完成");
            return getLockOrWatchLast();
        }

        public void releaseLock() throws Exception {
            try {
                // 1、释放锁，删除节点
                if (childLockPath != null) {
                    zookeeper.delete(childLockPath, -1);
                    LOG.info("{}节点删除成功！！", childLockPath);
                }

                // 2、最后一个释放的删除锁节点
                List<String> children = zookeeper.getChildren(LOCK_ROOT, false);
                LOG.info("{}下的子节点有：{}", LOCK_ROOT, children);
                if (children.isEmpty()) {
                    try {
                        zookeeper.delete(LOCK_ROOT, -1);
                    } catch (KeeperException e) {
                        // 如果删除之前又新加了一个子节点，会删除失败
                        LOG.info("删除节点 {} 失败", LOCK_ROOT);
                    }
                }
            } catch (Exception e) {
                LOG.error("release lock error", e);
                throw new RuntimeException("release lock error");
            } finally {
                System.out.println("releaseLock: " + LOCK_ROOT + SEPARATOR);
            }
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            // 唤醒阻塞的线程（这是在监听线程，跟获取锁的线程不是同一个线程）
            LOG.info("++++++++++++++++++++++++++++++++++");
            if (this.thread != null) {
                LockSupport.unpark(this.thread);
                this.thread = null;
            }
        }

        private boolean getLockOrWatchLast() throws Exception {
            // 1、获取锁的根节点下的所有子节点。

            List<String> children = zookeeper.getChildren(LOCK_ROOT, false);

            // 2、必须要排序一下，这里取出来的顺序可能是乱的
            Collections.sort(children);

            // 3、如果当前节点是第一个子节点，则获取锁成功
            if ((LOCK_ROOT + SEPARATOR + children.get(0)).equals(childLockPath)) {
                return true;
            }

            // 4、如果不是第一个子节点，就监听前一个节点
            String last = "";
            for (String child : children) {
                if ((LOCK_ROOT + SEPARATOR + child).equals(childLockPath)) {
                    break;
                }
                last = child;
            }

            LOG.info("{}添加监听器为{}", childLockPath, LOCK_ROOT + SEPARATOR + last);
            if (zookeeper.exists(LOCK_ROOT + SEPARATOR + last, true) != null) {
                this.thread = Thread.currentThread();
                // 阻塞当前线程
                LockSupport.park();
                // 唤醒之后重新检测自己是不是最小的节点，因为有可能上一个节点断线了
                return getLockOrWatchLast();
            } else {
                // 如果上一个节点不存在，说明还没来得及监听就释放了，重新检查一次
                return getLockOrWatchLast();
            }
        }

        public String getChildLockPath() {
            return this.childLockPath;
        }
    }
}
