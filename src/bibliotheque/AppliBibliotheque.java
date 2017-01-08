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

	public static void main(String[] args) throws IOException {
		int numAbonné, numDocument;
		Scanner clavier = new Scanner(System.in);
		Socket laSocket = new Socket(ADR_IP_BIBLIO, PORT_EMPRUNT);
		BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
		PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);
		
		/* bonjour */
		System.out.println("Bienvenue sur votre système de réservation : ");
		System.out.println("Vous pouvez ici réserver un livre disponible ");
		System.out.println("et passer le chercher dans les 2 heures");
		
		/* saisie des données */;
		System.out.println("Votre numéro d'abonné, svp :");
		while((numAbonné = isInteger(clavier.next()))<=0);
		System.out.println("Le numéro de livre que vous souhaitez réserver :");
		while((numDocument = isInteger(clavier.next()))<=0);
		
		/* envoi des données au service */
		socketOut.println(numAbonné);
		socketOut.println(numDocument);
		
		/* réception de la réponse
		* et affichage de cette réponse */
		System.out.println(socketIn.readLine());
		// fermeture de la connexion
		laSocket.close();
	}
		
	public static int isInteger(String s){
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
