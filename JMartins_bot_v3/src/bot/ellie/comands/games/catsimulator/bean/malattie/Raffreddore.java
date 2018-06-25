package bot.ellie.comands.games.catsimulator.bean.malattie;

public class Raffreddore extends Malattia {
	
	public Raffreddore() {
		nome = "Raffreddore";
		descrizione = "Malattia comune, non è tanto grave, ma meglio non sottovalutarla";
		descrMorte = "Il raffreddore gli è stato fatale";
		danno = 1;
		probabilitaDiCura = 25;
		curato = false;
	}
}
