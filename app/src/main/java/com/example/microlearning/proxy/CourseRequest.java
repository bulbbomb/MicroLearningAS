package com.example.microlearning.proxy;

import com.example.microlearning.struct.Response;
import com.example.microlearning.util.Proxy;
import com.google.gson.Gson;

/**
 * Created by SHI on 2014/5/10.
 */
public class CourseRequest {

    public static boolean getCourseInfo(String resource_id) {
        String resString = null;
        try {
            resString = Proxy.doGet("http://115.28.59.79/wecastsvr?functioncode=2002&resource_id=517e1608c3069b1ca44b1c6d");
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
