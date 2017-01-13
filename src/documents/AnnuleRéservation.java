package documents;

import java.util.Timer;
import java.util.TimerTask;

public class AnnuleR�servation extends TimerTask {
	private Livre livre;
	private Timer timer;

	public AnnuleR�servation(Livre l, Timer t) {
		livre = l;
		timer = t;
	}

	@Override
	public void run() {
		livre.retour();
		timer.cancel();
	}

}
