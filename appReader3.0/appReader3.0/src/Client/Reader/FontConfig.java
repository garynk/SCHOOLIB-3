package Client.Reader;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Configura il font usato dal programma
 *
 * @author Gerald Shyti
 * */

public class FontConfig{
    private File plainPath;
    private File boldPath;
    private File italicPath;
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public FontConfig(File plainPath, File boldPath, File italicPath){
        this.plainPath = plainPath;
        this.boldPath = boldPath;
        this.italicPath = italicPath;

    }

    //Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("REVOLUTION.ttf"))

    public void setupPlain(){
        Font plain = null;
        try {
            plain = Font.createFont(Font.TRUETYPE_FONT,plainPath);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ge.registerFont(plain);
    }
    public void setupBold(){
        Font bold = null;
        try {
            bold = Font.createFont(Font.TRUETYPE_FONT, boldPath);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ge.registerFont(bold);
    }
    public void setupItalic(){
        Font italic = null;
        try {
            italic = Font.createFont(Font.TRUETYPE_FONT, italicPath);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ge.registerFont(italic);

        }
    }




