package documents;

import dataAppli.Abonne;
import dataAppli.PasLibreException;

public interface EtatDocument {
	
	EtatDocument emprunt(Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatDocument reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatDocument retour() throws IllegalStateException;
	
	String getStatus();
	
}
