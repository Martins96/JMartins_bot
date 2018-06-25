package bot.ellie.comands.games.catsimulator.events;

import bot.ellie.comands.games.catsimulator.bean.PartitaBean;

public class NoEvents implements EventoCasuale {

	@Override
	public PartitaBean startEvent(PartitaBean pb, int idthread) {
		return pb;
	}

}
