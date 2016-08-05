package bot.ellie.comands;

import bot.ellie.Main;

public class Postino {

public Postino() {
}
	/**Spedisce un messaggio in modalità admin
	 * 
	 * @param id del destinatario, messaggio da spedire
	 * @return stringa di risultato se il messaggio è stato spedito
	 */
	public String consegnaMessaggioMartins(String destinatario, String testo)
	{
		String risultato = new String("Messaggio non consegnato!");
		destinatario = destinatario.toLowerCase();
		long id = 0;
		
		switch(destinatario)
		{
			case("martins"):
				id = 115949778;
				break;
			case("gaia"):
				id = 124796388;
				break;
			case("ale"):
				id = 143009205;
				break;
			case("calvi"):
				id = 180117484;
				break;
			case("matteo"):
				id = 76891271;
				break;
			case("francesco"):
				id = 170595365;
				break;
			case("dinu"):
				id = 148278619;
				break;
			case("pol"):
				id = 196422609;
				break;
			case("vale"):
				id = 104412734;
				break;
			case("bruno"):
				id = 75443863;
				break;
			case("manara"):
				id = 125212616;
				break;
			case("andrea"):
				id = 27585212;
			break;
			case("parmraj"):
				id = 164741728;
			break;
			default:
				id = -1;
				break; 
		}
		if (id == -1)
		{
			risultato = "Errore: destinatario non conosciuto";
		}
		else
		{
			Main.sendMessage(id, "Martins mi ha chiesto di scriverti questo 😊: \n" + testo);
			Main.log.info("Inviato messaggio in modalità ADMIN a: " + id);
			risultato = "Ho inviato il messaggio all'id: " + id + " (" + destinatario + ") 😄";
		}
		
		return risultato;
	}
	
	/**Spedisce un messaggio in modalità user
	 * 
	 * @param id del destinatario, messaggio da spedire
	 * @return stringa di risultato se il messaggio è stato spedito
	 */
	public String consegnaMessaggio(String destinatario, String testo, String mittente)
	{
		String risultato = new String("Messaggio non consegnato!");
		long id = 0;
		destinatario = destinatario.toLowerCase();
		switch(destinatario)
		{
			case("martins"):
				id = 115949778;
				break;
			case("gaia"):
				id = 124796388;
				break;
			case("ale"):
				id = 143009205;
				break;
			case("calvi"):
				id = 180117484;
				break;
			case("matteo"):
				id = 76891271;
				break;
			case("dinu"):
				id = 148278619;
				break;
			case("pol"):
				id = 196422609;
				break;
			case("vale"):
				id = 104412734;
				break;
			default:
				id = -1;
				break; 
		}
		if (id == -1)
		{
			risultato = "Errore: perdonami ma non conosco il destinatario, per una lista dei destinatari che conosco digita '/postino help'";
		}
		else
		{
			Main.sendMessage(id, mittente +" mi ha chiesto di scriverti questo 😊: \n" + testo);
			Main.log.info(mittente + " ha inviato messaggio in modalità USER a: " + id);
			risultato = "Ho inviato il messaggio all'id: " + id + " (" + destinatario + ") 😄";
		}
		
		return risultato;
	}
	
	public String helpadmin()
	{
		return "Aiuto per il comando /postino:\n"
				+ "SINTASSI:\n"
				+ "/postino 'nome del destinatario' 'messaggio da inviare'\n"
				+ "ESEMPIO:\n\n"
				+ "/postino Gigi Hey andiamo alla sagra del coniglio domani sera?\n"
				+ "\nID contatti conosciuti:\n"
				+ "'Martins' - 115949778\n"
				+ "'Gaia'    - 124796388\n"
				+ "'Ale'     - 143009205\n"
				+ "'Calvi'   - 180117484\n"
				+ "'Matteo' (Fratello Martins)   - 76891271\n"
				+ "'Francesco'  - 170595365\n"
				+ "'Manara'     - 125212616\n"
				+ "'Bruno'      - 75443863\n"
				+ "'Dinu'       - 148278619\n"
				+ "'Vale'       - 104412734\n"
				+ "'Pol'        - 196422609\n"
				+ "'Andrea'		- 27585212\n"
				+ "'Parmraj'    - 164741728\n";
	}
	
	public String helpuser()
	{
		return "Aiuto per il comando /postino:\n"
				+ "SINTASSI:\n"
				+ "/postino 'nome del destinatario' 'messaggio da inviare'\n"
				+ "ESEMPIO:\n\n"
				+ "/postino Gianni Hey andiamo alla sagra del coniglio domani sera?\n"
				+ "\nID contatti conosciuti:\n"
				+ "'Martins' - 115949778\n"
				+ "'Gaia'    - 124796388\n"
				+ "'Ale'     - 143009205\n"
				+ "'Calvi'   - 180117484\n"
				+ "'Matteo' (Fratello Martins)   - 76891271\n"
				+ "'Dinu'    - 148278619\n"
				+ "'Vale'       - 104412734\n"
				+ "'Pol'        - 196422609\n"
				+ "'Andrea'		- 27585212\n";
	}
	
	public String syshelp()
	{
		return "Comando da Admin:\n"
				+ "Invio un messaggio pulito al destinatario\n"
				+ "SINTASSI:\n"
				+ "/system 'nome del destinatario' 'messaggio da inviare'"
				+ "\nID contatti conosciuti:\n"
				+ "'Martins' - 115949778\n"
				+ "'Gaia'    - 124796388\n"
				+ "'Ale'     - 143009205\n"
				+ "'Calvi'   - 180117484\n"
				+ "'Matteo' (Fratello Martins)   - 76891271\n"
				+ "'Manara'     - 125212616\n"
				+ "'Bruno'      - 75443863\n"
				+ "'Dinu'  	    - 148278619\n"
				+ "'Vale'       - 104412734\n"
				+ "'Pol'        - 196422609\n"
				+ "'Andrea'		- 27585212\n"
				+ "'Parmraj'    - 164741728\n";
	}
	
	public String sysMessaggio(String destinatario, String testomex)
	{
		String risultato = new String("Messaggio non consegnato!");
		destinatario = destinatario.toLowerCase();
		long id = 0;
		
		switch(destinatario)
		{
			case("martins"):
				id = 115949778;
				break;
			case("gaia"):
				id = 124796388;
				break;
			case("ale"):
				id = 143009205;
				break;
			case("matteo"):
				id = 76891271;
				break;
			case("francesco"):
				id = 170595365;
				break;
			case("dinu"):
				id = 148278619;
				break;
			case("pol"):
				id = 196422609;
				break;
			case("vale"):
				id = 104412734;
				break;
			case("manara"):
				id = 125212616;
			case("bruno"):
				id = 75443863;
				break;
			case("andrea"):
				id = 27585212;
			break;
			case("parmraj"):
				id = 164741728;
			break;
			default:
				id = -1;
				break; 
		}
		if (id == -1)
		{
			risultato = "Errore system: destinatario non conosciuto";
		}
		else
		{
			Main.sendMessage(id, testomex);
			Main.log.info("Inviato messaggio in modalità SYSTEM a: " + id);
			risultato = "Ok papà! Ho inviato il messaggio pulito all'id: " + id + " (" + destinatario + ") 😄";
		}
		
		return risultato;
	}
}

/*Id conosciuti:
 * 
 * Martins    			- 115949778
 * Matte   			    - 76891271
 * Gaia       			- 124796388
 * Ale        			- 143009205
 * Calvie				- 180117484
 * Francesco  			- 170595365
 * Dinu       			- 148278619
 * Davide Colleoni  	- 71188575
 * Manara				- 125212616
 * Bruno 				- 75443863
 * Andrea 	        	- 27585212
 * Parmraj Singh        - 164741728
 * 
 */

