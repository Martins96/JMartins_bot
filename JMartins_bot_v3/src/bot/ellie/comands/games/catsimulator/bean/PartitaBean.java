package bot.ellie.comands.games.catsimulator.bean;

public class PartitaBean {
	
	private short orario;
	//questo è il tempo impiegato per l'ultima azione compiuta
	//per calcolare la modifica dei parametri
	private short tempoUltimaAzione;
	private UserActionImg lastAction;
	private Cat cat;
	private boolean gameFinished; 
	
	private String descrizioneMorte;
	
	
	public String checkMessage = null;
	
	
	public PartitaBean(Cat cat, short orario) {
		this.cat = cat;
		this.orario = orario;
		this.gameFinished = false;
		this.tempoUltimaAzione = 0;
		this.lastAction = UserActionImg.START;
	}

	public short getOrario() {
		return orario;
	}

	public void setOrario(short orario) {
		this.orario = orario;
	}

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}
	
	public boolean isGameFinished() {
		return gameFinished;
	}
	
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	public String getDescrizioneMorte() {
		return descrizioneMorte;
	}

	public void setDescrizioneMorte(String descrizioneMorte) {
		this.descrizioneMorte = descrizioneMorte;
	}
	
	public void addTimeTempoUltimaAzione(int t) {
		tempoUltimaAzione = (short) (tempoUltimaAzione + t);
	}
	
	public short getAndResetTempoUltimaAzione() {
		short t = tempoUltimaAzione;
		tempoUltimaAzione = 0;
		return t;
	}
	
	public UserActionImg getLastAction() {
		return lastAction;
	}

	public void setLastAction(UserActionImg lastAction) {
		this.lastAction = lastAction;
	}

	@Override
	public String toString() {
		return "PartitaBean [orario=" + orario + ":00, cat=" + cat + "]";
	}
	
	
	
}
