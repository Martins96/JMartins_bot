package bot.ellie.utils;

public class User {
	
	private String nomeUtente;
	
	
	/**
	 * oggetto per gestione dell'utente
	 */
	public User() {
		nomeUtente = null;
	}
	
	
	
	/**aggiunge l'id alla lista utenti in base alla password inserita
	* 
	* @param password
	* @param id
	* @return esito
	*/
	public String aggiungiUser(String password, long id) {
		switch(password)
		{
		case("Scoiattolo123"): //accesso Gaia
			nomeUtente = "Gaia";
		    return "Hey, ciao Gaia, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Alesnap123"): //accesso Ale
			nomeUtente = "Ale";
			return "Hey, ciao Ale, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Ciaolol")://accesso Matte
			nomeUtente = "Matte";
			return "Hey, ciao Matte, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Tumama69")://accesso Dinu
			nomeUtente = "Dinu";
			return "Hey, ciao Alberto, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Jamal")://accesso Vale
			nomeUtente = "Vale";
			return "Hey, ciao Vale, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Spongebob32")://accesso Pol
			nomeUtente = "Pol";
			return "Hey, ciao Pol, sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		
		case("Ginseng")://Accesso Andrea
			nomeUtente = "Andrea";
			return "Hey, ciao Andrea! sono contenta di sentirti 😊 - ricorda che per informazioni sulle tue funzioni da utente devi digitare il comando /userhelp";
		}
		
		return "Non credo di conoscerti, scusa";
	}
	
	/**controlla se l'id inserito è nella lista degli user
	 * 
	 * @param  id
	 * @return true se l'utente è loggato, altrimenti false
	 */
 	public boolean isLogged()
	{
 		if(nomeUtente == null)
 			return false;
 		return true;
	}
 	
 	public String rimuoviUser(long userdarimuovere)
	{
		if(nomeUtente != null) {
			String s = nomeUtente;
			nomeUtente = null;
			return "Privilegio da USER rimosso, buona giornata " + s + " 😊";
		} else {
		return "Non sei nella lista degli user";
		}
	}
 	
 	public String getName() {
 		return nomeUtente;
 	}
	
}
