package bot.ellie;

import java.io.IOException;

import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.models.updates.Update;

public class MainThreadMsg extends Thread {
	
	Message message;
	
	public void run(){ 
		while (true) {
			try { // in ascolto di eventuali messaggi
				while (true) {
					Update[] updates;
					if ((updates = Main.ellie.getUpdates()) != null) {
						for (Update update : updates) {
							message = update.getMessage();
							if (message != null) {
								Main.log.info("---------------------------------------------------");
								Main.log.info("-----------NUOVO---MESSAGGIO---RICEVUTO-----------");
								Main.log.info("Nome: " + message.getFrom().getFirst_name());
								Main.log.info("Cognome: " + message.getFrom().getLast_name());
								Main.log.info("Username: " + message.getFrom().getUsername() + " ("
										+ message.getFrom().getId() + ")");
								Main.log.info("Chat ID: " + message.getChat().getId());
								Main.log.info("Testo: " + message.getText());
								Main.log.info("---------------------------------------------------");
								synchronized (Main.message) {
									Main.message = message;
								}

							}
						}
					}
					message = new Message();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Main.log.error(e);
			}
		}
	}

}
