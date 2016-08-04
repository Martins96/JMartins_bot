package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import bot.ellie.Main;

public class ASCIIart {
	

	public ASCIIart() {
		
	}

	
	/**Genero un art con caratteri da tastiera
	 * 
	 * @param numero dell'art da generare
	 * @throws IOException
	 */
	public String generaArt(int i) throws IOException
	{
		String s = "", file = "ascii" + i;
		
		FileReader f;
	    f=new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/ASCII/" + file + ".txt");

	    BufferedReader b;
	    b=new BufferedReader(f);
	    String t = b.readLine();
	    while(t != null)
	    {
	    	s = s + t + "\n";
	    	t = b.readLine();
	    }
	    b.close();
	    f.close();
		return s;
	}
}
