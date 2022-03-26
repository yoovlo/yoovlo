package org.yoovlo.shr.java.events;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.ser.Json_ser;
import org.yoovlo.shr.java.Utils;

public class Prd_h {
	public String thread_id;
	public KafkaProducer<String,Req> prd;
	public Properties props;
	public String brokers_str;
	public Prd_h() {
		
	}
	public void init() {
		T.t("Prd_h init","events_prd");
        props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers_str);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Utils.node_id()+":events_prd_"+thread_id);
        prd=new KafkaProducer<String,Req>(props,new StringSerializer(), new Json_ser<Req>(Req.class));
    }
}
