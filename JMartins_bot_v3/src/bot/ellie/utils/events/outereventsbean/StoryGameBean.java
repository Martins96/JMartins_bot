package bot.ellie.utils.events.outereventsbean;

import com.pengrad.telegrambot.model.User;

public class StoryGameBean {
	private boolean request4Start; //richiesta per avviare il gioco
	private User userTarget; //utente da chiamare
	private User userHost; //utente mittente della richiesta
	
	public StoryGameBean(boolean startedRequest, User userTarget, User userHost) {
		this.request4Start = startedRequest;
		this.userTarget = userTarget;
		this.userHost = userHost;
	}

	public boolean isRequest4Start() {
		return request4Start;
	}

	public void setRequest4Start(boolean startedRequest) {
		this.request4Start = startedRequest;
	}

	public User getUserTarget() {
		return userTarget;
	}

	public void setUserTarget(User userTarget) {
		this.userTarget = userTarget;
	}
	
	public User getUserHost() {
		return userHost;
	}

	public void setUserHost(User userHost) {
		this.userHost = userHost;
	}
	
	
	
}
