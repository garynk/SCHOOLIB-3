/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.Serializable;

/**
 *
 * @author lorenzogavazzeni1
 */
public class UtenteReader extends Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String[] INQUADRAMENTI_READER_DEFAULT = {"Tecnico","Studente","Docente"};

    public static final int Reader_Default_Type_Value = 2;

    public UtenteReader(String n, String g, String cod, String em, String inq, String num, char[] psw, char[] code) {
        super(n, g, cod, em, inq, num, psw, Reader_Default_Type_Value, code);
    }

}
