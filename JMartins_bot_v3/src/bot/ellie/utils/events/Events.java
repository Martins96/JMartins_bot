package bot.ellie.utils.events;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.comands.Meteo;
import bot.ellie.utils.CompleannoEllie;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Sender;

public abstract class Events {
	
	protected Events() {
	}
	
	/**
	 * invio messaggi evento, controllo un nuovo evento ogni minuto
	 */
	public abstract void messaggiCasuali();
	
	/**
	 * lancia messaggi casuali
	 */
	public static void controllaEvento(String eventodate) {
		Date date = new Date();

		// meteo di fine settimana
		DateFormat dateFormatWeekendMeteo = new SimpleDateFormat("u HH:mm");
		String weekendmeteo = new String(dateFormatWeekendMeteo.format(date)); // 5 15:00 5 = Friday
		if (weekendmeteo.equalsIgnoreCase(Costants.ORA_DEL_WEEKEND_METEO)) {
			String meteo = Meteo.getWeekendMeteo("/Bergamo");
			Main.log.info("Evento avviato (Meteo del WeekEnd): " + "\nDestinatario: " + "Martins" + " ( 115949778 )\n"
					+ meteo + "\n");
			Sender.sendMessage(115949778, meteo);
			Main.log.info("Evento avviato (Meteo del WeekEnd): " + "\nDestinatario: " + "Mamma" + " ( 240650708 )\n"
					+ meteo + "\n");
			Sender.sendMessage(240650708, meteo);
			Main.log.info("Messaggio spedito in data: " + eventodate);

		}

		// evento tramite file
		try {
			FileReader fr = new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/eventi.txt");
			LineNumberReader lnr = new LineNumberReader(fr);
			String line;
			while ((line = lnr.readLine()) != null) {
				if (line.equals(eventodate)) {
					String destinatario = lnr.readLine();
					String mex = lnr.readLine();
					// compleanno ellie
					if (mex.equalsIgnoreCase(Costants.COMPLEANNO_ELLIE_MEX)) {
						mex = CompleannoEllie.getAuguriDiEllie();
					}
					long iddestinatario = 0;

					// seleziono destinatario
					switch (destinatario) {
						case ("Martins"):
							iddestinatario = 115949778;
							break;
						case ("Mamma"):
							iddestinatario = 240650708;
							break;
						case ("Gaia"):
							iddestinatario = 124796388;
							break;
						case ("Ale"):
							iddestinatario = 143009205;
							break;
						case ("Matte"):
							iddestinatario = 76891271;
							break;
						case ("Francesco"):
							iddestinatario = 170595365;
							break;
						case ("A<3L"):
							Main.log.info("LOVE EVENT");
							iddestinatario = 115949778; // PAPA'
							Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Martins" + " ("
									+ iddestinatario + ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito in data: " + eventodate);
		
							iddestinatario = 240650708; // MAMMA
							Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Mamma" + " ("
									+ iddestinatario + ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito in data: " + eventodate);
							return;
						case ("All"): {
							// broadcast
							Main.log.info("EVENT BROADCAST");
							// inserire manualmente gli id
							iddestinatario = 115949778;
							Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Martins" + " ("
									+ iddestinatario + ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito in data: " + eventodate);
		
							iddestinatario = 240650708;
							Main.log.info("Evento avviato (broadcast): " + line + "\nDestinatario: " + "Mamma" + " ("
									+ iddestinatario + ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito in data: " + eventodate);
		
							iddestinatario = 124796388;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Gaia" + " (" + iddestinatario
									+ ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
		
							iddestinatario = 143009205;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Ale" + " (" + iddestinatario
									+ ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
		
							iddestinatario = 76891271;
							Main.log.info("Evento avviato: " + line + "\nDestinatario: " + "Matte" + " (" + iddestinatario
									+ ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
		
							iddestinatario = 104412734;
							System.out.println("Evento avviato: " + line + "\nDestinatario: " + "Valentino" + " ("
									+ iddestinatario + ")\n" + mex + "\n");
							Sender.sendMessage(iddestinatario, mex);
							Main.log.info("Messaggio spedito");
		
							/*
							 * iddestinatario =;
							 * System.out.println("Evento avviato: " + line +
							 * "\nDestinatario: " + destinatario + " (" +
							 * iddestinatario + ")\n" + mex + "\n"); chat = new
							 * User(iddestinatario, "");
							 * ellie.getSender().sendText(mex, chat, null);
							 * System.out.println("Messaggio spedito");
							 */
							fr.close();
							lnr.close();
							return;
						}
					}
					Main.log.info("Evento avviato: " + line + "\nDestinatario: " + destinatario + " (" + iddestinatario
							+ ")\n" + mex + "\n");
					Sender.sendMessage(iddestinatario, mex);
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
	
}
