package bot.ellie.comands;

import java.util.Random;

public class Sticker {

	private final int N_STICKERS = 79;
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
	 * @return lo sricker casuale che è stato generato
	 */
	public String getSticker() {
		return sticker;
	}

	private String generaSticker() {
		int n = random.nextInt(N_STICKERS);
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
			
		default:
			s = null;
			break;
		}

		return s;
	}

}
