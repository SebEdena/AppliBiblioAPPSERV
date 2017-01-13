package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import dataAppli.Biblioth�que;
import dataAppli.Document;

public class ServiceRetour extends Service{

	public ServiceRetour(Socket accept) {
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
			
			String line = in.readLine();
			Integer id = Integer.parseInt(line);
			Document d = Biblioth�que.getInstance().findDocument(id);
			if(d == null)
				throw new IllegalArgumentException("Document inexistant");

			d.retour();

			System.out.println(d);
			out.println("Le retour a �t� enregistr�.");
		}
		catch (IllegalArgumentException | IllegalStateException e){
			out.println("Le retour a �chou�. Motif : " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("pb dans les readers/writers");
			out.println("Le retour a �chou� pour des raisons techniques.");
		}
		System.err.println("Fin du service");
		try {getClient().close();} catch (IOException e2) {}	
	}

}
