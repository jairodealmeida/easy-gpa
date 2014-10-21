package br.com.core.session;

import android.app.Activity;
import android.content.SharedPreferences;
import br.com.core.util.Cripto;
import br.com.core.util.Log;
/**
 * Classe responss�vel por armazenar as preferencias e configura��es da aplica��o
 * Em uma sess�o que � recuperada sem acesso direto ao banco de dados
 * @author Jairo
 *
 */
public class Preferences {
	public static final String PREFS_NAME = "ConfigPreferences";
	private static Activity mActivity;
	
	public static final String NUMERO_DISP = "numero_disp";
	public static final String UNIDADE_ADM = "unidade_adm";
	public static final String SERVIDOR = "servidor";
	public static final String PORTA = "porta";
	public static final String DATABASE_NAME = "databaseName";
	public static final String IDIOMA = "idioma";
	public static final String BACKUP = "backup";
	public static final String RESTORE = "restore";
	public static final String USUARIO_SISTEMA_LEGADO = "usuario_sistema_legado";
	public static final String SENHA_SISTEMA_LEGADO = "senha_sistema_legado";
	public static final String IDIOMA_SISTEMA_LEGADO = "idioma_sistema_legado";
	public static final String DOMINIO_SISTEMA_LEGADO = "dominio_sistema_legado";
	public static final String USUARIO_SISTEMA_MOBILE = "usuario_sistema_mobile"; 
	public static final String AUTO_LOGIN = "auto_login";
	public static final String SENHA_SISTEMA_MOBILE = "senha_sistema_mobile";
	public static final String EMAIL_USUARIO = "email_usuario";
	public static final String CIDADE_USUARIO = "cidade_usuario";
	
	public static final String CONFIG_NOTIFICACOES_CUPONS_EXPIRANDO = "config_notificacoes_cupons_expirando";
	public static final String CONFIG_NOTIFICACOES_CUPONS_DISPONIVEIS = "config_notificacoes_cupons_disponiveis";
	public static final String SERVICE_URL = "service_url";
	public static final String APPLICATION_NAME = "application_name";
	public static final String DEBUG_DATA_TRANSFER = "DebugDataTransfer";
	public static final String BLOCO_TRANFERENCIA = "BlocoTransferencia";
	public static final String MODO_DEPURACAO = "ModoDepuracao";
	/**
	 * Metodo que seta os parametros configurados pelo usuario
	 * @param numeroDispositivo - Numero do dispositivo
	 * @param bean - UnidadeAdm desejada
	 * @param servidor - Servidor de sincronismo de dados do sistema legdo
	 * @param porta - Porta usada pelo servidor de sincronismo do sistema legado
	 */
	@Deprecated
	public Preferences(Activity mActivity,
			int numeroDispositivo,
			String servidor,
			String porta,
			String databaseName){
		  try
		  {
		  if(mActivity==null)
				throw new NullPointerException("Activity � obrigat�ria");
		  }
		  catch (Exception e) {
			  Log.e(mActivity, e.getLocalizedMessage());
		}
		  Preferences.mActivity = mActivity; 
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("numero_disp" , numeroDispositivo);
	      editor.putString("servidor" , servidor);
	      editor.putString("porta" , porta);
	      editor.putString("databaseName", databaseName);
	      // Salvando proferencias
	      editor.commit();
	}
	@Deprecated //substituido por serActivity(Activity mActivity)
	public Preferences(Activity mActivity){
		  if(mActivity==null){
				throw new NullPointerException("Activity � obrigat�ria");
		  }
		  Preferences.mActivity = mActivity; 
	}
	public static void setActivity(Activity mActivity){
		  if(mActivity==null){
				throw new NullPointerException("Activity � obrigat�ria");
		  }
		  Preferences.mActivity = mActivity; 
	}
	public static void setNumeroDisp(int numeroDispositivo){
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt(Preferences.NUMERO_DISP , numeroDispositivo);
	      // Salvando proferencias
	      editor.commit();
	}
	/**
	 * Pega a preferencia de numero do dispositivo
	 * @return
	 */
	public static Integer getNumeroDisp(){
		try {
			 SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		     return settings.getInt(Preferences.NUMERO_DISP , -1);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}


	public static void setServidor(String servidor){
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString(Preferences.SERVIDOR , servidor);
	      // Salvando proferencias
	      editor.commit();
	}
	/**
	 * Pega a preferencia de caminho do servidor 
	 * @return
	 */
	public static String getServidor(){
		try {
			 SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		     return settings.getString(Preferences.SERVIDOR , null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setPorta(String porta){
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString(Preferences.PORTA , porta);
	      // Salvando proferencias
	      editor.commit();
	}
	
	/**
	 * Pega a preferencia de numero de porta
	 * @return
	 */
	public static String getPorta(){
		try {
			 SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		     return settings.getString(Preferences.PORTA , null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setIdioma(String idioma){ 
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString(Preferences.IDIOMA , idioma);
	      // Salvando proferencias
	      editor.commit();
	}
	
	public static void setUsuarioMobile(String usuario){ 
		  //O objeto Editor captura as configura��es de proferencia 	
	      // Todos os objetos vem do android.context.Context
	      SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString(Preferences.USUARIO_SISTEMA_MOBILE , usuario);
	      // Salvando proferencias
	      editor.commit();
	}
	
	public static String getIdioma(){
		try {
			 SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		     return settings.getString(Preferences.IDIOMA, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setDatabaseName(String databaseName)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.DATABASE_NAME, databaseName);
		editor.commit();
	}
	
	public static String getDatabaseName()
	{
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.DATABASE_NAME, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setDateBackup(String dateBackup)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.BACKUP, dateBackup);
		editor.commit();
	}
	
	public static String getDateBackup()
	{
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.BACKUP, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setDateRestore(String dateRestore)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.RESTORE, dateRestore);
		editor.commit();
	}
	
	public static String getDateRestore()
	{
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.RESTORE, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setUsuarioSistemaLegado(String usuarioSistemaLegado)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(
				Preferences.USUARIO_SISTEMA_LEGADO, 
				usuarioSistemaLegado);
		editor.commit();
	}
	public static String getUsuarioSistemaLegado(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.USUARIO_SISTEMA_LEGADO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static String getUsuarioMobile(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.USUARIO_SISTEMA_MOBILE, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	public static void setSenhaSistemaLegado(String senhaSistemaLegado)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.SENHA_SISTEMA_LEGADO, senhaSistemaLegado);
		editor.commit();
	}
	public static String getSenhaSistemaLegado(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.SENHA_SISTEMA_LEGADO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setIdiomaSistemaLegado(String idiomaSistemaLegado)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.IDIOMA_SISTEMA_LEGADO, idiomaSistemaLegado);
		editor.commit();
	}
	public static String getIdiomaSistemaLegado(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.IDIOMA_SISTEMA_LEGADO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setDomainSistemaLegado(String domainSistemaLegado)
	{
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.DOMINIO_SISTEMA_LEGADO, domainSistemaLegado);
		editor.commit();
	}
	public static String getDomainSistemaLegado(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.DOMINIO_SISTEMA_LEGADO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setAutoLogin(boolean autoLogin){

		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Preferences.AUTO_LOGIN, autoLogin);
		editor.commit();
	}
	public static boolean getAutoLogin(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getBoolean(Preferences.AUTO_LOGIN, false);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return false;
	}
	
	public static void setSenhaMobile(String senha){
		String senhaCripto = Cripto.encripty(senha);
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.SENHA_SISTEMA_MOBILE, senhaCripto);
		editor.commit();
	}
	public static String getSenhaMobile(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			String senha = settings.getString(Preferences.SENHA_SISTEMA_MOBILE, null);
			if(senha!=null){
				String senhaDecripto = Cripto.decripty(senha);
				return senhaDecripto;
			}else{
				return null;
			}

		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setEmail(String email) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.EMAIL_USUARIO, email);
		editor.commit();
	}
	public static String getEmail(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.EMAIL_USUARIO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	public static void setCidadeUsuario(String nomeCidade) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.CIDADE_USUARIO, nomeCidade);
		editor.commit();
	}
	public static String getCidadeUsuario(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.CIDADE_USUARIO, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	

	public static void setConfigNotificacoesCuponsExpirando(boolean valor) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Preferences.CONFIG_NOTIFICACOES_CUPONS_EXPIRANDO, valor);
		editor.commit();
	}
	public static boolean getConfigNotificacoesCuponsExpirando(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getBoolean(Preferences.CONFIG_NOTIFICACOES_CUPONS_EXPIRANDO, true);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return true;
	}
	public static void setConfigNotificacoesCuponsDisponiveis(boolean valor) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Preferences.CONFIG_NOTIFICACOES_CUPONS_DISPONIVEIS, valor);
		editor.commit();
	}
	public static boolean getConfigNotificacoesCuponsDisponiveis(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getBoolean(Preferences.CONFIG_NOTIFICACOES_CUPONS_DISPONIVEIS, true);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return true;
	}
	public static void setServiceUrl(String serviceUrl) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.SERVICE_URL, serviceUrl);
		editor.commit();
	}
	public static String getServiceUrl(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.SERVICE_URL, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	
	public static void setApplicationName(String applicationName) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Preferences.APPLICATION_NAME, applicationName);
		editor.commit();
	}
	public static String getApplicationName(){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(Preferences.APPLICATION_NAME, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	
	public static void addPreference(String name, String value){
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);
		editor.commit();
	}
	public static String getPreference(String name){
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getString(name, null);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	 
	
	public static boolean isDebugDataTransfer() {
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getBoolean(Preferences.DEBUG_DATA_TRANSFER, false);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return false;
	}
	public static void setDebugDataTransfer(boolean enabled) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Preferences.DEBUG_DATA_TRANSFER, enabled);
		editor.commit();
	}
	public static void setBlocoTransferencia(int bloco) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Preferences.BLOCO_TRANFERENCIA, bloco);
		editor.commit();
	}
	public static int getBlocoTransferencia() {
		try {
			SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			return settings.getInt(Preferences.BLOCO_TRANFERENCIA, 50);
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return 50;
	}
	public static boolean isModoDepuracao() {
		try {
			if(mActivity!=null){
				SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
				return settings.getBoolean(Preferences.MODO_DEPURACAO, true);
			}else{
				return false;
			}
		} catch (Exception e) {
			Log.e(mActivity,e.getLocalizedMessage(),e);
		}
		return false;
	}
	public static void setModoDepuracao(boolean enabled) {
		SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Preferences.MODO_DEPURACAO, enabled);
		editor.commit();
	}
}
