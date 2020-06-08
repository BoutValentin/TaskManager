package Front;

import java.io.File;
import java.util.List;

import Back.ActualRunningSave;
import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SpecficTime;
import Back.SubTask;
import Back.TaskSaver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Class static implementant deux methodes afin de creer une tache ou une sous
 * taches
 */
public class WindowCreateTask {
	private static final int size = 35;
	private static final String resLink = "/";
	private static ActualRunningSave actualRunningSave;
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "help.png"), 15, 15, true,
			true);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "error.png"), size, size,
			false, false);
	private static Image cancel = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "closeC.png"), size,
			size, false, false);
	private static Image plus = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "plusC.png"), size, size,
			false, false);

	/**
	 * Mutateurs de l'attribut static de sauvegarde de la derniere tache lancée
	 * 
	 * @param actualRunnigSave ActualRunningSave sauvegardant la derniere tache
	 *                         lancée pour le system tray
	 */
	public static void setActualRunnigSave(ActualRunningSave actualRunnigSave) {
		WindowCreateTask.actualRunningSave = actualRunnigSave;
	}

	/**
	 * Méthode static permettant la création d'une sous-tache appelé au click des
	 * boutons ajouter une sous taches créant une nouvelle fenêtre
	 * 
	 * @param hboxGlobal    HBoxGlobalTask la HBox de la tache globale a laquelle on
	 *                      va creer une sous-tache
	 * @param allHbox       List<HBoxGlobalTask> la liste de toute les HBoxGlobale
	 *                      de l'application
	 * @param allGlobalTask TaskSaver l'objet contenant toute les taches globales
	 * @param globalTask    GlobalTask la tache globale auquel on doit ajouter la
	 *                      sous-tache
	 * @param prStage       Stage de l'application principale
	 * @param root          VBox le widget le plus "haut" dans le hierarchie des
	 *                      widgets = conteneur
	 * @param aChrono       Chrono commun a toute l'application
	 */
	public static void createASubTask(HBoxGlobalTask hboxGlobal, List<HBoxGlobalTask> allHbox, TaskSaver allGlobalTask,
			GlobalTask globalTask, Stage prStage, VBox root, Chrono aChrono) {
		long[] dateValue = new long[] { 0, 0, 0, 0, 0, 0 };
		GlobalTask[] taskToChanged = new GlobalTask[] { globalTask };
		Label titleLabel = new Label("Creer une sous-tache");
		titleLabel.getStyleClass().add("createSubTitle");
		HBoxGlobalTask[] hboxtoChanged = new HBoxGlobalTask[] { hboxGlobal };
		Label changeNamelabel = new Label("Nommer votre tache :");
		changeNamelabel.getStyleClass().add("parameterName");
		TextField textFielName = new TextField("");
		textFielName.setMaxWidth(940);
		VBox.setMargin(textFielName, new Insets(5, 0, 15, 25));
		textFielName.setPromptText("250 caractere maximum");
		Label errorLabel = new Label();
		errorLabel.setText("Le nom de la tache ne peut etre vide");
		errorLabel.setGraphic(new ImageView(error));
		Label changeAmountOfTimeLabel = new Label("Ajouter du temps a cette sous-tache :");
		changeAmountOfTimeLabel.getStyleClass().add("parameterName");
		ImageView helpIcon = new ImageView(help);
		helpIcon.getStyleClass().add("helpIcon");
		Tooltip.install(helpIcon,
				new Tooltip("Ajouter du temps si vous avez deja passé du temps sur cette sous-tache"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel, helpIcon);
		HBox.setMargin(helpIcon, new Insets(20, 5, 0, 5));
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissiser les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		secondsComboBox.setPromptText("" + dateValue[5]);
		secondsComboBox.setOnAction((e) -> {
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissiser les minutes dans cette area"));
		minutesComboBox.setPromptText("" + dateValue[4]);
		minutesComboBox.setOnAction((e) -> {
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississer les heures dans cette area"));
		hoursComboBox.setPromptText("" + dateValue[3]);
		hoursComboBox.setOnAction((e) -> {
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();

		daysComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississer le nombre de jour dans cette area"));
		daysComboBox.setPromptText("" + dateValue[2]);
		daysComboBox.setOnAction((e) -> {
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 13));
		monthComboBox.setTooltip(new Tooltip("Choississer le nombre de mois dans cette area"));
		monthComboBox.setPromptText("" + dateValue[1]);
		monthComboBox.setOnAction((e) -> {
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
		});
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Taper le nombre d'année de votre tache"));
		yearsInput.setPromptText("" + dateValue[0]);
		// Verifionis si pendant l'input on as des chiffres
		yearsInput.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0] = aTest;
				}
			} catch (NullPointerException e) {
				yearsInput.setText("");
			} catch (NumberFormatException e) {
				try {
					yearsInput.setText(oldValue);
				} catch (NullPointerException e2) {
					yearsInput.setText("");
				}
			}
		});

		HBox timeBox = new HBox();
		timeBox.getStyleClass().add("timeBox");
		timeBox.setSpacing(15);
		timeBox.setPadding(new Insets(5, 0, 15, 25));
		timeBox.getChildren().addAll(new Label("Année:"), yearsInput, new Label("Mois:"), monthComboBox,
				new Label("Jour:"), daysComboBox, new Label("Heure:"), hoursComboBox, new Label("Minutes:"),
				minutesComboBox, new Label("Secondes:"), secondsComboBox);
		// Modifiez la root globalTask
		Label changeGlobalRootLabel = new Label("Choississer la tache globale d'appartenance de la sous tache :");
		changeGlobalRootLabel.getStyleClass().add("parameterName");
		ComboBox<GlobalTask> globalTaskComboBox = new ComboBox<>();
		globalTaskComboBox.setPromptText(globalTask.getName());
		globalTaskComboBox.getItems().addAll(allGlobalTask.getListAllTask());
		globalTaskComboBox.setCellFactory(new Callback<ListView<GlobalTask>, ListCell<GlobalTask>>() {
			@Override
			public ListCell<GlobalTask> call(ListView<GlobalTask> param) {
				return new RenderCellGlobaltask();
			}
		});
		globalTaskComboBox.setOnAction(e -> {
			taskToChanged[0] = globalTaskComboBox.getSelectionModel().getSelectedItem().equals(globalTask) ? globalTask
					: globalTaskComboBox.getSelectionModel().getSelectedItem();
			hboxtoChanged[0] = allHbox.get(allGlobalTask.getPositionOfGlobalTask(taskToChanged[0]));
		});
		VBox.setMargin(globalTaskComboBox, new Insets(5, 0, 15, 25));
		Button saveButton = new Button("Creer la sous-tache", new ImageView(plus));
		saveButton.getStyleClass().addAll("buttonActionCreateSub", "boldText");
		saveButton.setDisable(true);
		Button cancelButton = new Button("Annuler", new ImageView(cancel));

		cancelButton.getStyleClass().add("buttonActionCreateSub");

		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxButton.setSpacing(20);

		hboxButton.getStyleClass().add("createButtonBoxSub");
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(titleLabel, changeNamelabel, textFielName, errorLabel, changeAmountOfTimeBox,
				timeBox, changeGlobalRootLabel, globalTaskComboBox, hboxButton);
		rootWindows.getStyleClass().add("rootCreateSub");
		textFielName.textProperty().addListener((observable, oldvalue, newValue) -> {
			System.out.println(oldvalue + "" + newValue);
			if (newValue == null || newValue.length() == 0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(3, errorLabel);

			} else if (newValue.length() > 250) {
				textFielName.setText(oldvalue);
			} else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour creez la Tache"));
				if (rootWindows.getChildren().contains(errorLabel))
					rootWindows.getChildren().remove(errorLabel);
			}
		});
		Scene secondScene = new Scene(rootWindows, 1000, 500);
		secondScene.getStylesheets().add(WindowCreateTask.class
				.getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		Stage newWindow = new Stage();
		newWindow.setTitle("Creer une sous tache");
		newWindow.setScene(secondScene);
		newWindow.initModality(Modality.APPLICATION_MODAL);
		newWindow.initOwner(prStage);
		newWindow.setX(prStage.getX() + prStage.getWidth() / 2 - 550);
		newWindow.setY(prStage.getY() + prStage.getHeight() / 2 - 200);
		newWindow.show();
		saveButton.setOnMouseClicked(e -> {
			SpecficTime timeCreate = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3],
					dateValue[4], dateValue[5]);
			System.out.println(timeCreate);
			saveCreatingSub(hboxtoChanged[0], allHbox, allGlobalTask, taskToChanged[0], prStage, root,
					textFielName.getText(), timeCreate, aChrono);
			newWindow.close();
		});
		cancelButton.setOnMouseClicked(e -> newWindow.close());
	}

	/**
	 * Méthode static permettant la création d'une sous-tache appelé au click du
	 * bouton confirmer de la fenetre de création de sous tache
	 * 
	 * @param hboxGlobal    HBoxGlobalTask la HBox de la tache globale a laquelle on
	 *                      va creer une sous-tache
	 * @param allHbox       List<HBoxGlobalTask> la liste de toute les HBoxGlobale
	 *                      de l'application
	 * @param allGlobalTask TaskSaver l'objet contenant toute les taches globales
	 * @param globalTask    GlobalTask la tache globale auquel on doit ajouter la
	 *                      sous-tache
	 * @param prStage       Stage de l'application principale
	 * @param root          VBox le widget le plus "haut" dans le hierarchie des
	 *                      widgets = conteneur
	 * @param text          String le nom de la sous-tache
	 * @param aTime         SpecficTime le temps a mettre dans la sous-tache
	 * @param aChrono       Chrono commun a toute l'application
	 */
	private static void saveCreatingSub(HBoxGlobalTask hboxGlobal, List<HBoxGlobalTask> allHbox,
			TaskSaver allGlobalTask, GlobalTask globalTask, Stage prStage, VBox root, String text, SpecficTime aTime,
			Chrono aChrono) {
		SubTask subTask = new SubTask(text);
		subTask.setAmontOfTimeByTime(aTime);
		globalTask.addASubtask(subTask);
		globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		if (globalTask.getPositionInList(subTask) != -1) {
			HBoxSubTask anHbox = new HBoxSubTask(WindowCreateTask.actualRunningSave, allHbox,
					hboxGlobal.getListHBoxSubAttach(), allGlobalTask, globalTask, subTask, prStage, root, hboxGlobal,
					aChrono, hboxGlobal.getListHBoxSubAttach().size());
			anHbox.setHBox();
			hboxGlobal.getListHBoxSubAttach().add(anHbox);
		}
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(prStage, root, allHbox, allGlobalTask, aChrono);
	}

	/**
	 * Méthode static permettant la création d'une tache appelé au click des boutons
	 * ajouter une tache créant une nouvelle fenêtre
	 * 
	 * 
	 * @param allHbox   List<HBoxGlobalTask> la liste de toute les HBoxGlobale de
	 *                  l'application
	 * @param rootOfAll VBox le widget le plus "haut" dans le hierarchie des widgets
	 *                  = conteneur
	 * @param primStage Stage de l'application principale
	 * @param allTask   TaskSaver l'objet contenant toute les taches globales
	 * @param aChrono   Chrono commun a toute l'application
	 */
	public static void createAGlobalTask(List<HBoxGlobalTask> allHbox, VBox rootOfAll, Stage primStage,
			TaskSaver allTask, Chrono aChrono) {

		long[] dateValue = new long[] { 0, 0, 0, 0, 0, 0 };
		Label titleCreate = new Label("Creer une tache");
		titleCreate.getStyleClass().add("createTitle");
		Label changeNamelabel = new Label("Nommer votre tache :");
		changeNamelabel.getStyleClass().add("parameterName");
		TextField textFielName = new TextField("");
		textFielName.setMaxWidth(950);
		VBox.setMargin(textFielName, new Insets(5, 0, 15, 25));
		textFielName.setPromptText("250 caracteres maximum");
		Label errorLabel = new Label();
		errorLabel.setText("Le nom de la tache ne peut etre vide");
		errorLabel.setGraphic(new ImageView(error));

		Label changeAmountOfTimeLabel = new Label("Ajouter du temps sur cette tache :");
		changeAmountOfTimeLabel.getStyleClass().add("parameterName");

		ImageView helpIcon = new ImageView(help);
		helpIcon.getStyleClass().add("helpIcon");
		Tooltip.install(helpIcon, new Tooltip(
				"Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		HBox.setMargin(helpIcon, new Insets(20, 5, 0, 5));
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel, helpIcon);
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissiser les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		secondsComboBox.setPromptText("" + dateValue[5]);
		secondsComboBox.setOnAction((e) -> {
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissiser les minutes dans cette area"));
		minutesComboBox.setPromptText("" + dateValue[4]);
		minutesComboBox.setOnAction((e) -> {
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississer les heures dans cette area"));
		hoursComboBox.setPromptText("" + dateValue[3]);
		hoursComboBox.setOnAction((e) -> {
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().add(dateValue[2]);
		daysComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississer le nombre de jour dans cette area"));
		daysComboBox.setPromptText("" + dateValue[2]);
		daysComboBox.setOnAction((e) -> {
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 13));
		monthComboBox.setTooltip(new Tooltip("Choississer le nombre de mois dans cette area"));
		monthComboBox.setPromptText("" + dateValue[1]);
		monthComboBox.setOnAction((e) -> {
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
		});
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Taper le nombre d'année de votre tache"));
		yearsInput.setPromptText("" + dateValue[0]);
		// Verifionis si pendant l'input on as que des chiffres
		yearsInput.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0] = aTest;
				}
			} catch (NullPointerException e) {
				yearsInput.setText("");
			} catch (NumberFormatException e) {
				try {
					yearsInput.setText(oldValue);
				} catch (NullPointerException e2) {
					yearsInput.setText("");
				}
			}
		});

		HBox timeBox = new HBox();
		timeBox.getStyleClass().add("timeBox");
		timeBox.setSpacing(15);
		timeBox.setPadding(new Insets(5, 0, 15, 25));
		timeBox.getChildren().addAll(new Label("Année:"), yearsInput, new Label("Mois:"), monthComboBox,
				new Label("Jour:"), daysComboBox, new Label("Heure:"), hoursComboBox, new Label("Minutes:"),
				minutesComboBox, new Label("Secondes:"), secondsComboBox);
		// Modifiez la root globalTask

		Button saveButton = new Button("Creer la tache", new ImageView(plus));
		saveButton.setDisable(true);
		Button cancelButton = new Button("Annuler", new ImageView(cancel));
		saveButton.getStyleClass().addAll("buttonActionCreateSub", "boldText");
		cancelButton.getStyleClass().add("buttonActionCreateSub");

		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxButton.setSpacing(20);
		hboxButton.getStyleClass().add("createButtonBox");

		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(titleCreate, changeNamelabel, textFielName, errorLabel, changeAmountOfTimeBox,
				timeBox, hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue) -> {
			System.out.println(oldvalue + "" + newValue);
			if (newValue == null || newValue.length() == 0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(3, errorLabel);

			} else if (newValue.length() > 255) {
				textFielName.setText(oldvalue);
			} else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour creer la Tache"));
				if (rootWindows.getChildren().contains(errorLabel))
					rootWindows.getChildren().remove(errorLabel);
			}
		});
		rootWindows.getStyleClass().add("rootCreateSub");
		Scene secondScene = new Scene(rootWindows, 1000, 400);
		secondScene.getStylesheets().add(WindowCreateTask.class
				.getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Creez une tache");
		newWindow.setScene(secondScene);
		// Specifies the modality for new window.
		newWindow.initModality(Modality.APPLICATION_MODAL);
		// Specifies the owner Window (parent) for new window
		newWindow.initOwner(primStage);
		// Set position of second window, related to primary window.
		newWindow.setX(primStage.getX() + primStage.getWidth() / 2 - 500);
		newWindow.setY(primStage.getY() + primStage.getHeight() / 2 - 200);
		newWindow.show();
		saveButton.setOnMouseClicked(e -> {
			SpecficTime timeCreate = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3],
					dateValue[4], dateValue[5]);
			System.out.println(timeCreate);
			saveCreatingGlobal(allHbox, rootOfAll, primStage, allTask, textFielName.getText(), timeCreate, aChrono);
			newWindow.close();
		});
		cancelButton.setOnMouseClicked(e -> newWindow.close());
	}

	/**
	 * Méthode static permettant la création d'une tache appelé au click du boutons
	 * ajouter une tache de la fenetre de création
	 * 
	 * 
	 * @param allHbox   List<HBoxGlobalTask> la liste de toute les HBoxGlobale de
	 *                  l'application
	 * @param rootOfAll VBox le widget le plus "haut" dans le hierarchie des widgets
	 *                  = conteneur
	 * @param prStage   Stage de l'application principale
	 * @param allTask   TaskSaver l'objet contenant toute les taches globales
	 * @param text      String du nom de la tache à creer
	 * @param aTime     SpecficTime le temps a mettre dans la tache créé
	 * @param aChrono   Chrono commun a toute l'application
	 */
	public static void saveCreatingGlobal(List<HBoxGlobalTask> allHbox, VBox rootOfAll, Stage prStage,
			TaskSaver allTask, String text, SpecficTime aTime, Chrono aChrono) {
		GlobalTask newGlobalTask = new GlobalTask(text);
		newGlobalTask.setAmontOfTimeByTime(aTime);
		allTask.addAGlobalTask(newGlobalTask);
		HBoxGlobalTask anHbox = new HBoxGlobalTask(WindowCreateTask.actualRunningSave, allTask, newGlobalTask, allHbox,
				prStage, rootOfAll, aChrono, allHbox.size());
		anHbox.setVBox();
		allHbox.add(anHbox);
		new DataSaver().saveData(allTask, 0);
		Refresh.refresContent(prStage, rootOfAll, allHbox, allTask, aChrono);
	}
}
