package org.yoovlo.shr.java;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/*
Requester
	util class with convenience statics
 */
public class Reqr {
	public static Reqr i;
	public CloseableHttpClient http;
	public PoolingHttpClientConnectionManager cm;
	public Reqr() {
		cm=new PoolingHttpClientConnectionManager();
		http=HttpClients.custom()
			.setConnectionManager(cm)
			.build();
	}
	public Object http(String url) {
		return http(url,"get","str");
	}
	public Object http(String url,String method,String return_type) {
		Result res=new Result();
		res.info_init();
		String response_str=null;
		HttpRequestBase request=null;
		switch(method) {
			case "get":
				request=new HttpGet(url);
				break;
		}
		try {
			HttpResponse response=http.execute(request);
			HttpEntity ent=response.getEntity();
			if(ent==null) {
				T.t("ent==null","reqr");
			}
			T.t(ent.toString());
			Header content_enc=ent.getContentEncoding();
			if(content_enc==null) {
				T.t("content_enc==null","reqr");
			}
			String content_enc_str=null;
			if(content_enc!=null) {
				content_enc_str=content_enc.getValue();
				T.t("content_enc_str:"+content_enc_str,"reqr");
			}
			response_str=Utils.is_to_str(ent.getContent(),content_enc_str);
		} catch(Exception e) {
			Utils.he(e);
			res.error_add("f","http request failed exception:"+e.toString());
			res.info_init();
			res.info.put("excp",e);
		}
		T.t("response_str:"+response_str,"reqr");
		switch(return_type) {
			case "str":
				if(!res.success) {
					return null;
				}
				return response_str;
			case "res":
				if(res.success) {
					res.info.put("res_str",response_str);
				}
				return res;
		}
		res.error_add("i","invalid return_type:"+return_type);
		return res;
	}
	/*
	todo
		ws: web socket
		amf: amf socket
		rs: rs socket
		meteor
		grpc
	 */
}
