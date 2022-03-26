package org.yoovlo.shr.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Ser {
	public static ObjectMapper mpr;
	static {
		mpr=new ObjectMapper();
		mpr.setSerializationInclusion(Include.NON_NULL);
	}
	public static String sSer(Object o) {
		return sSer(o,"json");
	}
	/*
	string serialize
	*/
	public static String sSer(Object o,String type) {
		switch(type) {
			case "json":
				try {
				String res=mpr.writeValueAsString(o);
				return res;
				} catch(Exception e) {
					Utils.he(e);
				}
		}
		return null;
	}
	/*
	string unserialize
	*/
	public static <T> T sUnser(String data,String type,Class<T> klass) {
		switch(type) {
			case "json":
				try {
				T o=mpr.readValue(data, klass);
				return o;
				} catch(Exception e) {
					Utils.he(e);
				}
		}
		return null;
	}
}
