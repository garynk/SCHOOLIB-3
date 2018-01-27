package Server.DBComponent;

import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLChecker extends Thread{


    private Connection conn;
    private ServerView logger;

    private SQLSupporter supporter;

    public SQLChecker(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }


    synchronized public boolean checkExistingEasy(String column, String to_compare, int type) {
        PreparedStatement stmt = null;

        String table_check = SQLSupporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query = "SELECT count(*) FROM " + table_check
                    + " WHERE " + column.toLowerCase() + " = '" + to_compare + "';";

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();
            result.next();

            int i = result.getInt("count");

            conn.close();

            logger.Write("SQL: Controllo esistenza di *" + to_compare + "* in colonna:" + column);

            return i > 0;

        } catch (Exception er) {
            er.printStackTrace();
            logger.Write("*Errore SQL: Check_Existing in colonna: *" + column + "* con elemento: " + to_compare + " -> " + er.getMessage());
        }

        return false;
    }

    synchronized public boolean checkExistingEasyPrenPres(String isbn, String userid, int type) {
        PreparedStatement stmt = null;

        String table_check = supporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query;

            if (userid.isEmpty()) {

                query = "SELECT count(*) FROM " + table_check
                        + " WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "';";
            } else {

                query = "SELECT count(*) FROM " + table_check
                        + " WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "'AND userid = '" + Integer.parseInt(userid.trim()) + "';";
            }

            logger.Write("SQL: Controllo esistenza di *" + isbn + " | " + userid + "* in " + table_check);

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();
            result.next();

            int i = result.getInt("count");

            conn.close();

            return i > 0;

        } catch (Exception er) {
            er.printStackTrace();
            logger.Write("*Errore SQL: Check_Existing_Prenotaz/Prest per isbn: *" + isbn + "* con userid: " + userid + " -> " + er.getMessage());
        }

        return false;
    }

    synchronized public boolean checkPasswordByID(String user_id, char[] pass, int type) {
        PreparedStatement stmt = null;

        String table_check = supporter.defineTablebyType(type);

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query = "SELECT password psw FROM " + table_check
                    + " WHERE userid = '" + user_id + "';";

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();

            result.next();

            conn.close();

            logger.Write("SQL: Confronto di password per: " + user_id);

            return result.getArray("psw").toString().equals(supporter.normalizePsw(Arrays.toString(pass)));

        } catch (Exception er) {
            er.printStackTrace();
            logger.Write("*Errore SQL: Check_Password con userid: " + user_id + " -> " + er.getMessage());
        }

        return false;
    }

    synchronized public int checkLoginUser(String user_id, char[] pass, int type) {
        if (checkExistingEasy("USERID", user_id, type)) {
            if (checkPasswordByID(user_id, pass, type)) {
                logger.Write("SQL: Utente: " + user_id + ", loggato con successo");
                return 0;
            } else {
                logger.Write("*Errore SQL: Utente: " + user_id + ", confronto password fallto");
                return 2;
            }
        } else {
            logger.Write("*Errore SQL: Utente: " + user_id + ", non presente");
            return 1;
        }
    }


}
