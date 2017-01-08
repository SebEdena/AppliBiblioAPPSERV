package bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AppliBibliotheque {
	
	private static final String ADR_IP_BIBLIO = "localhost";
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOUR = 2700;

	public static void main(String[] args) throws IOException {
		Scanner clavier = new Scanner(System.in);
		
		System.out.println("Bienvenue sur l'application de la bibliothèque. Voulez-vous emprunter ou rendre un livre ?");
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
			System.out.println("Vous pouvez ici emprunter un livre disponible ");
			System.out.println("qui n'est pas déjà réservé par quelqu'un d'autre");

			/* saisie des données */;
			System.out.println("Votre numéro d'abonné, svp :");
			while((numAbonné = isInteger(clavier.next()))<=0);
			System.out.println("Le numéro de livre que vous souhaitez emprunter :");
			while((numDocument = isInteger(clavier.next()))<=0);

			/* envoi des données au service */
			socketOut.println(numAbonné);
			socketOut.println(numDocument);

			/* réception de la réponse
			 * et affichage de cette réponse */
			System.out.println(socketIn.readLine());

		} catch (IOException e) {System.out.println("Connection fermee par le serveur");}
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
			System.out.println("Bienvenue sur votre système de retour de livre");
			
			/* saisie des données */;
			System.out.println("Le numéro de livre que vous souhaitez retourner :");
			while((numDocument = isInteger(clavier.next()))<=0);

			/* envoi des données au service */
			socketOut.println(numDocument);

			/* réception de la réponse
			 * et affichage de cette réponse */
			System.out.println(socketIn.readLine());

		} catch (IOException e) {System.out.println("Connection fermee par le serveur");}
		try{
			if(laSocket != null )
				// fermeture de la connexion
				laSocket.close();
		}catch(IOException e){}
	}

	public static int isInteger(String s){
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
