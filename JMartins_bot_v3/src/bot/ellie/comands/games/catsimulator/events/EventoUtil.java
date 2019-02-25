package bot.ellie.comands.games.catsimulator.events;

import java.util.Random;

import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;

public abstract class EventoUtil implements EventoCasuale {
	
	protected static String getUserInput(int idthread, String message, String[] allowedCommands, int idUser) {
		String mex = new String();
		do {
			Sender.sendMessage(idUser, message);
			mex = Getter.attendiMessaggio(idthread);
		} while(!inputAllowed(mex, allowedCommands));
		
		return mex;
	}
	
	protected static boolean inputAllowed(String message, String[] allowedCommands) {
		for (String c : allowedCommands) {
			if(c != null && c.equals(message))
				return true;
		}
		return false;
	}
	
	protected static int getPercentuale() {
		Random rand = new Random();
		return rand.nextInt(100) + 1;
	}
	
	@Override
	public abstract PartitaBean startEvent(PartitaBean pb, int idthread, int idUser);
}
