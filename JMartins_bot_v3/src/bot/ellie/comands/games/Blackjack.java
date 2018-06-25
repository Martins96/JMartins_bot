package bot.ellie.comands.games;

import java.util.Random;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.Main;
import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;
import bot.ellie.utils.messages.Help;
import bot.ellie.utils.messages.Messages;

public class Blackjack extends GameBase {
	
	private Random random;
	
	public Blackjack(int idthread, Message messaggio) {
		random = new Random();
		this.idthread = idthread;
		this.messaggio = messaggio;
	}
	
	@Override
	public String startGame() {
		Main.log.info("Avvio gioco Blackjack per " + messaggio.from().firstName());
		boolean[][] mazzo = new boolean[13][4];
		for (int m1 = 0; m1 < 13; m1++)
			for (int m2 = 0; m2 < 4; m2++)
				mazzo[m1][m2] = false;
		int iellie = 0, igiocatore = 0;
		String[] carteellie = new String[20], cartegiocatore = new String[20];
		String message = null;
		int temp;
		int numero, seme;
		numero = random.nextInt(13);
		seme = random.nextInt(4);
		// ---------------------------------------------------
		Sender.sendMessage(messaggio.from().id(), Help.BLACKJACK_HELP);
		while (mazzo[numero][seme]) {
			numero = random.nextInt(13);
			seme = random.nextInt(4);
		}
		mazzo[numero][seme] = true;
		carteellie[iellie] = setCartaBlackjack(numero, seme, messaggio.from().id());
		iellie++;
		numero = random.nextInt(13);
		seme = random.nextInt(4);
		while (mazzo[numero][seme]) {
			numero = random.nextInt(13);
			seme = random.nextInt(4);
		}
		mazzo[numero][seme] = true;
		mazzo[numero][seme] = true;
		cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
		igiocatore++;
		numero = random.nextInt(13);
		seme = random.nextInt(4);
		while (mazzo[numero][seme]) {
			numero = random.nextInt(13);
			seme = random.nextInt(4);
		}
		mazzo[numero][seme] = true;
		mazzo[numero][seme] = true;
		cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
		igiocatore++;

		message = messaggio.text() != null ? messaggio.text() : "";
		while (!message.equalsIgnoreCase("/exit")) {
			Sender.sendMessage(messaggio.from().id(),
					"ELLIE:\n" + "punti: " + puntiBlackjack(carteellie, iellie) + "\n"
							+ stampaCarteBlackjack(carteellie, iellie) + "\n\n\n" + "TU:\n" + "punti: "
							+ puntiBlackjack(cartegiocatore, igiocatore) + "\n"
							+ stampaCarteBlackjack(cartegiocatore, igiocatore));
			boolean check = true;
			while (check) {
				message = Getter.attendiMessaggio(idthread);
				switch (message) {
				case ("/carta"):
					numero = random.nextInt(13);
					seme = random.nextInt(4);
					while (mazzo[numero][seme]) {
						numero = random.nextInt(13);
						seme = random.nextInt(4);
					}
					mazzo[numero][seme] = true;
					mazzo[numero][seme] = true;
					cartegiocatore[igiocatore] = setCartaBlackjack(numero, seme, messaggio.from().id());
					igiocatore++;
					if ((temp = puntiBlackjack(cartegiocatore, igiocatore)) > 21)
						return "I tuoi punti sono: " + temp
								+ Messages.BLACKJACK_LOSE_UP_21;
					check = false;
					break;
				case ("/stop"):
					temp = puntiBlackjack(carteellie, iellie);
					while (temp < 17) {
						numero = random.nextInt(13);
						seme = random.nextInt(4);
						while (mazzo[numero][seme]) {
							numero = random.nextInt(13);
							seme = random.nextInt(4);
						}
						mazzo[numero][seme] = true;
						mazzo[numero][seme] = true;
						carteellie[iellie] = setCartaBlackjack(numero, seme, messaggio.from().id());
						iellie++;
						temp = puntiBlackjack(carteellie, iellie);
					}
					if (temp > 21)
						return Messages.BLACKJACK_WIN_ELLIE_UP_21;
					else {
						if (temp > puntiBlackjack(cartegiocatore, igiocatore))
							return "Ellie ha vinto con " + temp + " punti\n i tuoi punti sono: "
									+ puntiBlackjack(cartegiocatore, igiocatore);
						else if (temp < puntiBlackjack(cartegiocatore, igiocatore))
							return "Hai superato i " + temp
									+ " di Ellie, hai vinto!!!\n❤♦♣♠";
						else
							return "Pareggio, avete totalizzato entrambi " + temp + " punti";
					}
				case ("/exit"):
					
					return Messages.BLACKJACK_END;

				default:
					Sender.sendMessage(messaggio.from().id(), Messages.BLACKJACK_INVALID_INPUT);
					break;
				}
			}
		}
		
		return "Uscita eseguita, blackjack terminato";
	}
	
	
	
	
	private String setCartaBlackjack(int numero, int seme, Object chatid)
	{
		String s, s2;
		numero++;
		switch(numero)
		{
		case(11):
			s = "J";
			break;
		case(12):
			s = "Q";
			break;
		case(13):
			s = "K";
			break;
		default:
			s = "" + numero;
		}
		switch(seme)
		{
		case(0):
			s2 = "cuori";
			break;
		case(1):
			s2 = "quadri";
			break;
		case(2):
			s2 = "fiori";
			break;
		case(3):
			s2 = "picche";
			break;
		default :
			s2 = "cuori";
			break;
		}
		Sender.sendMessage(chatid, "Ho pescato: " + s + " di " + s2);
		return s;
	}
	
	
	private String stampaCarteBlackjack(String[] carte, int icarte)
	{
		String s = new String("-  ");
		for(int i = 0; i<icarte; i++)
			s = s + carte[i] + "  -  ";
		return s;
	}
	
	
	private int puntiBlackjack(String[] carte, int icarte) {
		int punti = 0;
		int assi = 0;
		
		for(int i = 0; i<icarte; i++) {
			switch(carte[i]) {
			case("1"):
				assi++;
				break;
			case("2"):
				punti = punti + 2;
				break;
			case("3"):
				punti = punti + 3;
				break;
			case("4"):
				punti = punti + 4;
				break;
			case("5"):
				punti = punti + 5;
				break;
			case("6"):
				punti = punti + 6;
				break;
			case("7"):
				punti = punti + 7;
				break;
			case("8"):
				punti = punti + 8;
				break;
			case("9"):
				punti = punti + 9;
				break;
			case("10"):
				punti = punti + 10;
				break;
			case("J"):
				punti = punti + 10;
				break;
			case("Q"):
				punti = punti + 10;
				break;
			case("K"):
				punti = punti + 10;
				break;
			}
		}
			while(assi>1) {
				punti++;
				assi--;
			}
			if(assi==1) {
				if(punti+11<=21)
					punti = punti+11;
				else
					punti++;
			}
		
		return punti;
	}
	
	
}
