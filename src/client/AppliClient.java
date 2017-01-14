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
		int numAbonné, numDocument;
		Scanner clavier = new Scanner(System.in);
		Socket laSocket = null;
		
		try {
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut =  new PrintWriter(laSocket.getOutputStream(), true);

			/* bonjour */
			System.out.println("Bienvenue sur votre système de réservation : ");
			System.out.println("Vous pouvez ici réserver un document disponible ");
			System.out.println("et passer le chercher dans les 2 heures");
			
			/* saisie des données */;
			System.out.println("Votre numéro d'abonné, svp :");
			while((numAbonné = ControleSaisie.isInteger(clavier.next()))<=0);
			System.out.println("Le numéro de document que vous souhaitez réserver :");
			while((numDocument = ControleSaisie.isInteger(clavier.next()))<=0);

			/* envoi des données au service */
			socketOut.println(numAbonné);
			socketOut.println(numDocument);

			/* réception de la réponse
			 * et affichage de cette réponse */
			System.out.println(socketIn.readLine());
			
		} catch (IOException e) {System.out.println("Réservation annulée : vous avez été déconnecté "
				+ "pour inactivité de 10 minutes");}
		try{
			if(laSocket != null )
				// fermeture de la connexion
				laSocket.close();
		}catch(IOException e){}
	}
}	

