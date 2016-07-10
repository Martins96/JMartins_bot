package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HDDList {

	public HDDList() {
		
	}
	
	public String generaLista() throws IOException
	{
		String s = "";
		
		FileReader f;
	    f=new FileReader("src/bot/ellie/readfiles/HDDList.txt");
	
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
