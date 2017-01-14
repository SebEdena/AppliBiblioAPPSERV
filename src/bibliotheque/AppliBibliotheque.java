package bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import fileUtil.ControleSaisie;

public class AppliBibliotheque {
	
	private static final String ADR_IP_BIBLIO = "localhost";
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOUR = 2700;

	public static void main(String[] args) throws IOException {
		Scanner clavier = new Scanner(System.in);
		
		System.out.println("Bienvenue sur l'application de la bibliothèque. "
				+ "Voulez-vous emprunter ou rendre un document ?");
		System.out.println("\"emprunt\" ou \"retour\"");
		String choix = clavier.next();
		
		switch(choix){
		case "emprunt" : emprunt(clavier);
		break;
		case "retour" : retour(clavier);
		break;
		}
	}
	
	private static void emprunt(Scanner clavier){
		int numAbonné, numDocument;
		Socket laSocket = null;
		try{
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_EMPRUNT);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);
			/* bonjour */
			System.out.println("Bienvenue sur votre système d'emprunt : ");
			System.out.println("Vous pouvez ici emprunter un document disponible ");
			System.out.println("qui n'est pas déjà réservé par quelqu'un d'autre");

			/* saisie des données */;
			System.out.println("Votre numéro d'abonné, svp :");
			while((numAbonné = ControleSaisie.isInteger(clavier.next()))<=0);
			System.out.println("Le numéro de document que vous souhaitez emprunter :");
			while((numDocument = ControleSaisie.isInteger(clavier.next()))<=0);

			/* envoi des données au service */
			socketOut.println(numAbonné);
			socketOut.println(numDocument);

			/* réception de la réponse
			 * et affichage de cette réponse */
			System.out.println(socketIn.readLine());
			
		} catch (IOException e) {System.out.println("Emprunt annulé : vous avez été déconnecté "
											+ "pour inactivité de 3 minutes");}
		try{
			if(laSocket != null )
				// fermeture de la connexion
				laSocket.close();
		}catch(IOException e){}
	}
	
	private static void retour(Scanner clavier){
		int numDocument;
		Socket laSocket = null;
		try{
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_RETOUR);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);

			/* bonjour */
			System.out.println("Bienvenue sur votre système de retour de document.");
			
			/* saisie des données */;
			System.out.println("Le numéro de document que vous souhaitez retourner :");
			while((numDocument = ControleSaisie.isInteger(clavier.next()))<=0);

			/* envoi des données au service */
			socketOut.println(numDocument);
			
			/* réception de la réponse
			 * et affichage de cette réponse */
			System.out.println(socketIn.readLine());

		} catch (IOException e) {System.out.println("Retour annulé : vous avez été déconnecté "
										+ "pour inactivité de 3 minutes");}
		try{
			if(laSocket != null )
				// fermeture de la connexion
				laSocket.close();
		}catch(IOException e){}
	}
}
