package documents.etats;

import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.Bibliothèque;
import dataAppli.Document;
import dataAppli.Mailer;
import dataAppli.PasLibreException;
import documents.AnnuleRéservation;
import documents.EtatDocument;

public class DocumentRéservé implements EtatDocument {
	
	//Timer utilisé pour la durée de réservation
	private static final long TIMER_RES = 7200000;
	
	private Abonne abonné;
	private Timer t;
	
	public DocumentRéservé(Document d, Abonne ab) {
		abonné = ab;
		t = new Timer();
		t.schedule(new AnnuleRéservation(d, t), TIMER_RES);
	}
	
	@Override
	public EtatDocument emprunt(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab){
			t.cancel();
			return new DocumentEmprunté(ab);
		}
		throw new PasLibreException("Document déjà réservé, "
						+ "indisponible à l'emprunt");
	}

	@Override
	public EtatDocument reservation(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab)
			throw new IllegalStateException("L'abonné a déjà réservé ce document");
		Bibliothèque.getInstance().addWishingList(d,ab);
		throw new PasLibreException("Document déjà réservé");
	}

	@Override
	public EtatDocument retour(Document d) throws IllegalStateException {
		Mailer.getInstance().loadingMail(d);
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "réservé par : " + abonné.toString();
	}
}
