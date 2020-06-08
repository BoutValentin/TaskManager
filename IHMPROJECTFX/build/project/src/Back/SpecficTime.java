package Back;

import java.io.Serializable;
import java.util.List;
/** 
 * Une class permettant la représentation du temps avec toutes les méthodes liées
 * */
public class SpecficTime implements Serializable{
	private long year;
	private long month;
	private long day;
	private long hours;
	private long minutes;
	private long seconds;
	
	/** 
	 * Constructeur: prend en paramètre tout les valeurs de l'anné a la seconde avec pour condition d'etre > 0 ou initialiser a 0
	 * @param year L'année liée au temps
	 * @param month Le mois à liées au temps 
	 * @param days Le(s) jour(s) à liées au temps
	 * @param hours L(es) heure(s) à liées au temps
	 * @param minutes La/Les minute(s) à liées au temps
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long year, long month, long day,long hours, long minutes, long seconds) {
		this.year = (year>=0) ? year : 0;
		this.month = (month>=0) ? month : 0;
		this.day = (day>=0) ? day:0;
		this.hours = (hours>=0) ? hours:0;
		this.minutes = (minutes>=0)?minutes:0;
		this.seconds = (seconds>=0)?seconds:0;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Constructeur: prend en paramètre les valeurs du mois à la seconde avec pour condition d'etre > 0 ou initialiser a 0.
	 * Ici l'attribut years = 0;
	 * @param month Le mois à liées au temps 
	 * @param days Le(s) jour(s) à liées au temps
	 * @param hours L(es) heure(s) à liées au temps
	 * @param minutes La/Les minute(s) à liées au temps
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long month, long day,long hours, long minutes, long seconds) {
		this(0,month,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur: prend en paramètre les valeurs du jours à la seconde avec pour condition d'etre > 0 ou initialiser a 0.
	 * Ici l'attribut years = 0.
	 * Ici l'attribut month = 0. 
	 * @param days Le(s) jour(s) à liées au temps
	 * @param hours L(es) heure(s) à liées au temps
	 * @param minutes La/Les minute(s) à liées au temps
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long day,long hours, long minutes, long seconds) {
		this(0,0,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur: prend en paramètre les valeurs de l'heure à la seconde avec pour condition d'etre > 0 ou initialiser a 0.
	 * Ici l'attribut years = 0;
	 * Ici l'attribut month = 0.
	 * Ici l'attribut days = 0.
	 * @param hours L(es) heure(s) à liées au temps
	 * @param minutes La/Les minute(s) à liées au temps
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long hours, long minutes, long seconds) {
		this(0,0,0,hours,minutes,seconds);
	}
	/** 
	 * Constructeur: prend en paramètre les valeurs de la minute à la seconde avec pour condition d'etre > 0 ou initialiser a 0.
	 * Ici l'attribut years = 0;
	 * Ici l'attribut month = 0.
	 * Ici l'attribut days = 0.
	 * Ici l'attribut hours = 0.
	 * @param minutes La/Les minute(s) à liées au temps
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long minutes, long seconds) {
		this(0,0,0,0,minutes,seconds);
	}
	/** 
	 * Constructeur: prend en paramètre les secondes avec pour condition d'etre > 0 ou initialiser a 0.
	 * Ici l'attribut years = 0;
	 * Ici l'attribut month = 0.
	 * Ici l'attribut days = 0.
	 * Ici l'attribut hours = 0.
	 * Ici l'attribut minutes = 0.
	 * @param seconds La/Les seconde(s) à liées au temps
	 * */
	public SpecficTime(long seconds) {
		this(0,0,0,0,0,seconds);
	}
	/** 
	 * Constructeur: par defaut : initialiser a 0.
	 * Ici l'attribut years = 0;
	 * Ici l'attribut month = 0.
	 * Ici l'attribut days = 0.
	 * Ici l'attribut hours = 0.
	 * Ici l'attribut minutes = 0.
	 * Ici l'attribut seconds = 0.
	 * */
	public SpecficTime() {
		this(0,0,0,0,0,0);
	}
	/** 
	 * Accesseur de l'attribut year
	 * @return les années du temps
	 * */
	public long getYear() {
		return year;
	}
	/** 
	 * Mutateurs de l'attribut year suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param year les années a mettre au temps
	 * */
	public void setYear(long year) {
		this.year = (year>=0)?year:this.year;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Accesseur de l'attribut month
	 * @return le mois du temps
	 * */
	public long getMonth() {
		return month;
	}
	/** 
	 * Mutateurs de l'attribut month suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param month les mois a mettre au temps
	 * */
	public void setMonth(long month) {
		this.month = (month>0)?month:this.month;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Accesseur de l'attribut day
	 * @return les jours du temps
	 * */
	public long getDay() {
		return day;
	}
	/** 
	 * Mutateurs de l'attribut day suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param day les jours a mettre au temps
	 * */
	public void setDay(long day) {
		this.day = (day>=0)?day:this.day;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Accesseur de l'attribut hours
	 * @return les heures du temps
	 * */
	public long getHours() {
		return hours;
	}
	/** 
	 * Mutateurs de l'attribut hours suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param hours les heures a mettre au temps
	 * */
	public void setHours(long hours) {
		this.hours = (hours>=0)?hours:this.hours;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Accesseur de l'attribut minutes
	 * @return les minutes du temps
	 * */
	public long getMinutes() {
		return minutes;
	}
	/** 
	 * Mutateurs de l'attribut minutes suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param minutes les minutes a mettre au temps
	 * */
	public void setMinutes(long minutes) {
		this.minutes = (minutes>=0)?minutes:this.minutes;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Accesseur de l'attribut seconds
	 * @return les secondes du temps
	 * */
	public long getSeconds() {
		return seconds;
	}
	/** 
	 * Mutateurs de l'attribut seconds suivant d'une mise en bonne condition du temps voir methode makeInGoodConditionAppend
	 * @param seconds les secondes a mettre au temps
	 * */
	public void setSeconds(long seconds) {
		this.seconds = (seconds>=0)?seconds:this.seconds;
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Méthode privés :  remet le temps a zero;
	 * */
	private void resetMyTime() {
		this.year=0;
		this.month=0;
		this.day=0;
		this.hours=0;
		this.minutes=0;
		this.seconds=0;
	}
	/** 
	 * Méthode permettant d'ajouter autre temps dans le temps actuelle qui sera ensuite mis dans de bonne condition
	 * @param time Le temps dont les valeurs sont a copier dans le temps appelant la méthode
	 * */
	public void setATimeByAddingAnotherTime(SpecficTime time) {
		time.makeInGoodConditionAppend();
		this.year += time.getYear();
		this.month += time.getMonth();
		this.day += time.getDay();
		this.hours += time.getHours();
		this.minutes += time.getMinutes();
		this.seconds += time.getSeconds();
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Méthode permettant de ajouter au temps un nombre de secondes > 0 ou le nombre de secondes sera definit a 0
	 * @param seconds Un long représentant les secondes du à ajouter
	 * */
	public void setATimeByAddingSeconds(long seconds) {
		SpecficTime aTime = new SpecficTime(seconds);
		this.setATimeByAddingAnotherTime(aTime);
	}
	/** 
	 * Méthode permettant de parametrer le temps a partir d'un nombre de secondes donné >0
	 * @param seconds Un long représentant les seconde a paramètrer
	 * */
	public void setATimeWithSeconds(long seconds) {
		if(seconds>=0) {
			this.resetMyTime();
			this.seconds =seconds;
			this.makeInGoodConditionAppend();
		}
	}
	/** 
	 * Méthode permettant de copier un autre temps dans le temps actuelle qui sera ensuite mis dans de bonne condition
	 * @param time Le temps dont les valeurs sont a copier dans le temps appelant la méthode
	 * */
	public void setATimeWithAnotherTime(SpecficTime time) {
		this.seconds=time.getSeconds();
		this.minutes=time.getMinutes();
		this.hours=time.getHours();
		this.day=time.getDay();
		this.month=time.getMonth();
		this.year=time.getYear();
	}
	/** 
	 * Méthode privé réalisant la mise en condition correcte du temps.
	 * C'est a dire si seconde > 60 alors on ajoute x minutes a l'attribut minutes que l'on retire a l'attribut seconds.
	 * Ceci s'appliquant a tout les attributs afin d'avoir un temps dans des conditions correctes d'affichage et de calcul.
	 * */
	private boolean makeInGoodConditionAppend() {
		boolean append = true;
		while(append) {
			append = false;
		if(this.seconds>=60) {
			this.minutes += this.seconds/60;
			this.seconds = (this.seconds%60);
			append=true;
		}
		//Si les minutes sont superieur a 60
		if(this.minutes>=60) {
			this.hours += this.minutes/60;
			this.minutes = this.minutes%60;
			append=true;
		}
		//Si les heures sont superrieur a 24
		if(this.hours>=24) {
			this.day += this.hours/24;
			this.hours = this.hours%24;
			append=true;
		}
		//Si les jours sont superieur a 30
		if(this.day>=30) {
			this.month += this.day/30;
			this.day = this.day%30;
			append=true;
		}
		//Si les mois sont supperieur a 12
		if(this.month>=12) {
			this.year+=this.month/12;
			this.month=this.month%12;
			append=true;
		}
		}
		return append;
	}
	/** 
	 * Méthode public determinant si le temps actuelle est plus grand que celui en parametre
	 * @param time Le temps a comparer
	 * @return true si le temps actuelle est plus grand que le temps en paramètre ou false le cas echant
	 * */
	public boolean isGreaterThan(SpecficTime time) {
		if(this.getYear()<time.getYear()) return false;
		if(this.getYear()==0 && time.getYear()==0 && this.getMonth()<time.getMonth()) return false;
		if(this.getYear()==0&&this.getMonth()==0&&time.getYear()==0&&time.getMonth()==0 && this.getDay()<time.getDay()) return false;
		if(this.getYear()==0&&this.getMonth()==0&&time.getYear()==0&&time.getMonth()==0 && this.getDay()==0 && time.getDay()==0 &&this.getHours()<time.getHours()) return false;
		if(this.getYear()==0&&this.getMonth()==0&&time.getYear()==0&&time.getMonth()==0 && this.getDay()==0 && time.getDay()==0 && this.getHours()==0 && time.getHours()==0 && this.getMinutes()<time.getMinutes()) return false;
		if(this.getYear()==0&&this.getMonth()==0&&time.getYear()==0&&time.getMonth()==0 && this.getDay()==0 && time.getDay()==0 && this.getHours()==0 && time.getHours()==0 && this.getMinutes()==0&&time.getMinutes()==0&&this.getSeconds()<time.getSeconds()) return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (day ^ (day >>> 32));
		result = prime * result + (int) (hours ^ (hours >>> 32));
		result = prime * result + (int) (minutes ^ (minutes >>> 32));
		result = prime * result + (int) (month ^ (month >>> 32));
		result = prime * result + (int) (seconds ^ (seconds >>> 32));
		result = prime * result + (int) (year ^ (year >>> 32));
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
		SpecficTime other = (SpecficTime) obj;
		if (day != other.day)
			return false;
		if (hours != other.hours)
			return false;
		if (minutes != other.minutes)
			return false;
		if (month != other.month)
			return false;
		if (seconds != other.seconds)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	/** 
	 * Méthode rélisant le retrait d'un temps au temps actuelle. C'est a dire une soustraction entre deux temps.
	 * @params time Le temps a soustraire
	 * */
	public void setATimeByRemovingWithAnotherTime(SpecficTime time) {
		SpecficTime anotherTimeSub = new SpecficTime();
		if(!this.isGreaterThan(time)) {
			this.resetMyTime();
		}else if(this.equals(time)) {
			this.resetMyTime();
		}else {
			if(time.getSeconds()>this.getSeconds()) {
				anotherTimeSub.setSeconds((60-time.getSeconds())+this.getSeconds());
				time.setMinutes(time.getMinutes()+1);
			}else {
				anotherTimeSub.setSeconds(this.getSeconds()-time.getSeconds());
			}
			if(time.getMinutes()>this.getMinutes()) {
				anotherTimeSub.setMinutes((60-time.getMinutes())+this.getMinutes());
				time.setHours(time.getHours()+1);
			}else {
				anotherTimeSub.setMinutes(this.getMinutes()-time.getMinutes());
			}
			if(time.getHours()>this.getHours()) {
				anotherTimeSub.setHours((24-time.getHours())+this.getHours());
				time.setDay(time.getDay()+1);
			}else {
				anotherTimeSub.setHours(this.getHours()-time.getHours());
			}
			if(time.getDay()>this.getDay()) {
				anotherTimeSub.setDay((30-time.getDay())+this.getDay());
				time.setMonth(time.getMonth()+1);
			}else {
				anotherTimeSub.setDay(this.getDay()-time.getDay());
			}
			if(time.getMonth()>this.getMonth()) {
				anotherTimeSub.setMonth((12-time.getMonth())+this.getMonth());
				time.setYear(time.getYear()+1);
			}else {
				anotherTimeSub.setMonth(this.getMonth()-time.getMonth());
			}
			anotherTimeSub.setYear(this.getYear()-time.getYear());
		}
		this.setATimeWithAnotherTime(anotherTimeSub);
		this.makeInGoodConditionAppend();
	}
	/** 
	 * Méthode réalisant la soustraction d'un nombre de second au temps actuelle.
	 * Si le nombre de secondes est < 0 alors ce nombre sera remplacer par 0.
	 * @param seconds Le nombre de seconds a retirer
	 * */
	public void setATimeByRemovingWithSeconds(long seconds) {
		SpecficTime atime = new SpecficTime(seconds);
		atime.makeInGoodConditionAppend();
		this.setATimeByRemovingWithAnotherTime(atime);
	}
	/** 
	 * Méthode toString réalisant l'affichage du temps 
	 * @return Un String representatif du temps
	 * */
	public String toString() {
		return "Time: "+this.year+"years, "+this.month+"month, "+this.day+"days, "+this.hours+"hours, "+this.minutes+"minutes, "+this.seconds+"seconds";
	}
	
	
	
}
