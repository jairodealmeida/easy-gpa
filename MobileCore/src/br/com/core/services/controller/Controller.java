package br.com.core.services.controller;


import java.util.StringTokenizer;
import java.util.WeakHashMap;

import br.com.core.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Classe de heranï¿½a das classes de controller, 
 * Classe que implementa mï¿½todos facilitadores para contruï¿½ï¿½o de artefatos para a GUI(Graphics use interface) do sistema
 * Na heranï¿½a mantem as referencias entre as interfaces e as funcionalidades de serviï¿½o e repositï¿½rio
 * Essas classes sï¿½o intermediadoras da suposta Interface e do Modelo 
 * Toda classe que herdar este artefato assumirï¿½ as caracterï¿½sticas de um controlador 
 * @author jairodealmeida@gmail.com
 * @since 11/06/2012
 */
public abstract class Controller {
	
	protected Context activity;
	@Deprecated
	private ProgressDialog dialog;
	
	protected static final String MENSAGEM_NAO_CADASTRADA = "Mensagem não cadastrada";
	protected static final String DATABASE_PATH = "/data/data/br.com.mysus_mobile/databases/";
	protected static final String DATABASE_NAME = "fito_db";
	/**
	 * Contrutor com referï¿½ncia para a interface corrente  
	 * @param activity
	 */
	public Controller(Context activity){
		this.activity = activity;
	}

	/**
	 * exibe menssagens
	 * @param message
	 */
	@Deprecated //moved to MessageUtil 
	public void  showMessage(CharSequence message){
		Context context = ((Activity)activity).getApplicationContext();
		final Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.show();
	}
	
	/**
	 * Para usar esse mï¿½todo ï¿½ necessï¿½rio informar a string da menssagem
	 * con tokens representando os argumentos da menssagem
	 * Por exemplo: 
	 * 	message : Envierei esta menssagem com (?) parametros e esse nome (?)
	 * 	args : new String[]{"2", "O Jairo ï¿½ lindï¿½o"}
	 * resultaria na menssagem  
	 * 	Envierei esta menssagem com 2 parametros e esse nome O Jairo ï¿½ lindï¿½o
	 * @param message - Menssagem que serï¿½ analizada
	 * @param args - Argumentos listados na menssagem
	 */
	@Deprecated //moved to MessageUtil 
	private String appendMessageAndArgs(String message, String[] args){
		
			if(message!=null && args!=null && args.length>0){
				StringBuilder result = new StringBuilder();
				StringTokenizer tokenizer = new StringTokenizer(message);
				int countArgs = 0;
				try {
					while(tokenizer.hasMoreTokens()){
						String tokenString = tokenizer.nextToken();
						if(tokenString.equals("(?)")){
							result.append(" " + args[countArgs]);
							countArgs = countArgs + 1;
						}else{
							result.append(" " + tokenString);
						}
					}
				} catch (Exception e) {
					Log.e(activity, e.getLocalizedMessage(),e);
				}finally{
					return result.toString();
				}
			}else{
				return null;
			}

	}
	
	/**
	 * metodo que usa as menssagens internaciobnalizadas do sistema
	 * @param stringId - Id da menssagem internacionalizada no string.xml
	 * @param argsIds - Ids das opï¿½ï¿½es internacionalizadas que seram substituidas na sintax (?) 
	 * @return retornando menssagem real de acordo o idioma selecionado
	 */
	@Deprecated //moved to MessageUtil 
	private String getMessage(int stringId, int[] argsIds){
		String message = activity.getString(stringId);
		if(message==null){
			message = MENSAGEM_NAO_CADASTRADA;
		}
		if(argsIds!=null){
			String[] args = new String[argsIds.length];
			for (int i = 0; i < args.length; i++) {
				args[i] = activity.getString(argsIds[i]);
				if(args[i]==null){
					throw new NullPointerException("argumento nï¿½o internacionalizado");
				}
			}
			message = appendMessageAndArgs(message, args);
		}
		return message;
	
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param message - menssagem que tem a sintax (?) para os parametros
	 * @param args - argumentos se for = null nï¿½o tem parametros
	 */
	@Deprecated //moved to MessageUtil 
	public void showMessage(String message, String[] args){
		message = appendMessageAndArgs(message, args);
		showMessage(message);
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @param argsIds - argumentos internacionalizados se for = null nï¿½o tem parametros
	 */
	@Deprecated //moved to MessageUtil 
	public void showMessage(int stringId, int[] argsIds){
		try {
			String message = getMessage(stringId, argsIds);
			showMessage(message);
		} catch (Exception e) {
			Log.e(activity, e.getLocalizedMessage(), e);
		}
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @param args - argumentos se for = null nï¿½o tem parametros
	 */
	@Deprecated //moved to MessageUtil 
	public void showMessage(int stringId, String[] args){
		String message = activity.getString(stringId);
		if(message==null){
			message = MENSAGEM_NAO_CADASTRADA;
		}
		message = appendMessageAndArgs(message, args);
		showMessage(message);
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada
	 */
	@Deprecated //moved to MessageUtil 
	public void  showMessage(int stringId){
		String message = activity.getString(stringId);
		if(message==null){
			message = MENSAGEM_NAO_CADASTRADA;
		}
		showMessage(message);
	}
	
	private boolean validacao = false;
	//Definition for WeakHashMap Object
	@Deprecated //moved to MessageUtil 
	private WeakHashMap< Integer, Dialog > managedDialogs = new WeakHashMap< Integer, Dialog  >();
	public static final int DIALOG = 0;
	/**
	 * Exibe um dialogo com uma menssagem de pergunta e as opï¿½ï¿½es sim ou nï¿½o
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @return a opï¿½ï¿½o selecionada no dialogo yes/no
	 */
	@Deprecated //moved to MessageUtil 
	public boolean showMessageYesNoQuestion(int stringId){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(stringId)
		       .setCancelable(false)
		       .setPositiveButton("Sim", new DialogInterface.OnClickListener(){
		           public void onClick(DialogInterface dialog, int id){
		                dialog.cancel();
		                validacao = true;
		               // notify();
		           }
		       })
		       .setNegativeButton("Não", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		                validacao = false;
		                //notify();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		return validacao;
	}
	@Deprecated //moved to MessageUtil 
	public boolean showMessageYesNoQuestion(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("Sim", new DialogInterface.OnClickListener(){
		           public void onClick(DialogInterface dialog, int id){
		                dialog.cancel();
		                validacao = true;
		                //notify();
		           }
		       })
		       .setNegativeButton("Não", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		                validacao = false;
		                //notify();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		return validacao;
	}
	@Deprecated //moved to MessageUtil 
	public boolean showMessageOk(String mensagem){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(mensagem)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener(){
		           public void onClick(DialogInterface dialog, int id){
		                dialog.cancel();
		                validacao = true;
		           }
		       });
		AlertDialog alert = builder.create();
		
		alert.show();
		return validacao;
	}
	@Deprecated //moved to MessageUtil 
	public boolean showMessageOk2(String title, String mensagem){		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		
		TextView titles = new TextView(activity);
		titles.setTextSize(30);
		titles.setText(title);
		
		TextView text = new TextView(activity);
		text.setTextSize(20);
		text.setText(mensagem);
		
		builder.setCustomTitle(titles);
		builder.setView(text)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener(){
		           public void onClick(DialogInterface dialog, int id)
		           {
		                dialog.cancel();
		                validacao = true;
		           }
		       });
		
		AlertDialog alert = builder.create();						
		alert.show();
		return validacao;
	}
	@Deprecated //moved to MessageUtil 
    public WeakHashMap< Integer, Dialog > getManagedDialogs(){
    	return managedDialogs;
    }
	/**
	 * Inicializa o dialogo de processando
	 * @param title - titulo para o dialogo
	 * @param message - menssagem para o dialogo
	 */
	@Deprecated //moved to MessageUtil 
	public void startProgress(String title, 
							  String message){
		if(dialog!=null){
			dialog.dismiss();
		}
		dialog = ProgressDialog.show(activity,title,message, true, true);
	}
	/**
 	 * Inicializa o dialogo de processando
	 * @param idTitle - titulo internacionalizado para o dialogo
	 * @param idMessage - menssagem internacionalizada para o dialogo
	 */
	@Deprecated //moved to MessageUtil 
	public void startProgress(
			int idTitle, 
			int idMessage){
		if(dialog!=null){
			dialog.dismiss();
		}
		String title = activity.getResources().getString(idTitle);
		String message = activity.getResources().getString(idMessage);
		dialog = ProgressDialog.show(activity,title,message, true, true);
	}
	@Deprecated //moved to MessageUtil 
	public ProgressDialog getProcessDialog(){
		return dialog;
	}
	/**
	 * para o dialogo de processando
	 */
	@Deprecated //moved to MessageUtil 
	public void stopProgress(){
    	if(dialog!=null){
			dialog.dismiss();
		}
	}
	/**
	 * muda o status da menssagem do dialogo de processando
	 * @param message
	 */
	@Deprecated //moved to MessageUtil 
	public void waitProgress(String message){
		  if(dialog!=null){
		   dialog.setMessage(message);
		  }
		 }
	/**
	 * pega referencia do contexto atual, 
	 * referencia da interface com o controler
	 */
	public Context getActivity(){
		return activity;
	}
	
	
}
