package documents.etats;

import dataAppli.Abonne;
import documents.EtatDocument;
import documents.Livre;

public class DocumentLibre implements EtatDocument{

	@Override
	public EtatDocument emprunt(Abonne ab) {
		return new DocumentEmprunt�(ab);
	}

	@Override
	public EtatDocument reservation(Livre l, Abonne ab) {
		return new DocumentR�serv�(l, ab);
	}

	@Override
	public EtatDocument retour() throws IllegalStateException {
		throw new IllegalStateException("Livre libre.");
	}

	@Override
	public String getStatus() {
		return "libre"; 
	}
}
