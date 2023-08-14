package com.luchavor.streamprocess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.test.TestArtifact;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZeekConsumerService {

	@Autowired
	KafkaTemplate<String, TestArtifact> kafkaTemplate;
	
	@KafkaListener(topics="test", groupId = "zeek", containerFactory = "testArtifactListener")
	void handle(TestArtifact testArtifact) {
		log.info(testArtifact.toString());
	}
	
	@KafkaListener(topics="observed-host", groupId = "zeek", containerFactory = "observedHostListener")
	void handle(ObservedHost observedHost) {
		log.info(observedHost.toString());
	}
}