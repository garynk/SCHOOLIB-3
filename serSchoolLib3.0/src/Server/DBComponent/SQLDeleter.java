package Server.DBComponent;

import Server.Core.SQLCORE;
import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Gerald / Lorenzo on 27/09/2017.
 */
public class SQLDeleter extends Thread{

    private Connection conn;
    private ServerView logger;

    SQLSupporter supporter;

    /**
     * Costruttore di classe
     *
     * @param support Oggetto SQLSupporter per avere le operazioni generali
     * @param log Oggetto ServerView per gestire gli output
     * */
    public SQLDeleter(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

    /**
     * Metodo per cancellare un Utente di qualsiasi tipo
     *
     * @param user_id ID utente da cancellare
     * @param type il tipo di utente da cancellare (LIB/READ)
     *
     * @return boolean true se a buon fine, false altrimenti
     * */
    synchronized public boolean deleteUserAccount(String user_id, int type) throws Exception {
        String table = supporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            String delete = "DELETE FROM " + table + " WHERE userid = '" + user_id + "';";

            int correct = stmt.executeUpdate(delete);
            stmt.close();

            conn.commit();
            conn.close();

            logger.write("SQL: Utente: " + user_id + " - ELIMINATO");

            return correct > 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL: deleteUserAccount fallisce per userid: " + user_id  + " -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Metodo per cancellare un Libro dalla tabella
     *
     * @param isbn ISBN del libro da cancellare
     *
     * @return boolean true se a buon fine, false altrimenti
     * */
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

            logger.write("SQL: Libro: " + isbn + " - ELIMINATO");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL:  deleteBookByISBN fallisce per isbn: " + isbn  + " -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Elimina una Prenotazione a partire da un isbn, un ID utente
     *
     * @param isbn ISBN del libro della prenotazione da cancellare
     * @param userid ID utente dell'utente della prenotazione da cancellare
     * @param type tipo di utente per differenziare le tabelle
     *
     * @return boolean true se a buon fine, false altrimenti
     * */
    synchronized public boolean deletePrenotazioneByISBNByID(int isbn, String userid, int type) throws Exception {
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

            logger.write("SQL: Prenotazione: *" + userid + " | " + isbn + "* - ELIMINATA");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.writeException("*Errore SQL:  deletePrenotazioneByISBNByID fallisce per isbn: " + isbn  + " e userid: " + userid + " -> " + ex.getMessage());
        }

        return false;
    }
}
