/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Core;


import Common.Libro;
import Common.Utente;
import Server.Graphic.ServerView;
import Server.ServerInterface;

import javax.swing.table.DefaultTableModel;
import java.io.Reader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public class serSchoolLib extends UnicastRemoteObject implements ServerInterface {
    
    private static final String VERSION = "2.3.4";
    
    private static final int MAX_PRENOTAZIONI_PERUSER = 10;
    private static final int MAX_PRESTITI_PERUSER = 5;

    private int Librarian_Counter = 0;
    private int Reader_Counter = 0;

    private static final int CODE_SIZE = 9;
    private static final String[] PASSWORD_WORD_ARRAY = {"ARANCIA", "DRESANO", "BANANA", "MELONE", "BARCHETTA", "SGABELLO", "NATURALE"};

    private static SQLCORE sqlmanager;
    private static serSchoolLib server;
    private EmailSender2 emailsender;
    private static ServerView logger_pane;
    private PasswordToolkit passToolkit;


    public serSchoolLib() throws RemoteException {
    }

    private void init_SERVER() {

        try {
            Registry reg = LocateRegistry.createRegistry(1099);

            server = new serSchoolLib();

            reg.rebind("SERVERLIB", server);

            logger_pane.write("   ### SERSCHOOLLIB v." + VERSION + " STARTED ###");

        } catch (Exception e) {
            System.out.println("SERVER error on start: " + e.getMessage());
        }
    }

    private void initSQLDatabase() {

        try {
            logger_pane.write("SQL Manager - initing connection 1/2");

            sqlmanager = new SQLCORE(logger_pane);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
            logger_pane.writeException("* Errore Connessione SQL (server context) ");

        }

    }



    public void start() {

        init_SERVER();
        initSQLDatabase();


        try {

            sqlmanager.omniaTableCreation();

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }


        CREATE_BOOK_TEST();

    }

    public int getMaxPrenotazioni() {
        return MAX_PRENOTAZIONI_PERUSER;
    }

    public int getMaxPrestiti() {
        return MAX_PRESTITI_PERUSER;
    }

    public void getLastCommunication(int id)
    {
        if(id == 1) {
            Librarian_Counter--;
            logger_pane.writeStatus(">> Stato Connessioni Attuali: ");
            logger_pane.writeStatus("READER: " +  Reader_Counter + " | LIBRARIAN: " + Librarian_Counter);
        }
        else if (id == 2) {
            Reader_Counter--;
            logger_pane.writeStatus(">> Stato Connessioni Attuali: ");
            logger_pane.writeStatus("READER: " +  Reader_Counter + " | LIBRARIAN: " + Librarian_Counter);
        }
        else{
            logger_pane.writeStatus(">>[RICHIESTA STATO NON PREVISTA] ");
        }
    }

    public void getFirstCommunication(int id)
    {
        if(id == 1) {
            Librarian_Counter++;
            logger_pane.writeStatus(">> Stato Connessioni Attuali: ");
            logger_pane.writeStatus("READER: " +  Reader_Counter + " | LIBRARIAN: " + Librarian_Counter);
        }
        else if (id == 2) {
            Reader_Counter++;
            logger_pane.writeStatus(">> Stato Connessioni Attuali: ");
            logger_pane.writeStatus("READER: " +  Reader_Counter + " | LIBRARIAN: " + Librarian_Counter);
        }
        else{
            logger_pane.writeStatus(">>[RICHIESTA STATO NON PREVISTA] ");
        }
    }
    
    public void getClientComunication(String communication)
    {
       logger_pane.write(communication);
    }
    
    public void setLogger(ServerView ServerView)
    {
        emailsender = new EmailSender2();
        logger_pane = ServerView;
        emailsender.setlogger(logger_pane);
    }
    
    public char[] generateUserCode(String user_id) {
        final int first_part_size = 4;
        final int second_part_size = 3;
        final int third_part_size = 2;

        final int max_random_value = 9;
        final int min_random_value = 0;

        int code_index = 0;

        char[] code = new char[CODE_SIZE];

        for (int i = 0; i < first_part_size; i++) {
            code[i] = user_id.charAt(i);
            code_index = i + 1;
        }

        for (int j = code_index; j < first_part_size + second_part_size; j++) {
            Random rand = new Random();

            int random_generated = rand.nextInt(max_random_value - min_random_value + 1) + min_random_value;

            String single_char = Integer.toString(random_generated);

            code[j] = single_char.charAt(0);

            code_index = j + 1;
        }

        Calendar current = Calendar.getInstance();

        int hour_int = current.get(Calendar.HOUR);

        String hour_string = Integer.toString(hour_int);

        for (int k = code_index; k < first_part_size + second_part_size + third_part_size; k++) {
            int posix_hour = 0;

            code[k] = hour_string.charAt(posix_hour);

            posix_hour++;
        }

        System.err.println(code);

        return code;

    }

    public char[] generatePassword() {
        char[] psw_generated;

        int random_psw_posix = ThreadLocalRandom.current().nextInt(0, PASSWORD_WORD_ARRAY.length - 1);
        int last_index = 0;

        psw_generated = new char[PASSWORD_WORD_ARRAY[random_psw_posix].length() + 2];

        for (int i = 0; i < PASSWORD_WORD_ARRAY[random_psw_posix].length(); i++) {
            psw_generated[i] = PASSWORD_WORD_ARRAY[random_psw_posix].charAt(i);
            last_index = i;
        }

        for (int i = last_index + 1; i < psw_generated.length; i++) {
            psw_generated[i] = Integer.toString(ThreadLocalRandom.current().nextInt(0, 9 + 1)).charAt(0);
        }
        String password = new String(psw_generated);
        String finalPassword;
        password = passToolkit.randomLowerCase(password);
        finalPassword = passToolkit.addRandomNumbers(password);
        //Quella sotto è la funzione che permette di effettuare l'hashing della password
       // password = passToolkit.hashPassword(password,"$(d*:>3W#"); bisogna assicurare che l'user non riceva la password hashata, bensì quella finale, che per comodità ho chiamato finalPassword
        psw_generated = password.toCharArray();

        return psw_generated;

    }
    public String randomLowerCase(String password){
    //random letters to lowercase
        String newPassword;
       int nOfIterations = ThreadLocalRandom.current().nextInt(1,password.length());
       StringBuilder sb = new StringBuilder();
       boolean Selected [] = new boolean[password.length()];
       int index;
       char [] passArray = password.toCharArray();
       while(nOfIterations>0){
           index = ThreadLocalRandom.current().nextInt(1,passArray.length);
           if (Character.isUpperCase(passArray[index])){
           passArray[index] = Character.toLowerCase(passArray[index]);
           nOfIterations--;
           }
       }
       for (int i=0;i<passArray.length;i++){
       sb.append(passArray[i]);
       }
       newPassword = sb.toString();
       
     return newPassword;
 
    
   
    }
     public String addRandomNumbers(String password){
        
        int bound = ThreadLocalRandom.current().nextInt(3,7);
        while (bound>0){
    password = password + ThreadLocalRandom.current().nextInt(0,9);
 bound--;
 }
        return password;
        
    }

    public void sendConfirmationCode(String user_id, int type) {
        String code = sqlmanager.getParametricInformationByID("CODICE", type, user_id);
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        String real_code = "";

        for (int i = 0; i < code.length(); i++) {
            if (Character.isDigit(code.charAt(i))) {
                real_code += code.charAt(i);
            } else {
            }
        }

        EmailSender2.sendConfirmationEmail(email_to, real_code);
    }
    
    public void sendNewPasswordEmail(String user_id, int type) {
        String code = sqlmanager.getParametricInformationByID("CODICE", type, user_id);
        String psw = sqlmanager.getParametricInformationByID("PASSWORD", type, user_id);
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        String real_code = "";
        String real_psw = "";

        for (int i = 0; i < code.length(); i++) {
            if (Character.isDigit(code.charAt(i))) {
                real_code += code.charAt(i);
            } else {
            }
        }

        for (int i = 0; i < psw.length(); i++) {
            if (Character.isLetterOrDigit(psw.charAt(i))) {
                real_psw += psw.charAt(i);
            } else {
            }
        }

        EmailSender2.sendNewPasswordEmail(email_to, real_code, real_psw);
    }

    public void sendNewInformation(String user_id, String message, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        EmailSender2.sendInformationUpdate(email_to, message);
    }

    public void sendDeletedPrenotazioneEmail(String user_id, String book_code, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        EmailSender2.sendDeletePrenotazioneEmail(email_to, book_code);
    }

    public void sendLibroDisponibileEmail(String user_id, String book_code, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);
        String titolo = sqlmanager.getBookInformation("TITOLO", book_code);

        EmailSender2.sendLibroDisponibileEmail(email_to, book_code, titolo);
    }
    
    public boolean setEmailMasterUser(String email_from)
    {
       return emailsender.setEmailMasterUser(email_from);
    }
    
    public void setPasswordMasterUser(String psw)
    {
        emailsender.setPasswordMasterUser(psw);
    }

    public boolean checkExistingEasy(String column, String to_compare, int type) {
        return sqlmanager.checkExistingEasy(column, to_compare, type);
    }

    public boolean checkExistingEasyPrenPres(String isbn, String userid, int type) throws RemoteException {

        return sqlmanager.checkExistingEasyPrenPres(isbn, userid, type);
    }

    public Utente getUtenteFromDBByID(String id, int type) throws RemoteException {
        return sqlmanager.getUserDataFromID(id, type);
    }

    public String getParametricInformation(String field_to_take, int type, String element_to_compare) {
        return sqlmanager.getParametricInformationByID(field_to_take, type, element_to_compare);
    }

    public Vector<String> getUserIDFromPrenPrestByISBN(String isbn, int type) {
        return sqlmanager.getUserIDFromPrenPrestByISBN(isbn, type);
    }

    public boolean getPrestitoSconfinantebyID(String userid) {
        return sqlmanager.getPrestitoSconfinantebyID(userid);
    }

    synchronized public int loginConfirmation(String user_id, char[] pass, int type) throws RemoteException {
        return sqlmanager.checkLoginUser(user_id, pass, type);
    }

    public boolean checkPasswordByID(String user_id, char[] pass, int type) {
        return sqlmanager.checkPasswordByID(user_id, pass, type);

    }

    public DefaultTableModel builderBooksjTable() {
        return sqlmanager.getBooksInTable();
    }

    public String getBookInformationbyISBN(String field, String isbn) {
        return sqlmanager.getBookInformation(field, isbn);
    }

    public DefaultTableModel getLookedForBooks(String search) {
        return sqlmanager.getLookedForBooks(search);
    }

    public DefaultTableModel getPrenotazioniPrestitiByUserID(String user_id, int request_type) {
        return sqlmanager.getPrenotazioniPrestitiByUserID(user_id, request_type);
    }
    
    public DefaultTableModel getClassificaLibri(int request_type)
    {
        return sqlmanager.getClassificaLibri(request_type);
    }

    public boolean insertUser(Utente u) {
        return sqlmanager.insertUser(u);
    }

    public boolean insertNewBook(Libro lib) {
        return sqlmanager.insertBook(lib);
    }

    public boolean insertPrenotazione(String isbn, String userid) {
        return sqlmanager.insertPrenotazione(isbn, userid);
    }

    public boolean insertPrestito(String isbn, String userid) {
        return sqlmanager.insertPrestito(isbn, userid);
    }

    public boolean insertPrestitoStorico(String isbn, String userid) {
        return sqlmanager.insertPrestitoStorico(isbn, userid);
    }

    public boolean updateUserPassword(String user_id, char[] new_psw, int type) {

        try {

            return sqlmanager.updateUserPassword(user_id, new_psw, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateAttemptLogin(String user_id, int type) {
        return sqlmanager.updateAttemptLogin(user_id, type);
    }

    public boolean updateUserInformation(String field, String user_id, String info, int type) {
        return sqlmanager.updateUserInformation(field, user_id, info, type);
    }

    public boolean updateUserRegistrationCode(String user_id, char[] code, int type) {
        try {

            return sqlmanager.updateRegistrationCode(user_id, code, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateBookStatus(String isbn, int status) {
        return sqlmanager.updateBookStatus(isbn, status);
    }

    public boolean deleteUserAccount(String user_id, String field, int type) {
        try {

            return sqlmanager.deleteUserAccount(user_id, field, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean deleteBookByISBN(int isbn) {
        try {

            return sqlmanager.deleteBookByISBN(isbn);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean deletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) {
        try {
            return sqlmanager.deletePrenotazioneByISBNByID(isbn, userid, type);
        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public int countPrenotazioniPrestitiByID(String userid, int type) {

        return sqlmanager.countPrenotazioniPrestitiByID(userid, type);
    }


    public void CREATE_BOOK_TEST()
    {
        logger_pane.write("  -- Inizializzo Biblioteca -- ");
        sqlmanager.insertBook(new Libro("110","In Nome della Rosa","Non Saprei","Delphi","1700","Romanzi","Italiano"));
        sqlmanager.insertBook(new Libro("111","Balcone con Vista","Riccardina","Achette","2010","Didattica","Spagnolo"));
        sqlmanager.insertBook(new Libro("112","Biciclette","Dario Fossi","DeAgostini","2004","2010","Romanzi","Italiano"));
        logger_pane.write("  -- Biblioteca Riempieta -- ");
    } 
    
}
