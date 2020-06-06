package Front;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SpecficTime;
import Back.SubTask;
import Back.Task;
import Back.TaskSaver;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HBoxGlobalTask {
	private static final int size = 35;
	private static final String resLink = "/";
	private static Image start = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"interface.png"),size,size,false,false);
	private static Image downArrow = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"downArrow.png"),size,size,false,false);
	private static Image upArrow = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"upArrow.png"),size,size,false,false);
	private static Image delete = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"delete.png"),size,size,false,false);
	private static Image parameter = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"symbol.png"),size,size,false,false);
	private static Image pause = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"pause.png"),size,size,false,false);
	private static Image plus = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"plus.png"),size,size,false,false);
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"help.png"),size,size,false,false);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"error.png"),size,size,false,false);
	
	private Chrono chronoOfAll;
	private Stage primaryStage;
	private VBox rootOfAll;
	private VBox root;
	private HBox containAll ;
	private HBox containExeptDelete;
	private Button startButton;
	private Button parameterButton;
	private Button toggleButton;
	private Button deleteButton;
	private Button addButton;
	private Label taskNameLabel;
	private Label underTaskLabel;
	private Label timeTextLabel;
	private Label labelTheTime;
	private List<HBoxSubTask> children;
	private int indexInSave;
	
	//A retirer des que fini
	
	public HBoxGlobalTask() {
		
	}
	public HBoxGlobalTask(Stage primaryStage,VBox rootofall, Chrono aChrono,int index) {
		this.root = new VBox();
		this.containAll = new HBox();
		this.containExeptDelete= new HBox();
		this.startButton = new Button();
		this.parameterButton = new Button();
		this.toggleButton = new Button();
		this.deleteButton = new Button();
		this.addButton = new Button();
		this.taskNameLabel = new Label();
		this.underTaskLabel = new Label();
		this.timeTextLabel = new Label();
		this.labelTheTime = new Label();
		this.children = new ArrayList<HBoxSubTask>();
		this.indexInSave = index;
		this.primaryStage=primaryStage;
		this.rootOfAll = rootofall;
		this.chronoOfAll=aChrono;
	}
	public List<HBoxSubTask> getListHBoxSubAttach(){
		return this.children;
	}
	public Label getTimeLabel() {
		return this.labelTheTime;
	}
	public void setVBox(TaskSaver allGlobalTask,GlobalTask task,List<HBoxGlobalTask> containHboxGLobale) {
		//Ajouter le style aux widgets
		this.containAll.getStyleClass().add("GlobalHBox");
		this.containExeptDelete.getStyleClass().add("encadreHBox");
		this.startButton.getStyleClass().add("encadreButton");
		this.deleteButton.getStyleClass().add("deleteButtonGlobale");
		this.addButton.getStyleClass().add("addSubPadding");
		this.timeTextLabel.getStyleClass().add("timeFielGlobale");
		this.labelTheTime.getStyleClass().add("textFieldGlobale");
		this.taskNameLabel.getStyleClass().add("textFieldGlobale");
		this.underTaskLabel.getStyleClass().add("textFieldGlobale");
		//Initialisation
		this.startButton.setText("Start");this.startButton.setGraphic(new ImageView(start));this.startButton.setTooltip(new Tooltip("Demarrez la tache: "+task.getName()));
		this.parameterButton.setGraphic(new ImageView(parameter));this.parameterButton.setTooltip(new Tooltip("Parametrez la tache: "+task.getName()));
		this.deleteButton.setGraphic(new ImageView(delete));this.deleteButton.setTooltip(new Tooltip("Supprimez la tache: "+task.getName()));
		this.toggleButton.setGraphic(new ImageView(task.getToggle()? upArrow:downArrow));this.toggleButton.setTooltip(new Tooltip("Affichez les sous-taches liées à: "+task.getName()));
		this.taskNameLabel.setText(task.getName());this.taskNameLabel.setTooltip(new Tooltip("Nom de la tache"));
		this.underTaskLabel.setText(""+task.getNumberOfUnderTask()+" sous-taches");this.underTaskLabel.setTooltip(new Tooltip("Nombre de sous-taches liès a :"+task.getName()));
		this.timeTextLabel.setText("Temps : ");
		this.labelTheTime.setText(createTimeLabel(task));this.labelTheTime.setTooltip(new Tooltip("Temps passer sur cette tache globale"));
		int idx = 0;
		for(SubTask s:task.getListOfSubTaskAttach()) {
			HBoxSubTask anHboxSub = new HBoxSubTask(this.primaryStage,this.rootOfAll,this,this.chronoOfAll,idx++);
			anHboxSub.setHBox(containHboxGLobale,this.children,allGlobalTask,task,s, this.chronoOfAll);
			this.children.add(anHboxSub);
		}
		this.addButton.setText("Creez une sous-taches");;this.addButton.setGraphic(new ImageView(plus));
		//Event
		Timer time = new Timer();
		this.deleteButton.setOnMouseClicked(e->handleDelete(allGlobalTask, containHboxGLobale, task));
		this.toggleButton.setOnMouseClicked(e->{
			handleShowMore(containHboxGLobale, allGlobalTask, task);
			System.out.println(task.getToggle());
		});
		this.startButton.setOnMouseClicked(e->chooseMethod(allGlobalTask, containHboxGLobale, task, time));
		this.parameterButton.setOnMouseClicked(e->handleParameterAction(containHboxGLobale, allGlobalTask, task));
		this.addButton.setOnMouseClicked(e->WindowCreateTask.createASubTask(this, containHboxGLobale, allGlobalTask, task, this.primaryStage, this.rootOfAll, this.chronoOfAll));
		
		//Ajouter le tout a la VBox
		this.root.getChildren().add(this.containAll);
		this.containAll.getChildren().addAll(this.containExeptDelete, this.deleteButton);
		this.containExeptDelete.getChildren().addAll(this.startButton,this.taskNameLabel,this.underTaskLabel,this.timeTextLabel,this.labelTheTime, this.parameterButton,this.toggleButton);
		
		this.addButton.setTooltip(new Tooltip("Creez une tache"));
		if(task.getToggle()){
			for(HBoxSubTask h:this.children) {
				this.root.getChildren().add(h.getHBoxattach());
			}
			this.root.getChildren().add(this.addButton);
		}

	}
	
	public void refresh(GlobalTask task, Chrono aChrono) {
		if(!aChrono.getIsOn()) {
			this.startButton.setDisable(false);
			this.parameterButton.setDisable(false);
			this.deleteButton.setDisable(false);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setText("Start");
		}else if(aChrono.getIsOn() && task.getIsChronoOn()) {
			this.startButton.setDisable(false);
			this.parameterButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(pause));
			this.startButton.setText("Pause");
		}else {
			this.startButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.parameterButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setText("Start");
		}
		this.taskNameLabel.setText(task.getName());
		this.underTaskLabel.setText(""+task.getNumberOfUnderTask()+" sous-taches");
		this.labelTheTime.setText(createTimeLabel(task));
		this.toggleButton.setGraphic(new ImageView(task.getToggle()? upArrow:downArrow));
		int idx = 0;
		for(HBoxSubTask hbSt : this.children) {		
			hbSt.refresh(task.getASubTaskById(idx++), aChrono);
		}
		
		this.root.getChildren().clear();
		this.root.getChildren().add(this.containAll);
		
		idx = 0;
		if(task.getToggle()){
			System.out.println(this.children);
			for(HBoxSubTask anHboxSub : this.children) {
				this.root.getChildren().add(anHboxSub.getHBoxattach());
			}
			this.root.getChildren().add(this.addButton);
		}
	}
	public VBox getVboxAttach() {
		return this.root;
	}
	//Obsolete
	public  HBox createHBox(Stage primaryStage,VBox view, TaskSaver allTask,GlobalTask task,Chrono aChrono,int index) {
		/* Changez cela en si ChronoOn et taskIsOn alors on appelle update sachant que le chrono est ON dc on 
		 * update juste ntore label en le passant en parametre et avant verifier si ce timer est deja on si oui on cancel et on reprend 
		 * un thread */
		Timer aTimer =new Timer();
		HBox containerAll = new HBox();
		containerAll.getStyleClass().add("GlobalHBox");
		HBox container = new HBox();
		container.getStyleClass().add("encadreHBox");
		Button buttonStart = new Button("Start",new ImageView(start));
		if(!aChrono.getIsOn()) {
			buttonStart.setDisable(false);
		}else if(aChrono.getIsOn() && task.getIsChronoOn()) {
			buttonStart.setDisable(false);
		}else {
			buttonStart.setDisable(true);
		}
		//buttonStart.setDisable((!aChrono.getIsOn() || (aChrono.getIsOn()&&task.getIsChronoOn())));
		buttonStart.getStyleClass().add("encadreButton");
		Button buttonDownArrow = new Button();
		if(!task.getToggle())buttonDownArrow.setGraphic(new ImageView(upArrow));
		else buttonDownArrow.setGraphic(new ImageView(downArrow));
		
		buttonDownArrow.setOnMouseClicked(e->handleShowMore(primaryStage,view, allTask,task, buttonDownArrow,aChrono));
	
		Button buttonParameter = new Button();
		buttonParameter.setGraphic(new ImageView(parameter));
		buttonParameter.setOnMouseClicked(e->handleParametreAction(primaryStage, view,containerAll , allTask, task,aChrono));
		Button buttonDelete = new Button();
		buttonDelete.setGraphic(new ImageView(delete));
		buttonDelete.setOnMouseClicked(e->{
			handleDelete(primaryStage, allTask, task,aChrono);
		});
		Label labelTaskName = new Label(task.getName());
		Label labelUnderTask = new Label (""+task.getNumberOfUnderTask()+" sous-taches");
		Label labelTemps = new Label("Temps: ");
		
		labelTheTime.setText(createTimeLabel(task));
		buttonStart.setOnMouseClicked((e)->{
			chooseMethod(primaryStage,view,allTask,task, buttonStart, aChrono, aChrono.getIsOn(),aTimer,labelTheTime,index);
			});
		container.getChildren().addAll(buttonStart,labelTaskName,labelUnderTask,labelTemps,labelTheTime,buttonParameter,buttonDownArrow);
		containerAll.getChildren().addAll(container, buttonDelete);
		if(!aChrono.getIsOn()) {
			buttonStart.setDisable(false);
			buttonParameter.setDisable(false);
			buttonDelete.setDisable(false);
		}else if(aChrono.getIsOn() && task.getIsChronoOn()) {
			buttonStart.setDisable(false);
			buttonParameter.setDisable(true);
			buttonDelete.setDisable(true);
			buttonStart.setGraphic(new ImageView(pause));
			buttonStart.setText("pause");
		}else {
			buttonStart.setDisable(true);
			buttonDelete.setDisable(true);
			buttonParameter.setDisable(true);
		}
		return containerAll;
	}
	private void handleDelete(TaskSaver allGlobalTask ,List<HBoxGlobalTask> allHbox, GlobalTask taskRemove) {
		VBox windowsRoot = new VBox();
		Label textLabel = new Label("Voulez vous vraiment supprimer la tache :"+taskRemove.getName()+"et ses sous taches attachés ?");
		HBox containerButton = new HBox();
		Button valider = new Button("J'en suis sur !");
		Button annuler = new Button("Non! annulez");
		containerButton.getChildren().addAll(valider,annuler);
		windowsRoot.getChildren().addAll(textLabel,containerButton);
		Scene windowsScene = new  Scene(windowsRoot,1000,400);
		Stage newWindow = new Stage();
        newWindow.setTitle("Supprimez une tache");
        newWindow.setScene(windowsScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.show();
        valider.setOnMouseClicked(e->{this.deleteTask(allGlobalTask, allHbox, taskRemove);newWindow.close();});
        annuler.setOnMouseClicked(e->{newWindow.close();});
	}
	private void deleteTask(TaskSaver allGlobalTask ,List<HBoxGlobalTask> allHbox, GlobalTask taskRemove) {
		taskRemove.clearAll();
		allGlobalTask.deleteAGlobalTaskByGlobalTask(taskRemove);
		new DataSaver().saveData(allGlobalTask, 0);
		allHbox.remove(this);
		//Aplliquer un refresh ici
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allGlobalTask, this.chronoOfAll);
	}
	//N'est plus d'actualite
	private void handleDelete(Stage primStage,TaskSaver allTask, GlobalTask task,Chrono aChrono) {
		VBox windowsRoot = new VBox();
		Label textLabel = new Label("Voulez vous vraiment supprimer la tache "+task.getName()+" ?");
		HBox buttonContain = new HBox();
		Button valider = new Button("J'en suis sur");
		Button cancel = new Button("annulez");
		buttonContain.getChildren().addAll(valider,cancel);
		windowsRoot.getChildren().addAll(textLabel,buttonContain);
		Scene secondScene = new Scene(windowsRoot, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Supprimez une tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primStage);
        // Set position of second window, related to primary window.
        newWindow.setX(primStage.getX() + 200);
        newWindow.setY(primStage.getY() + 100);
        newWindow.show();
        valider.setOnMouseClicked(e->{deleteTask(primStage,allTask, task,aChrono);newWindow.close();});
        cancel.setOnMouseClicked(e->{newWindow.close();});
	}
	//n'est plus d'actualite
	private void deleteTask(Stage pStage,TaskSaver allTask, GlobalTask task,Chrono aChrono) {
		task.clearAll();
		allTask.deleteAGlobalTaskByGlobalTask(task);
		new DataSaver().saveData(allTask, 0);
		Refresh.refreshContent(pStage,allTask,aChrono);
	}
	private void handleShowMore(List<HBoxGlobalTask> allHbox,TaskSaver allTask,GlobalTask task) {
		task.setToggleOrUnToggleViewSubTask();
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allTask, this.chronoOfAll);
	}
	
	//Plus d'actualite
	private void handleShowMore(Stage primaryStage,VBox view, TaskSaver allTask,GlobalTask task, Button b,Chrono aChrono) {
		
		task.setToggleOrUnToggleViewSubTask();
		System.out.println(task.getToggle());
		if(!task.getToggle()) b.setGraphic(new ImageView(upArrow));
		else b.setGraphic(new ImageView(downArrow));
		new DataSaver().saveData(allTask, 0);
		Refresh.refreshContent(primaryStage,allTask, aChrono);
		//refreshView(primaryStage,view,allTask, task);
	}
	/*private static void refreshView(Stage primaryStage,VBox view, TaskSaver allTask,GlobalTask task) {
		if(!task.getToggle()) {
			view.getChildren().clear();
			view.getChildren().add(HBoxGlobalTask.createHBox(primaryStage,view, allTask, task));
		}else {
			for(SubTask s: task.getListOfSubTaskAttach()) {
				
				view.getChildren().add(HBoxSubTask.createHBoxSub(primaryStage,view, (HBox)view.getChildren().get(0),allTask,task, s));
			}
			Button addOne = new Button("add a Subtask", new ImageView(plus));
			view.getChildren().add(addOne);
		}
	}*/
	private void updateLabelTime(Timer time,GlobalTask task) {
		time.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(()->{
					labelTheTime.setText(createTimeLabel(task));
					//Refresh.refreshContent(pr, allTask, aChrono);
				});
				
			}
		}, 0, 1000);
	}
	
	//Obsolete
	private void updateLabelTime(Timer time,Label aLabel, GlobalTask task) {
		time.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(()->{
					System.out.println(aLabel.getText());
					System.out.println(labelTheTime.getText());
					aLabel.setText(createTimeLabel(task));
					//Refresh.refreshContent(pr, allTask, aChrono);
				});
				
			}
		}, 0, 1000);
	}
	protected static String createTimeLabel(Task task) {
		switch (task.precisionToShow()) {
		case 2:
			return ""+task.getAmountOfTime().getMinutes()+"min "+task.getAmountOfTime().getSeconds();
		case 3:
			return ""+task.getAmountOfTime().getHours()+"heures "+task.getAmountOfTime().getMinutes()+"min "+task.getAmountOfTime().getSeconds();
		case 4:
			return ""+task.getAmountOfTime().getDay()+"jours "+task.getAmountOfTime().getHours()+"heures "+task.getAmountOfTime().getMinutes()+"min "+task.getAmountOfTime().getSeconds();
		case 5:
			return ""+task.getAmountOfTime().getMonth()+"mois "+task.getAmountOfTime().getDay()+"jours "+task.getAmountOfTime().getHours()+"heures "+task.getAmountOfTime().getMinutes()+"min "+task.getAmountOfTime().getSeconds();
		case 6:
			return ""+task.getAmountOfTime().getYear()+"année "+task.getAmountOfTime().getMonth()+"mois "+task.getAmountOfTime().getDay()+"jours "+task.getAmountOfTime().getHours()+"heures "+task.getAmountOfTime().getMinutes()+"min "+task.getAmountOfTime().getSeconds();
			
		default:
			break;
		}
		return "";
	}
	private void chooseMethod(TaskSaver allTask,List<HBoxGlobalTask> allHbox,GlobalTask task,Timer time) {
		if(!task.getIsChronoOn())this.startChrono(allTask, allHbox, task, time);
		else this.closeChrono(allTask, allHbox, task, time);
	}
	private void startChrono(TaskSaver allTask,List<HBoxGlobalTask> allHbox,GlobalTask task,Timer time) {
		time = new Timer();
		task.setIsChronoOn(true);
		this.chronoOfAll.start(task.getAmountOfTime());
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allTask, this.chronoOfAll);
		this.updateLabelTime(time, task);
	}
	private void closeChrono(TaskSaver allTask,List<HBoxGlobalTask> allHbox,GlobalTask task,Timer time) {
		task.setIsChronoOn(false);
		this.chronoOfAll.stop();
		for(SubTask s: task.getListOfSubTaskAttach()) {
			s.setIsChronoOn(false);
		}
		time.cancel();
		new DataSaver().saveData(allTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allTask, this.chronoOfAll);
	}
	//Obsolete
	private void chooseMethod(Stage pr,VBox root,TaskSaver allTask,GlobalTask task, Button b, Chrono aChrono, Boolean b2,Timer time,Label aLabel,int index) {
		if(!b2)startChrono(pr,root,allTask,task, b, aChrono,time,aLabel,index);
		else closeChrono(pr,allTask,task, b, aChrono,time);
		
	}
	//obsolete
	private void startChrono(Stage pr,VBox root,TaskSaver allTask,GlobalTask task, Button b, Chrono aChrono,Timer time,Label aLabel,int index) {
		task.setIsChronoOn(true);
		aChrono.start(task.getAmountOfTime());
		time = new Timer();
		//new DataSaver().saveData(allTask, 0);
		VBox roo = Refresh.refreshContent(pr, allTask, aChrono);
		VBox root2 = (VBox)roo.getChildren().get(index);
		HBox hbroot = (HBox)root2.getChildren().get(0);
		HBox hbroot2 = (HBox) hbroot.getChildren().get(0);
		updateLabelTime(time,(Label) hbroot2.getChildren().get(4), task);
		Button button = (Button)hbroot2.getChildren().get(0);
		button.setGraphic(new ImageView(pause));
		button.setText("pause");
		//Refresh.refreshContent(pr, allTask, aChrono);

	}
	//Obsolete
	private void closeChrono(Stage pr,TaskSaver allTask,GlobalTask task, Button b, Chrono aChrono,Timer time) {
		task.setIsChronoOn(false);
		aChrono.stop();
		time.cancel();
		new DataSaver().saveData(allTask, 0);
		Refresh.refreshContent(pr, allTask, aChrono);
		b.setGraphic(new ImageView(start));
		b.setText("play");
	}
	private void handleParameterAction(List<HBoxGlobalTask>allHbox,TaskSaver allGlobalTask,GlobalTask globalTask) {
		long[] dateValue = new long[] {globalTask.getAmountOfTime().getYear(), globalTask.getAmountOfTime().getMonth(),globalTask.getAmountOfTime().getDay(),globalTask.getAmountOfTime().getHours(),globalTask.getAmountOfTime().getMinutes(),globalTask.getAmountOfTime().getSeconds()};
		SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
		SpecficTime greaterInList = globalTask.getGreaterInList(globalTask.getListOfTime());
		Label changeNamelabel = new Label("Renommez votre tache");
		TextField textFielName = new TextField(globalTask.getName());
		Label errorLabel = new Label();
		Label errorLabelTime = new Label("Le temps ne peut etre inferieur au temps de la sous tache la plus longue, ici :"+greaterInList.toString(), new ImageView(error));
		Label changeAmountOfTimeLabel = new Label("Changez le temps passer sur cette tache");
		ImageView helpIcon = new ImageView(help);
		Tooltip.install(helpIcon, new Tooltip("Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel,helpIcon);
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissisez les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		secondsComboBox.setPromptText(""+dateValue[5]);
		
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Tapez le nombre d'année de votre tache"));
		yearsInput.setPromptText(""+dateValue[0]);
		//Verifionis si pendant l'input on as des chiffres
		
		
		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(new Label("Année:"),yearsInput,new Label("Mois:"),monthComboBox,new Label("Jour:"),daysComboBox,new Label("Heure:"),hoursComboBox,new Label("Minutes:"),minutesComboBox,new Label("Secondes:"),secondsComboBox);
		//Modifiez la root globalTask
		
		
		
		
		Button saveButton = new Button("save changes");
		Button cancelButton = new Button("cancel");
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(changeNamelabel,textFielName,changeAmountOfTimeBox,timeBox,hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue)->{
			if(newValue==null || newValue.length()==0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(2, errorLabel);
				errorLabel.setText("Le nom de la tache ne peut etre vide");
				errorLabel.setGraphic(new ImageView(error));
			}else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
				if(rootWindows.getChildren().contains(errorLabel))rootWindows.getChildren().remove(errorLabel);
			}
		});
		
		secondsComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 5, greaterInList, saveButton, rootWindows, secondsComboBox, errorLabelTime);
		});
		minutesComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 4, greaterInList, saveButton, rootWindows, minutesComboBox, errorLabelTime);
		});
		hoursComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 3, greaterInList, saveButton, rootWindows, hoursComboBox, errorLabelTime);
		});
		daysComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 2, greaterInList, saveButton, rootWindows, daysComboBox, errorLabelTime);
		});
		monthComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 1, greaterInList, saveButton, rootWindows, monthComboBox, errorLabelTime);
		});
		
		yearsInput.textProperty().addListener((observable, oldValue, newValue)->{
			try {				
				if(!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0]=aTest;
					SpecficTime time = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
					if(!time.isGreaterThan(greaterInList)) {
						saveButton.setDisable(true);
						saveButton.setTooltip(new Tooltip("Le nouveaux temps ne peut etre inferieur au temps le plus grand des sous taches"));
						rootWindows.getChildren().add(4, errorLabelTime);
					}else {
						saveButton.setDisable(false);
						saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
						if(rootWindows.getChildren().contains(errorLabelTime))rootWindows.getChildren().remove(errorLabelTime);
					}
				}
			}catch(NullPointerException e) {
				yearsInput.setText("");
			}catch(NumberFormatException e) {
				try {
				yearsInput.setText(oldValue);
				}catch(NullPointerException e2) {
					yearsInput.setText("");
				}
			}
		});
		Scene secondScene = new Scene(rootWindows, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Modifiez une tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(this.primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(this.primaryStage.getX() + 200);
        newWindow.setY(this.primaryStage.getY() + 100);
        newWindow.show();
        saveButton.setOnMouseClicked(e->{
        	SpecficTime timeCreat = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	savesChanges(allHbox, allGlobalTask, globalTask, textFielName.getText(), timeCreat);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	private void savesChanges(List<HBoxGlobalTask>allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,String text, SpecficTime time) {
		globalTask.setName(text);
		globalTask.setAmontOfTimeByTime(time);
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allGlobalTask, this.chronoOfAll);
	}
	//Obsolète
	private void handleParametreAction(Stage primaryStage,VBox view,HBox hboxglobale,TaskSaver allGlobalTask,GlobalTask globalTask,Chrono aChrono) {
		long[] dateValue = new long[] {globalTask.getAmountOfTime().getYear(), globalTask.getAmountOfTime().getMonth(),globalTask.getAmountOfTime().getDay(),globalTask.getAmountOfTime().getHours(),globalTask.getAmountOfTime().getMinutes(),globalTask.getAmountOfTime().getSeconds()};
		SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
		SpecficTime greaterInList = globalTask.getGreaterInList(globalTask.getListOfTime());
		Label changeNamelabel = new Label("Renommez votre tache");
		TextField textFielName = new TextField(globalTask.getName());
		Label errorLabel = new Label();
		Label errorLabelTime = new Label("Le temps ne peut etre inferieur au temps de la sous tache la plus longue, ici :"+greaterInList.toString(), new ImageView(error));
		Label changeAmountOfTimeLabel = new Label("Changez le temps passer sur cette tache");
		ImageView helpIcon = new ImageView(help);
		Tooltip.install(helpIcon, new Tooltip("Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel,helpIcon);
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissisez les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		secondsComboBox.setPromptText(""+dateValue[5]);
		
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Tapez le nombre d'année de votre tache"));
		yearsInput.setPromptText(""+dateValue[0]);
		//Verifionis si pendant l'input on as des chiffres
		
		
		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(new Label("Année:"),yearsInput,new Label("Mois:"),monthComboBox,new Label("Jour:"),daysComboBox,new Label("Heure:"),hoursComboBox,new Label("Minutes:"),minutesComboBox,new Label("Secondes:"),secondsComboBox);
		//Modifiez la root globalTask
		
		
		
		
		Button saveButton = new Button("save changes");
		Button cancelButton = new Button("cancel");
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox root = new VBox();
		root.getChildren().addAll(changeNamelabel,textFielName,changeAmountOfTimeBox,timeBox,hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue)->{
			if(newValue==null || newValue.length()==0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				root.getChildren().add(2, errorLabel);
				errorLabel.setText("Le nom de la tache ne peut etre vide");
				errorLabel.setGraphic(new ImageView(error));
			}else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
				if(root.getChildren().contains(errorLabel))root.getChildren().remove(errorLabel);
			}
		});
		
		secondsComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 5, greaterInList, saveButton, root, secondsComboBox, errorLabelTime);
		});
		minutesComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 4, greaterInList, saveButton, root, minutesComboBox, errorLabelTime);
		});
		hoursComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 3, greaterInList, saveButton, root, hoursComboBox, errorLabelTime);
		});
		daysComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 2, greaterInList, saveButton, root, daysComboBox, errorLabelTime);
		});
		monthComboBox.setOnAction((e)->{
			handleTimeError(dateValue, 1, greaterInList, saveButton, root, monthComboBox, errorLabelTime);
		});
		
		yearsInput.textProperty().addListener((observable, oldValue, newValue)->{
			try {				
				if(!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0]=aTest;
					SpecficTime time = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
					if(!time.isGreaterThan(greaterInList)) {
						saveButton.setDisable(true);
						saveButton.setTooltip(new Tooltip("Le nouveaux temps ne peut etre inferieur au temps le plus grand des sous taches"));
						root.getChildren().add(4, errorLabelTime);
					}else {
						saveButton.setDisable(false);
						saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
						if(root.getChildren().contains(errorLabelTime))root.getChildren().remove(errorLabelTime);
					}
				}
			}catch(NullPointerException e) {
				yearsInput.setText("");
			}catch(NumberFormatException e) {
				try {
				yearsInput.setText(oldValue);
				}catch(NullPointerException e2) {
					yearsInput.setText("");
				}
			}
		});
		Scene secondScene = new Scene(root, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Modifiez une tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.show();
        saveButton.setOnMouseClicked(e->{
        	SpecficTime timeCreat = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	saveChanges(primaryStage,view,hboxglobale,allGlobalTask,globalTask,textFielName.getText(), timeCreat, aChrono);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	
	private void handleTimeError(long[] dateValue, int position, SpecficTime greaterInList,Button saveButton, VBox root,ComboBox<Long> comboBox,Label errorLabelTime) {
		dateValue[position] = comboBox.getSelectionModel().getSelectedItem();
		SpecficTime time = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
		if(!time.isGreaterThan(greaterInList)) {
			saveButton.setDisable(true);
			saveButton.setTooltip(new Tooltip("Le nouveaux temps ne peut etre inferieur au temps le plus grand des sous taches"));
			root.getChildren().add(4, errorLabelTime);
		}else {
			saveButton.setDisable(false);
			saveButton.setTooltip(new Tooltip("appuyer ici pour enregistrez vos modifications"));
			if(root.getChildren().contains(errorLabelTime))root.getChildren().remove(errorLabelTime);
		}
	}
	private void saveChanges(Stage primStage, VBox view,HBox hboxglobale,TaskSaver allGlobalTask,GlobalTask globalTask, String text, SpecficTime time,Chrono aChrono ) {
		globalTask.setName(text);
		globalTask.setAmontOfTimeByTime(time);
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refreshContent(primStage,allGlobalTask,aChrono);
		/*view.getChildren().clear();
		HBox modify = (HBox)hboxglobale.getChildren().get(0);
		modify.getChildren().set(4,new Label(HBoxGlobalTask.createTimeLabel(globalTask)) );
		modify.getChildren().set(1, new Label(""+globalTask.getName()));
		view.getChildren().add(hboxglobale);
		if(globalTask.getToggle()) {
			for(SubTask s: globalTask.getListOfSubTaskAttach()) {
				System.out.println(s.getName());
				HBox anHBox = HBoxSubTask.createHBoxSub(primStage,view,hboxglobale,allGlobalTask,globalTask, s);
				view.getChildren().add(anHBox);
			}
			Button addOne = new Button("add a Subtask", new ImageView(plus));
			view.getChildren().add(addOne);
		}*/
		
	}
}
