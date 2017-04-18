package bot.ellie.utils;

import java.util.Arrays;
import java.util.List;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.messages.Errors;

public class ChatterBot {
	
	public static String cleverBotResponse(String request) {
		
		String response = null;
		List<String> keyClever = Arrays.asList(Costants.CLEVER_BOT_TOKEN);
		
		for(String key : keyClever) {
			CleverBotQuery bot = new CleverBotQuery(key, request);
			try {
			    bot.sendRequest();
			    response = bot.getResponse();
			}
			catch (Exception e) {
			    Main.log.error("Key not work: " + bot.getAPIKey());
			    ErrorReporter.sendError("Errore Cleverbot API ");
			}
		}
		return response != null ? response : Errors.RESPONSE_NOT_FOUND;
	}
	
	
}
