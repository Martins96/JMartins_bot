package bot.ellie.comands.clouding;

import java.io.File;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InputFile;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;

public class Shared {
	
	private int idUser;
	private int idthread;
	
	private static String PATH = Main.PATH_INSTALLAZIONE + "/root/share";
	
	
	public Shared(int id, int threadID) {
		this.idUser = id;
		this.idthread = threadID;
	}
	
	public String startSharedFileMode() {
		
		startCloud();
		stampFolderFiles();
		String message = attendiMessaggio().text();
		if(message.length() > 0 && !message.substring(0, 1).equals("/"))
			message = "/" + message;
		while(!"/:exit".equalsIgnoreCase(message) && !"/:quit".equalsIgnoreCase(message)) {
			
			File file = new File(PATH + message);
			if(file.exists()) {
				sendDocument(file.getPath());
			} else {
				sendMessage("!! 404 File not found !!");
			}
			
			stampFolderFiles();
			message = attendiMessaggio().text();
			if(message.length() > 0 && !message.substring(0, 1).equals("/"))
				message = "/" + message;
		}
		
		
		return "Uscita dalla repository";
	}
	
	
	
	
	
	// XXX metodi
	
	
	/**
	 * roba grafica...
	 */
	private void startCloud() {
		String s = new String();
		s = s + ".              .-~~~-.           \n";
		s = s + "  .- ~ ~-(                )_ _       \n";
		s = s + " /                                  ~ -.  \n";
		s = s + "|                                           ',\n";
		s = s + " \\                                         .'\n";
		s = s + "   ~- ._ ,. ,....,.,......, ,....... -~   \n";
		s = s + "              '               '         \n"
				+ "_.::CLOUD READY::._\n__build 0.2\n\n Welcome visitor :-)\n\n"
				+ "Enter :quit or :exit for exit from this mode\n"
				+ "Tap on the name of the file for download it, or digit the name with keyboard\n\n";
		sendMessage(s);
	}
	
	private void stampFolderFiles() {
		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		String lista = new String();
		
		if(listOfFiles.length > 0){
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					lista = lista + listOfFiles[i].getName() + "\n";
				} else if (listOfFiles[i].isDirectory()) {
					lista = "[Dir]" + listOfFiles[i].getName() + "\\\n" + lista;
				}
			}
			sendMessage(lista + "\n\nEnter :quit or :exit for exit from this mode");
		} else {
			sendMessage("!! No files or dirs found !!");
		}
	}

	private void sendMessage(String text) {
		Main.sendMessage(idUser, text);
	}
	
	private void sendDocument(String doc) {
		Main.sendDocument(idUser, new InputFile("", new File(doc)));
	}
	
	private Message attendiMessaggio() {
		boolean flag = true;
		sendMessage("Enter comand...");
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
			}
		}
		return Main.botThread[idthread].message;
	}
		
		
}
