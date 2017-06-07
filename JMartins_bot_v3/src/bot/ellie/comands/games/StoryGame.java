package bot.ellie.comands.games;

import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;
import bot.ellie.utils.events.outereventsbean.MultiplayerGameBean;

public class StoryGame {
	
	private MultiplayerGameBean coreBean;
	
	public StoryGame(MultiplayerGameBean storyGameBean) {
		this.coreBean = storyGameBean;
	}
	
	public String startGame() {
		sendMessage4All("\bVERSIONE ALPHA\n\n"
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
		
		sendMessage4All("Scegliamo il/la o i/le protagonista/i... dimmi un po' chi c'è?");
		who1 = attendiMessaggio(coreBean.getIdThreadHost());
		who2 = attendiMessaggio(coreBean.getIdThreadTarget());
		
		sendMessage4All("Bene, ora che cosa fa o fanno di bello?");
		what1 = attendiMessaggio(coreBean.getIdThreadHost());
		what2 = attendiMessaggio(coreBean.getIdThreadTarget());
		
		sendMessage4All("Ok, e dove si svolge il tutto?");
		where1 = attendiMessaggio(coreBean.getIdThreadHost());
		where2 = attendiMessaggio(coreBean.getIdThreadTarget());
		
		sendMessage4All("E quando accade?");
		when1 = attendiMessaggio(coreBean.getIdThreadHost());
		when2 = attendiMessaggio(coreBean.getIdThreadTarget());
		
		sendMessage4All("Perfetto, ultima cosa, perché accade tutto?");
		why1 = attendiMessaggio(coreBean.getIdThreadHost());
		why2 = attendiMessaggio(coreBean.getIdThreadTarget());
		
		String story1 = who1 + "\n" + what2 + "\n" + where1 + "\n" 
						+ when2 + "\n" + why1;
		String story2 = who2 + "\n" + what1 + "\n" + where2 + "\n" 
				+ when1 + "\n" + why2;
		
		sendMessage4All("Ecco a voi le vostre storielle 😄");
		
		sendMessage4All(story1);
		sendMessage4All(story2);
		
		return "Gioco finito";
	}
	
	
	
	
	
	private void sendMessage4All(String mex) {
		Sender.sendMessage(coreBean.getUserHost().id(), mex);
		Sender.sendMessage(coreBean.getUserTarget().id(), mex);
	}
	
	private String attendiMessaggio(short id) {
		String mex = Getter.attendiMessaggio(id);
		
		return mex;
	}
}
