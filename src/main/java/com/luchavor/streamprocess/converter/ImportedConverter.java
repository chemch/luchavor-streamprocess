package com.luchavor.streamprocess.converter;

import org.springframework.stereotype.Component;

import com.luchavor.datamodel.artifact.network.common.NetworkProtocolType;
import com.luchavor.datamodel.artifact.network.observation.observedfile.ObservedFile;
import com.luchavor.datamodel.artifact.network.observation.observedfile.ObservedFileImpl;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHost;
import com.luchavor.datamodel.artifact.network.observation.observedhost.ObservedHostImpl;
import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedService;
import com.luchavor.datamodel.artifact.network.observation.observedservice.ObservedServiceImpl;
import com.luchavor.datamodel.artifact.network.observation.software.Software;
import com.luchavor.datamodel.artifact.network.observation.software.SoftwareImpl;
import com.luchavor.datamodel.artifact.network.session.connection.Connection;
import com.luchavor.datamodel.artifact.network.session.connection.ConnectionImpl;
import com.luchavor.datamodel.artifact.network.session.modbus.ModbusEvent;
import com.luchavor.datamodel.artifact.network.session.modbus.ModbusEventImpl;
import com.luchavor.datamodel.artifact.network.session.modbus.PduType;
import com.luchavor.datamodel.util.FieldTypeConverter;
import com.luchavor.streamprocess.model.ImportedConnection;
import com.luchavor.streamprocess.model.ImportedModbusEvent;
import com.luchavor.streamprocess.model.ImportedObservedFile;
import com.luchavor.streamprocess.model.ImportedObservedHost;
import com.luchavor.streamprocess.model.ImportedObservedService;
import com.luchavor.streamprocess.model.ImportedSoftware;

@Component
public class ImportedConverter {
	
	public ObservedHost convert(ImportedObservedHost imported) {
		ObservedHost converted = new ObservedHostImpl();
		converted.setHostIp(imported.getHost());
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		return converted;
	}
	
	public ModbusEvent convert(ImportedModbusEvent imported) {
		ModbusEvent converted = new ModbusEventImpl();
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setCuid(imported.getUid());
		converted.setOriginatorIp(imported.getOrig_h());
		converted.setResponderIp(imported.getResp_h());
		converted.setOriginatorPort(imported.getOrig_p());
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setCuid(imported.getUid());
		converted.setOriginatorIp(imported.getOrig_h());
		converted.setResponderIp(imported.getResp_h());
		converted.setOriginatorPort(imported.getOrig_p());
		converted.setResponderPort(imported.getResp_p());
		converted.setFunction(imported.getFunc());
		converted.setPduType(PduType.valueOf(imported.getPdu_type().toUpperCase()));
		converted.setUnit(imported.getUnit());
		converted.setTransactionId(imported.getTid());
		return converted;
	}
	
	public ObservedFile convert(ImportedObservedFile imported) {
		ObservedFile converted = new ObservedFileImpl();
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setCuid(imported.getUid());
		// initialize connection object to null; it will get added after object creation
		converted.setConnection(null);
		converted.setOriginatorIp(imported.getOrig_h());
		converted.setResponderIp(imported.getResp_h());
		converted.setFuid(imported.getFuid());
		converted.setSource(imported.getSource());
		converted.setDepth(imported.getDepth());
		converted.setAnalyzers(imported.getAnalyzers());
		converted.setMimeType(imported.getMime_type());
		converted.setDuration(imported.getDuration());
		converted.setLocalOriginationFlag(imported.getLocal_orig());
		converted.setFromOriginatorFlag(imported.getIs_orig());
		converted.setBytesSeen(imported.getSeen_bytes());
		converted.setMissingBytes(imported.getMissing_bytes());
		converted.setOverflowBytes(imported.getOverflow_bytes());
		converted.setTimedOutFlag(imported.getTimedout());
		converted.setMd5Hash(imported.getMd5());
		converted.setSha1Hash(imported.getSha1());
		converted.setOriginatorPort(imported.getOrig_p());
		converted.setResponderPort(imported.getResp_p());
		converted.setFilename(imported.getFilename());
		converted.setExtractedFileCutoffFlag(imported.getExtracted_cutoff());
		converted.setExtractedFilename(imported.getExtracted());
		converted.setExtractedFileSize(imported.getExtracted_size());
		converted.setSha256Hash(imported.getSha256());
		converted.setParentFuid(imported.getParent_fuid());
		converted.setTotalFileBytes(imported.getTotal_bytes());
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
	
	public Software convert(ImportedSoftware imported) {
		Software converted = new SoftwareImpl();
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setHostIp(imported.getHost());
		converted.setName(imported.getName());
		converted.setAdditionalVersionInfo(imported.getUnparsed_version());
		converted.setMajorVersion(imported.getMajor_version());
		converted.setMinorVersion(imported.getMinor_version());
		return converted;
	}
	
	public Connection convert(ImportedConnection imported) {
		Connection converted = new ConnectionImpl();
		converted.setTimestamp(FieldTypeConverter.convertLongEpochTimestamp(imported.getTs()));
		converted.setCuid(imported.getUid());
		converted.setOriginatorIp(imported.getOrig_h());
		converted.setResponderIp(imported.getResp_h());
		converted.setOriginatorPort(imported.getOrig_p());
		converted.setResponderPort(imported.getResp_p());
		converted.setNetworkProtocolType(NetworkProtocolType.valueOf(imported.getProto().toUpperCase()));
		converted.setService(imported.getService());
		converted.setDuration(imported.getDuration());
		converted.setOriginatorPayloadByteCount(imported.getOrig_bytes());
		converted.setResponderPayloadByteCount(imported.getResp_bytes());
		converted.setConnectionState(imported.getConn_state());
		converted.setLocalOriginatorFlag(imported.getLocal_orig());
		converted.setLocalResponderFlag(imported.getLocal_resp());
		converted.setMissedByteCount(imported.getMissed_bytes());
		converted.setStateHistory(imported.getHistory());
		converted.setOriginatorPacketCount(imported.getOrig_pkts());
		converted.setOriginatorTotalByteCount(imported.getOrig_ip_bytes());
		converted.setResponderPacketCount(imported.getResp_pkts());
		converted.setResponderTotalByteCount(imported.getResp_ip_bytes());
		converted.setParentTunnelUid(imported.getTunnel_parents());
		converted.setCommunityId(imported.getCommunity_id());
		converted.setVlan(imported.getVlan());
		converted.setInnerVlan(imported.getInner_vlan());
		converted.setOriginatorMacAddress(imported.getOrig_l2_addr());
		converted.setResponderMacAddress(imported.getResp_l2_addr());
		return converted;
	}
}