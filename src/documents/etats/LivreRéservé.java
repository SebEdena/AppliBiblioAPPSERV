package documents.etats;

import java.util.Timer;

import bibliotheque.Abonne;
import documents.AnnuleRéservation;
import documents.EtatLivre;
import documents.Livre;
import documents.PasLibreException;

public class LivreRéservé implements EtatLivre {
	
	private static final long TIMER_RES = 7200000, TIMER_RES2 = 30000;
	
	private Abonne abonné;
	private Timer t;
	private Livre livre;
	
	public LivreRéservé(Livre l, Abonne ab) {
		abonné = ab;
		t = new Timer();
		this.livre = l;
		t.schedule(new AnnuleRéservation(l, t), TIMER_RES2);
	}
	
	@Override
	public EtatLivre emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab){
			t.cancel();
			return new LivreEmprunté(ab);
		}
		throw new PasLibreException("Livre déjà réservé, "
						+ "indisponible à l'emprunt");
	}

	@Override
	public EtatLivre reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonné == ab)
			throw new IllegalStateException("L'abonné a déjà réservé ce livre");
		throw new PasLibreException("Livre déjà réservé");
	}

	@Override
	public EtatLivre retour() throws IllegalStateException {
		throw new IllegalStateException("Impossible de retourner ce livre");
	}

	@Override
	public String getStatus() {
		return "réservé par : " + abonné.toString();
	}

	@Override
	public EtatLivre annuleReservation() throws IllegalStateException {
		return new LivreLibre();
	}

}
