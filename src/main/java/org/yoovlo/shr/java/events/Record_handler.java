package org.yoovlo.shr.java.events;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.req.Req_handler;
import org.yoovlo.shr.java.T;
import org.apache.kafka.clients.consumer.ConsumerRecord;
public class Record_handler implements Runnable {
	public Req_handler req_handler;
	public ConsumerRecord<String, Req> record;
	public Req req;
	public Record_handler(ConsumerRecord<String, Req> lrecord) {
		record=lrecord;
		T.t("Record_handler construct","events_con");
		req_handler=new Req_handler();
	}
	@Override
	public void run() {
		T.t("run","events_con");
		req=record.value();
		T.t("Record_handler.run k: "+record.key()+" v: "+req.toString(),"events_con");
		req_handler.req_exec(req);
	}
}