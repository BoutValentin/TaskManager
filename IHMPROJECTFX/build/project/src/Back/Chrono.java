package Back;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
/** 
 * Une classe realisant l'implementation d'un chronometre ajoutant du temps. Class étroitement liées a la Class SpecificTime
 * */
public class Chrono implements Serializable {
	private Timer aTimer;
	private boolean isOn =false;
	/** 
	 * Constructeur: creer simplement un nouveaux Timer et par defaut isOn est a false comme aucun chronomètre n'est encore lancé
	 * */
	public Chrono() {
		this.aTimer = new Timer();
	}
	/** 
	 * Accesseur afin de connaitre l'état du chronomètre (actif ou non)
	 * @return l'etat actuel de l'attribut isOn et plus generalement l'etat du chronomètre
	 * */
	public boolean getIsOn() {
		return this.isOn;
	}
	/** 
	 * Implemente le demarrage du chronometre pour un seul temps et réalise toute les secondes l'incrémentation de 1 seconde de son temps
	 * @param time Le temps a mettre a jour
	 * */
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
	/** 
	 * Implemente le démarrage d'un double chronomètre increment de 1 seconde les deux temps passés en paramètre
	 * @param time1 Le temps 1 à mettre à jour
	 * @param time2 Le temps 2 à mettre à jour
	 * */
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
	/** 
	 * Methode pout arrete le chronomètre en cours
	 * */
	public void stop() {
		isOn=false;
		aTimer.cancel();
	}

}
