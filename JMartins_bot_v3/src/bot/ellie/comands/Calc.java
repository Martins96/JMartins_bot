package bot.ellie.comands;

import java.util.Random;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.Main;
import bot.ellie.utils.Sender;

public class Calc {
	
	public static String help() {
		return "Funzione di calcolo numerale:\n"
					+ "Fai eseguire i tuoi conti a Ellie! Lei lo fa velocemente e non sbaglia!\n"
					+ "Comandi:\n"
					+ "/+ numero1 numero2 numero3 etc...\n"
					+ "Esegue un addizione tra i numeri inseriti\n"
					+ "\n/- numero1 numero2 etc...\n"
					+ "Esegue una sottrazione tra i numeri inseriti\n"
					+ "\n/x numero1 numero2 etc...\n"
					+ "Esegue una moltiplicazione tra i numeri inseriti\n"
					+ "\n/: numero1 numero2\n"
					+ "Esegue una divisione tra i numeri inseriti (solo 2)\n"
					+ "\n/media numero1 numero2 numero3 etc...\n"
					+ "Esegue la media dei numeri inseriti\n"
					+ "\n/sqrt numero\n"
					+ "Esegue la radice quadtrata del numero inserito (solo 1)\n"
					+ "\n/rand numero\n"
					+ "Genero un numero casuale tra 0 (inclusivo) e il numero inserito (esclusivo)\n"
					+ "\n/letterarand\n"
					+ "Genera una lettera dell'alfabeto in modo casuale\n"
					+ "\nEsempi:\n"
					+ "/+ 37 84\n"
					+ "/+ 1 2 3 4 5\n"
					+ "/- 60 10 30 5\n"
					+ "/rand 11\n"
					+ "/media 6 8 9 7 5 10";
	}
	
	public static String comandoSomma(Message messaggio) {
		String n1string = new String("");
		boolean firsttime = true;
		double n1 = 0;
		double somma = 0;

		for (int j = 3; messaggio.text().length() > j; j++) {
			if (messaggio.text().substring(j, j + 1).equals(" ")) {
				try {
					n1 = Double.parseDouble(n1string);
					if (firsttime) {
						somma = n1;
						firsttime = false;
					} else
						somma = n1 + somma;
				} catch (NumberFormatException e) {
					Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
				}
				n1string = new String("");
			} else {
				if (messaggio.text().substring(j, j + 1).equals(","))
					n1string = n1string + ".";
				else
					n1string = n1string + messaggio.text().substring(j, j + 1);
			}
		}
		try {
			n1 = Double.parseDouble(n1string);
			somma = n1 + somma;
		} catch (NumberFormatException e) {
			Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
		}

		if (firsttime)
			return "Non è stato possibile effettuare la somma.\n Per una guida su questa funzione digita /calc";
		else
			return "Somma : " + somma;
	}
	
	public static String comandoDifferenza(Message messaggio) {
		String n1string = new String("");
		double n1 = 0;
		double dif = 0;
		boolean firsttime = true;
		while (true) {
			for (int j = 3; messaggio.text().length() > j; j++) {
				if (messaggio.text().substring(j, j + 1).equals(" ")) {
					try {
						n1 = Double.parseDouble(n1string);
						if (firsttime) {
							dif = n1;
							firsttime = false;
						} else
							dif = dif - n1;
					} catch (NumberFormatException e) {
						Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
					}
					n1string = new String("");
				} else {
					if (messaggio.text().substring(j, j + 1).equals(","))
						n1string = n1string + ".";
					else
						n1string = n1string + messaggio.text().substring(j, j + 1);
				}
			}
			try {
				n1 = Double.parseDouble(n1string);
				dif = dif - n1;
			} catch (NumberFormatException e) {
				Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
			}
			break;
		}

		if (firsttime)
			return "Non è stato possibile eseguire la sottrazione, per informazioni sulla funzione /calc";
		else
			return "Differenza : " + dif;
	}
	
	public static String comandoProdotto(Message messaggio) {
		String n1string = new String("");
		double n1 = 0;
		double prod = 1;
		boolean firsttime = true;
		while (true) {
			for (int j = 3; messaggio.text().length() > j; j++) {
				if (messaggio.text().substring(j, j + 1).equals(" ")) {
					try {
						n1 = Double.parseDouble(n1string);
						if (firsttime) {
							prod = n1;
							firsttime = false;
						} else
							prod = prod * n1;
					} catch (NumberFormatException e) {
						Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
					}
					n1string = new String("");
				} else {
					if (messaggio.text().substring(j, j + 1).equals(","))
						n1string = n1string + ".";
					else
						n1string = n1string + messaggio.text().substring(j, j + 1);
				}
			}
			try {
				n1 = Double.parseDouble(n1string);
				prod = prod * n1;
			} catch (NumberFormatException e) {
				Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
			}
			break;
		}
		if (firsttime)
			return "Non è stato possibile eseguire la moltiplicazione, per informazioni sulla funzione /calc";
		else
			return "Prodotto : " + prod;
	}

	public static String comandoQuoziente(Message messaggio) {
		String n1string = new String("");
		String n2string = new String("");
		double n2 = 0;
		double n1 = 0;
		double div = 1;

		for (int j = 0; messaggio.text().length() > j; j++) {
			if (messaggio.text().substring(j, j + 1).equals(" ")) {
				j++;
				while (messaggio.text().length() > j) {
					if (messaggio.text().substring(j, j + 1).equals(" ")) {
						j++;
						while (messaggio.text().length() > j) {
							if (messaggio.text().substring(j, j + 1).equals(" "))
								break;
							n2string = n2string + messaggio.text().substring(j, j + 1);
							j++;
						}
						break;
					}
					n1string = n1string + messaggio.text().substring(j, j + 1);
					j++;
				}
			}
		}
		// controlli vari...
		if (n1string.equals("") || n2string.equals(""))
			return "Errore nei dati inseriti";
		if (n2string.equals("0"))
			return "Impossibile dividere per 0.\nSolo Chuck Norris può dividere per zero, io no";
		try {
			n1 = Double.parseDouble(n1string);
		} catch (NumberFormatException e) {
			Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
		}
		try {
			n2 = Double.parseDouble(n2string);
		} catch (NumberFormatException e) {
			Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
		}
		div = n1 / n2;
		return "Quoziente: " + div;
	}

	public static String comandoRadice(Message messaggio) {
		String n1string = new String("");
		double n1 = 0;
		boolean firsttime = true;

		for (int j = 6; messaggio.text().length() > j; j++) {
			if (messaggio.text().substring(j, j + 1).equals(" "))
				break;
			else {
				if (messaggio.text().substring(j, j + 1).equals(","))
					n1string = n1string + ".";
				else
					n1string = n1string + messaggio.text().substring(j, j + 1);
			}
		}
		try {
			n1 = Double.parseDouble(n1string);
			if (n1 < 0)
				return "Impossibile calcolare la radice quadrata di un numero negativo";
			n1 = Math.sqrt(n1);
			firsttime = false;
		} catch (NumberFormatException e) {
			Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
		}

		if (firsttime)
			return "Non è stato possibile eseguire la radice quadrata, per informazioni sulla funzione di calcolo digita /calc";
		else
			return "Radice: " + n1;
	}
	
	public static String comandoRandom(Message messaggio) {
			String n1string = new String("");
			Random random = new Random();
			int i1 = 0;
			
			if (messaggio.text().length() == 6)
				return "Richiesto numero per limite massimo (esclusivo)";
			
			for (int j=6; messaggio.text().length() > j; j++)
			{
				if (messaggio.text().substring(j, j+1).equals(" "))
					break;
				else
				{
					if (messaggio.text().substring(j, j+1).equals(",") || messaggio.text().substring(j, j+1).equals("."))
					{
						Sender.sendMessage(messaggio.from().id(), "Richiesto un numero senza decimali, userò il numero: " + n1string);
						break;
					}
					else
						n1string = n1string + messaggio.text().substring(j, j+1);
				}
			}
			
			try
			{	
				i1 = Integer.parseInt(n1string);
				if (i1 < 0)
					return "Richiesto un numero senza decimali e positivo";
				i1 = random.nextInt(i1);
			}
			catch(NumberFormatException e)
			{
				return "Non riesco a capire il numero: " + n1string;
            }
			return "" + i1;
	}
	
	public static String comandoMedia(Message messaggio) {
		String n1string = new String("");
		double somma = 0;
		short i = 0;
		double n1 = 0;

		for (int j = 7; messaggio.text().length() > j; j++) {
			if (messaggio.text().substring(j, j + 1).equals(" ")) {
				try {
					n1 = Double.parseDouble(n1string);
					i++;
					somma = n1 + somma;
				} catch (NumberFormatException e) {
					Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
				}
				n1string = new String("");
			} else {
				if (messaggio.text().substring(j, j + 1).equals(","))
					n1string = n1string + ".";
				else
					n1string = n1string + messaggio.text().substring(j, j + 1);
			}
		}
		try {
			n1 = Double.parseDouble(n1string);
			i++;
			somma = n1 + somma;
		} catch (NumberFormatException e) {
			Sender.sendMessage(messaggio.from().id(), "Non riseco a capire il numero: " + n1string);
		}

		if (i == 0)
			return "Non è stato possibile eseguire la media dei numeri";
		Double media = somma / i;
		return "Media: " + media;
	}
	
	/**
	 * Genera una lettera casuale.
	 * Nessun parametro richiesto
	 */
	public static String letterarand()
	{
		Random random = new Random();
		int i = random.nextInt(26);
		switch(i)
		{
			case(0):
				return "X";
			case(1):
				return "J";
			case(2):
				return "C";
			case(3):
				return "D";
			case(4):
				return "E";
			case(5):
				return "F";
			case(6):
				return "G";
			case(7):
				return "H";
			case(8):
				return "I";
			case(9):
				return "B";
			case(10):
				return "K";
			case(11):
				return "L";
			case(12):
				return "M";
			case(13):
				return "N";
			case(14):
				return "O";
			case(15):
				return "P";
			case(16):
				return "Q";
			case(17):
				return "R";
			case(18):
				return "S";
			case(19):
				return "T";
			case(20):
				return "U";
			case(21):
				return "V";
			case(22):
				return "W";
			case(23):
				return "A";
			case(24):
				return "Y";
			case(25):
				return "Z";
			default:
				return "A";
		}
	}
	
	public static String moneta() {
		Random random = new Random();
		boolean moneta = random.nextBoolean();
		if (moneta)
			return "Testa";
		return "Croce";
	}
}
