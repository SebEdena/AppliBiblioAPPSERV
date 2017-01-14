package documents;

import dataAppli.Document;
import dataAppli.IDataFactory;

public class DocumentFactory implements IDataFactory {

	@Override
	public Document getDocument(String type, String[] data) {
		switch(type){
		case "livre" : return new Livre(data);
		default : return null;
		}
	}

}
