package com.example.microlearning.proxy;

import com.example.microlearning.struct.Response;
import com.example.microlearning.util.Proxy;
import com.google.gson.Gson;
import com.google.gson.JsonArray;



public class LoginRequest {
	
	public static boolean login(String mEmail, String mPassword) {
		String resString = null;
		try {
//			resString = Proxy.doGet("http://115.28.59.79/wecastsvr?functioncode=1001&login_pwd=shi123&login_name=testshi");
			resString = Proxy.doGet("http://115.28.59.79/wecastsvr?functioncode=1001&login_pwd=" + mPassword + "&login_name=" + mEmail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		Response response = gson.fromJson(resString, Response.class);
		
		
		
		if ("000000".equals(response.returncode)) return true;
		else return false;
	}
	
	public static boolean logout() {
        String resString = null;
        try {
            resString = Proxy.doGet("http://115.28.59.79/wecastsvr?functioncode=1012");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Response response = gson.fromJson(resString, Response.class);

        if ("000000".equals(response.returncode)) return true;
        else return false;
    }
}
