package bot.ellie.comands.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;
import bot.ellie.utils.events.MessaggiCasualiEvent;

public class StartRandomEvent {
	
	private Message message;
	private int idthread;
	
	public StartRandomEvent(Message message, int idthread) {
		this.message = message;
		this.idthread = idthread;
	}
	
	public String startMethod() {
		do {
			String selection = attendiMessaggio(  "Ok, papà che evento vuoi avviare?\n"
												+ "/testo\n"
												+ "/foto\n"
												+ "/video\n"
												+ "/gif\n"
												+ "/exit\n");
			int n;
			switch(selection) {
			case "/testo":
				try {
					n = getNumber4Text(); //prendo indice della riga da inviare come evento casuale testo
					if(n==0)
						return "Comando annullato";
					MessaggiCasualiEvent.startTextRandomEvent(n); //avvio evento casuale in modo manuale
				} catch (IOException e) {
					Main.log.error(e);
					ErrorReporter.sendError("Errore avvio evento casuale manuale", e);
					return "Comando annullato";
				}
				return "Avviato evento casuale per testo";
			case "/foto":
				n = getNumber4Image(); //prendo numero dell'immagine del inviare come evento casuale testo
				if(n==0)
					return "Comando annullato";
				MessaggiCasualiEvent.startImageRandomEvent(n);; //avvio evento casuale in modo manuale
				return "Avviato evento casuale per foto";
			case "/video":
				n = getNumber4Video(); //prendo numero del video da inviare come evento casuale testo
				if(n==0)
					return "Comando annullato";
				MessaggiCasualiEvent.startVideoRandomEvent(n); //avvio evento casuale in modo manuale
				return "Avviato evento casuale per video";
			case "/gif":
				n = getNumber4Gif(); //prendo numero della gif da inviare come evento casuale testo
				if(n==0)
					return "Comando annullato";
				MessaggiCasualiEvent.startGifRandomEvent(n); //avvio evento casuale in modo manuale
				return "Avviato evento casuale per gif";
			case "/exit":
				return "Uscita dalla funzione eseguita";
			default:
				sendMessage("Input non corretto, dai papà...");
			}
		} while(true);
	}
	
	
	
	
	
	//---------------------------------------------------------------------
	
	private int getNumber4Text() {
		do {
			String s = attendiMessaggio("Quale testo vuoi inviare?\n"
							+ "Il numero massimo di testi che ho sono: " + Costants.N_RANDOM_TEXT
							+ "\nCon 0 annullo il comando"
							+ "\nTi faccio vedere l'anteprima non ti preoccupare papà ❤");
			try {
				int n = Integer.parseInt(s);
				if(n <= Costants.N_RANDOM_TEXT) {
					if(n==0)
						return n;
					String ok = attendiMessaggio(testoRandomMessage(n) 
							+"\n\nOk?\n/si\n/no"); //Anteprima e conferma
					if(ok.equals("/si"))
						return n;
				}
				sendMessage("Input non corretto");
			} catch (NumberFormatException e) {
				if(s.equals("exit"))
					return 0;
				sendMessage("Input non corretto");
			}
		} while (true);
	}
	
	private int getNumber4Image() {
		do {
			String s = attendiMessaggio("Quale immagine vuoi inviare?\n"
							+ "Il numero massimo di immagini che ho sono: " + Costants.N_RANDOM_IMAGE
							+ "\nCon 0 annullo il comando"
							+ "\nTi faccio vedere l'anteprima non ti preoccupare papà ❤");
			try {
				int n = Integer.parseInt(s);
				if(n <= Costants.N_RANDOM_IMAGE) {
					if(n==0)
						return n;
					sendImage(Main.PATH_INSTALLAZIONE + "/readfiles/photo/randomSend/image" + n + ".jpg");
					String ok = attendiMessaggio("\n\nOk?\n/si\n/no");
					if(ok.equals("/si"))
						return n;
					
				}
				sendMessage("Input non corretto");
			} catch (NumberFormatException e) {
				if(s.equals("exit"))
					return 0;
				sendMessage("Input non corretto");
			}
		} while (true);
	}
	
	private int getNumber4Video() {
		do {
			String s = attendiMessaggio("Quale Video vuoi inviare?\n"
							+ "Il numero massimo di video che ho sono: " + Costants.N_RANDOM_VIDEO
							+ "\nCon 0 annullo il comando"
							+ "\nTi faccio vedere l'anteprima non ti preoccupare papà ❤");
			try {
				int n = Integer.parseInt(s);
				if(n <= Costants.N_RANDOM_VIDEO) {
					if(n==0)
						return n;
					sendImage(Main.PATH_INSTALLAZIONE + "/readfiles/video/randomSend/video" + n + ".mp4");
					String ok = attendiMessaggio("\n\nOk?\n/si\n/no");
					if(ok.equals("/si"))
						return n;
					
				}
				sendMessage("Input non corretto");
			} catch (NumberFormatException e) {
				if(s.equals("exit"))
					return 0;
				sendMessage("Input non corretto");
			}
		} while (true);
	}
	
	private int getNumber4Gif() {
		do {
			String s = attendiMessaggio("Quale immagine vuoi inviare?\n"
							+ "Il numero massimo di immagini che ho sono: " + Costants.N_RANDOM_GIF
							+ "\nCon 0 annullo il comando"
							+ "\nTi faccio vedere l'anteprima non ti preoccupare papà ❤");
			try {
				int n = Integer.parseInt(s);
				if(n <= Costants.N_RANDOM_GIF) {
					if(n==0)
						return n;
					sendGif(Main.PATH_INSTALLAZIONE + "/readfiles/photo/randomSend/gif/image" + n + ".gif");
					String ok = attendiMessaggio("\n\nOk?\n/si\n/no");
					if(ok.equals("/si"))
						return n;
					
				}
				sendMessage("Input non corretto");
			} catch (NumberFormatException e) {
				if(s.equals("exit"))
					return 0;
				sendMessage("Input non corretto");
			}
		} while (true);
	}
	
	//---------------------------------------------------------------------
	
	private String testoRandomMessage(int i) {
		LineNumberReader lnr = null;
		try {
			lnr = new LineNumberReader(
						new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/messaggicasuali.txt"));
			String line;
			while ((line = lnr.readLine()) != null) {
				if (lnr.getLineNumber() == i) {
					return line;
				}
			}
			lnr.close();
		} catch (FileNotFoundException e) {
			Main.log.error(e);
		} catch (IOException e) {
			Main.log.error(e);
		} finally {
			try {
				lnr.close();
			} catch (IOException e) {
				Main.log.error(e);
			}
		}
		return "";
	}
	
	
	private String attendiMessaggio(String stringa) {
		if(stringa != null)
			sendMessage(stringa);
		return Getter.attendiMessaggio(idthread);
	}
	
	private void sendMessage(String text) {
		Sender.sendMessage(message.from().id(), text);
	}
	
	private void sendImage(String text) {
		Sender.sendPhoto(message.from().id(), new File(text));
	}
	
	@SuppressWarnings("unused")
	private void sendVideo(String text) {
		Sender.sendVideo(message.from().id(), new File(text));
	}
	
	private void sendGif(String text) {
		Sender.sendDocument(message.from().id(), new File(text));
	}
}
