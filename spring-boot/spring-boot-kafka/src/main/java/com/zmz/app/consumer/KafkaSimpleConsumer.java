package com.zmz.app.consumer;

import com.zmz.app.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-02-01 13:23
 * @description : kafka 简单消息消费者
 */
@Slf4j
@Component
public class KafkaSimpleConsumer {

    /**
     * 简单消费者
     * @param record
     * @param topic
     * @param consumer
     */
    @KafkaListener(groupId = "simpleGroup", topics = Topic.SIMPLE)
    public void consumer1_1(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
        System.out.println("消费者收到消息:" + record.value() + "; topic:" + topic);
        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */
    }

}
