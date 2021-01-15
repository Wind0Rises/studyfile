<h1 align="center">RocketMq 基础知识</h1>

## 一、基础使用
### 1.1 生产者
```java
// 01、创建一个MQ生产者。'test_group':生产者组，简单来说就是多个发送同一类消息的生产者称之为一个生产者组。
DefaultMQProducer producer = new DefaultMQProducer("test_group");

// 02、设置生产者的：namespace地址；因为Broker会把自己注册到nameServer上，生产者会去nameServer获取要向哪一个Broker发送消息。
producer.setNamesrvAddr("127.0.0.1:9876");

// 03、开启生产者。
producer.start();

// 04、发送消息。
while (true) {
    // 04-1、获取输入的消息内容
    Scanner scanner = new Scanner(System.in);
    System.out.print("请输入发送的消息：");
    String message = scanner.nextLine();

    // 04-2、创建消息。FIRST_TOPIC：是一个Topic，
    Message msg = new Message(CommonConstants.FIRST_TOPIC, message.getBytes());

    // 04-3、实际发送过程
    SendResult sendResult = producer.send(msg);
    System.out.printf("%s%n", sendResult);
}

```
### 1.2 消费者
``` java
// 01、创建消息生产者，test_group:消费组，简单来说就是多个消费同一类消息的消费者称之为一个消费者组。
DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_group");

// 02、设置namespace的地址。
consumer.setNamesrvAddr("localhost:9876");

// 03、订阅Topic。
consumer.subscribe(CommonConstants.FIRST_TOPIC, "*");

// 04、添加监听器。
consumer.registerMessageListener(new MessageListenerConcurrently() {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        // 标记该消息已经被成功消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

});

// 05、启动消费者。
consumer.start();

System.out.println("---------消费者启动成功---------");
```

## 二、生产者
### 2.1 生产者类图
```java
@startuml
Title "BundleLauncher类图"
interface BundleExtractor
abstract class BundleLauncher
abstract class SoBundleLauncher
abstract class AssetBundleLauncher

BundleLauncher <|-- ActivityLauncher
BundleLauncher <|-- SoBundleLauncher
SoBundleLauncher <|-- ApkBundleLauncher
BundleExtractor <|.. SoBundleLauncher
SoBundleLauncher <|-- AssetBundleLauncher
AssetBundleLauncher <|-- WebBundleLauncher

class ActivityLauncher {
+ public preloadBundle(Bundle bundle)
}

class SoBundleLauncher {
+ public preloadBundle(Bundle bundle)
}

class ApkBundleLauncher {
+ public loadBundle(Bundle bundle)
}

class AssetBundleLauncher {
+ public loadBundle(Bundle bundle)
}
@enduml
```
