package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Perla {

	public Perla() {
		
	}
	/** legge da un file una riga contenente una perla di saggezza
	 * 
	 * @param numero della battuta da dire
	 */
	public String generaPerla(int i) throws IOException
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
	
}
