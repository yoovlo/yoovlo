package org.yoovlo.shr.java.req;
import java.util.List;
import org.yoovlo.shr.java.Result;
import javax.annotation.PostConstruct;
import org.yoovlo.shr.java.T;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("req")
public class Req_svc_spring {
	public int st;
	public Req_handler req_handler;
	public Req_svc_spring() {
	}
	@PostConstruct
	public void init() {
		T.t("Req_svc_spring init","req_svc");
		req_handler=new Req_handler();
		st=1;
	}
	@RequestMapping(value="/exec",produces="application/json",method={RequestMethod.POST,RequestMethod.GET})
	public Result exec(@RequestBody Req req,
		HttpServletRequest request,
		HttpServletResponse response) {
		T.t("Req_svc_spring.req_exec req: "+req.toString(),"req_svc_exec");
		if(req_handler==null) {
			T.t("exec req_handler==null","req_svc_exec");
		}
		HttpServletRequest_w_utils request_w_utils=new HttpServletRequest_w_utils(request);
		HttpServletResponse_w_utils response_w_utils=new HttpServletResponse_w_utils(response);
		Result res=req_handler.req_exec(req,request_w_utils,response_w_utils);
		return res;
	}
}