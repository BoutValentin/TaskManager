package Front;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Back.ActualRunningSave;
import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SpecficTime;
import Back.SubTask;
import Back.TaskSaver;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HBoxSubTask {
	private static final int size = 35;
	private static final String resLink = File.separator;
	private static Image start = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "play.png"), size, size,
			false, false);
	private static Image delete = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "trashC.png"), size,
			size, false, false);
	private static Image parameter = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "settingsC2.png"),
			size, size, false, false);
	private static Image pause = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "pauseC.png"), size, size,
			false, false);
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "help.png"), 15, 15, true,
			true);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "error.png"), size, size,
			false, false);
	private static Image save = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "saveC.png"), size, size,
			false, false);
	private static Image cancel = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "closeC.png"), size,
			size, false, false);

	private Chrono chronoOfAll;
	private Stage primaryStage;
	private VBox rootOfAll;
	private HBoxGlobalTask rootHboxGlobale;
	private List<HBoxGlobalTask> allHBoxGlobal;
	private List<HBoxSubTask> allHBoxSub;
	private TaskSaver allGlobalTask;
	private GlobalTask globalTask;
	private SubTask subTask;
	private ActualRunningSave actualRunnigSave;
	private Timer time;
	private HBox root;
	private HBox containAllExpectDelete;
	private Button startButton;
	private Button paramsButton;
	private Button deleteButton;
	private Label taskNameLabel;
	private Label timeTextLabel;
	private Label timingLabel;
	private Region spacer;
	private int indexInGlobalList;

	/**
	 * Constructeur créant une instance de HBoxGlobalTask prenant en paramètre les
	 * ressources necessaires
	 * 
	 * @param actualRunnigSave ActualRunningSave permettant la sauvegarde de la
	 *                         tache en cours pour le system tray
	 * @param allHBoxGlobal    List<HBoxGlobalTask> la list de toutes les HBox dans
	 *                         l'application
	 * @param allHBoxSub       List<HBoxSubTask>> la list de toutes les HBoxSubTask
	 *                         liées a la sous tache principale
	 * @param allGlobalTask    TaskSaver la List de toutes les globalTask de
	 *                         l'application utile pour le merging de sous-tache ou
	 *                         le changement de tache globale pour les sous tache
	 * @param globalTask       GlobalTask la tache mère de la sous-tache
	 * @param task             SubTask la sous-tache liés a cette HBox
	 * @param primaryStage     Stage de l'application principale
	 * @param rootofall        VBox de l'application = le widget contenant toutes
	 *                         l'application
	 * @param rootHboxGlobale  HBoxGlobalTask la HBox de la tache mère de cette
	 *                         sous-tache
	 * @param aChrono          Chrono commun a toutes l'application
	 * @param index            int de la position dans l'application dans la VBox de
	 *                         la tache mère
	 *
	 */
	public HBoxSubTask(ActualRunningSave actualRunnigSave, List<HBoxGlobalTask> allHBoxGlobal,
			List<HBoxSubTask> allHBoxSub, TaskSaver allGlobalTask, GlobalTask globalTask, SubTask task,
			Stage primaryStage, VBox rootofall, HBoxGlobalTask rootHboxGlobale, Chrono aChrono, int index) {
		this.root = new HBox();
		this.containAllExpectDelete = new HBox();
		this.startButton = new Button();
		this.paramsButton = new Button();
		this.deleteButton = new Button();
		this.taskNameLabel = new Label();
		this.timeTextLabel = new Label();
		this.timingLabel = new Label();
		this.spacer = new Region();
		this.indexInGlobalList = index;
		this.primaryStage = primaryStage;
		this.rootOfAll = rootofall;
		this.rootHboxGlobale = rootHboxGlobale;
		this.chronoOfAll = aChrono;
		this.allHBoxGlobal = allHBoxGlobal;
		this.allHBoxSub = allHBoxSub;
		this.allGlobalTask = allGlobalTask;
		this.globalTask = globalTask;
		this.subTask = task;
		this.actualRunnigSave = actualRunnigSave;
	}

	/**
	 * Méthode permettant la mise en place du widget, de ses widgets contenu et de
	 * son style
	 */
	public void setHBox() {
		// Ajoutons le style definit dans le fichier css
		this.root.getStyleClass().addAll("SubHBox", "rootBack");
		this.containAllExpectDelete.getStyleClass().add("encadreSubHbox");
		this.startButton.getStyleClass().addAll("encadreButton");
		this.paramsButton.getStyleClass().add("buttonSub");
		this.deleteButton.getStyleClass().add("buttonSubTrash");
		this.containAllExpectDelete.setStyle(
				"-fx-background-color:" + ((this.rootHboxGlobale.getIndex() + this.indexInGlobalList) % 2 == 0 ? "#ffffff;" : "#ededed;"));
		this.containAllExpectDelete.setMinWidth(1018);
		this.containAllExpectDelete.setMaxWidth(1018);
		HBox.setHgrow(this.spacer, Priority.ALWAYS);
		this.spacer.setPrefWidth(150);
		this.timeTextLabel.getStyleClass().add("textFieldSub");
		this.timingLabel.getStyleClass().add("textFieldSub");
		this.taskNameLabel.getStyleClass().add("textFielNameSub");
		Font f = this.taskNameLabel.getFont();
		this.taskNameLabel.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
		this.startButton.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
		this.paramsButton.getStyleClass().add("buttonParamsAndToogle");
		// Initialisons nos widgets
		this.startButton.setText("Start");
		this.startButton.setGraphic(new ImageView(start));
		this.startButton.setTooltip(new Tooltip("Demarrer le chrono de la sous-taches: " + this.subTask.getName()));
		this.paramsButton.setGraphic(new ImageView(parameter));
		this.paramsButton.setTooltip(new Tooltip("Parametrer la sous-tache: " + this.subTask.getName()));
		this.deleteButton.setGraphic(new ImageView(delete));
		this.deleteButton.setTooltip(new Tooltip("Supprimer la sous-tache" + this.subTask.getName()));
		this.taskNameLabel.setText(this.subTask.getName());
		this.taskNameLabel.setTooltip(new Tooltip("Le nom de la sous-tache"));
		this.timeTextLabel.setText("Temps: ");
		this.timingLabel.setText(HBoxGlobalTask.createTimeLabel(this.subTask));
		this.timingLabel.setTooltip(new Tooltip("Temps passé sur la sous-taches: " + this.subTask.getName()));

		// Initialisons les events
		this.time = new Timer();
		this.startButton.setOnMouseClicked(e -> chooseMethod());
		this.deleteButton.setOnMouseClicked(e -> handleDeleteAction());
		this.paramsButton.setOnMouseClicked(e -> handleParameterClick());
		// Ajoutons le tout dans les containers
		this.root.getChildren().addAll(this.containAllExpectDelete, this.deleteButton);
		this.containAllExpectDelete.getChildren().addAll(this.startButton, this.taskNameLabel, this.spacer,
				this.timeTextLabel, this.timingLabel, this.paramsButton);
	}

	/**
	 * Accesseur de l'attribut du nom de la tache liées a cette HBox
	 * 
	 * @return String le nom de la tache
	 */
	public String getGlobalTaskName() {
		return this.globalTask.getName();
	}

	/**
	 * Accesseur de l'attribut du nom de la sous-tache liées a cette HBox
	 * 
	 * @return String le nom de la sous-tache
	 */
	public String getSubTaskName() {
		return this.subTask.getName();
	}

	/**
	 * Méthode rafraichissant les widgets contenue dans la sous tache en fonction
	 * des attributs de la sous-tache liés
	 */
	public void refresh() {
		if (!this.chronoOfAll.getIsOn()) {
			this.startButton.setDisable(false);
			this.paramsButton.setDisable(false);
			this.deleteButton.setDisable(false);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setText("Start");
			this.startButton.setTooltip(new Tooltip("Demarrer le chrono de la sous-taches: " + this.subTask.getName()));
		} else if (this.chronoOfAll.getIsOn() && this.subTask.getIsChronoOn()) {
			this.startButton.setDisable(false);
			this.paramsButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(pause));
			this.startButton.setText("Pause");
			this.startButton.setTooltip(new Tooltip("Arreter le chrono de la sous-taches: " + this.subTask.getName()));
		} else {
			this.startButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.paramsButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setText("Start");
			this.startButton.setTooltip(new Tooltip("Demarrer le chrono de la sous-taches: " + this.subTask.getName()));
		}

		this.taskNameLabel.setText(this.subTask.getName());
		this.timingLabel.setText(HBoxGlobalTask.createTimeLabel(this.subTask));
		Font f = this.taskNameLabel.getFont();
		this.taskNameLabel.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
		this.startButton.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
	}

	/**
	 * Accesseur de la HBox contenant tout les widgets
	 * 
	 * @return HBox la HBox de cette objets
	 */
	public HBox getHBoxattach() {
		return this.root;
	}

	/**
	 * Méthode appelé au click sur le bouton delete affichant une nouvelle fenetre
	 * demandant la confirmation de la suppression ou d'annuler la demande
	 */
	private void handleDeleteAction() {
		Label aLabel = new Label("Voulez vous vraiment supprimer la sous-tache:\n" + this.subTask.getName() + "\nde :\n"
				+ this.globalTask.getName());
		aLabel.getStyleClass().add("deleteTitle");
		aLabel.setTextAlignment(TextAlignment.CENTER);
		Button yesButton = new Button("Oui je suis sur", new ImageView(delete));
		yesButton.getStyleClass().addAll("buttonActionCreateSub", "boldText");
		Button cancelButton = new Button("Annuler", new ImageView(cancel));
		cancelButton.getStyleClass().add("buttonActionCreateSub");
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(yesButton, cancelButton);
		buttonBox.setSpacing(20);
		buttonBox.getStyleClass().add("deleteButtonBox");
		VBox secondaryLayout = new VBox();
		secondaryLayout.getStyleClass().add("rootCreateSub");
		secondaryLayout.getChildren().addAll(aLabel, buttonBox);
		Scene secondScene = new Scene(secondaryLayout, 490, 200);
		// New window (Stage)
		Stage newWindow = new Stage();
		secondScene.getStylesheets().add(WindowCreateTask.class
				.getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		newWindow.setTitle("Supprimer une Sous Tache");
		newWindow.setScene(secondScene);
		// Specifies the modality for new window.
		newWindow.initModality(Modality.APPLICATION_MODAL);
		// Specifies the owner Window (parent) for new window
		newWindow.initOwner(this.primaryStage);
		// Set position of second window, related to primary window.
		newWindow.setX(primaryStage.getX() + this.primaryStage.getWidth() / 2 - 245);
		newWindow.setY(primaryStage.getY() + this.primaryStage.getHeight() / 2 - 100);
		newWindow.show();
		yesButton.setOnMouseClicked(e -> {
			this.removeTask();
			newWindow.close();
		});
		cancelButton.setOnMouseClicked(e -> newWindow.close());
	}

	/**
	 * Methode appelé au click du bouton confirmer de la fenetre modale supprimant
	 * la sous-tache et rafraichissant l'affichage
	 */
	private void removeTask() {
		globalTask.removeAmountOfTimeByTime(this.subTask.getAmountOfTime());
		globalTask.deleteATaskByTask(this.subTask);
		this.allHBoxSub.remove(this);
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.allHBoxGlobal, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode appelé par la méthode de démarrage du chronomètre Cette méthode met a
	 * jour l'attribut label Time de cette HBox et de la HBox mère toute les
	 * secondes pour pouvoir suivre l'evolution du compteur en temps réél
	 * 
	 */

	private void updateLabelTime(Timer time) {
		time.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					timingLabel.setText(HBoxGlobalTask.createTimeLabel(subTask));
					rootHboxGlobale.getTimeLabel().setText(HBoxGlobalTask.createTimeLabel(globalTask));
				});

			}
		}, 0, 1000);
	}

	/**
	 * Méthode appelé au click du bouton start afin de déterminer si il faut
	 * démarrer ou arreter le chronomètre de la sous-tache et sa tache mère
	 */
	public void chooseMethod() {
		if (!globalTask.getIsChronoOn() && !subTask.getIsChronoOn())
			this.startChrono();
		else
			this.closeChrono();
	}

	/**
	 * Méthode qui permet de démarrer un chronomètre afin d'incrementer le temps
	 * d'une tache et de la sous tache toutes les secondes ainsi que de mettre a
	 * jour les Labels du temps
	 */
	private void startChrono() {
		this.subTask.setIsChronoOn(true);
		this.globalTask.setIsChronoOn(true);
		this.actualRunnigSave.actualOrLastHbox = this.rootHboxGlobale;
		this.actualRunnigSave.actualOrLastSubHbox = this;
		this.actualRunnigSave.lastIsSub = true;
		this.chronoOfAll.doubleStart(this.globalTask.getAmountOfTime(), this.subTask.getAmountOfTime());
		this.time = new Timer();
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.allHBoxGlobal, this.allGlobalTask,
				this.chronoOfAll);
		updateLabelTime(this.time);
	}

	/**
	 * Méthode arretant le chronomètre en cours et rafraichissant la View afin de
	 * pouvoir remettre les boutons dans un etat d'actif
	 */
	private void closeChrono() {
		this.subTask.setIsChronoOn(false);
		this.globalTask.setIsChronoOn(false);
		
		this.chronoOfAll.stop();
		this.time.cancel();
		new DataSaver().saveData(this.allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.allHBoxGlobal, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode appelé au click sur le bouton paramètre mettant en place la fenetre
	 * de paramètrage de la sous-tache
	 */
	private void handleParameterClick() {
		long[] dateValue = new long[] { this.subTask.getAmountOfTime().getYear(),
		this.subTask.getAmountOfTime().getMonth(), this.subTask.getAmountOfTime().getDay(),
		this.subTask.getAmountOfTime().getHours(), this.subTask.getAmountOfTime().getMinutes(),
		this.subTask.getAmountOfTime().getSeconds() };
		GlobalTask[] taskToChanged = new GlobalTask[] { null };
		SubTask[] taskToMerge = new SubTask[] { null };
		Label labelTitle = new Label("Paramètres de la sous-tache");
		labelTitle.getStyleClass().add("paramsTitle");
		Button saveButton = new Button("Sauvegarder", new ImageView(save));
		Button cancelButton = new Button("Annuler", new ImageView(cancel));
		saveButton.getStyleClass().addAll("buttonActionSetting", "boldText");
		cancelButton.getStyleClass().add("buttonActionSetting");
		Label changeNamelabel = new Label("Renommez votre tache :");
		changeNamelabel.getStyleClass().add("parameterName");
		TextField textFielName = new TextField(this.subTask.getName());
		textFielName.setMaxWidth(945);
		VBox.setMargin(textFielName, new Insets(5, 0, 15, 25));
		Label errorLabel = new Label();
		Label changeAmountOfTimeLabel = new Label("Changer le temps passé sur cette tache :");
		changeAmountOfTimeLabel.getStyleClass().add("parameterName");
		ImageView helpIcon = new ImageView(help);
		helpIcon.getStyleClass().add("helpIcon");
		Tooltip.install(helpIcon, new Tooltip(
				"Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel, helpIcon);
		HBox.setMargin(helpIcon, new Insets(20, 5, 0, 5));
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissiser les secondes dans cette zone");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(compteurInList(0, 60));
		secondsComboBox.setPromptText("" + dateValue[5]);
		secondsComboBox.setOnAction((e) -> {
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
			if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissiser les minutes dans cette zone"));
		minutesComboBox.setPromptText("" + dateValue[4]);
		minutesComboBox.setOnAction((e) -> {
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
			if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississer les heures dans cette zone"));
		hoursComboBox.setPromptText("" + dateValue[3]);
		hoursComboBox.setOnAction((e) -> {
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
			if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().addAll(compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississer le nombre de jour dans cette zone"));
		daysComboBox.setPromptText("" + dateValue[2]);
		daysComboBox.setOnAction((e) -> {
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
			if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);

		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(compteurInList(0, 13));
		monthComboBox.setTooltip(new Tooltip("Choississer le nombre de mois dans cette zone"));
		monthComboBox.setPromptText("" + dateValue[1]);
		monthComboBox.setOnAction((e) -> {
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
			if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
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

		Label changeGlobalRootLabel = new Label("Changer la tache globale d'appartenance de la sous tache :");
		changeGlobalRootLabel.getStyleClass().add("parameterName");
		ComboBox<GlobalTask> globalTaskComboBox = new ComboBox<>();
		VBox.setMargin(globalTaskComboBox, new Insets(5, 0, 15, 25));
		globalTaskComboBox.setPromptText(this.globalTask.getName());
		globalTaskComboBox.getItems().addAll(this.allGlobalTask.getListAllTask());
		globalTaskComboBox.setCellFactory(new Callback<ListView<GlobalTask>, ListCell<GlobalTask>>() {
			@Override
			public ListCell<GlobalTask> call(ListView<GlobalTask> param) {
				return new RenderCellGlobaltask();
			}
		});
		globalTaskComboBox.setOnAction(e -> {
			taskToChanged[0] = globalTaskComboBox.getSelectionModel().getSelectedItem().equals(this.globalTask) ? null
					: globalTaskComboBox.getSelectionModel().getSelectedItem();
			if (!taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(this.subTask))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
		});

		Label mergeTwoTask = new Label("Combiner deux sous-taches en une :");
		mergeTwoTask.getStyleClass().add("parameterName");
		ComboBox<SubTask> subTaskComboBox = new ComboBox<SubTask>();
		VBox.setMargin(subTaskComboBox, new Insets(5, 0, 15, 25));
		subTaskComboBox.setPromptText(this.subTask.toString());
		subTaskComboBox.getItems().addAll(this.globalTask.getListOfSubTaskAttach());
		subTaskComboBox.setCellFactory(new Callback<ListView<SubTask>, ListCell<SubTask>>() {
			public ListCell<SubTask> call(ListView<SubTask> param) {
				return new RenderCellSubTask();
			}
		});
		subTaskComboBox.setOnAction(e -> {
			taskToMerge[0] = subTaskComboBox.getSelectionModel().getSelectedItem();
		});

		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxButton.setSpacing(20);

		hboxButton.getStyleClass().add("createButtonBoxSub");
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(labelTitle, changeNamelabel, textFielName, changeAmountOfTimeBox, timeBox,
				changeGlobalRootLabel, globalTaskComboBox, mergeTwoTask, subTaskComboBox, hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue) -> {
			if (newValue == null || newValue.length() == 0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(3, errorLabel);
				errorLabel.setText("Le nom de la tache ne peut etre vide");
				errorLabel.setGraphic(new ImageView(error));
			} else if (taskToChanged[0] != null && !taskToChanged[0].equals(this.globalTask)
					&& taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),
							dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4], dateValue[5])))
				saveButton.setDisable(true);
			else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
				if (rootWindows.getChildren().contains(errorLabel))
					rootWindows.getChildren().remove(errorLabel);
			}

		});
		Scene secondScene = new Scene(rootWindows, 1000, 550);
		rootWindows.getStyleClass().add("rootCreateSub");
		secondScene.getStylesheets().add(WindowCreateTask.class
				.getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		Stage newWindow = new Stage();
		newWindow.setTitle("Modifier une sous tache");
		newWindow.setScene(secondScene);
		newWindow.initModality(Modality.APPLICATION_MODAL);
		newWindow.initOwner(this.primaryStage);
		newWindow.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - 500);
		newWindow.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - 250);
		newWindow.show();
		saveButton.setOnMouseClicked(e -> {
			SpecficTime timeCreate = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3],
					dateValue[4], dateValue[5]);
			System.out.println(timeCreate);
			saveChanges(textFielName.getText(), timeCreate, taskToChanged[0], taskToMerge[0], taskToChanged[0] != null);
			newWindow.close();
		});
		cancelButton.setOnMouseClicked(e -> newWindow.close());
	}

	/**
	 * Méthode appeler au click sur le bouton sauvegarder remettant les taches a
	 * jours et rafraichissant la view ainsi que les actions en cas de merging ou de
	 * changement de tache mère
	 */
	public void saveChanges(String text, SpecficTime time, GlobalTask otherGlobalTask, SubTask subTaskmerge,
			boolean changedRoot) {
		if (subTaskmerge != null && !subTaskmerge.equals(this.subTask) && changedRoot) {
			this.subTask.setName(text);
			this.globalTask.removeAmountOfTimeByTime(this.subTask.getAmountOfTime());
			this.globalTask.removeAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			int pos = this.globalTask.getPositionInList(this.subTask);
			if ( pos != -1) {
				System.out.println("global"+this.globalTask.getListOfSubTaskAttach());
				System.out.println("allaroothbox"+this.rootHboxGlobale.getListHBoxSubAttach());
				if (this.globalTask.getPositionInList(subTaskmerge) != -1) {
					System.out.println("global"+this.globalTask.getListOfSubTaskAttach());
					System.out.println("allaroothbox"+this.rootHboxGlobale.getListHBoxSubAttach());
					this.rootHboxGlobale.getListHBoxSubAttach().remove(this.globalTask.getPositionInList(subTaskmerge));
					this.rootHboxGlobale.getListHBoxSubAttach().remove(pos);
				}
				
			}
			

			this.subTask.setAmontOfTimeByTime(time);
			this.subTask.addAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			otherGlobalTask.addASubtask(this.subTask);
			otherGlobalTask.addAmountOfTimeByTime(this.subTask.getAmountOfTime());
			if (this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask) != -1) {
				HBoxSubTask ansub = new HBoxSubTask(this.actualRunnigSave, this.allHBoxGlobal,
						this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
								.getListHBoxSubAttach(),
						allGlobalTask, otherGlobalTask, subTask, this.primaryStage, this.rootOfAll,
						this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)),
						this.chronoOfAll, this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
								.getListHBoxSubAttach().size());
				ansub.setHBox();
				this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach()
						.add(ansub);
			}
			this.globalTask.deleteATaskByTask(subTaskmerge);
			this.globalTask.deleteATaskByTask(this.subTask);
		} else if (subTaskmerge != null && !subTaskmerge.equals(this.subTask)) {
			this.subTask.setName(text);
			this.globalTask.removeAmountOfTimeByTime(this.subTask.getAmountOfTime());
			this.globalTask.removeAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			this.subTask.setAmontOfTimeByTime(time);
			this.subTask.addAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			if (this.globalTask.getPositionInList(subTaskmerge) != -1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(this.globalTask.getPositionInList(subTaskmerge));
			}
			this.globalTask.deleteATaskByTask(subTaskmerge);
			this.globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		} else if (!changedRoot) {
			this.subTask.setName(text);
			this.globalTask.removeAmountOfTimeByTime(this.subTask.getAmountOfTime());
			this.subTask.setAmontOfTimeByTime(time);
			this.globalTask.addAmountOfTimeByTime(this.subTask.getAmountOfTime());
		} else {
			this.subTask.setName(text);
			this.globalTask.removeAmountOfTimeByTime(this.subTask.getAmountOfTime());
			if (this.globalTask.getPositionInList(this.subTask) != -1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(this.globalTask.getPositionInList(this.subTask));

			}
			//TODO: flknsk
			if (this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask) != -1) {
				System.out.println(this +"actualHbox");
				System.out.println("new HBox"+ this.allHBoxGlobal.get(this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)));
				HBoxSubTask ansub = new HBoxSubTask(this.actualRunnigSave, this.allHBoxGlobal,
						this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
								.getListHBoxSubAttach(),
						allGlobalTask, otherGlobalTask, subTask, this.primaryStage, this.rootOfAll,
						this.allHBoxGlobal.get(this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)),
						this.chronoOfAll,
						this.allHBoxGlobal.get(this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
								.getListHBoxSubAttach().size());
				ansub.setHBox();
				this.allHBoxGlobal.get(this.allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
						.getListHBoxSubAttach().add(ansub);
				System.out.println(this.allHBoxGlobal.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask))
						.getListHBoxSubAttach());
			}
			this.globalTask.deleteATaskByTask(this.subTask);
			this.subTask.setAmontOfTimeByTime(time);
			otherGlobalTask.addASubtask(this.subTask);
			otherGlobalTask.addAmountOfTimeByTime(this.subTask.getAmountOfTime());
		}

		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.allHBoxGlobal, this.allGlobalTask,
				this.chronoOfAll);
	}
	/** 
	 * Méthode renvoyant une List de Long en fonction d'un indice de debut inclu et d'un indice de fin exclu 
	 * @param begin int de la borne inferieur inclu
	 * @param stop int de la borne supérieur exclu
	 * @return List<long> contenant une list de 1 en 1 entre les deux bornes passé en paramètres
	 * */
	protected static List<Long> compteurInList(int begin, int stop) {
		List<Long> aList = new ArrayList<Long>();
		for (long i = begin; i < stop; i++) {
			aList.add(i);
		}
		return aList;
	}
}
