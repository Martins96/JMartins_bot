package bot.ellie.comands.admin;

import bot.ellie.Main;

public class Shutdown {
	
	public static void comandoShutdown() {
		Main.flagLongpolling = false;
		Main.running = false;
	}
	
	
}
