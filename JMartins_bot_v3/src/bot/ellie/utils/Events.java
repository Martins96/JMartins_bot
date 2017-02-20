package bot.ellie.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.comands.Meteo;

public class Events {
	

	private static Date date = new Date();
	
	/**
	 * invio messaggi evento, controllo un nuovo evento ogni minuto
	 */
	public static void controllaEvento(String eventodate) {	
		
		//meteo di fine settimana
		DateFormat dateFormatWeekendMeteo = new SimpleDateFormat("u HH:mm");
		String weekendmeteo = new String(dateFormatWeekendMeteo.format(date)); //5 15:00    5 = Friday
		if(weekendmeteo.equalsIgnoreCase(Costants.ORA_DEL_WEEKEND_METEO)){
			
			String meteo = Meteo.getWeekendMeteo("/Bergamo");
			Main.log.info("Evento avviato (Meteo del WeekEnd): " + "\nDestinatario: " + "Martins" + " ( 115949778 )\n" + meteo + "\n");
			Main.sendMessage(115949778, meteo);
			Main.log.info("Evento avviato (Meteo del WeekEnd): " + "\nDestinatario: " + "Mamma" + " ( 240650708 )\n" + meteo + "\n");
			Main.sendMessage(240650708, meteo);
			Main.log.info("Messaggio spedito in data: " + eventodate);
			
		}
		
		
		//evento tramite file
		try {
			FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/eventi.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;
			while ((line = lnr.readLine ()) != null)
			{
			    if (line.equals(eventodate))
			    {
			    	String destinatario = lnr.readLine();
			    	String mex = lnr.readLine();
			    	//compleanno ellie
			    	if(mex.equalsIgnoreCase(Costants.COMPLEANNO_ELLIE_MEX)){
			    		mex = CompleannoEllie.getAuguriDiEllie();
			    	}
			    	long iddestinatario = 0;
			    	
			    	//seleziono destinatario
			    	switch(destinatario)
			    	{
			    	case("Martins"):
			    		iddestinatario = 115949778;
			    	break;
			    	case("Mamma"):
			    		iddestinatario = 240650708;
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
			    	case("A<3L"):
			    		Main.log.info("LOVE EVENT");
			    		iddestinatario = 115949778; //PAPA'
			    		Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Martins" + " (" + iddestinatario + ")\n" + mex + "\n");
			    		Main.sendMessage(iddestinatario, mex);
			    		Main.log.info("Messaggio spedito in data: " + eventodate);
						
						iddestinatario = 240650708; //MAMMA
						Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Mamma" + " (" + iddestinatario + ")\n" + mex + "\n");
						Main.sendMessage(iddestinatario, mex);
						Main.log.info("Messaggio spedito in data: " + eventodate);
			    	return;
			    	case("All"):
				    	{
				    		//broadcast
			    			Main.log.info("EVENT BROADCAST");
				    		//inserire manualmente gli id
				    		iddestinatario = 115949778;
				    		Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Martins" + " (" + iddestinatario + ")\n" + mex + "\n");
				    		Main.sendMessage(iddestinatario, mex);
				    		Main.log.info("Messaggio spedito in data: " + eventodate);
							
							iddestinatario = 240650708;
							Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Mamma" + " (" + iddestinatario + ")\n" + mex + "\n");
							Main.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito in data: " + eventodate);
							
							iddestinatario = 124796388;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Gaia" + " (" + iddestinatario + ")\n" + mex + "\n");
							Main.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
							
							iddestinatario = 143009205;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Ale" + " (" + iddestinatario + ")\n" + mex + "\n");
							Main.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
							
							iddestinatario = 76891271;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Matte" + " (" + iddestinatario + ")\n" + mex + "\n");
							Main.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
							
							iddestinatario = 104412734;
							System.out.println("Evento avviato: " + line + "\nDestinatario: " + "Valentino" + " (" + iddestinatario + ")\n" + mex + "\n");
							Main.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
							
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
			    	Main.log.info("Evento avviato: " + line + "\nDestinatario: " + destinatario + " (" + iddestinatario + ")\n" + mex + "\n");
			    	Main.sendMessage(iddestinatario, mex);
			    	Main.log.info("Messaggio spedito in data: " + eventodate);
			    	fr.close();
			    	lnr.close();
			    	return;
			    }
			}
			lnr.close();
		} catch (IOException e) {
			Main.log.error("Errore gestione eventi");
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
				//carico variabile casuale per messaggi random - randomsend
				Random random = new Random();
				int i = random.nextInt(1600);
				if(i == 88) {
					switch (random.nextInt(4)) {
					case 0:
						i = random.nextInt(Costants.N_RANDOM_TEXT);
						startTextRandomEvent(i);
						return;

					case 1:
						// send an image
						{
							int n = random.nextInt(Costants.N_RANDOM_IMAGE);
							startImageRandomEvent(n);
						}
						return;
						
					case 2:
						// send a gif
						{
							int n = random.nextInt(Costants.N_RANDOM_GIF);
							startGifRandomEvent(n);
						}
						return;
						
					case 3:
						// send a video
						{
							int n = random.nextInt(Costants.N_RANDOM_VIDEO);
							startVideoRandomEvent(n);
						}
						return;
					}
				}		
			} else 
				return;
		} catch (IOException e) {
			Main.log.error("Errore lettura file messaggi casuali\n", e);
			ErrorReporter.sendError("Errore lettura file messaggi casuali\n", e);
		}
	}
	
	public static void startTextRandomEvent(int i) throws IOException{
		LineNumberReader lnr = new LineNumberReader(
				new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/messaggicasuali.txt"));
		String line;
		while ((line = lnr.readLine()) != null) {
			if (lnr.getLineNumber() == i) {
				Main.log.info("Messaggio casuale riga " + i + " inviato: " + line
						+ "\nDestinatario: Martins(115949778)\n");
				Main.sendMessage(115949778, line);
				// --------------------------------------
				Main.log.info("Messaggio casuale riga " + i + " inviato: " + line
						+ "\nDestinatario: Matte(76891271)\n");
				Main.sendMessage(76891271, line);
				// --------------------------------------
				Main.log.info("Messaggio casuale riga " + i + " inviato: " + line
						+ "\nDestinatario: Mamma(240650708)\n");
				Main.sendMessage(240650708, line);
				// --------------------------------------
				/*
				 * AGGIUNGERE MANUALMENTE ID DEL DESTINATARIO
				 * PER MESSAGGI CASUALI
				 * Main.log.info("Evento avviato: " + line +
				 * "\nDestinatario: " + destinatario + " (" +
				 * iddestinatario + ")\n" + mex + "\n");
				 * ellie.Main.sendMessage(new ChatId(), line);
				 * Main.log.info("Messaggio spedito");
				 */

				lnr.close();
				return;
			}
		}
		lnr.close();
	}

	public static void startImageRandomEvent(int n) {
		Main.log.info("Foto casuale inviata" + n + "\nDestinatario: Martins(115949778)\n");
		Main.sendPhoto(115949778, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/randomSend/image" + n + ".jpg"));
		//---------------------------------------
		Main.log.info("Foto casuale inviata" + n + "\nDestinatario: Matte(76891271)\n");
		Main.sendPhoto(76891271, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/randomSend/image" + n + ".jpg"));
		// --------------------------------------
		Main.log.info("Foto casuale inviata numero " + n + "\nDestinatario: Mamma(240650708)\n");
		Main.sendPhoto(240650708, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/randomSend/image" + n + ".jpg"));
	}

	public static void startGifRandomEvent(int n) {
		Main.log.info("GIF casuale inviata\nDestinatario: Martins(115949778)\n");
		Main.sendDocument(115949778, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/randomSend/gif/image" + n + ".gif"));
		Main.log.info("GIF casuale inviata\nDestinatario: Mamma(240650708)\n");
		Main.sendDocument(240650708, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/randomSend/gif/image" + n + ".gif"));
	}

	public static void startVideoRandomEvent(int n) {
		Main.log.info("Video casuale inviato\nDestinatario: Martins(115949778)\n");
		Main.sendVideo(115949778, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/video/randomSend/video" + n + ".mp4"));
	}
	
	
}
