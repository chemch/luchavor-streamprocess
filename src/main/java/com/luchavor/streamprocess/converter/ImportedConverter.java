package com.luchavor.streamprocess.converter;

import org.springframework.stereotype.Component;

import com.luchavor.datamodel.artifact.network.common.NetworkProtocolType;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHostImpl;
import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedService;
import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedServiceImpl;
import com.luchavor.datamodel.artifact.test.TestArtifact;
import com.luchavor.datamodel.util.FieldTypeConverter;
import com.luchavor.streamprocess.model.ImportedObservedHost;
import com.luchavor.streamprocess.model.ImportedObservedService;
import com.luchavor.streamprocess.model.ImportedTestArtifact;

@Component
public class ImportedConverter {
	
	public ObservedHost convert(ImportedObservedHost imported) {
		ObservedHost converted = new ObservedHostImpl();
		converted.setHostIp(imported.getHost());
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		return converted;
	}
	
	public ObservedService convert(ImportedObservedService imported) {
		ObservedService converted = new ObservedServiceImpl();
		converted.setHostIp(imported.getHost());
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setPort(imported.getPort_num());
		converted.setNetworkProtocolType(NetworkProtocolType.valueOf(imported.getPort_proto().toUpperCase()));
		converted.setServices(imported.getService());
		return converted;
	}
	
	public TestArtifact convert(ImportedTestArtifact imported) {
		return new TestArtifact();
	}
}