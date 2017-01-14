package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.Biblioth�que;
import dataAppli.Document;
import dataAppli.PasLibreException;

public class ServiceEmprunt extends Service{
	
	//Variable du timer � modifier pour tester l'inactivit� de 3 minutes
	private static final long DELAI_INACTIVITE = 180000;
	private Timer t;
	
	public ServiceEmprunt(Socket accept) {
		super(accept);
		t = new Timer();
	}
	
	@Override
	public void run() {
		System.out.println("Nouveau client : " + getClient().getInetAddress());
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader (new InputStreamReader(getClient().getInputStream()));
			out = new PrintWriter (getClient().getOutputStream (), true);

			// lit les lignes en controlant l'inactivit�
			t.schedule(new FinDeService(this, t), DELAI_INACTIVITE);
			
			String line = in.readLine();
			Integer id = Integer.parseInt(line);
			Abonne ab = Biblioth�que.getInstance().findAbonne(id);
			if(ab == null)
				throw new IllegalArgumentException("Abonn� inexistant");

			line = in.readLine();
			t.cancel();
			id = Integer.parseInt(line);
			Document d = Biblioth�que.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.emprunter(ab);

			out.println("L'emprunt a �t� enregistr�.");
			System.out.println("Emprunt ok.");
		}
		catch (PasLibreException e){
			System.out.println("Emprunt pas ok.");
			out.println("La r�servation a �chou� : " + e.getMessage() +
					". Vous recevrez un mail quand le document sera � nouveau disponible.");
		}
		catch (IllegalArgumentException | IllegalStateException e){
			System.out.println("Emprunt pas ok.");
			out.println("La r�servation a �chou�. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e);
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}
	}
}
