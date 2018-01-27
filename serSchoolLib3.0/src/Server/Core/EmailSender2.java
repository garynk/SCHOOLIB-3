package Server.Core;

import Server.Graphic.ServerView;

import javax.mail.*;
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

    public static void Send_Confirmation_Email(String to_email, String code) {
        try {

            send_uninsubria_email(to_email, CODE_SUBJECT, CODE_MESSAGE + code);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("* Errore: ConfirmationEmail");
        }
    }

    public static void Send_NewPassword_Email(String email_to, String real_code, String real_psw) {
        try {

            send_uninsubria_email(email_to, NEWPSW_SUBJECT, CODE_MESSAGE + real_code + NEWPSW_MESSAGE + real_psw);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("* Errore: NewPasswordEmail");
        }
    }

    public static void Send_Information_Update(String email_to, String info) {
        try {

            send_uninsubria_email(email_to, NEWINFO_SUBJECT, INFO_UPDATE_MESSAGE + info);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("* Errore: SendInformationEmail");
        }
    }

    public static void Send_DeletePrenotazione_Email(String email_to, String book_code) {
        try {

            send_uninsubria_email(email_to, PRENOTAZIONEDELETE_SUBJECT, PRENOTAZIONEDELETE_MESSAGE1 + book_code + PRENOTAZIONEDELETE_MESSAGE2);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("* Errore: DeletePrenotazioneEmail");
        }
    }

    public static void Send_LibroDisponibile_Email(String email_to, String book_code, String titolo) {
        try {

            send_uninsubria_email(email_to, LIBRODISPONIBILE_SUBJECT, LIBRODISPONIBILE_MESSAGE1 + "*" + book_code + "* - " + titolo + LIBRODISPONIBILE_MESSAGE2);

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender2.class.getName()).log(Level.SEVERE, null, ex);
            logger.Write("* Errore: LibroDisponibileEmail");
        }
    }

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

                logger.Write("Email Sent Successful");

                Transport.send(msg, username, password);
            } catch (MessagingException e) {
                e.printStackTrace();
                logger.Write("*Error Mail: Email not Sent -> " + e.getMessage());
            }

        }).start();


    }

    private boolean CheckEmailMasterUser(String email)
    {
        final AtomicBoolean is_Verified = new AtomicBoolean();
        is_Verified.set(false);

        Thread check_faster = new Thread(() -> {

            String domain_to_check = email.substring(email.lastIndexOf("@") + 1);
            InetAddress to_reach;

            try {

                to_reach = InetAddress.getByName(domain_to_check.trim());
                is_Verified.set(to_reach.isReachable(1000));

            } catch (UnknownHostException e) {
                e.printStackTrace();
                logger.WriteException("-- DOMINIO: *" + domain_to_check + "* NON E' RAGGIUNGIBILE --");
            } catch (IOException e) {
                e.printStackTrace();
                logger.WriteException("-- DOMINIO: *" + domain_to_check + "* NON CORRETTO --");
            }

        });


        check_faster.start();

        try {
            check_faster.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return is_Verified.get();
    }

    public boolean SetEmailMasterUser(String email)
    {
        username = email;

        return true;
    }

    public void SetPasswordMasterUser(String psw){password = psw;}
    public void SetLogger(ServerView log){logger = log;}
    
}
