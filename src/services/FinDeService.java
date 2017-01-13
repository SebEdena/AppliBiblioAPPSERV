package services;

import java.util.Timer;
import java.util.TimerTask;

public class FinDeService extends TimerTask {
	
	private Service service;
	private Timer timer;
	
	public FinDeService(Service res, Timer t) {
		service = res;
		timer = t;
	}

	@Override
	public void run() {
		timer.cancel();
		try {
			service.finalize();
		} catch (Throwable e) {	}
	}

}
