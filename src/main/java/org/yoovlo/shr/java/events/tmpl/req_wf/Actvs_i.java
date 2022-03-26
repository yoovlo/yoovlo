package org.yoovlo.shr.java.events.tmpl.req_wf;

import io.temporal.activity.ActivityMethod;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Result;
import io.temporal.activity.ActivityInterface;
@ActivityInterface
public interface Actvs_i {
	//(scheduleToCloseTimeoutSeconds = 180)
	@ActivityMethod
	public Result req_exec(Req req);
}
