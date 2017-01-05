package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.Bibliothèque;

public class ServiceEmprunt extends Service{
	
	public ServiceEmprunt(Socket accept) {
		super(accept);
	}
	
	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(getClient().getInputStream ( )));
		PrintWriter out = new PrintWriter (getClient().getOutputStream ( ), true);
				
			while(true){
				String line = in.readLine();
				out.println("le service à reçu "+line);
				
			}
		}
		catch (IOException e) {
		}
		System.err.println("Fin du service d'emprunt : ");
		try {getClient().close();} catch (IOException e2) {}
		
	}
	
	protected void finalize() throws Throwable {
		 getClient().close(); 
	}
}
