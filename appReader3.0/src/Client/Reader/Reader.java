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
    public int GetMaxPrenotazioni() {
        try {

            return server.GetMaxPrenotazioni();

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

    public String GetID() {

        if (!utente.GetUserID().equals("null")) {
            return utente.GetUserID();
        } else {
            return "000";
        }

    }

    public int GetType() {
        return utente.GetUserType();
    }

    public int GetDefaultType() {
        return Reader_Default_Type_Value;
    }

    public char[] GenerateUserCode(String user_id) {
        try {
            return server.GenerateUserCode(user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public char[] GeneratePassword() {
        try {
            return server.GeneratePassword();
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public void SendConfirmationCode(String user_id, int type) {
        try {
            server.SendConfirmationCode(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void SendNewPassword(String user_id, int type) {
        try {
            server.SendNewPasswordEmail(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void SendNewInformation(String user_id, String message, int type) {
        try {
            server.SendNewInformation(user_id, message, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }
    
    public void SendCommunicationServer(String communication)
    {
        try {
            server.GetClientComunication(communication);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
    }

    public void SetUtente(Utente u) {
        utente = u;
    }

    public void Set_tmp_ID(String id) {
        utente.SetUserID(id);
    }

    public void Set_Reader_type() {
        utente.SetType(Reader_Default_Type_Value);
    }

    public boolean CheckParametricExisting(String column, String to_compare, int type) {
        try {
            return server.CheckExistingEasy(column, to_compare, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean Check_Existing_Easy_PrenPres(String isbn, String userid, int type) {
        try {
            return server.Check_Existing_Easy_PrenPres(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean InserUser() throws RemoteException {
        if (utente == null) {
            System.err.println("UTENTE VUOTO");
            return false;
        } else {
            return server.InsertUser(utente);
        }
    }

    public boolean InsertPrenotazione(String isbn, String userid) {

        try {
            return server.InsertPrenotazione(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public String GetParametricInformation(String field, int type, String user_id) {
        try {
            return server.GetParametricInformation(field, type, user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "NULL";
    }

    public Utente GetUtentebyID(String user_id, int type) {
        try {
            return server.GetUtenteFromDB_byID(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    
     public boolean GetPrestitoSconfinantebyID(String userid)
     {
        try {
            return server.GetPrestitoSconfinantebyID(userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        
        return false;
     }

    public int Login_Confirmation(String userid, char[] psw, int type) {
        try {
            return server.Login_Confirmation(userid, psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }

    public boolean MatchingUserID_Password(String user_id, char[] pass, int type) {
        try {
            return server.Check_Password_byID(user_id, pass, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public DefaultTableModel BuilderBookTableModel() {
        try {
            return server.BuilderBooksjTable();
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }
    
    public DefaultTableModel GetPrenotazioniPrestitiByUserID(String userid, int request_type)
    {
        try {
            return server.GetPrenotazioniPrestitiByUserID(userid, request_type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }
        
        return null;
    }

    public DefaultTableModel GetLookedBooks(String search) {
        try {
            return server.GetLookedForBooks(search);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return null;
    }

    public boolean UpdateUserInfo(String user_id, String field, String info, int type) {
        try {
            return server.Update_User_Information(user_id, field, info, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean UpdateDecrementAttempsLogIn(String user_id, int type) {
        try {
            return server.Update_Attempt_Login(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean UpdateUserPassword(String user_id, char[] new_psw, int type) {
        try {
            return server.Update_User_Password(user_id, new_psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public boolean DeleteUserAccount(String userid, String field, int type) {

        try {
            return server.DeleteUserAccount(userid, field, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }


    public boolean DeletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) {
        try {
            return server.DeletePrestitoPrenotazioneByISBNByID(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return false;
    }

    public int Count_PrenotazioniPrestitiByID(String userid, int type) {
        try {
            return server.Count_PrenotazioniPrestitiByID(userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(),"ERRORE FATALE, SERVER OFFLINE");
            System.exit(0);
        }

        return -1;
    }



}
