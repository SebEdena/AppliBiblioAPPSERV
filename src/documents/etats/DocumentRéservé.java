package documents.etats;

import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.Biblioth�que;
import dataAppli.Document;
import dataAppli.Mailer;
import dataAppli.PasLibreException;
import documents.AnnuleR�servation;
import documents.EtatDocument;

public class DocumentR�serv� implements EtatDocument {
	
	//Timer utilis� pour la dur�e de r�servation
	private static final long TIMER_RES = 7200000;
	
	private Abonne abonn�;
	private Timer t;
	
	public DocumentR�serv�(Document d, Abonne ab) {
		abonn� = ab;
		t = new Timer();
		t.schedule(new AnnuleR�servation(d, t), TIMER_RES);
	}
	
	@Override
	public EtatDocument emprunt(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab){
			t.cancel();
			return new DocumentEmprunt�(ab);
		}
		throw new PasLibreException("Document d�j� r�serv�, "
						+ "indisponible � l'emprunt");
	}

	@Override
	public EtatDocument reservation(Document d, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab)
			throw new IllegalStateException("L'abonn� a d�j� r�serv� ce document");
		Biblioth�que.getInstance().addWishingList(d,ab);
		throw new PasLibreException("Document d�j� r�serv�");
	}

	@Override
	public EtatDocument retour(Document d) throws IllegalStateException {
		Mailer.getInstance().loadingMail(d);
		return new DocumentLibre();
	}

	@Override
	public String getStatus() {
		return "r�serv� par : " + abonn�.toString();
	}
}
