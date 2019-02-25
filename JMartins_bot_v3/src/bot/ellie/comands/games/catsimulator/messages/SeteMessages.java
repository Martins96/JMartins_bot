package bot.ellie.comands.games.catsimulator.messages;

public class SeteMessages {
	
	public static String calculateMessage(short sete) {
		if(sete > 80)
			return Messages.ID100;
		else if(sete > 60)
			return Messages.ID80;
		else if(sete > 40)
			return Messages.ID60;
		else if(sete > 20)
			return Messages.ID40;
		else if(sete > 0)
			return Messages.ID20;
		return Messages.ID0;
	}
	
	private static class Messages {
		
		private static final String ID100 = "Pieno zeppo di liquidi";
		private static final String ID80 = "Dissetato";
		private static final String ID60 = "Leggermente assetato";
		private static final String ID40 = "Assetato";
		private static final String ID20 = "Molto disidratato";
		private static final String ID0 = "Morto di sete";
	}
	
}
