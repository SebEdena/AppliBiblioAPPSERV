package documents;

import java.util.Timer;
import java.util.TimerTask;

import dataAppli.Document;

public class AnnuleR�servation extends TimerTask {
	private Document doc;
	private Timer timer;

	public AnnuleR�servation(Document d, Timer t) {
		doc = d;
		timer = t;
	}

	@Override
	public void run() {
		doc.retour();
		timer.cancel();
	}

}
