package bot.ellie.utils;

import java.util.ArrayList;
import java.util.List;

public class BotInfos {
	
	private String startDate;
	private List<String> usersList;
	private long retrySeconds = 50;
	
	public BotInfos() {
		usersList = new ArrayList<String>();
	}

	
	
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<String> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<String> usersList) {
		this.usersList = usersList;
	}
	
	public void setUserInList(String user) {
		this.usersList.add(user);
	}

	public long getRetrySeconds() {
		return retrySeconds;
	}

	public void setRetrySeconds(long retrySeconds) {
		this.retrySeconds = retrySeconds;
	}

	@Override
	public String toString() {
		return "BotInfos ["
				+ "startDate=" + startDate 
				+ ", usersList=" + usersList 
				+ ", retrymilliSeconds=" + retrySeconds 
				+ "]";
	}
	
	
}
