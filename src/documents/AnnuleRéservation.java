package documents;

import java.util.Timer;
import java.util.TimerTask;

import dataAppli.Document;

public class AnnuleR�servation extends TimerTask {
	private Document livre;
	private Timer timer;

	public AnnuleR�servation(Document d, Timer t) {
		livre = d;
		timer = t;
	}

	@Override
	public void run() {
		livre.retour();
		timer.cancel();
	}

}
