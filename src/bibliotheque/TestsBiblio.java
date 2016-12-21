package bibliotheque;

import java.util.List;

import org.junit.Test;

import fileUtil.XMLReader;
import org.junit.Assert;

public class TestsBiblio {
	
	@Test
	public void testAbonne(){
		List<String> test = XMLReader.read("data.xml");
		Assert.assertEquals("Stephane Manamanche", test.get(0).split(";")[2]);
		Abonne a = new Abonne(test.get(3).split(";"));
	}
	
	@Test 
	public void testBiblio(){
		Bibliothèque b = new Bibliothèque("data.xml");
		
		for(Abonne a : b.getAbonnes()){
			System.out.println(a.toString());
		}
		System.out.println();
		for(Document d : b.getDocuments()){
			System.out.println(d.toString());
		}
	}
}
