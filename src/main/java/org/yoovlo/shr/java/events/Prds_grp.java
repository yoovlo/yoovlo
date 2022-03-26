package org.yoovlo.shr.java.events;

import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.ser.Json_deser;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.Utils;
/**
Instantiate a group of prds and a threadpool to multiplex sending messages
This should be optimized later when there is high enough load to warrant a multi producer implementation
Producer configuration should be tuned for high performance based with partition,buffer configuration
Input:
thread_num_max: number of threads to perform production on
*/
@Component
public class Prds_grp {
	@Value("${kafka.prds.threads_num}")
	public Integer threads_num;
	public ThreadPoolExecutor executor;
	@Value("${kafka.brokers}")
	public String brokers_str;
	@Value("${kafka.topic}")
	public String topic;
	public Prd_h[] prd_hs;
	public Prds_grp() {
		T.t("Prds_grp","events_prd");
	}
	public void run_t() {
		T.t("run_t","events_prd");
		prd_hs=new Prd_h[threads_num];
		executor = new ThreadPoolExecutor(threads_num,threads_num, 0L, TimeUnit.MILLISECONDS,
		new ArrayBlockingQueue<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());
		for(int a=0;a<threads_num;++a) {
			Prd_h prd_h=new Prd_h();
			prd_h.brokers_str=brokers_str;
			prd_h.thread_id=String.valueOf(a);
			prd_h.init();
			prd_hs[a]=prd_h;
		}
	}

	public Future<RecordMetadata> send(String k, Req v) {
		return send(topic,k,v);
	}
	public Future<RecordMetadata> send(String t, String k, Req v) {
		T.t("t "+t+" k "+k+" v "+v.toString(),"events_prd");
		int a=ThreadLocalRandom.current().nextInt(0,threads_num);
		Prd_h prd_h=prd_hs[a];
		Prd_runner prd_r=new Prd_runner(prd_h);
		prd_r.t=t;
		prd_r.k=k;
		prd_r.v=v;
		Future<Future<RecordMetadata>> submit_future=executor.submit(prd_r);
		try {
			return submit_future.get();
		} catch(Exception e) {
			Utils.he(e);
		}
		return null;
	}
	public void run_f() {
		executor.shutdown();
	}
	@Override
	public String toString() {
		return "Prds_grp";
	}
}