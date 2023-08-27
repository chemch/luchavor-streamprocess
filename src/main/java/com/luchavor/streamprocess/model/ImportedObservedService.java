package com.luchavor.streamprocess.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedObservedService {
	private Long ts;
	private String host;
	private Integer port_num;
	private String port_proto;
	private List<String> service;
}