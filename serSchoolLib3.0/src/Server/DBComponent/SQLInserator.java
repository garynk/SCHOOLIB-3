package Server.DBComponent;

import Common.Libro;
import Common.Utente;
import Server.Graphic.ServerView;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLInserator extends Thread {

    private Connection conn;
    private ServerView logger;

    private SQLSupporter supporter;
    private SQLChecker checker;

    /**
     * Costruttore di classe
     *
     * @param check Oggetto SQLCheker per i check da svolgere
     * @param support Oggetto SQLSupporter per le operazioni di supporto
     * @param log Oggetto ServerView per gestire gli output
     * */
    public SQLInserator(SQLChecker check, SQLSupporter support, ServerView log)
    {
        supporter = support;
        checker = check;
        logger = log;
    }

    /**
     * Metodo per l'inserimento di un Utente generico di qualsiasi tipo all'interno del Database
     *
     * @param generico Oggetto utente da inserire
     *
     * @return boolean true se inserimento a buon fine, false altrimenti
     * */
    synchronized public boolean insertUser(Utente generico) {

        String table = supporter.defineTablebyType(generico.getUserType());

        Statement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String inser = "INSERT INTO " + table + " (userid,nome,cognome,inquadramento,email,numero,password,confirmed,codice) "
                    + "VALUES ('"
                    + generico.GetUserID() + supporter.SQL_INSERT_SEPARATOR
                    + generico.getNome() + supporter.SQL_INSERT_SEPARATOR
                    + generico.getCognome() + supporter.SQL_INSERT_SEPARATOR
                    + generico.getInquadramento() + supporter.SQL_INSERT_SEPARATOR
                    + generico.getEmail() + supporter.SQL_INSERT_SEPARATOR
                    + generico.getNumeroTelefono() + supporter.SQL_INSERT_SEPARATOR
                    + supporter.normalizePsw(Arrays.toString(generico.getPassword())) + supporter.SQL_INSERT_SEPARATOR
                    + generico.getConfirmed() + supporter.SQL_INSERT_SEPARATOR
                    + supporter.normalizeCode(Arrays.toString(generico.getCode()))
                    + "');";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.write("SQL: Utente *" + generico.GetUserID() + "* inserito con successo.");
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.writeException("*Errore SQL: Utente *" + generico.GetUserID() + "* inserimento fallito: " + e.getMessage());
        }

        return false;
    }

    /**
     * Metodo per l'inserimento di un oggetto Libro all'interno del Database
     *
     * @param lib Oggetto Libro da inserire
     *
     * @return boolean true se inserimento a buon fine, false altrimenti
     * */
    synchronized public boolean insertBook(Libro lib) {

        if (checker.checkExistingEasy("ISBN", lib.getISBN(), lib.getObjectType())) {
            logger.writeException("*Errore SQL: Libro *" + lib.getISBN() + "* già presente \n Skipping..");
            return false;
        } else {
        }

        Statement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String inser = "INSERT INTO " + supporter.LIBRI_TABLE_NAME + " (isbn,titolo,autore,casa_editrice,anno_pubblicazione,anno_ristampa,categoria,lingua,scaffale) "
                    + "VALUES ('"
                    + lib.getISBN() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getTitolo() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getAutore() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getCasaEditrice() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getAnnoPubb() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getAnnoRistampa() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getCategoria() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getLingua() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.getScaffale() + " "
                    + "');";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.write("SQL: Libro *" + lib.getISBN() + "* inserito con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.writeException("*Errore SQL: Libro *" + lib.getISBN() + "* inserimento fallito: " + e.getMessage());

        }

        return false;
    }

    /**
     * Metodo per l'inserimento di una Prenotazione all'interno del Database
     *
     * @param isbn ISBN del libro prenotato
     * @param userid ID Utente che ha prenotato
     *
     * @return boolean true se inserimento a buon fine, false altrimenti
     * */
    synchronized public boolean insertPrenotazione(String isbn, String userid) {

        if (checker.checkExistingEasyPrenPres(isbn, userid, 4)) {
            logger.writeException("*Errore SQL: Prenotazione *" + isbn + " | " + userid  + "* già presente.");
            return false;
        } else {
        }

        Statement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String inser = "INSERT INTO " + supporter.PRENOTAZIONI_TABLE_NAME + " (userid,isbn,data) "
                    + "VALUES ('"
                    + Integer.parseInt(userid.trim()) + supporter.SQL_INSERT_SEPARATOR + " "
                    + Integer.parseInt(isbn.trim()) + "', ( "
                    + "CURRENT_TIMESTAMP" + ") "
                    + ");";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.write("SQL: Prenotazione *" + isbn + " | " + userid  + "* inserita con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.writeException("*Errore SQL: Prenotazione *" + userid + "|" + isbn + "* inserimento fallito: " + e.getMessage());
        }

        return false;

    }

    /**
     * Metodo per l'inserimento di un Prestito all'interno del Database
     *
     * @param isbn ISBN del libro in prestito
     * @param userid ID Utente che ha attivato il prestito
     *
     * @return boolean true se inserimento a buon fine, false altrimenti
     * */
    synchronized public boolean insertPrestito(String isbn, String userid) {

        if (checker.checkExistingEasyPrenPres(isbn, userid, 5)) {
            logger.writeException("*Errore SQL: Prestito *" + isbn + " | " + userid  + "* già presente.");
            return false;
        } else {
        }

        Statement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String inser = "INSERT INTO " + supporter.PRESTITI_TABLE_NAME + " (isbn,userid,data_erog,data_ricon) "
                    + "VALUES ('"
                    + Integer.parseInt(isbn.trim()) + supporter.SQL_INSERT_SEPARATOR
                    + Integer.parseInt(userid.trim()) + "', ("
                    + "CURRENT_TIMESTAMP" + ") " + ", ("
                    + "select CURRENT_TIMESTAMP + interval '1' month" + ") "
                    + ");";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.write("SQL: Prestito *" + isbn + " | " + userid  + "* inserito con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.writeException("*Errore SQL: Prestito *" + userid + "|" + isbn + "* inserimento fallito: " + e.getMessage());
        }

        return false;
    }

    /**
     * Metodo per l'inserimento di un Prestito Storico all'interno del Database
     *
     * @param isbn ISBN del libro in prestito
     * @param userid ID Utente che ha attivato il prestito
     *
     * @return boolean true se inserimento a buon fine, false altrimenti
     * */
    synchronized public boolean insertPrestitoStorico(String isbn, String userid) {
        if (checker.checkExistingEasyPrenPres(isbn, userid, 5)) {

            Statement stmt = null;

            try {

                conn = supporter.enstablishConnection();
                conn.setAutoCommit(false);

                stmt = conn.createStatement();

                String inser = "INSERT INTO " + supporter.STORICO_TABLE_NAME + " (isbn,userid,data_ricon) "
                        + "VALUES ('"
                        + Integer.parseInt(isbn.trim()) + supporter.SQL_INSERT_SEPARATOR
                        + Integer.parseInt(userid.trim()) + "', ("
                        + "CURRENT_TIMESTAMP" + ") " + ");";

                stmt.executeUpdate(inser);
                stmt.close();

                conn.commit();
                conn.close();

                return true;

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

            }

        } else {
            logger.writeException("*Errore SQL: Prestito *" + isbn + " | " + userid  + "* non presente.");
            return false;
        }

        return false;
    }

}
