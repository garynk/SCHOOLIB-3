package Server.DBComponent;

import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLCounter {

    private Connection conn;
    private ServerView logger;

    SQLSupporter supporter;

    /**
     * Costruttore di Classe
     *
     * @param support Oggetto SQLSupporter per ottenere le operazioni utili
     * @param log Oggetto ServerView per gestire gli Output
     * */
    public SQLCounter(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

    /**
     * Conta il numero di Prenotazione o Prestiti presenti appartenenti a un certo ID_Utente
     *
     * @param userid ID dell'utente in oggett
     * @param type Tipo di richiesta (se prenotaz o prestiti)
     *
     * @return il numero di Prenotaz/Prestiti attivi su ID utente
     * */
    synchronized public int countPrenotazioniPrestitiByID(String userid, int type) {


        PreparedStatement stmt = null;

        String table = SQLSupporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query = "SELECT count(*) FROM " + table
                    + " WHERE userid = '" + userid + "';";

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();
            result.next();

            int i = result.getInt("count");

            conn.close();

            return i;

        } catch (Exception er) {
            er.printStackTrace();
            logger.writeException("*Errore SQL: countPrenotazioniPrestitiByID fallisce per userid: " + userid + " -> " + er.getMessage());
        }

        return -1;

    }
}
