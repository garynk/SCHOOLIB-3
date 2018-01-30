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

    private static final int CODE_SIZE = 9;
    private static final String[] PASSWORD_WORD_ARRAY = {"ARANCIA", "DRESANO", "BANANA", "MELONE", "BARCHETTA", "SGABELLO", "NATURALE"};

    private static SQLCORE sqlmanager;
    private static serSchoolLib server;
    private EmailSender2 emailsender;
    private static ServerView logger_pane;


    public serSchoolLib() throws RemoteException {
    }

    private void init_SERVER() {

        try {
            Registry reg = LocateRegistry.createRegistry(1099);

            server = new serSchoolLib();

            reg.rebind("SERVERLIB", server);

            logger_pane.Write("   ### SERSCHOOLLIB v." + VERSION + " STARTED ###");

        } catch (Exception e) {
            System.out.println("SERVER error on start: " + e.getMessage());
        }
    }

    private void initSQLDatabase() {

        try {
            logger_pane.Write("SQL Manager - initing connection 1/2");

            sqlmanager = new SQLCORE(logger_pane);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
            logger_pane.Write("* Errore Connessione SQL (server context) ");

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

    public int GetMaxPrenotazioni() {
        return MAX_PRENOTAZIONI_PERUSER;
    }

    public int GetMaxPrestiti() {
        return MAX_PRESTITI_PERUSER;
    }

    
    public void GetClientComunication(String communication) 
    {
       logger_pane.Write(communication);
    }
    
    public void SetLogger(ServerView ServerView)
    {
        emailsender = new EmailSender2();
        logger_pane = ServerView;
        emailsender.SetLogger(logger_pane);
    }
    
    public char[] GenerateUserCode(String user_id) {
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

    public char[] GeneratePassword() {
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

    public void SendConfirmationCode(String user_id, int type) {
        String code = sqlmanager.getParametricInformationByID("CODICE", type, user_id);
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        String real_code = "";

        for (int i = 0; i < code.length(); i++) {
            if (Character.isDigit(code.charAt(i))) {
                real_code += code.charAt(i);
            } else {
            }
        }

        EmailSender2.Send_Confirmation_Email(email_to, real_code);
    }
    
    public void SendNewPasswordEmail(String user_id, int type) {
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

        EmailSender2.Send_NewPassword_Email(email_to, real_code, real_psw);
    }

    public void SendNewInformation(String user_id, String message, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        EmailSender2.Send_Information_Update(email_to, message);
    }

    public void SendDeletedPrenotazioneEmail(String user_id, String book_code, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);

        EmailSender2.Send_DeletePrenotazione_Email(email_to, book_code);
    }

    public void SendLibroDisponibileEmail(String user_id, String book_code, int type) {
        String email_to = sqlmanager.getParametricInformationByID("EMAIL", type, user_id);
        String titolo = sqlmanager.getBookInformation("TITOLO", book_code);

        EmailSender2.Send_LibroDisponibile_Email(email_to, book_code, titolo);
    }
    
    public boolean SetEmailMasterUser(String email_from)
    {
       return emailsender.SetEmailMasterUser(email_from);
    }
    
    public void SetPasswordMasterUser(String psw)
    {
        emailsender.SetPasswordMasterUser(psw);
    }

    public boolean CheckExistingEasy(String column, String to_compare, int type) {
        return sqlmanager.checkExistingEasy(column, to_compare, type);
    }

    public boolean Check_Existing_Easy_PrenPres(String isbn, String userid, int type) throws RemoteException {

        return sqlmanager.checkExistingEasyPrenPres(isbn, userid, type);
    }

    public Utente GetUtenteFromDB_byID(String id, int type) throws RemoteException {
        return sqlmanager.getUserDataFromID(id, type);
    }

    public String GetParametricInformation(String field_to_take, int type, String element_to_compare) {
        return sqlmanager.getParametricInformationByID(field_to_take, type, element_to_compare);
    }

    public Vector<String> GetUserIDFromPrenPrest_byISBN(String isbn, int type) {
        return sqlmanager.getUserIDFromPrenPrestByISBN(isbn, type);
    }

    public boolean GetPrestitoSconfinantebyID(String userid) {
        return sqlmanager.getPrestitoSconfinantebyID(userid);
    }

    synchronized public int Login_Confirmation(String user_id, char[] pass, int type) throws RemoteException {
        return sqlmanager.checkLoginUser(user_id, pass, type);
    }

    public boolean Check_Password_byID(String user_id, char[] pass, int type) {
        return sqlmanager.checkPasswordByID(user_id, pass, type);

    }

    public DefaultTableModel BuilderBooksjTable() {
        return sqlmanager.getBooksInTable();
    }

    public String GetBookInformationbyISBN(String field, String isbn) {
        return sqlmanager.getBookInformation(field, isbn);
    }

    public DefaultTableModel GetLookedForBooks(String search) {
        return sqlmanager.getLookedForBooks(search);
    }

    public DefaultTableModel GetPrenotazioniPrestitiByUserID(String user_id, int request_type) {
        return sqlmanager.getPrenotazioniPrestitiByUserID(user_id, request_type);
    }
    
    public DefaultTableModel GetClassificaLibri(int request_type)
    {
        return sqlmanager.getClassificaLibri(request_type);
    }

    public boolean InsertUser(Utente u) {
        return sqlmanager.insertUser(u);
    }

    public boolean InsertNewBook(Libro lib) {
        return sqlmanager.insertBook(lib);
    }

    public boolean InsertPrenotazione(String isbn, String userid) {
        return sqlmanager.insertPrenotazione(isbn, userid);
    }

    public boolean InsertPrestito(String isbn, String userid) {
        return sqlmanager.insertPrestito(isbn, userid);
    }

    public boolean InsertPrestitoStorico(String isbn, String userid) {
        return sqlmanager.insertPrestitoStorico(isbn, userid);
    }

    public boolean Update_User_Password(String user_id, char[] new_psw, int type) {

        try {

            return sqlmanager.updateUserPassword(user_id, new_psw, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean Update_Attempt_Login(String user_id, int type) {
        return sqlmanager.updateAttemptLogin(user_id, type);
    }

    public boolean Update_User_Information(String field, String user_id, String info, int type) {
        return sqlmanager.updateUserInformation(field, user_id, info, type);
    }

    public boolean Update_User_Registration_Code(String user_id, char[] code, int type) {
        try {

            return sqlmanager.updateRegistrationCode(user_id, code, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean UpdateBookStatus(String isbn, int status) {
        return sqlmanager.updateBookStatus(isbn, status);
    }

    public boolean DeleteUserAccount(String user_id, String field, int type) {
        try {

            return sqlmanager.deleteUserAccount(user_id, field, type);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean DeleteBook_byISBN(int isbn) {
        try {

            return sqlmanager.deleteBookByISBN(isbn);

        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean DeletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) {
        try {
            return sqlmanager.deletePrenotazioneByISBNByID(isbn, userid, type);
        } catch (Exception ex) {
            Logger.getLogger(serSchoolLib.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public int Count_PrenotazioniPrestitiByID(String userid, int type) {

        return sqlmanager.countPrenotazioniPrestitiByID(userid, type);
    }


    public void CREATE_BOOK_TEST()
    {
        logger_pane.Write("  -- Inizializzo Biblioteca -- ");
        sqlmanager.insertBook(new Libro("110","In Nome della Rosa","Non Saprei","Delphi","1700","Romanzi","Italiano"));
        sqlmanager.insertBook(new Libro("111","Balcone con Vista","Riccardina","Achette","2010","Didattica","Spagnolo"));
        sqlmanager.insertBook(new Libro("112","Biciclette","Dario Fossi","DeAgostini","2004","2010","Romanzi","Italiano"));
        logger_pane.Write("  -- Biblioteca Riempieta -- ");
    } 
    
}
