package org.yoovlo.shr.java.events.cdnc.req_wf;

import com.uber.cadence.workflow.WorkflowMethod;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.events.cdnc.Wf_run_i;

public interface Wf_i extends Wf_run_i {
    @WorkflowMethod(taskList=Req_wf_p.Task_list,executionStartToCloseTimeoutSeconds = 180)
    public Result run(Req req);
}
