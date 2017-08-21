package com.comutils.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPOutputStream;


public class HttpUtil {
	//通过URL获得HTTPPOST对象
	public static HttpPost getHttpPost(String url){
		return new HttpPost(url);
	}
	//通过URL获得HTTPGET对象
	public static HttpGet getHttpGet(String url){
		return new HttpGet(url);
		 
    }
	//通过URL获得HTTPGET对象
	public static HttpHead getHttpHead(String url){
		return new HttpHead(url);
	}
	//通过HTTPPOST获得HTTPRESPONSE对象
	public static HttpResponse getHttpResponse(HttpHead request) throws ClientProtocolException,IOException{
			HttpResponse response = new DefaultHttpClient().execute(request);
			return response;
	}
	//通过HTTPPOST获得HTTPRESPONSE对象
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	//通过HTTPPOST获得HTTPRESPONSE对象
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	//通过URL发送POST请求，返回请求结果
	public static String queryStringForPost(String actUrl,List<NameValuePair> params){
		StringBuilder url = new StringBuilder();
		url.append(actUrl);
		HttpPost request = HttpUtil.getHttpPost(url.toString());
		StringBuilder result = new StringBuilder();
		try{
			//参数封装
			request.setEntity(new UrlEncodedFormEntity(params,org.apache.http.protocol.HTTP.UTF_8));
			////Log.i("", "tag sss=req="+request.getURI().toString());
			////Log.i("", "tag sss=req="+request.getEntity().toString());
			
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity()));
			}else{
				result.append("601");
				Log.i("", "tag ssoo net ===="+EntityUtils.toString(response.getEntity()));
				//result.append(EntityUtils.toString(response.getEntity()));
			}
		}catch(ClientProtocolException e){
			Log.i("", "tag ssoo net ===="+result.toString());
			result.append("601");
		}catch(IOException e){
			result.append("601");
			Log.i("", "tag ssoo net ===="+result.toString());
		}
		//Log.i("", "tag sssososos===="+result.toString());
		return result.toString();
	}
	//通过URL发送POST请求，返回请求结果
	public static String queryStringForPost(String actUrl,JSONObject paramsobj){
		StringBuilder url = new StringBuilder();
		url.append(actUrl);
		HttpPost request = HttpUtil.getHttpPost(url.toString());
		StringBuilder result = new StringBuilder();
		try{
			request.addHeader("device", "X11; Linux ppc");
			request.addHeader("Content-Type", "application/json");
			//参数封装
			//request.setEntity(new StringEntity(paramsobj.toString(),HTTP.UTF_8));   
			//request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			////Log.i("", "tag sss=req="+request.getURI().toString());
			////Log.i("", "tag sss=req="+request.getEntity().toString());
			
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity()));
			}else{
				result.append("601");
				Log.i("", "tag ssoo net ===="+EntityUtils.toString(response.getEntity()));
				//result.append(EntityUtils.toString(response.getEntity()));
			}
		}catch(ClientProtocolException e){
			Log.i("", "tag ssoo2 net ===="+result.toString());
			result.append("601");
		}catch(IOException e){
			result.append("601");
			Log.i("", "tag ssoo2 net ===="+result.toString());
		}
		//Log.i("", "tag sssososos===="+result.toString());
		return result.toString();
	}


	public static String queryStringForPost4(String actUrl,JSONObject params){
		Log.i("", "tag regsspass=code =data=");//?param=helloMK
		 //String strUrl = "http://61.164.59.230:8087/wxtapp/handle";
	        URL url = null; String result = "";
	        try{
	        	
	            url = new URL(actUrl);
	            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
	            urlConn.setConnectTimeout(5000);
	            urlConn.setDoInput(true);
	            urlConn.setDoOutput(true);
	            urlConn.setUseCaches(false);
	            urlConn.setRequestMethod("POST");
	            urlConn.setRequestProperty("Charset", "utf-8");
	            urlConn.setRequestProperty("connection", "keep-alive");
	            urlConn.setRequestProperty("Content-Type", "multipart/form-data");
	            urlConn.setRequestProperty("content-encoding", "gzip");
	            
	            /*把JSON数据转换成String类型使用输出流向服务器写*/
				String content = String.valueOf(params);
				Log.i("", "tag regsspass=code =data25s2="+content);
				GZIPOutputStream gos = new GZIPOutputStream(urlConn.getOutputStream());
				//OutputStream os = urlConn.getOutputStream();
				gos.write(content.getBytes());
				/* 将数据发送出去并且清空数据输出流  */
				gos.flush();
				/* 关闭输出流并且释放占用的资源空间  */
				gos.close();
				 
	            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
	            BufferedReader bufferedReader = new BufferedReader(in);
	           
	            String readline = null;
	            while((readline = bufferedReader.readLine())!=null)
	            {
	                result += readline;
	            }
	            in.close();
	            urlConn.disconnect();
	           // textView.setText(result);
	            }catch(Exception e){
	               // textView.setText("连接超时");
	            	Log.i("", "tag regsspass=code =data= 连接超时"+e.getMessage());
	            	return "601";
	            }
		
	        Log.i("", "tag regsspass=code =data23="+result);
	        return result;
	}
	public static String queryStringForPost5(String actUrl,JSONObject params){
		HttpHead request = HttpUtil.getHttpHead(actUrl.toString());
		StringBuilder result = new StringBuilder();
		try{
			//request.setParams();
			//HttpParams params2 ;
			//params2..addHeader("name", "value");
			//params2.addQueryStringParameter("name", "value");

			// 只包含字符串参数时默认使用BodyParamsEntity，
			// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
			//params2.addBodyParameter("name", "value");
			
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity(),org.apache.http.protocol.HTTP.UTF_8));
			}else{
				result.append("601");
			}
		}catch(ClientProtocolException e){
			result.append("601");
		}catch(IOException e){
			result.append("601");
		}
		return result.toString();
		
	}
	public static String queryStringForGet(String action){
		StringBuilder url = new StringBuilder();
		url.append(action);
		HttpGet request = HttpUtil.getHttpGet(url.toString());
		StringBuilder result = new StringBuilder();
		try{
			
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result.append(EntityUtils.toString(response.getEntity(),org.apache.http.protocol.HTTP.UTF_8));
			}else{
				result.append("601");
			}
		}catch(ClientProtocolException e){
			result.append("601");
		}catch(IOException e){
			result.append("601");
		}
		return result.toString();
	}
}
