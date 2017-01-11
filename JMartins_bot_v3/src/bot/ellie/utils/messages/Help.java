package bot.ellie.utils.messages;

/**
 *	Contiene i messaggi di help
 */
public class Help {
	
	public static final String HELP = "Lista d'aiuto:\n"
			+ "------------------------------------"
			+ "\n/admin\nlog in da admin\n"
			+ "\n/user\nlog in da user\n"
			+ "\n/manual\nesegue alcune funzionalità in modalità manuale\n"
			+ "\n/battuta\ncerco di alzarti un po' il morale\nper ulteriori informazioni digita /battuta help\n"
			+ "\n/perla\nscrivo una perla di saggezza\n"
			+ "\n/meteo\nprevisioni del meteo di Ellie\n"
			+ "\n/asciiart\ngenero un disegno in ASCII\n"
			+ "\n/foto\ninvia una foto\n"
			+ "\n/musica\ninvia una canzone\n"
			+ "\n/sticker\ninvia uno sticker casuale\n"
			+ "\n/ora\nti dico che ore sono\n"
			+ "\n/calc\napre menu di calcolatrice e generazione numeri casuali\n"
			+ "\n/dado\nlancio un dado(ti dico un valore casuale tra 1 e 6)\n"
			+ "\n/moneta\nlancio una moneta(ti dico testa o croce in maniera casuale)\n"
			+ "\n/shared\naccedi ai files condivisi in cloud\n"
			+ "\n/impiccato\ngioca al gioco dell'impiccato\n"
			+ "\n/blackjack\ngioca a blackjack con Ellie\n"
			+ "\n/personalinfo\nvisualizza informazioni personali\n"
			+ "\n/botinfo\nvisualizza informazioni del bot\n"
			+ "------------------------------------";
	
	public static final String ADMIN_HELP = "Lista:\n"
			+ "/hddlist\nvisualizza lista degli HDD "
			+ "/postino\ninvio un messaggio ad un utente, sintassi da seguire:\n/postino 'id destinatario' 'testo del messaggio da inviare'\n"
			+ "/system\ninvio un messaggio pulito a un utente\n"
			+ "/adminimage\ninvio un'immagine delle tue...\n"
			+ "/threadattivi\nvisualizzo i thread attivi\n"
			+ "/cloud\nmodalità di clouding\n"
			+ "/shutdown\nspegnimento di Ellie\n"
			+ "/adminexit\nesci dalla lista degli admin ";
	
	public static final String MYLADY_HELP = "Lista:\n"
			+ "/postino\ninvio un messaggio ad un utente, sintassi da seguire:\n/postino 'id destinatario' 'testo del messaggio da inviare'\n"
			+ "/myladyimage\ninvia un'immagine delce quanto la mamma\n"
			+ "/myladyexit\nesci dalla modalità mamma\n";
	
	public static final String USER_HELP = "Lista dei comandi da utente:\n"
			+ "\n/postino\nInvia un messaggio ad  un utente, sintassi da seguire:\n/postino 'id destinatario' 'testo del messaggio da inviare'\n"
			+ "\n/cloud\nAccedi al tuo cloud, un'area riservata per i tuoi file gestita da Ellie\n"
			+ "\n/userexit\nesci dalla lista degli utenti";
	
	public static final String CLOUD_HELP = ">.::Help::.\n"
			+ "get\n   download a file\n"
			+ "getall\n   download all file in current dir\n"
			+ "ls\n   list of files\n"
			+ "mkdir\n   create a new folder\n"
			+ "rename\n   rename a file or directory\n"
			+ "rm\n   delete a file or a folder\n"
			+ "send\n   upload a file\n";
	public static final String IMPICCATO_HELP = "ISTRUZIONI:\n\n"
			+ "Gioca all'impiccato con Ellie!\n"
			+ "Ellie sceglie casualmente una parola e te devi indovinarla.\n"
			+ "Puoi provare a dare una lettera, se la lettera è nella parola scelta allora comparirà a schermo, in caso contrario verrà  aumentata la figura dell'impiccato, sbagliando 8 volte il gioco finirà  in Game Over.\n"
			+ "\nPer uscire dal gioco usa il comando /exit.\n"
			+ "\nSe pensi di poter indovinare la parola usa il comando /parola 'parola da indovinare'\n\n"
			+ "INIZIAMO!";
	public static final String BLACKJACK_HELP = "BLACKJACK DI ELLIE\n\n" + "Istruzioni:\n"
			+ "Il blackjack di Ellie è un blackjack semplice, si gioca con le 52 carte francesi, i giocatori sono 2, te e il banco(Ellie), lo scopo del gioco è cercare di ottenere il punteggio più alto dell'avversario senza superare 21 punti.\n"
			+ "I punti si calcolano in base al valore della carta: il 2 vale 2 punti, il 3 vale 3 punti, ecc... le figure K, Q, J valgono 10 punti, l'asso può valere o 11 punti o 1 in base alle esigenze."
			+ "All'inizio Ellie darà  due carte a se stessa (una visibile e una coperta) e due carte al giocatore, le iterazioni possibili sono:\n\n"
			+ "/carta per ottenere una carta\n" + "/stop per fermarsi e passare il turno al banco\n"
			+ "/exit per uscire dal gioco\n\n" + "INIZIAMO!";

}
