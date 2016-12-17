package serveur;

import java.io.IOException;

import services.ServiceEmprunt;

public class ServeurEmprunt extends Serveur{

	ServeurEmprunt(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try {
			while(true)
				new ServiceEmprunt(getListen_socket().accept()).lancer();
		}
		catch (IOException e) { 
			try {this.getListen_socket().close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}

	protected void finalize() throws Throwable {
		try {this.getListen_socket().close();} catch (IOException e1) {}
	}

}
