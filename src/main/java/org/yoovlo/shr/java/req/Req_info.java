package org.yoovlo.shr.java.req;
import org.yoovlo.shr.java.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.yoovlo.shr.java.T;
public class Req_info {
	public Req req;
	public Object res;
	public boolean r_do_complete;
	public String redir;
	public void redir_set(String v) {
		redir=v;
		Result r=null;
		if(res!=null) {
			r=(Result)res;
		} else {
			r=new Result();
			res=r;
		}
		r.info_init();
		r.info.put("redir",v);
	}
	public HttpServletRequest_w_utils servlet_req;
	public HttpServletResponse_w_utils servlet_res;
	public void Req_info() {
		r_do_complete=true;
	}
	public void r(Object b) {
		r(b,false);
	}
	public void r(Object b,boolean res_is_object) {
		if(res_is_object) {
			Res res=new Res();
			res.b=b;
			this.res=res;
		} else {
			res=b;
		}
		complete();
	}
	public void complete() {
		T.t("complete","req");
	}
}
