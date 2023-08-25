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

import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.streamprocess.model.ImportedObservedHost;

@EnableKafka
@Configuration
public class KafkaConfig {

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
}