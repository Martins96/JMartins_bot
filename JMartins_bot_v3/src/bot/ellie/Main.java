package bot.ellie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import bot.ellie.readfiles.Bloccati;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import me.shib.java.lib.jtelebot.models.types.ChatId;
import me.shib.java.lib.jtelebot.models.updates.Message;
import me.shib.java.lib.jtelebot.models.updates.Update;
import me.shib.java.lib.jtelebot.service.TelegramBot;

public class Main {
	
	//carico LOG4J
	public static Logger log = null;
	
	private final static String TOKEN = "142988631:AAHPmCwgega6a3X9lv5DCdfpcGdCk293F_E";
	//creazione bot Ellie
	public static TelegramBot ellie = TelegramBot.getInstance(TOKEN);
	//messaggio in arrivo
	public static Message message;
	private static Message emptyMessage = new Message();
	//carico indice dei thread
	public static BotThread[] botThread = new BotThread[50];
	public static short nThread = 0;
	//costruisco oggetto per controllo id bloccati
	private static Bloccati idbloccati = new Bloccati();
	//carico per la prima volta data e ora per eventi
	private static DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
	private static Date date = new Date();
	private static String eventodate = new String(dateFormat.format(date)); //08/06 15:59
	private static String datafissa = new String(eventodate);
	

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("src/bot/ellie/readfiles/config.properties");
		log = Logger.getLogger(Main.class);
		//creo demone per ascolto dei messaggi
		MainThreadMsg mainThreadMsg = new MainThreadMsg();
		mainThreadMsg.setDaemon(true);
		message = emptyMessage;
		//Console Boot
		BufferedReader bboot;
		FileReader fboot;
		try {
	    	fboot = new FileReader("src/bot/ellie/readfiles/Boot.txt");
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
		System.out.println("\nCreate by Martins(c)\n--build 3.0.1.0\n\n");
		
		log.info("\n|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|");
		log.info("\n\n[  Ellie è attiva! :-)  ]\n");
		
		//messaggi eventi
		dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		date = new Date();
		//avvio demone per ascolto messaggi
		mainThreadMsg.start();

		while(true) {
			// controllo eventi
			dateFormat = new SimpleDateFormat("MM/dd HH:mm");
			date = new Date();
			eventodate = new String(dateFormat.format(date)); //08/06 15:59
			if(!datafissa.equals(eventodate))
			{
				datafissa = new String(eventodate);
				controllaEvento(eventodate);
				messaggiCasuali();
			}
			//ascolto messaggi in arrivo
			synchronized (message) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.fatal("ERRORE FATALE nello sleep del main");
					log.fatal(e);
					e.printStackTrace();
				}
			}
			
			
			if(!message.equals(emptyMessage)) {
				//messaggio ricevuto
				//controllo lista esclusi
				if(idbloccati.controllaBloccato("" + message.getFrom().getId()))
				{
					String s = "Non ho piacere a parlare con te 😠 Puoi insultarmi quanto vuoi ma ignorerò i tuoi messaggi\n\n\n\n -Bloccato da admin-";
					try {
						ellie.sendMessage(new ChatId(message.getFrom().getId()), s);
					} catch (IOException e) {
						log.error("Errore invio messaggio all'ID bloccato :(");
						ErrorReporter.sendError("Errore invio messaggio all'ID bloccato :(" + e.getMessage());
						e.printStackTrace();
					}
				} else { // id mittente accettato
					boolean idGiaInThread = false;
					for(short i = 0; i<nThread; i++) {
						if(botThread[i].idUserThread == message.getFrom().getId()) {
							idGiaInThread = true;
							synchronized (botThread[i].message) {
								log.info("Gestisce THREAD " + i);
								botThread[i].message = message;
							}
						}
					}
					if(!idGiaInThread) {// l'utente cha ha inviato il messaggio non ha un thread collegato
						botThread[nThread] = new BotThread(nThread, message);
						botThread[nThread].start();
						nThread++;
					}
				}
				message = emptyMessage;
			}
		}
	}
	
	public static Message attentiMessaggio() {
		Message message = null;
		
		try { //in ascolto di eventuali messaggi
			Update[] updates;
			if ((updates = ellie.getUpdates()) != null) {
				for (Update update : updates) {
					message = update.getMessage();
					if (message != null) {
						if (message.getText() != null) {
							log.info("---------------------------------------------------");
							log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
							log.info("Nome: " + message.getFrom().getFirst_name());
							log.info("Cognome: " + message.getFrom().getLast_name());
							log.info("Username: " + message.getFrom().getUsername() + " (" + message.getFrom().getId() + ")");
							log.info("Testo: " + message.getText());
							log.info("---------------------------------------------------");
							return message;
						} else {
							log.info("---------------------------------------------------");
							log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
							log.info(":messaggio non di testo da parte di:");
							log.info("Nome: " + message.getFrom().getFirst_name());
							log.info("Cognome: " + message.getFrom().getLast_name());
							log.info("Username: " + message.getFrom().getUsername() + " (" + message.getFrom().getId() + ")");
							log.info("---------------------------------------------------");
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			ErrorReporter.sendError("Errore ricezione mex" + e.getMessage());
			log.error("Errore ricezione mex");
			log.error(e);
		}
		return message;
		
	}
	
	private static void controllaEvento(String eventodate)
	{	
		
		try {
			FileReader fr = new FileReader ("src/bot/ellie/readfiles/eventi.txt");
			LineNumberReader lnr = new LineNumberReader (fr);

			String line;
			while ((line = lnr.readLine ()) != null)
			{
			    if (line.equals(eventodate))
			    {
			    	String destinatario = lnr.readLine();
			    	String mex = lnr.readLine();
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
							ellie.sendMessage(new ChatId(iddestinatario), mex);
							log.info("Messaggio spedito in data: " + eventodate);
							
							iddestinatario = 124796388;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Gaia" + " (" + iddestinatario + ")\n" + mex + "\n");
							ellie.sendMessage(new ChatId(iddestinatario), mex);
							log.info("Messaggio spedito");
							
							iddestinatario = 143009205;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Ale" + " (" + iddestinatario + ")\n" + mex + "\n");
							ellie.sendMessage(new ChatId(iddestinatario), mex);
							log.info("Messaggio spedito");
							
							iddestinatario = 76891271;
							log.info("Evento avviato: " + line + "\nDestinatario: " + "Matte" + " (" + iddestinatario + ")\n" + mex + "\n");
							ellie.sendMessage(new ChatId(iddestinatario), mex);
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
			    	ellie.sendMessage(new ChatId(iddestinatario), mex);
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
					LineNumberReader lnr = new LineNumberReader (new FileReader ("src/bot/ellie/readfiles/messaggicasuali.txt"));
					String line;
					
					//INSERIRE QUI IL NUMERO DELLE RIGHE DEL FILE messaggicasuali.txt
					i = random.nextInt(19);
					while ((line = lnr.readLine ()) != null) {
					    if (lnr.getLineNumber () == i) {
					    	log.info("Messaggio casuale inviato: " + line + "\nDestinatario: Martins(115949778)\n");
							ellie.sendMessage(new ChatId(115949778), line);
							log.info("Messaggio spedito in data: " + orario);
							//--------------------------------------
							log.info("Messaggio casuale inviato: " + line + "\nDestinatario: Matte(76891271)\n");
							ellie.sendMessage(new ChatId(76891271), line);
							log.info("Messaggio spedito in data: " + orario);
							//--------------------------------------
							/* AGGIUNGERE MANUALMENTE ID DEL DESTINATARIO PER MESSAGGI CASUALI
							log.info("Evento avviato: " + line + "\nDestinatario: " + destinatario + " (" + iddestinatario + ")\n" + mex + "\n");
							ellie.sendMessage(new ChatId(), line);
							log.info("Messaggio spedito");
							*/
							
							lnr.close();
					    	return;
					    }
					}
					lnr.close();
				}
			} else 
				return;
		} catch (IOException e) {
			log.error("Errore lettura file messaggi casuali");
			e.printStackTrace();
		}
	}

}
