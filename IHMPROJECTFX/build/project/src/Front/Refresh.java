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

/**
 * Une class implementant une methode static mettant un jour un Stage en
 * fonction des paramètres
 */
public class Refresh {

	/**
	 * Méthode static mettant a jour le contenu de la fenetre passer en paramètre en
	 * appelant les methodes refresh de chaque HBox
	 * 
	 * @param primaryStage Stage de la fenetre a mettre a jour
	 * @param root         VBox le widget principale de l'application contenant les
	 *                     autres HBox
	 * @param allHbox      List<HBoxGlobalTask> des HBox par taches globale
	 * @param allTask      TaskSaver toutes les taches globales
	 * @param aChrono      Chrono commun a toute l'application
	 */
	public static void refresContent(Stage primaryStage, VBox root, List<HBoxGlobalTask> allHbox, TaskSaver allTask,
			Chrono aChrono) {
		int idx = 0;
		for (HBoxGlobalTask h : allHbox) {
			h.refresh(allTask.getAGlobalTaskById(idx++), aChrono);
		}
		root.getChildren().clear();
		for (HBoxGlobalTask h : allHbox) {
			root.getChildren().add(h.getVboxAttach());
		}
		Button addOne = new Button("Ajouter une tache",
				new ImageView(new Image(HBoxGlobalTask.class.getResourceAsStream(File.separator + "plusC.png"), 35, 35,
						false, false)));
		root.getChildren().add(addOne);
		addOne.setDisable(aChrono.getIsOn());
		addOne.setOnMouseClicked(e -> {
			WindowCreateTask.createAGlobalTask(allHbox, root, primaryStage, allTask, aChrono);
		});
		addOne.getStyleClass().add("createGlobalTask");

	}
}
