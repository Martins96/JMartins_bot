package bot.ellie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InputFile;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import bot.ellie.comands.Sticker;
import bot.ellie.utils.Bloccati;
import bot.ellie.utils.CompleannoEllie;
import bot.ellie.utils.IMG2ASCII;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {
	
	//carico LOG4J
	public static Logger log = null;
	
	public static boolean flagLongpolling = true;
	
	public final static String BUILD_VERSION = "3.1.4.2";
	private final static short MAX_THREADS_NUMBER = 50;
	private final static String TOKEN = "142988631:AAHPmCwgega6a3X9lv5DCdfpcGdCk293F_E";
	private final static String COMPLEANNO_ELLIE_MEX = "Compleanno Ellie";
	public static String PATH_INSTALLAZIONE;
	//numero di immagini e video per messaggi casuali
	private static final int N_RANDOM_VIDEO = 0;
	private static final int N_RANDOM_IMAGE = 21;
	//INSERIRE QUI IL NUMERO DELLE RIGHE DEL FILE messaggicasuali.txt
	private static final int N_RANDOM_TEXT = 20;
	//creazione bot Ellie
	public static TelegramBot ellie = TelegramBotAdapter.build(TOKEN);
	//messaggio in arrivo
	public static Message message;
	//carico indice dei thread
	public static BotThread[] botThread = new BotThread[MAX_THREADS_NUMBER];
	public static short nThread = 0;
	//costruisco oggetto per controllo id bloccati
	private static Bloccati idbloccati = new Bloccati();
	//carico per la prima volta data e ora per eventi
	private static DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
	private static Date date = new Date();
	private static String eventodate = new String(dateFormat.format(date)); //08/06 15:59
	private static String datafissa = new String(eventodate);
	
	
	public static void main(String[] args) {
		try {
			mainProgram();
		} catch (Exception e) {
			//try to send the error to my master
			ErrorReporter.sendError("FATAL ERROR - MAIN\n\n", e);
			PropertyConfigurator.configure(PATH_INSTALLAZIONE + "/readfiles/config.properties");
			Logger emergencyLog = Logger.getLogger(Main.class);
			emergencyLog.fatal("FATAL ERROR - MAIN\n\n", e);
		}
	}
	private static void mainProgram() {
		
		//carico configurazioni
		try{
			Properties configurations = new Properties();
			FileInputStream in = new FileInputStream("./conf/configuration.properties");
			configurations.load(in);
			PATH_INSTALLAZIONE = configurations.getProperty("PATH_INSTALLAZIONE");
		} catch(FileNotFoundException e){
			e.printStackTrace();
			return;
		} catch(IOException ex) {
			ex.printStackTrace();
			return;
		}
		
		PropertyConfigurator.configure(PATH_INSTALLAZIONE + "/readfiles/config.properties");
		log = Logger.getLogger(Main.class);
		//Console Boot
		BufferedReader bboot;
		FileReader fboot;
		try {
	    	fboot = new FileReader(PATH_INSTALLAZIONE + "/readfiles/Boot.txt");
	    	bboot = new BufferedReader(fboot);
	    	String boot = new String(bboot.readLine());
		
			while(boot != null)
			{
				System.out.println(boot);
				boot = bboot.readLine();
			}
			fboot.close();
			bboot.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println();
		System.out.println("     _________________________");
		System.out.println("    /                         \\");
		System.out.println("    | Ellie, The telegram bot |");
		System.out.println("    |        Version  3       |");
		System.out.println("    \\_________________________/");
		System.out.println("\nCreate by Martins(c)\n--build " + BUILD_VERSION + "\n\n");
		
		log.info("\n|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|");
		log.info("\n\n[  Ellie è attiva! :-)  ]\n");
		
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
				datafissa = new String(eventodate);
				controllaEvento(eventodate);
				messaggiCasuali();
			}
			// ascolto messaggi in arrivo
			GetUpdatesResponse updatesResponse = ellie.execute(new GetUpdates().limit(0).offset(indexUpdates).timeout(0));
            List<Update> updates = updatesResponse.updates();
			for (int z = 0; z < updates.size(); z++) {
				indexUpdates = updates.get(z).updateId() + 1;
				Message message = updates.get(z).message();
				stampaInfoMessaggio(message);
				// gestisco messaggio/i in arrivo
				// controllo lista esclusi
				if (idbloccati.controllaBloccato("" + message.from().id())) {
					String s = "Non ho piacere a parlare con te 😡😡😡😡 Puoi insultarmi quanto vuoi ma ignorerò i tuoi messaggi\n\n\n\n -Bloccato da admin-";
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
					if (!idGiaInThread) {// l'utente cha ha inviato il
											// messaggio non ha un thread
											// collegato
						if(nThread < MAX_THREADS_NUMBER) {
							botThread[nThread] = new BotThread(nThread, message);
							botThread[nThread].start();
							nThread++;
						} else { //numero di threads superiore al limite massimo
							sendMessage(message.from().id(), "Ellie è veramente troppo occupata ora non posso rispondere, per favore prova più tardi");
						}
					}
				}
			}
		}
		
		//comando di spegnimento di ellie lanciato
		log.info("ELLIE TURNING IN SHUTDOWN MODE");
		for (int i = 0; i<nThread; i++) {
			botThread[i].flagThreadLife = false;
		}
		System.out.println("Ellie is ready to turn off... Bye Bye \n\n O/\n/|   <3 \n/ \\");
	}
	
	private static void stampaInfoMessaggio(Message message) {

					if (message != null) {
						if (message.text() != null) {
							log.info("---------------------------------------------------");
							log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
							log.info("Nome: " + message.from().firstName());
							log.info("Cognome: " + message.from().lastName());
							log.info("Username: " + message.from().username() + " (" + message.from().id() + ")");
							log.info("Testo: " + message.text());
							log.info("---------------------------------------------------");
						} else {
							log.info("---------------------------------------------------");
							log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
							log.info(":messaggio non di testo da parte di:");
							log.info("Nome: " + message.from().firstName());
							log.info("Cognome: " + message.from().lastName());
							log.info("Username: " + message.from().username() + " (" + message.from().id() + ")");
							if(message.photo() != null && message.photo()[0] != null) {
								GetFile getFile = new GetFile(message.photo()[0].fileId());
								GetFileResponse response = ellie.execute(getFile);
								if(response != null) {
									IMG2ASCII converter = new IMG2ASCII();
									converter.convertToAscii(ellie.getFullFilePath(response.file()));
								} else {
									log.info("Il file ricevuto è troppo grande...");
								}
							} else {
								if(message.sticker() != null) {
									log.info("Sticker : " + message.sticker().fileId());
									sendSticker(message.from().id(), new Sticker().getSticker());
								}
							}
							log.info("---------------------------------------------------");
						}
					}
		
	}
	
	private static void controllaEvento(String eventodate)
	{	
		
		try {
			FileReader fr = new FileReader (PATH_INSTALLAZIONE + "/readfiles/eventi.txt");
			LineNumberReader lnr = new LineNumberReader (fr);

			String line;
			while ((line = lnr.readLine ()) != null)
			{
			    if (line.equals(eventodate))
			    {
			    	String destinatario = lnr.readLine();
			    	String mex = lnr.readLine();
			    	//compleanno ellie
			    	if(mex.equalsIgnoreCase(COMPLEANNO_ELLIE_MEX)){
			    		mex = CompleannoEllie.getAuguriDiEllie();
			    	}
			    	long iddestinatario = 0;
			    	
			    	//seleziono destinatario
			    	switch(destinatario)
			    	{
			    	case("Martins"):
			    		iddestinatario = 115949778;
			    	break;
			    	case("Gaia"):
			    		iddestinatario = 124796388;
			    	break;
			    	case("Ale"):
			    		iddestinatario = 143009205;
			    	break;
			    	case("Matte"):
			    		iddestinatario = 76891271;
			    	break;
			    	case("Francesco"):
			    		iddestinatario = 170595365;
			    	break;
			    	case("All"):
				    	{
				    		//broadcast
			    			log.info("EVENT BROADCAST");
				    		//inserire manualmente gli id
				    		iddestinatario = 115949778;
				    		log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Martins" + " (" + iddestinatario + ")\n" + mex + "\n");
				    		sendMessage(iddestinatario, mex);
							log.info("Messaggio spedito in data: " + eventodate);
							
							iddestinatario = 124796388;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Gaia" + " (" + iddestinatario + ")\n" + mex + "\n");
							sendMessage(iddestinatario, mex);
							log.info("Messaggio spedito");
							
							iddestinatario = 143009205;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Ale" + " (" + iddestinatario + ")\n" + mex + "\n");
							sendMessage(iddestinatario, mex);
							log.info("Messaggio spedito");
							
							iddestinatario = 76891271;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Matte" + " (" + iddestinatario + ")\n" + mex + "\n");
							sendMessage(iddestinatario, mex);
							log.info("Messaggio spedito");
							
							iddestinatario = 104412734;
							System.out.println("Evento avviato: " + line + "\nDestinatario: " + "Valentino" + " (" + iddestinatario + ")\n" + mex + "\n");
							sendMessage(iddestinatario, mex);
							log.info("Messaggio spedito");
							
							/*iddestinatario =;
							System.out.println("Evento avviato: " + line + "\nDestinatario: " + destinatario + " (" + iddestinatario + ")\n" + mex + "\n");
							chat = new User(iddestinatario, "");
							ellie.getSender().sendText(mex, chat, null);
							System.out.println("Messaggio spedito");*/
					    	fr.close();
							lnr.close();
							return;
				    	}
			    	}
			    	log.info("Evento avviato: " + line + "\nDestinatario: " + destinatario + " (" + iddestinatario + ")\n" + mex + "\n");
			    	sendMessage(iddestinatario, mex);
			    	log.info("Messaggio spedito in data: " + eventodate);
			    	fr.close();
			    	lnr.close();
			    	return;
			    }
			}
			lnr.close();
		} catch (IOException e) {
			log.error("Errore gestione eventi");
			ErrorReporter.sendError("Errore gestione eventi" + e);
			e.printStackTrace();
		} 
	}
	
	public static void messaggiCasuali()
	{
		try {
			//mando messaggi solo in orari specifici
			DateFormat oraFormat = new SimpleDateFormat("HH");
			Date orario = new Date();
			int ora = Integer.parseInt("" + oraFormat.format(orario));
			if(ora > 10)
			{
				//carico variabile casuale per messaggi random
				Random random = new Random();
				int i = random.nextInt(1600);
				if(i == 88)
				{
					switch (random.nextInt(2)) {
					case 0:
						LineNumberReader lnr = new LineNumberReader(
								new FileReader(PATH_INSTALLAZIONE + "/readfiles/messaggicasuali.txt"));
						String line;
						i = random.nextInt(N_RANDOM_TEXT);
						while ((line = lnr.readLine()) != null) {
							if (lnr.getLineNumber() == i) {
								log.info("Messaggio casuale riga " + i + " inviato: " + line
										+ "\nDestinatario: Martins(115949778)\n");
								sendMessage(115949778, line);
								log.info("Messaggio spedito in data: " + orario);
								// --------------------------------------
								log.info("Messaggio casuale riga " + i + " inviato: " + line
										+ "\nDestinatario: Matte(76891271)\n");
								sendMessage(76891271, line);
								log.info("Messaggio spedito in data: " + orario);
								// --------------------------------------
								log.info("Messaggio casuale riga " + i + " inviato: " + line
										+ "\nDestinatario: Gaia(124796388)\n");
								sendMessage(124796388, line);
								log.info("Messaggio spedito in data: " + orario);
								// --------------------------------------
								/*
								 * AGGIUNGERE MANUALMENTE ID DEL DESTINATARIO
								 * PER MESSAGGI CASUALI
								 * log.info("Evento avviato: " + line +
								 * "\nDestinatario: " + destinatario + " (" +
								 * iddestinatario + ")\n" + mex + "\n");
								 * ellie.sendMessage(new ChatId(), line);
								 * log.info("Messaggio spedito");
								 */

								lnr.close();
								return;
							}
						}
						lnr.close();
						return;

					case 1:
						// send an image
						log.info("Foto casuale inviata\nDestinatario: Martins(115949778)\n");
						sendPhoto(115949778, new InputFile("jpg", new File(PATH_INSTALLAZIONE
								+ "/readfiles/photo/randomSend/image" + random.nextInt(N_RANDOM_IMAGE) + ".jpg")));
						log.info("Foto casuale inviata\nDestinatario: Gaia(124796388)\n");
						sendPhoto(124796388, new InputFile("jpg", new File(PATH_INSTALLAZIONE
								+ "/readfiles/photo/randomSend/image" + random.nextInt(N_RANDOM_IMAGE) + ".jpg")));
						log.info("Foto casuale spedito in data: " + orario);
						return;
						
					case 2:
						// send an video
						log.info("Video casuale inviato\nDestinatario: Martins(115949778)\n");
						sendVideo(115949778, new InputFile("mp4", new File(PATH_INSTALLAZIONE
								+ "/readfiles/video/randomSend/video" + random.nextInt(N_RANDOM_VIDEO) + ".mp4")));
						log.info("Messaggio spedito in data: " + orario);
						return;
					}
				}		
			} else 
				return;
		} catch (IOException e) {
			log.error("Errore lettura file messaggi casuali");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * invio messaggi
	 */
	public static void sendMessage(Object id, String text) {
		ellie.execute(new SendMessage(id, text));
	}
	
	/**
	 * invio documenti
	 */
	public static void sendDocument(Object id, InputFile doc) {
		ellie.execute(new SendDocument(id, doc.getFile()));
	}
	
	/**
	 * invio foto
	 */
	public static void sendPhoto(Object id, InputFile photo) {
		ellie.execute(new SendPhoto(id, photo.getFile()));
	}
	
	/**
	 * invio audio
	 */
	public static void sendAudio(Object id, InputFile music) {
		ellie.execute(new SendAudio(id, music.getFile()));
	}
	
	/**
	 * invio video
	 */
	public static void sendVideo(Object id, InputFile music) {
		ellie.execute(new SendVideo(id, music.getFile()));
	}
	
	/**
	 * invio sticker
	 */
	public static void sendSticker(Object id, String sticker) {
		ellie.execute(new SendSticker(id, sticker));
	}
}
