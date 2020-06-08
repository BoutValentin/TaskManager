package application;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import Back.ActualRunningSave;
import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SubTask;
import Back.TaskSaver;
import Front.HBoxGlobalTask;
import Front.WindowCreateTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	private static final String iconPath = File.separator + "task.png";
	private Stage stage;
	private Timer notificationTimer = new Timer();
	private TaskSaver allTask;
	private Chrono aChrono;
	private ActualRunningSave actualRunnigSave;
	private TrayIcon icontray;
	public void start(Stage stage) {
		this.stage = stage;
		this.stage.getIcons().add(new Image(iconPath));
		Platform.setImplicitExit(false);
		javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
			@Override
			public Void call() {
				addAppToTray();
				return null;
			}
		};
		Image img = new Image(iconPath, 65, 65, true, false);
		HBox appNameLabel = new HBox();
		Label appNameLabel2 = new Label("Task Manager", new ImageView(img));
		appNameLabel2.getStyleClass().add("appName");
		appNameLabel.getChildren().add(appNameLabel2);
		new Thread(task).start();
		List<HBoxGlobalTask> containALl = new ArrayList<>();
		this.aChrono = new Chrono();
		GlobalTask task1 = new GlobalTask("my firs task2", 922337200);
		GlobalTask task2 = new GlobalTask("my firs task3", 85);
		TaskSaver allTasker = loadData();
		actualRunnigSave = new ActualRunningSave();
		WindowCreateTask.setActualRunnigSave(actualRunnigSave);
		if (allTasker == null) {
			allTasker = new TaskSaver();
			SubTask aSubTask1 = new SubTask("toto");
			SubTask aSubTask2 = new SubTask("michel");
			SubTask aSubTask3 = new SubTask("aandre");
			SubTask aSubTask4 = new SubTask("bernard");
			SubTask aSubTask5 = new SubTask("beloche");
			task1.addASubtask(aSubTask1);
			task1.addASubtask(aSubTask2);
			task1.addASubtask(aSubTask3);
			task1.addASubtask(aSubTask4);
			task2.addASubtask(aSubTask5);
			allTasker.addAGlobalTask(task1);
			allTasker.addAGlobalTask(task2);
		}
		this.allTask = allTasker;
		int idx = 0;
		GridPane gridPane = new GridPane();
		ColumnConstraints column0 = new ColumnConstraints(0);
		ColumnConstraints column1 = new ColumnConstraints(1280);
		ColumnConstraints column3 = new ColumnConstraints(0);
		ScrollPane scrollView = new ScrollPane();
		VBox root = new VBox();
		GridPane.setConstraints(scrollView, 1, 0);
		GridPane.setConstraints(appNameLabel, 0, 0);
		gridPane.getStyleClass().add("rootBack");
		scrollView.getStyleClass().add("rootBack");
		root.getStyleClass().addAll("rootBack", "rootVboxPad");
		gridPane.getChildren().addAll(appNameLabel, scrollView);
		appNameLabel.setAlignment(Pos.TOP_CENTER);
		gridPane.getColumnConstraints().addAll(column0, column1, column3);
		System.out.println(allTask.getListAllTask());
		for (GlobalTask t : allTask.getListAllTask()) {
			HBoxGlobalTask anHBoxGlobal = new HBoxGlobalTask(actualRunnigSave, allTask, t, containALl, stage, root,
					aChrono, idx);
			anHBoxGlobal.setVBox();
			containALl.add(anHBoxGlobal);
			idx++;
		}
		for (HBoxGlobalTask h : containALl) {
			root.getChildren().add(h.getVboxAttach());
		}
		Button addOne = new Button("Ajouter une tache",
				new ImageView(new Image(HBoxGlobalTask.class.getResourceAsStream(File.separator + "plusC.png"), 35, 35,
						false, false)));
		root.getChildren().add(addOne);
		addOne.getStyleClass().add("createGlobalTask");
		addOne.setOnMouseClicked(e -> WindowCreateTask.createAGlobalTask(containALl, root, stage, allTask, aChrono));
		scrollView.setContent(root);
		Scene scene = new Scene(gridPane, Color.valueOf("#242424"));
		// scene.setFill(Paint.valueOf("#242424"));
		scene.getStylesheets().add(
				getClass().getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		stage.setTitle("Task Manager");
		stage.setScene(scene);
		stage.show();
		this.stage.widthProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue.intValue() > 1300) {
				int delta = newvalue.intValue() - 1255;
				column0.setPrefWidth((int) delta / 2);
				column3.setPrefWidth((int) delta / 2);
			} else {
				column0.setPrefWidth(0);
				column3.setPrefWidth(0);
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				System.out.println(allTask);
				stage.hide();
				if (!SystemTray.isSupported()) {
					System.out.println("pas de systeme tray suport");
					stop(stage, "resources/saveData", allTask);

				}
			}
		});
	}

	/**
	 * Met en place le systeme Tray pour l'application si il est supporté et les
	 * events liées a ce System Tray
	 */
	private void addAppToTray() {
		try {
			System.out.println("hello");
			Toolkit.getDefaultToolkit();
			if (!SystemTray.isSupported()) {
				System.out.println("pas de systeme tray suport");
				Platform.exit();
			}
			SystemTray systemTray = SystemTray.getSystemTray();
			URL imageURl = this.getClass().getResource(iconPath);
			java.awt.Image image = ImageIO.read(imageURl);
			TrayIcon trayIcon = new TrayIcon(image);
			this.icontray = trayIcon;
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(e -> Platform.runLater(this::showStage));

			System.out.println("lol");
			java.awt.MenuItem openItem = new java.awt.MenuItem("Task Manager");
			openItem.addActionListener(e -> Platform.runLater(this::showStage));
			openItem.setFont(Font.decode(null).deriveFont(Font.BOLD));
			java.awt.MenuItem restartLastTask = new java.awt.MenuItem("Redemarrer/Arreter la dernière tache");
			restartLastTask.addActionListener(e -> Platform.runLater(new Runnable() {
				@Override
				public void run() {
					handleChoose(restartLastTask);
				}
			}));
			java.awt.MenuItem closeItem = new java.awt.MenuItem("Fermer l'application");
			closeItem.addActionListener(e -> {
				stop(this.stage, "resources/saveData", this.allTask);
			});
			this.notificationTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					if (aChrono.getIsOn())
						trayIcon.displayMessage("Task Manager est Actif",
								"Vous travaillez sur " + (actualRunnigSave.lastIsSub
										? actualRunnigSave.actualOrLastSubHbox.getSubTaskName()
										: actualRunnigSave.actualOrLastHbox.getTaskName()),
								MessageType.INFO);
					trayIcon.displayMessage("Task Manager est Inactif", "Aucune tache lancée", MessageType.INFO);
				}
			}, 5000, 150000);
			PopupMenu popup = new PopupMenu();
			popup.add(openItem);
			popup.addSeparator();
			popup.add(restartLastTask);
			popup.addSeparator();
			popup.add(closeItem);
			trayIcon.setPopupMenu(popup);
			systemTray.add(trayIcon);
		} catch (AWTException | IOException e) {
			System.out.println("system tray not init");
			e.printStackTrace();
		}
	}

	/**
	 * Implemente le choix de la methode a utilisé en fonction du type de Tache
	 * dernierement joué ou le cas echant affiche une alerte Box si aucune tache n'a
	 * encore etait démarée
	 * 
	 * @params anItem un MenuItem (de java.awt) a mettre a jour en fonction de
	 *         l'etat du Chrono actif ou non
	 */
	public void handleChoose(java.awt.MenuItem anItem) {
		if (actualRunnigSave.actualOrLastHbox == null) {
			Alert alert = new Alert(AlertType.WARNING, "Aucune tache n'a encore etait demarré", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
		} else if (actualRunnigSave.lastIsSub) {
			anItem.setEnabled(true);
			actualRunnigSave.actualOrLastSubHbox.chooseMethod();
			if (aChrono.getIsOn())
				anItem.setLabel("Arreter la sous-tache en cours");
			else
				anItem.setLabel("Reprendre la sous-tache en cours");
		} else {
			anItem.setEnabled(true);
			actualRunnigSave.actualOrLastHbox.chooseMethod();
			if (aChrono.getIsOn())
				anItem.setLabel("Arreter la tache en cours");
			else
				anItem.setLabel("Reprendre la tache en cours");
		}
	}

	/**
	 * Implemente l'arret de l'application afin de sauvegarder toutes les taches en
	 * serilization et ou d'afficher une alerte box si une tache est encore en cours
	 * d'utilisation
	 * 
	 * @param stage    Le Stage de l'application a fermer
	 * @param pathname Un String du chemin de sauvergarde des ressources
	 * @param data     Un TaskSaver contenant toutes les taches et sous-taches
	 */
	public void stop(Stage stage, String pathname, TaskSaver data) {
		if (aChrono.getIsOn()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = new Alert(AlertType.WARNING, "La tache"
							+ (actualRunnigSave.lastIsSub ? actualRunnigSave.actualOrLastSubHbox.getSubTaskName()
									: actualRunnigSave.actualOrLastHbox.getTaskName())
							+ "est encore en cours! Arreter la avant de quitter", ButtonType.OK);
					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
					alert.show();
				}
			});
			return;
		}
		for (GlobalTask g : this.allTask.getListAllTask()) {
			for (SubTask s : g.getListOfSubTaskAttach()) {
				s.setIsChronoOn(false);
			}
			g.setIsChronoOn(false);
		}
		this.saveData(data, 0);
		if(SystemTray.isSupported())SystemTray.getSystemTray().remove(icontray);
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Implemente le chargement des données dans le fichier de serialization
	 * 
	 * @return Un TaskSaver contenant toutes les taches et sous-taches associées
	 */
	public TaskSaver loadData() {
		DataSaver loader = new DataSaver();
		TaskSaver task = loader.loadData();
		return task;
	}

	/**
	 * Implemente la sauvegarde des données dans le fichier de serialization avec le
	 * lien par defaut
	 * 
	 * @param dataSave        Un objet TaskSaver a sauvegarder dans le fichier de
	 *                        serialization
	 * @param numberExecution un entier indiquant le nombre de fois ou cette
	 *                        fonction a ete executée afin de gerer les erreurs
	 * 
	 */
	public void saveData(TaskSaver dataSave, int numberExecution) {
		DataSaver saver = new DataSaver();
		saver.saveData(dataSave, numberExecution);
	}

	/**
	 * Affiche le stage principale dans le cas ou celui n'est pas null et l'affiche
	 * devant tout
	 */
	private void showStage() {
		if (this.stage != null) {
			stage.show();
			stage.toFront();
		}
	}

	public static void main(String[] args) throws IOException, AWTException {
		Application.launch(args);
	}
}
