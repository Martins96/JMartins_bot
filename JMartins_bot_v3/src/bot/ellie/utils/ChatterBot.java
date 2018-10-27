package bot.ellie.utils;

import org.apache.log4j.Logger;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.messages.Errors;

public class ChatterBot {
	
	private static Logger log = Logger.getLogger(ChatterBot.class);
	
	public static String cleverBotResponse(String request) {
				
		for(String key : Costants.CLEVER_BOT_TOKEN) {
			CleverBotQuery bot = new CleverBotQuery(key, request);
			try {
			    bot.sendRequest();
			    return bot.getResponse();
			}
			catch (Exception e) {
			    log.error("Key not work: " + key);
			    log.error("Errore Cleverbot API ", e);
			    ErrorReporter.sendError("Errore Cleverbot API Key not work: " + key);
			    try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e1) {
					Main.log.error(e1);
				}
			}
		}
		return Errors.RESPONSE_NOT_FOUND;
	}
	
	
}
