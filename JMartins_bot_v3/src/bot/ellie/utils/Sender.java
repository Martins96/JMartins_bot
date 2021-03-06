package bot.ellie.utils;

import java.io.File;

import org.apache.log4j.Logger;

import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendGame;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.SendVideo;

import bot.ellie.Main;
import bot.ellie.utils.tracing.TraceChat;

public class Sender extends Main{
	
	private static Logger log = Logger.getLogger(Sender.class);
	
	
	private Sender() {
	}
	
	/**
	 * invio messaggi
	 */
	public static void sendMessage(Object id, String text) {
		if(ellie != null) {
			for(int retry = 3; retry > 0; retry--) {
				try {
					TraceChat.trace(id, text, null);
					ellie.execute(new SendMessage(id, text));
					log.info("Messaggio inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send photo to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio documenti
	 */
	public static void sendDocument(Object id, File doc) {
		if(ellie != null) {
			for(int retry = 2; retry > 0; retry--) {
				try {
					ellie.execute(new SendDocument(id, doc));
					log.info("Documento inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send Document to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio foto
	 */
	public static void sendPhoto(Object id, File photo) {
		if(ellie != null) {
			for(int retry = 3; retry > 0; retry--) {
				try {
					ellie.execute(new SendPhoto(id, photo));
					log.info("Foto inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send photo to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio audio
	 */
	public static void sendAudio(Object id, File music) {
		if(ellie != null) {
			for(int retry = 2; retry > 0; retry--) {
				try {
					ellie.execute(new SendAudio(id, music));
					log.info("Musica inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send audio to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio video
	 */
	public static void sendVideo(Object id, File video) {
		if(ellie != null) {
			for(int retry = 3; retry > 0; retry--) {
				try {
					ellie.execute(new SendVideo(id, video));
					log.info("Video inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send video to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio sticker
	 */
	public static void sendSticker(Object id, String sticker) {
		if(ellie != null) {
			for(int retry = 2; retry > 0; retry--) {
				try {
					ellie.execute(new SendSticker(id, sticker));
					log.info("Sticker inviato a " + id);
					return;
				} catch (RuntimeException e) {
					Main.log.error("Error send Sticker to " + id);
				}
			}
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	/**
	 * invio gioco
	 */
	public static void sendGame(Object id, String game) {
		if(ellie != null) {
			ellie.execute(new SendGame(id, game));
			log.info("Gioco inviato a " + id);
		} else {
			log.error("Ellie non è stata inizializzata!");
		}
	}
	
	
	
	
}
