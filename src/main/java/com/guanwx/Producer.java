package com.guanwx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception {

        // 声明一个交换机和队列
        String  exchange = "gwx_exchange";
        String  queue = "gwx_queue";

        // 创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置一个服务地址
        connectionFactory.setHost("127.0.0.1");
        // 设置服务端口号
        connectionFactory.setPort(5672);
        // 设置用户名
        connectionFactory.setUsername("guest");
        // 设置用户密码
        connectionFactory.setPassword("guest");
        // 创建连接
        Connection connection = connectionFactory.newConnection();
        // 创建信息通道
        Channel channel = connection.createChannel();


        /**
         * 创建交换机
         *
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否持久化
         * 4.是否自动删除
         * 5.其他参数(用来指定交换机其他的一些参数Map<String, Object>类型)
         */
        channel.exchangeDeclare(exchange, "direct", true, false, null);

        /**
         * 生成一个队列
         * 1.队列名称
         * 2.是否持久化
         * 3.是否独占本次连接（是否是私有的，只有创建他的应用程序才能消费消息）
         * 4.是否在不使用的时候自动删除（是否在没有消费者订阅的情况下自动删除）
         * 5.其他参数，队列的一些结构化信息，比如死信队列
         */
        channel.queueDeclare(queue, true, false, false, null);

        /**
         * 绑定交换机和队列
         * 1.队列名称
         * 2.交换机名称
         * 3.路由key，直连模式下就可以为队列名称
         */
        channel.queueBind(queue,exchange,queue);

        /**
         * 发送消息
         * 1.发送到那个交换机
         * 2.队列名称
         * 3.其他参数
         * 4.消息内容
         */
        String message = "hello rabbitmq";
        channel.basicPublish(exchange, queue, null, message.getBytes());
        System.out.println("发送消息成功");

        // 关闭资源
        channel.close();
        connection.close();
    }
}