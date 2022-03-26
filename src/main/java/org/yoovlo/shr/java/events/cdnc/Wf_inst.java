package org.yoovlo.shr.java.events.cdnc;
import org.yoovlo.shr.java.Result;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowStub;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import org.yoovlo.shr.java.Globals;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.T;
import java.util.Optional;
public class Wf_inst {
	public Wf_pack wfp;
	public String wf_id;
	public Cdnc_svc cdnc_svc;
	public Wf_inst() {

	}
	public WorkflowStub wf;
	public WorkflowExecution exec;
	public WorkflowClient client;
	public Result init(Wf_pack lwfp,String lwf_id) {
		cdnc_svc=Globals.spring_ctx.getBean(Cdnc_svc.class);
		wfp=lwfp;
		wf_id=lwf_id;
		T.t("wf_id:"+wf_id+" wfp.domain:"+wfp.domain,"cdnc");
		if(cdnc_svc.wf_svc==null) {
			T.t("wf_svc==null","cdnc");
		}
		exec = new WorkflowExecution();
		exec.setWorkflowId(wf_id);
		client=WorkflowClient.newInstance(cdnc_svc.wf_svc,wfp.domain);
		wf=client.newUntypedWorkflowStub(exec,Optional.empty());
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