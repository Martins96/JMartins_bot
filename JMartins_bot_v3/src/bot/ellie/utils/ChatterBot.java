package bot.ellie.utils;

import java.io.IOException;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

import bot.ellie.ErrorReporter;
import bot.ellie.utils.messages.Errors;

public class ChatterBot {
	
	public static String cleverBotResponse(String request) {
		
		CleverBotQuery bot = new CleverBotQuery(Costants.CLEVER_BOT_TOKEN, request);
		
		String response;

		try
		{
		    bot.sendRequest();
		    response = bot.getResponse();
		}
		catch (IOException e)
		{
		    response = Errors.RESPONSE_NOT_FOUND;
		    ErrorReporter.sendError("Errore Cleverbot API ", e);
		}
		return response;
	}
	
	
	
}
