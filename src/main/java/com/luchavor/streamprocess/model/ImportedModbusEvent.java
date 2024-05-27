package com.luchavor.streamprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedModbusEvent {
	private Long ts;
	private String uid;
	@JsonProperty("id.orig_h")
	private String orig_h;
	@JsonProperty("id.orig_p")
	private Integer orig_p;
	@JsonProperty("id.resp_h")
	private String resp_h;
	@JsonProperty("id.resp_p")
	private Integer resp_p;
	private Integer tid;
	private Integer unit;
	private String func;
	private String pdu_type;
}