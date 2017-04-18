package bot.ellie.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.imageio.ImageIO;

public class IMG2ASCII {

    private BufferedImage img;
    private double pixval;
    private PrintWriter prntwrt;
    StringWriter stringWriter = new StringWriter();

    public IMG2ASCII() {
            prntwrt = new PrintWriter(stringWriter);
    }

    public void convertToAscii(String imgname) {
        try {
            img = ImageIO.read(new URL(imgname));
        } catch (IOException e) {
        	e.printStackTrace();
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol
                        .getGreen() * 0.11)));
                print(strChar(pixval));
            }
            try {
                prntwrt.println("");
                prntwrt.flush();
                stringWriter.flush();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
        }
        
        try {
			stringWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String out = stringWriter.toString();
        System.out.println(out);
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }

    public void print(String str) {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            stringWriter.flush();
        } catch (Exception ex) {
        }
    }
}