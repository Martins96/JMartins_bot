package bot.ellie.readfiles.photo;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import me.shib.java.lib.jtelebot.models.types.ChatId;
import me.shib.java.lib.jtelebot.models.types.InputFile;

public class Photo {
	
	// costanti per il numero di immagini disponibili
	private static final int NUM_IMMAGINI = 10;
	private static final int NUM_ADMIN_IMMAGINI = 28;
	
	/**ritorna l'immagine della categoria selezionata
	 * in caso la categoria sia null o non riconosciuta ne ritorna una casuale
	 * 
	 * @param id del richiedente
	 * @param categoria della foto
	 * @return la foto della categoria selezionata
	 */
	public static InputFile getImage(ChatId id, String categoria) {
		Random ran = new Random();
		try {
			if(categoria == null || categoria.equals("/random")) {
				switch(ran.nextInt(3)) {
				case 0:
					categoria = "cute";
					break;
				case 1:
					categoria = "funny";
					break;
				case 2:
					categoria = "mystic";
					break;
				}
			} else {
				switch(categoria) {
					case "/cute":
						categoria = "cute";
						break;
					case "/funny":
						categoria = "funny";
						break;
					case "/nature":
						categoria = "mystic";
						break;
					default :
						Main.ellie.sendMessage(id, "Categoria non riconosciuta, ne scelgo una casuale");
							switch(ran.nextInt(3)) {
							case 0:
								categoria = "cute";
								break;
							case 1:
								categoria = "funny";
								break;
							case 2:
								categoria = "mystic";
								break;
							}
						break;
				}
			}
		} catch (IOException e) {
			Main.log.error("Errore nel caricamento della foto nella classe Photo.java");
			ErrorReporter.sendError("Errore nel caricamento della foto nella classe Photo.java", e);
			e.printStackTrace();
			return null;
		}
		
		File photo = new File("src/bot/ellie/readfiles/photo/" + categoria + ran.nextInt(NUM_IMMAGINI) + ".jpg");
		Main.log.info("Foto generata: " + photo.getPath());
		return new InputFile(photo);
	}
	
	public static InputFile getAdminImage() {
		
		File foto = new File("src/bot/ellie/readfiles/photo/admin/HF" + new Random().nextInt(NUM_ADMIN_IMMAGINI) + ".jpg");
		return new InputFile(foto);
	}
	
}
