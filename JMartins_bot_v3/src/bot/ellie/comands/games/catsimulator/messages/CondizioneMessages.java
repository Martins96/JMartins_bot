package bot.ellie.comands.games.catsimulator.messages;


public class CondizioneMessages {
	
	public static String calculateMessage(short condizione) {
		if(condizione > 80)
			return Messages.ID100;
		else if(condizione > 60)
			return Messages.ID80;
		else if(condizione > 40)
			return Messages.ID60;
		else if(condizione > 20)
			return Messages.ID40;
		else if(condizione > 0)
			return Messages.ID20;
		return Messages.ID0;
	}
	
	private static class Messages {
		
		private static final String ID100 = "Nessuna ferita";
		private static final String ID80 = "Pelo leggermente scomposto";
		private static final String ID60 = "Piccoli taglietti";
		private static final String ID40 = "Leggermente ferito";
		private static final String ID20 = "Gravemente ferito";
		private static final String ID0 = "Dissanguato";
	}
	
}
