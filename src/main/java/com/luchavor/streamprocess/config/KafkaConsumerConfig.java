package com.luchavor.streamprocess.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedService;
import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.streamprocess.model.ImportedConnection;
import com.luchavor.streamprocess.model.ImportedModbusEvent;
import com.luchavor.streamprocess.model.ImportedObservedFile;
import com.luchavor.streamprocess.model.ImportedObservedHost;
import com.luchavor.streamprocess.model.ImportedObservedService;
import com.luchavor.streamprocess.model.ImportedSoftware;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${kafka-server}")
	private String kafkaServer;

	// zeek group id for events
	private String groupId = "zeek";

	// tag::testArtifact[]
	@Bean
	ConsumerFactory<String, TestArtifact> testArtifactConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(TestArtifact.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, TestArtifact> testArtifactListener() {
		ConcurrentKafkaListenerContainerFactory<String, TestArtifact> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(testArtifactConsumerFactory());
		// return factory
		return factory;
	}
	// end::testArtifact[]
	
	// tag::modbusEvent[]
		@Bean
		ConsumerFactory<String, ImportedModbusEvent> modbusEventConsumerFactory() {
			Map<String, Object> config = new HashMap<>();
			// set configuration settings
			config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
			config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
			config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
			// return json format of message
			return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
					new JsonDeserializer<>(ImportedModbusEvent.class));
		}

		@Bean
		ConcurrentKafkaListenerContainerFactory<String, ImportedModbusEvent> modbusEventListener() {
			ConcurrentKafkaListenerContainerFactory<String, ImportedModbusEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
			// set consumer factory
			factory.setConsumerFactory(modbusEventConsumerFactory());
			// return factory
			return factory;
		}
		// end::modbusEvent[]

	// tag::observedHost[]
	@Bean
	ConsumerFactory<String, ImportedObservedHost> observedHostConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(ImportedObservedHost.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ImportedObservedHost> observedHostListener() {
		ConcurrentKafkaListenerContainerFactory<String, ImportedObservedHost> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(observedHostConsumerFactory());
		// return factory
		return factory;
	}
	// end::observedHost[]
	
	// tag::observedService[]
	@Bean
	ConsumerFactory<String, ImportedObservedService> observedServiceConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(ImportedObservedService.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ImportedObservedService> observedServiceListener() {
		ConcurrentKafkaListenerContainerFactory<String, ImportedObservedService> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(observedServiceConsumerFactory());
		// return factory
		return factory;
	}
	// end::observedService[]
	
	// tag::serviceEnrichment[]
		@Bean
		ConsumerFactory<String, ObservedService> serviceEnrichmentConsumerFactory() {
			Map<String, Object> config = new HashMap<>();
			// set configuration settings
			config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
			config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
			config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
			// return json format of message
			return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
					new JsonDeserializer<>(ObservedService.class));
		}

		@Bean
		ConcurrentKafkaListenerContainerFactory<String, ObservedService> serviceEnrichmentListener() {
			ConcurrentKafkaListenerContainerFactory<String, ObservedService> factory = new ConcurrentKafkaListenerContainerFactory<>();
			// set consumer factory
			factory.setConsumerFactory(serviceEnrichmentConsumerFactory());
			// return factory
			return factory;
		}
		// end::serviceEnrichment[]
	
	// tag::observedFile[]
	@Bean
	ConsumerFactory<String, ImportedObservedFile> observedFileConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(ImportedObservedFile.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ImportedObservedFile> observedFileListener() {
		ConcurrentKafkaListenerContainerFactory<String, ImportedObservedFile> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(observedFileConsumerFactory());
		// return factory
		return factory;
	}
	// end::observedFile[]
	
	// tag::software[]
	@Bean
	ConsumerFactory<String, ImportedSoftware> softwareConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(ImportedSoftware.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ImportedSoftware> softwareListener() {
		ConcurrentKafkaListenerContainerFactory<String, ImportedSoftware> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(softwareConsumerFactory());
		// return factory
		return factory;
	}
	// end::software[]
	
	// tag::connection[]
	@Bean
	ConsumerFactory<String, ImportedConnection> connectionConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		// set configuration settings
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		// return json format of message
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(ImportedConnection.class));
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, ImportedConnection> connectionListener() {
		ConcurrentKafkaListenerContainerFactory<String, ImportedConnection> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// set consumer factory
		factory.setConsumerFactory(connectionConsumerFactory());
		// return factory
		return factory;
	}
	// end::connection[]
}