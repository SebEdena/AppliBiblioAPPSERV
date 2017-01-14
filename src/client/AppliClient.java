package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import fileUtil.ControleSaisie;

public class AppliClient {
	
	private static final String ADR_IP_BIBLIO = "localhost";
	private static final int PORT_RESERVATION = 2500;

	public static void main(String[] args) {
		int numAbonn�, numDocument;
		Scanner clavier = new Scanner(System.in);
		Socket laSocket = null;
		
		try {
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);

			/* bonjour */
			System.out.println("Bienvenue sur votre syst�me de r�servation : ");
			System.out.println("Vous pouvez ici r�server un document disponible ");
			System.out.println("et passer le chercher dans les 2 heures");
			
			/* saisie des donn�es */;
			System.out.println("Votre num�ro d'abonn�, svp :");
			while((numAbonn� = ControleSaisie.isInteger(clavier.next()))<=0);
			System.out.println("Le num�ro de document que vous souhaitez r�server :");
			while((numDocument = ControleSaisie.isInteger(clavier.next()))<=0);

			/* envoi des donn�es au service */
			socketOut.println(numAbonn�);
			socketOut.println(numDocument);

			/* r�ception de la r�ponse
			 * et affichage de cette r�ponse */
			System.out.println(socketIn.readLine());
			
		} catch (IOException e) {System.out.println("R�servation annul�e : vous avez �t� d�connect� "
				+ "pour inactivit� de 10 minutes");}
		try{
			if(laSocket != null )
				// fermeture de la connexion
				laSocket.close();
		}catch(IOException e){}
	}
}	

