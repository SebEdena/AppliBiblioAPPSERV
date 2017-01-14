package documents;

import java.util.Timer;
import java.util.TimerTask;

import dataAppli.Document;

public class AnnuleRéservation extends TimerTask {
	private Document doc;
	private Timer timer;

	public AnnuleRéservation(Document d, Timer t) {
		doc = d;
		timer = t;
	}

	@Override
	public void run() {
		doc.retour();
		timer.cancel();
	}

}
