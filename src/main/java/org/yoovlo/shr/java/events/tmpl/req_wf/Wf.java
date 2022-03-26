package org.yoovlo.shr.java.events.tmpl.req_wf;
import org.yoovlo.shr.java.req.Req;
import io.temporal.workflow.Workflow;
import io.temporal.activity.ActivityOptions;
import java.time.Duration;
import org.yoovlo.shr.java.Result;

public class Wf implements Wf_i {
	public final Actvs_i actvs=Workflow.newActivityStub(Actvs_i.class,ActivityOptions.newBuilder().setScheduleToCloseTimeout(Duration.ofMillis(180000)).build());
	public long x_id;
	@Override
	public Result run(Req req) {
		Result res=new Result();
		actvs.req_exec(req);
		return res;
	}
}