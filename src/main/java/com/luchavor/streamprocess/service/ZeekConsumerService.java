package com.luchavor.streamprocess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.luchavor.datamodel.artifact.Artifact;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.datamodel.factory.ArtifactFactory;
import com.luchavor.neo4japi.dao.ArtifactDao;
import com.luchavor.streamprocess.converter.ImportedConverter;
import com.luchavor.streamprocess.model.ImportedObservedHost;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZeekConsumerService {

	@Autowired
	KafkaTemplate<String, TestArtifact> kafkaTemplate;
	
	@Autowired
	ImportedConverter importedConverter;
	
	@Autowired
	ArtifactDao artifactDao;
	
	@Autowired
	ArtifactFactory artifactFactory;
	
	@KafkaListener(topics="test", groupId = "zeek", containerFactory = "testArtifactListener")
	void handle(TestArtifact testArtifact) {
		log.info(testArtifact.toString());
	}
	
	@KafkaListener(topics="observed-host", groupId = "zeek", containerFactory = "observedHostListener")
	void handle(ImportedObservedHost importedObservedHost) {
		log.info(importedObservedHost.toString());
		Artifact<ObservedHost> artifact = artifactFactory.create(importedConverter.convert(importedObservedHost));
		artifactDao.save(artifact);
	}
}