package Back;

import java.io.Serializable;

import javafx.scene.control.Label;
/** 
 * Une class abstraite represenant une tache avec un nom, un temps, et un boolean si le chrono est en cours
 * */
public abstract class Task implements Serializable{
	private String taskName;
	private SpecficTime amoutOfTime;
	private boolean isChronoOn;
	
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param year Long pour le nombre d'années
	 * @param month Long pour le nombre de mois 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		this.taskName = name;
		this.amoutOfTime = new SpecficTime(year, month, day, hours, minutes, seconds);
		this.isChronoOn=false;
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param month Long pour le nombre de mois 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name,long month, long day,long hours, long minutes, long seconds ) {
		this(name,0,month,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param seconds Long pour le nombre de minutes
	 * */
	public Task(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * */
	public Task(String name) {
		this(name,0,0,0,0,0,0);
	}
	/** 
	 * Mutateurs de l'attribut boolean chrono
	 * @param b boolean de l'attribut a remplacer 
	 * */
	public void setIsChronoOn(boolean b) {
		this.isChronoOn=b;
	}
	/** 
	 * Accesseurs de l'attribut boolean chrono
	 * @return boolean de l'attribut chrono
	 * */
	public boolean getIsChronoOn() {
		return this.isChronoOn;
	}
	/** 
	 * Accesseurs de l'attribut name de la tache
	 * @return String le nom de la tache
	 * */
	public String getName(){
		return this.taskName;
	}
	/** 
	 * Mutateurs de l'attribut name de a tache
	 * @param name String a remplacer comme nom de la tache
	 * */
	public void setName(String name){
		this.taskName = name;
	}
	/** 
	 * Accesseurs de l'attribut SpecficTime
	 * @return SpecficTime le temps liées a la tache
	 * */
	public SpecficTime getAmountOfTime(){
		return this.amoutOfTime;
	}
	/** 
	 * Mutateurs de l'attribut SpecficTime
	 * @param time SpecficTime a remplacer de l'attribut actuel
	 * */
	public void setAmontOfTimeByTime(SpecficTime time){
		this.amoutOfTime = time;
	}
	/** 
	 * Méthode déleguée fixant le temps par un nombre de seconde
	 * @param seconds Long > 0 ou remplacé à 0 du temps a fixer
	 * */
	public void setAmountOfTimeBySeconds(long seconds){
		this.amoutOfTime.setATimeWithSeconds(seconds);
	}
	/** 
	 * Méthode déléguée ajoutant au temps le nombre de seconde
	 * @param seconds Long > 0 ou remplacé par 0 du temps à ajouter
	 * */
	public void addAmountOfTimeBySeconds(long seconds){
		this.amoutOfTime.setATimeByAddingSeconds(seconds);
	}
	/** 
	 * Méthode déléguée ajoutant au temps un autre temps
	 * @param time SpecficTime du temps à ajouter au temps de la tache
	 * */
	public void addAmountOfTimeByTime(SpecficTime time){
		this.amoutOfTime.setATimeByAddingAnotherTime(time);
	}
	/** 
	 * Méthode déléguée qui soustrait un nombre de seconde au temps de la tache
	 * @param seconds Long du nombre de seconds a retirer du temps
	 * */
	public void removeAmountOfTimeBySeconds(long seconds) {
		this.amoutOfTime.setATimeByRemovingWithSeconds(seconds);
	}
	/** 
	 * Méthode déléguée qui soustrait au temps actuel un autre temps
	 * @param time SpecficTime a retirer du temps actuel
	 * */
	public void removeAmountOfTimeByTime(SpecficTime time) {
		this.amoutOfTime.setATimeByRemovingWithAnotherTime(time);
	}
	
	public int precisionToShow(){
		if(this.amoutOfTime.getYear()!=0)return 6;
		if(this.amoutOfTime.getMonth()!=0)return 5;
		if(this.amoutOfTime.getDay()!=0)return 4;
		if(this.amoutOfTime.getHours()!=0)return 3;
		return 2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amoutOfTime == null) ? 0 : amoutOfTime.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (amoutOfTime == null) {
			if (other.amoutOfTime != null)
				return false;
		} else if (!amoutOfTime.equals(other.amoutOfTime))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}
	
	
	
}
