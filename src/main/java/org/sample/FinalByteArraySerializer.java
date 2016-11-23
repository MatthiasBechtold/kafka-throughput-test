package org.sample;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

public final class FinalByteArraySerializer implements Serializer<byte[]> {
	@Override
	public byte[] serialize(String arg0, byte[] arg1) {
		return arg1;
	}

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}
}
