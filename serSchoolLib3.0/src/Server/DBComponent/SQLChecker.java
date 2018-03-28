package Server.DBComponent;

import Server.Graphic.ServerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by Gerald / Lorenzo on 27/09/2017.
 */
public class SQLChecker extends Thread{


    private Connection conn;
    private ServerView logger;

    private SQLSupporter supporter;

    /**
     * Costruttore di Classe
     *
     * @param support Oggetto SQLSupporter per ottenere le operazioni utili
     * @param log Oggetto ServerView per gestire gli Output
     * */
    public SQLChecker(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

    /**
     * Controlla solamente se esiste un certo dato in una certa colonna in una certa tabella
     *
     * @param column Colonna da confrontare
     * @param to_compare Dato da confrontare
     * @param type Identifica la tabella in cui effettuare tale confronto
     *
     * @return true se esiste almeno un elemento con quei dati, false altrimenti
     * */
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

            logger.write("SQL: Controllo esistenza di *" + to_compare + "* in colonna:" + column);

            return i > 0;

        } catch (Exception er) {
            er.printStackTrace();
            logger.writeException("*Errore SQL: Check_Existing in colonna: *" + column + "* con elemento: " + to_compare + " -> " + er.getMessage());
        }

        return false;
    }

    /**
     * Controlla solamente se esiste un certo ID_utente e un certo ISBN_libro in Prenotazioni o Prestiti
     *
     * @param isbn ISBN del libro in oggetto
     * @param userid ID Utente in oggetto
     * @param type Identifica la tabella in cui effettuare tale confronto
     *
     * @return true se esiste almeno un elemento con quei dati, false altrimenti
     * */
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
                        + " WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "'AND userid = '" + userid + "';";
            }

            logger.write("SQL: Controllo esistenza di *" + isbn + " | " + userid + "* in " + table_check);

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();
            result.next();

            int i = result.getInt("count");

            conn.close();

            return i > 0;

        } catch (Exception er) {
            er.printStackTrace();
            logger.writeException("*Errore SQL: Check_Existing_Prenotaz/Prest per isbn: *" + isbn + "* con userid: " + userid + " -> " + er.getMessage());
        }

        return false;
    }

    /**
     * Controlla se la password inserita corrisponde a quella associata all'utente in ingresso
     *
     * @param user_id ID Utente su cui effettuare il controllo
     * @param pass Password su cui fare il confronto
     * @param type Identifica la tabella in cui effettuare tale confronto
     *
     * @return true se corrisponde, false altrimenti
     * */
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

            logger.write("SQL: Confronto di password per: " + user_id);


            return result.getArray("psw").toString().equals(supporter.normalizePsw(Arrays.toString(pass)));

        } catch (Exception er) {
            er.printStackTrace();
            logger.writeException("*Errore SQL: Check_Password con userid: " + user_id + " -> " + er.getMessage());
        }

        return false;
    }

    /**
     * Effettua i controlli sul login dell'utnete differenziando con un intero le diverse eccezioni
     *
     * @param user_id ID Utente su cui effettuare il controllo
     * @param pass Password su cui fare il confronto
     * @param type Identifica la tabella in cui effettuare tale confronto
     *
     * @return 1 se l'utente non esiste; 2 se la password non corrisponde, 0 se il log in ha avuto successo
     * */
    synchronized public int checkLoginUser(String user_id, char[] pass, int type) {
        if (checkExistingEasy("USERID", user_id, type)) {
            if (checkPasswordByID(user_id, pass, type)) {
                logger.write("SQL: Utente: " + user_id + ", loggato con successo");
                return 0;
            } else {
                logger.writeException("*Errore SQL: Utente: " + user_id + ", confronto password fallito");
                return 2;
            }
        } else {
            logger.writeException("*Errore SQL: Utente: " + user_id + ", non presente");
            return 1;
        }
    }


}
