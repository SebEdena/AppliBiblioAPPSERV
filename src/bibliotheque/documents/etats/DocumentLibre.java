package documents.etats;

import dataAppli.Abonne;
import documents.EtatDocument;

public class DocumentLibre implements EtatDocument{

	@Override
	public EtatDocument emprunt(Abonne ab) {
		return new DocumentEmprunté(ab);
	}

	@Override
	public EtatDocument reservation(Document d, Abonne ab) {
		return new DocumentRéservé(d, ab);
	}

	@Override
	public EtatDocument retour(Document d) throws IllegalStateException {
		throw new IllegalStateException("Livre libre.");
	}

	@Override
	public String getStatus() {
		return "libre"; 
	}
}
