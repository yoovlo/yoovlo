package org.yoovlo.shr.java.events.cdnc;
import org.yoovlo.shr.java.Ser;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.req.Req_info;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Globals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

@Component
public class Cdnc_svc {
	@Value("${cdnc.host}")
	public String host;
	@Value("${cdnc.port}")
	public int port;
	@Value("${cdnc.domain}")
	public String domain;
	/**
	status 
	*/
	public int st;
	/**
	workflow packages map
	*/
	public HashMap<String,Wf_pack> wfps_m;
	public IWorkflowService wf_svc;
	public Cdnc_svc() {
		wfps_m=new HashMap<String,Wf_pack>();
		st=0;
	}
	@PostConstruct
	public void init() {
		T.t("Cdnc_svc init host:"+host,"cdnc");
	}
	public void run_t() {
		if(host==null || host.equals("")) {
			T.t("host invalid not creating Cadence connection","cdnc");
			return;
		}
		Globals.svcs_m.put("Cdnc",Cdnc_svc.class);
		wf_svc=new WorkflowServiceTChannel(host,port);
		st=1;
	}
	/**
	workflow packages add
	*/
	public Result wfps_add(Wf_pack wfp) {
		T.t("wfps_add","cdnc");
		Result res=new Result();
		wfps_m.put(wfp.hash,wfp);
		wfp.server_set(host,port,domain);
		wfp.worker_start();
		return res;
	}
	/**
	workflow instance operation
		a workflow instance is a wrapper around a cdnc.workflow
	*/
	public void wfi_op(HashMap<String,Object> b, Req_info req_info) {
		Result res=null;
		String op=(String) b.get("op");
		String wfp_hash=(String) b.get("wfp_hash");
		T.t("wfi_op op:"+op+" wfp_hash:"+wfp_hash,"cdnc");
		Wf_pack wfp=wfps_m.get(wfp_hash);
		if(wfp==null) {
			res=new Result();
			res.error_add("h","wfp_hash inval");
			req_info.r(res);
			return;
		}
		String wfi_id=null;
		wfi_id=(String)b.get("wfi_id");
		Wf_inst inst=null;
		Req req=null;
		String cron_sch=null;
		switch(op) {
			case "crt":
				break;
			default:
				inst=new Wf_inst();
				inst.init(wfp,wfi_id);
				break;
		}
		switch(op) {
			case "query": case"crt":
				String req_s=(String)b.get("req");
				req=Ser.sUnser(req_s,"json",Req.class);
				T.t("req: "+req.toString(),"cdnc");
				break;
		}
		switch(op) {
			case "crt"://create
				cron_sch=(String)b.get("cron_sch");
				res=wfp.inst_crt(wfi_id,cron_sch,req);
				T.t("res:"+res.toString(),"cdnc");
				break;
			case "run_t":
				break;
			case "run_f":
				/*
				stop and set state to a non-resumptive position but maintain historical information of this inst
				*/
				inst.run_f();
				break;
			/*
			todo implement pause,dst
			*/
			case "dst"://destroy
				/*
				stop and destroy historical information of this inst
				*/
				inst.dst();
				break;
			case "pause":
				/*
				halt execution, but maintain resumptive state
				halt scheduled execution
				*/
				break;
			case "query":
				String qt=(String)b.get("qt");
				res=inst.query(qt,req);
				break;
			case "info":
				res=inst.info();
				break;
		}
		req_info.r(res);
	}
}