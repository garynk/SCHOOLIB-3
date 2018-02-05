/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Reader;

import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 *E' una classe contente il font, i colori e le grandezze delle scritte usate in tutta l'interfaccia grafica di Reader
 *
 * @author Gerald / Lorenzo
 */

public class ReaderStyle {

    private static String FONT_NAME_PLAIN = "AvenirNext LT Pro Regular";
    private static String FONT_NAME_BOLD = "AvenirNext LT Pro Bold";
    private static String FONT_NAME_ITALIC = "AvenirNext LT Pro Cn";

    public static final java.awt.Color BACKGROUD_DEFAULT_1 = new java.awt.Color(211, 93, 72);
    public static final java.awt.Color BACKGROUD_DEFAULT_2 = new java.awt.Color(225, 129, 108);
    public static final java.awt.Color BUTTON_DEFAULT_COLOR = new java.awt.Color(195, 113, 98);
    public static final java.awt.Color EXCEPTION_COLOR = new java.awt.Color(194, 20, 20);
    public static final java.awt.Color SUCCESS_COLOR = new java.awt.Color(0, 153, 0);
    public static final java.awt.Color DEFAULT_BORDER_COLOR = new java.awt.Color(0, 0, 0);

    public static final java.awt.Font BIG_TITLE_FONT = new java.awt.Font(FONT_NAME_ITALIC, Font.ITALIC, 34);
    public static final java.awt.Font MEDIUM_TITLE_FONT = new java.awt.Font(FONT_NAME_ITALIC, Font.ITALIC, 24);
    public static final java.awt.Font LABEL_FONT = new java.awt.Font(FONT_NAME_BOLD, Font.BOLD, 15);
    public static final java.awt.Font LABEL_FONT_2 = new java.awt.Font(FONT_NAME_PLAIN, Font.PLAIN, 13);
    public static final java.awt.Font LABEL_FONT_3 = new java.awt.Font(FONT_NAME_PLAIN, Font.PLAIN, 14);
    public static final java.awt.Font DISCORSIVE_LABEL_FONT = new java.awt.Font(FONT_NAME_PLAIN, Font.PLAIN, 13);
    public static final java.awt.Font BUTTON_FONT = new java.awt.Font(FONT_NAME_BOLD, Font.BOLD, 13);
    public static final java.awt.Font EXCEPTION_FONT = new java.awt.Font(FONT_NAME_BOLD, Font.BOLD, 12);
    public static final java.awt.Font SUCCESS_LABEL_FONT = new java.awt.Font(FONT_NAME_BOLD, Font.BOLD, 13);

    public static final MatteBorder DEFAULT_MATTE_REGISTRATION = new MatteBorder(0, 0, 1, 0, DEFAULT_BORDER_COLOR);

}
