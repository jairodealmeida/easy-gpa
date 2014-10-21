package br.com.core.services.communicator;

import java.util.List;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import br.com.core.services.http.HttpClientImpl;
import br.com.core.session.Preferences;
import br.com.core.util.ChronometerUtil;
import br.com.core.util.Log;


public abstract class Communicator {
	//private static ConfigParameters configParameters;
	public static String url_services;
	public static String ACTION;
	public static ChronometerUtil chronometerUtil = new ChronometerUtil();
	
	static{
		initParameters();
	}
	
	

	public static void logout(HttpClientImpl httpclient) {
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, HttpClientImpl.TIMEOUT_CONNECTION);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, HttpClientImpl.TIMEOUT_SOCKET);
			httpclient.logout(httpclient,Communicator.url_services);
		} catch (Exception e) {
			Log.e("Falha ao tentar finalizar a sessão HTTP");
		}
	
	}
	/**
	 *Método que executa o serviço Rest do ServletController, 
	 *@param String - Nome do  commando, referente classe extende Command para reflexão
	 */
	//public abstract List<Entity> execute(HttpClientImpl http,String methodName) throws Exception;
	
	
	
	/**
	 *Método que executa o serviço Rest do ServletController, 
	 *@param String - Nome do  commando, referente classe extende Command para reflexão
	 *@param List<Usuario> elements - Lista de parametros se necessário para o serviço
	 */
	/*public abstract String uploadFoto(HttpClientImpl http, List<Foto> fotos) throws Exception ;
	public abstract List<Entity> execute(HttpClientImpl http,String methodName,String contentType) throws Exception ;
	public abstract List<Entity> execute(HttpClientImpl http,String methodName, List<Entity> elements) throws Exception;
	public abstract List<Entity> execute(HttpClientImpl http,String methodName, Entity entity) throws Exception;
	public abstract List<Entity> execute(HttpClientImpl http,String methodName, Collection requestCollection) throws Exception;*/
	
	public static void initParameters(){
		//url_services = "http://www.myzus.com.br/myzus-geo-connector-services";
		//url_services = "http://jairodealmeida.zapto.org:8080/myzus-geo-connector-services";
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://");
		urlBuilder.append(Preferences.getServiceUrl());
		if(Preferences.getPorta()!=null){
			urlBuilder.append(":");
			urlBuilder.append(Preferences.getPorta());
		}
		urlBuilder.append("/");
		urlBuilder.append(Preferences.getApplicationName());
		url_services = urlBuilder.toString();
		ACTION = "ServletController";
	}
	
}
