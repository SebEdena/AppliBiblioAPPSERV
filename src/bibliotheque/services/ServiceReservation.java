package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;

import dataAppli.Abonne;
import dataAppli.Bibliothèque;
import dataAppli.Document;
import dataAppli.PasLibreException;

public class ServiceReservation extends Service{

	private static final long DELAI_INACTIVITE = 10000;
	private Timer t;
	
	public ServiceReservation(Socket accept) {
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
			
			// lit les lignes en controlant l'inactivité
			t.schedule(new FinDeService(this, t), DELAI_INACTIVITE);
			
			String line = in.readLine();
			Integer id = Integer.parseInt(line);
			Abonne ab = Bibliothèque.getInstance().findAbonne(id);
			if(ab == null)
				throw new IllegalArgumentException("Abonné inexistant");

			line = in.readLine();
			t.cancel();
			id = Integer.parseInt(line);
			Document d = Bibliothèque.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.reserver(ab);

			System.out.println(d);
			out.println("La réservation a été effectuée.");
		}
		catch (PasLibreException | IllegalArgumentException | IllegalStateException e){
			out.println("La réservation a échoué. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e);
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}
	}

}
