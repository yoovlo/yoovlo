package org.yoovlo.shr.java.req;
import org.springframework.stereotype.Component;
import org.yoovlo.shr.java.T;
import org.springframework.beans.factory.annotation.Value;
@Component
public class Req_props {
	@Value("${rs.url}")
	public String rs_url;
	public Req_props() {
	}
}