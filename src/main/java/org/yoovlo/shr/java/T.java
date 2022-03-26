package org.yoovlo.shr.java;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class T {
	public static Logger l=LoggerFactory.getLogger("gen");
	public static String[] tags= {
		"events_con",
		"events_prd",
		"cdnc",
		"tmpl",
		"req",
		"req_svc",
		"req_handler",
		"req_svc_exec",
		"reqr",
		"req_wf",
		"email_send",
		"email_send_err",
		"saml",
		"saml_err",
		"aws",
		"aws_err",
		//"id_svc",
		"adir_svc",
		"ex_svc"
	};
	public static void t(String s) {
		t(s,null);
	}
	public static void t(String s,String tag) {
		if(tag==null || ArrayUtils.contains(tags,tag)) {
			l.trace(s+" call:"+get_call_pos_info_str());
		}
	}
	public static String get_call_pos_info_str()
	{
		StackTraceElement[] els=Thread.currentThread().getStackTrace();
		//l.trace("els.len "+els.length);
		int el_i=-1;
		for(int a=0;a<els.length;++a) {
			StackTraceElement ael=els[a];
			//System.out.println("ael methodName: "+ael.getMethodName());
			if (ael.getMethodName().equals("get_call_pos_info_str")) {
				el_i=a+3;
				break;
			}
		}
		StackTraceElement el=els[el_i];
		int line=el.getLineNumber();
		String class_name=el.getClassName().substring(19);
		String method_name=el.getMethodName();
		String s=class_name+"."+method_name+":"+line;
		return s;
	}
}
