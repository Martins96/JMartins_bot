package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import bot.ellie.Main;

public class Perla {

	public Perla() {
		
	}
	/** legge da un file una riga contenente una perla di saggezza
	 * 
	 * @param numero della battuta da dire
	 */
	private static String generaPerla(int i) throws IOException
	{
		String file = "src/bot/ellie/readfiles/perle.txt";
		
		FileReader f;
	    f=new FileReader(file);
		    BufferedReader b;
	    b=new BufferedReader(f);
	    String t = b.readLine();
	    for(int j = 1; i > j; j++)
	    {
	    	t = b.readLine();
	    }
    	t = b.readLine();
    	b.close();
    	f.close();
		return t;
	}
	
	public static String comandoPerla() {
		Random random = new Random();
		String aforisma = new String();
		int n = random.nextInt(55);
		try {
			aforisma = generaPerla(n);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Main.log.info("perla generata n. " + n);
		return aforisma;
	}
	
}
