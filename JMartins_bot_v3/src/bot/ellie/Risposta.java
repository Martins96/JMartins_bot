package bot.ellie;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InputFile;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import bot.ellie.comands.*;
import bot.ellie.comands.clouding.Cloud;
import bot.ellie.comands.clouding.Shared;
import bot.ellie.security.Security;
import bot.ellie.utils.*;



public class Risposta {
	
	User user;	
	boolean adminid = false;
	private boolean check = true;
	//user id
	Random random = new Random();
	private int idthread;
	
	private final String ANNULLA_SPEDIZIONE_MESSAGGIO = BotThread.ANNULLA_SPEDIZIONE_MESSAGGIO;
	
	String pass = "Password123";
	
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
						ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
						ChatterBotSession bot1session = bot1.createSession();
						Main.log.info("Risposta non trovata, mando richiesta a bot host");
						risposta = bot1session.think(testoMessaggio);
						risposta = ControlliAutoRensponse.checkAutobotNome(risposta);
					} catch (Exception e) {
						Main.log.error("Errore Cleverbot - ", e);
						ErrorReporter.sendError("Errore Cleverbot - " + e.getMessage());
						risposta = "Scusami, il mio dizionario di risposte è limitato. Sono stata sviluppata per eseguire diverse e specifiche funzioni, dai un'occhiata a /help per vedere quali puoi usare. Per comodità  evita le faccine per favore";
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
					risposta = "Scusami, il mio dizionario di risposte è limitato. Sono stata sviluppata per eseguire diverse e specifiche funzioni, dai un'occhiata a /help per vedere quali puoi usare. Per comodità  evita le faccine per favore";
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
			return "Oh, qualcosa è andato storto nell'esecuzione del comando";
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
		//funzioni standard
			case("/start"):
				return "Sono sveglia ❤";
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
				return "/-----------------------------\\"
						+ "\n| Ellie  - The Telegram Bot |\n"
						+ "\\-----------------------------/"
						+ "\n\nVersion : " + Main.BUILD_VERSION + "\n"
						+ "Create by Martins\n"
						+ "\nEllie is a Telegram Bot programmed in Java for fun, the goal is to make you smile, have a nice day :)";
				
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
				
				String text = new String("Qualcosa è andato storto...");
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
					if(text.equalsIgnoreCase("Non credo di conoscerti, scusa"))
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
				return "Scusami, ma non fai parte della lista degli user";
			}
			else
				return Help.USER_HELP;
		
		case("/userexit"):
			id = messaggio.from().id();
			if(!user.isLogged()) {
				Main.log.info(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'uscita user non facendone parte");
				text = "Scusami, ma non fai parte della lista degli user";
			}
			else {
				text = user.rimuoviUser(id);
			}
			return text;
				
		//--------------------------------------------------------------------------------------------------------------
			//funzioni da ADMIN
			
			case("/admin"):
				text = new String("Qualcosa è andato storto...");
				text = new String();
				if(controllaAdmin())
				{
					Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato nuovamente l'accesso admin.");
					text = "Scusami, ma sei già  admin";
				}
				else {
					if(user.isLogged()) {
						Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'accesso admin mentre loggato come user.");
						text = "Scusami, ma sei già un user";
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
							text = "Papà , ti ricordo che la lista dei tuoi comandi è \" /adminhelp \" 😘";
							aggiungiAdmin();	
						}
						else {
							Main.log.warn("ACCESSO ADMIN NEGATO A: " + messaggio.from().username() + " - id(" + messaggio.from().id() + ")");
							text = "No, fa niente";
						}
					}
				}
				return text;
		//----------------------------------------------------------------------------------------------------------
			case("/adminhelp"):
				String lista = new String("Perdonami, ma non posso accontentarti. Non hai i privilegio da admin per questo comando");
				if (controllaAdmin())
				{
					lista = Help.ADMIN_HELP;
				}
				return lista;
		//---------------------------------------------------------------------------------------------------------------
			case("/hddlist"):
			String aHDDRisp = new String("Perdonami, ma non posso accontentarti. Non hai i privilegio da admin per questo comando");
			if (controllaAdmin())
			{
				aHDDRisp = HDDList.comandoHDDList();
			}
			return aHDDRisp;
		//---------------------------------------------------------------------------------------------------------------
			case("/adminimage"): {
				String s = new String("Perdonami, ma non posso accontentarti. Non hai i privilegio da admin per questo comando");
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
				return "Privilegio da ADMIN rimosso, buona giornata papà  😘❤";
		//---------------------------------------------------------------------------------------------------------------
			case("/shutdown"):
				String shutdownRisp = "Scusami, ma non hai i privilegi necessari per questo comando";
				if(controllaAdmin()) {
					Main.sendMessage(messaggio.from().id(), "Papà, sei sicuro di volermi spegnere? 😟 \n/Yep \n\n/Nope");
					Message m = attendiMessaggio();
					shutdownRisp = "Ok resto attiva 😘";
					if(m.text().equalsIgnoreCase("/Yep")) {
						shutdownRisp = "Vado a nanna 😇 \n Bye Bye";
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
					return "Non hai i privilegi necessari per questa funzione, accedi tramite /admin";
			}
				return "Fine modalità clouding";
				
		//---------------------------------------------------------------------------------------------------------------
			case("/postino"):
			
				message = new Message();
				String esitopostino = new String("Errore invio messaggio: consultare la guida su come inviare tramite il comando '/postino help'");
				Postino postino = new Postino();
				String destinatario = "", testomex = "", mittente = "";
				check = false;
					mittente = user.getName();
					if(mittente == null)
					{
						check = controllaAdmin();
					}
					else
						check = true;
					if(check)
					{
						//sistemo destinatario e testomex
						if (comando.length>2 && comando[1] != null && comando[2] != null)
						{
							testomex = comando[1];
							destinatario = comando[2];
						}
						else if (comando.length>1 && comando[1] != null) {
							testomex = comando[1];
						}
						
						if (destinatario.equalsIgnoreCase(""))
						{
							Main.sendMessage(messaggio.from().id(), "Ok, a chi devo spedire il messaggio?\nDigita /exit per annullare il comando");
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
							Main.sendMessage(messaggio.from().id(), "Ok, cosa devo inviare?\nDigita /exit per annullare");
							message = attendiMessaggio();
							//messaggio in arrivo
							if(message.text() != null)
							{
								testomex = message.text();
							}
						}
						if (testomex.equalsIgnoreCase("/exit") || testomex.equalsIgnoreCase(""))
							return "Comando annullato";
						//----------------------------------------------------------------------------
						if (mittente == null)
							esitopostino = postino.consegnaMessaggioMartins(destinatario, testomex);
						else
							esitopostino = postino.consegnaMessaggio(destinatario, testomex, mittente);
					}
					else
					{
						return "Scusa, ma non hai il privilegio necessario per questo comando.\nAccedi tramite /user o /admin";
					}
				return esitopostino;
			//------------------------------------------------------------------------------------------------------
			case("/system"):
				esitopostino = new String("Errore invio messaggio: consultare la guida su come inviare tramite il comando '/postino help'");
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
						return "Errore invio messaggio: consultare la guida su come inviare tramite il comando '/postino help'";
					else if (destinatario.equalsIgnoreCase("help"))
							return postino.syshelp();
					return postino.sysMessaggio(destinatario, testomex);
				}
				else
				{
					return "Scusa, ma non hai il privilegio necessario per questo comando.\nAccedi tramite /user o /admin";
				}
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
					return "Non hai i privilegi necessari per questo comando";
			//------------------------------------------------------------------------------------------------------
			
			case("/shared"):
				Main.sendMessage(messaggio.from().id(), "Avvio modalità clouding...");
				Main.log.info(messaggio.from().firstName() + messaggio.from().lastName() + messaggio.from().username() + ""
						+ "(" + messaggio.from().id() + ") ENTRA IN MODALITA' CLOUD");
				
				Shared shared = new Shared(messaggio.from().id(), idthread);
				return shared.startSharedFileMode();
				
			
			//------------------------------------------------------------------------------------------------------
			
			case("/meteo"):
				Main.sendMessage(messaggio.from().id(), "Seleziona una città  dalla lista:\n"
						+ "/Bergamo\n"
						+ "/Verona\n"
						+ "/Milano\n"
						+ "/Torino\n"
						+ "/Roma\n"
						+ "/Bari\n"
						+ "/Palermo\n"
						+ "\n/exit - comando di uscita dalla funzione");
				message = attendiMessaggio();
				String meteo = Meteo.getMeteo(message.text());
				if (meteo.equalsIgnoreCase("error")) {
					return "Città  non riconosciuta esco dalla funzione meteo. Utilizza /meteo per riprovate e seleziona una città  dalla lista";
				}
				if (meteo.equalsIgnoreCase("uscita")) {
					return "Funzione meteo annullata";
				} // input OK
				return meteo;
			
			//------------------------------------------------------------------------------------------------------
			
			case("/storia"):
				return "Funzione in via di sviluppo...";
			
			//------------------------------------------------------------------------------------------------------	
			
			case("/impiccato"):
				
				Impiccato impiccato = new Impiccato(idthread);
				return impiccato.startGame(messaggio);
				
			//------------------------------------------------------------------------------------------------------
				
		case ("/blackjack"):
			Main.log.info("Avvio gioco Blackjack per " + messaggio.from().firstName());
			boolean[][] mazzo = new boolean[13][4];
			for (int m1 = 0; m1 < 13; m1++)
				for (int m2 = 0; m2 < 4; m2++)
					mazzo[m1][m2] = false;
			int iellie = 0, igiocatore = 0;
			String[] carteellie = new String[20], cartegiocatore = new String[20];
			message = null;
			int temp;
			int numero, seme;
			numero = random.nextInt(13);
			seme = random.nextInt(4);
			// ---------------------------------------------------
			Main.sendMessage(messaggio.from().id(),
					"BLACKJACK DI ELLIE\n\n" + "Istruzioni:\n"
							+ "Il blackjack di Ellie è un blackjack semplice, si gioca con le 52 carte francesi, i giocatori sono 2, te e il banco(Ellie), lo scopo del gioco è cercare di ottenere il punteggio più alto dell'avversario senza superare 21 punti.\n"
							+ "I punti si calcolano in base al valore della carta: il 2 vale 2 punti, il 3 vale 3 punti, ecc... le figure K, Q, J valgono 10 punti, l'asso può valere o 11 punti o 1 in base alle esigenze."
							+ "All'inizio Ellie darà  due carte a se stessa (una visibile e una coperta) e due carte al giocatore, le iterazioni possibili sono:\n\n"
							+ "/carta per ottenere una carta\n" + "/stop per fermarsi e passare il turno al banco\n"
							+ "/exit per uscire dal gioco\n\n" + "INIZIAMO!");
			while (mazzo[numero][seme]) {
				numero = random.nextInt(13);
				seme = random.nextInt(4);
			}
			mazzo[numero][seme] = true;
			carteellie[iellie] = setCartaBlackjack(numero, seme, messaggio.from().id());
			iellie++;
			numero = random.nextInt(13);
			seme = random.nextInt(4);
			while (mazzo[numero][seme]) {
				numero = random.nextInt(13);
				seme = random.nextInt(4);
			}
			mazzo[numero][seme] = true;
			mazzo[numero][seme] = true;
			cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
			igiocatore++;
			numero = random.nextInt(13);
			seme = random.nextInt(4);
			while (mazzo[numero][seme]) {
				numero = random.nextInt(13);
				seme = random.nextInt(4);
			}
			mazzo[numero][seme] = true;
			mazzo[numero][seme] = true;
			cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
			igiocatore++;

			message = messaggio;
			while (!message.text().equalsIgnoreCase("/exit")) {
				Main.sendMessage(messaggio.from().id(),
						"ELLIE:\n" + "punti: " + puntiBlackjack(carteellie, iellie) + "\n"
								+ stampaCarteBlackjack(carteellie, iellie) + "\n\n\n" + "TU:\n" + "punti: "
								+ puntiBlackjack(cartegiocatore, igiocatore) + "\n"
								+ stampaCarteBlackjack(cartegiocatore, igiocatore));
				check = true;
				while (check) {
					message = attendiMessaggio();
					switch (message.text()) {
					case ("/carta"):
						numero = random.nextInt(13);
						seme = random.nextInt(4);
						while (mazzo[numero][seme]) {
							numero = random.nextInt(13);
							seme = random.nextInt(4);
						}
						mazzo[numero][seme] = true;
						mazzo[numero][seme] = true;
						cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
						igiocatore++;
						if ((temp = puntiBlackjack(cartegiocatore, igiocatore)) > 21)
							return "I tuoi punti sono: " + temp
									+ "\nHai superato i 21 punti, Ellie ha vinto\n\nBlackjack terminato";
						check = false;
						break;
					case ("/stop"):
						temp = puntiBlackjack(carteellie, iellie);
						while (temp < 17) {
							numero = random.nextInt(13);
							seme = random.nextInt(4);
							while (mazzo[numero][seme]) {
								numero = random.nextInt(13);
								seme = random.nextInt(4);
							}
							mazzo[numero][seme] = true;
							mazzo[numero][seme] = true;
							carteellie[iellie] = setCartaBlackjack(numero, seme, messaggio.from().id());
							iellie++;
							temp = puntiBlackjack(carteellie, iellie);
						}
						if (temp > 21)
							return "Ellie ha superato i 21 punti, hai vinto!!!\n❤💐♦😄♣💐♠";
						else {
							if (temp > puntiBlackjack(cartegiocatore, igiocatore))
								return "Ellie ha vinto con " + temp + " punti\n i tuoi punti sono: "
										+ puntiBlackjack(cartegiocatore, igiocatore);
							else if (temp < puntiBlackjack(cartegiocatore, igiocatore))
								return "Hai superato i " + temp
										+ " di Ellie, hai vinto!!!\n❤♦♣♠";
							else
								return "Pareggio, avete totalizzato entrambi " + temp + " punti";
						}
					case ("/exit"):

					default:
						Main.sendMessage(messaggio.from().id(),
								"Iterazione inserita non riconosciuta, reinserire\n"
										+ "/carta\nper richiedere una carta\n" + "/stop\nper fermarsi\n"
										+ "/exit\nper uscire dal gioco");
						break;
					}
				}
			}
			return "Uscita eseguita, blackjack terminato";
			
			//------------------------------------------------------------------------------------------------------
					
			case("/foto"):

				Main.sendMessage(messaggio.from().id(),
						"Che categoria di foto preferisci?\n" + "/cute		un'immagine cucciolosa\n"
								+ "/funny		un'immagine divertente\n" + "/nature		un'immagine di paesaggi\n"
								+ "/random		un'immagine casuale");
				// attendo categoria
				message = attendiMessaggio();
				InputFile photo = Photo.getImage(message.from().id(), message.text());
				if (photo == null)
					return "Ops... qualcosa è andato storto, meglio chiamare papà  :'(";
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
			default: return "Comando " + comando + " non riconosciuto, per una lista dei comandi digita /help";	
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
		return adminid;
	}
	/**Aggiunge l'id alla lista degli admin
	 * 
	 * @param ID Admin Da Aggiungere
	 */
	public void aggiungiAdmin()
	{
		adminid = true;
	}
	public void rimuoviAdmin()
	{
		adminid = false;
	}
	
	
	//------------------------------------------------------------------------------------------------------------
	//IMPICCATO
	
	
	//-----------------------------------------------------------------------------------------------------------
	//BLACKJACK
	
	private String setCartaBlackjack(int numero, int seme, Object chatid)
	{
		String s, s2;
		numero++;
		switch(numero)
		{
		case(11):
			s = "J";
			break;
		case(12):
			s = "Q";
			break;
		case(13):
			s = "K";
			break;
		default:
			s = "" + numero;
		}
		switch(seme)
		{
		case(0):
			s2 = "cuori";
			break;
		case(1):
			s2 = "quadri";
			break;
		case(2):
			s2 = "fiori";
			break;
		case(3):
			s2 = "picche";
			break;
		default :
			s2 = "cuori";
			break;
		}
		Main.sendMessage(chatid, "Ho pescato: " + s + " di " + s2);
		return s;
	}
	private String stampaCarteBlackjack(String[] carte, int icarte)
	{
		String s = new String("-  ");
		for(int i = 0; i<icarte; i++)
			s = s + carte[i] + "  -  ";
		return s;
	}
	private int puntiBlackjack(String[] carte, int icarte)
	{
		int punti = 0;
		int assi = 0;
		
		for(int i = 0; i<icarte; i++)
		{
			switch(carte[i])
			{
			case("1"):
				assi++;
				break;
			case("2"):
				punti = punti + 2;
				break;
			case("3"):
				punti = punti + 3;
				break;
			case("4"):
				punti = punti + 4;
				break;
			case("5"):
				punti = punti + 5;
				break;
			case("6"):
				punti = punti + 6;
				break;
			case("7"):
				punti = punti + 7;
				break;
			case("8"):
				punti = punti + 8;
				break;
			case("9"):
				punti = punti + 9;
				break;
			case("10"):
				punti = punti + 10;
				break;
			case("J"):
				punti = punti + 10;
				break;
			case("Q"):
				punti = punti + 10;
				break;
			case("K"):
				punti = punti + 10;
				break;
			}
		}
			if(assi>1)
			{
				punti++;
				assi--;
			}
			if(assi==1)
			{
				if(punti+11<=21)
					punti = punti+11;
				else
					punti++;
			}
		
		return punti;
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

