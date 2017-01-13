package documents;

import dataAppli.Abonne;
import dataAppli.Document;
import dataAppli.PasLibreException;

public interface EtatDocument {
	
	EtatDocument emprunt(Document d, Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatDocument reservation(Document d, Abonne ab) throws PasLibreException, IllegalStateException;
	
	EtatDocument retour(Document d) throws IllegalStateException;
	
	String getStatus();
	
}
