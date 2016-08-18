package bot.ellie.comands;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;

public class Impiccato {
	
	private Random random;
	private int idthread;
	
	public Impiccato(int idthread) {
		random = new Random();
		this.idthread = idthread;
	}
	
	/**
	 * avvio gioco dell'impicato
	 * @param messaggio
	 * @return messaggio di fine gioco
	 */
	public String startGame(Message messaggio) {
		Main.log.info("Avvio gioco dell'impiccato per: " + messaggio.from().firstName());
		//Carico variabili di gioco
		String parola = generaParolaImpiccato();
		int lunghezzaparola = parola.length();
		char[] parolaavideo = new char[lunghezzaparola];
		//azzero array
		for(int i = 0; i < lunghezzaparola; i++)
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
		Message message = messaggio;
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
	}
	
	
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
	
	private Message attendiMessaggio() {
		Message emptyMessage = new Message();
		do {
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
		} while(Main.botThread[idthread].message.text() == null);
		return Main.botThread[idthread].message;
	}
}
