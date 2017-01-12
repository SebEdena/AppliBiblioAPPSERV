package documents.etats;

import java.util.Timer;

import bibliotheque.Abonne;
import documents.AnnuleR�servation;
import documents.EtatLivre;
import documents.Livre;
import documents.PasLibreException;

public class LivreR�serv� implements EtatLivre {
	
	private static final long TIMER_RES = 7200000, TIMER_RES2 = 30000;
	
	private Abonne abonn�;
	private Timer t;
	private Livre livre;
	
	public LivreR�serv�(Livre l, Abonne ab) {
		abonn� = ab;
		t = new Timer();
		this.livre = l;
		t.schedule(new AnnuleR�servation(l, t), TIMER_RES2);
	}
	
	@Override
	public EtatLivre emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab){
			t.cancel();
			return new LivreEmprunt�(ab);
		}
		throw new PasLibreException("Livre d�j� r�serv�, "
						+ "indisponible � l'emprunt");
	}

	@Override
	public EtatLivre reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonn� == ab)
			throw new IllegalStateException("L'abonn� a d�j� r�serv� ce livre");
		throw new PasLibreException("Livre d�j� r�serv�");
	}

	@Override
	public EtatLivre retour() throws IllegalStateException {
		throw new IllegalStateException("Impossible de retourner ce livre");
	}

	@Override
	public String getStatus() {
		return "r�serv� par : " + abonn�.toString();
	}

	@Override
	public EtatLivre annuleReservation() throws IllegalStateException {
		return new LivreLibre();
	}

}
