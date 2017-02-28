package bot.ellie;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

import bot.ellie.utils.Costants;

public class ErrorReporter implements Runnable{
	
	private final static String TOKEN = Costants.TELEGRAM_TOKEN;
	//creazione bot Ellie
	private static TelegramBot EmergencyEllie = null; //   
	
	private String error;
	private Exception e;
	
	public ErrorReporter() {
		
	}
	
	private ErrorReporter(String error, Exception e) {
		this.error = error;
		this.e = e;
	}

	@Override
	public void run() {
		
		String errore = error;
		if(e != null)
			errore = errore + " \n " + e.getMessage();
		
		EmergencyEllie = TelegramBotAdapter.buildDebug(TOKEN);
		EmergencyEllie.execute(new SendMessage(115949778, "EMERGENCY ELLIE\n\nERRORE :'(\n" + error));
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	
	/**invio errori a martins <3
	 * 
	 * @param error
	 */
	public static void sendError(String error) {
		(new Thread(new ErrorReporter(error, null))).start();
	}
	
	public static void sendError(String error, Exception e) {
		(new Thread(new ErrorReporter(error, e))).start();
	}
	
	
	
	
	
	
	
}
