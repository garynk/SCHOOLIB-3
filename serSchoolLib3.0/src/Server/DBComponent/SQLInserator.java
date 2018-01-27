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

    public SQLInserator(SQLChecker check, SQLSupporter support, ServerView log)
    {
        supporter = support;
        checker = check;
        logger = log;
    }


    synchronized public boolean insertUser(Utente generico) {

        String table = supporter.defineTablebyType(generico.GetUserType());

        Statement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String inser = "INSERT INTO " + table + " (userid,nome,cognome,inquadramento,email,numero,password,confirmed,codice) "
                    + "VALUES ('"
                    + generico.GetUserID() + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetNome() + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetCognome() + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetInquadramento() + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetEmail() + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetNumeroTelefono() + supporter.SQL_INSERT_SEPARATOR
                    + supporter.normalizePsw(Arrays.toString(generico.GetPassword())) + supporter.SQL_INSERT_SEPARATOR
                    + generico.GetConfirmed() + supporter.SQL_INSERT_SEPARATOR
                    + supporter.normalizeCode(Arrays.toString(generico.GetCode()))
                    + "');";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Utente *" + generico.GetUserID() + "* inserito con successo.");
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.Write("*Errore SQL: Utente *" + generico.GetUserID() + "* inserimento fallito: " + e.getMessage());
        }

        return false;
    }

    synchronized public boolean insertBook(Libro lib) {

        if (checker.checkExistingEasy("ISBN", lib.GetISBN(), lib.GetObjectType())) {
            logger.Write("*Errore SQL: Libro *" + lib.GetISBN() + "* già presente \n Skipping..");
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
                    + lib.GetISBN() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetTitolo() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetAutore() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetCasaEditrice() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetAnnoPubb() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetAnnoRistampa() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetCategoria() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetLingua() + supporter.SQL_INSERT_SEPARATOR + " "
                    + lib.GetScaffale() + " "
                    + "');";

            stmt.executeUpdate(inser);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Libro *" + lib.GetISBN() + "* inserito con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.Write("*Errore SQL: Libro *" + lib.GetISBN() + "* inserimento fallito: " + e.getMessage());

        }

        return false;
    }

    synchronized public boolean insertPrenotazione(String isbn, String userid) {

        if (checker.checkExistingEasyPrenPres(isbn, userid, 4)) {
            logger.Write("*Errore SQL: Prenotazione *" + isbn + " | " + userid  + "* già presente.");
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

            logger.Write("SQL: Prenotazione *" + isbn + " | " + userid  + "* inserita con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.Write("*Errore SQL: Prenotazione *" + userid + "|" + isbn + "* inserimento fallito: " + e.getMessage());
        }

        return false;

    }

    synchronized public boolean insertPrestito(String isbn, String userid) {

        if (checker.checkExistingEasyPrenPres(isbn, userid, 5)) {
            logger.Write("*Errore SQL: Prestito *" + isbn + " | " + userid  + "* già presente.");
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

            logger.Write("SQL: Prestito *" + isbn + " | " + userid  + "* inserito con successo.");

            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.Write("*Errore SQL: Prestito *" + userid + "|" + isbn + "* inserimento fallito: " + e.getMessage());
        }

        return false;
    }

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
            logger.Write("*Errore SQL: Prestito *" + isbn + " | " + userid  + "* non presente.");
            return false;
        }

        return false;
    }

}
