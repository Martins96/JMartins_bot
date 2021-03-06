package bot.ellie.comands.games;

import java.util.Random;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.Main;
import bot.ellie.utils.messages.Errors;
import bot.ellie.utils.messages.Help;
import bot.ellie.utils.messages.Messages;

public class SassoCartaForbice extends GameBase{
		
	private static final Random RANDOM = new Random();
	
	private short playerValue;//mossa del giocatore
	private short ellieValue;	//mossa di Ellie
	
	private static final short SASSO = 0;
	private static final short CARTA = 1;
	private static final short FORBICI = 2;
	
	private static final String SASSO_ICON = "/sasso";
	private static final String CARTA_ICON = "/carta";
	private static final String FORBICI_ICON = "/forbice";
	
	private static final short EXIT = 88;
	
	public SassoCartaForbice(int idthread, Message messaggio) {
		this.idthread = idthread;
		this.messaggio = messaggio;
	}
	
	public String startGame() {
		String exit = "";
		//inizializzo gioco
		sendInfoGame();
		while(!exit.equals("/si")) {
			String inputPlayer;
			
			do {
			sendMessage("Fai la tua mossa");
			inputPlayer = getMessage();
			} while(checkInputAndSetValue(inputPlayer) != true);
			
			if(playerValue == EXIT)
				return Messages.SCF_EXIT_DONE;
			
			thinkEllie();
			sendEllieAction();
			
			short result = calcolateWinner();
			
			if(result == 0)
				sendMessage(Messages.SCF_WIN);
			else if(result == 1)
				sendMessage(Messages.SCF_PARI);
			else
				sendMessage(Messages.SCF_LOSE);
			
			
			
				
			
			sendMessage(Messages.SCF_RESTART_CHECK);
			exit = getMessage();
		}
		return Messages.SCF_END;
	}
	
	
	//------------------------------------------------------------------------------------------------
	
	private void sendInfoGame() {
		sendMessage(Help.SCF_HELP);
	}
	
	private boolean checkInputAndSetValue(String input) {
		switch (input) {
		case SASSO_ICON:
			playerValue = SASSO;
			return true;
		case CARTA_ICON:
			playerValue = CARTA;
			return true;
		case FORBICI_ICON:
			playerValue = FORBICI;
			return true; 
		case "/exit":
			playerValue = EXIT;

		default:
			sendMessage(Errors.SCF_INPUT_NOT_VALID);
			return false;
		}
	}
	
	private void thinkEllie() {
		ellieValue = (short)RANDOM.nextInt(3);
	}
	
	private void sendEllieAction() {
		switch (ellieValue) {
		case SASSO:
			sendMessage(SASSO_ICON);
			return;
		case CARTA:
			sendMessage(CARTA_ICON);
			return;
		case FORBICI:
			sendMessage(FORBICI_ICON);
			return;
		}
	}
	
	/**
	 * @return 0 win - 1 pari - 2 lose
	 */
	private short calcolateWinner() {
		switch (playerValue) {
		case SASSO:
			if(ellieValue == FORBICI)
				return 0;
			if(ellieValue == SASSO)
				return 1;
			return 2;
			
		case CARTA:
			if(ellieValue == SASSO)
				return 0;
			if(ellieValue == CARTA)
				return 1;
			return 2;
		case FORBICI:
			if(ellieValue == CARTA)
				return 0;
			if(ellieValue == FORBICI)
				return 1;
			return 2;

		default:
			Main.log.error("Errore SCF calcolo del vincitore");
			sendMessage(Errors.GENERAL_ERROR);
			return 2;
		}
	}
	
	

}
