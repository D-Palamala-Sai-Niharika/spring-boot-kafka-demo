package com.producer.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.producer.demo.dto.Customer;
import com.producer.demo.service.KafkaMessagePublisher;


@RestController
@RequestMapping("/publish")
public class EventPublishController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventPublishController.class);
	
	@Autowired
	public KafkaMessagePublisher kafkaMessagePublisher;
	
	@GetMapping("/{msg}")
	public ResponseEntity<?> sendMessage(@PathVariable String msg){
		try {
			
			for(int i=1;i<=1000;i++) { // send bulk msgs
				kafkaMessagePublisher.sendMessageToTopic(msg + " : " + i);
			}
			return ResponseEntity.ok("Messages published Successfully");
			
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/customer")
	public ResponseEntity<?> sendCustomerData(@RequestBody Customer customer){
		logger.info("started customer object controllers");
		try {
			kafkaMessagePublisher.sendObjectToTopic(customer);
			return ResponseEntity.ok("Customer Data send Successfully");
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
