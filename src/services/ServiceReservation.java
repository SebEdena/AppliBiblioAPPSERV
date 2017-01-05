package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.Abonne;
import bibliotheque.Bibliothèque;
import bibliotheque.Document;
import bibliotheque.PasLibreException;

public class ServiceReservation extends Service{

	public ServiceReservation(Socket accept) {
		super(accept);
	}

	@Override
	public void run() {
		System.out.println("Nouveau client : " + getClient().getInetAddress());
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(getClient().getInputStream()));
			PrintWriter out = new PrintWriter (getClient().getOutputStream (), true);

			//while (true) {
				// lit la ligne
				String line = in.readLine();
				Integer id = Integer.parseInt(line);
				Abonne ab = Bibliothèque.getInstance().findAbonne(id);
				if(ab == null)
					throw new Exception("Abonné inexistant");
				
				line = in.readLine();
				id = Integer.parseInt(line);
				Document d = Bibliothèque.getInstance().findDocument(id);
				System.out.println(d);
				if(d == null)
					throw new Exception("Document inexistant");
				
				d.reserver(ab);
				
				out.println("ok!\n"+d);
			//}
		}
		catch (IOException e) {
			System.out.println("nol");
		} 
		catch (Exception e2) {
			System.out.println("nok");
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}
	}

}
