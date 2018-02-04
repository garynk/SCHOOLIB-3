/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Graphic;

import Server.Core.serSchoolLib;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

/**
 *
 * @author lorenzogavazzeni1
 */
public class ServerView extends javax.swing.JFrame {

    /**
     * Creates new form ServerLogger
     */

    private serSchoolLib server;
    
    public ServerView() {
      
        initComponents();
        initBackEnd();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Logger = new javax.swing.JTextPane();
        HeaderPanel = new javax.swing.JPanel();
        passwordTextField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        OKButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("serSchoolLib");
        setMinimumSize(new java.awt.Dimension(550, 546));
        setResizable(false);
        setSize(new java.awt.Dimension(550, 546));

        jPanel1.setBackground(new java.awt.Color(113, 144, 255));

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SERSCHOOLLIB - STATUS PANEL");

        Logger.setEditable(false);
        Logger.setBackground(new java.awt.Color(55, 55, 55));
        Logger.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        Logger.setForeground(new java.awt.Color(153, 255, 153));
        Logger.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportView(Logger);
        Logger.getAccessibleContext().setAccessibleName("");

        HeaderPanel.setBackground(new java.awt.Color(137, 152, 255));
        HeaderPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        HeaderPanel.setMinimumSize(new java.awt.Dimension(530, 95));

        passwordTextField.setText("passw");

        jLabel1.setText("eMail: ");

        jLabel2.setText("Password:");

        emailTextField.setText("email");

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)))
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(emailTextField)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                        .addComponent(OKButton)
                        .addGap(70, 70, 70))))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OKButton))
                .addGap(19, 19, 19)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jLabel4.setText("Logger:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initBackEnd()
    {
        Logger.setEnabled(true);

        try {

            server = new serSchoolLib();

        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
            writeException("** ERRORE FATALE ** - IMPOSSIBILE GENERARE IL SERVER");
            return;
        }

        server.setLogger(this);

    }

    /**
     * Ritorna una Stringa con il formato ORA:MINUTO corrente
     *
     * @return String con formato ORA:MINUTO
     * */
    public String getHourMinuteString()
    {
        Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        
        String hour_minute = Integer.toString(hour) + ":" + Integer.toString(minute);
        
        return hour_minute;
                
    }

    /**
     * Scrive nella console del server la String in ingresso writeme
     *
     * @param writeme
     *
     * */
    public void write(String writeme)
    {
        
        StyledDocument styled = Logger.getStyledDocument();
        
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            
        StyleConstants.setForeground(attributeSet, Logger.getForeground());
        StyleConstants.setBackground(attributeSet, Logger.getBackground());
            
        
        try {
            
            styled.insertString(styled.getLength(),"[" + getHourMinuteString() + "] " + writeme + "\n", attributeSet);
            
        } catch (BadLocationException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Scrive nella console del server una eccezione con la String in ingresso writeme
     *
     * @param writeme
     *
     * */
    public void writeException(String writeme)
    {

        StyledDocument styled = Logger.getStyledDocument();

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();

        StyleConstants.setForeground(attributeSet, new java.awt.Color(255,107,0));
        StyleConstants.setBackground(attributeSet, Logger.getBackground());


        try {

            styled.insertString(styled.getLength(),"[" + getHourMinuteString() + "] " +writeme + "\n", attributeSet);

        } catch (BadLocationException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeStatus(String status)
    {
        StyledDocument styled = Logger.getStyledDocument();

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();

        StyleConstants.setForeground(attributeSet, java.awt.Color.getHSBColor(255,0,208));
        StyleConstants.setBackground(attributeSet, Logger.getBackground());


        try {

            styled.insertString(styled.getLength(),"[" + getHourMinuteString() + "] " + status + "\n", attributeSet);

        } catch (BadLocationException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(passwordTextField.getPassword().length > 4 &&
                        !emailTextField.getText().isEmpty() &&
                        emailTextField.getText().contains("@")){

                    String s = new String(passwordTextField.getPassword());

                    write("Loading...");
                    if(!server.setEmailMasterUser(emailTextField.getText()))
                    {
                        writeException("Dominio Email Errato");
                    }
                    else {
                        server.setPasswordMasterUser(s);

                        write(">> Credenziali Corrette <<");
                        write("Accesso in corso ...");

                        for(Component c : HeaderPanel.getComponents())
                        {
                            c.setEnabled(false);
                        }

                        server.start();

                        return;
                    }

                }
                else
                {
                    writeException("Errore, credenziali errate");
                    return;
                }
            }
        }).start();


    }//GEN-LAST:event_OKButtonActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JTextPane Logger;
    private javax.swing.JButton OKButton;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField passwordTextField;
    // End of variables declaration//GEN-END:variables
}
