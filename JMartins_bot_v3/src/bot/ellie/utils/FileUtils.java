package bot.ellie.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import bot.ellie.Main;

public class FileUtils {
	
	/**Controlla se nel file risposte.txt c'è il testo del messaggio in arrivo
	 * 
	 * @param messaggio in arrivo
	 * @return numero riga del testo del messaggio in arrivo, oppure -1 in caso non ci sia
	 * @throws IOException
	 */
	public static int leggiFileRisposta(String s) throws IOException {
		//converto prima lettera in maiuscola
		
		String word = "-" + s;
		int n = -1;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposte.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;

			while ((line = lnr.readLine ()) != null) {
			    if (line.equalsIgnoreCase(word)) {
			    	n = lnr.getLineNumber();
			    	break;
			    }   
			}
			
			lnr.close();
			fr.close();
			return n;
	}
	
	/**Leggo da file risposte la riga in input
	 * @param numero riga del file da leggere
	 * @return Stringa della riga selezionata
	 * @throws IOException
	 */
	public static String rispondiFileRisposta(int riga) throws IOException {
		FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposte.txt");
		LineNumberReader lnr = new LineNumberReader (fr);
		String line;

		while ((line = lnr.readLine ()) != null)
		    if (lnr.getLineNumber () == riga)
		    	break;
		
		lnr.close();
		return line;
	}
	
	/**Controllo se nel file riposteinfo.txt è presente la richiesta in input
	 * 
	 * @param messaggio richiesta dell'utente
	 * @return numero della riga se trovata risposta, altrimenti -1
	 * @throws IOException
	 */
	public static int leggiFileRispostaInfo(String s) throws IOException {
		//converto prima lettera in maiuscola
		String word = "-" + s;
		
			int n = -1;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposteinfo.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;
	
			while ((line = lnr.readLine ()) != null) {
			    if (line.equalsIgnoreCase(word)) {
			    	n = lnr.getLineNumber();
			    	break;
			    }
			}
			
			lnr.close();
			fr.close();
			return n;
	}
	
	/**Leggo da file risposteinfo.txt la riga richiesta
	 * 
	 * @param numero riga da leggere
	 * @return Stringa della riga richiesta
	 * @throws IOException
	 */
	public static String rispondiFileRispostaInfo(int riga) throws IOException {
			
			FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposteinfo.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;
	
			while ((line = lnr.readLine ()) != null)
			    if (lnr.getLineNumber () == riga)
			    	break;
			
			lnr.close();
			fr.close();
			return line;
	}
	
}
