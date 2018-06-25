package bot.ellie.comands.games.catsimulator.messages;


public class SonnoMessages {
	
	public static String calculateMessage(short sonno) {
		if(sonno > 80)
			return Messages.ID100;
		else if(sonno > 60)
			return Messages.ID80;
		else if(sonno > 40)
			return Messages.ID60;
		else if(sonno > 20)
			return Messages.ID40;
		else if(sonno > 0)
			return Messages.ID20;
		return Messages.ID0;
	}
	
	private static class Messages {
		
		private static final String ID100 = "Super riposato";
		private static final String ID80 = "Ben riposato";
		private static final String ID60 = "Riposato";
		private static final String ID40 = "Stanco";
		private static final String ID20 = "Molto stanco";
		private static final String ID0 = "Stanco morto";
	}
	
}
