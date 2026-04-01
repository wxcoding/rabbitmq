package com.guanwx;

import com.rabbitmq.client.*;

public class Consumer {
    public static void main(String[] args) throws Exception {

        String queue = "gwx_queue";

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

        // 接收消息的回调函数
        DeliverCallback defaultConsumer = (consumer, message) -> {
            System.out.println("消费者：" + new String(message.getBody()));
        };

        // 取消订阅的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消费者取消订阅");
        };

        // 消费消息
        channel.basicConsume(queue, true, defaultConsumer, cancelCallback);

    }
}
