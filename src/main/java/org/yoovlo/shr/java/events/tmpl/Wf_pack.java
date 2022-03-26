package org.yoovlo.shr.java.events.tmpl;
import java.time.Duration;
import com.google.api.services.storage.Storage.Objects;
import io.temporal.workflow.Workflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.api.common.v1.WorkflowExecution;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Object_g;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.T;

public class Wf_pack<Wf_c,Wf_i extends Wf_run_i,Actvs_i> extends Object_g {
	public WorkflowClient cl;
	public String ns;
	public String task_list;
	public Class<Wf_c> wf_class;
	public Class<Wf_i> wf_i;
	public long run_to=5000;
	public long exec_to=5000; 
	public long actvs_start_to_close_to=5000;
	public Class<Actvs_i> actvs_i;
	public Object actvs;
	public String cron_sch;
	public Wf_pack(String lhash,String ltask_list,Class<Wf_c> lwf_class,Class<Wf_i> lwf_i,Class<Actvs_i> lactvs_i,Object lactvs,String lcron_sch) {
		hash=lhash;
		task_list=ltask_list;
		wf_class=lwf_class;
		wf_i=lwf_i;
		actvs_i=lactvs_i;
		actvs=lactvs;
		cron_sch=lcron_sch;
	}
	public Wf_pack(String lhash,String ltask_list,Class<Wf_c> lwf_class,Class<Wf_i> lwf_i,Class<Actvs_i> lactvs_i,Object lactvs) {
		this(lhash,ltask_list,lwf_class,lwf_i,lactvs_i,lactvs,null);
	}
	public void cl_set(WorkflowClient lcl) {
		cl=lcl;
	}
	public Result worker_start() {
		Result res=new Result();
		T.t("workerstart ns:"+ns,"tmpl");
		try {
			WorkerFactory factory=WorkerFactory.newInstance(cl);
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
		T.t("inst_crt "+hash,"tmpl");
		WorkflowOptions opts=null;
		WorkflowOptions.Builder opts_b=null;
		if(lcron_sch==null && cron_sch!=null) {
			lcron_sch=cron_sch;
		}
		opts_b=WorkflowOptions.newBuilder();
		opts_b=opts_b.setWorkflowId(id);
		opts_b=opts_b.setTaskQueue(task_list);
		T.t("inst_crt id:"+id,"tmpl");
		if(lcron_sch!=null) {
			T.t("lcron_sch:"+lcron_sch,"tmpl");
			opts_b=opts_b.setCronSchedule(lcron_sch);
		}
		opts_b=opts_b.setWorkflowRunTimeout(Duration.ofMillis(run_to));
		opts_b=opts_b.setWorkflowExecutionTimeout(Duration.ofMillis(exec_to));
		opts=opts_b.build();
		Wf_i wf = cl.newWorkflowStub(wf_i,opts);
		WorkflowExecution workflowExecution=WorkflowClient.start(wf::run,req);
		//Result res=wf.run(req);
		T.t("wf asyncStart post","tmpl");
		Result res=new Result();
		return res;
	}
}
