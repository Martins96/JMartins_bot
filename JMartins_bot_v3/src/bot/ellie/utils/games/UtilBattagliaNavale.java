package bot.ellie.utils.games;

import java.util.Random;

public class UtilBattagliaNavale {
	
	protected static Random random = new Random();
	
	protected static final short FIELD_SIZE 	= 	6;
	protected static final String WATER 		= 	"🌊";
	protected static final String WATER_MISSED	= 	"💦";
	protected static final String UNKNOWN		=	"❔";
	protected static final String BOAT			=  	"🚢";
	protected static final String HIT			= 	"💥";
	protected static final String LUCKY_FISH	=	"🐬";
	protected static final String FISH_HITTED 	=	"🎣";

	protected static String[][] generateHorizontalShip(String[][] m) {
		int x, y;
		boolean ok = false;
		do {
		y = random.nextInt(FIELD_SIZE - 3);
		x = random.nextInt(FIELD_SIZE);
		
		if(m[y][x].equals(WATER) //controllo spazio libero
			&& m[y+1][x].equals(WATER)
			&& m[y+2][x].equals(WATER))
			ok = true; //condizioni per mettere la barca possibili
		} while(ok!=true);
		m[y][x] = BOAT;
		m[y+1][x] = BOAT;
		m[y+2][x] = BOAT;
		return m;
	}
	
	protected static String[][] generateVerticalShip(String[][] m) {
		int x, y;
		boolean ok = false;
		do {
		x = random.nextInt(FIELD_SIZE - 3);
		y = random.nextInt(FIELD_SIZE);
		
		if(m[y][x].equals(WATER) //controllo spazio libero
			&& m[y][x+1].equals(WATER)
			&& m[y][x+2].equals(WATER))
			ok = true; //condizioni per mettere la barca possibili
		} while(ok!=true);
		m[y][x] = BOAT;
		m[y][x+1] = BOAT;
		m[y][x+2] = BOAT;
		return m;
	}
	
	protected static String[][] setLuckyFish(String[][] m) {
		int x, y;
		boolean valid = false;
		do {
		x = random.nextInt(FIELD_SIZE);
		y = random.nextInt(FIELD_SIZE);
		if(m[y][x].equals(WATER))
			valid = true;
		} while(valid != true);
		
		m[y][x] = LUCKY_FISH;
		return m;
	}
	
	protected static boolean isInputValid(String input) {
		if(input == null)
			return false;
		if(input.equals("/exit") || input.equals("/quit"))
			return true;
		if(input.length() == 2)
			return true;
		return false;
	}
	
	protected static boolean isCellValidForPlayer(int x, int y, String[][] m) {
		if(x < 0 || y < 0 || m == null)
			return false;
		try {
			String cell = m[y][x];
			if(cell == null)
				return false;
			
			if(cell.equals(UNKNOWN))
				return true;
			
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}
	
	protected static int getCoordinateX(String input) {
		if(input == null || input.length() < 2)
			return -1;
		switch(input.substring(0, 1).toUpperCase()) {
		case "A":
			return 0;
		case "B":
			return 1;
		case "C":
			return 2;
		case "D":
			return 3;
		case "E":
			return 4;
		case "F":
			return 5;
		default:
			return -1;
		}
	}
	
	protected static int getCoordinateY(String input) {
		if(input == null || input.length() < 2)
			return -1;
		try {
			int y = Integer.parseInt(input.substring(1, 2));
			y--; //sottraggo di uno per indice da array
			if(y >= 0 && y < FIELD_SIZE)
				return y; //coordinata valida
		} catch (NumberFormatException e) { //coordinata non numerale
			return -1;
		}
		return -1;
	}
	
	
}
