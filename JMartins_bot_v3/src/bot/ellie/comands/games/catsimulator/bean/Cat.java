package bot.ellie.comands.games.catsimulator.bean;

import java.util.Random;

import bot.ellie.utils.Costants;

public class Cat extends CatBase {
	
	public Cat() {
		generateID();
		fame = 75;
		sete = 75;
		sonno = 80;
		condizione = 95;
		umore = 80;
		malattia = null;
		
		obbedienza = 50;
	}
	
	
	
	private int generateID() {
		Random rand = new Random();
		id = rand.nextInt(Costants.N_ID_CAT);
		return id;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
	
	
}
