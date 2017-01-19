package bot.ellie.utils.messages;

public class Errors {
	
	public static final String GENERAL_ERROR = "Qualcosa è andato storto...";
	public static final String GENERAL_ERROR2 = "Ops... qualcosa è andato storto, meglio chiamare papà  :'(";
	
	public static final String ID_BLOCKED = "Non ho piacere a parlare con te 😡😡😡😡 "
										  + "Puoi insultarmi quanto vuoi ma ignorerò i tuoi messaggi\n\n\n\n "
										  + "-Bloccato da admin-";
	public static final String ELLIE_IS_BUSY = "Ellie è veramente troppo occupata ora non posso rispondere, "
											 + "per favore prova più tardi";
	public static final String EXEC_COMAND = "Oh, qualcosa è andato storto nell'esecuzione del comando";
	public static final String RESPONSE_NOT_FOUND = "Scusami, il mio dizionario di risposte è limitato. \n"
												  + "Sono stata sviluppata per eseguire diverse e specifiche funzioni, "
												  + "dai un'occhiata a /help per vedere quali puoi usare. "
												  + "Per comodità  evita le faccine per favore";
	public static final String METEO_CITY_NOT_FOUND = "Città  non riconosciuta esco dalla funzione meteo. "
											   		+ "Utilizza /meteo per riprovate e seleziona una città  dalla lista";
	public static final String POSTINO_SYNTAX_ERROR = "Errore invio messaggio: "
									   				+ "consultare la guida su come inviare tramite il comando '/postino help'";
	public static final String POSTINO_UNKNOW_TARGET = "Errore: perdonami ma non conosco il destinatario, "
													 + "per una lista dei destinatari "
													 + "che conosco digita '/postino help'";
	//USER Section
	public static final String USER_NOT_FOUND = "Non credo di conoscerti, scusa";
	public static final String USER_NOT_LOGGED = "Scusami, ma non fai parte della lista degli user, "
											   + "dimmi chi sei tramite il comando /user";
	//USER Section end
	//MYLADY Section
	public static final String MYLADY_ALREADY_LOGGED = "Sei già in modalità mylady 😄";
	public static final String MYLADY_ERROR_LOGIN = "No, fa niente";
	public static final String MYLADY_NOT_LOGGED = "Scusami ma non hai il privilegio di mylady, accedi con /mylady";
	public static final String MYLADY_NOT_LOGGED_4_IMAGES = "Scusami, ma queste immagini sono esculsive per la mamma, "
														  + "non hai il privilegio di mylady, accedi con /mylady"; 
	//MYLADY Section end
	//ADMIN Section
	public static final String ADMIN_ALREADY_LOGGED = "Scusami, ma sei già  admin";
	public static final String ADMIN_ERROR_LOGIN = "No, fa niente";
	public static final String ADMIN_NOT_LOGGED = "Perdonami, ma non posso accontentarti. "
												+ "Non hai il privilegio da admin per questo comando";
	//ADMIN Section end
	//IMPICCATO Section
	public static final String IMPICCATO_INPUT_NOT_VALID = "Parola inserita non corretta, reinserire";
	public static final String IMPICCATO_UNKNOW_ERROR = "Errore durante il gioco dell'impiccato";
	//IMPICCATO Section end
	
	//BATTAGLIANAVALE Section start
	public static final String BATTAGLIANAVALE_INPUT_NOT_VALID = "Il testo inserito non è valido:\n"
															   + "Inserisci le coordinate (ex. B1)\n"
															   + "oppure /exit per uscire";
	public static final String BATTAGLIANAVALE_SELECTION_NOT_VALID = "La cella selezionata non è valida, seleziona una cella valida";
	//BATTAGLIANAVALE Section end
	
	
}
