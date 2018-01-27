package bot.ellie.utils;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.messages.Errors;

public class ChatterBot {
	
	public static String cleverBotResponse(String request) {
		
		String response = null;
		
		for(String key : Costants.CLEVER_BOT_TOKEN) {
			CleverBotQuery bot = new CleverBotQuery(key, request);
			try {
			    bot.sendRequest();
			    return bot.getResponse();
			}
			catch (Exception e) {
			    Main.log.error("Key not work: " + bot.getAPIKey());
			    Main.log.error("Errore Cleverbot API ", e);
			    ErrorReporter.sendError("Errore Cleverbot API ");
			}
		}
		return Errors.RESPONSE_NOT_FOUND;
	}
	
	
}
