package documents;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.PasLibreException;

public class Livres implements Document{
	private static int cptLivre = 0;
	
	private static final int TYPE_INDEX=0,
			ID_INDEX=1, TITRE_INDEX=2, AUTEUR_INDEX=3,
			EMP_INDEX = 4, RES_INDEX=5;
	
	private int idLivre;
	
	private String titre;
	
	private String auteur;
	
	private Abonne emprunteur;
	
	private Abonne reserveur;
	
	public Livres(String[] data) {
		
		if(data.length > AUTEUR_INDEX && data[TYPE_INDEX].equals("livre")){
			try {
				++cptLivre;
				idLivre = Integer.valueOf(data[ID_INDEX]);
			}catch(NumberFormatException e){
				System.err.println("id invalide : le premier numéro "
						+ "libre est affecté à la place");
				idLivre = cptLivre;
			}
			titre = data[TITRE_INDEX];
			auteur = data[TITRE_INDEX];
		}
	}

	@Override
	public int numero() {
		return idLivre;
	}

	@Override
	public void reserver(Abonne ab) throws PasLibreException {
		if(ab != null && ab != reserveur){
			if(reserveur != null)
				throw new PasLibreException("Livre déjà réservé");
			if(emprunteur != null && emprunteur != ab){
				throw new PasLibreException("Livre déjà emprunté");
			}
			reserveur = ab;
		}
	}

	@Override
	public void emprunter(Abonne ab) throws PasLibreException {
		if(ab != null && ab != emprunteur){
			if(reserveur != null && ab != reserveur)
				throw new PasLibreException("Livre déjà réservé, "
						+ "indisponible à l'emprunt");
			if(emprunteur != null)
				throw new PasLibreException("Livre déjà emprunté");
			emprunteur = ab;
		}
		reserveur = null;
	}

	@Override
	public void retour() {
		emprunteur = null;
	}
	
	@Override
	public String toString() {
		return idLivre + " : \"" + titre + "\", " + auteur + 
				", emprunté par " + emprunteur + ", réservé par : " + 
				reserveur;
	}
}
