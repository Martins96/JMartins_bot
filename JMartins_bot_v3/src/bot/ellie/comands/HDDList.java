package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;

public class HDDList {

	public HDDList() {
		
	}
	
	private static String generaLista() throws IOException
	{
		String s = "";
		
		FileReader f = new FileReader(Main.PATH_INSTALLAZIONE + "/readfiles/HDDList.txt");
	
	    BufferedReader b = new BufferedReader(f);
	    String t = b.readLine();
	    while(t != null) {
	    	s = s + t + "\n";
	    	t = b.readLine();
	    }
	    b.close();
	    f.close();
		return s;
	}
	
	public static String comandoHDDList() {
		
		String aHDDRisp = "Scusami, qualcosa è andato storto...";
		try {
			aHDDRisp = generaLista();
		} catch (IOException e) {
			Main.log.error("Errore nella generazione lista HHDList" + e);
			ErrorReporter.sendError("Errore comandoHDDList", e);
		}
		return aHDDRisp;
	}
}
