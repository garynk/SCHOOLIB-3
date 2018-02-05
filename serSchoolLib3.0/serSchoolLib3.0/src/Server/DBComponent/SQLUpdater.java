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
 * Created by Gerald / Lorenzo on 27/09/2017.
 */
public class SQLUpdater {

    private Connection conn;
    private ServerView logger;

    private SQLSupporter supporter;

    /**
     * Costruttore della classe, necessita di due oggetti:
     * SQLSupporter e ServerView
     *
     * @param support Oggetto SQLSupport per operazioni generali su SQL.
     * @param log Oggetto ServerView per inviare messaggi alla console del Server
     */
    public SQLUpdater(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }


    /**
     * Aggiorna nel database la password di un Utente, di qualsiasi tipo, esistente con una in ingresso
     *
     * @param user_id L'ID dell'utente a cui andrà modificata la password
     * @param new_psw la nuova password che andrà inserita
     * @param type il tipo di utente che si sta andando a modificare (1 = Librarian ; 2 = Reader )
     *
     * @return boolean true (se la password è stata  modificata con successo) false altrimenti
     * */
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

            logger.write("SQL: Utente: " + user_id + " modifica password");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL:  updateUserPassword fallisce per userid: " + user_id + " -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Aggiorna nel database di qualsiasi tipo di utente un qualsiasi tipo di campo con una qualsiasi tipo di informazione
     *
     * @param user_id L'ID dell'utente a cui andrà modificata la password
     * @param field il campo da moficiare
     * @param info l'informazione da moficiare
     * @param type il tipo di utente che si sta andando a modificare (1 = Librarian ; 2 = Reader )
     *
     * @return boolean true (se l'informazione è stata modificata con successo) false altrimenti
     * */
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
                    logger.write("SQL: Utente: " + user_id + " - email aggiornata");
                    return correct > 1;
                case SQLSupporter.NUMERO:
                    logger.write("SQL: Utente: " + user_id + " - numero aggiornato");
                    return correct > 1;
                case SQLSupporter.INQUADRAMENTO:
                    logger.write("SQL: Utente: " + user_id + " - inquadramento aggiornato");
                    return correct > 1;
                case SQLSupporter.CONFIRMED:
                    logger.write("SQL: Utente: " + user_id + " - status account aggiornato");
                    return correct > 1;
                case SQLSupporter.PASSWORD:
                    logger.write("SQL: Utente: " + user_id + " - password aggiornata");
                    return correct > 1;
                case SQLSupporter.TENTATIVI:
                    logger.write("SQL: Utente: " + user_id + " - tentativi aggiornati");
                    return correct > 1;
                default:
                    logger.writeException("*Errore SQL: Campo: " + field + " - non consentito");
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL: updateUserInformation fallisce per userid: " + user_id + " e info: " + info + " -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Aggiorna nel database i tentativi di accesso relativi a un qualsiasi tipo di utente
     *
     * @param user_id L'ID dell'utente
     * @param type il tipo di utente che si sta andando a modificare (1 = Librarian ; 2 = Reader )
     *
     * @return boolean true (i tentativi sono stati moficiati con successo) false altrimenti
     * */
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
            logger.writeException("*Errore SQL: updateAttemptLogin fallisce per userid: " + user_id + " -> " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore: updateAttemptLogin fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;

    }

    /**
     * Aggiorna nel database il codice di accesso relativo a un qualsiasi tipo di utente
     *
     * @param user_id L'ID dell'utente
     * @param code il codice che si sta inserendo
     * @param type il tipo di utente che si sta andando a modificare (1 = Librarian ; 2 = Reader )
     *
     * @return boolean true ( se il codice è stato modificato con successo) false altrimenti
     * */
    synchronized public boolean updateRegistrationCode(String user_id, char[] code, int type) throws Exception {
        String table = SQLSupporter.defineTablebyType(type);

        if (code.length > 10) {
            logger.writeException("*Errore SQL: Utente: " + user_id + " - tentativo codice: Fallito");
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

            logger.write("SQL: Utente: " + user_id + " - tentativo codice: a Buon Fine");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL: updateRegistrationCode fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Aggiorna nel database lo stato di prenotabilità del libro
     *
     * @param isbn L'ISBN del libro
     * @param status lo stato del libro attuale
     *
     * @return boolean true (se lo stato è stato moficiato con successo) false altrimenti
     * */
    synchronized public boolean updateBookStatus(String isbn, int status) {

        try {
            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            int correct;

            try (Statement stmt = conn.createStatement()) {

                String update = "UPDATE " + SQLSupporter.LIBRI_TABLE_NAME
                        + " SET " + SQLSupporter.DISPONIBILE.toLowerCase() + " = '" + status + "' WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "';";

                correct = stmt.executeUpdate(update);

                logger.write("SQL: Libro: " + isbn+ " - status: aggiornato");

                return correct > 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL: updateBookStatus fallisce per isbn: " + isbn  + " -> " + ex.getMessage());
        }

        return false;
    }


}
