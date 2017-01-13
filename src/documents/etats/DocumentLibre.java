package documents.etats;

import dataAppli.Abonne;
import dataAppli.Document;
import documents.EtatDocument;

public class DocumentLibre implements EtatDocument{

	@Override
	public EtatDocument emprunt(Document d, Abonne ab) {
		return new DocumentEmprunt�(ab);
	}

	@Override
	public EtatDocument reservation(Document d, Abonne ab) {
		return new DocumentR�serv�(d, ab);
	}

	@Override
	public EtatDocument retour(Document d) throws IllegalStateException {
		throw new IllegalStateException("Document libre.");
	}

	@Override
	public String getStatus() {
		return "libre"; 
	}
}
