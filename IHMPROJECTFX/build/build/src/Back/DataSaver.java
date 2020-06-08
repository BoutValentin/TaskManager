package Back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/** 
 * Une class implementant les méthodes necessaire a la serialization d'objet avec la sauvegarde et le 
 * chargement.
 * */
public class DataSaver {
	private final String pathname ; 
	
	/** 
	 * Constructeur par defaut: definit le lien vers le fichier de sauvergarde comme ressources( / | \ )saveData
	 * */
	public DataSaver() {
		this.pathname = "saveData";
	}
	/** 
	 * Constructeur: definit le lien vers le fichier de sauvegarde et son nom
	 * @param pathname String du lien vers le fichier de sauvegarde
	 * */
	public DataSaver(String pathname) {
		this.pathname=pathname;
	}
	/** 
	 * Méthode réalisant la sauvegarde d'un TaskSaver d'apres le lien definit précedement
	 * @param dataSaving TaskSaver a serializer
	 * @param numberExecution Un entier pour la gestion d'erreur dans le cas d'un FileNotFoundException
	 * */
	public void saveData(TaskSaver dataSaving, int numberExecution) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( new File(this.pathname)))){
			if(numberExecution==1) new File(this.pathname).createNewFile();
			oos.writeObject(dataSaving);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			saveData(dataSaving,1);
		}catch (IOException e) {
			System.out.println("IE EXEPTION");
			e.printStackTrace();
			System.out.println("Retry...");
			saveData(dataSaving,2);
		}
	} 
	/** 
	 * Méthode rélisant le chargement ou desérialization a partir du lien definit précedement
	 * @return TaskSaver contenant tout les Taches Globales et sous Tache liées
	 * */
	public TaskSaver loadData() {
		TaskSaver loader = null;
		try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(new File(this.pathname)))){
			 loader = (TaskSaver) ios.readObject();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return loader;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			new File(pathname).delete();
			loadData();
		}catch (InvalidClassException e) {
			e.printStackTrace();
			new File(pathname).delete();
			loadData();
		}catch (IOException e) {
			e.printStackTrace();
			loadData();
		}
		return loader;
	}
}
