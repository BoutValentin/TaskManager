package Back;

import java.io.Serializable;

import javafx.scene.control.Label;

public abstract class Task implements Serializable{
	//Rajoutez un boolean chrono is On a handle pendant le refresh
	private String taskName;
	private SpecficTime amoutOfTime;
	private boolean isChronoOn;
	public Task(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		this.taskName = name;
		this.amoutOfTime = new SpecficTime(year, month, day, hours, minutes, seconds);
		this.isChronoOn=false;
	}
	
	public Task(String name,long month, long day,long hours, long minutes, long seconds ) {
		this(name,0,month,day,hours,minutes,seconds);
	}
	public Task(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	public Task(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	public Task(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	public Task(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	public Task(String name) {
		this(name,0,0,0,0,0,0);
	}
	public void setIsChronoOn(boolean b) {
		this.isChronoOn=b;
	}
	public boolean getIsChronoOn() {
		return this.isChronoOn;
	}
	public String getName(){
		return this.taskName;
	}
	public void setName(String name){
		this.taskName = name;
	}
	public SpecficTime getAmountOfTime(){
		return this.amoutOfTime;
	}
	public void setAmontOfTimeByTime(SpecficTime time){
		this.amoutOfTime = time;
	}
	
	public void setAmountOfTimeBySeconds(long seconds){
		this.amoutOfTime.setATimeWithSeconds(seconds);
	}
	public void addAmountOfTimeBySeconds(long seconds){
		this.amoutOfTime.setATimeByAddingSeconds(seconds);
	}
	public void addAmountOfTimeByTime(SpecficTime time){
		this.amoutOfTime.setATimeByAddingAnotherTime(time);
	}
	public void removeAmountOfTimeBySeconds(long seconds) {
		this.amoutOfTime.setATimeByRemovingWithSeconds(seconds);
	}
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
