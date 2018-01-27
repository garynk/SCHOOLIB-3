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

    public SQLCounter(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

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
            logger.Write("*Errore SQL: countPrenotazioniPrestitiByID fallisce per userid: " + userid + " -> " + er.getMessage());
        }

        return -1;

    }
}
