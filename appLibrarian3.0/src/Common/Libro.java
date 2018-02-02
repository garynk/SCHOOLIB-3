/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lorenzo Gavazzeni
 *
 */
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int OBJ_TYPE = 3;

    public static final String[] Categorie_List =  {"saggi","romanzi","matematica","arte","letteratura inglese","letteratura italiana","grammatica"};

    private String ISBN;
    private String Titolo;
    private String Autore;
    private String Casa_Editrice;
    private String Anno_Pubblicaz;
    private String Anno_Ristampa;
    private String Categoria;
    private String Lingua;
    private int Scaffale;

    public Libro() {

    }

    public Libro(String _isbn, String tit, String aut, String c_ed, String anno_pubb, String catg, String lin) {
        ISBN = _isbn;
        Titolo = tit;
        Autore = aut;
        Casa_Editrice = c_ed;
        Anno_Pubblicaz = anno_pubb;
        Categoria = catg;
        Lingua = lin;

        Anno_Ristampa = "NULL";
        Scaffale = 0;
    }

    public Libro(String _isbn, String tit, String aut, String c_ed, String anno_pubb, String catg, String lin, int sca) {
        ISBN = _isbn;
        Titolo = tit;
        Autore = aut;
        Casa_Editrice = c_ed;
        Anno_Pubblicaz = anno_pubb;
        Anno_Ristampa = "NULL";
        Categoria = catg;
        Lingua = lin;
        Scaffale = sca;

    }

    public Libro(String _isbn, String tit, String aut, String c_ed, String anno_pubb, String anno_rist, String catg, String lin) {
        ISBN = _isbn;
        Titolo = tit;
        Autore = aut;
        Casa_Editrice = c_ed;
        Anno_Pubblicaz = anno_pubb;
        Anno_Ristampa = anno_rist;
        Categoria = catg;
        Lingua = lin;
        Scaffale = 0;

    }

    public Libro(String _isbn, String tit, String aut, String c_ed, String anno_pubb, String anno_rist, String catg, String lin, int sca) {
        ISBN = _isbn;
        Titolo = tit;
        Autore = aut;
        Casa_Editrice = c_ed;
        Anno_Pubblicaz = anno_pubb;
        Anno_Ristampa = anno_rist;
        Categoria = catg;
        Lingua = lin;
        Scaffale = sca;

    }

    public void SetAnnoRistampa(String ar) {
        Anno_Ristampa = ar;
    }

    public void SetScaffale(int s) {
        Scaffale = s;
    }

    public String GetISBN() {
        return ISBN;
    }

    public String GetTitolo() {
        return Titolo;
    }

    public String GetAutore() {
        return Autore;
    }

    public String GetCasaEditrice() {
        return Casa_Editrice;
    }

    public String GetAnnoPubb() {
        return Anno_Pubblicaz;
    }

    public String GetAnnoRistampa() {
        if (!Anno_Ristampa.isEmpty()) {
            return Anno_Ristampa;
        } else {
            return "NULL";
        }
    }

    public String GetCategoria() {
        return Categoria;
    }

    public String GetLingua() {
        return Lingua;
    }

    public int GetScaffale() {
        return Scaffale;
    }

    public int GetObjectType() {
        return OBJ_TYPE;
    }

}
