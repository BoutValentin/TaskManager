package Back;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GlobalTask extends Task{
	
	//private Set<SubTask> listOfSubTaskAttach ;
	private List<SubTask> listOfSubTaskAttach ;

	private boolean showTheSubTask;
	public GlobalTask(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		super(name, year, month, day, hours, minutes, seconds);
		this.listOfSubTaskAttach = new ArrayList<SubTask>();
		this.showTheSubTask=false;
	}
	public GlobalTask(String name,long month, long day,long hours, long minutes, long seconds ) {
		this(name,0,month,day,hours,minutes,seconds);
	}
	public GlobalTask(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	public GlobalTask(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	public GlobalTask(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	public GlobalTask(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	public GlobalTask(String name) {
		this(name,0,0,0,0,0,0);
	}
	public List<SubTask> getListOfSubTaskAttach() {
		return listOfSubTaskAttach;
	}
	public void setToggleOrUnToggleViewSubTask() {
		this.showTheSubTask = !this.showTheSubTask;
	}
	public boolean getToggle() {
		return this.showTheSubTask;
	}
	public void setListOfSubTaskAttach(List<SubTask> listOfSubTaskAttach) {
		this.listOfSubTaskAttach = listOfSubTaskAttach;
	}
	
	public SubTask getASubTaskById(int id) {
		int i=0;
		for(SubTask s: this.listOfSubTaskAttach) {
			if(i==id) return s;
			i++;
		}
		return null;
	}
	public void deleteATaskById(int id) {
		SubTask sub = this.getASubTaskById(id);
		this.listOfSubTaskAttach.remove(sub);
	}
	public void deleteATaskByTask(SubTask sub) {
		System.out.println(listOfSubTaskAttach);
		System.out.println(listOfSubTaskAttach.contains(sub));
		System.out.println(sub);
		if(this.listOfSubTaskAttach.remove(sub))System.out.println("find and remove");;
	}
	public void clearAll() {
		this.listOfSubTaskAttach.clear();
	}
	public int getNumberOfUnderTask() {
		return this.listOfSubTaskAttach.size();
	}
	public void addASubtask(SubTask sub) {
		this.listOfSubTaskAttach.add(sub);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listOfSubTaskAttach == null) ? 0 : listOfSubTaskAttach.hashCode());
		result = prime * result + (showTheSubTask ? 1231 : 1237);
		result = prime * result + this.getName().hashCode();
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
		GlobalTask other = (GlobalTask) obj;
		if (listOfSubTaskAttach == null) {
			if (other.listOfSubTaskAttach != null)
				return false;
		} else if (!listOfSubTaskAttach.equals(other.listOfSubTaskAttach))
			return false;
		if (showTheSubTask != other.showTheSubTask)
			return false;
		return true;
	}
	public String toString() {
		return this.getName();
	}
	public List<SpecficTime> getListOfTime(){
		List<SpecficTime> feedback = new ArrayList<>();
		for(SubTask s: this.getListOfSubTaskAttach()) {
			feedback.add(s.getAmountOfTime());
		}
		return feedback;
	}
	public SpecficTime getGreaterInList(List<SpecficTime> times) {
		SpecficTime feedBack = times.get(0);
		for(SpecficTime s:times) {
			if(s.isGreaterThan(feedBack)) feedBack=s;
		}
		return feedBack;
	}
	public boolean containName(String name) {
		for(SubTask s:this.listOfSubTaskAttach) {
			if(s.getName().equals(name)) return true;
		}
		return false;
	}
	public int getPositionInList(SubTask sub) {
		int res =0;
		for(SubTask s: this.listOfSubTaskAttach) {
			if(s.equals(sub)) return res;
			res++;
		}
		return -1;
	}
	
	
}
