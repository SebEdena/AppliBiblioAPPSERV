package documents.etats;

import dataAppli.Abonne;
import dataAppli.Biblioth�que;
import dataAppli.Document;
import dataAppli.PasLibreException;
import documents.EtatDocument;
import fileUtil.Mailer;

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
	public EtatDocument reservation(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre d�j� emprunt�");
		throw new IllegalStateException("Vous avez d�j� emprunt� ce livre.");
	}

	@Override
	public EtatDocument retour(Document d) {
		Mailer.getInstance().loadingMail(d);
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "emprunt� par : " + abonne.toString();
	}
}
