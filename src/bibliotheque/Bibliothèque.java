package bibliotheque;

import java.util.ArrayList;
import java.util.List;

import documents.Livres;
import fileUtil.XMLReader;

public class Bibliothèque {
	
	private static final int ABO_INDEX = 4;
	
	private List<Document> documents;
	
	private List<Abonne> abonnes;
	
	public Bibliothèque(String path){
		documents = new ArrayList<Document>();
		abonnes = new ArrayList<Abonne>();
		init(path);
	}

	//Il faudra changer la dépendance du livre un fois la factory
	//mise en place
	private void init(String path) {
		List<String> objects = XMLReader.read(path);
		for(String s : objects){
			String[] data = s.split(";");
			switch(data[0]){
			case "abonne" : abonnes.add(new Abonne(data));
							break;
			case "livre" : Livres l = new Livres(data);
							int id = Integer.valueOf(data[ABO_INDEX]);
							if(id > 0)
								try {
									l.emprunter(findAbonne(id));
								} catch (PasLibreException e) {}
							id = Integer.valueOf(data[ABO_INDEX+1]);
							if(id > 0)
								try {
									l.reserver(findAbonne(id));
								} catch (PasLibreException e) {}
							documents.add(l);
							break;
			default : continue;
			}
		}
	}
	
	public Abonne findAbonne(int id){
		for(Abonne a : abonnes){
			if(a.getId() == id)
				return a;
		}
		return null;
	}
	
	public List<Document> getDocuments(){
		return documents;
	}
	
	public List<Abonne> getAbonnes(){
		return abonnes;
	}
}
