package bot.ellie.comands.games.catsimulator.bean.malattie;

public class FebbreFelina extends Malattia {
	
	public FebbreFelina() {
		nome = "Febbre felina";
		descrizione = "Malattia abbastanza grave, bisogna trovare una cura al più presto";
		descrMorte = "La febbre è una cosa grave e pericolosa, non averla curata in tempo ha ucciso il tuo gattino";
		danno = 5;
		probabilitaDiCura = 1;
		curato = false;
	}

}
