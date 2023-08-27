package com.luchavor.streamprocess.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedObservedFile {
	private Long ts;
	private String fuid;
	private String uid;
	@JsonProperty("id.orig_h")
	private String orig_h;
	@JsonProperty("id.orig_p")
	private Integer orig_p;
	@JsonProperty("id.resp_h")
	private String resp_h;
	@JsonProperty("id.resp_p")
	private Integer resp_p;
	private String source;
	private Integer depth;
	private List<String> analyzers;
	private String mime_type;
	private Double duration;
	private Boolean local_orig;
	private Boolean is_orig;
	private Integer seen_bytes;
	private Integer missing_bytes;
	private Integer overflow_bytes;
	private Boolean timedout;
	private String md5;
	private String sha1;
	private String sha256;
	private String filename;
	private Integer total_bytes;
	private String parent_fuid;
	private String extracted;
	private Integer extracted_size;
	private Boolean extracted_cutoff;
}