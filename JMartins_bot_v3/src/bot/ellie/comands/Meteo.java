package bot.ellie.comands;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Meteo {
	
	private static final String KEY = "211ca915aa2d81a7b94b90993a34e4ae";

	private Meteo() {
		
	}
	
	/**
	 * 
	 * @param citta in comando es. "/Bergamo"
	 * @return risultati meteorologici
	 */
	public static String getMeteo(String citta) {
		
		String result;
		ForecastIO fio = new ForecastIO(KEY); 						//instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             				//sets the units as SI - optional
		//fio.setExcludeURL("hourly,minutely");             		//excluded the minutely and hourly reports from the reply
		fio.setLang(ForecastIO.LANG_ITALIAN);
		String[] coordinate = getCoordinateCitta(citta);
		if (coordinate[0].equals("error") || coordinate[0].equals("uscita")) {
			return coordinate[0];
		}
		fio.getForecast(coordinate[0], coordinate[1]);
		//setto risultati
		result = "";
		FIOCurrently current = new FIOCurrently(fio);
		FIODaily daily = new FIODaily(fio);
		result = result + "ORA:\nSommario: " + current.get().summary();
		if (current.get().precipProbability() != 0.0)
			result = result + "\nPossibile precipitazione: " + current.get().precipProbability();
		if (current.get().temperatureMax() != null)
			result = result + "\nTemperatura massima: " + current.get().temperatureMax();
		if(current.get().temperatureMin() != null)
			result = result + "\nTemperatira minima: " + current.get().temperatureMin();
		if (!current.get().sunriseTime().equals("no data"))
			result = result + "\nAlba alle: " + current.get().sunriseTime();
		if(!current.get().sunsetTime().equals("no data"))
			result = result + "\nTramonto alle: " + current.get().sunsetTime();
		if(current.get().humidity() != 0)
			result = result + "\nUmidità: " + (current.get().humidity()*100) + "%";
		result = result + "\n\nDOMANI:\nSommario: " + daily.getDay(1).summary();
		result = result	+ "\nPossibile precipitazione: " + daily.getDay(1).precipProbability();
		result = result	+ "\nTemperatura massima: " + daily.getDay(1).temperatureMax();
		result = result	+ "\nTemperatira minima: " + daily.getDay(1).temperatureMin();
		result = result	+ "\nAlba alle: " + daily.getDay(1).sunriseTime();
		result = result	+ "\nTramonto alle: " + daily.getDay(1).sunsetTime();
		result = result	+ "\nUmidità: " + (daily.getDay(1).humidity()*100) + "%";
		result = result	+ "\n\nDOPO-DOMANI:\nSommario: " + daily.getDay(2).summary();
		result = result	+ "\nPossibile precipitazione: " + daily.getDay(2).precipProbability();
		result = result	+ "\nTemperatura massima: " + daily.getDay(2).temperatureMax();
		result = result	+ "\nTemperatira minima: " + daily.getDay(2).temperatureMin();
		result = result	+ "\nAlba alle: " + daily.getDay(2).sunriseTime();
		result = result	+ "\nTramonto alle: " + daily.getDay(2).sunsetTime();
		result = result	+ "\nUmidità: " + (daily.getDay(2).humidity()*100) + "%";
		result = result	+ "\n\nTRA 3 GIORNI:\nSommario: " + daily.getDay(3).summary();
		result = result	+ "\nPossibile precipitazione: " + daily.getDay(3).precipProbability();
		result = result	+ "\nTemperatura massima: " + daily.getDay(3).temperatureMax();
		result = result	+ "\nTemperatira minima: " + daily.getDay(3).temperatureMin();
		result = result	+ "\nAlba alle: " + daily.getDay(3).sunriseTime();
		result = result	+ "\nTramonto alle: " + daily.getDay(3).sunsetTime();
		result = result	+ "\nUmidità: " + (daily.getDay(3).humidity()*100) + "%";
		
		
		return result;
	}
	
	public static String getWeekendMeteo(String citta) {
		String result = "";
		ForecastIO fio = new ForecastIO(KEY); 						//instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             				//sets the units as SI - optional
		fio.setLang(ForecastIO.LANG_ITALIAN);
		String[] coordinate = getCoordinateCitta(citta);
		fio.getForecast(coordinate[0], coordinate[1]);
		FIOHourly hourly = new FIOHourly(fio);
		FIODaily daily = new FIODaily(fio);
		
		result = result + "Hey ciao, ecco le previsioni del WeekEnd 😄\n";
		result = result + "\n" + "Stasera: \n" + hourly.getHour(7).time() + "  " + hourly.getHour(7).summary();
		result = result + "\n" + hourly.getHour(10).time() + "  " + hourly.getHour(10).summary();
		result = result + "\n" + hourly.getHour(13).time() + "  " + hourly.getHour(13).summary();
		result = result + "\n\n" + "Domani: \n" + daily.getDay(2).summary();
		result = result + "\n\n" + "Sabato sera: \n" + hourly.getHour(31).time() + "  " + hourly.getHour(31).summary();
		result = result + "\n" + hourly.getHour(34).time() + "  " + hourly.getHour(34).summary();
		result = result + "\n" + hourly.getHour(37).time() + "  " + hourly.getHour(37).summary();
		result = result + "\n\n" + "Domenica: \n" + daily.getDay(3).summary();
		
		return result;
	}
	
	
	
	
	private static String[] getCoordinateCitta(String citta) {
		
		String[] s = new String[2];
		switch (citta) {
			case "/Bergamo":
				s[0] = "45.683333";
				s[1] = "9.716667";
				break;
			case "/Verona":
				s[0] = "45.4222409";
				s[1] = "11.09";
				break;
			case "/Milano":
				s[0] = "45.483333";
				s[1] = "9.2";
				break;
			case "/Torino":
				s[0] = "45.1";
				s[1] = "7.8";
				break;
			case "/Roma":
				s[0] = "41.8";
				s[1] = "13";
				break;
			case "/Bari":
				s[0] = "41";
				s[1] = "16.8";
				break;
			case "/Palermo":
				s[0] = "38.1";
				s[1] = "13.5";
				break;
			case "/exit":
				s[0] = "uscita";
				break;
			default:
				s[0] = "error";
				break;
		}
		return s;
	}
}
