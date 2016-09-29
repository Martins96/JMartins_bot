package bot.ellie.utils;

public class ControlliAutoRensponse {
	
	/**controllo di non dire che mi chiamo in un modo diverso da Ellie
	 * 
	 * @param autoResponse da cleverbot
	 * @return la stessa frase o una frase modificata con il nome Ellie
	 */
	public static String checkAutobotNome(String autoResponse) {
		if(autoResponse.length()>12) {
			if(autoResponse.substring(0, 12).equalsIgnoreCase("io mi chiamo"))
				return "Io mi chiamo Ellie";
		}
		if(autoResponse.length()>9) {
			if(autoResponse.substring(0,9).equalsIgnoreCase("mi chiamo")) {
				return "Mi chiamo Ellie";
			}
		}
		if(autoResponse.length()>7) {
			if(autoResponse.substring(0, 7).equalsIgnoreCase("io sono")) {
				return "Io sono Ellie";
			}
		}
		if(autoResponse.length()>4) {
			if(autoResponse.substring(0, 4).equalsIgnoreCase("sono")) {
				return "Io sono Ellie";
			}
		}
		if(autoResponse.length()>11) {
			if(autoResponse.substring(0, 11).equalsIgnoreCase("il mio nome")) {
				return "Io sono Ellie";
			}
		}
		
		if(autoResponse.length()>12) {
			if(autoResponse.substring(0, 13).equalsIgnoreCase("il mio è nome")) {
				return "Io sono Ellie";
			}
		}
		if(autoResponse.length()>21) {
			if(autoResponse.substring(0, 21).equalsIgnoreCase("non è vero, mi chiamo")) {
				return "Non è vero, mi chiamo Ellie";
			}
		}
		if(autoResponse.length()>24) {
			if(autoResponse.substring(0, 24).equalsIgnoreCase("te l'ho detto, mi chiamo")) {
				return "Te l'ho detto, mi chiamo Ellie";
			}
		}
		
		// check sesso
		if(autoResponse.length()>9) {
			if(autoResponse.substring(0, 10).equalsIgnoreCase("io maschio")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>11) {
			if(autoResponse.substring(0, 12).equalsIgnoreCase("sono maschio")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>6) {
			if(autoResponse.substring(0, 7).equalsIgnoreCase("maschio")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>6) {
			if(autoResponse.substring(0, 7).equalsIgnoreCase("ragazzo")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>14) {
			if(autoResponse.substring(0, 15).equalsIgnoreCase("io sono maschio")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>9) {
			if(autoResponse.substring(0, 10).equalsIgnoreCase("io ragazzo")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>14) {
			if(autoResponse.substring(0, 15).equalsIgnoreCase("io sono ragazzo")) {
				return "Io sono un PC";
			}
		}
		if(autoResponse.length()>22) {
			if(autoResponse.substring(0, 23).equalsIgnoreCase("guarda che sono maschio")) {
				return "Cerco di fare il meeglio che posso";
			}
		}if(autoResponse.length()>14) {
			if(autoResponse.substring(0, 15).equalsIgnoreCase("Sono un maschio")) {
				return "Sono un PC";
			}
		}if(autoResponse.length()>14) {
			if(autoResponse.substring(0, 15).equalsIgnoreCase("si sono maschio"))
				return "Sono un PC";
		}
		return autoResponse;
	}
	
}
