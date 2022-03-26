package org.yoovlo.shr.java.events.tmpl.req_wf;

import org.yoovlo.shr.java.Ox;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.req.Req_handler;
import org.yoovlo.shr.java.T;
import java.util.HashMap;
import java.util.ArrayList;
import org.yoovlo.shr.java.Globals;
import org.yoovlo.shr.java.req.Req_handler;

public class Actvs implements Actvs_i {
	public Actvs() {
	}
	public Req_handler req_handler;
	@Override
	public Result req_exec(Req req) {
		req_handler=new Req_handler();
		T.t("Actvs.req_exec req: "+req.toString(),"req_wf");
		Result res=req_handler.req_exec(req);
		return res;
	}
}
