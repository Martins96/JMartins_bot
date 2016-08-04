package bot.ellie.utils;

public class Bloccati {
	
	int i = 2;
	String[] idbloccati = new String[10];

	public Bloccati() {
		//aggiungere l'id dell'utente da bloccare qua sotto
		idbloccati[1] = "";
	}
	
	/** restituisce true se l'id del mittente è nell'elenco dei bloccati, altrimenti restituisce false
	 * @author martins
	 * @param id da controllare
	 */
	public boolean controllaBloccato(String id)
	{
		boolean risultato = false;
		for(int j=0; j<i; j++)
			if(id.equals(idbloccati[j]))
				risultato = true;
		return risultato;
	}

}