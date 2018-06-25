package bot.ellie.comands.games.catsimulator.bean.malattie;

public class Rabbia extends Malattia {
	
	public Rabbia() {
		this.nome = "Rabbia";
		this.descrizione = "Malattia grave e pericolosa, per il micio e per chi gli sta attorno, bisogna curarla al più presto";
		this.descrMorte = "Il tuo gattino è morto per la rabbia, è una malattia molto pericolosa";
		this.danno = 20;
		this.probabilitaDiCura = 2;
		this.curato = false;
	}
	
	
}
