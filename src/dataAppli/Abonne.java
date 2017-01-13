package dataAppli;

public class Abonne {
	
	private static int cptAbonne = 0;
	
	private static final int TYPE_INDEX=0,
			ID_INDEX=1, NOM_INDEX=2, MAIL_INDEX=3, DATA_SIZE=4;
	
	private int idAbonne;
	private String nom;
	private String mail;
	
	public Abonne(String[] data) {
		if(data.length == DATA_SIZE && data[TYPE_INDEX].equals("abonne")){
			try {
				++cptAbonne;
				idAbonne = Integer.valueOf(data[ID_INDEX]);
			}catch(NumberFormatException e){
				System.err.println("id invalide : le premier numéro "
						+ "libre est affecté à la place");
				idAbonne = cptAbonne;
			}
			nom = data[NOM_INDEX];
			mail = data[MAIL_INDEX];
		}
	}

	public int getId() {
		return idAbonne;
	}
	
	public String toString(){
		return idAbonne + " : " + nom;
	}

	public String getNom() {
		return nom;
	}

	public String getMail() {
		return mail;
	}
}
