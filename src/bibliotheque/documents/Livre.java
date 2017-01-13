package documents;

import dataAppli.Abonne;
import dataAppli.Document;
import dataAppli.PasLibreException;
import documents.etats.DocumentLibre;

public class Livre implements Document{
	private static int cptLivre = 0;
	
	private static final int TYPE_INDEX=0,
			ID_INDEX=1, TITRE_INDEX=2, AUTEUR_INDEX=3;
	
	private int idLivre;
	
	private String titre;
	
	private String auteur;
	
	private EtatDocument etat;
	
	public Livre(String[] data) {
		
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
			auteur = data[AUTEUR_INDEX];
			etat = new DocumentLibre();
		}
	}

	@Override
	public int numero() {
		return idLivre;
	}

	@Override
	public synchronized void reserver(Abonne ab) throws PasLibreException {
		if(ab != null){
			etat = etat.reservation(this, ab);
		}
	}

	@Override
	public synchronized void emprunter(Abonne ab) throws PasLibreException {
		if(ab != null){
			etat = etat.emprunt(ab);
		}
	}

	@Override
	public synchronized void retour() {
		etat = etat.retour(null);
	}
	
	public String getStatus(){
		return this.toString() + ", " + etat.getStatus();
	}
	
	@Override
	public String toString() {
		return idLivre + " : \"" + titre + "\", " + auteur;
				
	}
}
