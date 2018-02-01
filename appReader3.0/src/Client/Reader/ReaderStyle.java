/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Reader;

import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 *
 * @author Lorenzo Gavazzeni
 */

public class ReaderStyle {

    // public ReaderStyale(Font f){this.f = f;}
    
    public static String FONT_DIR = "Font/AvenirNextLTPro-Regular.ttf";
    private static String FONT_NAME = "Avenir Next";

    public static final java.awt.Color BACKGROUD_DEFAULT_1 = new java.awt.Color(211, 93, 72);
    public static final java.awt.Color BACKGROUD_DEFAULT_2 = new java.awt.Color(225, 129, 108);
    public static final java.awt.Color BUTTON_DEFAULT_COLOR = new java.awt.Color(195, 113, 98);
    public static final java.awt.Color EXCEPTION_COLOR = new java.awt.Color(194, 20, 20);
    public static final java.awt.Color SUCCESS_COLOR = new java.awt.Color(0, 153, 0);
    public static final java.awt.Color DEFAULT_BORDER_COLOR = new java.awt.Color(0, 0, 0);

    public static final java.awt.Font BIG_TITLE_FONT = new java.awt.Font(FONT_NAME, Font.ITALIC, 30);
    public static final java.awt.Font MEDIUM_TITLE_FONT = new java.awt.Font(FONT_NAME, Font.ITALIC, 18);
    public static final java.awt.Font LABEL_FONT = new java.awt.Font(FONT_NAME, Font.BOLD, 15);
    public static final java.awt.Font LABEL_FONT_2 = new java.awt.Font(FONT_NAME, Font.PLAIN, 12);
    public static final java.awt.Font LABEL_FONT_3 = new java.awt.Font(FONT_NAME, Font.PLAIN, 13);
    public static final java.awt.Font DISCORSIVE_LABEL_FONT = new java.awt.Font(FONT_NAME, Font.PLAIN, 13);
    public static final java.awt.Font BUTTON_FONT = new java.awt.Font(FONT_NAME, Font.BOLD, 11);
    public static final java.awt.Font EXCEPTION_FONT = new java.awt.Font(FONT_NAME, Font.BOLD, 12);
    public static final java.awt.Font SUCCESS_LABEL_FONT = new java.awt.Font(FONT_NAME, Font.BOLD, 14);

    public static final MatteBorder DEFAULT_MATTE_REGISTRATION = new MatteBorder(0, 0, 1, 0, DEFAULT_BORDER_COLOR);

}
