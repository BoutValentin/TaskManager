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
import Back.Task;
import Back.TaskSaver;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

/**
 * Class réalisant l'implémentation d'une Hbox et d'une tache globale avec ses
 * sous taches attachées et ses représentations
 */
public class HBoxGlobalTask {

	// Image commune a toutes les instanciations des HBoxGlobalTask
	private static final int size = 35;
	private static final String resLink = File.separator;
	private static Image start = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "play.png"), size, size,
			false, false);
	private static Image downArrow = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "downArrow.png"),
			size, size, false, false);
	private static Image upArrow = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "upArrow.png"), size,
			size, false, false);
	private static Image delete = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "trashC.png"), size,
			size, false, false);
	private static Image parameter = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "settingsC2.png"),
			size, size, false, false);
	private static Image pause = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "pauseC.png"), size, size,
			false, false);
	private static Image plus = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "plusC.png"), size, size,
			false, false);
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "help.png"), 15, 15, true,
			true);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "error.png"), size, size,
			false, false);
	private static Image save = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "saveC.png"), size, size,
			false, false);
	private static Image cancel = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink + "closeC.png"), size,
			size, false, false);

	// Attribut Global Commun a tous les HboxGlobale
	private Chrono chronoOfAll;
	private Stage primaryStage;
	private VBox rootOfAll;
	private TaskSaver allGlobalTask;
	private GlobalTask globalTask;
	private List<HBoxGlobalTask> containHboxGLobale;
	private Timer time;
	private ActualRunningSave actualRunningSave;

	// Attributs liés a HBox definit
	private VBox root;
	private HBox containAll;
	private HBox containExeptDelete;
	private Button startButton;
	private Button parameterButton;
	private Button toggleButton;
	private Button deleteButton;
	private Button addButton;
	private Region spacer;
	private Label taskNameLabel;
	private Label underTaskLabel;
	private Label timeTextLabel;
	private Label labelTheTime;
	private List<HBoxSubTask> children;
	private int indexInSave;

	/**
	 * Constructeur créant une instance de HBoxGlobalTask prenant en paramètre les
	 * ressources necessaires
	 * 
	 * @param actualRunningSave  ActualRunnningSave permettant la sauvegarde de la
	 *                           tache en cours pour le system tray
	 * @param allGlobalTask      TaskSaver la List de toutes les globalTask de
	 *                           l'application utile pour le merging de sous-tache
	 *                           ou le changement de tache globale pour les sous
	 *                           tache
	 * @param task               GlobalTask la tache Globale en relation avec cette
	 *                           HBox
	 * @param containHboxGLobale List<HBoxGlobalTask> la list de toutes les
	 *                           HboxGlobales de l'application
	 * @param primaryStage       Stage le stage principal de l'application utile
	 *                           pour les fenetres modal
	 * @param rootofall          VBox contenant toute les widgets de l'application
	 *                           aka Widget de plus haut niveau
	 * @param aChrono            Chrono le chrono que l'application a en commun
	 * @param index              int la position dans la list de HBoxGlobaleTask
	 */
	public HBoxGlobalTask(ActualRunningSave actualRunningSave, TaskSaver allGlobalTask, GlobalTask task,
			List<HBoxGlobalTask> containHboxGLobale, Stage primaryStage, VBox rootofall, Chrono aChrono, int index) {
		this.root = new VBox();
		this.containAll = new HBox();
		this.containExeptDelete = new HBox();
		this.startButton = new Button();
		this.parameterButton = new Button();
		this.toggleButton = new Button();
		this.deleteButton = new Button();
		this.addButton = new Button();
		this.taskNameLabel = new Label();
		this.underTaskLabel = new Label();
		this.timeTextLabel = new Label();
		this.labelTheTime = new Label();
		this.spacer = new Region();
		this.children = new ArrayList<HBoxSubTask>();
		this.indexInSave = index;
		this.primaryStage = primaryStage;
		this.rootOfAll = rootofall;
		this.chronoOfAll = aChrono;
		this.allGlobalTask = allGlobalTask;
		this.globalTask = task;
		this.containHboxGLobale = containHboxGLobale;
		this.actualRunningSave = actualRunningSave;
	}

	/**
	 * Accesseur de l'attribut List<HBoxSubTask>
	 * 
	 * @return List<HBoxSubTask>
	 */
	public List<HBoxSubTask> getListHBoxSubAttach() {
		return this.children;
	}

	/**
	 * Accesseur de l'attribut Label pour le temps
	 * 
	 * @return Label label du temps
	 */
	public Label getTimeLabel() {
		return this.labelTheTime;
	}

	/**
	 * Accesseur de l'attribut name de la tache globale
	 * 
	 * @return String le nom de la tache globale liée a la HBox
	 */
	public String getTaskName() {
		return this.globalTask.getName();
	}

	/**
	 * Accesseur de l'attribut index
	 * 
	 * @return int position de la HBox dans l'application
	 */
	public int getIndex() {
		return this.indexInSave;
	}

	/**
	 * Méthode mettant en place les widgets contenue dans la HBox ainsi que le style
	 * de la HBox
	 */
	public void setVBox() {
		// Ajoutons le style aux widgets
		this.containAll.getStyleClass().addAll("GlobalHBox", "rootBack");
		this.containExeptDelete.getStyleClass().add("encadreHBox");
		this.containExeptDelete
				.setStyle("-fx-background-color:" + (this.indexInSave % 2 == 0 ? "#ededed;" : "#ffffff;"));
		this.startButton.getStyleClass().add("encadreButton");
		this.deleteButton.getStyleClass().add("deleteButtonGlobale");
		this.addButton.getStyleClass().add("addSubPadding");
		this.timeTextLabel.getStyleClass().add("timeFielGlobale");
		this.labelTheTime.getStyleClass().add("textFieldGlobale");
		this.taskNameLabel.getStyleClass().addAll("textFieldGlobale", "boldText");
		this.underTaskLabel.getStyleClass().add("textFieldGlobale");
		this.containExeptDelete.setMinWidth(1190); // On definit la taille minimum de l'hbox sans le button delete
		this.containExeptDelete.setPrefWidth(1190);
		Font f = this.taskNameLabel.getFont();
		this.taskNameLabel.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
		this.startButton.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
		HBox.setHgrow(this.spacer, Priority.ALWAYS); // On definit la priorite au spacer afin que les boutons parametres
														// etc... soit toujours sur la droite

		HBox.setHgrow(this.startButton, Priority.ALWAYS);
		this.parameterButton.getStyleClass().add("buttonParamsAndToogle");
		this.toggleButton.getStyleClass().add("buttonParamsAndToogle");
		// Initialisation
		this.startButton.setText("Start");
		this.startButton.setGraphic(new ImageView(start));
		this.startButton.setTooltip(new Tooltip("Demarrer la tache: " + this.globalTask.getName()));
		this.parameterButton.setGraphic(new ImageView(parameter));
		this.parameterButton.setTooltip(new Tooltip("Parametrer la tache: " + this.globalTask.getName()));
		this.deleteButton.setGraphic(new ImageView(delete));
		this.deleteButton.setTooltip(new Tooltip("Supprimer la tache: " + this.globalTask.getName()));
		this.toggleButton.setGraphic(new ImageView(this.globalTask.getToggle() ? upArrow : downArrow));
		this.toggleButton.setTooltip(new Tooltip("Afficher les sous-taches liées à: " + this.globalTask.getName()));
		this.taskNameLabel.setText(this.globalTask.getName());
		this.taskNameLabel.setTooltip(new Tooltip("Nom de la tache"));
		this.underTaskLabel.setText("" + this.globalTask.getNumberOfUnderTask() + " sous-taches");
		this.underTaskLabel.setTooltip(new Tooltip("Nombre de sous-taches liès a :" + this.globalTask.getName()));
		this.timeTextLabel.setText("Temps : ");
		this.labelTheTime.setText(createTimeLabel(this.globalTask));
		this.labelTheTime.setTooltip(new Tooltip("Temps passé sur cette tache globale"));
		this.addButton.setTooltip(new Tooltip("Creer une tache"));
		// Pour chaque sous tache on creer Une HboxSub que l'on ajoute a la List
		// contenant ses HboxSub par tache
		int idx = 0;
		for (SubTask s : this.globalTask.getListOfSubTaskAttach()) {
			HBoxSubTask anHboxSub = new HBoxSubTask(this.actualRunningSave, containHboxGLobale, this.children,
					allGlobalTask, this.globalTask, s, this.primaryStage, this.rootOfAll, this, this.chronoOfAll,
					idx++);
			anHboxSub.setHBox();
			this.children.add(anHboxSub);
		}
		this.addButton.setText("Creer une sous-taches");
		;
		this.addButton.setGraphic(new ImageView(plus));
		// Event
		this.time = new Timer();
		this.deleteButton.setOnMouseClicked(e -> handleDelete());
		this.toggleButton.setOnMouseClicked(e -> handleShowMore());
		this.startButton.setOnMouseClicked(e -> chooseMethod());
		this.parameterButton.setOnMouseClicked(e -> handleParameterAction());
		// on appelle la methode static de creation d'une sous tache au click sur le
		// bouton
		this.addButton.setOnMouseClicked(e -> WindowCreateTask.createASubTask(this, this.containHboxGLobale,
				this.allGlobalTask, this.globalTask, this.primaryStage, this.rootOfAll, this.chronoOfAll));

		// Ajouter le tout a la VBox
		this.root.getChildren().add(this.containAll);
		this.containAll.getChildren().addAll(this.containExeptDelete, this.deleteButton);
		this.containExeptDelete.getChildren().addAll(this.startButton, this.taskNameLabel, this.spacer,
				this.underTaskLabel, this.timeTextLabel, this.labelTheTime, this.parameterButton, this.toggleButton);
		// Si l'attribu de globalTask toggle est a true cela signifie que l'on souhaite
		// afficher les sous tache liés alors on les ajoute a la Vbox Globale par tache
		if (this.globalTask.getToggle()) {
			for (HBoxSubTask h : this.children) {
				this.root.getChildren().add(h.getHBoxattach());
			}
			// On ajoute le bouton creer une sous tache
			this.root.getChildren().add(this.addButton);
		}
	}

	/**
	 * Méthode rafraichissant les widgets contenue dans la tache et les HBox
	 * des sous taches liées en fonction des attributs des taches et sous taches
	 * liés
	 */
	public void refresh(GlobalTask task, Chrono aChrono) {
		if (!aChrono.getIsOn()) {
			this.startButton.setDisable(false);
			this.parameterButton.setDisable(false);
			this.deleteButton.setDisable(false);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setTooltip(new Tooltip("Demarrer la tache: " + this.globalTask.getName()));
			this.startButton.setText("Start");
		} else if (aChrono.getIsOn() && task.getIsChronoOn()) {
			this.startButton.setDisable(false);
			this.parameterButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(pause));
			this.startButton.setTooltip(new Tooltip("Arreter la tache: " + this.globalTask.getName()));
			this.startButton.setText("Pause");
		} else {
			this.startButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.parameterButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setTooltip(new Tooltip("Demarrer la tache: " + this.globalTask.getName()));
			this.startButton.setText("Start");
		}

		this.taskNameLabel.setText(task.getName());
		this.underTaskLabel.setText("" + task.getNumberOfUnderTask() + " sous-taches");
		this.labelTheTime.setText(createTimeLabel(task));
		this.toggleButton.setGraphic(new ImageView(task.getToggle() ? upArrow : downArrow));

		int idx = 0;
		for (HBoxSubTask hbSt : this.children) {
			hbSt.refresh();
		}

		this.root.getChildren().clear();
		this.root.getChildren().add(this.containAll);

		idx = 0;
		if (task.getToggle()) {
			System.out.println(this.children);
			for (HBoxSubTask anHboxSub : this.children) {
				this.root.getChildren().add(anHboxSub.getHBoxattach());
			}
			this.root.getChildren().add(this.addButton);
		}
		Font f = this.taskNameLabel.getFont();
		this.taskNameLabel.setFont(Font.font(f.getFamily(), FontWeight.BOLD, 15));
	}

	public VBox getVboxAttach() {
		return this.root;
	}

	/**
	 * Méthode appelé au click sur le bouton delete affichant une nouvelle fenetre
	 * demandant la confirmation de la suppression ou d'annuler la demande
	 */
	private void handleDelete() {
		VBox windowsRoot = new VBox();
		Label textLabel = new Label("Voulez vous vraiment supprimer la tache :\n" + this.globalTask.getName()
				+ "\net ses sous-taches attachées ?");
		textLabel.getStyleClass().add("deleteTitleGlobale");
		textLabel.setTextAlignment(TextAlignment.CENTER);
		HBox containerButton = new HBox();
		Button valider = new Button("J'en suis sur !", new ImageView(delete));
		valider.getStyleClass().add("boldText");
		valider.getStyleClass().add("buttonActionCreateSub");
		Button annuler = new Button("Non! annuler", new ImageView(cancel));
		annuler.getStyleClass().add("buttonActionCreateSub");
		containerButton.getChildren().addAll(valider, annuler);
		containerButton.setSpacing(20);
		containerButton.getStyleClass().add("deleteButtonBox");
		windowsRoot.getChildren().addAll(textLabel, containerButton);
		windowsRoot.getStyleClass().add("rootCreateSub");
		Scene windowsScene = new Scene(windowsRoot, 490, 180);
		windowsScene.getStylesheets().add(WindowCreateTask.class
				.getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		Stage newWindow = new Stage();
		newWindow.setTitle("Supprimer une tache");
		newWindow.setScene(windowsScene);
		newWindow.initModality(Modality.APPLICATION_MODAL);
		newWindow.initOwner(primaryStage);
		newWindow.setX(primaryStage.getX() + this.primaryStage.getWidth() / 2 - 245);
		newWindow.setY(primaryStage.getY() + this.primaryStage.getHeight() / 2 - 100);
		newWindow.show();
		valider.setOnMouseClicked(e -> {
			this.deleteTask();
			newWindow.close();
		});
		annuler.setOnMouseClicked(e -> {
			newWindow.close();
		});
	}

	/**
	 * Methode appelé au click du bouton confirmer de la fenetre modale supprimant
	 * la tache et ses sous-taches et rafraichissant l'affichage
	 */
	private void deleteTask() {
		this.globalTask.clearAll();
		allGlobalTask.deleteAGlobalTaskByGlobalTask(this.globalTask);
		new DataSaver().saveData(allGlobalTask, 0);
		this.containHboxGLobale.remove(this);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.containHboxGLobale, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode appelé au click du bouton afficher ou non activant la vue des sous
	 * taches ou les retirant de la vue
	 */
	private void handleShowMore() {
		this.globalTask.setToggleOrUnToggleViewSubTask();
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.containHboxGLobale, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode appelé par la méthode de démarrage du chronomètre Cette méthode met a
	 * jour l'attribut label Time toute les secondes pour pouvoir suivre l'evolution
	 * du compteur en temps réél
	 * 
	 */
	private void updateLabelTime(Timer time) {
		time.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> {
					labelTheTime.setText(createTimeLabel(globalTask));
				});

			}
		}, 0, 1000);
	}

	/**
	 * Méthode prenant en parametre une task et retournant l'affichage en fonction
	 * de la précision
	 * 
	 * @param task Task la tache avec le temps a affiché
	 * @return String le temps avec sa précision maximale
	 */
	protected static String createTimeLabel(Task task) {
		switch (task.precisionToShow()) {
		case 2:
			return "" + task.getAmountOfTime().getMinutes() + " minutes " + task.getAmountOfTime().getSeconds()
					+ " secondes";
		case 3:
			return "" + task.getAmountOfTime().getHours() + " heures " + task.getAmountOfTime().getMinutes()
					+ " minutes " + task.getAmountOfTime().getSeconds()+ " secondes";
		case 4:
			return "" + task.getAmountOfTime().getDay() + " jours " + task.getAmountOfTime().getHours() + " heures "
					+ task.getAmountOfTime().getMinutes() + " minutes " + task.getAmountOfTime().getSeconds()
					+ " secondes";
		case 5:
			return "" + task.getAmountOfTime().getMonth() + " mois " + task.getAmountOfTime().getDay() + " jours "
					+ task.getAmountOfTime().getHours() + " heures " + task.getAmountOfTime().getMinutes() + " minutes "
					+ task.getAmountOfTime().getSeconds() + " secondes";
		case 6:
			return "" + task.getAmountOfTime().getYear() + " année " + task.getAmountOfTime().getMonth() + " mois "
					+ task.getAmountOfTime().getDay() + " jours " + task.getAmountOfTime().getHours() + " heures "
					+ task.getAmountOfTime().getMinutes() + " minutes " + task.getAmountOfTime().getSeconds()
					+ " secondes";

		default:
			break;
		}
		return "";
	}

	/**
	 * Méthode appelé au click du bouton start afin de déterminer si il faut
	 * démarrer ou arreter le chronomètre de la tache
	 */
	public void chooseMethod() {
		if (!this.globalTask.getIsChronoOn())
			this.startChrono();
		else
			this.closeChrono();
	}

	/**
	 * Méthode qui permet de démarrer un chronomètre afin d'incrementer le temps
	 * d'une tache toutes les secondes ainsi que de mettre a jour le Label du temps
	 */
	private void startChrono() {
		time = new Timer();
		this.actualRunningSave.actualOrLastHbox = this;
		this.actualRunningSave.lastIsSub = false;
		this.globalTask.setIsChronoOn(true);
		this.chronoOfAll.start(this.globalTask.getAmountOfTime());
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.containHboxGLobale, this.allGlobalTask,
				this.chronoOfAll);
		this.updateLabelTime(time);
	}

	/**
	 * Méthode arretant le chronomètre en cours et rafraichissant la View afin de
	 * pouvoir remettre les boutons dans un etat d'actif
	 */
	private void closeChrono() {
		this.globalTask.setIsChronoOn(false);
		this.chronoOfAll.stop();
		for (SubTask s : this.globalTask.getListOfSubTaskAttach()) {
			s.setIsChronoOn(false);
		}
		time.cancel();
		new DataSaver().saveData(this.allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.containHboxGLobale, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode appelé au click sur le bouton paramètre mettant en place la fenetre
	 * de paramètrage de la tache
	 */
	private void handleParameterAction() {
		long[] dateValue = new long[] { globalTask.getAmountOfTime().getYear(), globalTask.getAmountOfTime().getMonth(),
				globalTask.getAmountOfTime().getDay(), globalTask.getAmountOfTime().getHours(),
				globalTask.getAmountOfTime().getMinutes(), globalTask.getAmountOfTime().getSeconds() };
		SpecficTime timeCreate = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4],
				dateValue[5]);
		SpecficTime greaterInList = globalTask.getGreaterInList(globalTask.getListOfTime());

		Label settingsLabel = new Label("Paramètres");
		settingsLabel.getStyleClass().add("settingsTitle");
		Label changeNamelabel = new Label("Renommer votre tache :");
		changeNamelabel.getStyleClass().add("parameterName");
		TextField textFielName = new TextField(globalTask.getName());
		textFielName.setMaxWidth(940);
		VBox.setMargin(textFielName, new Insets(5, 0, 15, 25));
		Label errorLabel = new Label();
		Label errorLabelTime = new Label(
				"Le temps ne peut être inferieur au temps de la sous tache la plus longue, ici :"
						+ greaterInList.toString(),
				new ImageView(error));
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
		Tooltip secondsToolTip = new Tooltip("Choissiser les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		secondsComboBox.setPromptText("" + dateValue[5]);

		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissiser les minutes dans cette area"));
		minutesComboBox.setPromptText("" + dateValue[4]);

		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississer les heures dans cette area"));
		hoursComboBox.setPromptText("" + dateValue[3]);

		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississer le nombre de jour dans cette area"));
		daysComboBox.setPromptText("" + dateValue[2]);

		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(HBoxSubTask.compteurInList(0, 13));
		monthComboBox.setTooltip(new Tooltip("Choississer le nombre de mois dans cette area"));
		monthComboBox.setPromptText("" + dateValue[1]);

		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Taper le nombre d'année de votre tache"));
		yearsInput.setPromptText("" + dateValue[0]);

		HBox timeBox = new HBox();
		timeBox.getStyleClass().add("timeBox");
		timeBox.setSpacing(15);
		timeBox.setPadding(new Insets(5, 0, 15, 25));
		timeBox.getChildren().addAll(new Label("Année:"), yearsInput, new Label("Mois:"), monthComboBox,
				new Label("Jour:"), daysComboBox, new Label("Heure:"), hoursComboBox, new Label("Minutes:"),
				minutesComboBox, new Label("Secondes:"), secondsComboBox);

		Button saveButton = new Button("Sauvegarder", new ImageView(save));
		Button cancelButton = new Button("Annuler", new ImageView(cancel));
		saveButton.getStyleClass().addAll("buttonActionSetting", "boldText");
		cancelButton.getStyleClass().add("buttonActionSetting");

		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton, cancelButton);
		hboxButton.getStyleClass().add("saveButtonBox");
		hboxButton.setSpacing(20);
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(settingsLabel, changeNamelabel, textFielName, changeAmountOfTimeBox, timeBox,
				hboxButton);
		rootWindows.getStyleClass().add("rootSettings");
		textFielName.textProperty().addListener((observable, oldvalue, newValue) -> {
			if (newValue == null || newValue.length() == 0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(3, errorLabel);
				errorLabel.setText("Le nom de la tache ne peut etre vide");
				errorLabel.setGraphic(new ImageView(error));
			} else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
				if (rootWindows.getChildren().contains(errorLabel))
					rootWindows.getChildren().remove(errorLabel);
			}
		});

		secondsComboBox.setOnAction((e) -> {
			handleTimeError(dateValue, 5, greaterInList, saveButton, rootWindows, secondsComboBox, errorLabelTime);
		});
		minutesComboBox.setOnAction((e) -> {
			handleTimeError(dateValue, 4, greaterInList, saveButton, rootWindows, minutesComboBox, errorLabelTime);
		});
		hoursComboBox.setOnAction((e) -> {
			handleTimeError(dateValue, 3, greaterInList, saveButton, rootWindows, hoursComboBox, errorLabelTime);
		});
		daysComboBox.setOnAction((e) -> {
			handleTimeError(dateValue, 2, greaterInList, saveButton, rootWindows, daysComboBox, errorLabelTime);
		});
		monthComboBox.setOnAction((e) -> {
			handleTimeError(dateValue, 1, greaterInList, saveButton, rootWindows, monthComboBox, errorLabelTime);
		});

		yearsInput.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0] = aTest;
					SpecficTime time = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3],
							dateValue[4], dateValue[5]);
					if (!time.isGreaterThan(greaterInList)) {
						saveButton.setDisable(true);
						saveButton.setTooltip(new Tooltip(
								"Le nouveaux temps ne peut etre inferieur au temps le plus grand des sous taches"));
						rootWindows.getChildren().add(5, errorLabelTime);
					} else {
						saveButton.setDisable(false);
						saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
						if (rootWindows.getChildren().contains(errorLabelTime))
							rootWindows.getChildren().remove(errorLabelTime);
					}
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
		Scene secondScene = new Scene(rootWindows, 1000, 350);
		secondScene.getStylesheets().add(
				getClass().getResource(File.separator + "style" + File.separator + "styling.css").toExternalForm());
		Stage newWindow = new Stage();
		newWindow.setTitle("Modifier une tache");
		newWindow.setScene(secondScene);
		newWindow.initModality(Modality.APPLICATION_MODAL);
		newWindow.initOwner(this.primaryStage);
		newWindow.setX(primaryStage.getX() + this.primaryStage.getWidth() / 2 - 500);
		newWindow.setY(primaryStage.getY() + this.primaryStage.getHeight() / 2 - 175);
		newWindow.show();
		saveButton.setOnMouseClicked(e -> {
			SpecficTime timeCreat = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3],
					dateValue[4], dateValue[5]);
			System.out.println(timeCreate);
			savesChanges(textFielName.getText(), timeCreat);
			newWindow.close();
		});
		cancelButton.setOnMouseClicked(e -> newWindow.close());
	}

	/**
	 * Méthode appeler au click sur le bouton sauvegarder remettant les taches a
	 * jours et rafraichissant la view
	 */
	private void savesChanges(String text, SpecficTime time) {
		this.globalTask.setName(text);
		this.globalTask.setAmontOfTimeByTime(time);
		new DataSaver().saveData(this.allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, this.containHboxGLobale, this.allGlobalTask,
				this.chronoOfAll);
	}

	/**
	 * Méthode permettant de gerer les erreurs liées au changement du temps ainsi le
	 * nouveau temps ne pourrait etre inferieur au temps le plus grand des sous
	 * taches
	 */
	private void handleTimeError(long[] dateValue, int position, SpecficTime greaterInList, Button saveButton,
			VBox root, ComboBox<Long> comboBox, Label errorLabelTime) {
		dateValue[position] = comboBox.getSelectionModel().getSelectedItem();
		SpecficTime time = new SpecficTime(dateValue[0], dateValue[1], dateValue[2], dateValue[3], dateValue[4],
				dateValue[5]);
		if (!time.isGreaterThan(greaterInList)) {
			saveButton.setDisable(true);
			saveButton.setTooltip(
					new Tooltip("Le nouveaux temps ne peut etre inferieur au temps le plus grand des sous taches"));
			root.getChildren().add(5, errorLabelTime);
		} else {
			saveButton.setDisable(false);
			saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
			if (root.getChildren().contains(errorLabelTime))
				root.getChildren().remove(errorLabelTime);
		}
	}

}
