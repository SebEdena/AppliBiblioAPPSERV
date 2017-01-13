package serveur;

import java.io.IOException;

public class ApplicationServeur {
	
	private final static int PORT_RESERVATION = 2500;
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOUR = 2700;
	
	public static void main(String[] args) {
		try {
			new ServeurReservation(PORT_RESERVATION).lancer();
			System.out.println("Serveur de réservation sur le port " + PORT_RESERVATION);
			new ServeurEmprunt(PORT_EMPRUNT).lancer();
			System.out.println("Serveur d'emprunt sur le port " + PORT_EMPRUNT);
			new ServeurRetour(PORT_RETOUR).lancer();
			System.out.println("Serveur de retour sur le port " + PORT_RETOUR);
			
		} catch (IOException e) {
				System.err.println("Pb lors de la création d'un des trois serveurs : " +  e);			
		}
	}
}
