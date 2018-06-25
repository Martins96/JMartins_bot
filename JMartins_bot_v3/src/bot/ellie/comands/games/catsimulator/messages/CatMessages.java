package bot.ellie.comands.games.catsimulator.messages;

import bot.ellie.comands.games.catsimulator.bean.GamesCommand;

public class CatMessages {
	
	public static final String WELCOME_MESSAGE = "Benvenuti in Cat Simulator";
	public static final String WAIT_ACTION = "Seleziona l'azione che vuoi fare con il tuo gattino\n"
			+ GamesCommand.COCCOLA + "\n"
			+ GamesCommand.CONTROLLA + "\n"
			+ GamesCommand.DAI_CIBO + "\n"
			+ GamesCommand.DAI_LATTINO + "\n"
			+ GamesCommand.DAL_MEDICO + "\n"
			+ GamesCommand.FAI_NANNA + "\n"
			+ GamesCommand.PORTA_A_SPASSO + "\n"
			+ GamesCommand.EXIT;
	public static final String END_MESSAGE = "END";
	public static final String NOT_VALID_CMD = "Comando inserito non valido";
	
}
