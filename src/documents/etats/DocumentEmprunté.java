package documents.etats;

import dataAppli.Abonne;
import dataAppli.PasLibreException;
import documents.EtatDocument;
import documents.Livre;

public class DocumentEmprunt� implements EtatDocument {
	
	private Abonne abonne;
	
	public DocumentEmprunt�(Abonne ab){
		this.abonne = ab;
	}
	
	@Override
	public EtatDocument emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre d�j� emprunt�");
		throw new IllegalStateException("Vous avez d�j� emprunt� ce livre.");
	}

	@Override
	public EtatDocument reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre d�j� emprunt�");
		throw new IllegalStateException("Vous avez d�j� emprunt� ce livre.");
	}

	@Override
	public EtatDocument retour() {
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "emprunt� par : " + abonne.toString();
	}
}
