package bot.ellie.utils.sendTypes;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;
import bot.ellie.utils.Sender;

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
	public static File getImage(Object id, String categoria) {
		Random ran = new Random();
		if (categoria == null || categoria.equals("/random")) {
			switch (ran.nextInt(3)) {
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
			switch (categoria) {
			case "/cute":
				categoria = "cute";
				break;
			case "/funny":
				categoria = "funny";
				break;
			case "/nature":
				categoria = "mystic";
				break;
			default:
				Sender.sendMessage(id, "Categoria non riconosciuta, ne scelgo una casuale");	
				switch (ran.nextInt(3)) {
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

		File photo = new File(Main.PATH_INSTALLAZIONE + "/readfiles/photo/" + categoria + ran.nextInt(NUM_IMMAGINI) + ".jpg");
		Main.log.info("Foto generata: " + photo.getPath());
		return photo;
	}
	
	public static File getAdminImage() {
		int nFoto = new Random().nextInt(NUM_ADMIN_IMMAGINI);
		File foto = new File(Main.PATH_INSTALLAZIONE + "/readfiles/photo/admin/HF" + nFoto + ".jpg");
		Main.log.info("Foto per admin generata numero " + nFoto);
		return foto;
	}
	
	
}
