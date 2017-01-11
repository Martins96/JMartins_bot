package bot.ellie.utils.users;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;
import bot.ellie.utils.Costants;

public class MyLady {
	
	private static final short N_IMAGES = Costants.N_RANDOM_IMAGE_MYLADY;
	
	
	
	public static boolean autenticaMylady(String password) {
		
		return (password.equals(Costants.MYLADY_PASSWORD)) ? true : false;
		
	}
	
	public static File getImageRandom() {
		
		Random random = new Random();
		short i = (short)random.nextInt(N_IMAGES);
		File image = new File(Main.PATH_INSTALLAZIONE + "/readfiles/photo/mylady/image" + i + ".jpg");
		Main.log.info("Spedisco immagine numero " + i + " a mylady <3");
		return image;
		
	}
	
	
	
	
}
