package bot.ellie.security;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.pengrad.telegrambot.model.Message;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;

public class Security {
	
	private int idUser;
	private int idthread;
	
	public Security(int idUser, int idthread) {
		this.idUser = idUser;
		this.idthread = idthread;
	}
	
	public void startSecurityMode() {
		startSecurity();
		String[] message = attendiMessaggio();
		while(!"exit".equalsIgnoreCase(message[0])) {
			switch(message[0]) {
			case ("ping"):
				ping(message);
			break;
			
			case ("trace"):
				trace(message);
			break;
			
			
			default:
				sendMessage("!! Invalid comand !!");
				break;
			}
			message = attendiMessaggio();
		}
	}

	private void startSecurity() {
		String s  = new String();
		s = s + "────────────█████████\n";
		s = s + "─────────███║║║║║║║║║███\n";
		s = s + "────────█║║║║║║║║║║║║║║║█\n";
		s = s + "───────█║║║║║███████║║║║║█\n";
		s = s + "──────█║║║║║██─────██║║║║║█\n";
		s = s + "─────█║║║║║██───────██║║║║║█\n";
		s = s + "────█║║║║║██─────────██║║║║║█\n";
		s = s + "────█║║║║██───────────██║║║║█\n";
		s = s + "────█║║║║█─────────────█║║║║█\n";
		s = s + "────█║║║║█─────────────█║║║║█\n";
		s = s + "────█║║║║█─────────────█║║║║█\n";
		s = s + "────█║║║║█─────────────█║║║║█\n";
		s = s + "───███████───────────███████\n";
		s = s + "───██║║║║║║██────────██║║║║║██\n";
		s = s + "──██║║║║║║║║██──────██║║║║║║║██\n";
		s = s + "─██║║║║║║║║║║██───██║║║║║║║║║║██\n";
		s = s + "██║║║║║║║║║║║║█████║║║║║║║║║║║║██\n";
		s = s + "█║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║║█\n";
		s = s + "█║║║║║║║║║║║║║█████║║║║║║║║║║║║║█\n";
		s = s + "█║║║║║║║║║║║║█░░░░░█║║║║║║║║║║║║█\n";
		s = s + "█║║║║║║║║║║║║█░░░░░█║║║║║║║║║║║║█\n";
		s = s + "█║║║║║║║║║║║║█░░░░░█║║║║║║║║║║║║█\n";
		s = s + "██║║║║║║║║║║║█░░░░░█║║║║║║║║║║║██\n";
		s = s + "██║║║║║║║║║║║║█░░░█║║║║║║║║║║║║██\n";
		s = s + "─██║║║║║║║║║║║█░░░█║║║║║║║║║║║██\n";
		s = s + "──██║║║║║║║║║║█░░░█║║║║║║║║║║██\n";
		s = s + "───██║║║║║║║║║█░░░█║║║║║║║║║██\n";
		s = s + "────██║║║║║║║║█████║║║║║║║║██\n";
		s = s + "─────██║║║║║║║║███║║║║║║║║██\n";
		s = s + "──────██║║║║║║║║║║║║║║║║║██\n";
		s = s + "───────██║║║║║║║║║║║║║║║██\n";
		s = s + "────────██║║║║║║║║║║║║║██\n";
		s = s + "─────────██║║║║║║║║║║║██\n";
		s = s + "──────────██║║║║║║║║║██\n";
		s = s + "───────────██║║║║║║║██\n";
		s = s + "────────────██║║║║║██\n";
		s = s + "─────────────██║║║██\n";
		s = s + "──────────────██║██\n";
		s = s + "───────────────███\n\n\n";
		s = s + "Welcome in Security Mode - enter a comand or exit for quit";
		sendMessage(s);
	}
	
	
	private String[] attendiMessaggio() {
		boolean flag = true;
		String[] s = null;
		sendMessage(">Enter comand...");
		Message emptyMessage = new Message();
		synchronized (Main.botThread[idthread].message) {
			Main.botThread[idthread].message = emptyMessage;
		}
		while(flag) {
			while (Main.botThread[idthread].message.equals(emptyMessage)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread");
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
			if(Main.botThread[idthread].message.text() == null) {
				Main.botThread[idthread].message = null;
				sendMessage( "!! INVALID INPUT !!\nMessage was ignored");
			} else {
				flag = false;
				s = Main.botThread[idthread].message.text().split(" ");
				if( !(s != null && s.length > 0 && (s[0] != null || !s[0].equals("")) ) ) {
					sendMessage( "!! INVALID INPUT !!\nMessage was ignored");
					flag = true;
				}
			}
		}
		return s;
	}
	
	private Message attendiMessaggio(String mex) {
		boolean flag = true;
		sendMessage(mex);
		Message emptyMessage = new Message();
		synchronized (Main.botThread[idthread].message) {
			Main.botThread[idthread].message = emptyMessage;
		}
		while(flag) {
			while (Main.botThread[idthread].message.equals(emptyMessage)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread");
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
			if(Main.botThread[idthread].message.text() == null) {
				Main.botThread[idthread].message = null;
				sendMessage("!! INVALID INPUT !!\nMessage was ignored");
			} else {
				flag = false;
			}
		}
		return Main.botThread[idthread].message;
	}
	
	private void sendMessage(String text) {
		Main.sendMessage(idUser, text);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// XXX Comands
	
	private void ping(String[] param) {
		try {
			String host = null;
			if(param.length > 1 && (param[1] != null || !param[0].equals("")) )
				host = param[1];
			else
				host = attendiMessaggio(">Enter the target").text();
			
			InetAddress inet = InetAddress.getByName(host);
			String risposta = inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable";
			String infoHost = "TARGET:\n"
					+ "\nName: " + inet.getHostName()
					+ "\nAddress: " + inet.getHostAddress()
					+ "\nCanonical Name: " + inet.getCanonicalHostName()
					+ "\nMulticast? :" + inet.isMulticastAddress();
			sendMessage(infoHost);
			sendMessage(":: " + risposta + " ::");
		} catch(SocketException e) {
			sendMessage("Socket for ping generate same errors... " + e.getMessage());
		} catch (UnknownHostException ex) {
			sendMessage("!! Host not recognized !!");
		} catch (IOException ex2) {
			Main.log.error("Ping error", ex2);
			ErrorReporter.sendError("Ping error", ex2);
		}
		
	}
	
	private void trace(String[] param) {
		try {
			String host = null;
			if(param.length > 1 && (param[1] != null || !param[0].equals("")) )
				host = param[1];
			else
				host = attendiMessaggio(">Enter the target").text();
			
			LookupService lookupService = new LookupService(Main.PATH_INSTALLAZIONE + "/readfiles/security/GeoLiteCity.dat", LookupService.GEOIP_CHECK_CACHE);
			InetAddress inet = InetAddress.getByName(host);
			Location location = lookupService.getLocation(inet);
			String s = "TARGET:\nName: " + inet.getHostName() + "\nIP: " + inet.getHostAddress() +
					   "\n\nCountry: " + location.countryName +
					   "\nRegion: " + location.region +
					   "\nCity: " + location.city +
					   "\nCOORDINATE:: LAT " + location.latitude + " - LON " + location.longitude;
			sendMessage(s);
		} catch (SocketException e) {
			sendMessage("Socket for trace generate same errors...");
		} catch (IOException ex) {
			Main.log.error("Trace error", ex);
			ErrorReporter.sendError("Trace error", ex);
		} catch(NullPointerException nullpointex) {
			sendMessage("Target not found...");
		} catch (Exception exs) {
			sendMessage("Trace comand generate same errors..." + exs.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
