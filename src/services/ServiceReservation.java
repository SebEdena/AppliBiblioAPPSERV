package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.Abonne;
import bibliotheque.Biblioth�que;
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
			Abonne ab = Biblioth�que.getInstance().findAbonne(id);
			if(ab == null)
				throw new IllegalArgumentException("Abonn� inexistant");

			line = in.readLine();
			id = Integer.parseInt(line);
			Document d = Biblioth�que.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.reserver(ab);

			System.out.println(d);
			out.println("La r�servation a �t� effectu�e.");
		}
		catch (PasLibreException | IllegalArgumentException e){
			out.println("La r�servation a �chou�. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("pb dans les readers/writers");
			out.println("La r�servation a �chou� pour des raisons techniques.");
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}
	}

}
