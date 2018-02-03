package Server.DBComponent;

import Common.Utente;
import Server.Core.SQLCORE;
import Server.Graphic.ServerView;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lorenzo Gavazzeni on 27/09/2017.
 */
public class SQLGetter {

    private Connection conn;
    private ServerView logger;

    SQLSupporter supporter;

    /**
     * Costruttore della Classe
     *
     * @param support Oggetto SQLSupporter per le operazioni utili
     * @param log Oggetto ServerView per gestire gli output
     * */
    public SQLGetter(SQLSupporter support, ServerView log)
    {
        supporter = support;
        logger = log;
    }

    /**
     * Ritorna un vettore di stringe con le sole colonne da una query in ingresso
     *
     * @param query la Query in ingresso
     *
     * @return Vettore di Stringhe con le sole colonne
     * */
    private Vector<String> getColumnFromQuery(String query) {

        PreparedStatement stmt = null;

        Vector<String> columns_name = new Vector<>();

        try {

            conn = supporter.enstablishConnection();

            stmt = conn.prepareStatement(query);

            ResultSet result_elements = stmt.executeQuery();

            ResultSetMetaData result_set_meta = result_elements.getMetaData();

            int counter = result_set_meta.getColumnCount();

            for (int col = 1; col <= counter; col++) {
                columns_name.add(result_set_meta.getColumnName(col));
            }

            return columns_name;

        } catch (SQLException ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL [Interno]: GetColumn Fail, query > " + query + " < fallisce");
        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
        }

        return columns_name;
    }

    /**
     * Ritorna una generica informazione da un generico utente
     *
     * @param field_to_take il campo dell'informazione
     * @param type il tipo di utente
     * @param user_id l'id dell'utente
     *
     * @return Stringa contente l'informazione
     * */
    synchronized public String getParametricInformationByID(String field_to_take, int type, String user_id) {

        String table_to_use = supporter.defineTablebyType(type);

        PreparedStatement stmt = null;

        try {
            conn = supporter.enstablishConnection();

            String query = "SELECT " + field_to_take.toLowerCase() + " FROM " + table_to_use
                    + " WHERE userid = '" + user_id.toLowerCase() + "';";

            stmt = conn.prepareStatement(query);
            ResultSet result_element = stmt.executeQuery();
            result_element.next();

            conn.close();

            return result_element.getString(field_to_take);

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL: ParametricReturn() -> " + ex.getMessage());
        }

        return "NULL";
    }

    /**
     * Ritorna una lista di Utente partendo da un ISBN e verificando o in prenotazioni o in prestiti secondo un tipo in ingresso
     *
     * @param isbn l'ISBN del libro in oggetto
     * @param type la tabella che si vuole consultare (Prenotazione o Prestito)
     *
     * @return Lista di Stringhe contenenti tutti gli ID utente che hanno prenotato/che hanno in prestito un libro isbn
     * */
    synchronized public Vector<String> getUserIDFromPrenPrestByISBN(String isbn, int type) {

        String table_to_use = supporter.defineTablebyType(type);

        PreparedStatement stmt = null;
        Vector<String> fillin = new Vector<>();

        try {
            conn = supporter.enstablishConnection();

            String query = "SELECT " + supporter.USERID + " FROM " + table_to_use
                    + " WHERE isbn = '" + Integer.parseInt(isbn.trim()) + "' ORDER BY " + supporter.DATA_P + ";";

            System.out.println(query);

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();

            while (result_elements.next()) {
                fillin.add(result_elements.getString(supporter.USERID));
            }

            conn.close();

            return fillin;

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL: GetUserID from Pren/Prestiti -> " + ex.getMessage());
        }

        return fillin;
    }

    /**
     * Ritorna un oggetto Utente generico secondo un tipo specificato con tutte le informazioni a lui relative
     *
     * @param id l'Id dell'utente di cui si vogliono le info
     * @param type la tabella utente che si vuole consultare (Reader o Librarian)
     *
     * @return Oggetto Utente costruito
     * */
    synchronized public Utente getUserDataFromID(String id, int type) {
        Utente to_return = new Utente();

        try {

            to_return.setUserID(getParametricInformationByID(supporter.USERID, type, id));

            to_return.setNome(getParametricInformationByID(supporter.NOME, type, id));

            to_return.setCognome(getParametricInformationByID(supporter.COGNOME, type, id));

            to_return.setInquadramento(getParametricInformationByID(supporter.INQUADRAMENTO, type, id));

            to_return.setEmail(getParametricInformationByID(supporter.EMAIL, type, id));

            to_return.setNumeroTelefono(getParametricInformationByID(supporter.NUMERO, type, id));

            to_return.setPassword(getParametricInformationByID(supporter.PASSWORD, type, id).toCharArray());

            to_return.setConfirmed(Integer.parseInt(getParametricInformationByID(supporter.CONFIRMED, type, id)));

            to_return.setCode(getParametricInformationByID(supporter.CODICE, type, id).toCharArray());

        } catch (Exception er) {
            er.printStackTrace();
            logger.write("*Errore SQL: Impossibile Ricavare utente: *" + id + "* -> " + er.getMessage());
        }

        return to_return;
    }

    /**
     * Ritorna una booleana che indica se l'utente userid ha un prestito sconfinante
     *
     * @param userid ID dell'utente da verificare
     *
     * @return true se è sconfinante false altrimenti
     * */
    synchronized public boolean getPrestitoSconfinantebyID(String userid) {
        PreparedStatement stmt = null;

        try {

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query = "SELECT count(*) as sconfinanti FROM " + supporter.PRESTITI_TABLE_NAME
                    + " WHERE userid = '" + Integer.parseInt(userid.trim()) + "' AND CURRENT_TIMESTAMP > " + supporter.PRESTITI_TABLE_NAME.toLowerCase() + ".data_ricon;";

            System.out.println(query);

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();
            result_elements.next();

            int sconf = result_elements.getInt("sconfinanti");

            conn.close();

            return sconf > 0;

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL: GetPrestitoSconfinante -> " + ex.getMessage());
        }

        return false;
    }

    /**
     * Ritorna una generica informazione associata a un Libro con isbn in ingresso
     *
     * @param field il campo di interesse per quel libro
     * @param isbn l'isbn del libro interessato
     *
     * @return una Stringa con l'informazione
     * */
    synchronized public String getBookInformation(String field, String isbn) {

        try {

            PreparedStatement stmt = null;

            conn = supporter.enstablishConnection();
            conn.setAutoCommit(false);

            String query = "SELECT " + field.toLowerCase() + " FROM " + supporter.LIBRI_TABLE_NAME
                    + " WHERE  isbn  = '" + Integer.parseInt(isbn.trim()) + "';";

            stmt = conn.prepareStatement(query);

            ResultSet result = stmt.executeQuery();
            result.next();

            String return_me = result.getString(field.toLowerCase());

            conn.close();

            return return_me;

        } catch (Exception er) {
            er.printStackTrace();
            logger.write("*Errore SQL: getBookInformation Fallisce per isbn: " + isbn + " -> " + er.getMessage());
        }

        return "";
    }

    /**
     * Ritorna un oggetto DefaultTableModel costruito con tutti i libri nel database e verificando anche le prenotazioni attive
     *
     * @return DefaultTableModel, un modello di JTable con colonne e libri presi dalla tabella Libri
     * */
    public DefaultTableModel getBooksInTable() {

        PreparedStatement stmt = null;
        DefaultTableModel model = new DefaultTableModel();
        Vector<String> colmuns_name = new Vector<>();

        try {
            conn = supporter.enstablishConnection();

            String query = "SELECT " + supporter.DEFAULT_BOOK_PARAM + ", (select count(isbn) from prenotazioni p where lib.isbn = p.isbn) as prenotazioni FROM "
                    + SQLSupporter.LIBRI_TABLE_NAME + " lib ORDER BY ISBN;";

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();

            colmuns_name = getColumnFromQuery(query);

            model.setColumnIdentifiers(colmuns_name);

            while (result_elements.next()) {
                Vector<Object> vec = new Vector<Object>();

                for (int colin = 1; colin <= colmuns_name.size(); colin++) {
                    vec.add(result_elements.getObject(colin));
                }

                model.addRow(vec);
            }

            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL: getBooksInTable Fallisce -> " + ex.getMessage());
        }

        return model;
    }

    /**
     * Ritorna un oggetto DefaultTableModel costruito a partire da una Stringa di ricerca secondo titolo,autore o categoria
     *
     * @param search la Stringa con il parametro da ricercare
     *
     * @return DefaultTableModel, un modello di JTable con i libri costruiti secondo ricerca
     * */
    public DefaultTableModel getLookedForBooks(String search) {

        if (search.isEmpty()) {
            return getBooksInTable(); }

        PreparedStatement stmt = null;
        DefaultTableModel model = new DefaultTableModel();

        try {

            conn = supporter.enstablishConnection();

            String query = "SELECT " + supporter.DEFAULT_BOOK_PARAM+ " FROM "
                    + supporter.LIBRI_TABLE_NAME + " WHERE "
                    +  "LOWER(" + supporter.TITOLO + ") LIKE '%" + search.toLowerCase().trim() + "%'"
                    + " OR LOWER(" + supporter.AUTORE + ") LIKE '%" + search.toLowerCase().trim() + "%'"
                    + " OR LOWER(" + supporter.CATEGORIA + ") LIKE '%" + search.toLowerCase().trim() + "%'"
                    + " ORDER BY isbn;";

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();

            Vector<String> colmuns_name = getColumnFromQuery(query);
            model.setColumnIdentifiers(colmuns_name);

            while (result_elements.next()) {
                Vector<Object> vec = new Vector<Object>();

                for (int colin = 1; colin <= colmuns_name.size(); colin++) {
                    vec.add(result_elements.getObject(colin));
                }

                model.addRow(vec);
            }

            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL: GetLookedBooks Fallisce -> " + ex.getMessage());
        }

        return model;
    }

    /**
     * Ritorna un oggetto DefaultTableModel costruito a partire da un ID Utente e un tipo di richiesta per differenziare
     * le query e i tipi di tabella da analizzare
     *
     * @param user_id l'ID utente da cui costruire la tabella
     * @param request_type intero con il tipo di richiesta
     *
     * @return DefaultTableModel, un modello di JTable costruito a seconda del tipo di richiesta contente le informazioni di
     * Prenotazione/Prestito relative all'utnete user_ide
     */
    public DefaultTableModel getPrenotazioniPrestitiByUserID(String user_id, int request_type) {


        PreparedStatement stmt = null;
        DefaultTableModel model = new DefaultTableModel();
        Vector<String> colmuns_name = new Vector<>();

        String table;
        String query = "NULL";

        if(user_id == null && request_type == 2)
        {
            table = supporter.PRESTITI_TABLE_NAME;

            query = "SELECT pr.isbn as isbn,pr.userid as userid,lib.titolo as titolo,pr.data_erog,pr.data_ricon FROM "
                    + table + " pr, " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE titolo in (select titolo from "+ supporter.LIBRI_TABLE_NAME + " where pr.isbn = lib.isbn)"
                    + " ORDER BY isbn;";
        }
        else if(user_id == null && request_type == 1)
        {
            table = supporter.STORICO_TABLE_NAME;

            query = "SELECT pr.isbn as isbn,pr.userid as userid,lib.titolo as titolo,pr.data_ricon as data FROM "
                    + table + " pr, " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE titolo in (select titolo from "+ supporter.LIBRI_TABLE_NAME + " where pr.isbn = lib.isbn)"
                    + " ORDER BY isbn;";
        }
        else if(user_id == null && request_type == 3)
        {
            table = supporter.PRENOTAZIONI_TABLE_NAME;

            query = "SELECT pr.isbn as isbn,pr.userid as userid,pr.data,lib.titolo as titolo,pr.data as data FROM "
                    + table + " pr, " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE titolo in (select titolo from "+ supporter.LIBRI_TABLE_NAME + " where pr.isbn = lib.isbn)"
                    + " ORDER BY isbn;";
        }
        else if(request_type == 1)
        {
            table = supporter.STORICO_TABLE_NAME;

            query = "SELECT pr.serial,pr.isbn as isbn,lib.titolo as titolo,pr.data_ricon as data FROM "
                    + table + " pr, " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE pr.isbn = lib.isbn AND pr.userid = " + Integer.parseInt(user_id.trim())
                    + " ORDER BY pr.serial,pr.isbn;";
        }
        else if(request_type == 2)
        {
            table = supporter.PRESTITI_TABLE_NAME;

            query = "SELECT pr.isbn as isbn,lib.titolo as titolo,pr.data_erog,pr.data_ricon FROM "
                    + table + " pr, " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE pr.isbn = lib.isbn AND pr.userid = " + Integer.parseInt(user_id.trim())
                    + " ORDER BY isbn;";
        }
        else if(request_type == 3)
        {
            table = SQLSupporter.PRENOTAZIONI_TABLE_NAME;

            query = "SELECT pr.isbn as isbn,lib.titolo as titolo,pr.data as data FROM "
                    + table + " pr, " + SQLSupporter.LIBRI_TABLE_NAME + " lib"
                    + " WHERE pr.isbn = lib.isbn AND pr.userid = " + Integer.parseInt(user_id.trim())
                    + " ORDER BY isbn;";
        }
        else System.err.println("bad request type on: GetPrenotPrest-TableFormer");

        try {

            if(query.equals("NULL")) {logger.write("*Errore SQL: getPrenotazioniPrestitiByUserID query nulla"); return null; }

            conn = supporter.enstablishConnection();

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();

            colmuns_name = getColumnFromQuery(query);

            model.setColumnIdentifiers(colmuns_name);

            while (result_elements.next()) {
                Vector<Object> vec = new Vector<Object>();

                for (int colin = 1; colin <= colmuns_name.size(); colin++) {
                    vec.add(result_elements.getObject(colin));
                }

                model.addRow(vec);
            }

            conn.close();

            return model;

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL:  getPrenotazioniPrestitiByUserID fallisce per userid: " + user_id + " -> " + ex.getMessage());
        }

        return model;
    }


    /**
     * Ritorna un oggetto DefaultTableModel costruito a partire da un tipo di richiesta per differenziare
     * le query, tipi di tabella e i diversi ordinamenti
     *
     * @param request_type intero con il tipo di richiesta
     *
     * @return DefaultTableModel, un modello di JTable costruito a seconda del tipo di richiesta contente le informazioni ordinate e classificate
     */
    public DefaultTableModel getClassificaLibri(int request_type) {

        PreparedStatement stmt = null;
        DefaultTableModel model = new DefaultTableModel();
        Vector<String> colmuns_name = new Vector<>();

        String query = "NULL";

        if(request_type == 1)
        {
            String table1 = supporter.PRESTITI_TABLE_NAME;
            String table2 = supporter.STORICO_TABLE_NAME;

            query = "SELECT lib.isbn,lib.titolo, (SELECT COUNT(isbn) FROM " + table1
                    + " pr WHERE lib.isbn = pr.isbn) + "
                    + "(SELECT count(isbn) FROM " + table2 + " spr WHERE lib.isbn = spr.isbn)"
                    + " as letto FROM " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " ORDER BY letto desc";

            System.out.println(query);
        }
        else if(request_type == 2)
        {
            String table1 = supporter.PRESTITI_TABLE_NAME;
            String table2 = supporter.STORICO_TABLE_NAME;

            query = "SELECT lib.categoria, (SELECT COUNT(isbn) FROM " + table1
                    + " pr WHERE lib.isbn = pr.isbn) + "
                    + "(SELECT count(isbn) FROM " + table2 + " spr WHERE lib.isbn = spr.isbn)"
                    + " as letto FROM " + supporter.LIBRI_TABLE_NAME + " lib"
                    + " GROUP BY lib.categoria, letto"
                    + " ORDER BY letto desc";
        }
        else if(request_type == 3)
        {
            query = "select r.inquadramento,(select count(isbn) from prestiti pr where pr.userid = r.userid) + " +
                    " (select count(isbn) from storico_prestiti spr where spr.userid = r.userid) as letto " +
                    "from reader r, prestiti p, storico_prestiti sp " +
                    "where (r.userid = p.userid OR r.userid = sp.userid) " +
                    "group by r.inquadramento,letto " +
                    "order by letto desc";
        }
        else System.err.println("bad request type on: GetClassifica-TableFormer");

        try {

            if(query.equals("NULL")) {logger.write("*Errore SQL: GeClassificaLibri query nulla"); return null; }

            conn = supporter.enstablishConnection();

            stmt = conn.prepareStatement(query);
            ResultSet result_elements = stmt.executeQuery();

            colmuns_name = getColumnFromQuery(query);

            model.setColumnIdentifiers(colmuns_name);

            while (result_elements.next()) {
                Vector<Object> vec = new Vector<Object>();

                for (int colin = 1; colin <= colmuns_name.size(); colin++) {
                    vec.add(result_elements.getObject(colin));
                }

                model.addRow(vec);
            }

            conn.close();

        } catch (Exception ex) {
            Logger.getLogger(SQLCORE.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("*Errore SQL:  getClassificaLibri fallisce per request_type: " + request_type + " -> " + ex.getMessage());
        }

        return model;
    }
}
