package org.yoovlo.shr.java.req;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import org.yoovlo.shr.java.Ser;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.Globals;
import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.req.Req_info;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.req.Req_handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.common.MediaTypeNames;
import com.linecorp.armeria.common.QueryParams;

import com.linecorp.armeria.server.AbstractHttpService;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Consumes;
import com.linecorp.armeria.server.annotation.Default;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.Produces;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.logging.LoggingService;
/*
this is an rpc server, not a jvm exec server
*/
@Component
public class Req_svc_amr {
	public int st;
	public Req_handler req_handler;
	public Req_svc_amr() {
	}
	@PostConstruct
	public void init() {
		T.t("Req_svc_amr init","req_svc");
		req_handler=new Req_handler();
		st=1;
	}
	/*
	the Req is (json) serialized as the POST body
	*/
	@Post("/exec")
	@ProducesJson
	public Result exec(Req req) {
		T.t("Req_svc.req_exec req: "+req.toString(),"req_svc_exec");
		if(req_handler==null) {
			T.t("exec req_handler==null","req_svc_exec");
		}
		Result res=req_handler.req_exec(req);
		return res;
	}
}