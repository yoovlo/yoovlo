package org.yoovlo.shr.java.events;

import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.ser.Json_deser;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.kafka.common.errors.WakeupException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/*
 * Consumer handler
 * Consume messages and send to threadpool that calls local req endpoint
 * Input:
 * thread_num_max: number of threads to perform consumption on
 * req_svc_proto: request service protocol
 */
public class Con_h {
	public AtomicBoolean closed;
	public Integer threads_num_max;
	public Integer threads_num_core;
	public ThreadPoolExecutor executor;
	public KafkaConsumer<String,Req> con;
	public Properties props;
	public String brokers_str;
	public String cons_group_id;
	public Collection<String> topics;
	public String topic;
	public Con_h() {
	}
	public void init() {
		T.t("topic "+topic,"events_con");
		topics=Arrays.asList(topic);
		props=new Properties();
		props.put("bootstrap.servers", brokers_str);
		props.put("group.id",cons_group_id);
		if(threads_num_max>1) {
			threads_num_core=threads_num_max/2;
		} else {
			threads_num_core=1;
		}
		con=new KafkaConsumer<>(props, new StringDeserializer(), new Json_deser<Req>(Req.class));
		con.subscribe(topics);
	}
	public void run() throws WakeupException {
		executor = new ThreadPoolExecutor(threads_num_core,threads_num_max, 0L, TimeUnit.MILLISECONDS,
		//CallerRunsPolicy will run the Record_handler in this thread, when the ArrayBlockingQueue is full
		new ArrayBlockingQueue<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());
		while(run_st()) {
			ConsumerRecords<String, Req> records=con.poll(100);
			for(final ConsumerRecord<String, Req> record:records) {
				executor.submit(new Record_handler(record));
			}
		}
	}
	/*
	run status
	*/
	public boolean run_st() {
		if(closed!=null) {
			return closed.get();
		}
		return true;
	}
	public void wakeup() {
		con.wakeup();
	}
	public void close() {
		con.close();
	}
}