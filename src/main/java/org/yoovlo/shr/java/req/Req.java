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
	app_id: this is identifying the entity that effectively models what has been implemented in various software as tenant,domain,workspace,app
		the most generic implementation of tenant is: an App in a parent (or root) Meta App
		what we traditionally think of as Tenant, is essentially App defined entirely at runtime in data: without any custom,compiled functionality
			whereas what we think of traditionally as an App is a tenant App in a Meta App, with the ability to execute custom code 
				compiled,scripted,defined in external services,etc
			they are however the same construct, with an App being the super Type by nature of having the ability to implement custom code
				a traditional tenant often lacks compiled functionality, entirely defined by runtime capabilities
				while an App is compiled and is a tenant of some data and compute structure
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