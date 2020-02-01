package com.zmz.app.consumer;

import com.alibaba.fastjson.JSON;
import com.zmz.app.bean.Programmer;
import com.zmz.app.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-02-01 13:23
 * @description : kafka 消费者
 */
@Component
@Slf4j
public class KafkaBeanConsumer {

    @KafkaListener(groupId = "beanGroup",topics = Topic.BEAN)
    public void consumer(ConsumerRecord<String, Object> record) {
        System.out.println("消费者收到消息:" + JSON.parseObject(record.value().toString(), Programmer.class));
    }
}
