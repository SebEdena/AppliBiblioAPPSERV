package fileUtil;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dataAppli.Abonne;
import dataAppli.Bibliothèque;
import dataAppli.Document;

public class Mailer {
	private Properties props = new Properties();
	private static Mailer instance;
	
	private static String adresseGmailFrom = "bibliotheque465@gmail.com";
	private static String mdpGmailFrom = "connexion";
	
	private Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(adresseGmailFrom, mdpGmailFrom);
		}
	});
	
	private Mailer(){
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}
	
	public static Mailer getInstance(){
		if(instance == null)
			instance = new Mailer();
		return instance;
	}
	
	public void loadingMail(Document d){
		ArrayList<Abonne> destinataires = Bibliothèque.getInstance().getIntéressés(d);
		for (Abonne ab : destinataires)
			sendingMail(ab, d);
		Bibliothèque.getInstance().clearIntéressés(d);
	}
	
	private void sendingMail(Abonne ab, Document d){
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adresseGmailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ab.getMail()));
			message.setSubject("Il est enfin disponible !");
			message.setText("Le document "+ d.toString() + " , est disponible à l'emprunt.");

			Transport.send(message);

			System.out.println("Mail envoyé à " + ab.getNom() + " à propos du document "
					+ d.numero() + " pour l'informer de sa disponibilité.");

		} catch (MessagingException e) {
			throw new RuntimeException("Erreur lors de l'envoi du mail : " + e);
		}
	}
	
	
}
