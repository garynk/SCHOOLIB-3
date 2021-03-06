package Client.Librarian;

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

    public void setupPlain(){
        Font plain = null;

        try {

            plain = Font.createFont(Font.TRUETYPE_FONT,plainPath);

            LibrarianStyle.FONT_NAME_PLAIN = "AvenirNext LT Pro Regular";

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

            LibrarianStyle.FONT_NAME_BOLD = "AvenirNext LT Pro Regular";

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

            LibrarianStyle.FONT_NAME_ITALIC = "AvenirNext LT Pro Regular";

        } catch (FontFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        ge.registerFont(italic);

        }
    }




