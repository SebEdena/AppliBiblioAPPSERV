package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;

import dataAppli.Bibliothèque;
import dataAppli.Document;

public class ServiceRetour extends Service{
	
	private static final long DELAI_INACTIVITE = 10000;
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
			Document d = Bibliothèque.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.retour();

			System.out.println("Etat du livre : " + d);
			out.println("Le retour a été enregistré.");
		}
		catch (IllegalArgumentException | IllegalStateException e){
			out.println("Le retour a échoué. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e);
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}	
	}

}
