package bot.ellie.utils;

public class EmergencyRestartException extends Exception {

	private static final long serialVersionUID = 1L;
	
	
	public EmergencyRestartException() {
		super();
	}
	
	public EmergencyRestartException(String message) {
		super(message);
	}
	
	public EmergencyRestartException(Throwable e) {
		super(e);
	}
	
	public EmergencyRestartException(String message, Throwable e) {
		super(message, e);
	}
	
}
