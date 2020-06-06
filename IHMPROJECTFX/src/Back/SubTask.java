package Back;
/** 
 * Une class implementant une sous tache heritant de Task
 * */
public class SubTask extends Task{
	
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous tache
	 * @param year Long pour le nombre d'années
	 * @param month Long pour le nombre de mois 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name, long year, long month, long day,long hours, long minutes, long seconds ) {
		super(name, year, month, day, hours, minutes, seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous tache
	 * @param month Long pour le nombre de mois 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name,long month, long day,long hours, long minutes, long seconds ) {
		this(name,0,month,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous-tache 
	 * @param day Long pour le nombre de jour
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name,long day,long hours, long minutes, long seconds ) {
		this(name,0,0,day,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous-tache
	 * @param hours Long pour le nombre d'heure
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name,long hours, long minutes, long seconds ) {
		this(name,0,0,0,hours,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous-tache
	 * @param minutes Long pour le nombre de minutes
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name,long minutes, long seconds ) {
		this(name,0,0,0,0,minutes,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous-tache
	 * @param seconds Long pour le nombre de minutes
	 * */
	public SubTask(String name,long seconds ) {
		this(name,0,0,0,0,0,seconds);
	}
	/** 
	 * Constructeur definissant un nom pour la sous-tache et créant aussi un attribut SpecificTime
	 * @param name String pour le nom de la sous-tache
	 * */
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
	/** 
	 * Méthode toString retournant le nom de la  sous tache
	 * @return String du nom de la sous tache
	 * */
	public String toString() {
		return this.getName();
	}
	
	
}
