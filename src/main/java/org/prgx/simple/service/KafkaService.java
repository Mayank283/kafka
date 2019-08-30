/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.prgx.simple.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.prgx.simple.objects.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaService
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${prgx.kafka.topic}")
    private String kafkaTopic;

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    /**
     * This method sends message to the kafka topic
     *
     * @param person
     */
    public void sendMessageToBroker(Person person)
    {
        ProducerRecord<String, Person> record = new ProducerRecord<>(kafkaTopic, person);

        ListenableFuture<SendResult<String, Person>> delieveredMessage = kafkaTemplate.send(record);

        delieveredMessage.addCallback(new ListenableFutureCallback<SendResult<String, Person>>()
        {
            @Override
            public void onFailure(Throwable throwable)
            {
                logger.info("Message sending failed");
            }

            @Override
            public void onSuccess(SendResult<String, Person> stringStringSendResult)
            {
                logger.info("Sent message successfully");
            }
        });
    }

    /** assigning multiple consumer to a single consumer-group for a topic with multiple partitions */

    /*@KafkaListener(clientIdPrefix = "clientId0", topics = "mytopic1")
    public void receiveMessage(ConsumerRecord<String, String> record, @Payload String payload)
    {
        logger.info("Consumer record {} and payload {} ", record.toString(), payload);
    }

    @KafkaListener(clientIdPrefix = "clientId1", topics = "mytopic1")
    public void receiveMessage1(ConsumerRecord<String, String> record, @Payload String payload)
    {
        logger.info("Consumer record {} and payload {} ", record.toString(), payload);
    }*/

    /** assigning same consumer to multiple topics */
    @KafkaListener(clientIdPrefix = "clientId2", topics = { "mytopic", "mytopic1" }, id = "group.c")
    public void receiveMessage2(ConsumerRecord<String, Person> record, @Payload Person payload)
    {
        logger.info("Consumer record {} and payload {}", record.toString(), payload);
    }

    /** assigning specific partitions of topic to consumer */
   /* @KafkaListener(clientIdPrefix = "clientId3", topicPartitions = @TopicPartition(topic = "mytopic1", partitions = {
            "0", "1" }), id = "group.d")
    public void receiveMessage3(ConsumerRecord<String, String> record, @Payload String payload)
    {
        logger.info("Consumer record {} and payload {}", record.toString(), payload);
    }*/
}
