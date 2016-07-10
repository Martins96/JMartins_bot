package bot.ellie.readfiles.music;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;
import me.shib.java.lib.jtelebot.models.types.InputFile;

public class Music {

	private static int NUM_MUSICHE = 18;
	
	public static InputFile getAudio() {
		Random rand = new Random();
		int nMusica = rand.nextInt(NUM_MUSICHE);
		File musica = new File("src/bot/ellie/readfiles/music/music" + nMusica + ".mp3");
		Main.log.info("Audio inviato - src/bot/ellie/readfiles/music/music" + nMusica + ".mp3");
		return new InputFile(musica);
	}
	
}
