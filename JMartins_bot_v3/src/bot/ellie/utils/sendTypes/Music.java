package bot.ellie.utils.sendTypes;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;
import bot.ellie.utils.Costants;

public class Music {
	
	public static File getAudio() {
		Random rand = new Random();
		int nMusica = rand.nextInt(Costants.N_MUSIC);
		File musica = new File(Main.PATH_INSTALLAZIONE + "/readfiles/music/music" + nMusica + ".mp3");
		Main.log.info("Audio inviato - music" + nMusica + ".mp3");
		return musica;
	}
	
}
