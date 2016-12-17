package services;

public abstract class Service implements Runnable{

	private Thread thread = new Thread(this);
	
	@Override
	public abstract void run();

	public void lancer(){
		thread.start();
	}
}
