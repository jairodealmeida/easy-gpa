package br.com.core.util;

import java.util.StringTokenizer;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

public class MessageUtil {
	public static String MESSAGE_NAO_CADASTRADA = "Mensagem não cadastrada";
	private ProgressDialog dialog;
	/**
	 * exibe menssagens
	 * @param message
	 */
	public void  showMessage(CharSequence message, Activity activity){
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
	private String appendMessageAndArgs(Activity activity,String message, String[] args){
		
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
	private String getMessage(int stringId, int[] argsIds, Activity activity){
		String message = activity.getString(stringId);
		if(message==null){
			message = "Mensagem não cadastrada";
		}
		if(argsIds!=null){
			String[] args = new String[argsIds.length];
			for (int i = 0; i < args.length; i++) {
				args[i] = activity.getString(argsIds[i]);
				if(args[i]==null){
					throw new NullPointerException("argumento nï¿½o internacionalizado");
				}
			}
			message = appendMessageAndArgs(activity, message, args);
		}
		return message;
	
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param message - menssagem que tem a sintax (?) para os parametros
	 * @param args - argumentos se for = null nï¿½o tem parametros
	 */
	public void showMessage(String message, String[] args, Activity activity){
		message = appendMessageAndArgs(activity,message, args);
		showMessage(message,activity);
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @param argsIds - argumentos internacionalizados se for = null nï¿½o tem parametros
	 */
	public void showMessage(int stringId, int[] argsIds, Activity activity){
		try {
			String message = getMessage(stringId, argsIds,activity);
			showMessage(message,activity);
		} catch (Exception e) {
			Log.e(activity, e.getLocalizedMessage(), e);
		}
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @param args - argumentos se for = null nï¿½o tem parametros
	 */
	public void showMessage(int stringId, String[] args, Activity activity){
		String message = activity.getString(stringId);
		if(message==null){
			message = "Mensagem não cadastrada";
		}
		message = appendMessageAndArgs(activity,message, args);
		showMessage(message,activity);
	}
	/**
	 * exibe uma menssagens com argumentos
	 * @param stringId - menssagem internacionalizada
	 */
	public void  showMessage(int stringId, Activity activity){
		String message = activity.getString(stringId);
		if(message==null){
			message = MESSAGE_NAO_CADASTRADA;
		}
		showMessage(message,activity);
	}
	
	private static boolean validacao = false;
	//Definition for WeakHashMap Object
	private WeakHashMap< Integer, Dialog > managedDialogs = new WeakHashMap< Integer, Dialog  >();
	public static final int DIALOG = 0;
	/**
	 * Exibe um dialogo com uma menssagem de pergunta e as opï¿½ï¿½es sim ou nï¿½o
	 * @param stringId - menssagem internacionalizada que tem a sintax (?) para os parametros
	 * @return a opï¿½ï¿½o selecionada no dialogo yes/no
	 */
	public boolean showMessageYesNoQuestion(int stringId, Activity activity){
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
	public boolean showMessageYesNoQuestion(String message, Activity activity){
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
	public static boolean showMessageOk(String mensagem, Activity activity){
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
	
	public static boolean showMessageOk2(String title, String mensagem, Activity activity){		
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
	
    public WeakHashMap< Integer, Dialog > getManagedDialogs(){
    	return managedDialogs;
    }
	/**
	 * Inicializa o dialogo de processando
	 * @param title - titulo para o dialogo
	 * @param message - menssagem para o dialogo
	 */
	public void startProgress(String title, 
							  String message, Activity activity){
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
	public void startProgress(
			int idTitle, 
			int idMessage, Activity activity){
		if(dialog!=null){
			dialog.dismiss();
		}
		String title = activity.getResources().getString(idTitle);
		String message = activity.getResources().getString(idMessage);
		dialog = ProgressDialog.show(activity,title,message, true, true);
	}
	public ProgressDialog getProcessDialog(){
		return dialog;
	}
	/**
	 * para o dialogo de processando
	 */
	public void stopProgress(){
    	if(dialog!=null){
			dialog.dismiss();
		}
	}
	/**
	 * muda o status da menssagem do dialogo de processando
	 * @param message
	 */
	public void waitProgress(String message){
		  if(dialog!=null){
		   dialog.setMessage(message);
		  }
		 }
}
