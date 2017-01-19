package bot.ellie;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.pengrad.telegrambot.model.Message;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import bot.ellie.comands.*;
import bot.ellie.comands.clouding.Cloud;
import bot.ellie.comands.clouding.Shared;
import bot.ellie.comands.games.BattagliaNavale;
import bot.ellie.comands.games.Blackjack;
import bot.ellie.comands.games.Impiccato;
import bot.ellie.security.Security;
import bot.ellie.utils.*;
import bot.ellie.utils.controlli.ControlliAutoRensponse;
import bot.ellie.utils.controlli.ControlliMessaggiRicevuti;
import bot.ellie.utils.messages.Errors;
import bot.ellie.utils.messages.Help;
import bot.ellie.utils.messages.Messages;
import bot.ellie.utils.sendTypes.Music;
import bot.ellie.utils.sendTypes.Photo;
import bot.ellie.utils.users.MyLady;
import bot.ellie.utils.users.User;



public class Risposta {
	
	User user;	
	public boolean admin = false;
	public boolean mylady = false;
	private boolean check = true;
	//user id
	Random random = new Random();
	private int idthread;
	
	private final String ANNULLA_SPEDIZIONE_MESSAGGIO = BotThread.ANNULLA_SPEDIZIONE_MESSAGGIO;
	
	String pass = Costants.ADMIN_PASSWORD;
	
	//building object
	public Risposta(int idthread) 
	{
		this.idthread = idthread;
		user = new User();
	}
	
	/**
	 * Genero una risposta per il messaggio in ingresso
	 * @param messaggio
	 * @return
	 */
	public String generaRisposta(Message messaggio)
	{
		//se il messaggio in arrivo è un messaggio non di testo
		if(messaggio.text() == null) {
			return ANNULLA_SPEDIZIONE_MESSAGGIO;
		}
		String risposta = new String();
		String testoMessaggio = new String(messaggio.text());
		testoMessaggio = testoMessaggio.substring(0,1).toUpperCase() + testoMessaggio.substring(1,testoMessaggio.length()).toLowerCase();
		//cerco risposta in base al testo inserito
		try
		{
			String ioMiChiamo;
			if(!(ioMiChiamo = ControlliMessaggiRicevuti.ioMiChiamo(messaggio)).equalsIgnoreCase("")) {
				return ioMiChiamo;
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			Main.log.error("Index out of Bound: " + e.getStackTrace());
			ErrorReporter.sendError("Index out of Bound: " + e.getMessage());
		}
		//------------------------------------------------------------------
				{
					String comandoDaFrase = ControlliMessaggiRicevuti.setComandoDaFrase(messaggio);
					if(!comandoDaFrase.equalsIgnoreCase(""))
					{
						testoMessaggio = comandoDaFrase;
					}
				}
		
		
		if(testoMessaggio.length()>1)
			if(testoMessaggio.substring(0,1).equalsIgnoreCase("\\"))
			{
				testoMessaggio = "/" + testoMessaggio.substring(1, testoMessaggio.length());
			}
		
		// eseguo comando
		if(testoMessaggio.substring(0,1).equalsIgnoreCase("/"))
		{
			risposta = eseguiComando(messaggio);
		}
		else
		{
			//cerco nel file risposte.txt
			int n = -1;
			try {
				n = leggiFileRisposta(testoMessaggio);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (n==-1) { //testo messaggio non trovato
				try {
					n = leggiFileRispostaInfo(testoMessaggio);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if(n != -1)
				{
					try {
						risposta = rispondiFileRispostaInfo(n+1);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				else {
				// in caso non trovo una risposta in locale mando a bot host
					try {
						ChatterBotFactory factory = new ChatterBotFactory();
						ChatterBot cb = factory.create(ChatterBotType.CLEVERBOT);
						ChatterBotSession cbs = cb.createSession();
						Main.log.info("Risposta non trovata, mando richiesta a bot host");
						risposta = cbs.think(testoMessaggio);
						risposta = ControlliAutoRensponse.checkAutobotNome(risposta);
					} catch (Exception e) {
						Main.log.error("Errore Cleverbot - ", e);
						ErrorReporter.sendError("Errore Cleverbot - " + e.getMessage());
						risposta = Errors.RESPONSE_NOT_FOUND;
					}
				}
			}
			else { // testo trovato
				int ran = random.nextInt(3);
				n = ran + n +1;
				try {
					risposta = rispondiFileRisposta(n);
				} catch (IOException ex) {
					ex.printStackTrace();
					risposta = Errors.RESPONSE_NOT_FOUND;
				}
			}
		}
		return risposta;
	}
	
	/**
	 * Esegue un comando, per essere un comando deve essere preposto il carattere / al comando
	 */
	public String eseguiComando(Message messaggio)
	{
		if (messaggio.text() == null) {
			Main.log.error("Null text in comand");
			ErrorReporter.sendError("Null text in comand " + messaggio.from().firstName());
			return Errors.EXEC_COMAND;
		}
		String[] comando = messaggio.text().split(" ");
		//sistemo maiuscole in minuscole
		comando[0] = comando[0].toLowerCase();
		//sistemo /* per funzione /calc
		if(comando[0].equalsIgnoreCase("/*"))
			comando[0] = new String("/x");
		if(comando[0].equalsIgnoreCase("//"))
			comando[0] = new String("/:");
		if(comando[0].equalsIgnoreCase("/dado"))
		{
			//come se tirasse un dado
			int dado = random.nextInt(6) + 1;
			return "" + dado;
		}
		
		switch(comando[0])
		{
		case("/test"):
			
			return "Nessun test in corso...";
		
		//funzioni standard
			case("/start"):
				return Messages.START;
		//------------------------------------------------------------------------------------------------------
			case("/personalinfo"):
				return "Informazioni personali chat: \n "
						+ "\n\bID Chat\b\n" + messaggio.chat().id()
						+ "\n\n\bID Utente\b\n" + messaggio.from().id()
						+ "\n\n\bNome Utente\b\n" + messaggio.from().firstName()
						+ "\n\n\bCognome Utente\b\n" + messaggio.from().lastName()
						+ "\n\n\bUsername Utente\b\n" + messaggio.from().username();
		//-------------------------------------------------------------------------------------------------------
			case("/help"):
				return Help.HELP;
		//------------------------------------------------------------------------------------------------------
			case("/botinfo"):
				return Messages.BOT_INFO;
				
		//------------------------------------------------------------------------------------------------------
			case("/asciiart"):
				String asciiart = new String("");
				int i = random.nextInt(10) + 1;
				ASCIIart asciiobject = new ASCIIart();
				try {
					asciiart = asciiobject.generaArt(i);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Main.log.info("art generata n. " + i);
				return asciiart;
		//-------------------------------------------------------------------------------------------------------
			case("/battuta"):
				
				return Battuta.comandoBattuta(comando, messaggio.from().id());
				
				
		//--------------------------------------------------------------------------------------------------------
			case("/perla"):
				
				return Perla.comandoPerla();
				
		//------------------------------------------------------------------------------------------------------------------
			case("/sticker"):
				
				Sticker sticker = new Sticker();
				Main.sendSticker(messaggio.from().id(), sticker.getSticker());
				Main.log.info("Sticker spedito a " + messaggio.from().firstName());
				
			return ANNULLA_SPEDIZIONE_MESSAGGIO;
			
		//------------------------------------------------------------------------------------------------------------------
			case("/ora"):
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				Main.log.info(dateFormat.format(date)); //15:59:48
				return "Sono le:  " + dateFormat.format(date);
				
		//----------------------------------------------------------------------------------------------------------------
			//funzione CALC
			case("/calc"):
				return Calc.help();
			
			//------------------------------------------------------------------------------//
			
			case("/+"):
				return Calc.comandoSomma(messaggio);
				
			//----------------------------------------------------------------------------------//
			
			case("/-"):
				return Calc.comandoDifferenza(messaggio);		
		
			
			//----------------------------------------------------------------------------------//
			
			case("/x"):
				return Calc.comandoProdotto(messaggio);
				
			//----------------------------------------------------------------------------------//
			
			case("/:"):
				return Calc.comandoQuoziente(messaggio);
				
			//-------------------------------------------------------------------------------------//
				
			case("/sqrt"):
				return Calc.comandoRadice(messaggio);
					
				
			//-------------------------------------------------------------------------------------//
			
				case("/rand"):
					return Calc.comandoRandom(messaggio);
					
				
			//-------------------------------------------------------------------------------------//
				
				case("/media"):
					return Calc.comandoMedia(messaggio);
			//-------------------------------------------------------------------------------------//
				
				case("/letterarand"):
					return Calc.letterarand();
			//-------------------------------------------------------------------------------------//
				case("/moneta"):
					return Calc.moneta();
			//-------------------------------------------------------------------------------------//
		//------------------------------------------------------------------------------------------------------------------
			//funzioni da USER
			case("/user"):
				
				String text = new String(Errors.GENERAL_ERROR);
				Message message = null;
				if(user.isLogged())
				{
					Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato nuovamente l'accesso user.");
					text = "Scusami, ma sei già  nella lista degli user, " + user.getName();
				}
				else
				{
					String pass;
					if(comando.length>1 && comando[1] != null)
						pass = comando[1];
					else {
						Main.sendMessage(messaggio.from().id(), "Ciao, chi sei?");
						Main.botThread[idthread].message = new Message();
						message = attendiMessaggio();
						pass = message.text() == null ? "" : message.text();
					}
					
					text = user.aggiungiUser(pass, messaggio.from().id());
					if(text.equalsIgnoreCase(Errors.USER_NOT_FOUND))
					{
						Main.log.info("ACCESSO USER NEGATO A: " + messaggio.from().username() + " - id(" + messaggio.from().id() + ")");
					}
					else
					{
						Main.log.info("ACCESSO USER CONSENTITO A: " + messaggio.from().username() + " - id(" + messaggio.from().id() + ")");
					}
				}
				return text;
			
		case("/userhelp"):
			long id = messaggio.from().id();
			if(!user.isLogged()) {
				Main.log.info(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato un comando da user non facendone parte");
				return Errors.USER_NOT_LOGGED;
			}
			else
				return Help.USER_HELP;
		
		case("/userexit"):
			id = messaggio.from().id();
			if(!user.isLogged()) {
				Main.log.info(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'uscita user non facendone parte");
				text = Errors.USER_NOT_LOGGED;
			}
			else {
				text = user.rimuoviUser(id);
			}
			return text;
				
		//--------------------------------------------------------------------------------------------------------------
			//funzioni da MYLADY
			
			case("/mylady"):
				
				if(mylady == true) {
					Main.log.info(messaggio.from().firstName() + " " + messaggio.from().lastName() + " ha tentato nuovamente di effettuare l'accesso da mylady");
					return Errors.MYLADY_ALREADY_LOGGED;
				} else {
					Main.sendMessage(messaggio.from().id(), "Mamma, sei tu?");
					message = attendiMessaggio();
					mylady = MyLady.autenticaMylady(message.text());
					
					if (mylady)
						return Messages.MYLADY_WELCOME;
					else
						return Errors.MYLADY_ERROR_LOGIN;
				}
			//----------------------------------------------------------------------------------------------------------
			case("/myladyhelp"):
				if(mylady)
					return Help.MYLADY_HELP;
				else
					return Errors.MYLADY_NOT_LOGGED;
			
			//---------------------------------------------------------------------------------------------------------
			
			case("/myladyimage"):
				if(mylady) {
					Main.sendPhoto(messaggio.from().id(), MyLady.getImageRandom());
					return ANNULLA_SPEDIZIONE_MESSAGGIO;
				}
				else
					return Errors.MYLADY_NOT_LOGGED_4_IMAGES;
			
			//----------------------------------------------------------------------------------------------------------
			case("/myladyexit"):
				if(mylady) {
					mylady = false;
					return Messages.MYLADY_DISCONNECTION;
				}
				else
					return Errors.MYLADY_NOT_LOGGED;
				
			
		//--------------------------------------------------------------------------------------------------------------
			//funzioni da ADMIN
			
			case("/admin"):
				text = new String(Errors.GENERAL_ERROR);
				if(controllaAdmin())
				{
					Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato nuovamente l'accesso admin.");
					text = Errors.ADMIN_ALREADY_LOGGED;
				}
				else {
					if(user.isLogged()) {
						Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'accesso admin mentre loggato come user.");
						text = Errors.ADMIN_ALREADY_LOGGED;
					}
					else {
						boolean accesso = false;
						String pass;
						if(comando.length>1 && comando[1] != null)
							pass = comando[1];
						else {
							Main.sendMessage(messaggio.from().id(), "Martins? Sei tu?");
							Main.botThread[idthread].message = new Message();
							message = attendiMessaggio();
							pass = message.text() == null ? "" : message.text();
						}
						accesso = autenticazione(pass);
						if(accesso) {
							Main.log.warn("ACCESSO ADMIN CONSENTITO A: " + messaggio.from().username() + " - id(" + messaggio.from().id() + ")");
							Main.sendMessage(messaggio.from().id(), "Papà ! ❤😍😘❤");
							text = Messages.ADMIN_WELCOME;
							aggiungiAdmin();	
						}
						else {
							if(pass.equals("Password123")) {
								Main.sendMessage(messaggio.from().id(), "Eh, non sono in debug ora");
							}
							Main.log.warn("ACCESSO ADMIN NEGATO A: " + messaggio.from().username() + " - id(" + messaggio.from().id() + ")");
							text = Errors.ADMIN_NOT_LOGGED;
						}
					}
				}
				return text;
		//----------------------------------------------------------------------------------------------------------
			case("/adminhelp"):
				String lista = new String(Errors.ADMIN_NOT_LOGGED);
				if (controllaAdmin())
				{
					lista = Help.ADMIN_HELP;
				}
				return lista;
		//---------------------------------------------------------------------------------------------------------------
			case("/hddlist"):
			String aHDDRisp = new String(Errors.ADMIN_NOT_LOGGED);
			if (controllaAdmin())
			{
				aHDDRisp = HDDList.comandoHDDList();
			}
			return aHDDRisp;
		//---------------------------------------------------------------------------------------------------------------
			case("/adminimage"): {
				String s = new String();
					if(controllaAdmin()) {
						switch(new Random().nextInt(5)) { //risposta random
						case(0):
							s = "Ok papà , ma sei sempre il solito 😑";
							break;
						case(1):
							s = "Uff... odio questa funzione 😑";
							break;
						case(2):
							s = "Non è carino sai? 😑";
							break;
						case(3):
							s = "Penso che sia moralmente scorretto, ma se va bene a te... 😑";
							break;
						default:
							s = "Mmm... questa è carina. No, stavo scherzando, papà, cambierai mai? 😑";
							break;
						}
						Main.sendPhoto(messaggio.from().id(), Photo.getAdminImage());
					}
					return s;
				}
					
		//---------------------------------------------------------------------------------------------------------------
			case("/adminexit"):
				rimuoviAdmin();
				return Messages.ADMIN_DISCONNECTION;
		//---------------------------------------------------------------------------------------------------------------
			case("/shutdown"):
				String shutdownRisp = Errors.ADMIN_NOT_LOGGED;
				if(controllaAdmin()) {
					Main.sendMessage(messaggio.from().id(), Messages.ADMIN_SHUTDOWN_QUESTION);
					Message m = attendiMessaggio();
					shutdownRisp = Messages.SHUTDOWN_NOPE;
					if(m.text().equalsIgnoreCase("/Yep")) {
						shutdownRisp = Messages.SHUTDOWN_YEP;
						Shutdown.comandoShutdown();
					}
					
				}
				return shutdownRisp;
				
		//---------------------------------------------------------------------------------------------------------------	
				
			case("/cloud"):
			if(controllaAdmin()){
				Main.sendMessage(messaggio.from().id(), "Avvio modalità clouding...");
				Main.log.info(messaggio.from().firstName() + messaggio.from().lastName() + messaggio.from().username() + ""
						+ "(" + messaggio.from().id() + ") ENTRA IN MODALITA' CLOUD");
				
				Cloud cloudModality = new Cloud(messaggio.from().id(), idthread);
				cloudModality.startCloudModalityForAdmin();
			} else 
				if(user.isLogged()){
					Cloud cloudModality = new Cloud(messaggio.from().id(), idthread);
					cloudModality.startCloudModalityForUser(user.getName());
				} else {
					return Errors.ADMIN_NOT_LOGGED;
			}
				return "Fine modalità clouding";
				
		//---------------------------------------------------------------------------------------------------------------
			case("/postino"):
			
				message = new Message();
				String esitopostino = new String(Errors.POSTINO_SYNTAX_ERROR);
				Postino postino = new Postino();
				String destinatario = "", testomex = "", mittente = "";
				check = false;
					mittente = user.getName();
					if(mittente == null)
					{
						check = controllaAdmin();
						if(!check)
							check = mylady;
					}
					else 
						check = true;
					if(check)
					{
						//sistemo destinatario e testomex
						if (comando.length>2 && comando[1] != null && comando[2] != null)
						{
							testomex = comando[2];
							destinatario = comando[1];
						}
						else if (comando.length>1 && comando[1] != null) {
							testomex = comando[1];
						}
						
						if (destinatario.equalsIgnoreCase(""))
						{
							Main.sendMessage(messaggio.from().id(), Messages.POSTINO_NEED_DESTINATARIO);
							message = attendiMessaggio();
							//messaggio in arrivo
							if(message.text() != null)
							{
									Main.log.info("destinatario: " + destinatario);
									destinatario = message.text().toLowerCase();
							}
							if (destinatario.equalsIgnoreCase("/exit") || destinatario.equalsIgnoreCase(""))
								return "Comando annullato";
						}
						else if (destinatario.equalsIgnoreCase("help"))
								if (mittente == null)
									esitopostino = postino.helpadmin();
								else
									esitopostino = postino.helpuser(); 
						if (testomex == "")
						{	
							Main.sendMessage(messaggio.from().id(), Messages.POSTINO_NEED_MESSAGE);
							message = attendiMessaggio();
							//messaggio in arrivo
							if(message.text() != null)
							{
								testomex = message.text();
							}
						}
						if (testomex.equalsIgnoreCase("/exit") || testomex.equalsIgnoreCase(""))
							return Messages.ABORT_COMAND;
						//----------------------------------------------------------------------------
						if (mittente == null)
							if(mylady)
								esitopostino = postino.consegnaMessaggioMylady(destinatario, testomex);
							else
								esitopostino = postino.consegnaMessaggioMartins(destinatario, testomex);
						else
							esitopostino = postino.consegnaMessaggio(destinatario, testomex, mittente);
					}
					else
					{
						return Errors.USER_NOT_LOGGED;
					}
				return esitopostino;
			//------------------------------------------------------------------------------------------------------
			case("/system"):
				esitopostino = new String(Errors.POSTINO_SYNTAX_ERROR);
				postino = new Postino();
				destinatario = "";
				testomex = "";
				check = controllaAdmin();
				if(check)
				{
					//sistemo destinatario e testomex
					for (int j=0; messaggio.text().length() > j; j++)
					{
						if (messaggio.text().substring(j, j+1).equalsIgnoreCase(" "))
						{
							j++;
							while (messaggio.text().length() > j)
							{
								if (messaggio.text().substring(j, j+1).equalsIgnoreCase(" "))
								{
									j++;
									while (messaggio.text().length() > j)
									{
										testomex = testomex + messaggio.text().substring(j, j+1);
										j++;
									}
									break;
								}
								destinatario = destinatario + messaggio.text().substring(j, j+1);
								j++;
							}
						}
					}
					if (destinatario.equalsIgnoreCase(""))
						return Errors.POSTINO_SYNTAX_ERROR;
					else if (destinatario.equalsIgnoreCase("help"))
							return postino.syshelp();
					return postino.sysMessaggio(destinatario, testomex);
				}
				else
				{
					return Errors.USER_NOT_LOGGED;
				}
			//------------------------------------------------------------------------------------------------------
			
			case("/upgrade"):
				if(!controllaAdmin())
					return Errors.ADMIN_NOT_LOGGED;
				//else
				Upgrade upgrade = new Upgrade(messaggio, idthread);
				upgrade.startUpgrade();
				
				return Messages.ABORT_COMAND;
				
			//------------------------------------------------------------------------------------------------------
				
			case("/threadattivi"):
				if(controllaAdmin())
				{
					String s = new String("Gli ID legati ai thread sono: \n");
					for (i = 0; i<Main.nThread; i++)
						s = s + "Thread " + i + ": " + Main.botThread[i].idUserThread + "\n" + Main.botThread[i].nameUserThread + "\n";
					return "I thread attivi sono: " + Main.nThread + "\n"
							+ s;
				}
				else
					return Errors.ADMIN_NOT_LOGGED;
			//------------------------------------------------------------------------------------------------------
			
			case("/shared"):
				Main.sendMessage(messaggio.from().id(), "Avvio modalità clouding...");
				Main.log.info(messaggio.from().firstName() + messaggio.from().lastName() + messaggio.from().username() + ""
						+ "(" + messaggio.from().id() + ") ENTRA IN MODALITA' CLOUD");
				
				Shared shared = new Shared(messaggio.from().id(), idthread);
				return shared.startSharedFileMode();
				
			
			//------------------------------------------------------------------------------------------------------
			
			case("/meteo"):
				Main.sendMessage(messaggio.from().id(), Messages.METEO_GET_CITY);
				message = attendiMessaggio();
				String meteo = Meteo.getMeteo(message.text());
				if (meteo.equalsIgnoreCase("error")) {
					return Errors.METEO_CITY_NOT_FOUND;
				}
				if (meteo.equalsIgnoreCase("uscita")) {
					return Messages.METEO_ABORT;
				} // input OK
				return meteo;
			
			//------------------------------------------------------------------------------------------------------
			
			case("/storia"):
				return "Funzione in via di sviluppo...";
			
			//------------------------------------------------------------------------------------------------------
			
			case("/manual"):
				Manual manual = new Manual(idthread, messaggio.from().id());
				return manual.startManualMode(comando);
				
			//------------------------------------------------------------------------------------------------------------------
			
			case("/impiccato"):
				
				return new Impiccato(idthread).startGame(messaggio);
				
			//------------------------------------------------------------------------------------------------------
				
			case ("/blackjack"):
			
				return new Blackjack(idthread).startBlackjack(messaggio);
			
			//------------------------------------------------------------------------------------------------------
			
			case("/battaglianavale"):
				
				return new BattagliaNavale(idthread, messaggio).startGame();
			
			//------------------------------------------------------------------------------------------------------
					
			case("/foto"):

				Main.sendMessage(messaggio.from().id(),
						Messages.PHOTO_NEED_TYPE);
				// attendo categoria
				message = attendiMessaggio();
				File photo = Photo.getImage(message.from().id(), message.text());
				if (photo == null)
					return Errors.GENERAL_ERROR2;
				else
					Main.sendPhoto(messaggio.from().id(), photo);
				return ANNULLA_SPEDIZIONE_MESSAGGIO;
				
			//------------------------------------------------------------------------------------------------------
			
			case("/musica"):
			synchronized (Main.ellie) {
				Main.sendAudio(messaggio.from().id(), Music.getAudio());	
			}
				return ANNULLA_SPEDIZIONE_MESSAGGIO;
			//------------------------------------------------------------------------------------------------------
			case("/security"):
				Security securityMode = new Security(messaggio.from().id(), idthread);
				securityMode.startSecurityMode();
				
			//------------------------------------------------------------------------------------------------------
			default: return "Comando " + comando[0] + " non riconosciuto, per una lista dei comandi digita /help";
		}
	}
	
	
	
	
	
	
	//XXX inizio funzioni
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------
	/**Controlla se nel file risposte.txt c'è il testo del messaggio in arrivo
	 * 
	 * @param messaggio in arrivo
	 * @return numero riga del testo del messaggio in arrivo, oppure -1 in caso non ci sia
	 * @throws IOException
	 */
	public int leggiFileRisposta(String s) throws IOException
	{
		//converto prima lettera in maiuscola
		
		String word = "-" + s;
		int n = -1;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposte.txt");
				LineNumberReader lnr = new LineNumberReader (fr);
	
				String line;
	
				while ((line = lnr.readLine ()) != null)
				{
				    if (line.equalsIgnoreCase(word))
				    {
				    	n = lnr.getLineNumber();
				    	break;
				    }
				}
			lnr.close();
			fr.close();
			return n;
	}
	public String rispondiFileRisposta(int riga) throws IOException
	{
		FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposte.txt");
		LineNumberReader lnr = new LineNumberReader (fr);

		String line;

		while ((line = lnr.readLine ()) != null)
		{
		    if (lnr.getLineNumber () == riga)
		    {
		    	break;
		    }
		}
		lnr.close();
		return line;
	}
	public int leggiFileRispostaInfo(String s) throws IOException
	{
		//converto prima lettera in maiuscola
		
		String word = "-" + s;
		int n = -1;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposteinfo.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
	
			String line;
	
			while ((line = lnr.readLine ()) != null) {
			    if (line.equalsIgnoreCase(word)) {
			    	n = lnr.getLineNumber();
			    }
			}
			lnr.close();
			fr.close();
			return n;
	}
	private String rispondiFileRispostaInfo(int riga) throws IOException 
	{
		
		FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposteinfo.txt");
		LineNumberReader lnr = new LineNumberReader (fr);

		String line;

		while ((line = lnr.readLine ()) != null) {
		    if (lnr.getLineNumber () == riga) {
		    	break;
		    }
		}
		lnr.close();
		fr.close();
		return line;
	}
	
	//----------------------------------------------------------------------------------------------------------
	// user
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------//
	//admin//
	
	/** ritorna true se la password inserita corrisponde a quella settata, in caso contrario false
	 * 
	 * @param password
	 */
	public final boolean autenticazione(String pw)
	{
		boolean accesso;
		
		if (pw.equals(pass))
		{
			accesso = true;
		}
		else
		{
			accesso = false;
		}
		return accesso;
	}
	
	/**Ritorna true se l'id è nella lista degli admin, altrimenti false
	 * 
	 * @return
	 */
	public boolean controllaAdmin()
	{
		return admin;
	}
	/**Aggiunge l'id alla lista degli admin
	 * 
	 * @param ID Admin Da Aggiungere
	 */
	public void aggiungiAdmin()
	{
		admin = true;
	}
	public void rimuoviAdmin()
	{
		admin = false;
	}
	//------------------------------------------------------------------------------
	private Message attendiMessaggio() {
		Message emptyMessage = new Message();
		synchronized (Main.botThread[idthread].message) {
			Main.botThread[idthread].message = emptyMessage;
		}
		while (Main.botThread[idthread].message.equals(emptyMessage)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Main.log.error("Errore sync del Thread");
				ErrorReporter.sendError("Errore sync del Thread", e);
				e.printStackTrace();
			}
		}
		return Main.botThread[idthread].message;
	}
}

