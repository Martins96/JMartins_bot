package bot.ellie.comands.clouding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InputFile;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import static java.nio.file.StandardCopyOption.*;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.Help;

public class Cloud {
	
	private int idUser;
	private int idthread;
	
	private static String PATH = Main.PATH_INSTALLAZIONE + "/root/";
	private static final String HELP = 
			new String (Help.CLOUD_HELP);
	
	public Cloud(int id, int threadID) {
		this.idUser = id;
		this.idthread = threadID;
	}
	
	/**
	 * inizio la modalità cloud
	 */
	public void startCloudModality () {
		startCloud();
		Message message = attendiMessaggio();
		while(!"exit".equalsIgnoreCase(message.text())) {
			switch(message.text()) {
			case("cd"):
				cd();
			break;
			
			case("cp"):
				cp();
			break;
			
			case("get"):
				get();
			break;
			
			case("getall"):
				getall();
			break;
			
			case("help"):
				sendMessage(HELP);
			break;
			
			case("ls"):
				ls();
			break;
			
			case("mkdir"):
				mkdir();
			break;
			
			case("move"):
				move();
			break;
			
			case("rename"):
				rename();
			break;
			
			case("rm"):
				rm();
			break;
			
			case("send"):
				send();
			break;
			
			
			default:
				sendMessage("!! Invalid comand !!");
				break;
			}
			message = attendiMessaggio();
		}
		
		
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
				+ "_.::CLOUD READY::._\n__build 0.2\n\n Welcome Martins <3";
		sendMessage(s);
	}
	
	private Message attendiMessaggio() {
		boolean flag = true;
		sendMessage(">" + PATH + "$ Enter comand...");
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
	
	private String attendiDocumento() {
		boolean flag = true;
		sendMessage(">Ok, I'm ready to recive your document");
		Message emptyMessage = new Message();
		synchronized (Main.botThread[idthread].message) {
			Main.botThread[idthread].message = emptyMessage;
		}
		while(flag) {
			while (Main.botThread[idthread].message.equals(emptyMessage)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread");
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
			
			if(Main.botThread[idthread].message.text() != null) {
				if(Main.botThread[idthread].message.text().equalsIgnoreCase("quit"))
					flag = false;
				else {
					Main.botThread[idthread].message = null;
					sendMessage("!! INVALID INPUT !!\nMessage was ignored, send 'quit' to cancel the operation");
				}
			} else {
				flag = false;
				Message mex = Main.botThread[idthread].message;
				if(mex.audio() != null) {
					GetFileResponse response = Main.ellie.execute(new GetFile(mex.audio().fileId()));
					return Main.ellie.getFullFilePath(response.file());
					
				}
				if(mex.photo() != null) {
					GetFileResponse response = Main.ellie.execute(new GetFile(mex.photo()[mex.photo().length-1].fileId()));
					return Main.ellie.getFullFilePath(response.file());
				}
				if(mex.document() != null) {
					GetFileResponse response = Main.ellie.execute(new GetFile(mex.document().fileId()));
					return Main.ellie.getFullFilePath(response.file());
				}
				if(mex.video() != null) {
					GetFileResponse response = Main.ellie.execute(new GetFile(mex.video().fileId()));
					return Main.ellie.getFullFilePath(response.file());
				}
				if(mex.voice() != null) {
					GetFileResponse response = Main.ellie.execute(new GetFile(mex.voice().fileId()));
					return Main.ellie.getFullFilePath(response.file());
				}
				if(mex.sticker() != null) {
					sendMessage("!! INVALID INPUT !!");
				}
			}
		}
		return null;
	}
	
	private void sendMessage(String text) {
		Main.sendMessage(idUser, text);
	}
	
	private void cdback1() {
		String[] splittedPath = PATH.split("/");
		int indexRoot = 0;
		for(int i = 0; i<splittedPath.length; i++) {
			if(splittedPath[i].equalsIgnoreCase("root")) {
				indexRoot = i;
				continue;
			}
		}
		if(indexRoot == splittedPath.length - 1) {
			sendMessage("!! You're in root directory !!");
		} else {
			PATH = new String();
			for(int i = 0; i<splittedPath.length-1; i++) {
				PATH = PATH + splittedPath[i] + "/";
			}
		}
	}
	private void cdbackall() {
		PATH = Main.PATH_INSTALLAZIONE + "/root/";
	}
	
	private void rmdir(String[]entries, File file) {
		for(String s: entries){
		    File currentFile = new File(file.getPath(),s);
		    if(currentFile.isDirectory())
		    	if(currentFile.list().length != 0)
		    		rmdir(currentFile.list(), currentFile);
		    currentFile.delete();
		}
	}
	
	
	
	
	
	
	
	
	
	// XXX comands
	
	private void ls() {
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
			sendMessage(lista);
		} else {
			sendMessage("!! No files or dirs found !!");
		}
		
	}
	
	private void mkdir() {
		String folderName = attendiMessaggio(">Enter folder name...").text();
		File folder = new File(PATH + folderName);
		
		folder.mkdir();
		
		sendMessage("[info] The folder was created");
	}
	
	private void rm() {
		String fileName = attendiMessaggio(">Enter file to delete...").text();
		File file = new File(PATH + fileName);
		if(file.isDirectory()) { // è una cartella
			String[]entries = file.list();
			if(entries.length == 0) { // cartella vuota
				String check = attendiMessaggio(">Are you sure? Y/N").text();
				if(check.equalsIgnoreCase("Y"))
					file.delete();
			} else { // la cartella contiene dei file
				String check = attendiMessaggio(">Folder is not empty,\nAre you sure to delete this item? Y/N").text();
				if(check.equalsIgnoreCase("Y")) {
					rmdir(entries, file);
					file.delete();
				}
			}
		} else { // è un file
			String check = attendiMessaggio(">Are you sure? Y/N").text();
			if(check.equalsIgnoreCase("Y"))
				file.delete();
		}
	}
	
	
	private void send() {
		
		String pathFile = attendiDocumento();
		if(pathFile != null) {
			try {
				File file = new File(pathFile);
				
				InputStream in = new BufferedInputStream(new URL(pathFile).openStream());
				OutputStream out = new BufferedOutputStream(new FileOutputStream(PATH + file.getName()));
	
				for ( int i; (i = in.read()) != -1; ) {
				    out.write(i);
				}
				in.close();
				out.close();
			} catch(IOException e) {
				sendMessage("ERROR\n" + e.getMessage());
			}
		} else {
			sendMessage("!! Operation canceled !!");
		}
	}
	
	private void get() {
		String fileName = attendiMessaggio(">Ok, which file?").text();
		File file = new File(PATH + fileName);
		if(file.exists() && !file.isDirectory()) {
			InputFile inputFile = new InputFile("", file);
			Main.sendDocument(idUser, inputFile);
		}
		else {
			sendMessage("!! 404 File not found !!");
		}
		
	}
	
	private void rename() {
		String oldName = attendiMessaggio(">Enter the name of the file").text();
		File file = new File(PATH + oldName);
		if(file.exists()){
			String newName = attendiMessaggio(">Ok, enter the new name for this file").text();
			file.renameTo(new File(PATH + newName));
		} else {
			sendMessage("!! 404 File not found !!");
		}
	}
	
	private void cd() {
		String newPath = attendiMessaggio(">Enter the path").text();
		
		if(newPath.equals("..")){
			cdback1();
		} else if(newPath.equalsIgnoreCase("./")) {
			cdbackall();
		} else {
			File file = new File(PATH + newPath);
			if(file.exists() && file.isDirectory()) {
				PATH = PATH + newPath + "/";
			} else {
				sendMessage("!! Invalid path !!");
			}
		}
	}
	
	private void getall() {
		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		String lista = new String();
		
		if(listOfFiles.length > 0){
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					InputFile inputFile = new InputFile("", listOfFiles[i]);
					Main.sendDocument(idUser, inputFile);
				} else if (listOfFiles[i].isDirectory()) {
					lista = lista + "[Dir]" + listOfFiles[i].getName() + "\\\n";
				}
			}
			if(!lista.equals(""))
				sendMessage(lista);
		} else {
			sendMessage("!! No files or dirs found !!");
		}
	}
	
	
	private void move() {
		String nameFile = attendiMessaggio(">Enter the file to move").text();
		File file = new File(PATH + nameFile);
		if(file.exists()) {
			String destination = attendiMessaggio(">Ok, where I move it?").text();
			if(destination.length()>0 && !destination.substring(0, 1).equals("/"))
				destination = "/" + destination;
			destination = Main.PATH_INSTALLAZIONE + "/root" + destination;
			if(destination.length()>0 && !destination.substring(destination.length()-2, destination.length()-1).equals("/"))
				destination = destination + "/";
			File destinationPath = new File(destination);
			if(destinationPath.exists() && destinationPath.isDirectory()) {
				try {
					if(new File(destinationPath + "/" + nameFile).exists()) {
						if(attendiMessaggio(">The file in the target location alredy exists, Overwrite it? Y/N").text().equalsIgnoreCase("Y")) {
							Files.move(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile), REPLACE_EXISTING);
						}
						
					} else {
						Files.move(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile));
					}
				} catch (IOException e) {
					sendMessage("!! I have a problem with this operation !!");
				}
			} else {
				String createPath = attendiMessaggio(" >!Destination folder not exist. \n You want create it? Y/N").text();
				if(createPath.equals("Y")){
					String[] splittedPath = destination.split("/");
					String tempPath = new String();
					for(int i = 0; splittedPath.length > i; i++) {
						tempPath = tempPath + splittedPath[i] + "/";
						File tempFile = new File(tempPath);
						if(!tempFile.exists() || !tempFile.isDirectory()) {
							tempFile.mkdir();
						}
					}
					try {
						Files.move(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile));
					} catch (IOException e) {
						sendMessage("!! I have a problem with this operation !!");
					}
				} else {
					sendMessage("Comand cancelled");
				}
			}
			
		} else {
			sendMessage("!! 404 File not found !!");
		}
	}
	
	private void cp() {
		String nameFile = attendiMessaggio(">Enter the file to copy").text();
		File file = new File(PATH + nameFile);
		if(file.exists()) {
			String destination = attendiMessaggio(">Ok, where I copy it?").text();
			if(destination.length()>0 && !destination.substring(0, 1).equals("/"))
				destination = "/" + destination;
			destination = Main.PATH_INSTALLAZIONE + "/root" + destination;
			if(destination.length()>0 && !destination.substring(destination.length()-2, destination.length()-1).equals("/"))
				destination = destination + "/";
			File destinationPath = new File(destination);
			if(destinationPath.exists() && destinationPath.isDirectory()) {
				try {
					if(new File(destinationPath + "/" + nameFile).exists()) {
						if(attendiMessaggio(">The file in the target location alredy exists, Overwrite it? Y/N").text().equalsIgnoreCase("Y")) {
							Files.copy(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile), REPLACE_EXISTING);
						}
						
					} else {
						Files.copy(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile));
					}
				} catch (IOException e) {
					sendMessage("!! I have a problem with this operation !!");
				}
			} else {
				String createPath = attendiMessaggio(" >!Destination folder not exist. \n You want create it? Y/N").text();
				if(createPath.equals("Y")){
					String[] splittedPath = destination.split("/");
					String tempPath = new String();
					for(int i = 0; splittedPath.length > i; i++) {
						tempPath = tempPath + splittedPath[i] + "/";
						File tempFile = new File(tempPath);
						if(!tempFile.exists() || !tempFile.isDirectory()) {
							tempFile.mkdir();
						}
					}
					try {
						Files.copy(Paths.get(file.getPath()), Paths.get(destinationPath + "/" + nameFile));
					} catch (IOException e) {
						sendMessage("!! I have a problem with this operation !!");
					}
				} else {
					sendMessage("Comand cancelled");
				}
			}
			
		} else {
			sendMessage("!! 404 File not found !!");
		}
	}
	
	
	
	
}
