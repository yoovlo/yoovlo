package org.yoovlo.shr.java.events.tmpl.req_wf;
import java.util.HashMap;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.WorkflowInterface;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.events.tmpl.Wf_run_i;
@WorkflowInterface
public interface Wf_i extends Wf_run_i {
	//@WorkflowMethod(taskList=Req_wf_p.Task_list,executionStartToCloseTimeoutSeconds = 180)
	@WorkflowMethod
	public Result run(Req req);
}
