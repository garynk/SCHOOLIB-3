/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Core;

import Common.Libro;
import Common.Utente;
import Server.DBComponent.*;
import Server.Graphic.ServerView;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public class SQLCORE extends Thread {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    
    private static ServerView logger;
    private static SQLSupporter Supporter;
    private static SQLCreator Creator;
    private static SQLInserator Inserator;
    private static SQLChecker Checker;
    private static SQLGetter Getter;
    private static SQLUpdater Updater;
    private static SQLDeleter Deleter;
    private static SQLCounter Counter;

    public SQLCORE(ServerView slogg)
    {
        logger = slogg;

        try {
            Class.forName("org.postgresql.Driver");
            DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Supporter = new SQLSupporter(DB_URL, DB_USERNAME, DB_PASSWORD,slogg);

            Creator = new SQLCreator(Supporter, slogg);
            Checker = new SQLChecker(Supporter, slogg);
            Inserator = new SQLInserator(Checker, Supporter, slogg);
            Getter = new SQLGetter(Supporter, slogg);
            Updater = new SQLUpdater(Supporter, slogg);
            Deleter = new SQLDeleter(Supporter, slogg);
            Counter = new SQLCounter(Supporter, slogg);

            logger.Write("SQL Manager - connection enstablished 2/2");


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            logger.Write("*Errore Connessione SQL (SQL context)" + e.getMessage());
        }
    }

    public void omniaTableCreation(){

        Creator.start();
        try {

            Creator.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean insertUser(Utente generico) {

      return Inserator.insertUser(generico);

    }

    public boolean insertBook(Libro lib) {

        return Inserator.insertBook(lib);
    }

    public boolean insertPrenotazione(String isbn, String userid) {

        return Inserator.insertPrenotazione(isbn,userid);
    }

    public boolean insertPrestito(String isbn, String userid) {

        return Inserator.insertPrestito(isbn,userid);
    }

    public boolean insertPrestitoStorico(String isbn, String userid) {

        return Inserator.insertPrestitoStorico(isbn,userid);
    }

    public boolean checkExistingEasy(String column, String to_compare, int type) {

        return Checker.checkExistingEasy(column,to_compare,type);
    }

    public boolean checkExistingEasyPrenPres(String isbn, String userid, int type) {

        return Checker.checkExistingEasyPrenPres(isbn,userid,type);
    }

    public boolean checkPasswordByID(String user_id, char[] pass, int type) {

        return Checker.checkPasswordByID(user_id,pass,type);
    }

    public int checkLoginUser(String user_id, char[] pass, int type) {

        return Checker.checkLoginUser(user_id,pass,type);
    }

    public String getParametricInformationByID(String field_to_take, int type, String user_id) {

        return Getter.getParametricInformationByID(field_to_take,type,user_id);
    }

    public Vector<String> getUserIDFromPrenPrestByISBN(String isbn, int type) {

        return Getter.getUserIDFromPrenPrestByISBN(isbn,type);
    }

    public Utente getUserDataFromID(String id, int type) {

        return Getter.getUserDataFromID(id,type);
    }

    public boolean getPrestitoSconfinantebyID(String userid) {

        return Getter.getPrestitoSconfinantebyID(userid);
    }

    public String getBookInformation(String field, String isbn) {

        return Getter.getBookInformation(field,isbn);
    }

    public DefaultTableModel getBooksInTable() {

        return Getter.getBooksInTable();
    }

    public DefaultTableModel getLookedForBooks(String search) {

       return Getter.getLookedForBooks(search);
    }

    public DefaultTableModel getPrenotazioniPrestitiByUserID(String user_id, int request_type) {
        
        return Getter.getPrenotazioniPrestitiByUserID(user_id,request_type);
    }

    public DefaultTableModel getClassificaLibri(int request_type) {
        
        return Getter.getClassificaLibri(request_type);
    }
    
    public boolean updateUserPassword(String user_id, char[] new_psw, int type){

        try {
            return Updater.updateUserPassword(user_id,new_psw,type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserInformation(String user_id, String field, String info, int type) {
        return Updater.updateUserInformation(user_id,field,info,type);
    }

    public boolean updateAttemptLogin(String user_id, int type) {
        return Updater.updateAttemptLogin(user_id,type);
    }

    public boolean updateRegistrationCode(String user_id, char[] code, int type) throws Exception {

        return Updater.updateRegistrationCode(user_id,code,type);
    }

    public boolean updateBookStatus(String isbn, int status) {
        return Updater.updateBookStatus(isbn,status);
    }

    public boolean deleteUserAccount(String user_id, String field, int type) throws Exception {

        return Deleter.deleteUserAccount(user_id,field,type);
    }

    public boolean deleteBookByISBN(int isbn) throws Exception {

         return Deleter.deleteBookByISBN(isbn);
    }

    public boolean deletePrenotazioneByISBNByID(int isbn, int userid, int type) throws Exception {

         return Deleter.deletePrenotazioneByISBNByID(isbn,userid,type);
    }

    public int countPrenotazioniPrestitiByID(String userid, int type) {

         return Counter.countPrenotazioniPrestitiByID(userid,type);

    }

}
