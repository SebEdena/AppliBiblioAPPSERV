package documents.etats;

import java.nio.channels.IllegalSelectorException;

import bibliotheque.Abonne;
import documents.EtatLivre;
import documents.Livre;
import documents.PasLibreException;

public class LivreEmprunté implements EtatLivre {
	
	private Abonne abonne;
	
	public LivreEmprunté(Abonne ab){
		this.abonne = ab;
	}
	
	@Override
	public EtatLivre emprunt(Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre déjà emprunté");
		throw new IllegalStateException("Vous avez déjà emprunté ce livre.");
	}

	@Override
	public EtatLivre reservation(Livre l, Abonne ab) throws PasLibreException, IllegalStateException {
		if(abonne != ab)
			throw new PasLibreException("Livre déjà emprunté");
		throw new IllegalStateException("Vous avez déjà emprunté ce livre.");
	}

	@Override
	public EtatLivre retour() {
		return new LivreLibre();
	}

	@Override
	public String getStatus() {
		return "emprunté par : " + abonne.toString();
	}

	@Override
	public EtatLivre annuleReservation() throws IllegalStateException {
		throw new IllegalStateException("Livre emprunté, pas réservé");
	}
}
