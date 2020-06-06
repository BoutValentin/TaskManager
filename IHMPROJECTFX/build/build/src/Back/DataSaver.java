package Back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataSaver {
	private final String pathname ; 
	
	public DataSaver() {
		this.pathname = "saveData";
	}
	public DataSaver(String pathname) {
		this.pathname=pathname;
	}
	public void saveData(TaskSaver dataSaving, int numberExecution) {
		System.out.println(dataSaving);
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( new File(this.pathname)))){
			if(numberExecution==1) new File(this.pathname).createNewFile();
			oos.writeObject(dataSaving);
			System.out.println("allGoodSave");
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
	public TaskSaver loadData() {
		TaskSaver loader = null;
		try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(new File(this.pathname)))){
			 loader = (TaskSaver) ios.readObject();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return loader;
			//loadData();
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
