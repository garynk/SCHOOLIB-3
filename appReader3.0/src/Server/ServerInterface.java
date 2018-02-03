/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.Utente;

import javax.swing.table.DefaultTableModel;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public interface ServerInterface extends Remote {
    
     void getClientComunication(String communication) throws RemoteException;

     int getMaxPrenotazioni() throws RemoteException;

     char[] generateUserCode(String user_id) throws RemoteException;

     char[] generatePassword() throws RemoteException;

     void sendConfirmationCode(String user_id, int type) throws RemoteException;

     void sendNewPasswordEmail(String user_id, int type) throws RemoteException;

     void sendNewInformation(String user_id, String message, int type) throws RemoteException;

     boolean checkExistingEasy(String column, String to_compare, int type) throws RemoteException;

     boolean checkExistingEasyPrenPres(String isbn, String userid, int type) throws RemoteException;

     Utente getUtenteFromDBByID(String id, int type) throws RemoteException;

     String getParametricInformation(String field_to_take, int type, String user_id) throws RemoteException;

     boolean getPrestitoSconfinantebyID(String userid) throws RemoteException;

     int loginConfirmation(String user_id, char[] pass, int type) throws RemoteException;

     boolean checkPasswordByID(String user_id, char[] pass, int type) throws RemoteException;

     boolean insertUser(Utente u) throws RemoteException;

     boolean insertPrenotazione(String isbn, String userid) throws RemoteException;

     boolean updateUserPassword(String user_id, char[] new_psw, int type) throws RemoteException;

     boolean updateAttemptLogin(String user_id, int type) throws RemoteException;

     boolean updateUserInformation(String field, String user_id, String info, int type) throws RemoteException;

     boolean deleteUserAccount(String user_id, String field, int type) throws RemoteException;

     boolean deletePrestitoPrenotazioneByISBNByID(int isbn, int userid, int type) throws RemoteException;

     DefaultTableModel builderBooksjTable() throws RemoteException;

     DefaultTableModel getLookedForBooks(String search) throws RemoteException;

     DefaultTableModel getPrenotazioniPrestitiByUserID(String user_id, int request_type) throws RemoteException;

     int countPrenotazioniPrestitiByID(String userid, int type) throws RemoteException;

}
