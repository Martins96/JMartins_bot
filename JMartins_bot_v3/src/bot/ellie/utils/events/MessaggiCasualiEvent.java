package bot.ellie.utils.events;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Sender;
import bot.ellie.utils.events.Events;

public class MessaggiCasualiEvent extends Events {
	
	private static Random random = new Random();
	
	public MessaggiCasualiEvent() {
		super();
	}
	
	public void messaggiCasuali() {

		try {
			//mando messaggi solo in orari specifici
			DateFormat oraFormat = new SimpleDateFormat("HH");
			Date orario = new Date();
			int ora = Integer.parseInt("" + oraFormat.format(orario));
			if(ora > 10)
			{
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
				List<String> l = decidiDestinatari();
				for(String s : l) {
					Sender.sendMessage(s, line);
				}

				lnr.close();
				return;
			}
		}
		lnr.close();
	}

	public static void startImageRandomEvent(int n) {
		Main.log.info("Foto casuale inviata numero : " + n );
		List<String> l = decidiDestinatari();
		for(String s : l) {
			Sender.sendPhoto(s, new File(Main.PATH_INSTALLAZIONE
					+ "/readfiles/photo/randomSend/image" + n + ".jpg"));
		}
	}

	public static void startGifRandomEvent(int n) {
		Main.log.info("GIF casuale inviata numero : " + n);
		List<String> l = decidiDestinatari();
		for(String s : l) {
			Sender.sendDocument(s, new File(Main.PATH_INSTALLAZIONE
					+ "/readfiles/photo/randomSend/gif/image" + n + ".gif"));
		}
	}

	public static void startVideoRandomEvent(int n) {
		Main.log.info("Video casuale inviato numero : " + n);
		List<String> l = decidiDestinatari();
		for(String s : l) {
			Sender.sendVideo(115949778, new File(Main.PATH_INSTALLAZIONE
					+ "/readfiles/video/randomSend/video" + n + ".mp4"));
		}
	}
	
	
	private static List<String> decidiDestinatari() {
		List<String> list = new ArrayList<String>();
		Main.log.info("Decido destinatari per evento casuale\nDestinatari:");

		list.add("115949778"); //100%
		Main.log.info("Martins (115949778)");
		
		if(random.nextInt(100)>5) { // 95% possibilità di spedizione
			list.add("240650708");
			Main.log.info("Mamma (240650708)");
		}
		
		if(random.nextInt(100)>15) { // 85%
			list.add("76891271");
			Main.log.info("Matte (76891271)");
		}
		
		if(random.nextInt(100) > 50) { //50% 
			list.add("104412734");
			Main.log.info("Valentino (104412734)");
		}
		
		if(random.nextInt(100) > 75) { //25% 
			list.add("124796388");
			Main.log.info("Gaia (124796388)");
		}
		
		if(random.nextInt(100) > 99) { //1% 
			list.add("148278619");
			Main.log.info("Dinu (148278619)");
		}
		return null;
	}
	
}
