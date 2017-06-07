package bot.ellie.utils.controlli;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pengrad.telegrambot.model.Message;

public class ControlliMessaggiRicevuti {
	
	/**Controllo input dei mi chiamo, etc...
	 * 
	 * @param messaggio
	 * @return risposta
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static String ioMiChiamo(Message messaggio) throws ArrayIndexOutOfBoundsException {


		if(messaggio.text().length() > 9)
		{
			if(messaggio.text().substring(0, 9).equals("Mi chiamo"))
			{
				String nome = new String("");
				for(int i = 10; messaggio.text().length() > i; i++)
				{
					if(messaggio.text().substring(i, i+1).equals(" "))
					{
						if(!nome.equals(""))
							return "Ciao " + nome.substring(0, 1).toUpperCase() + nome.substring(1, nome.length()).toLowerCase() + ", io sono Ellie ðŸ˜„";
						else
							return "Beh, è una cosa strana chiamarsi da soli, comunque io sono Ellie 😄";
					}
					nome = nome + messaggio.text().substring(i, i+1);
				}
				if(!nome.equals(""))
					return "Ciao " + nome.substring(0, 1).toUpperCase() + nome.substring(1, nome.length()).toLowerCase() + ", io sono Ellie ðŸ˜„";
				else
					return "Beh, è una cosa strana chiamarsi da soli, comunque io sono Ellie 😄";
			}
		}
		if(messaggio.text().length() > 13)
		{
			if(messaggio.text().substring(0, 13).equals("Il mio nome è"))
			{
				String nome = new String("");
				for(int i = 14; messaggio.text().length() > i; i++)
				{
					if(messaggio.text().substring(i, i+1).equals(" "))
						if(!nome.equals(""))
							return "Ciao " + nome.substring(0, 1).toUpperCase() + nome.substring(1, nome.length()).toLowerCase() + ", io sono Ellie ðŸ˜„";
						else
							return "Il tuo nome è o non è 😄";
					else
						nome = nome + messaggio.text().substring(i, i+1);
				}
				if(!nome.equals(""))
					return "Ciao " + nome + ", io sono Ellie 😄";
				else
					return "Il tuo nome è o non è 😄";
			}
		}
		if(messaggio.text().length() > 7)
		{
			if(messaggio.text().substring(0, 7).equals("Io sono"))
			{
				String nome = new String("");
				for(int i = 8; messaggio.text().length() > i; i++)
				{
					if(messaggio.text().substring(i, i+1).equals(" "))
						if(!nome.equals(""))
							return "Ciao " + nome + ", io sono Ellie 😄";
						else
							return "Tu sei, Egli è";
					else
						nome = nome + messaggio.text().substring(i, i+1);
				}
				if(!nome.equals(""))
					return "Ciao " + nome + ", io sono Ellie";
				else
					return "Tu sei, Egli è";
			}
		}
		
		return null;
	
	}
	
	/**trasformo una frase semplice in un comando
	 * 
	 * @param messaggio
	 * @return comando
	 */
	public static String setComandoDaFrase(Message messaggio) {
		if(messaggio.text() == null)
			return "";
		
		if(messaggio.text().equals("Sono triste"))
		{
			return "/battuta";
		}
		if(messaggio.text().equals("Che ore sono?"))
		{
			return "/ora";
		}
		if(messaggio.text().equals("Sai che ore sono?"))
		{
			return "/ora";
		}
		if(messaggio.text().equals("Mi puoi dire l'ora?"))
		{
			return "/ora";
		}
		if(messaggio.text().equals("Impiccato?"))
		{
			return "/impiccato";
		}
		if(messaggio.text().equals("Blackjack?"))
		{
			return "/blackjack";
		}
		if(messaggio.text().equals("Avvia l'impiccato"))
		{
			return "/impiccato";
		}
		if(messaggio.text().equals("Avvia impiccato"))
		{
			return "/impiccato";
		}
		if(messaggio.text().equals("Avvia blackjack"))
		{
			return "/blackjack";
		}
		if(messaggio.text().equals("Avvia il blackjack"))
		{
			return "/blackjack";
		}
		return "";
	}
	
	/**Genero risposta per domande casuali
	 * 
	 * @param messaggio
	 * @return risposta se trovata, altrimenti <code>null</code>
	 */
	public static String risposteVeloci(Message msg) {
		String result = null;
		
		if(msg == null || msg.text() == null)
			return null;
		
		if("/cute".equalsIgnoreCase(msg.text()) ||
				"/nature".equalsIgnoreCase(msg.text()) ||
				"/funny".equalsIgnoreCase(msg.text()))
			return "comando non riconosciuto, ridigita '/foto' e poi scegli la categoria";
		
		if("Quanti anni hai?".equalsIgnoreCase(msg.text())) {
			SimpleDateFormat format = new SimpleDateFormat("YYYY");
			Date data = new Date();
			int anniEllie = Integer.parseInt(format.format(data)) - 2016;
			
			result = "Ho " + anniEllie;
			result = result + (anniEllie == 1 ? " anno" : " anni");
		}
		
		return result;
	}
	
	
}
