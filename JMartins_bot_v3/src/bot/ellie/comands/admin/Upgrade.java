package bot.ellie.comands.admin;

import com.pengrad.telegrambot.model.Message;

import bot.ellie.Main;
import bot.ellie.utils.Getter;
import bot.ellie.utils.Sender;

public class Upgrade {

	private Message message;
	private int idthread;
	private boolean admin = false;
	private boolean mylady = false;
	
	public Upgrade(Message messaggio, int idthread) {
		this.message = messaggio;
		this.idthread = idthread;
	}
	
	
	public void startUpgrade() {
		
		String target = attendiMessaggio("Ok papà, a chi devo fare l'upgrade?");
		int id;
		switch(target) {
			case("martins"):
				id = 115949778;
				admin = true;
				break;
			case("papà"):
				id = 115949778;
				admin = true;
				break;
			case("mamma"):
				id = 240650708;
				mylady = true;
				break;
			case("mylady"):
				id = 240650708;
				mylady = true;
				break;
			default:
				id = -1;
				break; 
		}
		if (id == -1)
		{
			sendMessage("Errore: modalità " + target + " non conosciuta");
			return;
		}
		else
		{
			
			String targetId = attendiMessaggio("Su quale ID devo abilitare il target?(numero) \n"
					+ target + "(" + id +")\n"
					+ "Martins    			- /115949778\n"
					+ "Matte   				- /76891271\n"
					+ "Gaia       			- /124796388\n"
					+ "Ale        			- /143009205\n"
					+ "Calvie				- /180117484\n"
					+ "Francesco  			- /170595365\n"
					+ "Dinu       			- /148278619\n"
					+ "Davide Colleoni  	- /71188575\n"
					+ "Manara				- /125212616\n"
					+ "Bruno 				- /75443863\n"
					+ "Andrea 	        	- /27585212\n"
					+ "Parmraj Singh        - /164741728\n"
					+ "Mamma (Ale)			- /240650708\n"
					);
			
			if(targetId.substring(0, 1).equals("/"))
				targetId = targetId.substring(1);
			
			//controllo nei thread attivi se è presente
			for (int i = 0; i<Main.nThread; i++) {
				if(("" + Main.botThread.get(i).idUserThread).equals(targetId)) {
					
					if(admin) {
						Main.botThread.get(i).setAdminMode(true);
						sendMessage(targetId + " aggiunto in modalità admin");
						return;
					}
					if(mylady) {
						Main.botThread.get(i).setMyladyMode(true);
						sendMessage(targetId + " aggiunto in modalità mylady");
						return;
					}
					
					sendMessage("Comando non riuscito, qualcosa è andato male...");
					return;
				}
			}
			
			sendMessage("L'id non è in thread, non è possibile eseguire l'upgrade");
			return;
		}
	}
	
	
	
	
	private String attendiMessaggio(String stringa) {
		sendMessage(stringa);
		return Getter.attendiMessaggio(idthread);
	}
	
	private void sendMessage(String text) {
		Sender.sendMessage(message.from().id(), text);
	}
	
}
