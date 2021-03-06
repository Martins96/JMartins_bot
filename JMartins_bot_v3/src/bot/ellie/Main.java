package bot.ellie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import bot.ellie.utils.Bloccati;
import bot.ellie.utils.BotInfos;
import bot.ellie.utils.Costants;
import bot.ellie.utils.EmergencyRestartException;
import bot.ellie.utils.Sender;
import bot.ellie.utils.console.ConsoleUtilities;
import bot.ellie.utils.events.Events;
import bot.ellie.utils.events.MessaggiCasualiEvent;
import bot.ellie.utils.messages.Errors;
import bot.ellie.utils.tracing.TraceChat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Main {
	
	//carico LOG4J
	public static Logger log = null;
	public static boolean flagLongpolling = true;
	
	//retry seconds variable
	private static long retrySeconds = 500l;
	
	public static String PATH_INSTALLAZIONE;
	//flag for running program
	public static boolean running;
	
	//info di exec del bot
	public static BotInfos botInfos;
	//creazione bot Ellie
	public static TelegramBot ellie;
	//messaggio in arrivo
	public static Message message;
	//carico indice dei thread
	public static List<BotThread> botThread = new ArrayList<BotThread>();
	public static short nThread = 0;
	//costruisco oggetto per controllo id bloccati
	private static Bloccati idbloccati = new Bloccati();
	//carico per la prima volta data e ora per eventi
	private static DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
	private static Date date = new Date();
	private static String eventodate = new String(dateFormat.format(date)); //08/06 15:59
	private static String datafissa = new String(eventodate);
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		running = true;
		botInfos = new BotInfos();
		while(running) {
			try {
				mainProgram();
			} catch (EmergencyRestartException e){
				//Richiesto riavvio remoto forzato di Ellie
				Logger emergencyLog = Logger.getLogger(Main.class);
				emergencyLog.warn("REMOTE RESTART REQUEST : " + e.getMessage());
				ErrorReporter.sendWarn("Ellie restart in 30 seconds");
				try {
					for(int i=10; i>0; i=-10){
						Thread.currentThread().sleep(1000);
						ErrorReporter.sendWarn("Ellie restart in " + i + " seconds");
					}
					ellie.removeGetUpdatesListener();
				} catch (InterruptedException e1) {
					emergencyLog = Logger.getLogger(Main.class);
					emergencyLog.fatal("Error during sleep\n\n", e1);
					e1.printStackTrace();
				}
				retrySeconds = 500l;
				running = true;
			} catch (Exception e) {
				//try to send the error to my master
				ErrorReporter.sendError("FATAL ERROR - MAIN\n\n" 
											+ e.getMessage()
											+ "\n\n" + e.getCause()
											+ "\n\n\n" + e.getLocalizedMessage());
				PropertyConfigurator.configure(PATH_INSTALLAZIONE + "/conf/log4j.properties");
				Logger emergencyLog = Logger.getLogger(Main.class);
				emergencyLog.fatal("FATAL ERROR - MAIN\n\n" + e.getStackTrace(), e);
				if(retrySeconds < 120000l)
					retrySeconds = retrySeconds*2;
				botInfos.setRetrySeconds(retrySeconds);
			}
			if(running) {
				//try to reconnect
				System.out.println("Provo la riconnessione");
				if(nThread>0) {
					stopAllThread();
					nThread = 0;
					botThread = new ArrayList<BotThread>();
					botInfos.setUsersList(new ArrayList<String>());
				}
				try {
					System.out.println("retry: " + retrySeconds);
					Thread.currentThread().sleep(retrySeconds);
				} catch (InterruptedException e1) {
					Logger emergencyLog = Logger.getLogger(Main.class);
					emergencyLog.fatal("Error during sleep\n\n", e1);
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	@SuppressWarnings("static-access")
	private static void mainProgram() throws EmergencyRestartException {
		
		//carico configurazioni
		try {
			Properties configurations = new Properties();
			FileInputStream in = new FileInputStream("./conf/configuration.properties");
			configurations.load(in);
			PATH_INSTALLAZIONE = configurations.getProperty("PATH_INSTALLAZIONE");
			ellie = new TelegramBot(Costants.TELEGRAM_TOKEN);
			botInfos.setStartDate(datafissa);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			ErrorReporter.sendError("Error loading config\n\n", e);
			return;
		} catch(IOException ex) {
			ex.printStackTrace();
			ErrorReporter.sendError("Error loading config\n\n", ex);
			return;
		}
		
		PropertyConfigurator.configure(PATH_INSTALLAZIONE + "/conf/log4j.properties");
		log = Logger.getLogger(Main.class);
		
		
		ConsoleUtilities.wakeUpEllieMessage();
		
		//messaggi eventi
		dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		date = new Date();
		
		//offset messaggi
		int indexMsg = 0;

		while (flagLongpolling) {
		  	try {
			  Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
			  log.error("Error in sleep: ", e);
			}
		  	
			// controllo eventi
			dateFormat = new SimpleDateFormat("MM/dd HH:mm");
			date = new Date();
			eventodate = new String(dateFormat.format(date)); // 08/06 15:59
			if (!datafissa.equals(eventodate)) {
				datafissa = eventodate;
				Events evento = new MessaggiCasualiEvent();
				Events.controllaEvento(eventodate);
				evento.messaggiCasuali();
			}
			
			// ascolto messaggi in arrivo
			GetUpdates getUpdates = new GetUpdates().limit(20).offset(indexMsg).timeout(0);
			GetUpdatesResponse updatesResponse = ellie.execute(getUpdates);
			List<Update> updates = updatesResponse.updates();
			for(Update update : updates) {
				indexMsg = update.updateId() + 1;
				Message message = update.message();
				//check if message is edited
				if(message == null){
					log.info("Messaggio modificato, scarto...");
					continue;
				}
				
				//Aspetto fine esecuzione per variabile sincornizzata
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					log.error("Error in sleep: ", e);
				}
				
				ConsoleUtilities.stampaInfoMessaggio(message);
				TraceChat.trace(message.from(), message.text(), Integer.toString(message.from().id()));
				EmergencyRestartCheck(message);
				
				// gestisco messaggio/i in arrivo
				// controllo lista esclusi
				if (idbloccati.controllaBloccato("" + message.from().id())) {
					String s = Errors.ID_BLOCKED;
					Sender.sendMessage(message.from().id(), s);
				} else { // id mittente accettato
					boolean idGiaInThread = false;
					for (BotThread bt : botThread) {
						if (bt.idUserThread == message.from().id()) {
							idGiaInThread = true;
							synchronized (bt.message) {
								bt.message = message;
							}
						}
					}
					if (!idGiaInThread) {	
					  	//l'utente cha ha inviato il messaggio non ha un thread collegato
						if(nThread < Costants.MAX_THREADS_NUMBER) {
							botThread.add(new BotThread(nThread, message));
							botThread.get(nThread).setDaemon(true);
							botThread.get(nThread).start();
							nThread++;
							botInfos.setUserInList(
									message.from().firstName() + " " +
									message.from().lastName() + " " +
									message.from().username());
						} else { //numero di threads superiore al limite massimo
							Sender.sendMessage(message.from().id(), Errors.ELLIE_IS_BUSY);
						}
					}
				}
			}
		}
		
		//comando di spegnimento di ellie lanciato
		log.info("ELLIE TURNING IN SHUTDOWN MODE");
		stopAllThread();
		System.out.println("Ellie is ready to turn off... Bye Bye \n\n O/\n/|   <3 \n/ \\");
	}
	
	private static void EmergencyRestartCheck(Message message) 
			throws EmergencyRestartException {
		if(message != null && message.text() != null && message.text().equals(Costants.REBOOT_CMD)) {
			String s = "[" + message.from().firstName() + " " 
					+ message.from().lastName() + " "
					+ message.from().username() + " "
					+ "(" + message.from().id() + ")]";
			ErrorReporter.sendWarn("è stato lanciato il comando di restart forzato da parte di " + s);
			ellie.execute(new SendMessage(message.from().id(), "[WANRING] Restart Ellie requested! Ellie will restart soon."));
			throw new EmergencyRestartException("Emergency Restart Ellie actived by: " + s);
		}
	}
	
	private static void stopAllThread() {
		for (BotThread bt : botThread) {
			bt.flagThreadLife = false;
		}
	}
	
	public static synchronized void resetTimeoutRetry() {
	  retrySeconds = 500l;
	}
	
	
}
