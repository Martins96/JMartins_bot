package bot.ellie.comands.games.catsimulator.bean.malattie;

public class Vomito extends Malattia {
	
	public Vomito() {
		nome = "Vomito";
		descrizione = "Malattia non troppo grave, ma che danneggia lentamente il micio";
		descrMorte = "Il vomito non fa assimilare correttamente il cibo e rende il micio sepre più debole fino a portarlo alla morte";
		danno = 2;
		probabilitaDiCura = 20;
		curato = false;
	}

}
