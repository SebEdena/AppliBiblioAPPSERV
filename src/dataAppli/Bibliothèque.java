package dataAppli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import documents.DocumentFactory;
import fileUtil.XMLReader;

public class Bibliothèque {
	
	private static Bibliothèque instance;
	
	private static final int ABO_INDEX = 4;
	
	private static String path = "data.xml";
	
	private List<Document> documents;
	
	private List<Abonne> abonnes;
	
	private HashMap<Document,ArrayList<Abonne>> intéressés;
	
	private IDataFactory docsFactory;
	
	private Bibliothèque(String path){
		documents = new ArrayList<Document>();
		abonnes = new ArrayList<Abonne>();
		intéressés = new HashMap<Document, ArrayList<Abonne>>();
		init();
	}

	private void init() {
		setFactory(new DocumentFactory());
		List<String> objects = XMLReader.read(path);
		for(String s : objects){
			String[] data = s.split(";");
			if(data[0].equals("abonne")){
				abonnes.add(new Abonne(data));
			}
			else{
				Document d = docsFactory.getDocument(data[0], data);
				if(d == null)
					continue;
				int id = Integer.valueOf(data[ABO_INDEX]);
				if(id > 0)
					try {
						System.out.println(findAbonne(id));
						d.emprunter(findAbonne(id));
					} catch (PasLibreException | IllegalStateException e) {}
				id = Integer.valueOf(data[ABO_INDEX+1]);
				if(id > 0)
					try {
						d.reserver(findAbonne(id));
					} catch (PasLibreException | IllegalStateException e ) {}
				documents.add(d);
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
	
	public ArrayList<Abonne> getIntéressés(Document d){
		return intéressés.get(d);
	}
	
	public void clearIntéressés(Document d) {
		intéressés.remove(d);
	}
	
	public static Bibliothèque getInstance(){
		if(instance == null)
			instance = new Bibliothèque(path);
		return instance;
	}

	public void addWishingList(Document d, Abonne ab) {
		ArrayList<Abonne> temp = intéressés.get(d);
		if(temp == null){
			temp = new ArrayList<Abonne>();
			temp.add(ab);
			intéressés.put(d, temp);
		}else{
			if(!temp.contains(ab)) temp.add(ab);
			intéressés.put(d, temp);
		}
		System.out.println(intéressés);
	}
	
	public void setFactory(IDataFactory f){
		this.docsFactory = f;
	}

}
