package com.example.microlearning.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class Proxy {
     private static final String TAG = "Proxy";
    
     public final static int RETRY_COUNT = 3;
    public final static int MAX_TOTAL_CONNECTIONS = 5;
    public final static int MAX_ROUTE_CONNECTIONS = 3;
    public final static int WAIT_TIMEOUT = 25000;
    public final static int CONNECT_TIMEOUT = 20000;
    public final static int READ_TIMEOUT = 30000;
    public final static int READ_TIMEOUT_FOR_UPLOAD_RECORD = 60000;
    
     private static HttpClient client;
    
     public static synchronized HttpClient getHttpClient() {
          if (client == null) {
               HttpParams params = new BasicHttpParams();
               HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
               HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
               HttpProtocolParams.setUseExpectContinue(params, true);
//               HttpProtocolParams.setUserAgent(params, useragent);
              
               // Set connection
               ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTIONS);
             ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
             ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
            
               // Set timeout
             ConnManagerParams.setTimeout(params, WAIT_TIMEOUT); // ConnectionPoolTimeout
             HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT); // ConnectionTimeout
             HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT); // SocketTimeout
              
             // Set HTTP / HTTPS mode
             SchemeRegistry registry = new SchemeRegistry();
             registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
             registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            
             ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params, registry);
             client = new DefaultHttpClient(connMgr, params);
          }
          return client;
     }
    
     public static String doGet(String url) throws Exception
     {
          HttpClient mHttpClient = null;
          HttpGet request = null;
          HttpResponse response = null;
         
          request = new HttpGet(url);
          mHttpClient = getHttpClient();
          try {
               response = mHttpClient.execute(request);
              
               if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new Exception("incorrect response");
               }
              
               HttpEntity resEntity = response.getEntity();
               return (resEntity == null)?null:EntityUtils.toString(resEntity, HTTP.UTF_8);
              
          } catch (ClientProtocolException | ParseException e) {
               Log.e(TAG, e.getMessage());
               return null;
          } catch (IOException e) {
               throw new Exception("connection failure", e);
          }
         
     }
    
     public static String doPost(String url, NameValuePair ...params) throws Exception
     {
          List<NameValuePair> formParams = new ArrayList<NameValuePair>();
          for (NameValuePair p : params) {
               formParams.add(p);
          }
          try {
               UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, HTTP.UTF_8);
              
               HttpPost request = new HttpPost(url);
               request.setEntity(entity);
              
               HttpClient client = getHttpClient();
               HttpResponse response = client.execute(request);
               if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new Exception("incorrect response");
               }
              
               HttpEntity resEntity = response.getEntity();
               return (resEntity == null)?null:EntityUtils.toString(resEntity, HTTP.UTF_8);
              
          } catch (UnsupportedEncodingException | ClientProtocolException e) {
               Log.e(TAG, e.getMessage());
               return null;
          } catch (IOException e) {
               throw new Exception("connection failure", e);
          }
         
     }
     
     public static String doGet(String url, String[] keys, String[] values) throws Exception {
    	 StringBuilder urlFull = new StringBuilder(url);
    	 
    	 for (int i = 0; i < keys.length; i ++) {
    		 urlFull.append((i==0)?'?':'&');
    		 urlFull.append(keys[i]).append('=').append(values[i]);
    		 
    	 }
    	 
    	 return doGet(urlFull.toString());
     }
     
     public static void main(String[] args) {
          final String url = "http://115.28.59.79/wecastsvr?functioncode=2003&resource_id=5248ccbe5f94ed7f1219cbf4";
         
         
          String resStr = null;
          try {
               resStr = doGet(url);
          } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
          }
          Log.i("TAG", resStr);
         
     }
    
}