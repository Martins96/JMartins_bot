package bot.ellie.utils.console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import bot.ellie.Main;

import bot.ellie.comands.Sticker;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Sender;

public class ConsoleUtilities {
	
	public static void wakeUpEllieMessage() {
		//Console Boot
				BufferedReader bboot;
				FileReader fboot;
				try {
			    	fboot = new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/Boot.txt");
			    	bboot = new BufferedReader(fboot);
			    	String boot = new String(bboot.readLine());
			    		    	
					while(boot != null)
					{
						System.out.println(boot);
						boot = bboot.readLine();
					}
					fboot.close();
					bboot.close();
				} catch (IOException e1) {
					Main.log.error(e1);
				}
		
		System.out.println();
		System.out.println("     _________________________");
		System.out.println("    /                         \\");
		System.out.println("    | Ellie, The telegram bot |");
		System.out.println("    |        Version  3       |");
		System.out.println("    \\_________________________/");
		System.out.println("\nCreate by Martins(c)\n--build " + Costants.BUILD_VERSION + "\n\n");
		
		Main.log.info("\n|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\|");
		Main.log.info("\n\n[  Ellie è attiva! :-)  ]\n");
	}
	
	
	/**Stampa le informazione del messaggio in console
	 * 
	 * @param message
	 */
	public static void stampaInfoMessaggio(Message message) {

		if (message != null) {
			if (message.text() != null) {
				Main.log.info("---------------------------------------------------");
				Main.log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
				Main.log.info("Nome: " + message.from().firstName());
				Main.log.info("Cognome: " + message.from().lastName());
				Main.log.info("Username: " + message.from().username() + " (" + message.from().id() + ")");
				Main.log.info("Testo: " + message.text());
				Main.log.info("---------------------------------------------------");
			} else {
				Main.log.info("---------------------------------------------------");
				Main.log.info("-----------NUOVO---MESSAGGIO---RICEVUTO------------");
				Main.log.info(":messaggio non di testo da parte di:");
				Main.log.info("Nome: " + message.from().firstName());
				Main.log.info("Cognome: " + message.from().lastName());
				Main.log.info("Username: " + message.from().username() + " (" + message.from().id() + ")");
				if(message.photo() != null && message.photo()[0] != null) {
					GetFile getFile = new GetFile(message.photo()[0].fileId());
					GetFileResponse response = Main.ellie.execute(getFile);
					if(response != null) {
						IMG2ASCII converter = new IMG2ASCII();
						converter.convertToAscii(Main.ellie.getFullFilePath(response.file()));
					} else {
						Main.log.info("Il file ricevuto è troppo grande...");
					}
				} else {
					if(message.sticker() != null) {
						Main.log.info("Sticker : " + message.sticker().fileId());
						//invio sticker a papà
						Sender.sendMessage(115949778, "Sticker ricevuto da: " + message.from().firstName() 
								+ " " + message.from().lastName()
								+ " " + message.from().username());
						Sender.sendSticker(115949778, message.sticker().fileId());
						Main.log.info("Sticker casuale inviato a: " + message.from().firstName());
						Sender.sendSticker(message.from().id(), new Sticker().getSticker());
					}
				}
				Main.log.info("---------------------------------------------------");
			}
		}

}
	
	
}
