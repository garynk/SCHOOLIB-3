/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.Libro;
import Common.Utente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public interface ServerInterface extends Remote {
    
     void GetClientComunication(String communication) throws RemoteException;

     int GetMaxPrenotazioni() throws RemoteException;

     char[] GenerateUserCode(String user_id) throws RemoteException;

     char[] GeneratePassword() throws RemoteException;

     void SendConfirmationCode(String user_id, int type) throws RemoteException;

     void SendNewPasswordEmail(String user_id, int type) throws RemoteException;

     void SendNewInformation(String user_id, String message, int type) throws RemoteException;

     void SendDeletedPrenotazioneEmail(String user_id, String book_code, int type) throws RemoteException;

     void SendLibroDisponibileEmail(String user_id, String book_code, int type) throws RemoteException;

     boolean CheckExistingEasy(String column, String to_compare, int type) throws RemoteException;

     boolean Check_Existing_Easy_PrenPres(String isbn, String userid, int type) throws RemoteException;

     Utente GetUtenteFromDB_byID(String id, int type) throws RemoteException;

     String GetParametricInformation(String field_to_take, int type, String user_id) throws RemoteException;

     String GetBookInformationbyISBN(String field, String isbn) throws RemoteException;

     boolean GetPrestitoSconfinantebyID(String userid) throws RemoteException;

     Vector<String> GetUserIDFromPrenPrest_byISBN(String isbn, int type) throws RemoteException;

     int Login_Confirmation(String user_id, char[] pass, int type) throws RemoteException;

     boolean Check_Password_byID(String user_id, char[] pass, int type) throws RemoteException;

     boolean InsertUser(Utente u) throws RemoteException;

     boolean InsertNewBook(Libro lib) throws RemoteException;

     boolean InsertPrenotazione(String isbn, String userid) throws RemoteException;

     boolean InsertPrestito(String isbn, String userid) throws RemoteException;

     boolean InsertPrestitoStorico(String isbn, String userid) throws RemoteException;

     boolean Update_User_Password(String user_id, char[] new_psw, int type) throws RemoteException;

     boolean Update_Attempt_Login(String user_id, int type) throws RemoteException;

     boolean Update_User_Information(String field, String user_id, String info, int type) throws RemoteException;

     boolean UpdateBookStatus(String isbn, int status) throws RemoteException;

     boolean DeleteUserAccount(String user_id, String field, int type) throws RemoteException;

     boolean DeleteBook_byISBN(int isbn) throws RemoteException;

     boolean DeletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) throws RemoteException;

     DefaultTableModel BuilderBooksjTable() throws RemoteException;

     DefaultTableModel GetLookedForBooks(String search) throws RemoteException;

     DefaultTableModel GetPrenotazioniPrestitiByUserID(String user_id, int request_type) throws RemoteException;

     DefaultTableModel GetClassificaLibri(int request_type) throws RemoteException;

     int Count_PrenotazioniPrestitiByID(String userid, int type) throws RemoteException;

}
