package bot.ellie.utils.tracing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.pengrad.telegrambot.model.User;

import bot.ellie.Main;

public class TraceChat {
	
	private static Logger log = Logger.getLogger(TraceChat.class);
	
	/**
	 * traccia la chat
	 * @param mittente nome del mittente, null se è Ellie a mandare il mex
	 */
	public static void trace(User usr, String msg, String mittente) {
		if(usr == null)
			return;
		trace(usr.id(), usr.username(), usr.firstName(), usr.lastName(), msg, mittente);
	}
	
	public static void trace(Object id, String msg, String mittente) {
		trace(id, null, null, null, msg, mittente);
	}
	
	public static void trace(Object id, String username, String name, String surname, String msg, String mittente) {
		if(id == null) return;
		boolean isNewFile = false;
		BufferedWriter out = null;
		File dir = new File(Main.PATH_INSTALLAZIONE + "/chats/");
		if(!dir.exists() || !dir.isDirectory()) {
		  dir.mkdir();
		}
		File output = new File(Main.PATH_INSTALLAZIONE + "/chats/" + id.toString() + ".txt");
		
		try {
			if(output.isDirectory())
				delDirectory(output);
			if(!output.exists()) {
				output.createNewFile();
				isNewFile = true;
			}
			out = new BufferedWriter(new FileWriter(output, true));
			
			if(isNewFile) {
				StringBuffer s = new StringBuffer();
				s.append("Chat per ");
				s.append(name);
				s.append(" ");
				s.append(surname);
				s.append(" ");
				s.append(username);
				s.append(" - ");
				s.append(id.toString());
				s.append("\n");
				out.write(s.toString());
			}
			
			if(mittente == null)
				out.append("Ellie: \t\t" + msg + "\n");
			else
				out.append(mittente + ": \t" + msg + "\n");
			
		} catch (IOException e) {
			log.error("Errore trace chat: " + e);
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (IOException e) {
				log.error("Errore: ", e);
			}
		}
	}
	
	private static void delDirectory(File folder) {
		if(!folder.isDirectory())
			folder.delete();
		else {
			List<File> listFile = Arrays.asList(folder.listFiles());
			if(listFile.size() == 0)
				folder.delete();
			else {
				for (File f : listFile) {
					delDirectory(f);
				}
			}
		}
	}
}
