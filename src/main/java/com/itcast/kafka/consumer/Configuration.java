package com.itcast.kafka.consumer;

import kafka.cluster.Partition;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * producer的配置文件信息
 * @author 拼命三石
 * @version 1.0
 */
public class Configuration implements Callable<List<String>> {

    //定义消息消费者
    final KafkaConsumer<String, String> consumer;

    private String Topic;

    public Configuration(String Topic) {

        System.out.println("Topic：" + Topic);

        Properties properties = new Properties();

        //配置消费消息的端口号
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");

        //配置是否自动提交位置信息
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        //配置起始偏移量（从Topic的哪里开始消费数据）
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //配置消费者组的ID
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "11");

        //配置传入参数KEY的值进行序列化操作
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //配置传入参数VALUE的值进行序列化操作
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<String, String>(properties);

        //消费消息的分区副本自动分配策略
        consumer.subscribe(Arrays.asList(Topic));
    }

    /**
     * 将得到的消息以列表传出
     * @return
     * @throws Exception
     */
    @Override
    public List<String> call() throws Exception {

        ConsumerRecords<String, String> records = consumer.poll(1000);

        Map<String, Integer> map = new HashMap<>();

        List<String> values = new ArrayList<>();

        for (ConsumerRecord<String, String> record : records) {

            //System.out.println(record.value());

            values.add(record.value());
        }

        consumer.close();

        return values;
    }
}
