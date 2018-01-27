package Server.DBComponent;

import Server.Core.SQLCORE;
import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLUpdater {

    private Connection conn;
    private ServerView logger;

    private SQLSupporter supporter;

    public SQLUpdater(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }


    synchronized public boolean updateUserPassword(String user_id, char[] new_psw, int type) throws Exception {
        String table = SQLSupporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String update = "UPDATE " + table + " SET Password = '" + supporter.normalizePsw(Arrays.toString(new_psw)) + "' WHERE userid = '" + user_id + "';";

            int correct = stmt.executeUpdate(update);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Utente: " + user_id + " modifica password");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL:  updateUserPassword fallisce per userid: " + user_id + " -> " + ex.getMessage());
        }

        return false;
    }

    synchronized public boolean updateUserInformation(String user_id, String field, String info, int type) {
        String table = SQLSupporter.defineTablebyType(type);

        try {
            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            int correct;

            try (Statement stmt = conn.createStatement()) {
                String update = "UPDATE " + table + " SET " + field.toLowerCase() + " = '" + info + "' WHERE userid = '" + user_id + "';";
                correct = stmt.executeUpdate(update);
            }

            conn.commit();
            conn.close();

            switch (field) {
                case SQLSupporter.EMAIL:
                    logger.Write("SQL: Utente: " + user_id + " - email aggiornata");
                    return correct > 1;
                case SQLSupporter.NUMERO:
                    logger.Write("SQL: Utente: " + user_id + " - numero aggiornato");
                    return correct > 1;
                case SQLSupporter.INQUADRAMENTO:
                    logger.Write("SQL: Utente: " + user_id + " - inquadramento aggiornato");
                    return correct > 1;
                case SQLSupporter.CONFIRMED:
                    logger.Write("SQL: Utente: " + user_id + " - status account aggiornato");
                    return correct > 1;
                case SQLSupporter.PASSWORD:
                    logger.Write("SQL: Utente: " + user_id + " - password aggiornata");
                    return correct > 1;
                case SQLSupporter.TENTATIVI:
                    logger.Write("SQL: Utente: " + user_id + " - tentativi aggiornati");
                    return correct > 1;
                default:
                    logger.Write("*Errore SQL: Campo: " + field + " - non consentito");
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL: updateUserInformation fallisce per userid: " + user_id + " e info: " + info + " -> " + ex.getMessage());
        }

        return false;
    }

    synchronized public boolean updateAttemptLogin(String user_id, int type) {
        String table = SQLSupporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String update = "UPDATE " + table + " SET tentativi = tentativi-1 WHERE userid = '" + user_id + "' AND tentativi > 0;";

            int correct = stmt.executeUpdate(update);
            stmt.close();

            conn.commit();
            conn.close();

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL: updateAttemptLogin fallisce per userid: " + user_id + " -> " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore: updateAttemptLogin fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;

    }

    synchronized public boolean updateRegistrationCode(String user_id, char[] code, int type) throws Exception {
        String table = SQLSupporter.defineTablebyType(type);

        if (code.length > 10) {
            logger.Write("*Errore SQL: Utente: " + user_id + " - tentativo codice: Fallito");
            return false;
        }

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String update = "UPDATE " + table + " SET Codice = '" + Arrays.toString(code) + "' WHERE userid = '" + user_id + "';";

            int correct = stmt.executeUpdate(update);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Utente: " + user_id + " - tentativo codice: a Buon Fine");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL: updateRegistrationCode fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;
    }

    synchronized public boolean updateBookStatus(String isbn, int status) {

        try {
            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            int correct;

            try (Statement stmt = conn.createStatement()) {

                String update = "UPDATE " + SQLSupporter.LIBRI_TABLE_NAME
                        + " SET " + SQLSupporter.DISPONIBILE.toLowerCase() + " = '" + status + "' WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "';";

                correct = stmt.executeUpdate(update);

                logger.Write("SQL: Libro: " + isbn+ " - status: aggiornato");

                return correct > 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL: updateBookStatus fallisce per isbn: " + isbn  + " -> " + ex.getMessage());
        }

        return false;
    }


}
