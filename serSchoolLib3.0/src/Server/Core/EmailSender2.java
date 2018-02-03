package Server.Core;

import Server.Graphic.ServerView;

//import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSender2 {

    private static final String CODE_SUBJECT = "[SchoolLib] Attivazione Account ";
    private static final String CODE_MESSAGE = "Il tuo codice di verifica è: ";

    private static final String NEWPSW_SUBJECT = "[SchoolLib] Password Cambiata";
    private static final String NEWPSW_MESSAGE = "\n La tua nuova password è: ";

    private static final String NEWINFO_SUBJECT = "[SchoolLib] Informazioni Cambiate";
    private static final String INFO_UPDATE_MESSAGE = "Informazione Cambiate :\n";

    private static final String PRENOTAZIONEDELETE_SUBJECT = "[SchoolLib] Prenotazione Cancellata";
    private static final String PRENOTAZIONEDELETE_MESSAGE1 = "Prenotazione per : ";
    private static final String PRENOTAZIONEDELETE_MESSAGE2 = " è stata cancellata.";

    private static final String LIBRODISPONIBILE_SUBJECT = "[SchoolLib] Libro Prenotato ora Disponibile";
    private static final String LIBRODISPONIBILE_MESSAGE1 = "Il Libro: ";
    private static final String LIBRODISPONIBILE_MESSAGE2 = " è ora disponibile.";

    private static String username;
    private static String password;
    
    private static ServerView logger;

    public EmailSender2() {
    }

    /**
     * Invia una mail di conferma con il codice
     *
     * @param to_email email a cui inviare il codice
     * @param code il codice da inviare
     * */
    public static void sendConfirmationEmail(String to_email, String code) {
        try {

            send_uninsubria_email(to_email, CODE_SUBJECT, CODE_MESSAGE + code);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("* Errore: ConfirmationEmail");
        }
    }

    /**
     * Invia una nuova password e un nuovo codice a un utente
     *
     * @param email_to email a cui inviare il codice
     * @param real_code il codice da inviare
     * @param real_psw la nuova password
     * */
    public static void sendNewPasswordEmail(String email_to, String real_code, String real_psw) {
        try {

            send_uninsubria_email(email_to, NEWPSW_SUBJECT, CODE_MESSAGE + real_code + NEWPSW_MESSAGE + real_psw);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("* Errore: NewPasswordEmail");
        }
    }

    /**
     * Invia una mail con le informazioni aggiornate
     *
     * @param email_to email a cui inviare la notifica
     * @param info l'informazione aggiornata
     * */
    public static void sendInformationUpdate(String email_to, String info) {
        try {

            send_uninsubria_email(email_to, NEWINFO_SUBJECT, INFO_UPDATE_MESSAGE + info);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("* Errore: SendInformationEmail");
        }
    }

    /**
     * Invia una mail con la conferma di avvenuta cancellazione di una prenotazione
     *
     * @param email_to email a cui inviare la notifica
     * @param book_code ISBN del libro associato alla prenotaz cancellata
     * */
    public static void sendDeletePrenotazioneEmail(String email_to, String book_code) {
        try {

            send_uninsubria_email(email_to, PRENOTAZIONEDELETE_SUBJECT, PRENOTAZIONEDELETE_MESSAGE1 + book_code + PRENOTAZIONEDELETE_MESSAGE2);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("* Errore: DeletePrenotazioneEmail");
        }
    }

    /**
     * Notifica per email la nuova disponibilità del libro
     *
     * @param email_to email a cui inviare la notifica
     * @param book_code ISBN del libro associata alla disponibilità
     * @param titolo il titolo del libro associato alla disponibilitò
     */
    public static void sendLibroDisponibileEmail(String email_to, String book_code, String titolo) {
        try {

            send_uninsubria_email(email_to, LIBRODISPONIBILE_SUBJECT, LIBRODISPONIBILE_MESSAGE1 + "*" + book_code + "* - " + titolo + LIBRODISPONIBILE_MESSAGE2);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.write("* Errore: LibroDisponibileEmail");
        }
    }

    /**
     * Invia la mail
     *
     * @param to email a cui inviare la mail
     * @param subject Soggetto della mail
     * @param body corpo della mail
     */
    public static void send_uninsubria_email(String to, String subject, String body) throws MessagingException {

        new Thread(() -> {
            String host = "smtp.office365.com";

            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", 587);

            Session session = Session.getInstance(props);
            try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(body);

                Transport.send(msg, username, password);
            } catch (MessagingException e) {
                e.printStackTrace();
                logger.write("*Error Mail: Email not Sent -> " + e.getMessage());
            }


            logger.write("Email Sent Successful");

        }).start();


    }

//**TEST ONLY
    public boolean setEmailMasterUser(String email)
    {
        username = email;

        return true;
    }

    public void setPasswordMasterUser(String psw){password = psw;}
    public void setlogger(ServerView log){logger = log;}
    
}
