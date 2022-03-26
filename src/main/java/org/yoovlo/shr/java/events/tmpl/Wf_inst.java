package org.yoovlo.shr.java.events.tmpl;
import org.yoovlo.shr.java.Result;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.yoovlo.shr.java.Globals;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.T;
import java.util.Optional;
public class Wf_inst {
	public Wf_pack wfp;
	public String wf_id;
	public Tmpl_svc tmpl_svc;
	public Wf_inst() {

	}
	public WorkflowStub wf;
	public WorkflowExecution exec;
	public WorkflowClient cl;
	public Result init(Wf_pack lwfp,String lwf_id,WorkflowClient cl) {
		tmpl_svc=Globals.spring_ctx.getBean(Tmpl_svc.class);
		wfp=lwfp;
		wf_id=lwf_id;
		T.t("wf_id:"+wf_id+" wfp.domain:"+wfp.ns,"tmpl");
		if(tmpl_svc.wf_svc==null) {
			T.t("wf_svc==null","tmpl");
		}
		if(cl==null) {
			cl=tmpl_svc.wf_cl;
		}
		wf=cl.newUntypedWorkflowStub(wf_id,null,null);
		Result res=new Result();
		return res;
	}
	public Result query(String qt,Req req) {
		String result=wf.query(qt,String.class);
		return new Result();
	}
	public Result info() {
		return new Result();
	}
	public Result run_f() {
		wf.cancel();
		Result res=new Result();
		return res;
	}
	/*
	ensure that it is stopped
	destoy all information associated with this inst
	*/
	public Result dst() {
		Result res=null;
		res=run_f();
		return res;
	}
}