package Back;


import Front.HBoxGlobalTask;
import Front.HBoxSubTask;
/** 
 * Sauvegarde (reinitialiser a chaque ouverture de l'application) la derniere tache lancer afin de pouvoir la relancer dans 
 * le systeme Tray a tout moment sans avoir a retourner dans l'application
 * */
public class ActualRunningSave{
	
	public HBoxGlobalTask actualOrLastHbox;
	public HBoxSubTask actualOrLastSubHbox;
	public boolean lastIsSub;
	
	public ActualRunningSave(HBoxGlobalTask actualOrLastHbox,HBoxSubTask actualOrLastSubHbox,boolean lastIsSub) {
		this.actualOrLastHbox = actualOrLastHbox;
		this.actualOrLastSubHbox = actualOrLastSubHbox;
		this.lastIsSub = lastIsSub;
	}
	public ActualRunningSave() {}
}
