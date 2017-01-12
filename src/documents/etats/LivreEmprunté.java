package documents.etats;

import java.nio.channels.IllegalSelectorException;

import bibliotheque.Abonne;
import documents.EtatLivre;
import documents.Livre;
import documents.PasLibreException;

public class LivreEmprunt� implements EtatLivre {
	
	private Abonne abonne;
	
	public LivreEmprunt�(Abonne ab){
		this.abonne = ab;
	}
	
	@Override
	public EtatLivre emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre d�j� emprunt�");
		throw new IllegalStateException("Vous avez d�j� emprunt� ce livre.");
	}

	@Override
	public EtatLivre reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre d�j� emprunt�");
		throw new IllegalStateException("Vous avez d�j� emprunt� ce livre.");
	}

	@Override
	public EtatLivre retour() {
		return new LivreLibre();
	}

	@Override
	public String getStatus() {
		return "emprunt� par : " + abonne.toString();
	}

	@Override
	public EtatLivre annuleReservation() throws IllegalStateException {
		throw new IllegalStateException("Livre emprunt�, pas r�serv�");
	}
}
