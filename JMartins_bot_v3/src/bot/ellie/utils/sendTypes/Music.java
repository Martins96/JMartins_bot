package bot.ellie.utils.sendTypes;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;

public class Music {

	private static int NUM_MUSICHE = 18;
	
	public static File getAudio() {
		Random rand = new Random();
		int nMusica = rand.nextInt(NUM_MUSICHE);
		File musica = new File(Main.PATH_INSTALLAZIONE + "/readfiles/music/music" + nMusica + ".mp3");
		Main.log.info("Audio inviato - music" + nMusica + ".mp3");
		return musica;
	}
	
}
