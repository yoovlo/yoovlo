package org.yoovlo.shr.java.req;
import java.util.HashMap;
import org.yoovlo.shr.java.req.Req_info;
import org.yoovlo.shr.java.T;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import org.yoovlo.shr.java.Result;
import org.yoovlo.shr.java.Globals;
@Component
public class Test_svc {
	public int st;
	public void test(HashMap<String,Object> b,Req_info req_info) {
		T.t("test","test");
		req_info.r(new Result());
	}
	@PostConstruct
	public void init() {
		T.t("Test_svc init","test");
		Globals.svcs_m.put("Test",Test_svc.class);
		st=1;
	}
}