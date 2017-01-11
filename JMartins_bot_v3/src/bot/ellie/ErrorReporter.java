package bot.ellie;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

import bot.ellie.utils.Costants;

public class ErrorReporter {
	
	private final static String TOKEN = Costants.TOKEN;
	//creazione bot Ellie
	private static TelegramBot EmergencyEllie = null; //   
	
	/**invio errori a martins <3
	 * 
	 * @param error
	 */
	public static void sendError(String error) {
		EmergencyEllie = TelegramBotAdapter.buildDebug(TOKEN);
			EmergencyEllie.execute(new SendMessage(115949778, "EMERGENCY ELLIE\n\nERRORE :'(\n" + error));
	}
	
	public static void sendError(String error, Exception e) {
		String errore = error;
		if(e != null)
			errore = errore + " \n " + e.getMessage();
		sendError(errore);
	}

}
