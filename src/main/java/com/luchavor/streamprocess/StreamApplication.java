package com.luchavor.streamprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
@ComponentScan({"com.luchavor.streamprocess","com.luchavor.neo4japi", "com.luchavor.datamodel"})
public class StreamApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}
}