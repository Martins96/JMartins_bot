package bot.ellie.comands.admin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import bot.ellie.Main;
import bot.ellie.utils.Costants;
import bot.ellie.utils.Sender;

public class Love {
	
	private static Random random = new Random();
	
	public static String execute(String idUser) throws IOException {
		int n = random.nextInt(Costants.N_L_IMAGE);
		Main.log.debug("Foto inviata numero : " + n );
		Sender.sendPhoto(idUser, new File(Main.PATH_INSTALLAZIONE
				+ "/readfiles/photo/love/image" + n + ".JPG"));
		return getFraseMessaggio();
	}
	
	private static String getFraseMessaggio() throws IOException{
		int i = random.nextInt(Costants.N_L_MSG) + 1;
		Main.log.debug("testo selezionato n. " + i);
		LineNumberReader lnr = new LineNumberReader(
				new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/l_message.txt"));
		String s = "❤️";
		String line;
		while ((line = lnr.readLine()) != null) {
			if (lnr.getLineNumber() == i) {
				s =  "\"" + line + "\"";
				lnr.close();
				return s;
			}
		}
		lnr.close();
		return s;
	}
	
}
