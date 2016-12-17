package fileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
	
	public static ArrayList<String> read(String path){
		DocumentBuilder builder = null;
		Document document = null;
		ArrayList<String> data = new ArrayList<String>();
		try {
			builder = DocumentBuilderFactory.newInstance()
										.newDocumentBuilder();
			document = builder.parse(new File(path));
		} catch (SAXException | IOException | ParserConfigurationException e){
			e.printStackTrace();
		}
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i<list.getLength(); i++) {
			if(list.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element object = (Element) list.item(i);
				String s = "";
				s += object.getNodeName() + ";" + 
						object.getAttribute("id").toString();
				NodeList subobjects = object.getChildNodes();
			    for (int j = 0; j < subobjects.getLength(); j++) {
					Element sub = (Element) subobjects.item(i);
					if(sub.getNodeType() == Node.ELEMENT_NODE){
						s+= ";" + sub.getTextContent();
					}
				}
			    data.add(s);
			}
		}
		return data;
	}
	
	/*public static void main(String[] args) {
		System.out.println(read("data.xml"));
	}*/
}
