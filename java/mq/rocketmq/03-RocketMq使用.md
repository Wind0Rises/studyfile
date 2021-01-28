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
### 2.1 同步发送消息的生产者。
```java
Message msg = new Message(CommonConstants.FIRST_TOPIC, message.getBytes());
SendResult sendResult = producer.send(msg);
```

### 2.2 异步发送消息的生产者。
```java

...

/**
 * 设置发送异步消息失败重试的次数。
 */
producer.setRetryTimesWhenSendAsyncFailed(0);

...

/**
 * 异步发送的操作。
 */
producer.send(message, new SendCallback() {
    @Override
    public void onSuccess(SendResult sendResult) {
        /**
         * 消息发送broker成功以后回调。sendResult发送结果。
         */
        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
    }

    @Override
    public void onException(Throwable e) {
        /**
         * 发送失败的处理。
         */
        System.out.printf("%-10d Exception %s %n", index, e);
        e.printStackTrace();
    }
});
```
### 2.3 过滤消息设置
需要设置消息的过滤字段，已经这个字段的值，然后消息者对这个字段添加过滤条件进行过滤。
* 生产者
```java
Message message = new Message("filter-topic", "*",
        (inputMessage + "  发送时间：" + DateUtils.parseDateToString(new Date()) + "   " + value).getBytes());

message.putUserProperty("column", String.valueOf(value));

producer.send(message);
```

* 消费者

RocketMQ只定义了一些基本语法来支持这个特性。你也可以很容易地扩展它。
<br>
1. 数值比较，比如：>，>=，<，<=，BETWEEN，=；<br>
2. 字符比较，比如：=，<>，IN；<br>
3. IS NULL 或者 IS NOT NULL；<br>
4. 逻辑符号 AND，OR，NOT；<br>

<br>
常量支持类型为：<br>
1. 数值，比如：123，3.1415；<br>
2. 字符，比如：'abc'，必须用单引号包裹起来；<br>
3. NULL，特殊的常量<br>
4. 布尔值，TRUE 或 FALSE


```java
// 设置过滤条件
consumer.subscribe("filter-topic", MessageSelector.bySql("column between 0 and 6"));

consumer.registerMessageListener(new MessageListenerConcurrently() {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        msgs.forEach(item -> {
            System.out.println("接收到的消息为：" + new String(item.getBody()));
        });
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
});
```

### 2.4 延迟消息
比如电商里，提交了一个订单就可以发送一个延时消息，1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存。延迟支持一下18个延迟时间，
``` java
private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
```

消息生产者
``` java
/**
 * 延迟发送消息。
 *
 * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 * 1  2  3   4   5  6  7  8  9....
 */
message.setDelayTimeLevel(3); // 延迟10秒。

producer.send(message);
```

### 2.5 批量消息处理
* 消息生产者
``` java
List<Message> messages = Lists.newArrayList();
IntStream.range(0, 3).forEach(item -> {
        System.out.printf("输入信息为：");

        String inputMessage = scanner.nextLine();

        Message message = new Message("test_batch", (inputMessage + "  发送时间：" + DateUtils.parseDateToString(new Date())).getBytes());

        messages.add(message);
    }
);

// 【【入参是一个List】】
producer.send(messages);
```

* 消息消费者
``` java

```

### 2.6 顺序消费。
* 消息生产者
``` java
List<OrderStep> orderList = new OrderProducer().buildOrders();

Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String dateStr = sdf.format(date);
for (int i = 0; i < 10; i++) {
    // 构建消息。
    String body = dateStr + " Hello RocketMQ " + orderList.get(i);
    Message msg = new Message("TopicTest", tags[i % tags.length], "KEY" + i, body.getBytes());

    producer.send(msg, new MessageQueueSelector() {
        /**
         * 确定消息使用哪一个MessageQueue
         * @param mqs 队列的集合
         * @param msg 消息
         * @param arg = orderList.get(i).getOrderId()
         * @return
         */
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
            Long id = (Long) arg;
            long index = id % mqs.size();
            return mqs.get((int) index);
        }
    }, orderList.get(i).getOrderId());
}

/**
 * 订单的步骤
 */
private static class OrderStep {
    private long orderId;
    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
```
如何让同一个消息被多个不同业务的消费者消费，可以定义不同的消费者组（consumer_group）
