package Back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/** 
 * Class contenant toutes les Taches globales et sous taches pour la serialization
 * */
public class TaskSaver implements Serializable {
	
	private List<GlobalTask> listAllTask;
	
	/** 
	 * Constructeur par defaut : construit une ArrayList<GlobalTask> vide
	 * */
	public TaskSaver() {
		this.listAllTask=new ArrayList<GlobalTask>();
		
	}
	/** 
	 * Méthode retournant la position d'une tache globale dans la list
	 * @param task GlobalTask dont on souhaite la position dans la liste
	 * @return int de la position dans la list ou -1 si cette tache n'est pas contenue
	 * */
	public int getPositionOfGlobalTask(GlobalTask task) {
		int res=0;
		for(GlobalTask g:this.listAllTask) {
			if(g.equals(task))return res;
			res++;
		}
		return -1;
	}
	/** 
	 * Accesseur de la List<GlobalTask>
	 * @return List<GlobalTask>
	 * */
	public List<GlobalTask> getListAllTask() {
		return listAllTask;
	}
	/** 
	 * Méthode réalisant l'ajout d'une tache globale a la list
	 * @param task GlobalTask a ajouter a la list<GlobalTask>
	 * */
	public void addAGlobalTask(GlobalTask task) {
		this.listAllTask.add(task);
	}
	/** 
	 * Mutateur de l'attribut List<GlobalTask>
	 * @param listAllTask List<GlobalTask> qui remplace la liste actuelle
	 * */
	public void setListAllTask(List<GlobalTask> listAllTask) {
		this.listAllTask = listAllTask;
	}
	/**
	 * Méthode retournant la tache a la position demander ou null si non contenue ou index trop grand
	 * @param id int de l'index a chercher
	 * @return GlobalTask correspond a l'id ou null
	 *  */
	public GlobalTask getAGlobalTaskById(int id) {
		if(id>=this.listAllTask.size()) return null;
		return this.listAllTask.get(id);
	}
	/** 
	 * Méthode supprimant une Tache globale et ses sous taches attachées a partir de sa position
	 * @param id int de l'index de la tache a supprimer
	 * */
	public void deleteAGlobalTaskById(int id) {
		GlobalTask s=this.getAGlobalTaskById(id);
		s.clearAll();
		this.listAllTask.remove(s);
	}
	/** 
	 * Méthode supprimant une Tache globale et ses sous taches attachées
	 * @param task GlobalTask a supprimer
	 * */
	public void deleteAGlobalTaskByGlobalTask(GlobalTask task) {
		task.clearAll();
		this.listAllTask.remove(task);
	}
	/** 
	 * Méthode supprimant toutes les taches globales de la list
	 * */
	public void clearAll() {
		this.listAllTask.clear();
	}
	/** 
	 * Méthode renvoyant la list sous forme de String par le nom des taches 
	 * @return String du nom de toutes les taches contenue dans la list
	 * */
	public String toString() {
		String sb = new String();
		for(GlobalTask t:listAllTask) {
			sb += t.toString();
		}
		return sb;
	}

	
}
