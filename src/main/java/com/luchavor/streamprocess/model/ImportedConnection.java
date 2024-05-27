package com.luchavor.streamprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedConnection {
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
	private String proto;
	private String service;
	private Double duration;
	private Integer orig_bytes;
	private Integer resp_bytes;
	private String conn_state;
	private Boolean local_orig;
	private Boolean local_resp;
	private Integer missed_bytes;
	private String history;
	private Integer orig_pkts;
	private Integer orig_ip_bytes;
	private Integer resp_pkts;
	private Integer resp_ip_bytes;
	private String tunnel_parents;
	private String community_id;
	private Integer vlan;
	private Integer inner_vlan;
	private String orig_l2_addr;
	private String resp_l2_addr;
}