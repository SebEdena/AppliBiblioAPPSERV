package documents;

import java.util.Timer;
import java.util.TimerTask;

public class AnnuleRéservation extends TimerTask {
	private Livre livre;
	private Timer timer;

	public AnnuleRéservation(Livre l, Timer t) {
		livre = l;
		timer = t;
	}

	@Override
	public void run() {
		livre.retour();
		timer.cancel();
	}

}
