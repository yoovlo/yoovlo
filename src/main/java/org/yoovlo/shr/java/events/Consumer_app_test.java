package org.yoovlo.shr.java.events;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.Utils;
import org.yoovlo.shr.java.Ser;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.yoovlo.shr.java.req.Req_props;
import org.springframework.boot.SpringApplication;
import org.yoovlo.shr.java.req.Req_info_mock;
import org.yoovlo.shr.java.req.Req;
import org.yoovlo.shr.java.Globals;
import org.yoovlo.shr.java.Ox;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
public class Consumer_app_test extends Consumer_app_gen {
	public static void main(String[] args) throws IOException {
		T.t("Consumer_app_test main");
		//Utils.kill_port_holder(8080);
		Globals.init();
		ConfigurableApplicationContext context=SpringApplication.run(Consumer_app_test.class, args);
		Globals.spring_ctx=context;
		Globals.beans_init();
		Consumer_app_test app = context.getBean(Consumer_app_test.class);
		app.run_t();
		app.tests();
		T.t("Consumer_app_test main end");
	}
	public Consumer_app_test() {
		super();
	}
	public void tests() {
		Req req=new Req();
		req.s="Test";
		req.a="test";
		req.exec_type="jvm";
		Utils.task_crt(req);
		Ox p=new Ox();
		p.id=1L;
		Utils.req_wfi_crt(req,p,null);
	}
}