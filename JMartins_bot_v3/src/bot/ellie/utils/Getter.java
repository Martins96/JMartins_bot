package bot.ellie.utils;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.tracing.TraceChat;

public class Getter {
	
	public static String attendiMessaggio(int idThread) {
		do {
			Message emptyMessage = new Message();
			synchronized (Main.botThread.get(idThread).message) {
				Main.botThread.get(idThread).message = emptyMessage;
			}
			while (Main.botThread.get(idThread).message.equals(emptyMessage)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread", e);
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
		} while (Main.botThread.get(idThread).message.text() == null); //continuo finché non ricevo un mex di testo
		return Main.botThread.get(idThread).message.text();
	}
	
	public static Message attendiMessaggioObject(int idThread) {
		do {
			Message emptyMessage = new Message();
			synchronized (Main.botThread.get(idThread).message) {
				Main.botThread.get(idThread).message = emptyMessage;
			}
			while (Main.botThread.get(idThread).message.equals(emptyMessage)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread", e);
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
		} while (Main.botThread.get(idThread).message.text() == null); //continuo finché non ricevo un mex di testo
		return Main.botThread.get(idThread).message;
	}
	
	public static String attendiDocumento(int idthread, Object idUser) {
		boolean flag = true;
		Sender.sendMessage(idUser, ">Ok, I'm ready to recive your document");
		Message emptyMessage = new Message();
		synchronized (Main.botThread.get(idthread).message) {
			Main.botThread.get(idthread).message = emptyMessage;
		}
		while(flag) {
			while (Main.botThread.get(idthread).message.equals(emptyMessage)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Main.log.error("Errore sync del Thread");
					ErrorReporter.sendError("Errore sync del Thread", e);
					e.printStackTrace();
				}
			}
			
			if(Main.botThread.get(idthread).message.text() != null) {
				if(Main.botThread.get(idthread).message.text().equalsIgnoreCase("quit"))
					flag = false;
				else {
					Main.botThread.get(idthread).message = null;
					Sender.sendMessage(idUser, "!! INVALID INPUT !!\nMessage was ignored, send 'quit' to cancel the operation");
				}
			} else {
				GetFileResponse response = null;
				flag = false;
				Message mex = Main.botThread.get(idthread).message;
				if(mex.audio() != null) {
					response = Main.ellie.execute(new GetFile(mex.audio().fileId()));
				}
				if(mex.photo() != null) {
					response = Main.ellie.execute(new GetFile(mex.photo()[mex.photo().length-1].fileId()));
				}
				if(mex.document() != null) {
					response = Main.ellie.execute(new GetFile(mex.document().fileId()));
					
				}
				if(mex.video() != null) {
					response = Main.ellie.execute(new GetFile(mex.video().fileId()));
				}
				if(mex.voice() != null) {
					response = Main.ellie.execute(new GetFile(mex.voice().fileId()));
				}
				if(mex.sticker() != null) {
					Sender.sendMessage(idUser, "!! INVALID INPUT !!");
					return null;
				}
				
				if(isResponseCorrect(response, idUser))
					return Main.ellie.getFullFilePath(response.file());
				else
					return null;
			}
		}
		return null;
	}
	
	private static boolean isResponseCorrect(GetFileResponse response, Object idUser) {
		if(response == null)
			return false;
		switch(response.errorCode()) {
		case (400):
			Main.log.info("File sended is too big");
			Sender.sendMessage(idUser, "!! File is too big! [MAX 20 MB] !!");
			return false;

		case(404):
			Sender.sendMessage(idUser, "!! File not found, I have same problem !!");
			return false;

		default:
			return true;
		}
	}
}
