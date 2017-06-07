package bot.ellie.utils.events.outereventsbean;

import com.pengrad.telegrambot.model.User;

public class MultiplayerGameBean {
	private short idThreadTarget;
	private short idThreadHost;
	private User userTarget; //utente da chiamare
	private User userHost; //utente mittente della richiesta
	
	public MultiplayerGameBean(short idThreadTarget, short idThreadHost, User userTarget, User userHost) {
		this.idThreadTarget = idThreadTarget;
		this.idThreadHost = idThreadHost;
		this.userTarget = userTarget;
		this.userHost = userHost;
		
	}

	public User getUserTarget() {
		return userTarget;
	}
	
	public User getUserHost() {
		return userHost;
	}

	public short getIdThreadTarget() {
		return idThreadTarget;
	}

	public short getIdThreadHost() {
		return idThreadHost;
	}
	
}
