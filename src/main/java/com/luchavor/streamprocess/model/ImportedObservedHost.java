package com.luchavor.streamprocess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportedObservedHost {
	private Long ts;
	private String host;
}