package bot.ellie.comands.games.catsimulator;

import java.io.File;
import java.util.Random;

import bot.ellie.Main;
import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.UserActionImg;
import bot.ellie.comands.games.catsimulator.events.EventoCasuale;
import bot.ellie.comands.games.catsimulator.events.NoEvents;
import bot.ellie.comands.games.catsimulator.events.PozzaAcquaGiardino;
import bot.ellie.comands.games.catsimulator.events.RovistareSpazzatura;
import bot.ellie.comands.games.catsimulator.events.Scappato;
import bot.ellie.comands.games.catsimulator.events.Smarrito;
import bot.ellie.comands.games.catsimulator.events.SorpresaSalotto;
import bot.ellie.comands.games.catsimulator.messages.CauseMorte;

public class CatGameUtils {
	
	private static final Random rand = new Random();
	
	/**
	 * controllo se l'utente ha dei salvataggi presenti
	 * @return true se ci sono salvataggi, false in caso contrario
	 */
	public static boolean isSavedPresent(int iduser) {
		return false;
	}
	
	public static void loadSaved(int iduser) {
		
	}
	
	public static void saveSaved(int iduser, Cat cat) {
		
	}
	
	public static Cat GenerateCat() {
		return new Cat();
	}
	
	public static File generateFileImg(Cat cat, UserActionImg action) {
		return new File(Main.PATH_INSTALLAZIONE + 
				"/readfiles/photo/catsimulator/" + cat.getId() + 
				"/" + action + ".jpg");
	}
	
	public static PartitaBean eventiCasuali(PartitaBean pb, int idthread, int idUser) {
		int percentuale = rand.nextInt(100) + 1;
		EventoCasuale event = new NoEvents();
		
		System.out.println("Percentuale = " + percentuale);
		
		if(percentuale < 10 ) {
			event = new PozzaAcquaGiardino();
		} else if (percentuale < 20) {
			event = new RovistareSpazzatura();
		} else if (percentuale < 25) {
			event = new SorpresaSalotto();
		} else if (percentuale < 30) {
			event = new Scappato();
		} else if (percentuale < 35) {
			event = new Smarrito();
		} else {
			event = new NoEvents();
		}
		System.out.println("EVENT: " + event.getClass());
		
		pb = event.startEvent(pb, idthread, idUser);
		
		System.out.println("PartitaBean: " + pb.getLastAction());
		
		return pb;
	}
	
	public static PartitaBean checkCat(PartitaBean pb) {
		//Controllo se gioco già finito
		if(pb.isGameFinished())
			return pb;
		
		Cat cat = pb.getCat();
		if(cat.getFame() < 20) {
			cat.setCondizione(
					cat.getCondizione() - 5);
		}
		if(cat.getSete() < 20) {
			cat.setCondizione(
					cat.getCondizione() - 5);
		}
		if(cat.getSonno() < 20) {
			if(cat.getSonno() <= 0) {
				cat.setCondizione(
						cat.getCondizione() - 15);
			} else {
				cat.setCondizione(
						cat.getCondizione() - 5);
			}
		}
		
		if(cat.getFame() <= 0) {
			pb.setGameFinished(true);
			pb.setDescrizioneMorte(CauseMorte.FAME);
		} else if(cat.getCondizione() <= 0) {
			pb.setGameFinished(true);
			pb.setDescrizioneMorte(CauseMorte.CONDIZIONE);
		} else if(cat.getSete() <= 0) {
			pb.setGameFinished(true);
			pb.setDescrizioneMorte(CauseMorte.SETE);
		}
		return pb;
	}
	
	public static PartitaBean normalizeCat(PartitaBean pb) {
		Cat cat = pb.getCat();
		int max = 100;
		int min = 0;
		if(cat.getCondizione() > 100)
			cat.setCondizione(max);
		if(cat.getFame() > 100)
			cat.setFame(max);
		if(cat.getSete() > 100)
			cat.setSete(max);
		if(cat.getSonno() > 100)
			cat.setSonno(max);
		if(cat.getUmore() > 100)
			cat.setUmore(max);
		if(cat.getSonno() < 0)
			cat.setSonno(min);
		if(cat.getObbedienza() > 100)
			cat.setObbedienza(max);
		if(cat.getObbedienza() < 0)
			cat.setObbedienza(min);
		
		pb.setCat(cat);
		return pb;
	}
	
	public static PartitaBean executePassaggioTempo(PartitaBean pb) {
		short t = pb.getAndResetTempoUltimaAzione();
		Cat cat = pb.getCat();
		cat.setFame(
				cat.getFame() - (t * 3));
		cat.setSete(
				cat.getSete() - (t * 3));
		if(!pb.getLastAction().equals(UserActionImg.DORME)) {
			cat.setSonno(
					cat.getSonno() - (t * 4));
		}
		
		//Malattia
		pb = gestioneMalattia(pb);
		
		
		
		pb.setCat(cat);
		short newOrario = (short) (pb.getOrario() + t);
		while (newOrario >= 24) {
			newOrario -= 24;
		}
		pb.setOrario(newOrario);
		
		return pb;
	}
	
	
	private static PartitaBean gestioneMalattia(PartitaBean pb) {
		if(pb == null)
			return null;
		
		Cat cat = pb.getCat();
			
		if(cat !=  null && cat.getMalattia() != null) {
			//il gatto ha una malattia
			cat.getMalattia().calcolateCura();
			
			//controllo se il gatto non si è curato
			if(!cat.getMalattia().isCurato()) {
				
				cat.setCondizione(
						cat.getCondizione() - cat.getMalattia().getDanno());
				if(cat.getCondizione() <= 0) {
					//la malattia ha ucciso il gatto
					pb.setCat(cat);
					pb.setDescrizioneMorte(cat.getMalattia().getDescrMorte());
					pb.setGameFinished(true);
				}
				
			} else { //Il gatto si è curato
				cat.setMalattia(null);
			}
		}
		
		return pb;
	}
	
	
	public static String diagnosiDoc(PartitaBean pb) {
		Cat cat = pb.getCat();
		String diagnosi = new String("Il dottore fa la sua diagnosi:\n");
		
		if(cat.getMalattia() == null) {
			if(cat.getCondizione() > 50) {
				diagnosi = diagnosi + "Il tuo gattino è in perfetta salute";
			} else {
				diagnosi = diagnosi + "Il tuo gattino presenta delle ferite, ";
			}
		} else { //malattia trovata
			diagnosi = diagnosi + "È stata riscontrata una malattia sul gattino: " + cat.getMalattia().getNome()
					+ "\n" + cat.getMalattia().getDescrizione();
			if(cat.getCondizione() < 50) {
				diagnosi =  diagnosi + "\nIl gatto presenta anche delle ferite";
			}
			diagnosi = diagnosi + "\nIl medico provvede a dargli dei medicinali, speriamo che si riprenda al più presto";
		}
		
		return diagnosi;
	}
	
	
	
	
}
















