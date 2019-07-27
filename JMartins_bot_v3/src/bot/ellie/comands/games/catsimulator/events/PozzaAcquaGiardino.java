package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.exceptions.EndGameRequestException;
import bot.ellie.comands.games.catsimulator.bean.malattie.Raffreddore;
import bot.ellie.utils.Sender;

public class PozzaAcquaGiardino extends EventoUtil {
	
	@Override
	public PartitaBean startEvent(PartitaBean pb, int idthread, int idUser) throws EndGameRequestException {
		Cat cat = pb.getCat();
		String command;
		command = getUserInput(idthread, "Il tuo gattino vuole uscire a giocare in giardino, ha piovuto da poco, "
				+ "ma c'è un bel sole ora. C'è un po' di vento, lo lasci?\n /Si\n /No", 
				new String[]{"/Si","/No"}, idUser);
		
		if("/No".equals(command)) {
			cat.setUmore(cat.getUmore() - 5);
			pb.setCat(cat);
			return pb;
		}
		//si flow

		cat.setFame(cat.getFame() - 2);
		cat.setSete(cat.getSete() - 2);
		cat.setSonno(cat.getSonno() - 2);
		cat.setUmore(cat.getUmore() + 5);
		
		if(getPercentuale() > 75) {
			//fine rapida
			Sender.sendMessage(idUser, "Il tuo gattino si diverte tanto a giocare all'aria aperta");
			pb.setCat(cat);
			return pb;
		}
		
		if(getPercentuale() > 60 && cat.getObbedienza() < 75) {
			//Sporca casa
			command = getUserInput(idthread, "Il tuo gattino dopo aver giocato ha tutte le zampe sporche di fango,"
					+ " è entrato in casa e ha sporcato tutto cosa fai?\n /Sgridalo\n /Pulisci_senza_sgridarlo",
					new String[] {"/Sgridalo","/Pulisci_senza_sgridarlo"}, idUser);
			if("/Sgridalo".equals(command)) {
				cat.setUmore(cat.getUmore() - 10);
				cat.setObbedienza(cat.getObbedienza() + 5);
				Sender.sendMessage(idUser, "Sgridi il tuo gattino, no può sporcare tutto così. Sembra un po' triste, ma si capisce che è dispiaciuto e ha compreso la situazione");
				pb.setCat(cat);
				return pb;
			} else {
				cat.setUmore(cat.getUmore() + 5);
				cat.setObbedienza(cat.getObbedienza() - 2);
				Sender.sendMessage(idUser, "Pulisci dove ha sporcato senza dirgli niente, il gattino è spensierato e felice, ma non ha capito che ha fatto danno");
				pb.setCat(cat);
				return pb;
			}
		}
		
		//else cade nella pozza
		Sender.sendMessage(idUser, "Mentre gioca il gattino cade in una pozza d'acqua lui è molto divertito da tutto ciò, ma c'è un po' di vento fresco e potrebbe ammalarsi.\n"
				+ "Lo prendi subito e lo asciughi per bene, speriamo che non abbia preso troppo freddo, comunque il gattino si è molto divertito");
		cat.setUmore(cat.getUmore() + 5);
		cat.setFame(cat.getFame() - 1);
		cat.setSete(cat.getSete() - 1);
		cat.setSonno(cat.getSonno() - 1);
		
		//calcolo malattia
		if(getPercentuale() <= 50) {
			cat.setMalattia(new Raffreddore());
		}
		
		
		return pb;
	}
	
}
