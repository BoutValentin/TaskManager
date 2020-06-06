package Back;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Chrono implements Serializable {
	private Timer aTimer;
	private boolean isOn =false;
	public Chrono() {
		this.aTimer = new Timer();
	}
	public boolean getIsOn() {
		return this.isOn;
	}
	public void start(SpecficTime time) {
		this.aTimer = new Timer();
		isOn=true;
		aTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				time.setATimeByAddingSeconds(1);
				System.out.println(time.toString());
			}
		}, 0, 1000);
	}
	public void doubleStart(SpecficTime time1, SpecficTime time2) {
		this.aTimer = new Timer();
		isOn=true;
		aTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				time1.setATimeByAddingSeconds(1);
				time2.setATimeByAddingSeconds(1);
				System.out.println(time1.toString()+time2.toString());
			}
		}, 0, 1000);
	}
	public void stop() {
		isOn=false;
		aTimer.cancel();
	}
	public static void main(String[] args) {
		Chrono aChrono = new Chrono();
		SpecficTime aTime = new SpecficTime();
		System.out.println("hello");
		aChrono.start(aTime);
		if(aTime.getMinutes()==1)aChrono.stop();
	}
}
