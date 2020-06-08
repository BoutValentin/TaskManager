package Back;

import java.util.ArrayList;
import java.util.List;
/** 
 * Une class heritant de la class Task avec une liste de sous-tache attache et un boolean afin de définir si les taches doit être affiché
 * */
public class GlobalTask extends Task{
	
	private List<SubTask> listOfSubTaskAttach ;
	private boolean showTheSubTask;
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
	public GlobalTask(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		super(name, year, month, day, hours, minutes, seconds);
		this.listOfSubTaskAttach = new ArrayList<SubTask>();
		this.showTheSubTask=false;
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
	public GlobalTask(String name,long month, long day,long hours, long minutes, long seconds ) {
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
	public GlobalTask(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public GlobalTask(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public GlobalTask(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * @param seconds Long pour le nombre de minutes
	 * */
	public GlobalTask(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la tache
	 * */
	public GlobalTask(String name) {
		this(name,0,0,0,0,0,0);
	}
	/** 
	 * Méthode retournant la liste des sous taches attachées 
	 * @return List<SubTask> la liste des sous taches attachées
	 * */
	public List<SubTask> getListOfSubTaskAttach() {
		return listOfSubTaskAttach;
	}
	/** 
	 * Méthode inversant le boolean indiquant si la liste de sous task doit etre attachés
	 * */
	public void setToggleOrUnToggleViewSubTask() {
		this.showTheSubTask = !this.showTheSubTask;
	}
	/** 
	 * Accesseurs de l'attribut toogle
	 * @return boolean de l'etat actuelle 
	 * */
	public boolean getToggle() {
		return this.showTheSubTask;
	}
	/** 
	 * Mutateurs de l'attribut des sous taches attachés
	 * @param listOfSubTaskAttach la list a remplacer par celle actuelle 
	 * */
	public void setListOfSubTaskAttach(List<SubTask> listOfSubTaskAttach) {
		this.listOfSubTaskAttach = listOfSubTaskAttach;
	}
	/** 
	 * Méthode retournant la sous-tache correspondant a l'id en paramètre
	 * @param id Un entier correspondant a la position de la tache souhaite
	 * @return SubTask correspondant a sa position dans la list
	 * */
	public SubTask getASubTaskById(int id) {
		if(id>=this.listOfSubTaskAttach.size()) return null;
		return this.listOfSubTaskAttach.get(id);
	}
	/** 
	 * Méthode rélisant la suppresion d'une sous tache par sa position
	 * @param id La position de la tache a supprimer dans la list
	 * */
	public void deleteATaskById(int id) {
		if(id>=this.listOfSubTaskAttach.size()) return ;
		SubTask sub = this.getASubTaskById(id);
		this.listOfSubTaskAttach.remove(sub);
	}
	/** 
	 * Méthode réalisant la suppression d'une tache dans la list attachée si elle existe
	 * @param sub SubTask a supprimer dans la liste attachée
	 * */
	public void deleteATaskByTask(SubTask sub) {
		this.listOfSubTaskAttach.remove(sub);
	}
	/** 
	 * Méthode réalisant la suppression de toutes les sous taches attachées 
	 * */
	public void clearAll() {
		this.listOfSubTaskAttach.clear();
	}
	/** 
	 * Méthode pour obtenir le nombre de sous tache attachées
	 * @return un entier correspondant au nombre de sous tache stockées 
	 * */
	public int getNumberOfUnderTask() {
		return this.listOfSubTaskAttach.size();
	}
	/** 
	 * Méthode ajoutant une sous-tache a la list 
	 * @param sub SubTask à ajouter a la list
	 * */
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
	/** 
	 * Méthoder retournant le nom de la tache
	 * @return String le nom de la sous tache
	 * */
	public String toString() {
		return this.getName();
	}
	/**
	 * Méthode recupérant les Temps des sous taches ajoutées et les retournant sous forme de list
	 * @return List<SpecficTime> la liste des Temps des sous taches 
	 * */
	public List<SpecficTime> getListOfTime(){
		List<SpecficTime> feedback = new ArrayList<>();
		for(SubTask s: this.getListOfSubTaskAttach()) {
			feedback.add(s.getAmountOfTime());
		}
		return feedback;
	}
	/** 
	 * Méthode retournant le temps le plus grand dans une list de sous tache
	 * @param times une List<SpecificTime> de temps
	 * @return SpecficTime le plus grand temps de la list en paramètre
	 * */
	public SpecficTime getGreaterInList(List<SpecficTime> times) {
		if(times.size()==0) return new SpecficTime();
		SpecficTime feedBack = times.get(0);
		for(SpecficTime s:times) {
			if(s.isGreaterThan(feedBack)) feedBack=s;
		}
		return feedBack;
	}
	/** 
	 * Méthode retournant un boolean si une sous tache porte le nom en paramètre
	 * @param name String du nom a tester
	 * @return boolean true si le nom est contenu sinon false
	 * */
	public boolean containName(String name) {
		for(SubTask s:this.listOfSubTaskAttach) {
			if(s.getName().equals(name)) return true;
		}
		return false;
	}
	/** 
	 * Méthode retournant la position d'une sous tache dans une list
	 * @param sub SubTask la sous tache dont ou souhaite obtenir la position
	 * @return int de la position dans la list ou -1 si non contenue
	 * */
	public int getPositionInList(SubTask sub) {
		int res =0;
		for(SubTask s: this.listOfSubTaskAttach) {
			System.out.println(res);
			if(s.equals(sub)) return res;
			res++;
		}
		return -1;
	}
}
