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

    public static final int OBJ_TYPE = 3;

    public static final List<String> Categorie_List = new ArrayList<String>() {
        {
            add("matematica");
            add("letteratura");
            add("romanzo");
            add("saggi");
            add("cucina");
            add("didattica");
        }
    };

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

    public void setAnnoRistampa(String ar) {
        Anno_Ristampa = ar;
    }

    public void setScaffale(int s) {
        Scaffale = s;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitolo() {
        return Titolo;
    }

    public String getAutore() {
        return Autore;
    }

    public String getCasaEditrice() {
        return Casa_Editrice;
    }

    public String getAnnoPubb() {
        return Anno_Pubblicaz;
    }

    public String getAnnoRistampa() {
        if (!Anno_Ristampa.isEmpty()) {
            return Anno_Ristampa;
        } else {
            return "NULL";
        }
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getLingua() {
        return Lingua;
    }

    public int getScaffale() {
        return Scaffale;
    }

    public int getObjectType() {
        return OBJ_TYPE;
    }

}
