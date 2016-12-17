package serveur;

import java.io.IOException;

import services.ServiceReservation;

public class ServeurReservation extends Serveur{

	ServeurReservation(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try {
			while(true)
				new ServiceReservation(getListen_socket().accept()).lancer();
		}
		catch (IOException e) { 
			try {this.getListen_socket().close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'�coute :"+e);
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.getListen_socket().close();} catch (IOException e1) {}
	}
}
