package com.luchavor.streamprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedSoftware {
	private Long ts;
	private String host;
	private String software_type;
	private String name;
	@JsonProperty("version.major")
	private Integer major_version;
	@JsonProperty("version.minor")
	private Integer minor_version;
	private String unparsed_version;
}