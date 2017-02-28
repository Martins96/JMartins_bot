package bot.ellie.comands.admin;

import bot.ellie.Main;

public class ThreadAttivi {
	
	public static String visualizzaThreadAttivi() {
		String s = new String("Gli ID legati ai thread sono: \n");
		for (int i = 0; i<Main.nThread; i++)
			s = s + "Thread " + i + ": " + Main.botThread[i].idUserThread + "\n" + Main.botThread[i].nameUserThread + "\n";
		return "I thread attivi sono: " + Main.nThread + "\n"
				+ s;
	}
}
