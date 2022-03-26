package org.yoovlo.shr.java.events;
import org.yoovlo.shr.java.req.Req;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.yoovlo.shr.java.T;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class Prd_runner implements Callable {
	private final Prd_h prd_h;
	public String t;
	public String k;
	public Req v;

	public Prd_runner(Prd_h lprd_h) {
		prd_h=lprd_h;
		T.t("Prd_runner","events_prd");
	}

	public Future<RecordMetadata>call() {
		return prd_h.prd.send(new ProducerRecord<String, Req>(t,k,v));
	}
 }