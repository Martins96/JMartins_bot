package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.exceptions.EndGameRequestException;
import bot.ellie.utils.Sender;

public class Scappato extends EventoUtil {

	@Override
	public PartitaBean startEvent(PartitaBean pb, int idthread, int idUser) throws EndGameRequestException {
		Cat cat = pb.getCat();
		String command;
		command = getUserInput(idthread, "Il tuo gattino è uscito dalla finestra ed è scappato cosa fai?\n"
				+ "/Aspetta_il_suo_ritorno\n/Vai_a_cercarlo", new String[] {"/Aspetta_il_suo_ritorno","/Vai_a_cercarlo"},
				idUser);
		if("/Aspetta_il_suo_ritorno".equalsIgnoreCase(command)) {
			pb.addTimeTempoUltimaAzione(1);
			if(cat.getObbedienza() > 60 && cat.getUmore() > 50) {
				if(getPercentuale() < 80) {
					Sender.sendMessage(idUser, "Il gattino è tornato, voleva solo uscire un po', ora non c'è nessun problema");
					cat.setSonno(cat.getSonno() - 2);
					cat.setUmore(cat.getUmore() + 2);
					pb.setCat(cat);
					return pb;
				}
			}
			Sender.sendMessage(idUser, "Il tuo micio non torna, sarà meglio andare a controllare");
		}
		int perc = getPercentuale();
		pb.addTimeTempoUltimaAzione(1);
		if(perc < 2) {
			return gattoMorto(pb, cat, idUser);
		} else if(perc < 35) {
			return gattoGiaACasa(pb, cat, idUser);
		} else if (perc < 70) {
			return gattoDormeAlParco(pb, cat, idUser);
		} else {
			return gattoInLotta(pb, cat, idUser);
		}
	}
	
	
	private PartitaBean gattoGiaACasa(PartitaBean pb, Cat cat, int idUser) {
		Sender.sendMessage(idUser, "hai girato nelle vicinanze, ma niente, però quando torni a casa il micio era già lì ad aspettarti, ti guarda come per dire\n"
				+ "'Cosa fai in giro tu?'\nAdesso va tutto bene");
		cat.setSonno(cat.getSonno() - 2);
		cat.setUmore(cat.getUmore() + 2);
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoDormeAlParco(PartitaBean pb, Cat cat, int idUser) {
		pb.addTimeTempoUltimaAzione(1);
		Sender.sendMessage(idUser, "Il tuo gattino stava facendo la nanna su una panchina al parco, che carino, voleva solo stare un po' nella natura");
		cat.setSonno(cat.getSonno() - 2);
		cat.setUmore(cat.getUmore() + 2);
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoInLotta(PartitaBean pb, Cat cat, int idUser) {
		cat.setCondizione(cat.getCondizione() - 25);
		cat.setUmore(cat.getUmore() - 5);
		cat.setSonno(cat.getSonno() - 10);
		Sender.sendMessage(idUser, "Vedi il tuo gattino fare la lotta con un cagnaccio randagio, i due si stanno mordendo.\n"
				+ "Rapidamente tiri un calcio al cane e recuperi il tuo povero gattino e tornate a casa");
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean gattoMorto(PartitaBean pb, Cat cat, int idUser) {
		Sender.sendMessage(idUser, "Il tuo gattino... è stato investito... povero cucciolo... è stato davvero un bel micio.");
		pb.setGameFinished(true);
		pb.setDescrizioneMorte("Investito da una macchina");
		pb.setCat(cat);
		return pb;
	}
	
	
	
}
