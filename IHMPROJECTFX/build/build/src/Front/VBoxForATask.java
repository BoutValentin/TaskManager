package Front;

import Back.Chrono;
import Back.GlobalTask;
import Back.SubTask;
import Back.TaskSaver;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxForATask {
	private static final int size = 35;
	private static final String resLink = "/";
	private static Image plus = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"plus.png"),size,size,false,false);
	
		public static VBox createVBoxTask(Stage primaryStage,TaskSaver allTask, GlobalTask task,Chrono aChrono,int index) {
			VBox root = new VBox();		
			HBox hboxGlobal = new HBoxGlobalTask().createHBox(primaryStage,root, allTask, task,aChrono,index);
			root.getChildren().add(hboxGlobal);
			if(task.getToggle()) {
				for(SubTask s: task.getListOfSubTaskAttach()) {
					HBox hboxSubTask = new HBoxSubTask().createHBoxSub(primaryStage,root, hboxGlobal,allTask,task, s,aChrono);
					root.getChildren().add(hboxSubTask);
				}		
				Button addOne = new Button("add a Subtask", new ImageView(plus));
				addOne.setDisable(aChrono.getIsOn());
				root.getChildren().add(addOne);
				//addOne.setOnMouseClicked(e ->WindowCreateTask.createASubTask(primaryStage, allTask, task,aChrono));
			}
			return root;
		}
}
