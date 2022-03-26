package org.yoovlo.shr.java.events.cdnc.req_wf;

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
	/*
	@Override
	public Ox x_get(long x_id) {
		T.t("x_get x_id: "+x_id,"req_wf");
	}
	@Override
	public Result x_updt() {
		Result res=new Result();
		Req req=new Req();
		req.exec_type="rs";
		req.s="Gen";
		req.a="x_updt";
		req.b_init();
		HashMap<String,Object> x_b=new HashMap<String,Object>();
		req.b.put("x",x_b);
		T.t("x.id:"+x.id,"req_wf");
		x_b.put("id",x.id);
		x_b.put("class_name","Ox");
		HashMap<String,Object> props=new HashMap<String,Object>();
		x_b.put("props",props);
		prds.send("req",req);
		return res;
	}
	*/
}
