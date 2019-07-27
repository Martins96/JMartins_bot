package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.exceptions.EndGameRequestException;
import bot.ellie.comands.games.catsimulator.bean.malattie.Raffreddore;
import bot.ellie.comands.games.catsimulator.bean.malattie.Vomito;
import bot.ellie.utils.Sender;

public class RovistareSpazzatura extends EventoUtil{
	
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
		pb.setCat(cat);
		
		if(getPercentuale() > 75) {
			//fine rapida
			Sender.sendMessage(idUser, "Il tuo gattino si diverte tanto a giocare all'aria aperta");
			return pb;
		}
		
		//rovista nella spazzatura
		command = getUserInput(idthread, "Il tuo gattino salta dentro un cassonetto, corri ad afferrarlo e lo tiri fuori,"
				+ " è tutto sporco! Cosa vuoi fare?\n/Non_fare_niente\n /Lavalo_in_giardino\n /Lavalo_in_casa", 
				new String[] {"/Non_fare_niente", "/Lavalo_in_giardino", "/Lavalo_in_casa"}, idUser);
		
		if("/Non_fare_niente".equals(command)) {
			return nonFareNienteFlow(pb, idUser);
		} else if("/Lavalo_in_giardino".equals(command)) {
			return lavaloFuoriFlow(pb, idUser);
		} else if("/Lavalo_in_casa".equals(command)) {
			return lavaloInCasaFlow(pb, idUser);
		}
		
		
		
		return pb;
	}
	
	//-----------------------------------------------------------------------------------------
	
	private PartitaBean nonFareNienteFlow(PartitaBean pb, int idUser) {
		Sender.sendMessage(idUser, "I gatti odiano l'acqua giusto? poi si puliscono da soli, meglio lasciarlo così");
		Cat cat = pb.getCat();
		if (getPercentuale() < 80) {
			cat.setMalattia(new Vomito());
		}
		
		cat.setCondizione(cat.getCondizione() - 5);
		
		pb.setCat(cat);
		return pb;
	}
	
	private PartitaBean lavaloInCasaFlow(PartitaBean pb, int idUser) {
		Cat cat = pb.getCat();
		if (getPercentuale() < 40) {
			cat.setMalattia(new Vomito());
		}
		
		cat.setCondizione(cat.getCondizione() - 2);
		cat.setUmore(cat.getUmore() - 10);
		
		pb.setCat(cat);
		return pb;
	}

	private PartitaBean lavaloFuoriFlow(PartitaBean pb, int idUser) {
		Cat cat = pb.getCat();
		if (getPercentuale() < 15) {
			cat.setMalattia(new Vomito());
		} else if (getPercentuale() < 50) {
			cat.setMalattia(new Raffreddore());
		}
		
		cat.setCondizione(cat.getCondizione() - 2);
		cat.setUmore(cat.getUmore() - 5);
		
		pb.setCat(cat);
		return pb;
	}
	
	
	
}
