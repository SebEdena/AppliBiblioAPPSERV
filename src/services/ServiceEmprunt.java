package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt extends Service{

	private Socket client;
	
	public ServiceEmprunt(Socket accept) {
		this.client = accept;
		this.lancer();
	}

	public void lancer() {
		(new Thread(this)).start();		
	}
	
	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
		PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
				// lit la ligne
				String line = in.readLine();
				out.println("le service à reçu "+line);
				line = in.readLine();
				out.println("le service à reçu "+line);
		}
		catch (IOException e) {
		}
		System.err.println("Fin du service d'emprunt : ");
		try {client.close();} catch (IOException e2) {}
		
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}
}
