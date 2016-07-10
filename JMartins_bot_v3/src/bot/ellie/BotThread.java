package bot.ellie;

import java.io.IOException;

import me.shib.java.lib.jtelebot.models.types.ChatId;
import me.shib.java.lib.jtelebot.models.updates.Message;

public class BotThread extends java.lang.Thread {
	
	public Message message;
	private Message msg;
	public short idThread;
	public long idUserThread; // id dell'utente legato a questo thread
	private Risposta risposta;
	
	public static final String ANNULLA_SPEDIZIONE_MESSAGGIO = "qwerty";
	
	public BotThread(short idthread, Message message) {
		idThread = idthread;
		idUserThread = message.getFrom().getId();
		this.message = message;
		risposta = new Risposta(idThread);
	}
	
	public void run() {
		try {
			Main.log.info("Thread " + idThread + " Partito!");
			Message emptyMsg = new Message();
			
			synchronized (message) {
				while (true) {
					while (message.equals(emptyMsg)) {
						sleep(1000);
					}
					msg = message; // salvo messaggio in una variabile privata
					//messaggio acqusito - pronti per elaborare
					String testoRisposta = risposta.generaRisposta(msg);
					if(testoRisposta.equals(ANNULLA_SPEDIZIONE_MESSAGGIO)) {
						//la richiesta del messaggio ha generato un tipo di risposta diverso da TESTO
						
					} else {
						Main.log.info("Risposta di Ellie a " + msg.getFrom().getUsername() + " :" + testoRisposta);
						Main.ellie.sendMessage(new ChatId(msg.getFrom().getId()), testoRisposta);
					}
					
					
					//fine esecuzione, azzero variabili per nuovo ciclo
					message = emptyMsg;
				}
			}
		}	catch (InterruptedException e) {
			Main.log.fatal("ERRORE FATALE! THREAD " + idThread + "  X-(");
			e.printStackTrace();
		} catch (IOException e) {
			Main.log.fatal("ERRORE FATALE! THREAD " + idThread + "  X-(");
			e.printStackTrace();
		}
		
		
	}
		
}
