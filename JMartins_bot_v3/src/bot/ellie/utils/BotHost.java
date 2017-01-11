package bot.ellie.utils;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import bot.ellie.Main;
import bot.ellie.utils.controlli.ControlliAutoRensponse;

public class BotHost {
	
	public static String generaRispostaHost(String testoMessaggio) throws Exception{
		String risposta;
		ChatterBotFactory factory = new ChatterBotFactory();
		ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
		ChatterBotSession bot1session = bot1.createSession();
		Main.log.info("Risposta non trovata, mando richiesta a bot host");
		risposta = bot1session.think(testoMessaggio);
		risposta = ControlliAutoRensponse.checkAutobotNome(risposta);
		return risposta;
	}
	
}
