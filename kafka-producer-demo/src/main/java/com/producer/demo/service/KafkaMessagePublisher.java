package com.producer.demo.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.producer.demo.dto.Customer;

@Service
public class KafkaMessagePublisher {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessagePublisher.class);

	@Autowired
	public KafkaTemplate<String, Object> template;
	
	public void sendMessageToTopic(String message) {
		CompletableFuture<SendResult<String, Object>> future= template.send("quickstart", message);
		// future.get() - blocks thread
		// callback - asynchronous handling (work to do after sending)
		future.whenCompleteAsync((result,ex)->{
			if(ex==null) {
				logger.info("sent message : {} to topic : {} in partition:{} with offset : {}",
						message, result.getRecordMetadata().topic(),result.getRecordMetadata().partition(),result.getRecordMetadata().offset());
			}else {
				logger.info("Unable to send the message due to : ", ex.getMessage());
			}
		});
	}
	
	public void sendObjectToTopic(Customer customer) {
		CompletableFuture<SendResult<String, Object>> future=template.send("customerTopic",customer);
		logger.info("started");
		future.whenCompleteAsync((result,ex)->{
			logger.info("started");
		   if(ex==null) {
			logger.info("sent customer data : {}", customer);
		   }else {
			logger.info("Unable to send the message due to : ", ex.getMessage());
		  }
	   });
	}
}
