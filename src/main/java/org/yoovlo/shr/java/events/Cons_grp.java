package org.yoovlo.shr.java.events;

import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.ser.Json_deser;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.yoovlo.shr.java.T;
/**
Instantiate a group of consumer to consume topic(s)
Input:
thread_num_max: number of threads to perform consumption on
req_svc_proto: request service protocol
 */
@Component
public class Cons_grp {
	@Value("${kafka.cons.threads_num}")
	public Integer threads_num;
	public ThreadPoolExecutor executor;
	@Value("${kafka.con.threads_num_max}")
	public Integer con_threads_num_max;
	@Value("${kafka.cons.group.id}")
	public String cons_group_id;
	@Value("${kafka.brokers}")
	public String brokers_str;
	@Value("${kafka.topic}")
	public String topic;
	public Cons_grp() {
		T.t("Cons_grp");
	}
	public void run_t() {
		T.t("run_t");
		executor = new ThreadPoolExecutor(threads_num,threads_num, 0L, TimeUnit.MILLISECONDS,
		new ArrayBlockingQueue<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());
		for(int a=0;a<threads_num;++a) {
			Con_h con_h=new Con_h();
			con_h.threads_num_max=con_threads_num_max;
			con_h.brokers_str=brokers_str;
			con_h.cons_group_id=cons_group_id;
			con_h.topic=topic;
			con_h.init();
			executor.execute(new Con_runner(con_h));
		}
	}
	public void run_f() {
		executor.shutdown();
	}
}