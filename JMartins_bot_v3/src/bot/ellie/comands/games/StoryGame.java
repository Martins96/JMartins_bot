package bot.ellie.comands.games;

import java.util.Random;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.Sender;
import bot.ellie.utils.events.outereventsbean.MultiplayerGameBean;
import bot.ellie.utils.games.storygame.GetMessageBean;

public class StoryGame {
	
	private MultiplayerGameBean coreBean;
	
	public StoryGame(MultiplayerGameBean storyGameBean) {
		this.coreBean = storyGameBean;
	}
	
	public String startGame() {
		sendMessage4All("\bVERSIONE BETA\n\n"
				+ "Avvio gioco crea storia in coppia.\n"
				+ "Ellie farà delle domande, poi mischierà le risposte\n"
				+ "Vediamo che salta fuori...");
		String who1;
		String who2;
		String what1;
		String what2;
		String where1;
		String where2;
		String when1;
		String when2;
		String why1;
		String why2;
		GetMessageBean tempBean;
		
		sendMessage4All("Scegliamo il/la o i/le protagonista/i... dimmi un po' chi c'è?");
		tempBean = attendiMessaggiPlayers(coreBean);
		who1 = tempBean.getHostMex();
		who2 = tempBean.getTargetMex();
		
		sendMessage4All("Bene, ora che cosa fa o fanno di bello?");
		tempBean = attendiMessaggiPlayers(coreBean);
		what1 = tempBean.getHostMex();
		what2 = tempBean.getTargetMex();
		
		sendMessage4All("Ok, e dove si svolge il tutto?");
		tempBean = attendiMessaggiPlayers(coreBean);
		where1 = tempBean.getHostMex();
		where2 = tempBean.getTargetMex();
		
		sendMessage4All("E quando accade?");
		tempBean = attendiMessaggiPlayers(coreBean);
		when1 = tempBean.getHostMex();
		when2 = tempBean.getTargetMex();
		
		sendMessage4All("Perfetto, ultima cosa, perché accade tutto?");
		tempBean = attendiMessaggiPlayers(coreBean);
		why1 = tempBean.getHostMex();
		why2 = tempBean.getTargetMex();
		
		String story1 = who1 + "\n" + what2 + "\n" + where1 + "\n" 
						+ when2 + "\n" + why1;
		String story2 = who2 + "\n" + what1 + "\n" + where2 + "\n" 
						+ when1 + "\n" + why2;
		Random rand = new Random();
		StringBuffer story3 = new StringBuffer();
		story3.append(rand.nextBoolean() ? who1 : who2);
		story3.append("\n");
		story3.append(rand.nextBoolean() ? what1 : what2);
		story3.append("\n");
		story3.append(rand.nextBoolean() ? where1 : where2);
		story3.append("\n");
		story3.append(rand.nextBoolean() ? when1 : when2);
		story3.append("\n");
		story3.append(rand.nextBoolean() ? why1 : why2);
		
		
		sendMessage4All("Ecco a voi le vostre storielle 😄");
		
		sendMessage4All("STORIA MISCHIATA 1:\n\n" + story1);
		sendMessage4All("STORIA MISCHIATA 2:\n\n" + story2);
		sendMessage4All("STORIA PREFERITA DI ELLIE:\n\n" +story3.toString());
		
		return "Gioco finito";
	}
	
	
	
	
	private void sendMessage4All(String mex) {
		Sender.sendMessage(coreBean.getUserHost().id(), mex);
		Sender.sendMessage(coreBean.getUserTarget().id(), mex);
	}
	
	private GetMessageBean attendiMessaggiPlayers(MultiplayerGameBean coreBean) {
	  	String hostMex = null, targetMex= null;
	  	Message emptyMessage = new Message();
	  	
	  	synchronized (Main.botThread.get(coreBean.getIdThreadHost()).message) {
	  	  	Main.botThread.get(coreBean.getIdThreadHost()).message = emptyMessage;
	  	}
	  	synchronized (Main.botThread.get(coreBean.getIdThreadTarget()).message) {
			Main.botThread.get(coreBean.getIdThreadTarget()).message = emptyMessage;
	  	}
	  	
	  	while (hostMex == null || targetMex == null) {
	  	  	if(!Main.botThread.get(coreBean.getIdThreadHost()).message.equals(emptyMessage)) {
	  	  	  	hostMex = Main.botThread.get(coreBean.getIdThreadHost()).message.text();
	  	  	}
	  	  	if(!Main.botThread.get(coreBean.getIdThreadTarget()).message.equals(emptyMessage)) {
	  	  	  	targetMex = Main.botThread.get(coreBean.getIdThreadTarget()).message.text();
	  	  	}
	  	  	
	  	  	try {
	  	  	  	Thread.sleep(100);
	  	  	} catch (InterruptedException e) {
	  	  	  	Main.log.error("Errore sync del Thread", e);
	  	  	  	ErrorReporter.sendError("Errore sync del Thread", e);
		  		e.printStackTrace();
	  	  	}
	  	}
	  	return new GetMessageBean(hostMex, targetMex);
	}
}
