package Front;

import java.io.File;
import java.util.List;

import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.TaskSaver;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Refresh {
	
	//Obsolete
	public static VBox refreshContent(Stage primaryStage,TaskSaver allTask,Chrono aChrono) {
		int i = 0;
		load(allTask);
		VBox root = new VBox();
		//Envoyer le root a tous les enfants pour l'update et le recuperer por update le label
		
		
		//root.prefWidthProperty().bind(primaryStage.widthProperty());
		ScrollPane scrollView = new ScrollPane();
		scrollView.setContent(root);
		for(GlobalTask gbtask:allTask.getListAllTask()) {
			System.out.println(gbtask);
			root.getChildren().add(VBoxForATask.createVBoxTask(primaryStage,allTask, gbtask,aChrono,i));
			System.out.println(i);
			i++;
		}
		Button addOne = new Button("add a Task", new ImageView(new Image(HBoxGlobalTask.class.getResourceAsStream(File.separator+"plus.png"),35,35,false,false)));
		root.getChildren().add(addOne);
		addOne.setDisable(aChrono.getIsOn());
		addOne.setOnMouseClicked(e->{
			//WindowCreateTask.createAGlobalTask(primaryStage, allTask, aChrono);
		});
		Scene  scene = new  Scene(scrollView);
		scene.getStylesheets().add(Refresh.class.getResource("/style/styling.css").toExternalForm());
		primaryStage.setScene(scene);
		return root;
	}
	private static void load(TaskSaver allTask) {
		allTask=new DataSaver().loadData();
	}
	
	
	public static void refresContent(Stage primaryStage,VBox root, List<HBoxGlobalTask> allHbox, TaskSaver allTask,Chrono aChrono) {
		int idx=0;
		for(HBoxGlobalTask h : allHbox) {
			h.refresh(allTask.getAGlobalTaskById(idx++), aChrono);
		}
		root.getChildren().clear();
		for(HBoxGlobalTask h : allHbox) {
			root.getChildren().add(h.getVboxAttach());
		}
		Button addOne = new Button("add a Task", new ImageView(new Image(HBoxGlobalTask.class.getResourceAsStream(File.separator+"plus.png"),35,35,false,false)));
		root.getChildren().add(addOne);
		addOne.setDisable(aChrono.getIsOn());
		addOne.setOnMouseClicked(e->{
			WindowCreateTask.createAGlobalTask(allHbox, root, primaryStage, allTask, aChrono);
		});
		
	}
}
