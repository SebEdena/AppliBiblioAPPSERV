package documents.etats;

import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.PasLibreException;
import documents.AnnuleRéservation;
import documents.EtatDocument;
import documents.Livre;

public class DocumentRéservé implements EtatDocument {
	
	private static final long TIMER_RES = 7200000, TIMER_RES2 = 30000;
	
	private Abonne abonné;
	private Timer t;
	
	public DocumentRéservé(Livre l, Abonne ab) {
		abonné = ab;
		t = new Timer();
		t.schedule(new AnnuleRéservation(l, t), TIMER_RES2);
	}
	
	@Override
	public EtatDocument emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab){
			t.cancel();
			return new DocumentEmprunté(ab);
		}
		throw new PasLibreException("Livre déjà réservé, "
						+ "indisponible à l'emprunt");
	}

	@Override
	public EtatDocument reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab)
			throw new IllegalStateException("L'abonné a déjà réservé ce livre");
		throw new PasLibreException("Livre déjà réservé");
	}

	@Override
	public EtatDocument retour() throws IllegalStateException {
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "réservé par : " + abonné.toString();
	}
}
