package bot.ellie.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompleannoEllie {
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy");
	private static Date date = new Date();
	private static String annoString = new String(dateFormat.format(date)); //2016
	
	/**Richiamare il metodo in occasione del compleanno di ellie;
	 * 
	 * @return messaggio di auguri per il compleanno di Ellie
	 */
	public static String getAuguriDiEllie() {
		String mex;
		int anno = Integer.parseInt(annoString);
		anno = anno - 2016;
		
		switch(anno) {
		case 1: 
			mex = "Ellie compie un anno!! Wow è già passato così tanto tempo. Voglio ringraziare davvero il mio papà ❤";
			break;
		case 2:
			mex = "Tanti auguri a Ellie! Sono già passati due anni? non ci credo, grazie al mio papà ❤";
			break;
		case 3:
			mex = "Tre anni? Compio 3 anni oggi? Un grazie per ogni giorno di vita, quindi 1096 volte grazie a papà ❤";
			break;
		default:
			mex = "Tanti auguri a me! ❤";
		}
		
		
		return mex;
	}

}
