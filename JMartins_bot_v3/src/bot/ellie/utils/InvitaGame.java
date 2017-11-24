package bot.ellie.utils;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import bot.ellie.BotThread;
import bot.ellie.Main;
import bot.ellie.utils.events.outereventsbean.MultiplayerGameBean;
import bot.ellie.utils.games.multi.InvitoRifiutatoException;

public class InvitaGame {
	
	private InvitaGame() {
	}
	
	public static MultiplayerGameBean invitaPlayer(String game, int idTarget, User userMittente) 
			throws InvitoRifiutatoException {
		StringBuffer sb = new StringBuffer();
		sb.append(userMittente.firstName() != null ? userMittente.firstName() + " " : "");
		sb.append(userMittente.lastName() != null ? userMittente.lastName() + " " : "");
		sb.append(userMittente.username() != null ? userMittente.username() + " " : "");
		sb.append("ti ha invitato a giocare a " + game);
		sb.append("\nAccetti? \n/si\n/no");
		Sender.sendMessage(idTarget, sb.toString());
		
		boolean isTargetInThread = false;
		short idThreadMittente = 0;
		short idThreadTarget = 0;
		for(BotThread bt : Main.botThread) {
			if(bt.idUserThread == idTarget) {
				isTargetInThread = true;
				if(bt.inGame) {
					Sender.sendMessage(userMittente.id(), "Utente già in game");
					throw new InvitoRifiutatoException();
				}
				idThreadTarget = bt.idThread;
				break;
			}
		}
		for(BotThread bt : Main.botThread) {
			if(bt.idUserThread == userMittente.id()) {
				idThreadMittente = bt.idThread;
				break;
			}
		}
		
		
		if(!isTargetInThread) {
			idThreadTarget = Main.nThread;
			Main.nThread++;
			Main.botThread.add(new BotThread(idThreadTarget, null, null, null, idTarget));
			Main.botThread.get(idThreadTarget).start();
		}
		Main.botThread.get(idThreadTarget).inGame = true;
		
		Message risposta = Getter.attendiMessaggioObject((int)idThreadTarget);
		if(risposta != null && risposta.text() != null 
				&& risposta.text().equalsIgnoreCase("/si")) {
			Main.botThread.get(idThreadTarget).inGame = true;
			Sender.sendMessage(userMittente.id(), "L'utente ha accettato l'invito");
		}
		else {
			Sender.sendMessage(userMittente.id(), "L'utente ha rifiutato l'invito");
			Main.botThread.get(idThreadTarget).inGame = false;
			throw new InvitoRifiutatoException();
		}
		
		MultiplayerGameBean bean = new MultiplayerGameBean(idThreadTarget, idThreadMittente, risposta.from(), userMittente);
		
		return bean;
	}
	
}
