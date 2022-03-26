package org.yoovlo.shr.java.req;

import java.util.HashMap;
import org.yoovlo.shr.java.Object_g;
import org.yoovlo.shr.java.Ser;
public class Req {
	public String id;
	public String sid;
	public String exec_type;
	/*
	for binaries
		the path is the binary
	*/
	public String proto;
	public String ser;
	public String net_proto;
	public String comp;
	public String path;
	/*
	the url on which the req was started from in the ingress service
	*/
	public String in_url;
	/* service */
	public String s;
	/* action */
	public String a;
	/*
	*/
	public int app_id;
	/* body */
	public HashMap<String,Object> b;
	public String cred_type;
	public String cred;
	public String method;
	public HashMap<String,Object> headers;
	public String user_agent;
	public void Req() {

	}
	public void b_init() {
		b=new HashMap<String,Object>();
	}
	@Override 
	public String toString() {
		return Ser.sSer(this);
	}
}