package serveur;

import java.io.IOException;

import services.ServiceRetour;

public class ServeurRetour extends Serveur{

	ServeurRetour(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try {
			while(true)
				new ServiceRetour(getListen_socket().accept()).lancer();
		}
		catch (IOException e) { 
			try {this.getListen_socket().close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.getListen_socket().close();} catch (IOException e1) {}
	}

}
