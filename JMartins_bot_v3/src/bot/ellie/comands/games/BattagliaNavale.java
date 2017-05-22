package bot.ellie.comands.games;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;
import bot.ellie.utils.games.UtilBattagliaNavale;
import bot.ellie.utils.messages.Errors;
import bot.ellie.utils.messages.Help;
import bot.ellie.utils.messages.Messages;

/**
 * 🌊 	= 	acqua
 * 💦  	=	acqua mancata
 * ❔ 	=	sconosciuto
 * 🚢	=	nave
 * 🐬	=	pesce fortunato
 * 🎣 	=	pesce colpito
 *
 */
public class BattagliaNavale extends UtilBattagliaNavale{
	
	private int idthread;
	private int iduser;
	
	private String[][] ellieField = new String[FIELD_SIZE][FIELD_SIZE];
	private String[][] playerField = new String[FIELD_SIZE][FIELD_SIZE];
	private String[][] visibleEllieField = new String[FIELD_SIZE][FIELD_SIZE];
	private boolean[][] playerFieldAvviable4Ellie = new boolean[FIELD_SIZE][FIELD_SIZE];
	private int naviRimanentiEllie = 9;
	private int naviRimanentiPlayer = 9;
	
	
	
	public BattagliaNavale(int idthread, Message messaggio) {
		this.idthread = idthread;
		this.iduser = messaggio.from().id();
	}
	
	public String startGame() {
		//inizializzo gioco
		sendInfoGame();
		initMatrix();
		boolean exit = false;
		
		//inizio gioco
		do {
			stampInfoField();
			sendMessage("Inserisci le coordinate da colpire:");
			String input = Getter.attendiMessaggio(idthread);
			if(isInputValid(input)) {
				if(input.equalsIgnoreCase("/exit"))
					return "Uscita dal gioco";
				
				int x = getCoordinateX(input);
				int y =	getCoordinateY(input);
				if(x == -1 || y == -1) {
					sendMessage(Errors.BATTAGLIANAVALE_INPUT_NOT_VALID);
				} else { //coordinate valido
					if(isCellValidForPlayer(x, y, visibleEllieField)) {
						String cellHit = getEllieCellHitted(x, y);
						if(cellHit.equals(BOAT))
							naviRimanentiEllie--;
						if(cellHit.equals(LUCKY_FISH))
							return Messages.BATTAGLIANAVALE_WIN_LUCKY_FISH;
						if(naviRimanentiEllie <= 0) {
							return Messages.BATTAGLIANAVALE_WIN_BOATS;
						}
						
						//TURNO DI ELLIE
						String cell = thinkEllie();
						//gestisco la cella selezionata da ellie
						if(cell.equals(BOAT)) {
							naviRimanentiPlayer--;
						}
						if(cell.equals(LUCKY_FISH)) {
							stampInfoField();
							return Messages.BATTAGLIANAVALE_LOSE_LUCKY_FISH;
						}
						if(naviRimanentiPlayer <= 0) {
							stampInfoField();
							return Messages.BATTAGLIANAVALE_LOSE_BOATS;
						}
						
					} else {
						sendMessage(Errors.BATTAGLIANAVALE_SELECTION_NOT_VALID);
					}
				}
			} else { //input non valido
				sendMessage(Errors.BATTAGLIANAVALE_INPUT_NOT_VALID);
			}
		} while(exit != true);
		
		sendMessage(stampField(ellieField));
		
		
		return "Gioco terminato";
	}
	
	
	
	
	//------------------------------------------------------------------------------------------------------
	//	XXX utilFunction
	//------------------------------------------------------------------------------------------------------
	
	private void sendInfoGame() {
		sendMessage(Help.BATTAGLIA_NAVALE_HELP);
	}
	
	private void initMatrix() {
		for(int i = 0; i < FIELD_SIZE; i++) {
			for(int j = 0; j < FIELD_SIZE; j++){
				ellieField[i][j] = WATER;
				playerField[i][j] = WATER;
				visibleEllieField[i][j] = UNKNOWN;
				playerFieldAvviable4Ellie[i][j] = false;
			}
		}
		//inizializzo campi di battaglia
		ellieField = generateHorizontalShip(ellieField);
		ellieField = generateHorizontalShip(ellieField);
		ellieField = generateVerticalShip(ellieField);
		
		playerField = generateHorizontalShip(playerField);
		playerField = generateHorizontalShip(playerField);
		playerField = generateVerticalShip(playerField);
		
		ellieField = setLuckyFish(ellieField);
		playerField = setLuckyFish(playerField);
	}
	
	private String getEllieCellHitted(int x, int y) {
		String cell = ellieField[y][x];
		visibleEllieField[y][x] = cell;
		sendMessage("Hai colpito: " + cell);
		return cell;
	}
	
	private String thinkEllie() {
		int x, y;
		do {
		x = random.nextInt(FIELD_SIZE);
		y = random.nextInt(FIELD_SIZE);
		} while(playerFieldAvviable4Ellie[y][x]);
		//trovate coordinate valide
		String cell = playerField[y][x];
		if(cell.equals(WATER)) {
			sendMessage("Ellie ha colpito l'acqua " + WATER);
			playerField[y][x] = WATER_MISSED;
		}
		if(cell.equals(BOAT)) {
			sendMessage("Ellie ha colpito una nave " + BOAT);
			playerField[y][x] = HIT;
		}
		if(cell.equals(LUCKY_FISH)) {
			sendMessage("Ellie ha colpito il pesce fortunato " + LUCKY_FISH) ;
			playerField[y][x] = FISH_HITTED;
		}
		return cell;
			
	}
	
	private void stampInfoField() {
		sendMessage("TUO CAMPO\n\n" + stampField(playerField));
		sendMessage("CAMPO DI ELLIE\n\n" + stampField(visibleEllieField));
		
	}
	
	private String stampField(String m[][]) {
		String s = new String("\\ A : B : C : D : E : F\n");
		
		for(int i = 0; i < FIELD_SIZE; i++) {
			s = s + (i + 1);
			for(int j = 0; j < FIELD_SIZE; j++){
				s = s + " " + m[i][j];
			}
			s = s + "\n\n";
		}
		
		return s;
	}
	
	//------------------------------------------------------------------------------
	
	private void sendMessage(String text) {
		Sender.sendMessage(iduser, text);
	}
	
}
