package org.yoovlo.shr.java.events;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import org.yoovlo.shr.java.App_base;
import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.Utils;
import org.yoovlo.shr.java.Ser;
import org.yoovlo.shr.java.events.cdnc.Cdnc_mgr;
import org.yoovlo.shr.java.events.tmpl.Tmpl_mgr;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.yoovlo.shr.java.req.Req_props;
import org.springframework.boot.SpringApplication;
import org.yoovlo.shr.java.req.Req_info_mock;
import org.yoovlo.shr.java.Globals;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import java.util.concurrent.CompletableFuture;
import org.yoovlo.shr.java.req.Req_svc_amr;
import org.yoovlo.shr.java.req.Req_svc_spring;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:applicationContext.xml")
public class Consumer_app_base extends App_base {
	@Autowired
	public Cons_grp cons_grp;
	@Autowired
	public Prds_grp prds_grp;
	@Autowired
	public Cdnc_mgr cdnc_mgr;
	@Autowired
	public Tmpl_mgr tmpl_mgr;
	@Autowired
	public Req_props req_props;
	@Autowired
	public Req_svc_amr req_svc_amr;
	//public boolean do_spring_serv;
	public boolean do_kafka;
	public boolean do_cdnc;
	public boolean do_tmpl;
	public boolean do_amr;
	@Autowired
	public Consumer_app_base() {
		super();
		//do_spring_serv=true;
		do_kafka=true;
		do_cdnc=false;
		do_tmpl=false;
		do_amr=false;
	}
	public void run_t() {
		T.t("run_t");
		T.t("rs_url:"+req_props.rs_url);
		if(do_kafka) {
			cons_grp.run_t();
			prds_grp.run_t();
		}
		if(do_cdnc) {
			cdnc_mgr.run_t();
		}
		if(do_tmpl) {
			tmpl_mgr.run_t();
		}
		if(do_amr) {
			ServerBuilder sb=Server.builder();
			sb.annotatedService("/req",req_svc_amr);
			sb.http(1357);
			Server server=sb.build();
			CompletableFuture<Void> future=server.start();
			future.join();
		}
	}
	public void run_f() {
		T.t("run_f");
		cons_grp.run_f();
		prds_grp.run_f();
		cdnc_mgr.run_f();
		tmpl_mgr.run_f();
	}
}