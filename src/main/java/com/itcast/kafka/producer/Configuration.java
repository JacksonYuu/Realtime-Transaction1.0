package com.itcast.kafka.producer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;

/**
 * consumer的配置文件信息
 * @author 拼命三石
 * @version 1.0
 */
public class Configuration implements Runnable {

    //定义消息生产者
    private final KafkaProducer<String, String> producer;

    private String[] s = {"江西省","北京市","天津市","上海市","重庆市","河北省","河南省","湖北省","湖南省","江苏省","辽宁省","吉林省","黑龙江省","陕西省","山西省","山东省","四川省","青海省","安徽省","海南省","广东省","贵州省","浙江省","福建省","台湾省","甘肃省","云南省","西藏自治区","宁夏回族自治区","广西壮族自治区","新疆维吾尔自治区","内蒙古自治区"};

    private String Topic;

    //在实例化的时候就配置好消息生产者的信息
    public Configuration(String Topic) {

        this.Topic = Topic;

        System.out.println("Topic：" + Topic);

        Properties properties = new Properties();

        //配置Topic的响应端口
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");

        //配置传入参数KEY的值进行序列化操作
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //配置传入参数VALUE的值进行序列化操作
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //创建消息生产者
        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 消息生产者做些什么
     */
    @Override
    public void run() {

        while (true) {

            Random random = new Random();

            int index = random.nextInt(s.length);

            producer.send(new ProducerRecord<String, String>(Topic, s[index]));

            System.out.println(Topic + " : " + index);

            //刷新消息生产者
            producer.flush();

            try {

                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
