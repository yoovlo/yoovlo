package org.yoovlo.shr.java.events.tmpl;
import org.yoovlo.shr.java.Ser;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.req.Req_info;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Globals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

@Component
public class Tmpl_svc {
	public int st;
	@Value("${tmpl.host}")
	public String host;
	@Value("${tmpl.port}")
	public int port;
	@Value("${tmpl.ns}")
	public String ns;
	/**
	workflow packages map
	*/
	public HashMap<String,Wf_pack> wfps_m;
	public WorkflowServiceStubs wf_svc;
	public WorkflowClient wf_cl;
	public ManagedChannel channel;
	public Tmpl_svc() {
		wfps_m=new HashMap<String,Wf_pack>();
	}
	@PostConstruct
	public void init() {
		T.t("Tmpl_svc init host:"+host,"tmpl");
	}
	public void run_t() {
		if(host==null || port==0) {
			T.t("host invalid not creating Cadence connection","tmpl");
			return;
		}
		Globals.svcs_m.put("Tmpl",Tmpl_svc.class);
		channel=ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		wf_svc=WorkflowServiceStubs.newInstance(WorkflowServiceStubsOptions.newBuilder().setChannel(channel).build());
		wf_cl=WorkflowClient.newInstance(wf_svc,WorkflowClientOptions.newBuilder().setNamespace(ns).build());
		st=1;
	}
	/**
	workflow packages add
	*/
	public Result wfps_add(Wf_pack wfp) {
		T.t("wfps_add","tmpl");
		Result res=new Result();
		wfps_m.put(wfp.hash,wfp);
		wfp.cl_set(wf_cl);
		wfp.worker_start();
		return res;
	}
	/**
	workflow instance operation
		a workflow instance is a wrapper around a tmpl.workflow
	*/
	public void wfi_op(HashMap<String,Object> b, Req_info req_info) {
		Result res=null;
		String op=(String) b.get("op");
		String wfp_hash=(String) b.get("wfp_hash");
		T.t("wfi_op op:"+op+" wfp_hash:"+wfp_hash,"tmpl");
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
				inst.init(wfp,wfi_id,wf_cl);
				break;
		}
		switch(op) {
			case "query": case"crt":
				String req_s=(String)b.get("req");
				req=Ser.sUnser(req_s,"json",Req.class);
				T.t("req: "+req.toString(),"tmpl");
				break;
		}
		switch(op) {
			case "crt"://create
				cron_sch=(String)b.get("cron_sch");
				res=wfp.inst_crt(wfi_id,cron_sch,req);
				T.t("res:"+res.toString(),"tmpl");
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