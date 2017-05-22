package bot.ellie;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

import bot.ellie.utils.Costants;

public class ErrorReporter {
	
	private final static String TOKEN = Costants.TELEGRAM_TOKEN_4_ERROR;
	//creazione bot Ellie
	private static TelegramBot EmergencyEllie = null; //   
	
	protected String error;
	protected Exception e;
	
	private ErrorReporter() {
	}
	
	public class EllieThreadError implements Runnable {
		
		public EllieThreadError(String error, Exception e) {
			ErrorReporter.this.error = error;
			ErrorReporter.this.e = e;
		}
		
		@Override
		public void run() {
			
			String errore = error;
			if(e != null)
				errore = errore + " \n " + e.getMessage()
							+ "\nCAUSE:\n" + e.getCause()
							+ "\nSTACK:\n" + e.getStackTrace();
			
			EmergencyEllie = TelegramBotAdapter.build(TOKEN);
			EmergencyEllie.execute(new SendMessage(115949778, "EMERGENCY ELLIE\n\nERRORE :'(\n" + error));
		}
	}
	
	public class EllieThreadWarn implements Runnable {
		
		private EllieThreadWarn(String error) {
			ErrorReporter.this.e = e;
		}
		
		@Override
		public void run() {
			EmergencyEllie = TelegramBotAdapter.build(TOKEN);
			EmergencyEllie.execute(new SendMessage(115949778, "WARNING: " + error));
		}
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	
	/**invio errori a martins <3
	 * 
	 * @param error
	 */
	public static void sendError(String error) {
		(new Thread(new ErrorReporter().new EllieThreadError(error, null))).start();
	}
	
	public static void sendError(String error, Exception e) {
		(new Thread(new ErrorReporter().new EllieThreadError(error, e))).start();
	}
	
	//-----------------------------------------------------------------------------------------------------
	
	public static void sendWarn(String s) {
		(new Thread(new ErrorReporter().new EllieThreadWarn(s))).start();
	}
	
	
	
	
}
