/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.openjdk.jmh.annotations.Benchmark;

public class KafkaBenchmark {
	private static final Map<String, Object> conf;
	private static final KafkaProducer<byte[], byte[]> producer, finalProducer;
	static {
		conf = new HashMap<>();
		conf.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "archvm.localdomain:9092");
		producer = new KafkaProducer<>(conf, new ByteArraySerializer(), new ByteArraySerializer());
		finalProducer = new KafkaProducer<>(conf, new FinalByteArraySerializer(), new FinalByteArraySerializer());
	}
	private static final byte[] data = new byte[64];

	@Benchmark
	public void testSerializer() {
		producer.send(new ProducerRecord<byte[], byte[]>("perftest", data));
		producer.flush();
	}

	@Benchmark
	public void testFinalSerializer() {
		finalProducer.send(new ProducerRecord<byte[], byte[]>("perftest", data));
		finalProducer.flush();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			producer.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		try {
			finalProducer.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		super.finalize();
	}
}
