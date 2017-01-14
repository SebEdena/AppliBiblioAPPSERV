package dataAppli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import documents.DocumentFactory;
import fileUtil.XMLReader;

public class Biblioth�que {
	
	private static Biblioth�que instance;
	
	private static final int ABO_INDEX = 4;
	
	private static String path = "data.xml";
	
	private List<Document> documents;
	
	private List<Abonne> abonnes;
	
	private HashMap<Document,ArrayList<Abonne>> int�ress�s;
	
	private IDataFactory docsFactory;
	
	private Biblioth�que(String path){
		documents = new ArrayList<Document>();
		abonnes = new ArrayList<Abonne>();
		int�ress�s = new HashMap<Document, ArrayList<Abonne>>();
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
	
	public ArrayList<Abonne> getInt�ress�s(Document d){
		return int�ress�s.get(d);
	}
	
	public void clearInt�ress�s(Document d) {
		int�ress�s.remove(d);
	}
	
	public static Biblioth�que getInstance(){
		if(instance == null)
			instance = new Biblioth�que(path);
		return instance;
	}

	public void addWishingList(Document d, Abonne ab) {
		ArrayList<Abonne> temp = int�ress�s.get(d);
		if(temp == null){
			temp = new ArrayList<Abonne>();
			temp.add(ab);
			int�ress�s.put(d, temp);
		}else{
			if(!temp.contains(ab)) temp.add(ab);
			int�ress�s.put(d, temp);
		}
		System.out.println(int�ress�s);
	}
	
	public void setFactory(IDataFactory f){
		this.docsFactory = f;
	}

}
