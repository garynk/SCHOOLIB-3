/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Reader;

import Checker.Checker;
import Common.Utente;
import Server.ServerInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Per i metodi qui non descritti si faccia riferimento ai medesimi metodi nella classe serSchoolLib
 *
 * @author Lorenzo Gavazzeni
 */
public class Reader extends UnicastRemoteObject {

    private static final long serialVersionUID = 1L;
    private static final int Reader_Default_Type_Value = 2;

    private Utente utente;

    private ServerInterface server;
    public Checker checker = new Checker();

    public Reader() throws RemoteException {
        initBackEnd();
        utente = new Utente(Reader_Default_Type_Value);
    }

    private void initBackEnd() {

        try {
            Registry reg;

            reg = LocateRegistry.getRegistry();

            server = (ServerInterface) reg.lookup("SERVERLIB");

        } catch (Exception e) {
            System.out.println("ReaderForm Init exception: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(),"SERVER OFFLINE, RIPROVARE");
            System.exit(0);
        }

    }

    /**
     *
     * @return int
     *
     */
    public int getMaxPrenotazioni() {
        try {

            return server.getMaxPrenotazioni();

        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }


    public Utente GetParamUser() {
        return utente;
    }

    public String getID() {

        if (!utente.GetUserID().equals("null")) {
            return utente.GetUserID();
        } else {
            return "000";
        }

    }

    public int getType() {
        return utente.getUserType();
    }

    public int GetDefaultType() {
        return Reader_Default_Type_Value;
    }

    public char[] generateUserCode(String user_id) {
        try {
            return server.generateUserCode(user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public char[] generatePassword() {
        try {
            return server.generatePassword();
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public void sendConfirmationCode(String user_id, int type) {
        try {
            server.sendConfirmationCode(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendNewPassword(String user_id, int type) {
        try {
            server.sendNewPasswordEmail(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendNewInformation(String user_id, String message, int type) {
        try {
            server.sendNewInformation(user_id, message, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendFirstCommunication(int id)
    {
        try {
            server.getFirstCommunication(id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendLastCommunication(int id)
    {
        try {
            server.getLastCommunication(id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void sendCommunicationServer(String communication)
    {
        try {
            server.getClientComunication(communication);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setReaderType() {
        utente.setType(Reader_Default_Type_Value);
    }

    public boolean checkParametricExisting(String column, String to_compare, int type) {
        try {
            return server.checkExistingEasy(column, to_compare, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean checkExistingEasyPrenPres(String isbn, String userid, int type) {
        try {
            return server.checkExistingEasyPrenPres(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean inserUser() throws RemoteException {
        if (utente == null) {
            System.err.println("UTENTE VUOTO");
            return false;
        } else {
            return server.insertUser(utente);
        }
    }

    public boolean insertPrenotazione(String isbn, String userid) {

        try {
            return server.insertPrenotazione(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public String getParametricInformation(String field, int type, String user_id) {
        try {
            return server.getParametricInformation(field, type, user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "NULL";
    }

    public Utente getUtentebyID(String user_id, int type) {
        try {
            return server.getUtenteFromDBByID(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    
     public boolean getPrestitoSconfinantebyID(String userid)
     {
        try {
            return server.getPrestitoSconfinantebyID(userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        
        return false;
     }

    public int loginConfirmation(String userid, char[] psw, int type) {
        try {
            return server.loginConfirmation(userid, psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }

    public boolean matchingUserIDPassword(String user_id, char[] pass, int type) {
        try {
            return server.checkPasswordByID(user_id, pass, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public DefaultTableModel builderBookTableModel() {
        try {
            return server.builderBooksjTable();
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    
    public DefaultTableModel getPrenotazioniPrestitiByUserID(String userid, int request_type)
    {
        try {
            return server.getPrenotazioniPrestitiByUserID(userid, request_type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        
        return null;
    }

    public DefaultTableModel getLookedBooks(String search) {
        try {
            return server.getLookedForBooks(search);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public boolean UpdateUserInfo(String user_id, String field, String info, int type) {
        try {
            return server.updateUserInformation(user_id, field, info, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean updateDecrementAttempsLogIn(String user_id, int type) {
        try {
            return server.updateAttemptLogin(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean updateUserPassword(String user_id, char[] new_psw, int type) {
        try {
            return server.updateUserPassword(user_id, new_psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean deleteUserAccount(String userid, String field, int type) {

        try {
            return server.deleteUserAccount(userid, field, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }


    public boolean deletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) {
        try {
            return server.deletePrestitoPrenotazioneByISBNByID(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public int countPrenotazioniPrestitiByID(String userid, int type) {
        try {
            return server.countPrenotazioniPrestitiByID(userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }



}
