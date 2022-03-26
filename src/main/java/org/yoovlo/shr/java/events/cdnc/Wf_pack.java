package org.yoovlo.shr.java.events.cdnc;

import com.google.api.services.storage.Storage.Objects;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.WorkflowExecution;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Object_g;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.T;

public class Wf_pack<Wf_c,Wf_i extends Wf_run_i> extends Object_g {
	public String host;
	public int port;
	public String domain;
	public String task_list;
	public Class<Wf_c> wf_class;
	public Class<Wf_i> wf_i;
	public Object actvs;
	public String cron_sch;
	public Wf_pack(String lhash,String ltask_list,Class<Wf_c> lwf_class,Class<Wf_i> lwf_i,Object lactvs,String lcron_sch) {
		hash=lhash;
		task_list=ltask_list;
		wf_class=lwf_class;
		wf_i=lwf_i;
		actvs=lactvs;
		cron_sch=lcron_sch;
	}
	public Wf_pack(String lhash,String ltask_list,Class<Wf_c> lwf_class,Class<Wf_i> lwf_i,Object lactvs) {
		this(lhash,ltask_list,lwf_class,lwf_i,lactvs,null);
	}
	public void server_set(String lhost,int lport,String ldomain) {
		host=lhost;
		port=lport;
		domain=ldomain;
	}
	public Result worker_start() {
		Result res=new Result();
		T.t("workerstart domain:"+domain,"cdnc");
		try {
			Worker.Factory factory=new Worker.Factory(host,port,domain);
			Worker worker=factory.newWorker(task_list);
			worker.registerWorkflowImplementationTypes(wf_class);
			worker.registerActivitiesImplementations(actvs);
			factory.start();
		} catch(Exception e) {
			res.error_add("e","exception");
			res.info_init();
			res.info.put("excp",e);
		}
		return res;
	}
	public 	Result inst_crt(String id,String lcron_sch,Req req) {
		T.t("inst_crt "+hash,"cdnc");
		WorkflowClient workflowClient = WorkflowClient.newInstance(host,port,domain);
		WorkflowOptions opts=null;
		WorkflowOptions.Builder opts_b=null;
		if(lcron_sch==null && cron_sch!=null) {
			lcron_sch=cron_sch;
		}
		opts_b=new WorkflowOptions.Builder();
		opts_b=opts_b.setWorkflowId(id);
		T.t("inst_crt id:"+id,"cdnc");
		if(lcron_sch!=null) {
			T.t("lcron_sch:"+lcron_sch,"cdnc");
			opts_b=opts_b.setCronSchedule(lcron_sch);
		}
		opts=opts_b.build();
		Wf_i wf = workflowClient.newWorkflowStub(wf_i,opts);
		WorkflowExecution workflowExecution=WorkflowClient.start(wf::run,req);
		//Result res=wf.run(req);
		T.t("wf asyncStart post","cdnc");
		Result res=new Result();
		return res;
	}
}
