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
import bot.ellie.utils.*;



public class Risposta {
	
	boolean adminid = false;
	String user;
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
		user = null;
		
		
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
			if(!(ioMiChiamo = ControlliMessaggiRicevuti.ioMiChiamo(messaggio)).equals("")) {
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
					if(!comandoDaFrase.equals(""))
					{
						testoMessaggio = comandoDaFrase;
					}
				}
		
		
		if(testoMessaggio.length()>1)
			if(testoMessaggio.substring(0,1).equals("\\"))
			{
				testoMessaggio = "/" + testoMessaggio.substring(1, testoMessaggio.length());
			}
		
		// eseguo comando
		if(testoMessaggio.substring(0,1).equals("/"))
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
		
		String comando = new String("");
		//trovo la substring del comando ignorando il testo dopo lo spazio
		for (int j=0; messaggio.text().length() > j; j++)
		{
			if (messaggio.text().substring(j, j+1).equals(" "))
				break;
			comando = comando + messaggio.text().substring(j, j+1);
		}
		//sistemo maiuscole in minuscole
		comando = comando.toLowerCase();
		//sistemo /* per funzione /calc
		if(comando.equals("/*"))
			comando = new String("/x");
		if(comando.equals("//"))
			comando = new String("/:");
		if(comando.equals("/dado"))
		{
			//come se tirasse un dado
			int dado = random.nextInt(6) + 1;
			return "" + dado;
		}
		
		switch(comando)
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
				
				return Battuta.comandoBattuta(messaggio);
				
				
		//--------------------------------------------------------------------------------------------------------
			case("/perla"):
				
				return Perla.comandoPerla();
				
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
				if(controllaUser() != null)
				{
					Main.log.warn(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato nuovamente l'accesso user.");
					text = "Scusami, ma sei già  nella lista degli user, " + controllaUser();
				}
				else
				{
					
					Main.sendMessage(messaggio.from().id(), "Ciao, chi sei?");
					Main.botThread[idthread].message = new Message();
					message = attendiMessaggio();
					
					text = aggiungiUser(message.text(), message.from().id());
					if(text.equals("Non credo di conoscerti, scusa"))
					{
						Main.log.info("ACCESSO USER NEGATO A: " + message.from().username() + " - id(" + message.from().id() + ")");
					}
					else
					{
						Main.log.info("ACCESSO USER CONSENTITO A: " + message.from().username() + " - id(" + message.from().id() + ")");
					}
				}
				return text;
			
		case("/userhelp"):
			long id = messaggio.from().id();
			if(controllaUser()== null)
			{
				Main.log.info(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'uscita user non facendone parte");
				return "Scusami, ma non fai parte della lista degli user";
			}
			else
				return Help.USER_HELP;
		
		case("/userexit"):
			id = messaggio.from().id();
			if(controllaUser() == null)
			{
				Main.log.info(messaggio.from().username() + "(" + messaggio.from().id() + ") ha tentato l'uscita user non facendone parte");
				text = "Scusami, ma non fai parte della lista degli user";
			}
			else
			{
				text = rimuoviUser(id);
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
				else
				{
					boolean accesso = false;
					Main.sendMessage(messaggio.from().id(), "Martins? Sei tu?");
					message = attendiMessaggio();
					// messaggio ricevuto
					accesso = autenticazione(message.text());
					if(accesso)
					{
						Main.log.warn("ACCESSO ADMIN CONSENTITO A: " + message.from().username() + " - id(" + message.from().id() + ")");
						Main.sendMessage(message.from().id(), "Papà ! ❤😍😘❤");
						text = "Papà , ti ricordo che la lista dei tuoi comandi è \" /adminhelp \" 😘";
						aggiungiAdmin();	
					}
					else
					{
						Main.log.warn("ACCESSO ADMIN NEGATO A: " + message.from().username() + " - id(" + message.from().id() + ")");
						text = "No, fa niente";
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
					if(m.text().equals("/Yep")) {
						shutdownRisp = "Vado a nanna 😇 \n Bye Bye";
						Shutdown.comandoShutdown();
					}
					
				}
				return shutdownRisp;
				
		//---------------------------------------------------------------------------------------------------------------	
				
			case("/cloud"):
//			if(controllaAdmin()){
				Main.sendMessage(messaggio.from().id(), "Avvio modalità clouding...");
				Main.log.info(messaggio.from().firstName() + messaggio.from().lastName() + messaggio.from().username() + ""
						+ "(" + messaggio.from().id() + ") ENTRA IN MODALITA' CLOUD");
				
				Cloud cloudModality = new Cloud(messaggio.from().id(), idthread);
				cloudModality.startCloudModality();
//			} else {
//				return "Non hai i privilegi necessari per questa funzione, accedi tramite /admin";
//			}
				return "Fine modalità clouding";
				
		//---------------------------------------------------------------------------------------------------------------
			case("/postino"):
			
				message = new Message();
				String esitopostino = new String("Errore invio messaggio: consultare la guida su come inviare tramite il comando '/postino help'");
				Postino postino = new Postino();
				String destinatario = "", testomex = "", mittente = "";
				check = false;
					mittente = controllaUser();
					if(mittente == null)
					{
						check = controllaAdmin();
					}
					else
						check = true;
					if(check)
					{
						//sistemo destinatario e testomex
						for (int j=0; messaggio.text().length() > j; j++)
						{
							if (messaggio.text().substring(j, j+1).equals(" "))
							{
								j++;
								while (messaggio.text().length() > j)
								{
									if (messaggio.text().substring(j, j+1).equals(" "))
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
						if (destinatario.equals(""))
						{
							Main.sendMessage(messaggio.from().id(), "Ok, a chi devo spedire il messaggio?\nDigita /exit per annullare il comando");
							message = attendiMessaggio();
							//messaggio in arrivo
							if(message.text() != null)
							{
									System.out.println("destinatario: " + destinatario);
									destinatario = message.text().toLowerCase();
							}
							if (destinatario.equals("/exit"))
								return "Comando annullato";
						}
						else if (destinatario.equals("help"))
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
						if (testomex.equals("/exit"))
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
						if (messaggio.text().substring(j, j+1).equals(" "))
						{
							j++;
							while (messaggio.text().length() > j)
							{
								if (messaggio.text().substring(j, j+1).equals(" "))
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
					if (destinatario.equals(""))
						return "Errore invio messaggio: consultare la guida su come inviare tramite il comando '/postino help'";
					else if (destinatario.equals("help"))
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
				if (meteo.equals("error")) {
					return "Città  non riconosciuta esco dalla funzione meteo. Utilizza /meteo per riprovate e seleziona una città  dalla lista";
				}
				if (meteo.equals("uscita")) {
					return "Funzione meteo annullata";
				} // input OK
				return meteo;
			
			//------------------------------------------------------------------------------------------------------
			
			case("/storia"):
				return "Funzione in via di sviluppo...";
			
			//------------------------------------------------------------------------------------------------------	
			
			case("/impiccato"):
				Main.log.info("Avvio gioco dell'impiccato per: " + messaggio.from().firstName());
				//Carico variabili di gioco
				String parola = generaParolaImpiccato();
				int lunghezzaparola = parola.length();
				char[] parolaavideo = new char[lunghezzaparola];
				//azzero array
				for(i = 0; i < lunghezzaparola; i++)
					parolaavideo[i] = '_';
				short fail = 0;
				boolean flag = false;
				
				Main.sendMessage(messaggio.from().id(), "ISTRUZIONI:\n\n"
										 + "Gioca all'impiccato con Ellie!\n"
										 + "Ellie sceglie casualmente una parola e te devi indovinarla.\n"
										 + "Puoi provare a dare una lettera, se la lettera è nella parola scelta allora comparirà a schermo, in caso contrario verrà  aumentata la figura dell'impiccato, sbagliando 8 volte il gioco finirà  in Game Over.\n"
										 + "\nPer uscire dal gioco usa il comando /exit.\n"
										 + "\nSe pensi di poter indovinare la parola usa il comando /parola 'parola da indovinare'\n\n"
										 + "INIZIAMO!");
				message = messaggio;
				while(!message.text().equals("/exit"))
				{
					Main.sendMessage(messaggio.from().id(), generaFiguraImpiccato(fail) + "\n\n\nerrori commessi:" + fail);
					Main.sendMessage(messaggio.from().id(), "Parola da indovinare:\n" + stampaParola(parolaavideo));
					if(fail >= 8)
						return "GAME OVER\n\nLa parola da indovinare era: " + parola + "\nGioco dell'impiccato terminato";
					message = attendiMessaggio();
					//AVVIO GIOCO
					if(message.text().length() == 1)
					{
						char lettera = message.text().toUpperCase().charAt(0);
						for(int j = 0; j<lunghezzaparola;j++)
						{
							if(lettera == parola.charAt(j))
							{
								parolaavideo[j] = lettera;
								flag=true;
							}
						}
						if(flag) {
							Main.sendMessage(messaggio.from().id(), "La lettera " + lettera + " è presente nella parola 😊");
							// Controllo se la parola da indovinare ha ancora "_"
							boolean bool = true;
							for(int j = 0; j<lunghezzaparola;j++) {
								if(parolaavideo[j] == '_')
										bool=false;
							}
							if(bool) {
								return "COMPLIMENTI!!!\n\n Hai vinto!\n❤💐💐😄😄💐💐❤";
							}
						}
						else
						{
							Main.sendMessage(messaggio.from().id(), "La lettera " + lettera + " non è presente nella parola 😔 Il numero di errori è aumentato di uno");
							fail++;
						}
							flag = false;
					} else {
						if(message.text().length() < 7) {
							if(message.text().equals("/exit"))
								return "Gioco dell'impicato terminato";
							else
								Main.sendMessage(messaggio.from().id(), "Parola inserita non corretta, reinserire");
						}
						else
						{
							if(message.text().substring(0, 7).equals("/parola"))
							{
								if(message.text().length() < 9)
									Main.sendMessage(messaggio.from().id(), "Parola inserita non corretta, reinserire");
								else
								{
									String soluzione = new String(message.text().substring(8, message.text().length()));
									soluzione = soluzione.toUpperCase();
									if(soluzione.equals(parola))
									{
										return "COMPLIMENTI!!!\n\n Hai vinto!\n❤💐💐😄😄💐💐❤";
									}
									else
									{
										Main.sendMessage(messaggio.from().id(), "Mi dispiace, non è la parola corretta 😔, devo aumentare di uno gli errori");
										fail++;
									}
								}
							}
							else
								Main.sendMessage(messaggio.from().id(), "Parola inserita non corretta, reinserire");
						}
					}
				}
				return "Gioco dell'impicato terminato";
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
			while (!message.text().equals("/exit")) {
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
				    if (line.equals(word))
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
			    if (line.equals(word)) {
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
	/**aggiunge l'id alla lista utenti in base alla password inserita
	 * 
	 * @param password
	 * @param id
	 * @return esito
	 */
	public String aggiungiUser(String password, long id)
	{
		switch(password)
		{
		case("Scoiattolo123"): //accesso Gaia
			user = "Gaia";
		    return "Hey, ciao Gaia, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		case("Alesnap123"): //accesso Ale
			user = "Ale";
			return "Hey, ciao Ale, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		case("Ciaolol")://accesso Matte
			user = "Matte";
			return "Hey, ciao Matte, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		case("Tumama69")://accesso Dinu
			user = "Dinu";
			return "Hey, ciao Alberto, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Jamal")://accesso Vale
			user = "Vale";
			return "Hey, ciao Vale, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Spongebob32")://accesso Pol
			user = "Pol";
			return "Hey, ciao Pol, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		case("Ginseng")://Accesso Andrea
			user = "Andrea";
			return "Hey, ciao Andrea! sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		}
		return "Non credo di conoscerti, scusa";
	}
	/**controlla se l'id inserito è nella lista degli user
	 * 
	 * @param  id
	 * @return nome utente dell'id inserito se trovato, altrimenti ritorna "null"
	 */
 	public String controllaUser()
	{
		return user;
	}	
	
	public String rimuoviUser(long userdarimuovere)
	{
		if(user != null) {
			String s = user;
			user = null;
			return "Privilegio da USER rimosso, buona giornata " + s + " 😊";
		} else {
		return "Il tuo ID non è stato trovato nella lista degli user";
		}
	}
	
	
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
	private String generaParolaImpiccato()
	{
		int riga = 0;
		//numero righe del file impiccato
		riga = random.nextInt(60);
		try
		{
			FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/impiccato.txt");
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
			fr.close();
			return line;
		}
		catch(IOException e)
		{
			Main.log.error("\nERRORE: File per il gioco dell'impicato non trovato\n\n");
			ErrorReporter.sendError("ERRORE: File per il gioco dell'impicato non trovato", e);
			return "Errore durante il gioco dell'impiccato";
		}
	}
	//lettura figura dell'impiccato
	private String generaFiguraImpiccato(int i)
	{
		String s = new String("");
		try
		{
			FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/impiccato1.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
		    String t = new String("");
		    int j = i*8+1;
		    while ((s = lnr.readLine ()) != null)
			{
			    if (lnr.getLineNumber () == j)
			    {
				    for(i = (i+1)*8; j<=i; j++)
				    {
				    	s = s + t + "\n";
				    	t = lnr.readLine();
				    }
				    break;
			    }
			}
		    lnr.close();
		    fr.close();
		}
		catch (IOException e)
		{
			Main.log.error("ERRORE: file impiccato1.txt non trovato");
			ErrorReporter.sendError("ERRORE: file impiccato1.txt non trovato", e);
			return "Errore nel gioco dell'impiccato";
		}
		return s;
	}
	private String stampaParola(char[] parola)
	{
		String s = new String("");
		for(int i = 0; i < parola.length; i++)
			s = s + parola[i] + "  ";
		return s;
	}
	
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Main.log.error("Errore sync del Thread");
				ErrorReporter.sendError("Errore sync del Thread", e);
				e.printStackTrace();
			}
		}
		return Main.botThread[idthread].message;
	}
}

