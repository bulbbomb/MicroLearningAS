package com.example.microlearning.proxy;

import com.example.microlearning.util.Proxy;

public class Request {
	private static final String SITE = "http://115.28.59.79/";
	private static final String WECAST_SVR_URL = "wecastsvr";

	public String sendReq() {
		
//		Proxy.doGet("http://115.28.59.79/wecastsvr?functioncode=1012");
		String params = "?functioncode=1012";
		try {
			return Proxy.doGet(SITE + WECAST_SVR_URL + params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
