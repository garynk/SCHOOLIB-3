package Common;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lorenzogavazzeni1
 */
public class Utente implements Serializable {


    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private String codice_fiscale;
    private String email;
    private String inquadramento;
    private String numero_telefono;
    private char[] password;
    private char[] code;
    private int confirmed;
    private int USER_TYPE;

    public Utente() {
        nome = "null";
        cognome = "null";
        codice_fiscale = "null";
        email = "null";
        inquadramento = "null";
        numero_telefono = "null";
        password = "null".toCharArray();
        code = "null".toCharArray();

        confirmed = -1;
        USER_TYPE = 0;
    }

    public Utente(int type) {
        nome = "null";
        cognome = "null";
        codice_fiscale = "null";
        email = "null";
        inquadramento = "null";
        numero_telefono = "null";
        password = "null".toCharArray();
        code = "null".toCharArray();

        confirmed = -1;
        USER_TYPE = type;
    }

    public Utente(String id, int type) {
        codice_fiscale = id;
        USER_TYPE = type;
    }

    public Utente(String n, String g, String cod, String em, String inq, String num, char[] psw, int type, char[] c) {
        nome = n;
        cognome = g;
        codice_fiscale = cod;
        email = em.toLowerCase();
        inquadramento = inq.toLowerCase();
        numero_telefono = num;
        password = psw;
        code = c;

        USER_TYPE = type;
        confirmed = 0;
    }

    public String GetNome() {
        return nome;
    }

    public String GetCognome() {
        return cognome;
    }

    public String GetUserID() {
        return codice_fiscale;
    }

    public String GetEmail() {
        return email;
    }

    public String GetInquadramento() {
        return inquadramento;
    }

    public String GetNumeroTelefono() {
        return numero_telefono;
    }

    public int GetUserType() {
        return USER_TYPE;
    }

    public void SetUserID(String id) {
        codice_fiscale = id;
    }

    public void SetType(int type) {
        USER_TYPE = type;
    }

}
