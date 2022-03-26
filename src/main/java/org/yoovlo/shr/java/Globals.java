package org.yoovlo.shr.java;
import java.util.HashMap;
import org.yoovlo.shr.java.Reqr;
import org.springframework.context.ConfigurableApplicationContext;
import org.yoovlo.shr.java.events.Prds_grp;
public class Globals extends Object_g {
	public static ConfigurableApplicationContext spring_ctx;
	public static Prds_grp prds;
	public static HashMap<String,Class> svcs_m;
	public static void init() {
		Reqr.i=new Reqr();
		svcs_m=new HashMap<String,Class>();
	}
	public static void beans_init() {
		prds=spring_ctx.getBean(Prds_grp.class);
	}
}