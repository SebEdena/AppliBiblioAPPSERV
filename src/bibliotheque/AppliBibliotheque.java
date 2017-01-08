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
		int numAbonn�, numDocument;
		Scanner clavier = new Scanner(System.in);
		Socket laSocket = new Socket(ADR_IP_BIBLIO, PORT_EMPRUNT);
		BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
		PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);
		
		/* bonjour */
		System.out.println("Bienvenue sur votre syst�me de r�servation : ");
		System.out.println("Vous pouvez ici r�server un livre disponible ");
		System.out.println("et passer le chercher dans les 2 heures");
		
		/* saisie des donn�es */;
		System.out.println("Votre num�ro d'abonn�, svp :");
		while((numAbonn� = isInteger(clavier.next()))<=0);
		System.out.println("Le num�ro de livre que vous souhaitez r�server :");
		while((numDocument = isInteger(clavier.next()))<=0);
		
		/* envoi des donn�es au service */
		socketOut.println(numAbonn�);
		socketOut.println(numDocument);
		
		/* r�ception de la r�ponse
		* et affichage de cette r�ponse */
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
