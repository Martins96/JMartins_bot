package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.utils.Sender;

public class SorpresaSalotto extends EventoUtil {

	@Override
	public PartitaBean startEvent(PartitaBean pb, int idthread) {
		Cat cat = pb.getCat();
		if(cat.getObbedienza() > 80) {
			//il gatto è molto obbediente
			return pb;
		}
		String command = getUserInput(idthread, "Il tuo bel gattino ha fatto... la pipì sul tappeto in salotto. Che cosa fai? Lo sgridi?\n"
				+ "/Sì\n"
				+ "/No", new String[] {"/Sì", "No"});
		
		if("/Sì".equalsIgnoreCase(command)) {
			cat.setUmore(cat.getUmore() - 5);
			cat.setObbedienza(cat.getObbedienza() + 5);
			Sender.sendMessage(idthread, "Decidi di sgridarlo, ha combinato un guaio e la sua cucciolosità non può fargliela passare liscia.\n"
					+ "Il tuo gattino impara la lezione, speriamo non lo rifaccia più.");
		} else { //"/No"
			cat.setUmore(cat.getUmore() + 5);
			cat.setObbedienza(cat.getObbedienza() -10);
			Sender.sendMessage(idthread, "Non vuoi renderlo triste sgridandolo, pulisci dove ha sporcato e lasci passare."
					+ "\nIl micio è tranquillo, mi sa che non ha capito di aver fatto danno.");
		}
		
		pb.setCat(cat);
		return pb;
	}

}
