package bot.ellie.utils;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import bot.ellie.BotThread;
import bot.ellie.Main;

public class InvitaGame {
	
	
	public static void invitaPlayer(String game, Object idTarget, User userMittente) {
		if(idTarget == null) return;
		StringBuffer sb = new StringBuffer();
		sb.append(userMittente.firstName() != null ? userMittente.firstName() + " " : "");
		sb.append(userMittente.lastName() != null ? userMittente.lastName() + " " : "");
		sb.append(userMittente.username() != null ? userMittente.username() + " " : "");
		sb.append("ti ha invitato a giocare a " + game);
		sb.append("\nAccetti? \n/si\n/no");
		Sender.sendMessage(idTarget, sb.toString());
		
		short idThread = Main.nThread;
		Main.nThread++;
		Main.botThread.add(new BotThread(idThread, null, null, null, (int)idTarget));
		Main.botThread.get(idThread).start();
		
		Message risposta = Getter.attendiMessaggioObject((int)idTarget);
		if(risposta != null && risposta.text() != null 
				&& risposta.text().equalsIgnoreCase("/si")) {
			Main.botThread.get(idThread).inGame = true;
			Sender.sendMessage(userMittente.id(), "L'utente ha accettato l'invito");
		}
		else {
			Sender.sendMessage(userMittente.id(), "L'utente ha rifiutato l'invito");
		}
	}
	
}
