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
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader (new InputStreamReader(getClient().getInputStream()));
			out = new PrintWriter (getClient().getOutputStream (), true);

			// lit la ligne
			String line = in.readLine();
			Integer id = Integer.parseInt(line);
			Abonne ab = Bibliothèque.getInstance().findAbonne(id);
			if(ab == null)
				throw new IllegalArgumentException("Abonné inexistant");

			line = in.readLine();
			id = Integer.parseInt(line);
			Document d = Bibliothèque.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.reserver(ab);

			System.out.println(d);
			out.println("La réservation a été effectuée.");
		}
		catch (PasLibreException | IllegalArgumentException e){
			out.println("La réservation a échoué. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("pb dans les readers/writers");
			out.println("La réservation a échoué pour des raisons techniques.");
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}
	}

}
