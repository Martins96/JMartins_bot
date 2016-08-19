package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InputFile;

import bot.ellie.BotThread;
import bot.ellie.ErrorReporter;
import bot.ellie.Main;

public class Manual {
	
	private int idthread;
	private int idUser;
	
	public Manual(int idthread, int idUser) {
		this.idthread = idthread;
		this.idUser = idUser;
	}
	
	/**
	 * @param param Array del messaggio in arrivo, al posto [0] deve esserci /manuale
	 * @return end of this method
	 */
	public String startManualMode(String[] param) {
		
		String comando;
		
		if(param.length > 1 && param[1] != null) {
			comando = param[1];
		} else {
			comando = attendiMessaggio("Ok, che comando devo eseguire in modalità manuale?");
		}
		if(comando != null && comando.length()>0) {
			if(!comando.substring(0, 1).equals("/"))
				comando = "/" + comando;
		
		return executeComand(comando);
		
		}
		return "Fine modalità manuale";
	
	}
	
	private String executeComand(String comand) {
		switch(comand.toLowerCase()) {
		case "/foto":
			foto();
			return BotThread.ANNULLA_SPEDIZIONE_MESSAGGIO;
		case "/musica":
			musica();
			return BotThread.ANNULLA_SPEDIZIONE_MESSAGGIO;
		case "/battuta":
			return battuta();
		case "/sticker":
			sticker();
			return BotThread.ANNULLA_SPEDIZIONE_MESSAGGIO;
		case "/exit":
			return "Comando manuale annullato";
		
		default: return "Comando " + comand + " non riconosciuto o non eseguibile in modalità manuale";	
		}
		
		
	}
	
	
	
	//-----------------------------------------------------------------------------
	
	private void foto() {
		String lista = new String();
		File folder = new File(Main.PATH_INSTALLAZIONE + "/readfiles/photo/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// trasformo bytes in KB o MB se sono tanti
				long size = listOfFiles[i].length();
				String valuta = " bytes";
				if (size > 2048) { // KB
					size = size / 1024;
					valuta = "KB";
					if (size > 1024) { // MB
						size = size / 1024;
						valuta = "MB";
						if (size > 1024) { // GB
							size = size / 1024;
							valuta = "GB";
						}
					}
				}
				lista = lista + listOfFiles[i].getName() + "	-	" + size + valuta + "\n";
			}
		}
		String pathFoto = attendiMessaggio("Lista dei file:\n" + lista + "\n\nChe file desideri?");

		File file = new File(Main.PATH_INSTALLAZIONE + "/readfiles/photo/" + pathFoto);
		if (file.exists()) {
			Main.log.info("Foto inviata: " + pathFoto);
			Main.sendPhoto(idUser, new InputFile("jpg", file));
		} else {
			Main.log.info("Foto non trovata");
			sendMessage("Non ho trovato il file specificato, annullo il comando manuale");
		}
	}
	
	private void musica() {
		String lista = new String();
		File folder = new File(Main.PATH_INSTALLAZIONE + "/readfiles/music/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// trasformo bytes in KB o MB se sono tanti
				long size = listOfFiles[i].length();
				String valuta = " bytes";
				if (size > 2048) { // KB
					size = size / 1024;
					valuta = "KB";
					if (size > 1024) { // MB
						size = size / 1024;
						valuta = "MB";
						if (size > 1024) { // GB
							size = size / 1024;
							valuta = "GB";
						}
					}
				}
				lista = lista + listOfFiles[i].getName() + "	-	" + size + valuta + "\n";
			}
		}
		String pathMusica = attendiMessaggio("Lista dei file:\n" + lista + "\n\nChe file desideri?");

		File file = new File(Main.PATH_INSTALLAZIONE + "/readfiles/music/" + pathMusica);
		if (file.exists()) {
			Main.log.info("Foto inviata: " + pathMusica);
			Main.sendAudio(idUser, new InputFile("mp3", file));
		} else {
			Main.log.info("Foto non trovata");
			sendMessage("Non ho trovato il file specificato, annullo il comando manuale");
		}
	}
	
	private void sticker() {
		try {
			int i = Integer.parseInt(attendiMessaggio("Ok, che numero di sticker vuoi? MAX: " + Sticker.N_STICKERS));
			if(i>Sticker.N_STICKERS || i<0)
				sendMessage("Valore inserito non ammesso");
			else 
				Main.sendSticker(idUser, new Sticker(i).getSticker());
		} catch (NumberFormatException e) {
			sendMessage("Valore inserito non corretto");
		}
	}
	
	private String battuta() {
		
		try {
		int i = Integer.parseInt(attendiMessaggio("Ok, quale battuta vuoi, MAX:" + Battuta.N_BATTUTE));
		if(i > Battuta.N_BATTUTE || i<0)
			return "Valore inserito non ammesso";
		
		String file = Main.PATH_INSTALLAZIONE + "/readfiles/battute.txt";
		
		FileReader f;
	    f=new FileReader(file);
		    BufferedReader b;
	    b=new BufferedReader(f);
	    String battuta = b.readLine();
	    for(int j = 1; i > j; j++)
	    {
	    	battuta = b.readLine();
	    }
    	battuta = b.readLine();
    	b.close();
    	f.close();
		return battuta;
		} catch(NumberFormatException e) {
			return "Numero inserito non valido";
		} catch (FileNotFoundException e1) {
			Main.log.error("File not found for battute");
			ErrorReporter.sendError("File not found for battute");
		} catch(IOException e2) {
			Main.log.error("Errore in battute manuale", e2);
			ErrorReporter.sendError("Errore in battute manuale", e2);
		}
		return "Scusami ma ho qualche problema con la funzione, meglio chiamare papà";
	}
	
	//-----------------------------------------------------------------------------
	private void sendMessage(String text) {
		Main.sendMessage(idUser, text);
	}
	
	private String attendiMessaggio(String text) {
		sendMessage(text);
		do {
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
		} while (Main.botThread[idthread].message.text() == null); //continuo finché non ricevo un mex di testo
		return Main.botThread[idthread].message.text();
	}
}
