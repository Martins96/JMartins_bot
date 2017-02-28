package bot.ellie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendGame;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import bot.ellie.utils.Bloccati;
import bot.ellie.utils.ConsoleUtilities;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Events;
import bot.ellie.utils.messages.Errors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Main {
	
	//carico LOG4J
	public static Logger log = null;
	public static boolean flagLongpolling = true;
	
	//retry seconds variable
	private static long retrySeconds = 500;
	
	public static String PATH_INSTALLAZIONE;
	//flag for running program
	public static boolean running;
	
	//creazione bot Ellie
	public static TelegramBot ellie;
	//messaggio in arrivo
	public static Message message;
	//carico indice dei thread
	public static BotThread[] botThread = new BotThread[Costants.MAX_THREADS_NUMBER];
	public static short nThread = 0;
	//costruisco oggetto per controllo id bloccati
	private static Bloccati idbloccati = new Bloccati();
	//carico per la prima volta data e ora per eventi
	private static DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
	private static Date date = new Date();
	private static String eventodate = new String(dateFormat.format(date)); //08/06 15:59
	private static String datafissa = new String(eventodate);
	
	
	public static void main(String[] args) {
		running = true;
		while(running) {
			try {
				mainProgram();
			} catch (Exception e) {
				//try to send the error to my master
				ErrorReporter.sendError("FATAL ERROR - MAIN\n\n", e);
				PropertyConfigurator.configure(PATH_INSTALLAZIONE + "/conf/log4j.properties");
				Logger emergencyLog = Logger.getLogger(Main.class);
				emergencyLog.fatal("FATAL ERROR - MAIN\n\n", e);
				if(retrySeconds < 1800000)
				retrySeconds = retrySeconds*2;
			}
			if(running) {
				//try to reconnect
				System.out.println("Provo la riconnessione");
				if(nThread>0) {
					stopAllThread();
					nThread = 0;
					botThread = new BotThread[Costants.MAX_THREADS_NUMBER];
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
	
	
	private static void mainProgram() {
		
		//carico configurazioni
		try {
			Properties configurations = new Properties();
			FileInputStream in = new FileInputStream("./conf/configuration.properties");
			configurations.load(in);
			PATH_INSTALLAZIONE = configurations.getProperty("PATH_INSTALLAZIONE");
			ellie = TelegramBotAdapter.build(Costants.TELEGRAM_TOKEN);
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
		int indexUpdates = 0;

		while (flagLongpolling) {
			// controllo eventi
			dateFormat = new SimpleDateFormat("MM/dd HH:mm");
			date = new Date();
			eventodate = new String(dateFormat.format(date)); // 08/06 15:59
			if (!datafissa.equals(eventodate)) {
				datafissa = eventodate;
				Events.controllaEvento(eventodate);
				Events.messaggiCasuali();
			}
			
			// ascolto messaggi in arrivo
			GetUpdatesResponse updatesResponse = ellie.execute(new GetUpdates().limit(10).offset(indexUpdates).timeout(0));
            List<Update> updates = updatesResponse.updates();
			for (int z = 0; z < updates.size(); z++) {
				indexUpdates = updates.get(z).updateId() + 1;
				Message message = updates.get(z).message();
				//check if message is edited
				if(message == null){
					log.info("Messaggio modificato, scarto...");
					continue;
				}
				ConsoleUtilities.stampaInfoMessaggio(message);
				// gestisco messaggio/i in arrivo
				// controllo lista esclusi
				if (idbloccati.controllaBloccato("" + message.from().id())) {
					String s = Errors.ID_BLOCKED;
					sendMessage(message.from().id(), s);
				} else { // id mittente accettato
					boolean idGiaInThread = false;
					for (short i = 0; i < nThread; i++) {
						if (botThread[i].idUserThread == message.from().id()) {
							idGiaInThread = true;
							synchronized (botThread[i].message) {
								//log.info("Gestisce THREAD " + i);
								botThread[i].message = message;
							}
						}
					}
					if (!idGiaInThread) {	// l'utente cha ha inviato il
											// messaggio non ha un thread collegato
						if(nThread < Costants.MAX_THREADS_NUMBER) {
							botThread[nThread] = new BotThread(nThread, message);
							botThread[nThread].start();
							nThread++;
						} else { //numero di threads superiore al limite massimo
							sendMessage(message.from().id(), Errors.ELLIE_IS_BUSY);
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
	
	private static void stopAllThread() {
		for (int i = 0; i<nThread; i++) {
			botThread[i].flagThreadLife = false;
		}
	}
	
	
	
	
	
	/**
	 * invio messaggi
	 */
	public static void sendMessage(Object id, String text) {
		ellie.execute(new SendMessage(id, text));
		log.info("Messaggio inviato a " + id);
	}
	
	/**
	 * invio documenti
	 */
	public static void sendDocument(Object id, File doc) {
		ellie.execute(new SendDocument(id, doc));
		log.info("Documento inviato a " + id);
	}
	
	/**
	 * invio foto
	 */
	public static void sendPhoto(Object id, File photo) {
		ellie.execute(new SendPhoto(id, photo));
		log.info("Foto inviato a " + id);
	}
	
	/**
	 * invio audio
	 */
	public static void sendAudio(Object id, File music) {
		ellie.execute(new SendAudio(id, music));
		log.info("Musica inviato a " + id);
	}
	
	/**
	 * invio video
	 */
	public static void sendVideo(Object id, File video) {
		ellie.execute(new SendVideo(id, video));
		log.info("Video inviato a " + id);
	}
	
	/**
	 * invio sticker
	 */
	public static void sendSticker(Object id, String sticker) {
		ellie.execute(new SendSticker(id, sticker));
		log.info("Sticker inviato a " + id);
	}
	
	/**
	 * invio gioco
	 */
	public static void sendGame(Object id, String game) {
		ellie.execute(new SendGame(id, game));
		log.info("Gioco inviato a " + id);
	}
}
