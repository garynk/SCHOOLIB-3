package Common;

import java.io.Serializable;


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

    public char[] GetPassword() {
        return password;

    }

    public char[] GetCode() {
        return code;
    }

    public int GetConfirmed() {
        return confirmed;
    }

    public int GetUserType() {
        return USER_TYPE;
    }



    public void SetNome(String n) {
        nome = n;
    }

    public void SetCognome(String g) {
        cognome = g;
    }

    public void SetUserID(String id) {
        codice_fiscale = id;
    }

    public void SetEmail(String em) {
        email = em.toLowerCase();
    }

    public void SetInquadramento(String in) {
        inquadramento = in.toLowerCase();
    }

    public void SetNumeroTelefono(String num) {
        numero_telefono = num;
    }

    public void SetPassword(char[] psw) {
        password = psw;
    }

    public void SetCode(char[] c) {
        code = c;
    }

    public void SetConfirmed(int status) {
        confirmed = status;
    }


}
