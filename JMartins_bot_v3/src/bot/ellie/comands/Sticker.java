package bot.ellie.comands;

import java.util.Random;

public class Sticker {

	public static final int N_STICKERS = 148;
	private Random random;
	private String sticker;

	/**
	 * crea uno sticker casuale
	 */
	public Sticker() {
		random = new Random();
		sticker = generaSticker();
	}
	/**
	 * crea uno sticker da indice
	 */
	public Sticker(int n) {
		sticker = generaSticker(n);
	}

	/**
	 * @return lo sricker casuale che è stato generato
	 */
	public String getSticker() {
		return sticker;
	}
	
	/**
	 * per manuale
	 */
	private String generaSticker(int n) {
		String s;
		switch (n) {
		case 0:
			s = "BQADBAAD7gAD2dcnASLoEU_rmppYAg";
			break;

		case 1:
			s = "BQADBQAD8gAD6QrIA8yQjbIGeR6rAg";
			break;

		case 2:
			s = "BQADBQAEAQAC6QrIA_fbx7d_f4cjAg";
			break;

		case 3:
			s = "BQADBQADGAEAAukKyAOhBlPpuCGV_AI";
			break;

		case 4:
			s = "BQADBQADBgEAAukKyANcSAABrSLaGqcC";
			break;

		case 5:
			s = "BQADBQADJAEAAukKyAPR4zXzJvghrwI";
			break;

		case 6:
			s = "BQADBQADgAEAAukKyAPHm11LojIaRgI";
			break;

		case 7:
			s = "BQADBQADhgEAAukKyAO7pa2cO91V4QI";
			break;

		case 8:
			s = "BQADBQADlAEAAukKyAPivF2FRfkGFwI";
			break;

		case 9:
			s = "BQADBQADkgEAAukKyAME4kAmQgfbrAI";
			break;

		case 10:
			s = "BQADBQADkAEAAukKyAMEtCrx38plRgI";
			break;

		case 11:
			s = "BQADBQADlgEAAukKyAN1vRzdONe0DgI";
			break;

		case 12:
			s = "BQADBAAD2wADK6lrAaFe4OKLI8ZFAg";
			break;

		case 13:
			s = "BQADAQADYQ0AAtpxZgcY9P774--RvwI";
			break;

		case 14:
			s = "BQADAQADZQ0AAtpxZgcQeTYOql6tBwI";
			break;

		case 15:
			s = "BQADAQADfw0AAtpxZgeFIQr_TOT7LAI";
			break;

		case 16:
			s = "BQADAQADew0AAtpxZge7ro9cmFTG9gI";
			break;

		case 17:
			s = "BQADAQADgw0AAtpxZgfxophVv6sK0wI";
			break;

		case 18:
			s = "BQADAQADiQ0AAtpxZgcSrBd02PojDgI";
			break;

		case 19:
			s = "BQADAQADiw0AAtpxZgfCcZ3YJM2mOwI";
			break;

		case 20:
			s = "BQADAQADkQ0AAtpxZgcaqLoIWoMcXwI";
			break;

		case 21:
			s = "BQADAQADow0AAtpxZgcOpxZBY3kyLQI";
			break;

		case 22:
			s = "BQADAQADoQ0AAtpxZgdUxOXI0ntwDgI";
			break;

		case 23:
			s = "BQADAQADrQ0AAtpxZgeTlrYr2iKGGQI";
			break;

		case 24:
			s = "BQADAQADrw0AAtpxZgdfQRGmNamwaQI";
			break;

		case 25:
			s = "BQADBAAD1AIAAkBS9wErp1K1jyDSPgI";
			break;

		case 26:
			s = "BQADBAAD2gIAAkBS9wGE4xGq91pgfQI";
			break;

		case 27:
			s = "BQADBAAD3gIAAkBS9wHZGrB8UUF4JgI";
			break;

		case 28:
			s = "BQADBAAD4AIAAkBS9wGM3ooGqz_wqwI";
			break;

		case 29:
			s = "BQADBAAD1gIAAkBS9wHG6UFYOJJ1WwI";
			break;

		case 30:
			s = "BQADAgADVwADHPyyBIqUWrLFb8tJAg";
			break;

		case 31:
			s = "BQADAgADYgADHPyyBJVafmE_lDGXAg";
			break;

		case 32:
			s = "BQADAgADZAADHPyyBOSgQa6u3uOHAg";
			break;

		case 33:
			s = "BQADAgADawADHPyyBI1iZngvNhe5Ag";
			break;

		case 34:
			s = "BQADAgADdQADHPyyBD6ueNMFuesNAg";
			break;

		case 35:
			s = "BQADAgADOwoAAlOx9wPG1r1eOaCFXAI";
			break;

		case 36:
			s = "BQADAgADQQoAAlOx9wMqGJCy3T3ccQI";
			break;

		case 37:
			s = "BQADAgADWQoAAlOx9wP21B0YhogX2wI";
			break;

		case 38:
			s = "BQADAgADTQoAAlOx9wMVHStWBIPhUAI";
			break;

		case 39:
			s = "BQADAgADywQAAlD_jgMPXL2-jflSpgI";
			break;

		case 40:
			s = "BQADAgAD-gQAAlD_jgOfs5zRuuca2wI";
			break;
		case 41:
			s = "BQADAgAEBQACUP-OAxSaHTa355sWAg";
			break;

		case 42:
			s = "BQADAgADnAcAAlD_jgM7wcGyFuZLZgI";
			break;

		case 43:
			s = "BQADAgADpAcAAlD_jgP-6RPIbPr3EAI";
			break;

		case 44:
			s = "BQADAgADXwAD9XK2ATCMAyCvkwABpQI";
			break;

		case 45:
			s = "BQADAgADZwAD9XK2AWHNKU2RXeWYAg";
			break;

		case 46:
			s = "BQADAgADaQAD9XK2AT3CQzskuCdXAg";
			break;

		case 47:
			s = "BQADAgADdwAD9XK2AYUTGCn4QQZjAg";
			break;

		case 48:
			s = "BQADAgADkQAD9XK2ATU23ug4oTGaAg";
			break;

		case 49:
			s = "BQADBAADQwEAAhA1aAABepIeDEFqgTAC";
			break;

		case 50:
			s = "BQADBAADSQEAAhA1aAABPPN9JxQ5llIC";
			break;

		case 51:
			s = "BQADBAADTQEAAhA1aAABZNXSsVaBVtgC";
			break;

		case 52:
			s = "BQADBAADRQEAAhA1aAABPK8271I3qZYC";
			break;

		case 53:
			s = "BQADBAADSwEAAhA1aAABKP8v9CC8bh4C";
			break;

		case 54:
			s = "BQADBAADOQEAAl1PhAFrGIDpha_IPwI";
			break;

		case 55:
			s = "BQADBAADOwEAAl1PhAGW7Z7l2Sq0-QI";
			break;

		case 56:
			s = "BQADBAADeQEAAl1PhAGLuwZNlAnLjAI";
			break;

		case 57:
			s = "BQADBAADsgEAAl1PhAERiBkAAdjzO0MC";
			break;

		case 58:
			s = "BQADBAAD1gEAAl1PhAGyp-XywDPzGwI";
			break;

		case 59:
			s = "BQADBAADPAIAAl1PhAHukPVPLqMXGwI";
			break;

		case 60:
			s = "BQADBAADhQIAAl1PhAHfvamYNqUfhAI";
			break;

		case 61:
			s = "BQADBAAD1wIAAl1PhAFnyB_mAso_tQI";
			break;

		case 62:
			s = "BQADAgADuwMAAjq5FQLtCYM50dXJZwI";
			break;

		case 63:
			s = "BQADAgADvQMAAjq5FQKDKb_G-aawSAI";
			break;

		case 64:
			s = "BQADAgAD2wMAAjq5FQLvz4A6VMrzbAI";
			break;

		case 65:
			s = "BQADAgAD9QMAAjq5FQJj6cB5_8YQdwI";
			break;

		case 66:
			s = "BQADAgADYwQAAjq5FQIlx3lrKf3_OwI";
			break;

		case 67:
			s = "BQADBAADBgEAAoglHgaitXmZbpgVYwI";
			break;

		case 68:
			s = "BQADBAADNQEAAoglHgZezD5F81c07AI";
			break;

		case 69:
			s = "BQADBAADIwAD8mn8AowUszP1WWC6Ag";
			break;

		case 70:
			s = "BQADBAADKwAD8mn8AvbnNRa7VYPzAg";
			break;

		case 71:
			s = "BQADBAADOwAD8mn8ArQ5HHg0X6bIAg";
			break;

		case 72:
			s = "BQADBAADTQAD8mn8ArEcw8AuENRHAg";
			break;

		case 73:
			s = "BQADBAADcwAD8mn8AiSdKCJ84dLuAg";
			break;

		case 74:
			s = "BQADAgAD9gEAAmqovAHbu2fDRwUazQI";
			break;

		case 75:
			s = "BQADAgAD9gEAAmqovAHbu2fDRwUazQI";
			break;

		case 76:
			s = "BQADAgAD-gEAAmqovAHejSsTY6pMwgI";
			break;

		case 77:
			s = "BQADAQADJCQAAtpxZgdmhymDwaLFqQI";
			break;

		case 78:
			s = "BQADAQADKCQAAtpxZgeQOmBPS3HuiQI";
			break;

		case 79:
			s = "BQADAQADggEAAs2YnAEMxh7Ipgaq1gI";
			break;

		case 80:
			s = "BQADAQADYgADkqHuCe2pMviRqfVfAg";
			break;

		case 81:
			s = "BQADAQADbgADkqHuCUb4-bv7l3arAg";
			break;

		case 82:
			s = "BQADAQADjgADkqHuCUWdeXusAlusAg";
			break;

		case 83:
			s = "BQADAQADBwEAApKh7glfsRgVMu-u9gI";
			break;

		case 84:
			s = "BQADBAADEgADgd1pAAE_e-0mZ5V9oAI";
			break;

		case 85:
			s = "BQADBAADGgADgd1pAAGvNem4KXjA1AI";
			break;

		case 86:
			s = "BQADBAADHgADgd1pAAEvM7KtzKV_QQI";
			break;

		case 87:
			s = "BQADBAADMQADgd1pAAHFqtbabvfrQwI";
			break;

		case 88:
			s = "BQADAgADaAADCuDjC9c_XAlhj58zAg";
			break;

		case 89:
			s = "BQADAgADcgADCuDjC-jcn7dxchEtAg";
			break;

		case 90:
			s = "BQADAgADeAADCuDjC6DgKp6jVwABiQI";
			break;

		case 91:
			s = "BQADAgADigADCuDjC4jRcVNsfqs7Ag";
			break;

		case 92:
			s = "BQADBAADGgADIvv9BfNbjBhDTzY2Ag";
			break;

		case 93:
			s = "BQADBAADNAADIvv9BYbYkBLjBMhPAg";
			break;

		case 94:
			s = "BQADBAADPgADIvv9BbWQGNybWorlAg";
			break;

		case 95:
			s = "BQADBAADagADIvv9BQG_3bDKLYEjAg";
			break;

		case 96:
			s = "BQADBAADfwADIvv9BUkE7ax7g_sWAg";
			break;

		case 97:
			s = "BQADBAADiQADIvv9BeOvnJ_fdoriAg";
			break;

		case 98:
			s = "BQADAQAD4DcAAtpxZgf_DUM0EFZrrQI";
			break;

		case 99:
			s = "BQADAQAD5DcAAtpxZgeLWikvH4iZtAI";
			break;

		case 100:
			s = "BQADAQADCDgAAtpxZgedM39JoDMxZgI";
			break;

		case 101:
			s = "BQADBAAD3QADvHSnAZgyFgvQ2e7aAg";
			break;

		case 102:
			s = "BQADBAADKQEAArx0pwGckd2RIR9e2QI";
			break;

		case 103:
			s = "BQADBAAD6QADvHSnATXSjHK1QU5ZAg";
			break;

		case 104:
			s = "BQADBAAD4QADvHSnAbN4Momc988cAg";
			break;

		case 105:
			s = "BQADBAADAgEAArx0pwHxHOUm5q9-CQI";
			break;

		case 106:
			s = "BQADBAADSwEAArx0pwETVQKhgLyzGwI";
			break;

		case 107:
			s = "BQADBQADTwADd9N_ARt_ESBnxMSdAg";
			break;

		case 108:
			s = "BQADBQADUQADd9N_AbtrwvfyZnDGAg";
			break;

		case 109:
			s = "BQADBQADXwADd9N_AetBZVIK8GtdAg";
			break;

		case 110:
			s = "BQADBQADcAADd9N_AS_NP8kTdEv3Ag";
			break;

		case 111:
			s = "BQADBAAD2wADcKvVBMiG8hx-91aKAg";
			break;

		case 112:
			s = "BQADBAAD5wADcKvVBIXtW0lbpbz_Ag";
			break;

		case 113:
			s = "BQADBAAD8wADcKvVBA02WyF52gABeQI";
			break;

		case 114:
			s = "BQADBAADCwEAAnCr1QTdku9acbiUnAI";
			break;

		case 115:
			s = "BQADBAADfgEAAnCr1QT9Cc4zlN6d6QI";
			break;

		case 116:
			s = "BQADAgADdQADUhThCjDTZAoB9vaZAg";
			break;

		case 117:
			s = "BQADAgADkwADUhThCqNvXk4Zm3WnAg";
			break;

		case 118:
			s = "BQADAgADrgADUhThCnAuDTW07m6AAg";
			break;

		case 119:
			s = "BQADAgADvwADUhThClbGQ4WoILi3Ag";
			break;

		case 120:
			s = "BQADBQAD7wADR5XbAUyOwGk4w8NUAg";
			break;

		case 121:
			s = "BQADBQAD-gADR5XbAevDB_1lf9XCAg";
			break;

		case 122:
			s = "BQADBQAEAQACR5XbAY3a7-LahBlfAg";
			break;

		case 123:
			s = "BQADBQADEAEAAkeV2wEG3d6WNiqtRgI";
			break;

		case 124:
			s = "BQADBQADGAEAAkeV2wGfLxbeAXiNwAI";
			break;

		case 125:
			s = "BQADAgADSgADnNbnCpcZRs41H_lnAg";
			break;

		case 126:
			s = "BQADAgADSAADnNbnCrgCanzqbDzvAg";
			break;

		case 127:
			s = "BQADAgADTAADnNbnCp9iJzzWsxQmAg";
			break;

		case 128:
			s = "BQADAwADPAADpYrrAAEaElCVtq2EPgI";
			break;

		case 129:
			s = "BQADAwADJgADpYrrAAFYIol7Hr5YSAI";
			break;

		case 130:
			s = "BQADAwADTAADpYrrAAFpuax8zNF88AI";
			break;

		case 131:
			s = "BQADAwADSgADpYrrAAHbCv0TLsAb7gI";
			break;

		case 132:
			s = "BQADBAAD4wADtUGvBvzoxImSR4ECAg";
			break;

		case 133:
			s = "BQADBAAD8wADtUGvBot1Qp_6YmbUAg";
			break;

		case 134:
			s = "BQADBAADBAEAArVBrwbFOIgKgFOwsQI";
			break;

		case 135:
			s = "BQADAQADUwADByXfDYpUoao-ElysAg";
			break;

		case 136:
			s = "BQADAQADXQADByXfDfaUz1aqJPciAg";
			break;

		case 137:
			s = "BQADAQADYwADByXfDWPQD3Vb8CyNAg";
			break;

		case 138:
			s = "BQADAQADVQADByXfDbWy3s8dspUTAg";
			break;

		case 139:
			s = "BQADAQADYQADByXfDbkhf9imOWXMAg";
			break;

		case 140:
			s = "BQADAQADjQADByXfDQeeDVGosf6BAg";
			break;

		case 141:
			s = "BQADAQADkQADByXfDfi6ystbRieDAg";
			break;

		case 142:
			s = "BQADAgADVQEAApb6EgVlavUUeCpBUQI";
			break;

		case 143:
			s = "BQADAgADbQEAApb6EgWKBmr2NlmerAI";
			break;

		case 144:
			s = "BQADAgADVQEAApb6EgVlavUUeCpBUQI";
			break;

		case 145:
			s = "BQADAgADwAEAApb6EgX-lTt2Kk7pxQI";
			break;

		case 146:
			s = "BQADAgADxgEAApb6EgXmZiGuKDGElQI";
			break;

		case 147:
			s = "BQADAgADZgADCuDjC0vHsWW4mTy7Ag";
			break;

		default:
			s = null;
			break;
		}

		return s;
	}
	
	/**
	 * per random
	 */
	private String generaSticker() {
		if (random == null)
			random = new Random();
		int n = random.nextInt(N_STICKERS);
		return generaSticker(n);
		
	}

}
