package documents;

import bibliotheque.Abonne;

public interface EtatLivre {
	
	EtatLivre emprunt(Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatLivre reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatLivre retour() throws IllegalStateException;
	
	EtatLivre annuleReservation() throws IllegalStateException;
	
	String getStatus();
	
}
