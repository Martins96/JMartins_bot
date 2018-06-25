package bot.ellie.comands.games.catsimulator.bean.malattie;

import java.util.Random;

public abstract class Malattia {

	protected String descrizione;
	protected String nome;
	protected String descrMorte;
	protected short danno;
	protected boolean curato;
	protected short probabilitaDiCura;
	
	public String getDescrizione() {
		return descrizione;
	}
	public String getNome() {
		return nome;
	}
	public String getDescrMorte() {
		return descrMorte;
	}
	public short getDanno() {
		return danno;
	}
	public boolean isCurato() {
		return curato;
	}
	
	protected Malattia() {
	}
	
	public void calcolateCura() {
		Random rand = new Random();
		if(probabilitaDiCura > rand.nextInt(100)) {
			curato = true;
		}
	}
	
	public void addProbabilitàDiCura(int value) {
		probabilitaDiCura += value;
	}
}
