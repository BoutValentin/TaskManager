package Back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskSaver implements Serializable {
	
	private List<GlobalTask> listAllTask;
	public TaskSaver() {
		this.listAllTask=new ArrayList<GlobalTask>();
	}
	public int getPositionOfGlobalTask(GlobalTask task) {
		int res=0;
		for(GlobalTask g:this.listAllTask) {
			if(g.equals(task))return res;
			res++;
		}
		return -1;
	}

	public List<GlobalTask> getListAllTask() {
		return listAllTask;
	}
	public void addAGlobalTask(GlobalTask task) {
		this.listAllTask.add(task);
	}

	public void setListAllTask(List<GlobalTask> listAllTask) {
		this.listAllTask = listAllTask;
	}
	public GlobalTask getAGlobalTaskById(int id) {
		int i=0;
		for(GlobalTask s: this.listAllTask) {
			if(i==id) return s;
			i++;
		}
		return null;
	}
	public void deleteAGlobalTaskById(int id) {
		GlobalTask s=this.getAGlobalTaskById(id);
		s.clearAll();
		this.listAllTask.remove(s);
	}
	public void deleteAGlobalTaskByGlobalTask(GlobalTask task) {
		task.clearAll();
		if(this.listAllTask.remove(task))System.out.println("append");;
	}
	public void clearAll() {
		this.listAllTask.clear();
	}
	public String toString() {
		String sb = new String();
		for(GlobalTask t:listAllTask) {
			sb += t.toString();
		}
		return sb;
	}

	
}
