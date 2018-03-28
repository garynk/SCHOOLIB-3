/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Librarian;

import Checker.Checker;
import Common.Libro;
import Common.Utente;
import Server.ServerInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Per i metodi qui non descritti si faccia riferimento ai medesimi metodi nella classe serSchoolLib
 *
 * @author Lorenzo Gavazzeni
 */
public class Librarian extends UnicastRemoteObject {

    private static final long serialVersionUID = 1L;
    private static final int LIBRARIAN_DEFAULT_TYPE_VALUE = 1;


    private Utente utente;

    private ServerInterface server;
    public Checker checker = new Checker();

    public Librarian() throws RemoteException {
        initBackEnd();
        utente = new Utente(LIBRARIAN_DEFAULT_TYPE_VALUE);
    }


    private void initBackEnd() {

        try {
            Registry reg;

            reg = LocateRegistry.getRegistry();

            server = (ServerInterface) reg.lookup("SERVERLIB");

        } catch (Exception e) {
            System.out.println("LibrarianForm Init exception: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(),"SERVER OFFLINE, RIPROVARE");
            System.exit(0);
        }

    }

    /**
     *
     * @return Utente
     */
    public Utente getUser() {
        return utente;
    }

    public String getID() {

        if (!utente.getUserID().equals("null")) {
            return utente.getUserID();
        } else {
            return "000";
        }

    }

    public int getDefaultType() {
        return LIBRARIAN_DEFAULT_TYPE_VALUE;
    }

    public int getMaxPrenotazioni() {

        try {
            return server.getMaxPrenotazioni();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }


    public char[] generateUserCode(String user_id) {
        try {
            return server.generateUserCode(user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public char[] generatePassword() {
        try {
            return server.generatePassword();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    public char[] generateStrongPassword(){
        try {
            return server.generateStrongPassword();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        return null;
    }
    

    public void sendFirstCommunication(int id)
    {
        try {
            server.getFirstCommunication(id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendLastCommunication(int id)
    {
        try {
            server.getLastCommunication(id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendCommunicationServer(String communication)
    {
        try {
            server.getClientComunication(communication);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public boolean verifySessionUser(String userId){
        try {
           return server.verifySessionUser(userId);
        } catch (RemoteException e) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        return false;
    }

    public boolean expireSessionUser(String userId) {

        try {
            return server.expireSessionUser(userId);
        } catch (RemoteException e) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(new JFrame(), "ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        return false;
    }
    
    public void sendConfirmationCode(String user_id, int type) {
        try {
            server.sendConfirmationCode(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendNewPassword(String user_id, int type) {
        try {
            server.sendNewPasswordEmail(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendNewInformation(String user_id, String message, int type) {
        try {
            server.sendNewInformation(user_id, message, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendDeletedPrenotazioneEmail(String user_id, String book_code, int type) {
        try {
            server.sendDeletedPrenotazioneEmail(user_id, book_code, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendLibroDisponibileEmail(String user_id, String book_code, int type) {
        try {
            server.sendLibroDisponibileEmail(user_id, book_code, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void setUtente(Utente u) {
        utente = u;
    }

    public void setTmpID(String id) {
        utente.setUserID(id);
    }

    public void setLibrarianType() {
        utente.setType(LIBRARIAN_DEFAULT_TYPE_VALUE);
    }

    public boolean checkParametricExisting(String column, String to_compare, int type) {
        try {
            return server.checkExistingEasy(column, to_compare, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean checkExistingEasyPrenPres(String isbn, String userid, int type) {
        try {
            return server.checkExistingEasyPrenPres(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean inserUser(Utente u) throws RemoteException {
        if (u == null) {
            System.err.println("UTENTE VUOTO");
            return false;
        } else {
            return server.insertUser(u);
        }
    }

    public boolean insertNewBook(Libro libro) {

        try {
            return server.insertNewBook(libro);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean insertPrenotazione(String isbn, String userid) {
        try {
            return server.insertPrenotazione(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean insertPrestito(String isbn, String userid) {
        try {
            return server.insertPrestito(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean insertPrestitoStorico(String isbn, String userid) {
        try {
            return server.insertPrestitoStorico(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public String getParametricInformation(String field, int type, String user_id) {
        try {
            return server.getParametricInformation(field, type, user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return "NULL";
    }

    public Utente getUtentebyID(String user_id, int type) {
        try {
            return server.getUtenteFromDBByID(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public Vector<String> getUserIDFromPrenPrestByISBN(String isbn, int type) {
        try {
            return server.getUserIDFromPrenPrestByISBN(isbn, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public boolean getPrestitoSconfinantebyID(String userid) {
        try {
            return server.getPrestitoSconfinantebyID(userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public int loginConfirmation(String userid, char[] psw, int type) {
        try {
            return server.loginConfirmation(userid, psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }

    public boolean matchingUserIDPassword(String user_id, char[] pass, int type) {
        try {
            return server.checkPasswordByID(user_id, pass, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public DefaultTableModel builderBookTableModel() {
        try {
            return server.builderBooksjTable();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public DefaultTableModel getLookedBooks(String search) {
        try {
            return server.getLookedForBooks(search);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public DefaultTableModel getPrenotazioniPrestitiByUserID(String user_id, int request_type) {
        try {
            return server.getPrenotazioniPrestitiByUserID(user_id, request_type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    
    public DefaultTableModel getClassificaLibri(int request_type)
    {
        try {
            return server.getClassificaLibri(request_type);
                    } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        
        return null;
    }

    public String getBookInformationbyISBN(String field, String isbn) {
        try {
            return server.getBookInformationbyISBN(field, isbn);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return "NULL";
    }

    public boolean updateUserInfo(String user_id, String field, String info, int type) {
        try {
            return server.updateUserInformation(user_id, field, info, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean updateDecrementAttempsLogIn(String user_id, int type) {
        try {
            return server.updateAttemptLogin(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean updateUserPassword(String user_id, char[] new_psw, int type) {
        try {
            return server.updateUserPassword(user_id, new_psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean updateBookStatus(String isbn, int status) {
        try {
            return server.updateBookStatus(isbn, status);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean deleteUserAccount(String userid, int type) {

        try {
            return server.deleteUserAccount(userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean deleteBookByISBN(int to_delete_isbn) {

        try {
            return server.deleteBookByISBN(to_delete_isbn);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;

    }

    public boolean deletePrestitoPrenotazioneByISBNByID(int isbn, String userid, int type) {
        try {
            return server.deletePrestitoPrenotazioneByISBNByID(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public int countPrenotazioniPrestitiByID(String userid, int type) {

        try {
            return server.countPrenotazioniPrestitiByID(userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }

    

}
