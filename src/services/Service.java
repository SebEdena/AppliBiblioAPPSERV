package services;

import java.net.Socket;

public abstract class Service implements Runnable{
	
	private Socket client;
	private Thread thread;
	
	public Service(Socket accept) {
		this.thread = new Thread(this);
		this.client = accept;
	}
	
	@Override
	public abstract void run();

	public void lancer(){
		thread.start();
	}

	public Socket getClient() {
		return client;
	}
}
