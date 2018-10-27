package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.UserActionImg;
import bot.ellie.utils.Sender;

public class Smarrito extends EventoUtil {
	
	@Override
	public PartitaBean startEvent(PartitaBean pb, int idthread, int idUser) {
		Cat cat = pb.getCat();
		if (pb.getLastAction().equals(UserActionImg.A_SPASSO)) {
			String command = getUserInput(idthread, "\"Il tuo gattino si è perso mentre tornavate a casa dal giretto,"
					+ "magari torna da solo e si sarà fermato a giocare da qualche parte\\nCosa fai?\"\n"
					+ "\n/Torna_a_cercarlo\n"
					+ "\n/Aspetta_il_ritorno", new String[]{"/Torna_a_cercarlo", "/Aspetta_il_ritorno"}, idUser);
			
			if("/Aspetta_il_ritorno".equalsIgnoreCase(command))  {
				pb.addTimeTempoUltimaAzione(1);
				if(cat.getUmore() > 80 && cat.getObbedienza() > 50) {
					if(getPercentuale() > 75) {
						return gattoSmarritoTornatoDaSolo(pb, cat, idthread);
					}
				}
				//micio non tornato da solo
				cat.setCondizione(cat.getCondizione() - 2);
				command = getUserInput(idthread, "\"Non vedi il gattino in lontanaza e inizia a fare buio\\nCosa fai?\"\n"
						+ "\n/Torna_a_cercarlo\n"
						+ "\n/Aspetta_il_ritorno", new String[]{"/Torna_a_cercarlo", "/Aspetta_il_ritorno"}, idUser);
				if("/Aspetta_il_ritorno".equalsIgnoreCase(command)) {
					pb.addTimeTempoUltimaAzione(1);
					if(cat.getObbedienza() > 80) {
						if(getPercentuale() > 60) {
							return gattoSmarritoTornatoDaSolo(pb, cat, idthread);
						}
					}
					//micio non tornato da solo ancora
					cat.setCondizione(cat.getCondizione() - 1);
					cat.setSonno(cat.getSonno() - 5);
					Sender.sendMessage(idthread, 
							"Il tuo gattino non si vede ancora, potrebbe essersi perso, meglio andare a cercarlo");
				}
			}
			pb.addTimeTempoUltimaAzione(1);
			//Torna_a_cercarlo command
			int perc = getPercentuale();
			if(perc < 25) {
				return gattoTrovatoNormale(pb, cat, idthread);
			} else if(perc < 50) {
				return gattoTrovatoGiocare(pb, cat, idthread);
			} else if(perc < 75) {
				return gattoIntrappolato(pb, cat, idthread);
			} else if(perc <= 99) {
				return gattoInLotta(pb, cat, idthread);
			} else {
				return gattoMorto(pb, cat, idthread);
			}
		}
		pb.setCat(cat);
		return pb;
	}
	
	
	private PartitaBean gattoSmarritoTornatoDaSolo(PartitaBean pb, Cat cat, int idthread) {
		cat.setCondizione(cat.getCondizione() - 1);
		cat.setSonno(cat.getSonno() - 5);
		Sender.sendMessage(idthread, "Il micino è tornato da solo, ma che bravo! Tante coccole");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoTrovatoGiocare(PartitaBean pb, Cat cat, int idthread) {
		cat.setSonno(cat.getSonno() - 5);
		cat.setUmore(cat.getUmore() + 2);
		Sender.sendMessage(idthread, "Il micino ha trovato un amico e sta giocando, sta bene, ma è ora di andare\n"
				+ "Saluta il suo 'amicietto' e tornate a casa");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoTrovatoNormale(PartitaBean pb, Cat cat, int idthread) {
		Sender.sendMessage(idthread, "Hai trovato il tuo micino sano e salvo, lo prendi in braccio e tornate a casa");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoIntrappolato(PartitaBean pb, Cat cat, int idthread) {
		cat.setCondizione(cat.getCondizione() - 10);
		cat.setFame(cat.getFame() + 1);
		Sender.sendMessage(idthread, "Hai trovato il tuo micino intrappolato in una scatola di latta, stava cercando di leccare il fondo, povero cucciolo.\n"
				+ "La lattina è tagliente e gli fa male, con attenzione lo liberi cercando di fargli meno male possibile.\n"
				+ "Una volta liberato lo porti a casa");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoInLotta(PartitaBean pb, Cat cat, int idthread) {
		cat.setCondizione(cat.getCondizione() - 25);
		cat.setUmore(cat.getUmore() - 5);
		cat.setSonno(cat.getSonno() - 10);
		Sender.sendMessage(idthread, "Vedi il tuo gattino fare la lotta con un cagnaccio randagio, i due si stanno mordendo.\n"
				+ "Rapidamente tiri un calcio al cane e recuperi il tuo povero gattino e tornate a casa");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoMorto(PartitaBean pb, Cat cat, int idthread) {
		Sender.sendMessage(idthread, "Il tuo gattino... è stato investito... povero cucciolo... è stato davvero un bel micio.");
		pb.setGameFinished(true);
		pb.setDescrizioneMorte("Investito da una macchina");
		pb.setCat(cat);
		return pb;
	}
	
	
	
}
