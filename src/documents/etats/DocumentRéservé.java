package documents.etats;

import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.PasLibreException;
import documents.AnnuleR�servation;
import documents.EtatDocument;
import documents.Livre;

public class DocumentR�serv� implements EtatDocument {
	
	private static final long TIMER_RES = 7200000, TIMER_RES2 = 30000;
	
	private Abonne abonn�;
	private Timer t;
	
	public DocumentR�serv�(Livre l, Abonne ab) {
		abonn� = ab;
		t = new Timer();
		t.schedule(new AnnuleR�servation(l, t), TIMER_RES2);
	}
	
	@Override
	public EtatDocument emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab){
			t.cancel();
			return new DocumentEmprunt�(ab);
		}
		throw new PasLibreException("Livre d�j� r�serv�, "
						+ "indisponible � l'emprunt");
	}

	@Override
	public EtatDocument reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab)
			throw new IllegalStateException("L'abonn� a d�j� r�serv� ce livre");
		throw new PasLibreException("Livre d�j� r�serv�");
	}

	@Override
	public EtatDocument retour() throws IllegalStateException {
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "r�serv� par : " + abonn�.toString();
	}
}
