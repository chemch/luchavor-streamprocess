package com.luchavor.streamprocess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.luchavor.datamodel.artifact.Artifact;
import com.luchavor.datamodel.artifact.network.observation.observedfile.ObservedFile;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedService;
import com.luchavor.datamodel.artifact.network.observation.software.Software;
import com.luchavor.datamodel.artifact.network.session.Session;
import com.luchavor.datamodel.artifact.network.session.connection.Connection;
import com.luchavor.datamodel.artifact.network.session.modbus.ModbusEvent;
import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.datamodel.factory.ArtifactFactory;
import com.luchavor.neo4japi.dao.ArtifactDao;
import com.luchavor.neo4japi.persistence.artifact.ArtifactRepo;
import com.luchavor.neo4japi.persistence.artifact.network.observation.ObservedFileRepo;
import com.luchavor.neo4japi.persistence.artifact.network.observation.ObservedHostRepo;
import com.luchavor.neo4japi.persistence.artifact.network.observation.ServiceRepo;
import com.luchavor.neo4japi.persistence.artifact.network.observation.SoftwareRepo;
import com.luchavor.neo4japi.persistence.artifact.network.session.ConnectionRepo;
import com.luchavor.neo4japi.persistence.artifact.network.session.ModbusEventRepo;
import com.luchavor.neo4japi.persistence.artifact.network.session.SessionRepo;
import com.luchavor.streamprocess.converter.ImportedConverter;
import com.luchavor.streamprocess.model.ImportedConnection;
import com.luchavor.streamprocess.model.ImportedModbusEvent;
import com.luchavor.streamprocess.model.ImportedObservedFile;
import com.luchavor.streamprocess.model.ImportedObservedHost;
import com.luchavor.streamprocess.model.ImportedObservedService;
import com.luchavor.streamprocess.model.ImportedSoftware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZeekConsumerService {

//	@Autowired
//	KafkaTemplate<String, TestArtifact> kafkaTemplate;
	
	@Autowired
	ImportedConverter importedConverter;
	
	@Autowired
	ArtifactDao artifactDao;
	
	@Autowired 
	ArtifactRepo artifactRepo;
	
	@Autowired
	ArtifactFactory artifactFactory;
	
	@Autowired 
	ObservedHostRepo observedHostRepo;
	
	@Autowired
	ObservedFileRepo observedFileRepo;
	
	@Autowired
	SoftwareRepo softwareRepo;
	
	@Autowired
	ServiceRepo serviceRepo;
	
	@Autowired
	SessionRepo sessionRepo;
	
	@Autowired
	ModbusEventRepo modbusEventRepo;
	
	@Autowired
	ConnectionRepo connectionRepo;
	
	@Autowired
	KafkaTemplate<String, String> serviceProducerTemplate;
	
	static final String WARN_DUPLICATE_FOUND = "\nDUPLICATE DETECTED: SKIPPING...\n";
	static final String WARN_MISSING_CONNECTION = "ASSOCIATED CONNECTION MISSING: OMITTING...\n";
	
	@KafkaListener(topics="tests", groupId = "zeek", containerFactory = "testArtifactListener")
	void handle(TestArtifact testArtifact) {
		log.info(testArtifact.toString());
	}
	
	@KafkaListener(topics="hosts", groupId = "zeek", containerFactory = "observedHostListener")
	void handle(ImportedObservedHost imported) {
		log.info(imported.toString());
		Optional<ObservedHost> existing = observedHostRepo.findByHostIp(imported.getHost());
		// add if non-existant
		if(!existing.isPresent()) {
			observedHostRepo.save(importedConverter.convert(imported));
		}
		else { 
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
	
	@KafkaListener(topics="services", groupId = "zeek", containerFactory = "observedServiceListener")
	void handle(ImportedObservedService imported) {
		log.info(imported.toString());
		Optional<ObservedService> existing = serviceRepo.findByServicesAndHostIp(imported.getService(), imported.getHost());
		// add if non-existant
		if(!existing.isPresent()) {
			ObservedService converted = importedConverter.convert(imported);
			serviceRepo.save(converted);
			// pass to inferences builder queue
			serviceProducerTemplate.send("services-inferences", "string");
		}
		else { 
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
	
	@KafkaListener(topics="services-enrichments", groupId = "zeek", containerFactory = "serviceEnrichmentListener")
	void handle(ObservedService observedService) {
		log.info(observedService.toString());
		
		
	}
	
	@KafkaListener(topics = "files", groupId = "zeek", containerFactory = "observedFileListener")
	void handle(ImportedObservedFile imported) {
		log.info(imported.toString());
		Optional<ObservedFile> existing = observedFileRepo.findByFuid(imported.getFuid());
		if(!existing.isPresent()) {
			ObservedFile converted = importedConverter.convert(imported);
			// find associated connection
			Optional<Connection> associatedConnection = connectionRepo.findByCuid(imported.getUid());
			if(associatedConnection.isPresent()) {
				converted.setConnection(associatedConnection.get());
			} 
			else {
				log.warn(WARN_MISSING_CONNECTION + imported.toString());	
			}
			// create and save artifact
			Artifact<ObservedFile> artifact = artifactFactory.create(converted);
			artifactDao.save(artifact);
		}
		else { // TODO enable updating of existing objects
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
	
	@KafkaListener(topics="modbus-events", groupId = "zeek", containerFactory = "modbusEventListener")
	void handle(ImportedModbusEvent imported) {
		log.info(imported.toString());
		ModbusEvent converted = importedConverter.convert(imported);
		System.out.println(converted.toString());
		Optional<ModbusEvent> existing = modbusEventRepo.findByTransactionId(converted.getTransactionId());
		// check if particular event already exists
		if(!existing.isPresent()) {
			Optional<Connection> associatedConnection = connectionRepo.findByCuid(converted.getCuid());
			// check if associated connection already exists
			if(associatedConnection.isPresent()) {
				// get connections session
				Session associatedSession = sessionRepo.findByConnection(associatedConnection.get());
				// add new event to existing list
				List<ModbusEvent> existingModbusEvents = associatedSession.getModbusEvents();
				existingModbusEvents.add(converted);
				// get associated artifact and update
				Artifact<Session> artifact = artifactRepo.findByValue(associatedSession);
				artifact.setValue(associatedSession);
				artifactDao.save(artifact);
			}
		}
		else { // TODO enable updating of existing objects
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
	
	@KafkaListener(topics="software", groupId = "zeek", containerFactory = "softwareListener")
	void handle(ImportedSoftware imported) {
		log.info(imported.toString());
		Optional<Software> existing = softwareRepo.findByNameAndHostIp(imported.getName(), imported.getHost());
		if(!existing.isPresent()) {
			Artifact<Software> artifact = artifactFactory.create(importedConverter.convert(imported));
			artifactDao.save(artifact);
		}
		else { // TODO enable updating of existing objects
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
	
	@KafkaListener(topics="connections", groupId = "zeek", containerFactory = "connectionListener")
	void handle(ImportedConnection imported) {
		log.info(imported.toString());
		Optional<Connection> existing = connectionRepo.findByCommunityId(imported.getCommunity_id());
		if(!existing.isPresent()) {
			Artifact<Session> artifact = artifactFactory.create(importedConverter.convert(imported));
			artifactDao.save(artifact);
		}
		else { // TODO enable updating of existing objects
			log.warn(WARN_DUPLICATE_FOUND + imported.toString());
		}
	}
}