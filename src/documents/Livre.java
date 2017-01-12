package documents;

import java.util.Timer;

import bibliotheque.Abonne;
import bibliotheque.Document;
import documents.etats.LivreLibre;

public class Livre implements Document{
	private static int cptLivre = 0;
	
	private static final int TYPE_INDEX=0,
			ID_INDEX=1, TITRE_INDEX=2, AUTEUR_INDEX=3,
			EMP_INDEX = 4, RES_INDEX=5;
	
	private int idLivre;
	
	private String titre;
	
	private String auteur;
	
	private EtatLivre etat;
	
	public Livre(String[] data) {
		
		if(data.length > AUTEUR_INDEX && data[TYPE_INDEX].equals("livre")){
			try {
				++cptLivre;
				idLivre = Integer.valueOf(data[ID_INDEX]);
			}catch(NumberFormatException e){
				System.err.println("id invalide : le premier num�ro "
						+ "libre est affect� � la place");
				idLivre = cptLivre;
			}
			titre = data[TITRE_INDEX];
			auteur = data[TITRE_INDEX];
			etat = new LivreLibre();
		}
	}

	@Override
	public int numero() {
		return idLivre;
	}

	@Override
	public void reserver(Abonne ab) throws PasLibreException {
		if(ab != null){
			etat = etat.reservation(null, ab);
		}
	}

	@Override
	public void emprunter(Abonne ab) throws PasLibreException {
		if(ab != null){
			etat = etat.emprunt(ab);
		}
	}

	@Override
	public void retour() {
		etat = etat.retour();
	}

	void annuleReservation() {
		etat = etat.annuleReservation();
	}
	
	@Override
	public String toString() {
		return idLivre + " : \"" + titre + "\", " + auteur + 
				", " + etat.getStatus();
	}
}
