package org.yoovlo.shr.java;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import org.yoovlo.shr.java.req.Req;
import java.util.HashMap;
import org.yoovlo.shr.java.Ser;
import org.yoovlo.shr.java.Ox;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.util.Base64;
import java.security.spec.PKCS8EncodedKeySpec;
public class Utils {
	public static X509Certificate cert_x509_from_str(String certificate) throws Exception {
		InputStream targetStream = new ByteArrayInputStream(certificate.getBytes());
		return (X509Certificate) CertificateFactory
			.getInstance("X509")
			.generateCertificate(targetStream);
	}
	public static PrivateKey rsa_prv_key_from_str(String key_s) {
		try {
			PrivateKey key;
			StringBuilder sb=new StringBuilder();
			BufferedReader rdr=new BufferedReader(new StringReader(key_s));
			String line;
			while((line=rdr.readLine())!=null) {
				sb.append(line);
			}
			key_s=sb.toString();
			key_s=key_s.replace("-----BEGIN PRIVATE KEY-----", "");
			key_s=key_s.replace("-----END PRIVATE KEY-----", "");
			key_s=key_s.replaceAll("\\s+","");
			byte [] bs=Base64.getDecoder().decode(key_s);
			PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(bs);
			KeyFactory kf=KeyFactory.getInstance("RSA");
			key=kf.generatePrivate(spec);
			return key;
		} catch(Exception e) {
			Utils.he(e);
		}
		return null;
	}
	public static void kill_port_holder(int port) {
		final String how = "-KILL";
		try {
			Runtime.getRuntime().exec(new String[]{"sh", "-c", "lsof -i :" + port + " -t | xargs kill " + how});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	return yoovlo node id
		implement as required
	*/
	public static String node_id() {
		return "n";
	}
	/*
	handle exception
	*/
	public static void he(Exception e) {
		T.t(e.toString());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		T.t(exceptionAsString);
	}
	/*
	input stream to string
	*/
	public static String is_to_str(InputStream is,String enc) {
		if(enc==null) {
			enc="UTF-8";
		}
		String r=null;
		try {
			r=IOUtils.toString(is,enc);
		} catch(Exception e) {
			he(e);
		}
		return r;
	}
	//url encode
	public static String ue(String v) {
		try {
			return URLEncoder.encode(v, StandardCharsets.UTF_8.toString());
		} catch(Exception e) {
			he(e);
			return null;
		}
	}
	public static Result task_crt(Req req) {
		Globals.prds.send("req",req);
		return new Result();
	}
	/*
	workflow instance run false
	*/
	public static Result wfi_run_f(Ox p,boolean p_do_save) {
		Result res=null;
		Req req=new Req();
		req.exec_type="jvm";
		req.s="Tmpl";
		req.a="wfi_op";
		String wfp_hash=null;
		wfp_hash=p.type2;
		if(wfp_hash==null) {
			wfp_hash="req";
		}
		req.b_init();
		HashMap<String,Object> b=req.b;
		b.put("op","run_f");
		b.put("wfp_hash",wfp_hash);
		b.put("p",Long.toString(p.id));
		T.t(req.toString());
		res=task_crt(req);
		return res;
	}
	/*
	req workflow instance create
		create a workflow that executes a req synchronously, generally of exec_type:rs
	*/
	public static Result req_wfi_crt(Req wf_req,Ox p,String cron_sch) {
		Req req=new Req();
		req.exec_type="jvm";
		req.s="Tmpl";
		req.a="wfi_op";
		String wfp_hash=null;
		wfp_hash="req";
		req.b_init();
		HashMap<String,Object> b=req.b;
		b.put("op","crt");
		b.put("wfp_hash",wfp_hash);
		b.put("wfi_id","p"+Long.toString(p.id));
		b.put("req",Ser.sSer(wf_req,"json"));
		if(cron_sch!=null) {
			b.put("cron_sch",cron_sch);
		}
		Result task_res=task_crt(req);
		return task_res;
	}
	/*
	implement as necessary depending on your preferred orm
	*/
	public static void prop_set(Object o,String p,Object v) {
		
	}
	/*
	utility function to get an array from string
		comma delimited
	*/
	public static String[] ar_from_str_basic(String s) {
		String[] is=s.split(",");
		String[] r=new String[is.length];
		int a=0;
		while(a<is.length) {
			String i=is[a];
			String[] ip=i.split("=");
			String v=ip[1];
			r[a]=v;
			//T.t("v:"+v);
			++a;
		}
		return r;
	}
}
