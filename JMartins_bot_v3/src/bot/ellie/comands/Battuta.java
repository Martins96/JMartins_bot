package bot.ellie.comands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import bot.ellie.Main;

public class Battuta {
	
	public static final int N_BATTUTE = 173;

	public Battuta() {
	}
	/** legge da un file una riga contenente una battuta
	 * 
	 * @param numero della battuta da dire
	 */
	public String generaBattuta(int i) throws IOException
	{
		String file = Main.PATH_INSTALLAZIONE + "/readfiles/battute.txt";
		
		FileReader f;
	    f=new FileReader(file);
		    BufferedReader b;
	    b=new BufferedReader(f);
	    String t = b.readLine();
	    for(int j = 1; i > j; j++)
	    {
	    	t = b.readLine();
	    }
    	t = b.readLine();
    	b.close();
    	f.close();
		return t;
	}
	
	public static String comandoBattuta(String[] messaggio, int id) {
		String battutastring = new String ("");
		Random random = new Random();
		int b = 0;
		Battuta battuta = new Battuta();
		boolean check = true;
		String parametroBattuta = "";
		if(check)
		{
			String s = new String();
			//sistemo parametro battuta
			if (messaggio.length > 1)
			{
				for (int i = 1; messaggio.length > i; i++)
					s = s + messaggio[i];
				parametroBattuta = s;
			}
		}
		switch (parametroBattuta.toLowerCase())
		{
		case (""):
			b = random.nextInt(173) + 1;
			break;
		case ("chucknorris"):
			b = random.nextInt(54) + 50;
			break;
		case ("help"):
			b = -1;
			break;
		case ("carabinieri"):
			b = random.nextInt(17) + 110;
			break;
		case ("carabiniere"):
			b = random.nextInt(17) + 110;
			break;
		default:
				Main.sendMessage(id, "Parametro non riconosciuto, ti mando una battuta casuale");
			b = random.nextInt(127) + 1;
			break;
		}
		if(b == -1)
		{
			battutastring = "Help di /battuta:\n"
					+ "\nGenera una battuta casuale, puoi utilizzare parametri per battute personalizzate, esempio:\n\n /battuta chuck norris \n\ni parametri disponibili sono: \nChuck norris\nCarabinieri\nhelp";
		}
		else
		{
			try {
				battutastring = battuta.generaBattuta(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Main.log.info("battuta generata n. " + b);
		return battutastring;
	}
}
