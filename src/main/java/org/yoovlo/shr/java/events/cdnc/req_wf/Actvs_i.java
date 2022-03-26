package org.yoovlo.shr.java.events.cdnc.req_wf;

import com.uber.cadence.activity.ActivityMethod;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Result;

public interface Actvs_i {
	@ActivityMethod(scheduleToCloseTimeoutSeconds = 180)
	public Result req_exec(Req req);
	/*
	@ActivityMethod(scheduleToCloseTimeoutSeconds = 180)
	public Ox x_get(long x_id);
	@ActivityMethod(scheduleToCloseTimeoutSeconds = 180)
	public Result x_updt();
	*/
}
