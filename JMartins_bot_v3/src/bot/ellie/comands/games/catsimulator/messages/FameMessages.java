package bot.ellie.comands.games.catsimulator.messages;

public class FameMessages {
	
	public static String calculateMessage(short fame) {
		if(fame > 80)
			return Messages.ID100;
		else if(fame > 60)
			return Messages.ID80;
		else if(fame > 40)
			return Messages.ID60;
		else if(fame > 20)
			return Messages.ID40;
		else if(fame > 0)
			return Messages.ID20;
		return Messages.ID0;
	}
	
	private static class Messages {
		
		private static final String ID100 = "Pienissimo e ciccionissimo";
		private static final String ID80 = "Sazio";
		private static final String ID60 = "Leggermente Affamato";
		private static final String ID40 = "Affamato";
		private static final String ID20 = "Veramente malnutrito";
		private static final String ID0 = "Morto di fame";
		
	}
}
