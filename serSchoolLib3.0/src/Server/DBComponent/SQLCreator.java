package Server.DBComponent;

import Server.Graphic.ServerView;


import java.sql.*;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLCreator extends Thread{

    private static final String TEXT_NOT_NULL = "TEXT NOT NULL";

    private Connection conn;
    private ServerView logger;

    SQLSupporter supporter;

    public SQLCreator(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

    private boolean check_Existing_Table(String table_name) {

        ResultSet table = null;
        try {

            DatabaseMetaData dbm = conn.getMetaData();

            table = dbm.getTables(null, null, table_name.toLowerCase(), null);

            if (!table.next())
            {
                table.close();
                return false;
            }
            else
            {
                table.close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    synchronized private void creatingTableUser(String table_name) {

        if (!check_Existing_Table(table_name)) {

            Statement stmt = null;

            try {

                stmt = conn.createStatement();

                String sql = "CREATE TABLE " + table_name
                        + "(" + supporter.USERID + " SERIAL  PRIMARY KEY,"
                        +
                        " " + supporter.NOME + " " + TEXT_NOT_NULL + ", "
                        +
                        " " + supporter.COGNOME + " " + TEXT_NOT_NULL + ", "
                        +
                        " " + supporter.INQUADRAMENTO + " " + TEXT_NOT_NULL + ", "
                        +
                        " " + supporter.EMAIL + " " + TEXT_NOT_NULL + ", "
                        +
                        " " + supporter.NUMERO + " " + TEXT_NOT_NULL + ", "
                        +
                        " " + supporter.PASSWORD + " VARCHAR(25) NOT NULL" + ", "
                        + " " + supporter.CONFIRMED + " SMALLINT NOT NULL DEFAULT 0" + ", "
                        + " " + supporter.CODICE + " TEXT" + ", "
                        + " " + supporter.TENTATIVI + " SMALLINT NOT NULL DEFAULT 5" + ", "
                        + " " + "CONSTRAINT confirmed_const CHECK (confirmed = 0 OR confirmed = 1),"
                        + " " + "CONSTRAINT attempt_const CHECK (tentativi < 5 OR tentativi > 0)"
                        + " );";

                stmt.executeQuery(sql);
                stmt.close();

                logger.Write("SQL: Tabella *" + table_name + "* Creata con successo");

                conn.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

                logger.Write("*Errore SQL: Statment Creazione Tabella *" + table_name + "* ");
            }
        } else {
            logger.Write("*SQL: Tabella *" + table_name + "* già presente \n Skipping...");
        }

    }

    synchronized private void creatingTableLibri(String table_name) {

        if (!check_Existing_Table(table_name)) {

            Statement stmt = null;

            try {

                stmt = conn.createStatement();

                String sql = "CREATE TABLE " + table_name
                        + "(" + SQLSupporter.ISBN + " SERIAL  PRIMARY KEY, "
                        + " " + SQLSupporter.TITOLO + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.AUTORE + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.CASA_EDITRICE + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.ANNO_PUBB + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.ANNO_RISTAMPA + " TEXT, "
                        + " " + SQLSupporter.CATEGORIA + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.LINGUA + " " + TEXT_NOT_NULL + ", "
                        + " " + SQLSupporter.SCAFFALE + " INTEGER " + ", "
                        + " " + SQLSupporter.DISPONIBILE + " SMALLINT NOT NULL DEFAULT 1" + ", "
                        + " " + "CONSTRAINT disponibile_const CHECK (disponibile = 0 OR disponibile = 1)"
                        + " );";

                stmt.executeUpdate(sql);
                stmt.close();

                logger.Write("SQL: Tabella *" + table_name + "* Creata con successo");

                conn.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

                logger.Write("*Errore SQL: Statment Creazione Tabella *" + table_name + "* ");
            }
        } else {
            logger.Write("*SQL: Tabella *" + table_name + "* già presente \n Skipping...");
        }

    }

    synchronized private void creatingTablePrenot(String table_name) {

        if (!check_Existing_Table(table_name)) {
            Statement stmt = null;

            try {

                stmt = conn.createStatement();

                String sql = "CREATE TABLE " + table_name
                        + "(" + supporter.USERID + " SERIAL NOT NULL, "
                        +
                        " " + supporter.ISBN + " SERIAL NOT NULL, "
                        +
                        " " + supporter.DATA_P + " TIMESTAMP NOT NULL, "
                        +
                        " FOREIGN KEY(USERID) REFERENCES " + supporter.READER_TABLE_NAME + "(USERID) ON DELETE CASCADE, "
                        + " FOREIGN KEY(ISBN) REFERENCES " + supporter.LIBRI_TABLE_NAME + "(ISBN) ON DELETE CASCADE, "
                        + " PRIMARY KEY(USERID, ISBN) "
                        + " );";

                stmt.executeQuery(sql);
                stmt.close();

                conn.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

                logger.Write("*Errore SQL: Statment Creazione Tabella *" + table_name + "* ");
            }
        } else {
            logger.Write("*SQL: Tabella *" + table_name + "* già presente \n Skipping...");
        }

    }

    synchronized private void creatingTablePrestiti(String table_name) {

        if (!check_Existing_Table(table_name)) {
            Statement stmt = null;

            try {

                stmt = conn.createStatement();

                String sql = "CREATE TABLE " + table_name
                        + "(" + supporter.ISBN + " SERIAL NOT NULL,"
                        +
                        " " + supporter.USERID + " SERIAL NOT NULL, "
                        +
                        " " + supporter.DATA_E + " TIMESTAMP NOT NULL, "
                        + " " + supporter.DATA_R + " TIMESTAMP, "
                        + " " + supporter.INCORSO + " SMALLINT NOT NULL DEFAULT 1" + ", "
                        + " " + "CONSTRAINT in_corso_constr CHECK (in_corso = 0 OR in_corso = 1),"
                        +
                        " FOREIGN KEY(ISBN) REFERENCES " + supporter.LIBRI_TABLE_NAME + "(ISBN) ON DELETE CASCADE, "
                        + " FOREIGN KEY(USERID) REFERENCES " + supporter.READER_TABLE_NAME + "(USERID) ON DELETE CASCADE,"
                        + " PRIMARY KEY(ISBN,USERID)" + ");";

                stmt.executeQuery(sql);
                stmt.close();

                conn.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

                logger.Write("*Errore SQL: Statment Creazione Tabella *" + table_name + "* ");
            }
        } else {
            logger.Write("*SQL: Tabella *" + table_name + "* già presente \n Skipping...");
        }

    }

    synchronized private void creatingTablePrestitiStorico(String table_name) {

        if (!check_Existing_Table(table_name)) {
            Statement stmt = null;

            try {

                stmt = conn.createStatement();

                String sql = "CREATE TABLE " + table_name
                        + "(" + supporter.STORICO_SERIAL + " SERIAL,"
                        +
                        " " + supporter.DATA_R + " TIMESTAMP NOT NULL, "
                        +
                        " " + supporter.ISBN + " SERIAL NOT NULL, "
                        + " " + supporter.USERID + " SERIAL NOT NULL,"
                        + "PRIMARY KEY (" + supporter.STORICO_SERIAL + "));";

                stmt.executeQuery(sql);
                stmt.close();

                conn.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

                logger.Write("*Errore SQL: Statment Creazione Tabella *" + table_name + "* ");
            }
        } else {
            logger.Write("*SQL: Tabella *" + table_name + "* già presente \n Skipping...");
        }

    }


    public void run()
    {
        try {

            conn = supporter.enstablishConnection();

            creatingTableUser(SQLSupporter.LIBRARIAN_TABLE_NAME);
            creatingTableUser(SQLSupporter.READER_TABLE_NAME);
            creatingTableLibri(SQLSupporter.LIBRI_TABLE_NAME);
            creatingTablePrenot(SQLSupporter.PRENOTAZIONI_TABLE_NAME);
            creatingTablePrestiti(SQLSupporter.PRESTITI_TABLE_NAME);
            creatingTablePrestitiStorico(SQLSupporter.STORICO_TABLE_NAME);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.Write("OmniaCreation completed.");

    }
}
