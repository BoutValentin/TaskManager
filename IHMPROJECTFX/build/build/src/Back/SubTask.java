package Back;

public class SubTask extends Task{

	public SubTask(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		super(name, year, month, day, hours, minutes, seconds);
	}
	public SubTask(String name,long month, long day,long hours, long minutes, long seconds ) {
		this(name,0,month,day,hours,minutes,seconds);
	}
	public SubTask(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	public SubTask(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	public SubTask(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	public SubTask(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	public SubTask(String name) {
		this(name,0,0,0,0,0,0);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	public String toString() {
		return this.getName()+this.getAmountOfTime().toString();
	}
	
	
}
