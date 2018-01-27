package bot.ellie.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;

import bot.ellie.ErrorReporter;
import bot.ellie.Main;
import bot.ellie.utils.controlli.ControlliAutoRensponse;
import bot.ellie.utils.messages.Errors;

public class FileUtils {
  
  /**Controllo delle parolacce e degli insulti nel messaggio
	 * 
	 * @param messaggio in arrivo
	 * @return numero riga del testo del messaggio in arrivo, oppure -1 in caso non ci sia
	 * @throws IOException
	 */
	private static boolean checkInsulti(String s) throws IOException {
		//converto prima lettera in maiuscola
		
		String word = "-" + s;
		boolean b = false;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/insulti.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;

			while ((line = lnr.readLine ()) != null) {
			    if (word.toLowerCase().contains(line.toLowerCase())) {
			    	b = true;
			    	break;
			    }   
			}
			
			lnr.close();
			fr.close();
			return b;
	}
	
	/**Controlla se nel file risposte.txt c'è il testo del messaggio in arrivo
	 * 
	 * @param messaggio in arrivo
	 * @return numero riga del testo del messaggio in arrivo, oppure -1 in caso non ci sia
	 * @throws IOException
	 */
	private static int leggiFileRisposta(String s) throws IOException {
		//converto prima lettera in maiuscola
		
		String word = "-" + s;
		int n = -1;
	    	FileReader fr = new FileReader (Main.PATH_INSTALLAZIONE + "/readfiles/risposte.txt");
			LineNumberReader lnr = new LineNumberReader (fr);
			String line;

			while ((line = lnr.readLine ()) != null) {
			    if (line.toLowerCase().contains(word.toLowerCase())) {
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
	private static String rispondiFileRisposta(int riga) throws IOException {
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
	private static int leggiFileRispostaInfo(String s) throws IOException {
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
	private static String rispondiFileRispostaInfo(int riga) throws IOException {
			
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
	
	
	public static String leggiFilesRisposte(String testoMessaggio) {
		String risposta = null;
		Random random = new Random();

		try {
			if (FileUtils.checkInsulti(testoMessaggio)) {
				return "Non apprezzo le parolacce, evitiamo certi termini 😊";
			}
		} catch (IOException e1) {
			Main.log.error(e1);
		}

		if (risposta == null) {
			// cerco nel file risposte.txt
			int n = -1;
			try {
				n = FileUtils.leggiFileRisposta(testoMessaggio);
			} catch (IOException ex) {
				Main.log.error(ex);
			}
			if (n == -1) { // testo messaggio non trovato
				try {
					n = leggiFileRispostaInfo(testoMessaggio);
				} catch (IOException ex) {
					Main.log.error(ex);
					;
				}
				if (n != -1) {
					try {
						risposta = FileUtils.rispondiFileRispostaInfo(n + 1);
					} catch (IOException ex) {
						Main.log.error(ex);
					}
				} else {
					// in caso non trovo una risposta in locale mando a bot host
					return null;
				}
			} else { // testo trovato
				int ran = random.nextInt(3);
				n = ran + n + 1;
				try {
					risposta = FileUtils.rispondiFileRisposta(n);
				} catch (IOException ex) {
					Main.log.error(ex);
					risposta = Errors.RESPONSE_NOT_FOUND;
				}
			}
		}
		return risposta;
	}
	
}
