package Back;

import java.io.Serializable;
import java.util.List;

public class SpecficTime implements Serializable{
	private long year;
	private long month;
	private long day;
	private long hours;
	private long minutes;
	private long seconds;
	
	public SpecficTime(long year, long month, long day,long hours, long minutes, long seconds) {
		this.year = (year>=0) ? year : 0;
		this.month = (month>=0) ? month : 0;
		this.day = (day>=0) ? day:0;
		this.hours = (hours>=0) ? hours:0;
		this.minutes = (minutes>=0)?minutes:0;
		this.seconds = (seconds>=0)?seconds:0;
		this.makeInGoodConditionAppend();
	}
	public SpecficTime(long month, long day,long hours, long minutes, long seconds) {
		this(0,month,day,hours,minutes,seconds);
	}
	public SpecficTime(long day,long hours, long minutes, long seconds) {
		this(0,0,day,hours,minutes,seconds);
	}
	public SpecficTime(long hours, long minutes, long seconds) {
		this(0,0,0,hours,minutes,seconds);
	}
	public SpecficTime(long minutes, long seconds) {
		this(0,0,0,0,minutes,seconds);
	}
	public SpecficTime(long seconds) {
		this(0,0,0,0,0,seconds);
	}
	public SpecficTime() {
		this(0,0,0,0,0,0);
	}
	
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = (year>=0)?year:this.year;
		this.makeInGoodConditionAppend();
	}
	public long getMonth() {
		return month;
	}
	public void setMonth(long month) {
		this.month = (month>0)?month:this.month;
		this.makeInGoodConditionAppend();
	}
	public long getDay() {
		return day;
	}
	public void setDay(long day) {
		this.day = (day>=0)?day:this.day;
		this.makeInGoodConditionAppend();
	}
	public long getHours() {
		return hours;
	}
	public void setHours(long hours) {
		this.hours = (hours>=0)?hours:this.hours;
		this.makeInGoodConditionAppend();
	}
	public long getMinutes() {
		return minutes;
	}
	public void setMinutes(long minutes) {
		this.minutes = (minutes>=0)?minutes:this.minutes;
		this.makeInGoodConditionAppend();
	}
	public long getSeconds() {
		return seconds;
	}
	public void setSeconds(long seconds) {
		this.seconds = (seconds>=0)?seconds:this.seconds;
		this.makeInGoodConditionAppend();
	}
	private void resetMyTime() {
		this.year=0;
		this.month=0;
		this.day=0;
		this.hours=0;
		this.minutes=0;
		this.seconds=0;
	}
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
	public void setATimeByAddingSeconds(long seconds) {
		SpecficTime aTime = new SpecficTime(seconds);
		this.setATimeByAddingAnotherTime(aTime);
	}
	public void setATimeWithSeconds(long seconds) {
		if(seconds>=0) {
			this.resetMyTime();
			this.seconds =seconds;
			this.makeInGoodConditionAppend();
		}
	}
	public void setATimeWithAnotherTime(SpecficTime time) {
		this.seconds=time.getSeconds();
		this.minutes=time.getMinutes();
		this.hours=time.getHours();
		this.day=time.getDay();
		this.month=time.getMonth();
		this.year=time.getYear();
	}
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
	//Todo methodde equals
	
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
	public void setATimeByRemovingWithSeconds(long seconds) {
		SpecficTime atime = new SpecficTime(seconds);
		atime.makeInGoodConditionAppend();
		this.setATimeByRemovingWithAnotherTime(atime);
	}
	
	public String toString() {
		return "Time: "+this.year+"years, "+this.month+"month, "+this.day+"days, "+this.hours+"hours, "+this.minutes+"minutes, "+this.seconds+"seconds";
	}
	
	
	
}
