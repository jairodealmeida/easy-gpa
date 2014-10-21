package br.com.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import br.com.core.services.http.HttpClientImpl;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtils {
	 // Set the timeout in milliseconds until a connection is established.
    // The default value is zero, that means the timeout is not used. 
    //public static final int timeoutConnection = 30000;
    // Set the default socket timeout (SO_TIMEOUT) 
    // in milliseconds which is the timeout for waiting for data.
    //public static final int timeoutSocket = 50000;
    
	public static String getLastVersion(String urlString){
	       try{
      	     StringBuilder builder = new StringBuilder(16384);
	    	 HttpGet httpGet = new HttpGet(urlString);
	    	 HttpParams httpParameters = new BasicHttpParams();
		     HttpConnectionParams.setConnectionTimeout(httpParameters, HttpClientImpl.TIMEOUT_CONNECTION);
		     HttpConnectionParams.setSoTimeout(httpParameters, HttpClientImpl.TIMEOUT_SOCKET);
	    	 DefaultHttpClient client = new DefaultHttpClient(httpParameters);
	    	    try {
	    	        HttpResponse execute = client.execute(httpGet);
	    	        InputStream content = execute.getEntity().getContent();
	    	        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	    	        String s = "";
	    	        while ((s = buffer.readLine()) != null) {
	    	            builder.append(s);
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	    return builder.toString();
	       } catch (Exception e){
	           e.printStackTrace();
	       }
	       return null;
	   }
	public static boolean isURLNotValid(String urlString){
	       try{
	           URL url = new URL(urlString);
	           HttpURLConnection urlConnection = (HttpURLConnection) url
	                   .openConnection();
	           int responseCode = urlConnection.getResponseCode();
	           urlConnection.disconnect();
	           return responseCode != 200;
	       } catch (MalformedURLException e){
	           e.printStackTrace();
	           return false;
	       } catch (IOException e){
	           e.printStackTrace();
	           return false;
	       }
	   }
	 public static boolean isConnectingToInternet(Activity activity){
	        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	 
	/*public static HttpClientImpl login() throws Exception{
	    	Communicator service = new UsuarioCommunicator();
	    	HttpClientImpl httpclient = service.login();
	    	br.com.mysus_mobile.util.Log.i("Sessão inicializada");
	    	return httpclient;
	}*/
	/*public static void logout(HttpClientImpl httpclient){
		try {
			Communicator service = new UsuarioCommunicator();
			service.logout(httpclient);
			if(httpclient.killSession()){
				br.com.mysus_mobile.util.Log.i("Sessão finalizada");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
