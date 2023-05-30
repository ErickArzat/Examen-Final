package patron;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import interfaze.CuentaBancaria;

public class InfRetiroDEcorador extends CuentaDecorador{

    private static String emailFrom = "erickarzat11@gmail.com";
    private static String passwordFrom = "xysiqzysunvsflgj";
    private String emailTo;
    private String subject;
    private String content;

    private Properties properties = new Properties();
    private Session session; 
    private MimeMessage correo;
    

    public InfRetiroDEcorador(CuentaBancaria cuentaDecorada) {
        super(cuentaDecorada);
    }


    @Override
    public void retirar(int saldo) {
        System.out.println("creando email");
        this.cuentaDecorada.retirar(saldo);
        createEmail();
        sendEmail();
    }

    private void createEmail(){
        emailTo = "ericktorres1118@gmail.com";
        subject = "Informacion de su cuenta ahorro";
        content = "";

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", emailFrom);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);

        
        try {
            
            correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress(emailFrom));
            correo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            correo.setSubject(subject);
            correo.setText(content, "ISO-8859-1", "html");
            
        } catch (MessagingException e) {

        }
    }

    public void sendEmail(){
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(correo, correo.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("correo enviado");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregarSaldo(int saldo) {
        this.cuentaDecorada.agregarSaldo(saldo);
    }

    @Override
    public int getSaldo() {
        return cuentaDecorada.getSaldo();
    }
    
}
