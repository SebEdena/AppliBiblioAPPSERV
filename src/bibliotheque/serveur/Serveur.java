package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Serveur implements Runnable{

	private Thread thread = new Thread(this);
	private ServerSocket listen_socket;
	
	Serveur(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}
	
	@Override
	public abstract void run();
	
	public void lancer(){
		thread.start();
	}

	public ServerSocket getListen_socket() {
		return listen_socket;
	}
	
	protected void finalize() throws Throwable {
		try {listen_socket.close();} catch (IOException e1) {}
	}
}
