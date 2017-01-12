package bibliotheque;

import java.util.ArrayList;
import java.util.List;

import documents.Livre;
import documents.PasLibreException;
import fileUtil.XMLReader;

public class Bibliothèque {
	
	private static Bibliothèque instance;
	
	private static final int ABO_INDEX = 4;
	
	private static String path = "data.xml";
	
	private List<Document> documents;
	
	private List<Abonne> abonnes;
	
	
	private Bibliothèque(String path){
		documents = new ArrayList<Document>();
		abonnes = new ArrayList<Abonne>();
		init();
	}

	//Il faudra changer la dépendance du livre un fois la factory
	//mise en place
	private void init() {
		List<String> objects = XMLReader.read(path);
		for(String s : objects){
			String[] data = s.split(";");
			switch(data[0]){
			case "abonne" : abonnes.add(new Abonne(data));
							break;
			case "livre" : Livre l = new Livre(data);
							int id = Integer.valueOf(data[ABO_INDEX]);
							if(id > 0)
								try {
									System.out.println(findAbonne(id));
									l.emprunter(findAbonne(id));
								} catch (PasLibreException | IllegalStateException e) {}
							id = Integer.valueOf(data[ABO_INDEX+1]);
							if(id > 0)
								try {
									l.reserver(findAbonne(id));
								} catch (PasLibreException | IllegalStateException e) {}
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
	
	public Document findDocument(int id){
		for(Document d : documents){
			if(d.numero() == id)
				return d;
		}
		return null;
	}
	
	public List<Document> getDocuments(){
		return documents;
	}
	
	public List<Abonne> getAbonnes(){
		return abonnes;
	}
	
	public static Bibliothèque getInstance(){
		if(instance == null)
			instance = new Bibliothèque(path);
		return instance;
	}
	
	public static void setPath(String newpath){
		path = newpath;
	}
}
