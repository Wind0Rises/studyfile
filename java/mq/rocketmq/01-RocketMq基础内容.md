<h1 align="center">RocketMq 基础知识</h1>

## 一、RocketMq 基本安装
### 1.1 下载安装包，并解压
1. 下载地址：http://rocketmq.apache.org/release_notes/，选择合适的版本
2. 下载Binary格式下载。

### 1.2 配置系统变量
1. 变量名：ROCKETMQ_HOME
2. 变量值：MQ解压路径\MQ文件夹名

### 1.3 启动RocketMq
1. 启动nameServer；Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行‘start mqnamesrv.cmd’，启动NAMESERVER。成功后会弹出提示框，此框勿关闭。
2. 启动Broker；Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行‘start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true’，启动BROKER。成功后会弹出提示框，此框勿关闭。

### 1.4 监控工具安装


## 二、RocketMQ各个角色的介绍
### 2.1 NameServer
管理Broker。集群是无状态的，NameServer集群是不需要数据同步的。

### 2.2 Broker
消息全部保存在Broker中，Broker自己会主动上报自己的状态信息给NameServer

### 2.3 Producer
生产者会把消息把发送到Broker，但是发送到哪个Broker？这个时候生产者会向NameServer询问可以向哪个Broker发送
数据。暂存和传输消息。

### 2.4 Consumer
消费者在消费数据的时候，会向NameServer询问，从哪个一个Broker获取消息，获取具体哪个Broker的地址。
消费可以

### 2.5 Topic
区分消息的种类。表示一类消息的集合，每个主题包含若干条消息，每条消息只能属于一个主题，是RocketMQ进行消息订阅的基本单位。

### 2.6 Message Queue

### 2.7 tog
为消息设置的标志，用于在同一主题下区分不同类型的消息。 来自同一业务单元的消息，可以根据不同业务目的在同一主题下设置不同标签。标签能够有效地保持代码的清晰度和连贯性，并优化RocketMQ提供的查询系统。消费者可以根据Tag实现对不同子主题的不同消费逻辑，实现更好的扩展性。

## 三、Broker集群
### 3.1 集群的集中模式
* 单master
* 多master模式
* 多master多slave模式（同步复制）性能笔异步复制略低10%
* 多master多slave模式（异步复制）发生宕机时会丢失少了数据
