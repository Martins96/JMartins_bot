package bot.ellie.comands.games.catsimulator;

import java.io.File;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.comands.games.GameBase;
import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.UserActionImg;
import bot.ellie.comands.games.catsimulator.bean.commands.UserCommands;
import bot.ellie.comands.games.catsimulator.bean.GamesCommand;
import bot.ellie.comands.games.catsimulator.messages.CatMessages;
import bot.ellie.utils.Sender;

public class CatGameMain extends GameBase{
	
	private PartitaBean partitaBean;
	private boolean exitGame = false;
	
	public CatGameMain(int idthread, Message messaggio) {
		this.idthread = idthread;
		this.messaggio = messaggio;
	}
	
	private String getOrarioMessage() {
		return "Orario: " + partitaBean.getOrario() + ":00";
	}

	

	@Override
	public String startGame() {
		
		String message = messaggio.text();
		int idUser = messaggio.from().id();
		
		if(CatGameUtils.isSavedPresent(idUser)) {
			CatGameUtils.loadSaved(idUser);
			//TODO
		} else { //l'utente non ha salvataggi
			partitaBean = new PartitaBean(CatGameUtils.GenerateCat(), (short)8);
			sendInfoCat(partitaBean.getCat(), UserActionImg.START, "START");
			sendMessage(getOrarioMessage());
		}
		
		ingame:
		while (!exitGame) {
			sendMessage(CatMessages.WAIT_ACTION);
			boolean commandNotCorrect;
			
			do {
				message = getMessage();
				commandNotCorrect = false;
				switch(message) {
				case(GamesCommand.COCCOLA):
					partitaBean = UserCommands.Coccola.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.COCCOLA, 
							"Hai coccolato il tuo gattino");
					partitaBean.setLastAction(UserActionImg.COCCOLA);
					break;
				case(GamesCommand.DAI_CIBO):
					partitaBean = UserCommands.DaiCibo.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.MANGIA, 
							"Il tuo gattino mangia");
					partitaBean.setLastAction(UserActionImg.MANGIA);
					break;
				case(GamesCommand.DAI_LATTINO):
					partitaBean = UserCommands.DaiLattino.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.BEVE, 
							"Il tuo gattino beve il lattino");
					partitaBean.setLastAction(UserActionImg.BEVE);
					break;
				case(GamesCommand.PORTA_A_SPASSO):
					partitaBean = UserCommands.PortaASpasso.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.A_SPASSO,
							"Si va a fare un giretto con il gattino");
					partitaBean.setLastAction(UserActionImg.A_SPASSO);
					break;
				case(GamesCommand.FAI_NANNA):
					partitaBean = UserCommands.FaiNanna.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.DORME, 
							"Il tuo gattino fa la nanna");
					partitaBean.setLastAction(UserActionImg.DORME);
					break;
				case(GamesCommand.CONTROLLA):
					partitaBean = UserCommands.Controlla.executeCommand(partitaBean);
					sendMessage(partitaBean.checkMessage);
					partitaBean.checkMessage = null;
					partitaBean.setLastAction(UserActionImg.START);
					break;
				case(GamesCommand.DAL_MEDICO):
					String diagnosi = CatGameUtils.diagnosiDoc(partitaBean);
					partitaBean = UserCommands.DalDottore.executeCommand(partitaBean);
					sendInfoCat(partitaBean.getCat(), UserActionImg.DOTTORE,
							diagnosi);
					break;
				case(GamesCommand.EXIT):
				case(GamesCommand.QUIT):
					sendMessage("Uscita dal gioco eseguita");
					break ingame;
				default:
					//setto comando non riconosiuto
					commandNotCorrect = true;
					sendMessage(CatMessages.WAIT_ACTION);
					break;
				}
			}while(commandNotCorrect); //continuo finché non riconosco il comando
			

			//EVENTI E CONTROLLI
			partitaBean = CatGameUtils.normalizeCat(partitaBean);
			partitaBean = CatGameUtils.eventiCasuali(partitaBean, idthread, messaggio.from().id());
			partitaBean = CatGameUtils.executePassaggioTempo(partitaBean);
			partitaBean = CatGameUtils.checkCat(partitaBean);
			
			sendMessage(getOrarioMessage());
			
			if(partitaBean.isGameFinished()) {
				//GAME OVER
				sendInfoCat(
						partitaBean.getCat(), UserActionImg.MORTO, partitaBean.getDescrizioneMorte());
				break ingame;
			}
			System.out.println("Condizione" + partitaBean.getCat().getCondizione());
			System.out.println("Fame" + partitaBean.getCat().getFame());
			System.out.println("Obbedienza" + partitaBean.getCat().getObbedienza());
			System.out.println("Sete" + partitaBean.getCat().getSete());
			System.out.println("Sonno" + partitaBean.getCat().getSonno());
			System.out.println("Umore" + partitaBean.getCat().getUmore());
			System.out.println("Malattia" + partitaBean.getCat().getMalattia());
			
		}
		
		return CatMessages.END_MESSAGE;
	}
	
	
	
	
	private void sendInfoCat(Cat cat, UserActionImg action, String description) {
		File catImage = CatGameUtils.generateFileImg(cat, action);
		
//		if (catImage != null)
//			Sender.sendDocument(messaggio.from().id(), catImage);
		
		Sender.sendMessage(messaggio.from().id(), description);
	}

}
