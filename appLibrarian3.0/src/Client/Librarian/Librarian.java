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

    public String GetID() {

        if (!utente.getUserID().equals("null")) {
            return utente.getUserID();
        } else {
            return "000";
        }

    }

    public int GetDefaultType() {
        return LIBRARIAN_DEFAULT_TYPE_VALUE;
    }

    public int GetMaxPrenotazioni() {

        try {
            return server.GetMaxPrenotazioni();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }


    public char[] GenerateUserCode(String user_id) {
        try {
            return server.GenerateUserCode(user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public char[] GeneratePassword() {
        try {
            return server.GeneratePassword();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void SendCommunicationServer(String communication)
    {
        try {
            server.GetClientComunication(communication);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SendConfirmationCode(String user_id, int type) {
        try {
            server.SendConfirmationCode(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendNewPassword(String user_id, int type) {
        try {
            server.SendNewPasswordEmail(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendNewInformation(String user_id, String message, int type) {
        try {
            server.SendNewInformation(user_id, message, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendDeletedPrenotazioneEmail(String user_id, String book_code, int type) {
        try {
            server.SendDeletedPrenotazioneEmail(user_id, book_code, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendLibroDisponibileEmail(String user_id, String book_code, int type) {
        try {
            server.SendLibroDisponibileEmail(user_id, book_code, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SetUtente(Utente u) {
        utente = u;
    }

    public void Set_tmp_ID(String id) {
        utente.SetUserID(id);
    }

    public void Set_Librarian_type() {
        utente.SetType(LIBRARIAN_DEFAULT_TYPE_VALUE);
    }

    public boolean CheckParametricExisting(String column, String to_compare, int type) {
        try {
            return server.CheckExistingEasy(column, to_compare, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean Check_Existing_Easy_PrenPres(String isbn, String userid, int type) {
        try {
            return server.Check_Existing_Easy_PrenPres(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean InserUser(Utente u) throws RemoteException {
        if (u == null) {
            System.err.println("UTENTE VUOTO");
            return false;
        } else {
            return server.InsertUser(u);
        }
    }

    public boolean InsertNewBook(Libro libro) {

        try {
            return server.InsertNewBook(libro);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean InsertPrenotazione(String isbn, String userid) {
        try {
            return server.InsertPrenotazione(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean InsertPrestito(String isbn, String userid) {
        try {
            return server.InsertPrestito(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean InsertPrestitoStorico(String isbn, String userid) {
        try {
            return server.InsertPrestitoStorico(isbn, userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public String GetParametricInformation(String field, int type, String user_id) {
        try {
            return server.GetParametricInformation(field, type, user_id);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "NULL";
    }

    public Utente GetUtentebyID(String user_id, int type) {
        try {
            return server.GetUtenteFromDB_byID(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Vector<String> GetUserIDFromPrenPrest_byISBN(String isbn, int type) {
        try {
            return server.GetUserIDFromPrenPrest_byISBN(isbn, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean GetPrestitoSconfinantebyID(String userid) {
        try {
            return server.GetPrestitoSconfinantebyID(userid);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public int Login_Confirmation(String userid, char[] psw, int type) {
        try {
            return server.Login_Confirmation(userid, psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public boolean MatchingUserID_Password(String user_id, char[] pass, int type) {
        try {
            return server.Check_Password_byID(user_id, pass, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public DefaultTableModel BuilderBookTableModel() {
        try {
            return server.BuilderBooksjTable();
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public DefaultTableModel GetLookedBooks(String search) {
        try {
            return server.GetLookedForBooks(search);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public DefaultTableModel GetPrenotazioniPrestitiByUserID(String user_id, int request_type) {
        try {
            return server.GetPrenotazioniPrestitiByUserID(user_id, request_type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public DefaultTableModel GetClassificaLibri(int request_type)
    {
        try {
            return server.GetClassificaLibri(request_type);
                    } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public String GetBookInformationbyISBN(String field, String isbn) {
        try {
            return server.GetBookInformationbyISBN(field, isbn);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "NULL";
    }

    public boolean UpdateUserInfo(String user_id, String field, String info, int type) {
        try {
            return server.Update_User_Information(user_id, field, info, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean UpdateDecrementAttempsLogIn(String user_id, int type) {
        try {
            return server.Update_Attempt_Login(user_id, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean UpdateUserPassword(String user_id, char[] new_psw, int type) {
        try {
            return server.Update_User_Password(user_id, new_psw, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean UpdateBookStatus(String isbn, int status) {
        try {
            return server.UpdateBookStatus(isbn, status);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean DeleteUserAccount(String userid, String field, int type) {

        try {
            return server.DeleteUserAccount(userid, field, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean DeleteBook_byISBN(int to_delete_isbn) {

        try {
            return server.DeleteBook_byISBN(to_delete_isbn);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public boolean DeletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) {
        try {
            return server.DeletePrestitoPrenotazioneByISBNByID(isbn, userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public int Count_PrenotazioniPrestitiByID(String userid, int type) {

        try {
            return server.Count_PrenotazioniPrestitiByID(userid, type);
        } catch (RemoteException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

}
