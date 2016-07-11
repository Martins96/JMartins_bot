package bot.ellie;

import java.io.IOException;

import me.shib.java.lib.jtelebot.models.types.ChatId;
import me.shib.java.lib.jtelebot.service.TelegramBot;

public class ErrorReporter {
	
	private final static String TOKEN = "142988631:AAHPmCwgega6a3X9lv5DCdfpcGdCk293F_E";
	//creazione bot Ellie
	private static TelegramBot EmergencyEllie = null; //   
	
	/**invio errori a martins <3
	 * 
	 * @param error
	 */
	public static void sendError(String error) {
		EmergencyEllie = TelegramBot.getInstance(TOKEN);
		try {
			EmergencyEllie.sendMessage(new ChatId(115949778), "EMERGENCY ELLIE\n\nERRORE :'(\n" + error);
		} catch (IOException e) {
			Main.log.fatal("EMERGENCY ELLIE IN ERRORE");
		}
	}
	
	public static void sendError(String error, Exception e) {
		String errore = error;
		if(e != null)
			errore = errore + " \n " + e.getMessage();
		sendError(errore);
	}

}
