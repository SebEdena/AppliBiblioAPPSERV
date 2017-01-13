package documents.etats;

import dataAppli.Abonne;
import dataAppli.Bibliothèque;
import dataAppli.Document;
import dataAppli.PasLibreException;
import documents.EtatDocument;
import fileUtil.Mailer;

public class DocumentEmprunté implements EtatDocument {
	
	private Abonne abonne;
	
	public DocumentEmprunté(Abonne ab){
		this.abonne = ab;
	}
	
	@Override
	public EtatDocument emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre déjà emprunté");
		throw new IllegalStateException("Vous avez déjà emprunté ce livre.");
	}

	@Override
	public EtatDocument reservation(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre déjà emprunté");
		throw new IllegalStateException("Vous avez déjà emprunté ce livre.");
	}

	@Override
	public EtatDocument retour(Document d) {
		Mailer.getInstance().loadingMail(d);
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "emprunté par : " + abonne.toString();
	}
}
