package bot.ellie.utils.messages;

import bot.ellie.utils.Costants;

public class Messages {

	public static final String BOT_INFO = "/-----------------------------\\"
									   + "\n| Ellie  - The Telegram Bot |\n"
									   + "\\-----------------------------/"
									   + "\n\nVersion : " + Costants.BUILD_VERSION + "\n"
									   + "Create by Martins\n"
									   + "\nEllie is a Telegram Bot programmed in Java for fun, "
									   + "the goal is to make you smile, "
									   + "have a nice day :)";
	public static final String START = "Sono sveglia ❤";
	public static final String ABORT_COMAND = "Comando annullato";
	public static final String METEO_ABORT = "Funzione meteo annullata";
	public static final String METEO_GET_CITY = "Seleziona una città  dalla lista:\n"
											  + "/Bergamo\n"
											  + "/Verona\n"
											  + "/Milano\n"
											  + "/Torino\n"
											  + "/Roma\n"
											  + "/Bari\n"
											  + "/Palermo\n"
											  + "\n/exit - comando di uscita dalla funzione";
	public static final String PHOTO_NEED_TYPE = "Che categoria di foto preferisci?\n" 
											   + "/cute		un'immagine cucciolosa\n"
											   + "/funny		un'immagine divertente\n" 
											   + "/nature		un'immagine di paesaggi\n"
											   + "/random		un'immagine casuale";
	public static final String POSTINO_NEED_DESTINATARIO = "Ok, a chi devo spedire il messaggio?"
														 + "\nDigita /exit per annullare il comando";
	public static final String POSTINO_NEED_MESSAGE = "Ok, cosa devo inviare?\nDigita /exit per annullare";
	// MYLADY Section
	public static final String MYLADY_WELCOME = "Ciao mamma 😄  sono felicissima di sentirti, "
											  + "ti ricordo che la tua lista dei comandi è /myladyhelp ❤️❤️";
	public static final String MYLADY_DISCONNECTION = "Disconnessione eseguita, ciao ciao mamma 😘❤";
	// MYLADY Section end
	//ADMIN Section
	public static final String ADMIN_WELCOME = "Papà , ti ricordo che la lista dei tuoi comandi è \" /adminhelp \" 😘";
	public static final String ADMIN_DISCONNECTION = "Privilegio da ADMIN rimosso, buona giornata papà  😘❤";
	public static final String ADMIN_SHUTDOWN_QUESTION = "Papà, sei sicuro di volermi spegnere? 😟 \n/Yep \n\n/Nope";
	
	public static final String SHUTDOWN_YEP = "Ok resto attiva 😘";
	public static final String SHUTDOWN_NOPE = "Vado a nanna 😇 \n Bye Bye";
	//ADMIN Section end
	//IMPICCATO Section
	public static final String IMPICCATO_INPUT_NOT_VALID = "Parola inserita non corretta, reinserire";
	public static final String IMPICCATO_WORD_NOT_CORRECT = "Mi dispiace, non è la parola corretta 😔, "
														  + "devo aumentare di uno gli errori";
	public static final String IMPICCATO_WIN = "COMPLIMENTI!!!\n\n Hai vinto!\n❤💐💐😄😄💐💐❤";
	public static final String IMPICCATO_END = "Gioco dell'impicato terminato";
	//IMPICCATO Section end
	//BLACKJACK Section
	public static final String BLACKJACK_LOSE_UP_21 = "\nHai superato i 21 punti, Ellie ha vinto\n\nBlackjack terminato";
	public static final String BLACKJACK_WIN_ELLIE_UP_21 = "Ellie ha superato i 21 punti, hai vinto!!!\n❤💐♦😄♣💐♠";
	public static final String BLACKJACK_END = "Comando d'uscita ricevuto \n\n Blackjack terminato";
	public static final String BLACKJACK_INVALID_INPUT = "Iterazione inserita non riconosciuta, reinserire\n"
													   + "/carta\nper richiedere una carta\n" + "/stop\nper fermarsi\n"
													   + "/exit\nper uscire dal gioco";
	//BLACKJACK Section end
	
	//BATTAGLIANAVALE Section start
	public static final String BATTAGLIANAVALE_WIN_LUCKY_FISH = "Pesce fortunato colpito, hai vinto";
	public static final String BATTAGLIANAVALE_WIN_BOATS = "Tutte le navi di Ellie abbattute, hai vinto";
	public static final String BATTAGLIANAVALE_LOSE_LUCKY_FISH =  "Ellie ha colpito il tuo pesce fortunato colpito,\n"
																+ "Ellie ha vinto";
	public static final String BATTAGLIANAVALE_LOSE_BOATS = "Ellie ha abbattuto tutte le tue navi,\nEllie ha vinto";
	
	
	
	
	
}
