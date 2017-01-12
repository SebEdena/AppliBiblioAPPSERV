package documents.etats;

import bibliotheque.Abonne;
import documents.EtatLivre;
import documents.Livre;

public class LivreLibre implements EtatLivre{

	@Override
	public EtatLivre emprunt(Abonne ab) {
		return new LivreEmprunt�(ab);
	}

	@Override
	public EtatLivre reservation(Livre l, Abonne ab) {
		return new LivreR�serv�(l, ab);
	}

	@Override
	public EtatLivre retour() throws IllegalStateException {
		throw new IllegalStateException("Livre libre.");
	}

	@Override
	public String getStatus() {
		return "libre"; 
	}

	@Override
	public EtatLivre annuleReservation() throws IllegalStateException {
		throw new IllegalStateException("R�servation inexistante : livre libre");
	}
	
}
