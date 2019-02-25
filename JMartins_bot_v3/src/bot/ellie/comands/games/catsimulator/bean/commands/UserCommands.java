package bot.ellie.comands.games.catsimulator.bean.commands;

import java.util.Random;

import bot.ellie.comands.games.catsimulator.bean.Cat;
import bot.ellie.comands.games.catsimulator.bean.PartitaBean;
import bot.ellie.comands.games.catsimulator.bean.malattie.Raffreddore;
import bot.ellie.comands.games.catsimulator.messages.CondizioneMessages;
import bot.ellie.comands.games.catsimulator.messages.FameMessages;
import bot.ellie.comands.games.catsimulator.messages.SeteMessages;
import bot.ellie.comands.games.catsimulator.messages.SonnoMessages;

public class UserCommands {
	
	private static int getPercentuale() {
		return (new Random().nextInt(100) + 1);
	}
	
	public static class Coccola {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setCondizione(
					(cat.getCondizione() + 2));
			cat.setMalattia(new Raffreddore());
			cat.setUmore(
					cat.getUmore() + 10);
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(1);
			return partitaBean;
		}
	}
	
	public static class DaiCibo {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setCondizione(
					(cat.getCondizione() + 2));
			cat.setFame(
					cat.getFame() + 40);
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(1);
			return partitaBean;
		}
	}
	
	public static class DaiLattino {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setCondizione(
					(cat.getCondizione() + 2));
			cat.setSete(
					cat.getSete() + 40);
			cat.setFame(
					cat.getFame() + 10);
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(1);
			return partitaBean;
		}
	}
	
	public static class PortaASpasso {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setUmore(
					cat.getUmore() + 25);
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(2);
			return partitaBean;
		}
	}
	
	public static class FaiNanna {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setCondizione(
					(cat.getCondizione() + 1));
			cat.setSonno(
					cat.getSonno() + 50);
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(6);
			return partitaBean;
		}
	}
	
	public static class Controlla {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			
			StringBuffer sb = new StringBuffer("Controlli il tuo gattino:\n");
			
			Cat cat = partitaBean.getCat();
			if(cat.getMalattia() != null) {
				//il gatto è malato, calcolo se si riesce a capire
				if(15 < getPercentuale()) {
					sb.append("Il tuo gattino sembrerebbe malato, sarebbe meglio portarlo dal dottore prima che sia troppo tardi");
				} else {
					sb.append("Il tuo gattino e sembra stare bene");
				}
			} else {
				sb.append("Il tuo gattino sembra in forma");
			}
			
			sb.append("\n" + CondizioneMessages.calculateMessage(cat.getCondizione()));
			sb.append("\n" + FameMessages.calculateMessage(cat.getFame()));
			sb.append("\n" + SeteMessages.calculateMessage(cat.getSete()));
			sb.append("\n" + SonnoMessages.calculateMessage(cat.getSonno()));
			
			partitaBean.checkMessage = sb.toString();
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(1);
			return partitaBean;
		}
	}
	
	public static class DalDottore {
		public static PartitaBean executeCommand(PartitaBean partitaBean) {
			Cat cat = partitaBean.getCat();
			cat.setCondizione(
					(cat.getCondizione() + 2));
			cat.setUmore(
					(cat.getUmore() - 5));
			
			//Visita in caso di malattie e ferite
			if(cat.getMalattia() != null) {
				cat.getMalattia().addProbabilitàDiCura(60);
			}
			if(cat.getCondizione() < 50) {
				cat.setCondizione(
						cat.getCondizione() + 40);
			}
			
			partitaBean.setCat(cat);
			partitaBean.addTimeTempoUltimaAzione(2);
			return partitaBean;
		}
	}
	
}
