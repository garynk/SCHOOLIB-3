package Server.DBComponent;

import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Lorenzo Gavazzeni on 03/10/2017.
 */
public class SQLSupporter {

    public static final String SQL_INSERT_SEPARATOR = "','";

    public static final String LIBRARIAN_TABLE_NAME = "LIBRARIAN";
    public static final String READER_TABLE_NAME = "READER";
    public static final String LIBRI_TABLE_NAME = "LIBRI";
    public static final String PRENOTAZIONI_TABLE_NAME = "PRENOTAZIONI";
    public static final String PRESTITI_TABLE_NAME = "PRESTITI";
    public static final String STORICO_TABLE_NAME = "STORICO_PRESTITI";

    public static final String USERID = "USERID";
    public static final String INQUADRAMENTO = "INQUADRAMENTO";
    public static final String NOME = "NOME";
    public static final String COGNOME = "COGNOME";
    public static final String EMAIL = "EMAIL";
    public static final String NUMERO = "NUMERO";
    public static final String PASSWORD = "PASSWORD";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String CODICE = "CODICE";
    public static final String TENTATIVI = "TENTATIVI";

    public static final String ISBN = "ISBN";
    public static final String TITOLO = "TITOLO";
    public static final String AUTORE = "AUTORE";
    public static final String CASA_EDITRICE = "CASA_EDITRICE";
    public static final String ANNO_PUBB = "ANNO_PUBBLICAZIONE";
    public static final String ANNO_RISTAMPA = "ANNO_RISTAMPA";
    public static final String CATEGORIA = "CATEGORIA";
    public static final String LINGUA = "LINGUA";
    public static final String SCAFFALE = "SCAFFALE";
    public static final String DISPONIBILE = "DISPONIBILE";

    public static final String DEFAULT_BOOK_PARAM = "isbn,titolo,autore,anno_pubblicazione,categoria,lingua,scaffale";

    public static final String DATA_P = "DATA";

    public static final String INCORSO = "IN_CORSO";
    public static final String DATA_E = "DATA_Erog";
    public static final String DATA_R = "DATA_Ricon";

    public static final String STORICO_SERIAL = "SERIAL";

    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    private static ServerView logger;

    /**
     * Costruttore della classe
     *
     * @param url URL del database
     * @param user l'USERNAME del database
     * @param psw la PASSWORD del database
     * @param log la ServerView per gestire gli output
     * */
    public SQLSupporter(String url, String user, String psw, ServerView log)
    {
        DB_URL = url;
        DB_USERNAME = user;
        DB_PASSWORD = psw;
        logger = log;
    }

    /**
     * Ritorna una Stringa con il nome della tabella secondo un tipo dato
     *
     * @param type numero identatificativo della tabella
     *
     * @return Stringa con il nome della tabella
     * */
    public static String defineTablebyType(int type) {

        switch (type) {
            case 1:
                return LIBRARIAN_TABLE_NAME;
            case 2:
                return READER_TABLE_NAME;
            case 3:
                return LIBRI_TABLE_NAME;
            case 4:
                return PRENOTAZIONI_TABLE_NAME;
            case 5:
                return PRESTITI_TABLE_NAME;
            default:
                return "NULL";
        }

    }

    /**
     * Stabilisce la connessione con il datablase
     *
     * @return Connection se la connessione è andata a buon fine
     * */
    public static Connection enstablishConnection()
    {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            logger.write("> ERRORE CRITICO: Impossibile stabilire connessione con DB " + e.getMessage());
        }

        return null;
    }

    /**
     * Normalizza la password in ingresseo
     *
     * @param to_normalize la Stringa in ingresso da normalizzare
     *
     * @return una Stringa normalizzata
     * */
    public String normalizePsw(String to_normalize) {
        String normalized_psw = "";

        for (int i = 0; i < to_normalize.length(); i++) {
            if (Character.isLetterOrDigit(to_normalize.charAt(i))) {
                normalized_psw += to_normalize.charAt(i);
            } else {
            }
        }

        return normalized_psw;
    }


    /**
     * Normalizza un codice in ingresseo
     *
     * @param to_normalize la Stringa in ingresso da normalizzare
     *
     * @return una Stringa normalizzata
     * */
    public String normalizeCode(String to_normalize) {
        String normalized_code = "";

        for (int i = 0; i < to_normalize.length(); i++) {
            if (Character.isDigit(to_normalize.charAt(i))) {
                normalized_code += to_normalize.charAt(i);
            } else {
            }
        }

        return normalized_code;
    }

}
