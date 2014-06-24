/**
 * @author 魏兴波 
 * @version 创建时间：2012-8-28 上午10:49:10
 * 类说明
 */
package com.my.util;

import com.my.android.http.AsyncHttpClient;
import com.my.android.http.AsyncHttpResponseHandler;
import com.my.android.http.RequestParams;

/**
 * @author Administrator
 * 
 */
public class HttpConnection {
	/**
	 * 网络请求方式
	 */
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    public static AsyncHttpClient getHttpClient(){
    	return client;
    }
    public static void HttpClientGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
    	client.get(url, params, responseHandler);
    }
	
    public static void HttpClientGet(String url, AsyncHttpResponseHandler responseHandler){
    	client.get(url, responseHandler);
    }
    
    public static void HttpClientPost(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
    	client.post(url,params,responseHandler);
    }
    
    public static void HttpClientPost(String url, AsyncHttpResponseHandler responseHandler){
    	client.post(url,responseHandler);

    }
    
    public static void HttpClientPut(String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
    	client.put(url,params,responseHandler);
    }
    
    public static void HttpClientPut(String url, AsyncHttpResponseHandler responseHandler){
    	client.put(url, responseHandler);
    }
 
}
