package bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private static final String ADR_IP_BIBLIO = "localhost";
	private static final int PORT_RESERVATION = 0;

	void main(String[] args) throws IOException {
		Socket laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
		BufferedReader socketIn = null;
		PrintWriter socketOut = null;
		/* bonjour */
		System.out.println("Bienvenue sur votre système de réservation : ");
		System.out.println("Vous pouvez ici réserver un livre disponible ");
		System.out.println("et passer le chercher dans les 2 heures");
		/* saisie des données */
		Scanner clavier = new Scanner(System.in);
		System.out.println("Votre numéro d'abonné, svp :");
		int numAbonné = clavier.nextInt();
		System.out.println("Le numéro de livre que vous souhaitez réserver :");
		int numDocument = clavier.nextInt();
		/* envoi des données au service */
		socketOut.println(numAbonné);
		socketOut.println(numDocument);
		/* réception de la réponse
		* et affichage de cette réponse */
		System.out.println(socketIn.readLine());
		// fermeture de la connexion
		laSocket.close();
	}
}	

