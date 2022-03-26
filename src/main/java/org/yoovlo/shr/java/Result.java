package org.yoovlo.shr.java;
import java.util.HashMap;
public class Result extends Object_g {
	public boolean success;
	public HashMap<String,Object> info;
	public HashMap<String,String> errors;
	public Result() {
		success=true;
	}
	public Result(boolean lsuccess,HashMap<String,Object> linfo) {
		success=lsuccess;
		info=linfo;
	}
	public Result(boolean lsuccess) {
		success=lsuccess;
	}
	public HashMap<String,Object> info_init() {
		if(info==null) {
			info=new HashMap<String,Object>();
		}
		return info;
	}
	public HashMap<String,String> errors_init() {
		if(errors==null) {
			errors=new HashMap<String,String>();
		}
		return errors;
	}
	public void error_add(String k,String msg) {
		success=false;
		errors_init();
		errors.put(k,msg);
	}
}