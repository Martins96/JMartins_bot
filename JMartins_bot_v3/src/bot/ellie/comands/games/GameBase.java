package bot.ellie.comands.games;


import com.pengrad.telegrambot.model.Message;

import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;

public abstract class GameBase {
	
	protected Message messaggio;
	protected int idthread;
		
	public abstract String startGame();
	
	
	
	protected void sendMessage(String text) {
		Sender.sendMessage(messaggio.from().id(), text);
	}
	
	protected String getMessage() {
		return Getter.attendiMessaggio(idthread);
	}
	
	
	
}
