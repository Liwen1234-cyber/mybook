# RocketMQ

## 一、消息队列模型

RocketMQ的消息队列模型主要分为两种模式：

1. **分区有序消息**：
   - 消息按照发送顺序被分配到同一个队列(Queue)中
   - 消费者从同一个队列顺序拉取消息
   - 示例：同一订单的不同状态消息(创建→付款→完成)会被发送到同一队列
   - 代码实现：通过MessageQueueSelector选择队列
   ```java
   SendResult result = producer.send(msg, new MessageQueueSelector() {
       public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
           long orderId = order.getOrderId();
           long mqIndex = orderId % list.size();
           return list.get((int) mqIndex);
       }
   }, null);
   ```

2. **全局有序消息**：
   - 所有消息都发送到同一个队列
   - 严格保证FIFO(先进先出)顺序
   - 性能较低，适用于强顺序要求的场景

## 二、高效读写机制

RocketMQ采用多种技术实现高效的消息存储和读写：

1. **文件系统存储**：
   - 相比数据库存储(如ActiveMQ)，避免了数据库瓶颈
   - 采用消息刷盘机制进行持久化存储

2. **SSD优化**：
   - 顺序写速度可达600MB/s(随机写仅100KB/s)
   - RocketMQ通过顺序写入大幅提高吞吐量

3. **零拷贝技术**：
   - 使用MappedByteBuffer实现
   - 数据传输由传统4次复制简化为3次复制
   - 要求预留1G以上的存储空间

4. **存储结构优化**：
   - 消息数据存储区域：包含topic、queueId和message
   - 消费逻辑队列：维护minOffset、maxOffset和consumerOffset
   - 索引系统：包括key索引和创建时间索引

## 三、刷盘机制

RocketMQ提供两种消息刷盘方式：

1. **同步刷盘**：
   ```java
   // 配置方式
   flushDiskType=SYNC_FLUSH
   ```
   - 流程：
     1. 生产者发送消息到MQ
     2. MQ挂起生产者线程
     3. 将消息写入内存
     4. 内存数据写入硬盘
     5. 磁盘存储成功后返回ACK
     6. 恢复生产者线程
   - 特点：安全性高，效率低，速度慢

2. **异步刷盘**：
   ```java
   // 配置方式
   flushDiskType=ASYNC_FLUSH
   ```
   - 流程：
     1. 生产者发送消息到MQ
     2. MQ立即返回ACK
     3. 异步将消息写入内存和磁盘
   - 特点：安全性较低，效率高，速度快

## 四、死信队列机制

1. **死信消息定义**：
   - 消息消费重试达到指定次数(默认16次)后
   - 无法被正常消费的消息成为死信消息

2. **死信队列特征**：
   - 归属消费者组(GroupId)，不属于特定Topic
   - 一个死信队列可包含同组下多个Topic的死信
   - 首次出现死信时才会初始化队列

3. **死信处理**：
   - 消息不会被重复消费
   - 3天有效期，到期自动清除
   - 可通过监控平台查找死信的messageId进行精准消费

4. **相关配置**：
   - 默认重试次数：16次
   - 可通过修改消费逻辑避免消息进入死信队列

## 五、消息处理流程图解

双主双从集群搭建架构：

![](./images/双主双从.png)


以上机制共同构成了RocketMQ高可靠、高性能的消息处理能力，开发者可根据业务需求选择合适的配置组合。