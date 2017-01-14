package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;

import dataAppli.Biblioth�que;
import dataAppli.Document;
import dataAppli.PasLibreException;

public class ServiceRetour extends Service{
	
	//Variable du timer � modifier pour tester l'inactivit� de 3 minutes
	private static final long DELAI_INACTIVITE = 180000;
	private Timer t;
	
	public ServiceRetour(Socket accept) {
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
			
			t.schedule(new FinDeService(this, t), DELAI_INACTIVITE);
			String line = in.readLine();
			t.cancel();
			Integer id = Integer.parseInt(line);
			Document d = Biblioth�que.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.retour();

			out.println("Le retour a �t� enregistr�.");
			System.out.println("Retour ok.");
		}
		catch (IllegalArgumentException | IllegalStateException e){
			System.out.println("Retour pas ok.");
			out.println("Le retour a �chou�. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e);
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}	
	}

}
