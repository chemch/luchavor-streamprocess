package com.luchavor.streamprocess.converter;

import org.springframework.stereotype.Component;

import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHostImpl;
import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.datamodel.util.FieldTypeConverter;
import com.luchavor.streamprocess.model.ImportedObservedHost;
import com.luchavor.streamprocess.model.ImportedTestArtifact;

@Component
public class ImportedConverter {
	
	public ObservedHost convert(ImportedObservedHost imported) {
		ObservedHost converted = new ObservedHostImpl();
		converted.setHostIp(imported.getHost());
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		return converted;
	}
	
	public TestArtifact convert(ImportedTestArtifact imported) {
		return new TestArtifact();
	}
}