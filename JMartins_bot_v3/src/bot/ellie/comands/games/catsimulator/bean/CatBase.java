package bot.ellie.comands.games.catsimulator.bean;

import bot.ellie.comands.games.catsimulator.bean.malattie.Malattia;

public class CatBase {
	
	protected int id;
	
	protected short umore;
	protected short condizione;
	protected short fame;
	protected short sete;
	protected short sonno;
	
	protected short obbedienza;
	
	protected Malattia malattia;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public short getUmore() {
		return umore;
	}
	public void setUmore(int umore) {
		this.umore = (short)umore;
	}
	public short getCondizione() {
		return condizione;
	}
	public void setCondizione(int condizione) {
		this.condizione = (short)condizione;
	}
	public short getFame() {
		return fame;
	}
	public void setFame(int fame) {
		this.fame = (short)fame;
	}
	public short getSete() {
		return sete;
	}
	public void setSete(int sete) {
		this.sete = (short)sete;
	}
	public short getSonno() {
		return sonno;
	}
	public void setSonno(int sonno) {
		this.sonno = (short)sonno;
	}
	public Malattia getMalattia() {
		return malattia;
	}
	public void setMalattia(Malattia malattia) {
		this.malattia = malattia;
	}
	public short getObbedienza() {
		return obbedienza;
	}
	public void setObbedienza(int obbedienza) {
		this.obbedienza = (short) obbedienza;
	}
	@Override
	public String toString() {
		return "CatBase [id=" + id + ", umore=" + umore + ", condizione=" + condizione + ", fame=" + fame + ", sete="
				+ sete + ", sonno=" + sonno + ", malattia=" + malattia + "]";
	}
	

}
