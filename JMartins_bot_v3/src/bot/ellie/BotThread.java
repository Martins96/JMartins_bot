package bot.ellie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.utils.Sender;



public class BotThread extends Thread {
	
	public Message message;
	private Message msg;
	public boolean flagThreadLife = true;
	public final short idThread;
	public long idUserThread; // id dell'utente legato a questo thread
	public String nameUserThread;
	private Risposta risposta;
	
	//Variabili outer events//
	public boolean inGame = false;
	//////////////////////////
	
	public static final String ANNULLA_SPEDIZIONE_MESSAGGIO = "qwerty";
	
	public BotThread(short idthread, Message message) {
		this.idThread = idthread;
		this.idUserThread = message.from().id();
		this.nameUserThread = message.from().firstName() + " " + message.from().lastName() + "(" + message.from().username() + ")";
		this.message = message;
		risposta = new Risposta(idThread);
	}
	
	public BotThread(short idthread, String firstName, String lastName, String username, int id) {
		this.idThread = idthread;
		this.idUserThread = id;
		this.nameUserThread = firstName + " " + lastName + "(" + username + ")";
		this.message = new Message();
		risposta = new Risposta(idThread);
	}
	
	public void recreateInfoThread(Message message) {
		if(message == null ||
				message.from() == null)
			return;
		this.idUserThread = message.from().id();
		this.nameUserThread = message.from().firstName() + " " + message.from().lastName() + "(" + message.from().username() + ")";
		
	}
	
	public void run() {
		try {
			startThread();
		} catch (Exception e) {
			//try to send the error to my master
			ErrorReporter.sendError("FATAL ERROR - Thread\n\n", e);
			PropertyConfigurator.configure(Main.PATH_INSTALLAZIONE + "/conf/log4j.properties");
			Logger emergencyLog = Logger.getLogger(Main.class);
			emergencyLog.fatal("FATAL ERROR - Thread " + idThread
					+ "\nLegato all'Utente: " + nameUserThread + "(" + idUserThread + ")"
					+ "\n", e);
		}
	}
	
	private void startThread() {
		try {
			Main.log.info("Thread " + idThread + " Partito!");
			Message emptyMsg = new Message();
			
			synchronized (message) {
				while (flagThreadLife) {
					while (message.equals(emptyMsg)) {
						sleep(100);
					}
					if(inGame) {
						sleep(5000);
						continue;
					}
					msg = message; // salvo messaggio in una variabile privata
					//messaggio acqusito - pronti per elaborare
					String testoRisposta = risposta.generaRisposta(msg);
					if(testoRisposta.equals(ANNULLA_SPEDIZIONE_MESSAGGIO)) {
						//la richiesta del messaggio ha generato un tipo di risposta diverso da TESTO
					} else {
						Main.log.info("Risposta di Ellie a " + msg.from().username() + " :" + testoRisposta);
						Sender.sendMessage(msg.from().id(), testoRisposta);
					}
					
					
					//fine esecuzione, azzero variabili per nuovo ciclo
					message = emptyMsg;
				}
			}
		}	catch (InterruptedException e) {
			Main.log.fatal("ERRORE FATALE! THREAD " + idThread + "  X-(", e);
			ErrorReporter.sendError("ERRORE FATALE! THREAD " + idThread + "  X-(", e);
		}
	}
	
	
	public void setAdminMode(boolean b) {
		risposta.admin = b;
	}
	
	public void setMyladyMode(boolean b) {
		risposta.mylady = b;
	}
}
