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
public class UtenteLibrarian extends Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String inq_default = "Bibliotecario";

    public static final int Librarian_Default_Type_Value = 1;

    public UtenteLibrarian() {
        super();
    }

    public UtenteLibrarian(String id) {
        super(id, Librarian_Default_Type_Value);
    }

    public UtenteLibrarian(String n, String g, String cod, String em, String num, char[] psw, char[] code) {
        super(n, g, cod, em, inq_default, num, psw, Librarian_Default_Type_Value, code);
    }

}
