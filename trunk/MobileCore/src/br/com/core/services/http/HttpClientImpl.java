package br.com.core.services.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import br.com.core.services.communicator.Communicator;
import br.com.core.session.Preferences;
import br.com.core.util.Log;

/**
 * @class HttpClientImpl
 * Classe para a conexï¿½o da aplicaï¿½ï¿½o android com o sistema legado
 * Objetivo : garantir a sincronizaï¿½ï¿½o dos dados com o aplicativo mï¿½vel
 * Requisitos : o serviï¿½o chamado deve existir no sistema legado e o xml gerado deve ser 
 * igual ao xml esperado pelas classes tradutiras de xml (Translators)
 * @author jairodealmeida@gmail.com
 * @since 11/06/2012
 */
public class HttpClientImpl extends DefaultHttpClient{
	
	private static HttpClientImpl httpclient;
	public static final int TIMEOUT_CONNECTION = 150000;
	public static final int TIMEOUT_SOCKET = 150000;
	/**
	 * contrutor private para nï¿½o permitir ser instanciado
	 * por outro artefato, instancia unica necessï¿½ria
	 */
	@Deprecated //usar HttpClientImpl(HttpParams httpParameters)
	private HttpClientImpl(){
		super();
	}
	public HttpClientImpl(HttpParams httpParameters){
		super(httpParameters);
	}
	
	public static boolean killSession(){
		if(httpclient!=null){
			httpclient = null;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * singleton para garantir unica instancia do objeto HttpClientImpl
	 * para manter a sessï¿½o ativa, uma vez logado, estarï¿½ sempre logado
	 * @return HttpClientImpl - instancia ï¿½nica do objeto de conexï¿½o http
	 */
	public static HttpClientImpl getInstance(String url, String login, String senha) throws Exception{
		//if(httpclient==null){
			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
			httpclient = new HttpClientImpl(httpParameters);
			//if(login(Communicator.url_services,login,senha)!=null){
				Log.i("Login efetuado com sucesso");
				return httpclient; 
			//}else{
			//	Log.e( "Falha na autenticação do login");
			//	return null;
			//}
		//}else{
		//	return httpclient;
		//}
	}
	public static HttpClientImpl getInstance(String login, String senha) throws Exception{
		//if(httpclient==null){
			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
			httpclient = new HttpClientImpl(httpParameters);
			
			//if(login(Communicator.url_services, login, senha)!=null){
				Log.i("Login efetuado com sucesso");
				return httpclient; 
			//}else{
			//	Log.e( "Falha na autenticação do login");
			//	return null;
			//}
		//}else{
		//	return httpclient;
		//}
	}
	/*public static HttpClientImpl getInstanceFromLogin() throws Exception{
		//if(httpclient==null){
			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
			httpclient = new HttpClientImpl(httpParameters);		
			//return httpclient; 
			
			Usuario usuario = EntrarSessaoBackgroundTask.getLogin();
			
			if(usuario!=null && login(Communicator.url_services, usuario.getUsuariologin(), usuario.getUsuariosenha())!=null){
				Log.i("Login efetuado com sucesso");
				return httpclient; 
			}else{
				Log.e( "Falha na autenticação do login");
				return null;
			}
			
		//}else{
		//	return httpclient;
		//}
	}*/
	public static HttpClientImpl getInstance() throws Exception{
		//if(httpclient==null){
			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
			httpclient = new HttpClientImpl(httpParameters);		
			//return httpclient; 
			
			//Usuario usuario = EntrarSessaoBackgroundTask.getLogin();
			
			//if(usuario!=null && login(Communicator.url_services, usuario.getUsuariologin(), usuario.getUsuariosenha())!=null){
				Log.i("Login efetuado com sucesso");
				return httpclient; 
			//}else{
			//	Log.e( "Falha na autenticação do login");
			//	return null;
			//}
			
		//}else{
		//	return httpclient;
		//}
	}
	/*public static Usuario login(String url,String login, String senha) throws Exception{
		//if(EntrarSessaoBackgroundTask.getLogin()!=null){
			//inicializando tradutor xml > pojo || pojo > xml
			UsuarioTranslate translator = new UsuarioTranslate();
			//conectando ao sistema legado 
			HttpClientImpl http = HttpClientImpl.getInstance();
			Map<String,String> map = new HashMap<String,String>();
			//inicializando parametros para o serviço REST
			map.put("command","br.com.geoconnector.service.command.UsuarioCommand");
			map.put("method", "loginMobile");
			//se houver parametros, converter para xml 
			//e enviar como parametro para o serviço REST
			Usuario usuarioLogado = EntrarSessaoBackgroundTask.getLogin();
			if(usuarioLogado == null){
				usuarioLogado = new Usuario(login, senha);
			}
			String requestXml =  translator.parseBeanToJSON( usuarioLogado );
			map.put("xml", requestXml.toString());
			
			//executando o serviço REST
			InputStream response = http.doPost(
					url + "/" + Communicator.ACTION, map, 
					"application/x-www-form-urlencoded", 
					"iso-8859-1");
			//interceptando response do serviço
			//readString(response);
			Usuario usuarioVerificado = translator.parseJSONToBean(response);
			return usuarioVerificado;
		//}else{
		//	return null;
		//}

	}*/
	/*public static InputStream upload(String url) throws Exception{
			//inicializando tradutor xml > pojo || pojo > xml
			HttpClientImpl http = HttpClientImpl.getInstance();
			Map<String,String> map = new HashMap<String,String>();
			//inicializando parametros para o serviço REST
			map.put("command","br.com.geoconnector.service.command.MovimentarApontamentoItemCommand");
			map.put("method", "uploadImage");
			//se houver parametros, converter para xml 
			//e enviar como parametro para o serviço REST
			Usuario usuarioLogado = EntrarSessaoBackgroundTask.getLogin();
			InputStream response = http.doPost(
					url + "/" + Communicator.ACTION, map, 
					"application/x-www-form-urlencoded", 
					"iso-8859-1");
			Log.i(response.toString());
			return response;

	}*/
	public static void logout(HttpClientImpl http,String url) throws Exception{
		//UsuarioTranslate translator = new UsuarioTranslate();
		Map<String,String> map = new HashMap<String,String>();
		//inicializando parametros para o serviço REST
		map.put("command","br.com.geoconnector.service.command.UsuarioCommand");
		map.put("method", "logout");
		
		//executando o serviço REST
		InputStream response = http.doPost(
				url + "/" + Communicator.ACTION, map, 
				"application/x-www-form-urlencoded", 
				"iso-8859-1");
		Log.i(response.toString());
	}
	public static void logout(String url) throws Exception{
		//if(EntrarSessaoBackgroundTask.getLogin()!=null){
			//inicializando tradutor xml > pojo || pojo > xml
			//UsuarioTranslate translator = new UsuarioTranslate();
			//conectando ao sistema legado 
			HttpClientImpl http = HttpClientImpl.getInstance();
			Map<String,String> map = new HashMap<String,String>();
			//inicializando parametros para o serviço REST
			map.put("command","br.com.geoconnector.service.command.UsuarioCommand");
			map.put("method", "logout");
			
			//executando o serviço REST
			InputStream response = http.doPost(
					url + "/" + Communicator.ACTION, map, 
					"application/x-www-form-urlencoded", 
					"iso-8859-1");
			Log.i(response.toString());
			
			//interceptando response do serviço
			
			//Usuario usuarioVerificado = translator.parseXMLToBean(response);
			//return usuarioVerificado;
		//}else{
		//	return null;
		//}

	}
	/*public Object doPost(String url, File fileUpload){
		FotoTranslate translator = new FotoTranslate();
		HttpEntity resEntity = null;
		try {
		    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		    HttpPost httpPost = new HttpPost(url);

		    MultipartEntity mpEntity = new MultipartEntity();
		    ContentBody cbFile = new FileBody(fileUpload, "image/jpeg");
		    mpEntity.addPart("userfile", cbFile);
		    //mpEntity.addPart("json", new StringBody(translator.parseBeanToJSON(foto)));

		    httpPost.setEntity(mpEntity);
		    System.out.println("executing request " + httpPost.getRequestLine());
		    HttpResponse response = httpclient.execute(httpPost);
		    resEntity = response.getEntity();

		    System.out.println(response.getStatusLine());
		    if (resEntity != null) {
		      return EntityUtils.toString(resEntity);
		    }
		} catch (Exception e) {
			 Log.e(  e.getMessage() ,e);
		}finally{
			if (resEntity != null) {
				try {
					resEntity.consumeContent();
				} catch (IOException e) {
					 Log.e(  e.getMessage() ,e);
				}
			}
		}
		return null;
	}*/
	
	/*public Object doPostImage(String url, Foto foto){
		FotoTranslate translator = new FotoTranslate();
		try {
		    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		    HttpPost httpPost = new HttpPost(url);
		    String i_file = foto.getCaminho();
		    File file = new File(i_file);

		    MultipartEntity mpEntity = new MultipartEntity();
		    ContentBody cbFile = new FileBody(file, "image/jpeg");
		    mpEntity.addPart("userfile", cbFile);
		    mpEntity.addPart("json", new StringBody(translator.parseBeanToJSON(foto)));

		    httpPost.setEntity(mpEntity);
		    System.out.println("executing request " + httpPost.getRequestLine());
		    HttpResponse response = httpclient.execute(httpPost);
		    HttpEntity resEntity = response.getEntity();

		    System.out.println(response.getStatusLine());
		    if (resEntity != null) {
		      System.out.println(EntityUtils.toString(resEntity));
		    }
		    if (resEntity != null) {
		      resEntity.consumeContent();
		    }

		   
		    
		} catch (Exception e) {
			 Log.e(  "!!! IOException " + e.getMessage() ,e);
		}
		return null;
	}
	*/
	
	
	public Object doPostObject(String url,
			Map<String, String> map, Object requestCollection) {
		
		 ObjectOutput out;
		try {
			HttpPost post = new HttpPost(url);
		    //Serialisation of object
		    ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		       out = new ObjectOutputStream(bos) ;
		       out.writeObject(requestCollection);
		       //puts bytes into object which is the body of the http request
		       post.setHeader(new BasicHeader("Content-Length", "" + bos.toByteArray().length));
		       ByteArrayEntity barr = new ByteArrayEntity(bos.toByteArray()); 
		       //sets the body of the request 
		       post.setEntity(barr);
		       out.close();
		       //executes request and returns a response
		       HttpResponse response = httpclient.execute(post);
		       return response.getEntity().getContent();
		  } catch (IOException e) {
		        Log.e(  "!!! IOException " + e.getMessage() ,e);
		  }
return null;
			
			
			/*
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type","application/x-java-serialized-object");
			httpPost.setHeader("Accept-Charset","iso-8859-1");
			List<NameValuePair> parameters = getParams(map);
			httpPost.setEntity(new UrlEncodedFormEntity(parameters,"iso-8859-1"));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				
				InputStream in = entity.getContent();
				//String texto = EntityUtils.toString( entity, HTTP.ISO_8859_1 );
				//InputStream in = new ByteArrayInputStream(texto.getBytes());
				if(Preferences.isDebugDataTransfer()){
					Log.i("");
				}
				//String texto = readString(in);
				return in;
			}else{
				return null;
			}*/
			
			
/*		
  try {	
  			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			//urlRequest.append(action);
			urlRequest.append("?command=" + map.get("command"));
			urlRequest.append("&method=" + map.get("method")); 
			//asdfasdf
			//URL urlParam = new URL(urlRequest.toString());
	        //HttpURLConnection http = (HttpURLConnection) urlParam.openConnection();
			httpclient.setRequestMethod("POST");
			httpclient.addRequestProperty("content-type", "application/x-java-serialized-object");  
			httpclient.setDoInput(true);
			httpclient.setDoOutput(true);
			httpclient.setConnectTimeout(20000);

			httpclient.connect();
	        //abre canal de saída
	        ObjectOutputStream oos = new ObjectOutputStream(http.getOutputStream());
	        //envia objeto
	        oos.writeObject(requestCollection);

	        //abre canal de leitura
	        ObjectInputStream ois = new ObjectInputStream(http.getInputStream());
	        //recebe objeto
	        Object responseCollection = (Object) ois.readObject();
	        //imprime log
	        //logger.info(TAG, "RETORNO:> " + obTeste.toString());
			return responseCollection;
		} catch (Exception e) {
			Log.e(e.getLocalizedMessage(),e);
		}
		return null;*/

	}
	/**
	 * methodo doPost para as transaï¿½ï¿½es de cliente http
	 * example Content-Type: application/x-www-form-urlencoded; charset=ISO-8859-1
	 * @param url - url do serviï¿½o que estï¿½ sendo requisitado
	 * @param map - parametros a serem enviados na requisiï¿½ï¿½o
	 */
	public InputStream doPost(String url, Map<String, String> map, String contentType, String charset) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		if(contentType!=null){
			httpPost.setHeader("Content-Type",contentType);
		}
		if(charset!=null){
			httpPost.setHeader("Accept-Charset",charset);
			List<NameValuePair> parameters = getParams(map);
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, charset));
		}
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		
		if(entity != null){
			
			//InputStream in = entity.getContent();
			String texto = EntityUtils.toString( entity, HTTP.ISO_8859_1 );
			InputStream in = new ByteArrayInputStream(texto.getBytes());
			if(Preferences.isDebugDataTransfer()){
				Log.i(texto);
			}
			//String texto = readString(in);
			return in;
		}else{
			return null;
		}
	}
	/**
	 * methodo doPost para as transaï¿½ï¿½es de cliente http
	 * O encoding type do formulï¿½rio deve ser: multipart/form-data, e a requisiï¿½ï¿½o POST
	 * @param url - url do serviï¿½o que estï¿½ sendo requisitado
	 * @param map - parametros a serem enviados na requisiï¿½ï¿½o
	 */
	public String doPost(String url, MultipartEntity map) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(map);
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if(entity != null){
			InputStream in = entity.getContent();
			String texto = readString(in);
			return texto;
		}else{
			return null;
		}
	}
	public String doGet(String url, String action, String method, Map<String, String> parameters){
		try {
			//preparando url para conexï¿½o com sistema legado
			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			urlRequest.append(action);
			urlRequest.append("?method=");
			urlRequest.append(method); 
			if(parameters!=null && parameters.size()>0){
				    for (Map.Entry<String, String> entry : parameters.entrySet()) {
			        	urlRequest.append("&");
						urlRequest.append( entry.getKey());
						urlRequest.append("=");
						urlRequest.append( URLEncoder.encode(  entry.getValue() ) );
			        }
			}
			//executando request via do get
			HttpGet httpGet = new HttpGet(urlRequest.toString());
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String texto = EntityUtils.toString(entity, HTTP.ISO_8859_1);
				return texto;
			}
		} catch (SocketTimeoutException  e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		catch (Exception e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		return null;
	}
	public String doGet(String url,String appName,Map<String, String> map){
		try {
			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			if(appName!=null){
				urlRequest.append(appName);
			}
			urlRequest.append("?");
			if(map!=null && map.size()>0){
				Iterator e = (Iterator) map.keySet().iterator();
				while(e.hasNext()){
					String name = (String) e.next();
					Object value = map.get(name);
					urlRequest.append( name );
					urlRequest.append("=");
					urlRequest.append( URLEncoder.encode(  value.toString() ) );
					if(e.hasNext()){
						urlRequest.append("&");
					}
				}
			}
			HttpGet httpGet = new HttpGet(urlRequest.toString());
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String texto = EntityUtils.toString(entity, HTTP.ISO_8859_1);
				return texto;
			}
		} catch (SocketTimeoutException  e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		catch (Exception e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		return null;
	}
	@Deprecated //use esse public String doGet(String url, String action, String method, Map<String, String> parameters){
	public String doGet(String url, String action, String method, HashMap<String,String> parameters){
		try {
			//preparando url para conexï¿½o com sistema legado
			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			urlRequest.append(action);
			urlRequest.append("?method=");
			urlRequest.append(method); 
			if(parameters!=null && parameters.size()>0){
				
				for (Map.Entry<String, String> entry : parameters.entrySet()) {
			        	urlRequest.append("&");
						urlRequest.append( entry.getKey());
						urlRequest.append("=");
						urlRequest.append( URLEncoder.encode(  entry.getValue() ) );
			    }
			}
			URI uri = new URI(urlRequest.toString());
			//executando request via do get
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String texto = EntityUtils.toString(entity, HTTP.ISO_8859_1);
				return texto;
			}
		} catch (SocketTimeoutException  e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		catch (Exception e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		return null;
	}
	/**
	 * methodo doGet para as transaï¿½ï¿½es de cliente http
	 * @param url - url do serviï¿½o que estï¿½ sendo requisitado
	 * @param action - action do struts que serï¿½ usada
	 * @param method - methodo da action mapeada no struts
	 * @param xml - xml marshaller das requisiï¿½ï¿½es do sistema legado
	 * @return String - resultado da consulta
	 */
	@Deprecated //usar o outro metodo doGet doGet(String url, String action, String method, ArrayList<Parameter> parameters)
	public String doGet(String url, String action, String method){
		try {
			//preparando url para conexï¿½o com sistema legado
			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			urlRequest.append(action);
			urlRequest.append("?method=");
			urlRequest.append(method); 
			URI uri = new URI(urlRequest.toString());
			//executando request via do get
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String texto = EntityUtils.toString(entity, HTTP.ISO_8859_1);
				Log.i( urlRequest.toString());
				Log.i( texto);
				return texto;
			}
		} catch (SocketTimeoutException  e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		catch (Exception e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		return null;
	}
	/**
	 * Convertendo inputstream para string
	 * @param in - imputstream resultado da requisiï¿½ï¿½o http
	 * @return String que representa o resultado da requisiï¿½ï¿½o
	 * @throws IOException
	 */
	public static String readString(InputStream in) throws IOException{
		byte[] bytes = readBytes(in);
		String texto = new String(bytes);
		return texto; 
	}
	/**
	 * Leitura de um response
	 * @param in - imputstream resultado da requisiï¿½ï¿½o http
	 * @return byte[] representa o resultado da requisiï¿½ï¿½o
	 * @throws IOException
	 */
	private static byte[] readBytes(InputStream in) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while((len = in.read(buf))>0){
			bos.write(buf,0,len);
		}
		byte[] bytes = bos.toByteArray();
		bos.close();
		return bytes;
	}
	/**
	 * Cast dos parametros do map para List<NameValuePair>
	 * @param map - parametros do request http
	 * @return parametros cast do request
	 * @throws IOException
	 */
	public List<NameValuePair> getParams(Map map) throws IOException{
		if(map == null || map.size() == 0){
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator e = (Iterator) map.keySet().iterator();
		while(e.hasNext()){
			String name = (String) e.next();
			Object value = map.get(name);
			params.add(new BasicNameValuePair(name, String.valueOf(value)));
		}
		return params;
	}
	
	public InputStream doGet2(String url, String action, String method){
		try {
			//preparando url para conexï¿½o com sistema legado
			StringBuilder urlRequest = new StringBuilder();
			urlRequest.append(url);
			urlRequest.append(action);
			urlRequest.append("?method=");
			urlRequest.append(method); 
			URI uri = new URI(urlRequest.toString());
			//executando request via do get
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream in = entity.getContent();
				return in;
			}
		} catch (SocketTimeoutException  e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		catch (Exception e) {
			Log.e( e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public void validator(String xmlMessage){
		
		//Serializable messageBean = null;
		//converter.parseXMLToBean(xmlMessage, messageBean);
		//((HttpMessage)messageBean).getErrorMessage();
	}
}
