package bot.ellie.utils.games.storygame;

public class GetMessageBean {
  
  private String hostMex;
  private String targetMex;
  
  public GetMessageBean() {
  }

  public GetMessageBean(String hostMex, String targetMex) {
	super();
	this.hostMex = hostMex;
	this.targetMex = targetMex;
  }

  public String getHostMex() {
    return hostMex;
  }

  public void setHostMex(String hostMex) {
    this.hostMex = hostMex;
  }

  public String getTargetMex() {
    return targetMex;
  }

  public void setTargetMex(String targetMex) {
    this.targetMex = targetMex;
  }
}
