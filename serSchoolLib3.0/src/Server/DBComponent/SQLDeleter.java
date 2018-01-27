package Server.DBComponent;

import Server.Core.SQLCORE;
import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLDeleter extends Thread{

    private Connection conn;
    private ServerView logger;

    SQLSupporter supporter;

    public SQLDeleter(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }


    synchronized public boolean deleteUserAccount(String user_id, String field, int type) throws Exception {
        String table = supporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String delete = "DELETE FROM " + table + " WHERE " + field.toLowerCase() + " = '" + user_id + "';";

            int correct = stmt.executeUpdate(delete);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Utente: " + user_id + " - ELIMINATO");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL: deleteUserAccount fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;
    }

    synchronized public boolean deleteBookByISBN(int isbn) throws Exception {
        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String delete = "DELETE FROM " + SQLSupporter.LIBRI_TABLE_NAME + " WHERE " + SQLSupporter.ISBN.toLowerCase() + " = '" + isbn + "';";

            stmt.executeUpdate(delete);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Libro: " + isbn + " - ELIMINATO");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL:  deleteBookByISBN fallisce per isbn: " + isbn  + " -> " + ex.getMessage());
        }

        return false;
    }

    synchronized public boolean deletePrenotazioneByISBNByID(int isbn, int userid, int type) throws Exception {
        String table = supporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String delete = "DELETE FROM " + table
                    + " WHERE " + SQLSupporter.ISBN.toLowerCase() + " = '" + isbn + "' AND " + SQLSupporter.USERID.toLowerCase() + " = '" + userid + "' ;";

            stmt.executeUpdate(delete);
            stmt.close();

            conn.commit();
            conn.close();

            logger.Write("SQL: Prenotazione: *" + userid + " | " + isbn + "* - ELIMINATA");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("*Errore SQL:  deletePrenotazioneByISBNByID fallisce per isbn: " + isbn  + " e userid: " + userid + " -> " + ex.getMessage());
        }

        return false;
    }
}
