package com.consumer.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.producer.demo.dto.Customer;


@Service
public class KafkaMessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

	@KafkaListener(topics="quickstart",groupId="group-1")
	public void consumeMessagesFromTopic(String msg) {
		try {
			logger.info("Message consumed by consumer : {}", msg);
		}catch(Exception ex) {
			logger.info("Unable to consume the message : {}", ex.getMessage());
		}
	}
	
	@KafkaListener(topics="customerTopic",groupId="group-4")
	public void consumeCustomerFromTopic(Customer customer) {
		try {
			logger.info("Customer data consumed: {}", customer);
		}catch(Exception ex) {
			logger.info("Unable to consume the message : {}", ex.getMessage());
		}
	}
}
