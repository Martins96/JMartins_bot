package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.exceptions.EndGameRequestException;

public interface EventoCasuale {
	
	public PartitaBean startEvent(PartitaBean pb, int idthread, int idUser) throws EndGameRequestException;
	
}
