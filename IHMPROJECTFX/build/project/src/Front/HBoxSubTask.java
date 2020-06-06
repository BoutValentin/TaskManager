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
import Back.TaskSaver;
import javafx.application.Platform;
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

public class HBoxSubTask {
	private static final int size = 35;
	private static final String resLink = "/";
	private static Image start = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"interface.png"),size,size,false,false);
	private static Image delete = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"delete.png"),size,size,false,false);
	private static Image parameter = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"symbol.png"),size,size,false,false);
	private static Image pause = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"pause.png"),size,size,false,false);
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"help.png"),size,size,false,false);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"error.png"),size,size,false,false);
	
	private Chrono chronoOfAll;
	private Stage primaryStage;
	private VBox rootOfAll;
	private HBoxGlobalTask rootHboxGlobale;
	private HBox root ;
	private HBox containAllExpectDelete ;
	private Button startButton ;
	private Button paramsButton ;
	private Button deleteButton ;
	private Label taskNameLabel ;
	private Label timeTextLabel ;
	private Label timingLabel ;
	private int indexInGlobalList;

	//a remove apres tout regler
	public HBoxSubTask() {}
	public HBoxSubTask(Stage primaryStage,VBox rootofall,HBoxGlobalTask rootHboxGlobale, Chrono aChrono,int index) {
		this.root = new HBox();
		this.containAllExpectDelete = new HBox();
		this.startButton = new Button();
		this.paramsButton = new Button();
		this.deleteButton = new Button();
		this.taskNameLabel = new Label();
		this.timeTextLabel = new Label();
		this.timingLabel = new Label();
		this.indexInGlobalList = index;
		this.primaryStage=primaryStage;
		this.rootOfAll = rootofall;
		this.rootHboxGlobale = rootHboxGlobale;
		this.chronoOfAll=aChrono;
	}
	public void setHBox(List<HBoxGlobalTask> allHBoxGlobal,List<HBoxSubTask> allHBoxSub,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask task, Chrono aChrono) {
		//Ajoutons le style definit dans le fichier css
		this.root.getStyleClass().add("SubHBox");
		this.containAllExpectDelete.getStyleClass().add("encadreHBox");
		this.startButton.getStyleClass().add("encadreButton");
		
		//Initialisons nos widgets
		this.startButton.setText("Start");this.startButton.setGraphic(new ImageView(start));this.startButton.setTooltip(new Tooltip("Demarrez le chrono de la sous-taches: "+task.getName()));
		this.paramsButton.setGraphic(new ImageView(parameter));this.paramsButton.setTooltip(new Tooltip("Parametrez la sous-tache: "+task.getName()));
		this.deleteButton.setGraphic(new ImageView(delete));this.deleteButton.setTooltip(new Tooltip("Supprimez la sous-tache"+task.getName()));
		this.taskNameLabel.setText(task.getName());this.taskNameLabel.setTooltip(new Tooltip("Le nom de la sous-tache"));
		this.timeTextLabel.setText("Temps: ");
		this.timingLabel.setText(HBoxGlobalTask.createTimeLabel(task));this.timingLabel.setTooltip(new Tooltip("Temps passé sur la sous-taches: "+task.getName()));
		
		//Initialisons les events
		Timer time = new Timer();
		this.startButton.setOnMouseClicked(e->chooseMethod(allHBoxGlobal, allGlobalTask, globalTask, task, time));
		this.deleteButton.setOnMouseClicked(e->handleDeleteAction(allHBoxGlobal, allHBoxSub, allGlobalTask, globalTask, task));
		this.paramsButton.setOnMouseClicked(e->handleParameterClick(allHBoxGlobal, allGlobalTask, globalTask, task));
		//Ajoutons le tout dans les containers
		this.root.getChildren().addAll(this.containAllExpectDelete, this.deleteButton);
		this.containAllExpectDelete.getChildren().addAll(this.startButton,this.taskNameLabel,this.timeTextLabel,this.timingLabel,this.paramsButton);
	}
	public void refresh(SubTask task, Chrono aChrono) {
		if(!aChrono.getIsOn()) {
			this.startButton.setDisable(false);
			this.paramsButton.setDisable(false);
			this.deleteButton.setDisable(false);
			this.startButton.setGraphic(new ImageView(start));
			this.startButton.setText("Start");
		}else if(aChrono.getIsOn() && task.getIsChronoOn()) {
			this.startButton.setDisable(false);
			this.paramsButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.startButton.setGraphic(new ImageView(pause));
			this.startButton.setText("Pause");
		}else {
			this.startButton.setDisable(true);
			this.deleteButton.setDisable(true);
			this.paramsButton.setDisable(true);
		}
		this.taskNameLabel.setText(task.getName());
		this.timingLabel.setText(HBoxGlobalTask.createTimeLabel(task));
	}
	public HBox getHBoxattach() {
		return this.root;
	}
	//Obsolète
	public HBox createHBoxSub(Stage primaryStage,VBox view,HBox hboxGlobalTask,TaskSaver allGlobalTask,GlobalTask globalTask, SubTask subTask,Chrono aChrono) {
		Timer aTimer = new Timer();		
		HBox root = new HBox();
		root.getStyleClass().add("SubHBox");
		HBox reste = new HBox();
		reste.getStyleClass().add("encadreHBox");
		//Creation des widgets
		Button startButton = new Button("start", new ImageView(start));
		startButton.getStyleClass().add("encadreButton");
		startButton.setTooltip(new Tooltip("appuyer ici pour demarrer le chronomètre de cette sous-tache"));
		Label subTaskName = new Label(subTask.getName());
		Label labelTime = new Label(new HBoxGlobalTask().createTimeLabel(subTask));
		Button paramsButton = new Button();
		paramsButton.setGraphic(new ImageView(parameter));
		Button deleteButton = new Button();
		deleteButton.setGraphic(new ImageView(delete));
		//Gere l'evenement parameter
		paramsButton.setOnMouseClicked(e->handleParameterClick(primaryStage,view, hboxGlobalTask,allGlobalTask,globalTask, subTask,aChrono));
		//Gere l'evenement start a chrono
		startButton.setOnMouseClicked(e->chooseMethod(hboxGlobalTask, globalTask, subTask, startButton, aChrono, aChrono.getIsOn(), aTimer, labelTime));
		//Gere l'evenement de suppression
		deleteButton.setOnMouseClicked(e->handleDeleteAction(primaryStage,view, hboxGlobalTask,allGlobalTask, globalTask, subTask, aChrono));
		reste.getChildren().addAll(startButton,subTaskName,labelTime,paramsButton);
		root.getChildren().addAll(reste,deleteButton);
		if(!aChrono.getIsOn()) {
			startButton.setDisable(false);
			paramsButton.setDisable(false);
			deleteButton.setDisable(false);
		}else if(aChrono.getIsOn() && subTask.getIsChronoOn()) {
			startButton.setDisable(false);
			paramsButton.setDisable(true);
			deleteButton.setDisable(true);
			startButton.setGraphic(new ImageView(pause));
			startButton.setText("pause");
		}else {
			startButton.setDisable(true);
			deleteButton.setDisable(true);
			paramsButton.setDisable(true);
		}
		return root;
	}
	
	private void handleDeleteAction(List<HBoxGlobalTask> allHBoxGlobale,List<HBoxSubTask> allSub,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask) {
		Label aLabel = new Label("Voulez vous vraiment supprimer la sous-tache:"+subTask.getName()+" de :"+globalTask.getName());
		Button yesButton = new Button("Oui je suis sur");
		
		Button cancelButton = new Button("annuler");
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(yesButton,cancelButton);
		
		VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(aLabel,buttonBox);
		Scene secondScene = new Scene(secondaryLayout, 230, 100);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Supprimez une Sous Tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(this.primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(this.primaryStage.getX() + 200);
        newWindow.setY(this.primaryStage.getY() + 100);
        newWindow.show();
        yesButton.setOnMouseClicked(e-> {this.removeTask(allHBoxGlobale, allSub, allGlobalTask, globalTask, subTask);newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	private void removeTask(List<HBoxGlobalTask> allHBoxGlobale,List<HBoxSubTask> allSubHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subtask) {
		globalTask.removeAmountOfTimeByTime(subtask.getAmountOfTime());
		globalTask.deleteATaskByTask(subtask);
		allSubHbox.remove(this);
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHBoxGlobale, allGlobalTask, this.chronoOfAll);
	}
	
	//Obsolète
	private  void handleDeleteAction(Stage primaryStage,VBox view, HBox hboxglobale, TaskSaver allGlobalTask,GlobalTask globalTask, SubTask subTask,Chrono aChrono) {
		//Open confirmation window
		//If feedback is true
		Label aLabel = new Label("Voulez vous vraiment supprimer la sous-tache:"+subTask.getName()+" de :"+globalTask.getName());
		Button yesButton = new Button("Oui je suis sur");
		
		Button cancelButton = new Button("annuler");
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(yesButton,cancelButton);
		
		VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().addAll(aLabel,buttonBox);
		Scene secondScene = new Scene(secondaryLayout, 230, 100);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Supprimez une Sous Tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.show();
        yesButton.setOnMouseClicked(e-> {removeTask(primaryStage, view, hboxglobale,allGlobalTask, globalTask, subTask,aChrono);newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	//Obsolète
	private void removeTask(Stage primaryStage,VBox view, HBox hboxglobale,TaskSaver allGlobalTask, GlobalTask globalTask, SubTask subTask,Chrono aChrono) {
		globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
		globalTask.deleteATaskByTask(subTask);
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refreshContent(primaryStage,allGlobalTask,aChrono);
		/*view.getChildren().clear();
		view.getChildren().add(hboxglobale);
		for(SubTask s: globalTask.getListOfSubTaskAttach()) {
			System.out.println(s.getName());
			HBox anHBox = HBoxSubTask.createHBoxSub(primaryStage,view, hboxglobale,allGlobalTask, globalTask, s);
			view.getChildren().add(anHBox);
		}
		Button addOne = new Button("add a Subtask", new ImageView(plus));
		view.getChildren().add(addOne);*/

	}
	
	private void updateLabelTime(Timer time, GlobalTask globalTask,SubTask subTask) {
		time.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(()->{
					timingLabel.setText(HBoxGlobalTask.createTimeLabel(subTask));
					rootHboxGlobale.getTimeLabel().setText(HBoxGlobalTask.createTimeLabel(globalTask));
				});
				
			}
		}, 0, 1000);
	}
	
	//Obsolete
	private void updateLabelTime(Timer time,Label labelGlobaltask, Label labelsubtask, GlobalTask GlobalTask, SubTask subtask) {
		time.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(()->{
					labelsubtask.setText(new HBoxGlobalTask().createTimeLabel(subtask));
					labelGlobaltask.setText(new HBoxGlobalTask().createTimeLabel(GlobalTask));
					
				});
				
			}
		}, 0, 1000);
	}
	
	private void chooseMethod(List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask,Timer time) {
		if(!globalTask.getIsChronoOn()&&!subTask.getIsChronoOn()) this.startChrono(allHbox,allGlobalTask,globalTask, subTask, time);
		else this.closeChrono(allHbox, allGlobalTask, globalTask, subTask, time);
	}
	private void startChrono(List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask,Timer time) {
		subTask.setIsChronoOn(true);globalTask.setIsChronoOn(true);
		this.chronoOfAll.doubleStart(globalTask.getAmountOfTime(), subTask.getAmountOfTime());
		time = new Timer();
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allGlobalTask, this.chronoOfAll);
		updateLabelTime(time, globalTask, subTask);
	}
	private void closeChrono(List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask,Timer time) {
		subTask.setIsChronoOn(false);globalTask.setIsChronoOn(false);
		this.chronoOfAll.stop();
		time.cancel();
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allGlobalTask, this.chronoOfAll);
	}
	
	//Obsolete
	private void chooseMethod(HBox hboxGloobal,GlobalTask globaltask, SubTask subTask,Button b, Chrono aChrono, Boolean b2,Timer time,Label aLabel) {
		if(!b2)startChrono(globaltask,subTask, b, aChrono,time,hboxGloobal,aLabel);
		else closeChrono(globaltask,subTask,hboxGloobal, b, aChrono,time);
		
	}
	//Obsolete
	private void startChrono(GlobalTask globaltask,SubTask subTask, Button b, Chrono aChrono,Timer time,HBox hboxGloobal, Label labelsubtask) {
		subTask.setIsChronoOn(true);
		globaltask.setIsChronoOn(true);
		aChrono.doubleStart(globaltask.getAmountOfTime(), subTask.getAmountOfTime());
		HBox contain = (HBox)hboxGloobal.getChildren().get(0);
		time = new Timer();
		updateLabelTime(time,(Label)contain.getChildren().get(4),labelsubtask,globaltask,subTask);
		
		Button frombig = (Button)contain.getChildren().get(0);
		frombig.setGraphic(new ImageView(pause));
		frombig.setText("pause");
		b.setGraphic(new ImageView(pause));
		b.setTooltip(new Tooltip("apputer ici pour arreter le chronomètre de cette sous-taches"));
		b.setText("pause");
	}
	//Obsolète
	private void closeChrono(GlobalTask globaltask,SubTask subTask,HBox hboxGloobal,Button b, Chrono aChrono,Timer time) {
		subTask.setIsChronoOn(false);
		globaltask.setIsChronoOn(false);
		aChrono.stop();
		time.cancel();
		HBox contain = (HBox)hboxGloobal.getChildren().get(0);
		Button frombig = (Button)contain.getChildren().get(0);
		frombig.setGraphic(new ImageView(start));
		frombig.setText("play");
		b.setGraphic(new ImageView(start));
		b.setTooltip(new Tooltip("apputer ici pour demarrer le chronomètre de cette sous-taches"));
		b.setText("play");
	}
	private void handleParameterClick(List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask) {
		long[] dateValue = new long[] {subTask.getAmountOfTime().getYear(), subTask.getAmountOfTime().getMonth(),subTask.getAmountOfTime().getDay(),subTask.getAmountOfTime().getHours(),subTask.getAmountOfTime().getMinutes(),subTask.getAmountOfTime().getSeconds()};
		GlobalTask[] taskToChanged = new GlobalTask[] {null};
		SubTask[] taskToMerge = new SubTask[] {null};
		Button saveButton = new Button("save changes", new ImageView(parameter));
		Button cancelButton = new Button("cancel");
		Label changeNamelabel = new Label("Renommez votre tache");
		TextField textFielName = new TextField(subTask.getName());
		Label errorLabel = new Label();
		Label changeAmountOfTimeLabel = new Label("Changez le temps passer sur cette tache");
		ImageView helpIcon = new ImageView(help);
		Tooltip.install(helpIcon, new Tooltip("Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel,helpIcon);
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissisez les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(compteurInList(0, 60));
		secondsComboBox.setPromptText(""+dateValue[5]);
		secondsComboBox.setOnAction((e)->{
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
			if(taskToChanged[0]!=null&&!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		minutesComboBox.setOnAction((e)->{
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
			if(taskToChanged[0]!=null&&!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		hoursComboBox.setOnAction((e)->{
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
			if(taskToChanged[0]!=null&&!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().add(dateValue[2]);
		daysComboBox.getItems().addAll(compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		daysComboBox.setOnAction((e)->{
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
			if(taskToChanged[0]!=null&&!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);

		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		monthComboBox.setOnAction((e)->{
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
			if(taskToChanged[0]!=null&&!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Tapez le nombre d'année de votre tache"));
		yearsInput.setPromptText(""+dateValue[0]);
		//Verifionis si pendant l'input on as des chiffres
		yearsInput.textProperty().addListener((observable, oldValue, newValue)->{
			try {				
				if(!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0]=aTest;
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
		
		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(new Label("Année:"),yearsInput,new Label("Mois:"),monthComboBox,new Label("Jour:"),daysComboBox,new Label("Heure:"),hoursComboBox,new Label("Minutes:"),minutesComboBox,new Label("Secondes:"),secondsComboBox);
		//Modifiez la root globalTask
		
		Label changeGlobalRootLabel = new Label("Changez la tache globale d'appartenance de la sous tache");
		ComboBox<GlobalTask> globalTaskComboBox = new ComboBox<>();
		globalTaskComboBox.setPromptText(globalTask.getName());
		globalTaskComboBox.getItems().addAll(allGlobalTask.getListAllTask());
		globalTaskComboBox.setCellFactory(new Callback<ListView<GlobalTask>, ListCell<GlobalTask>>() {
			@Override
			public ListCell<GlobalTask> call(ListView<GlobalTask> param) {
				return new RenderCellGlobaltask();
			}
		});
		globalTaskComboBox.setOnAction(e->{
			taskToChanged[0] = globalTaskComboBox.getSelectionModel().getSelectedItem().equals(globalTask) ? null:globalTaskComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(subTask)) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		
		Label mergeTwoTask = new Label("Merge deux sous tache en une ");
		ComboBox<SubTask> subTaskComboBox = new ComboBox<SubTask>();
		subTaskComboBox.setPromptText(subTask.toString());
		subTaskComboBox.getItems().addAll(globalTask.getListOfSubTaskAttach());
		subTaskComboBox.setCellFactory(new Callback<ListView<SubTask>, ListCell<SubTask>>() {
			public ListCell<SubTask> call(ListView<SubTask> param){
				return new RenderCellSubTask();
			}
		});
		subTaskComboBox.setOnAction(e->{
			taskToMerge[0] = subTaskComboBox.getSelectionModel().getSelectedItem();
		});
		
		
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(changeNamelabel,textFielName,changeAmountOfTimeBox,timeBox,changeGlobalRootLabel,globalTaskComboBox,mergeTwoTask,subTaskComboBox,hboxButton);

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
			if(taskToChanged[0]!=null && !taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		Scene secondScene = new Scene(rootWindows, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Modifiez une sous tache");
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
        	SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	saveChanges(allHbox,allGlobalTask,globalTask,subTask,textFielName.getText(), timeCreate,taskToChanged[0] , taskToMerge[0],taskToChanged[0]!=null);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	public void saveChanges(List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,SubTask subTask,String text, SpecficTime time,GlobalTask otherGlobalTask,SubTask subTaskmerge,boolean changedRoot) {
		//Retire les Hbox de sub de tout
		if(subTaskmerge!=null && !subTaskmerge.equals(subTask) && changedRoot) {
			System.out.println("merge + changedRoot");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			globalTask.removeAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			
			
			System.out.println(""+globalTask.getPositionInList(subTask)+"postionsubTask");
			if(globalTask.getPositionInList(subTask)!=-1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(globalTask.getPositionInList(subTask));

			}
			if(globalTask.getPositionInList(subTaskmerge)!=-1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(globalTask.getPositionInList(subTaskmerge));
			}
			
			subTask.setAmontOfTimeByTime(time);
			subTask.addAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			otherGlobalTask.addASubtask(subTask);
			otherGlobalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
			System.out.println(""+allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)+"positionInGLobalTAsk");
			if(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)!=-1) {
				HBoxSubTask ansub = new HBoxSubTask(this.primaryStage, this.rootOfAll, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)), this.chronoOfAll, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach().size());
				ansub.setHBox(allHbox, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach(), allGlobalTask, otherGlobalTask, subTask, this.chronoOfAll);
				allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach().add(ansub);
				System.out.println(allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach());
			}
			globalTask.deleteATaskByTask(subTaskmerge);
			globalTask.deleteATaskByTask(subTask);
		}else if(subTaskmerge!=null && !subTaskmerge.equals(subTask)) {
			//Problème a la deuxieme suppression
			System.out.println("merge + noChangedRoot");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			globalTask.removeAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			subTask.setAmontOfTimeByTime(time);
			subTask.addAmountOfTimeByTime(subTaskmerge.getAmountOfTime());
			if(globalTask.getPositionInList(subTaskmerge)!=-1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(globalTask.getPositionInList(subTaskmerge));
			}
			globalTask.deleteATaskByTask(subTaskmerge);
			globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}else if(!changedRoot) {
			System.out.println("no changed Root+ no merge");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			subTask.setAmontOfTimeByTime(time);
			globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}else {
			System.out.println("changed root + no merge");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			if(globalTask.getPositionInList(subTask)!=-1) {
				this.rootHboxGlobale.getListHBoxSubAttach().remove(globalTask.getPositionInList(subTask));

			}
			if(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)!=-1) {
				HBoxSubTask ansub = new HBoxSubTask(this.primaryStage, this.rootOfAll, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)), this.chronoOfAll, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach().size());
				ansub.setHBox(allHbox, allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach(), allGlobalTask, otherGlobalTask, subTask, this.chronoOfAll);
				allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach().add(ansub);
				System.out.println(allHbox.get(allGlobalTask.getPositionOfGlobalTask(otherGlobalTask)).getListHBoxSubAttach());
			}
			globalTask.deleteATaskByTask(subTask);
			subTask.setAmontOfTimeByTime(time);
			otherGlobalTask.addASubtask(subTask);
			otherGlobalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}
		
		new DataSaver().saveData(allGlobalTask, 0);
		System.out.println(this.primaryStage);
		System.out.println(this.rootOfAll);
		System.out.println(allHbox);
		System.out.println(allGlobalTask);
		System.out.println(this.chronoOfAll);
		Refresh.refresContent(this.primaryStage, this.rootOfAll, allHbox, allGlobalTask, this.chronoOfAll);
	}
	//Plus tard liste toute les globale task
	//Obsolete
	private void handleParameterClick(Stage primaryStage,VBox view, HBox hboxGloobal,TaskSaver allGlobalTask,GlobalTask globalTask, SubTask subTask,Chrono aChrono) {
		long[] dateValue = new long[] {subTask.getAmountOfTime().getYear(), subTask.getAmountOfTime().getMonth(),subTask.getAmountOfTime().getDay(),subTask.getAmountOfTime().getHours(),subTask.getAmountOfTime().getMinutes(),subTask.getAmountOfTime().getSeconds()};
		GlobalTask[] taskToChanged = new GlobalTask[] {null};
		SubTask[] taskToMerge = new SubTask[] {null};
		Button saveButton = new Button("save changes", new ImageView(parameter));
		Button cancelButton = new Button("cancel");
		Label changeNamelabel = new Label("Renommez votre tache");
		TextField textFielName = new TextField(subTask.getName());
		Label errorLabel = new Label();
		Label changeAmountOfTimeLabel = new Label("Changez le temps passer sur cette tache");
		ImageView helpIcon = new ImageView(help);
		Tooltip.install(helpIcon, new Tooltip("Si vous ne modifiez pas le temps il reste celui par defaut ainsi si seul seconde est modifier alors le reste est inchanger"));
		HBox changeAmountOfTimeBox = new HBox();
		changeAmountOfTimeBox.getChildren().addAll(changeAmountOfTimeLabel,helpIcon);
		ComboBox<Long> secondsComboBox = new ComboBox<Long>();
		Tooltip secondsToolTip = new Tooltip("Choissisez les secondes dans cette area");
		secondsComboBox.setTooltip(secondsToolTip);
		secondsComboBox.getItems().addAll(compteurInList(0, 60));
		secondsComboBox.setPromptText(""+dateValue[5]);
		secondsComboBox.setOnAction((e)->{
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		minutesComboBox.setOnAction((e)->{
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		hoursComboBox.setOnAction((e)->{
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().add(dateValue[2]);
		daysComboBox.getItems().addAll(compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		daysComboBox.setOnAction((e)->{
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);

		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		monthComboBox.setOnAction((e)->{
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		TextField yearsInput = new TextField();
		yearsInput.setTooltip(new Tooltip("Tapez le nombre d'année de votre tache"));
		yearsInput.setPromptText(""+dateValue[0]);
		//Verifionis si pendant l'input on as des chiffres
		yearsInput.textProperty().addListener((observable, oldValue, newValue)->{
			try {				
				if(!newValue.equals("") && !oldValue.equals(newValue)) {
					Long aTest = Long.parseLong(newValue);
					dateValue[0]=aTest;
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
		
		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(new Label("Année:"),yearsInput,new Label("Mois:"),monthComboBox,new Label("Jour:"),daysComboBox,new Label("Heure:"),hoursComboBox,new Label("Minutes:"),minutesComboBox,new Label("Secondes:"),secondsComboBox);
		//Modifiez la root globalTask
		
		Label changeGlobalRootLabel = new Label("Changez la tache globale d'appartenance de la sous tache");
		ComboBox<GlobalTask> globalTaskComboBox = new ComboBox<>();
		globalTaskComboBox.setPromptText(globalTask.getName());
		globalTaskComboBox.getItems().addAll(allGlobalTask.getListAllTask());
		globalTaskComboBox.setCellFactory(new Callback<ListView<GlobalTask>, ListCell<GlobalTask>>() {
			@Override
			public ListCell<GlobalTask> call(ListView<GlobalTask> param) {
				return new RenderCellGlobaltask();
			}
		});
		globalTaskComboBox.setOnAction(e->{
			taskToChanged[0] = globalTaskComboBox.getSelectionModel().getSelectedItem().equals(globalTask) ? null:globalTaskComboBox.getSelectionModel().getSelectedItem();
			if(!taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(subTask)) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		
		Label mergeTwoTask = new Label("Merge deux sous tache en une ");
		ComboBox<SubTask> subTaskComboBox = new ComboBox<SubTask>();
		subTaskComboBox.setPromptText(subTask.toString());
		subTaskComboBox.getItems().addAll(globalTask.getListOfSubTaskAttach());
		subTaskComboBox.setCellFactory(new Callback<ListView<SubTask>, ListCell<SubTask>>() {
			public ListCell<SubTask> call(ListView<SubTask> param){
				return new RenderCellSubTask();
			}
		});
		subTaskComboBox.setOnAction(e->{
			taskToMerge[0] = subTaskComboBox.getSelectionModel().getSelectedItem();
		});
		
		
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox root = new VBox();
		root.getChildren().addAll(changeNamelabel,textFielName,changeAmountOfTimeBox,timeBox,changeGlobalRootLabel,globalTaskComboBox,mergeTwoTask,subTaskComboBox,hboxButton);

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
			if(taskToChanged[0]!=null && !taskToChanged[0].equals(globalTask) && taskToChanged[0].getListOfSubTaskAttach().contains(new SubTask(textFielName.getText(),dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]))) saveButton.setDisable(true);
			else saveButton.setDisable(false);
		});
		Scene secondScene = new Scene(root, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Modifiez une sous tache");
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
        	SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	saveChanges(primaryStage,view,hboxGloobal,allGlobalTask,globalTask, taskToChanged[0], subTask, textFielName.getText(), timeCreate, taskToChanged[0]!=null, taskToMerge[0],aChrono);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	private void saveChanges(Stage primaryStage,VBox view,HBox hboxglobale,TaskSaver allGlobalTask,GlobalTask globalTask,GlobalTask otherGlobal, SubTask subTask, String text, SpecficTime time, boolean changedRoot, SubTask mergeSub,Chrono aChrono) {
		if(mergeSub!=null && !mergeSub.equals(subTask) && changedRoot) {
			System.out.println("merge + changedRoot");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			globalTask.removeAmountOfTimeByTime(mergeSub.getAmountOfTime());
			/*Ici remove de la list de HBoxGlobale les deux + ajouter a l'autre HBox
			 * 
			 * Si tous un numero je get par ce numero
			 * */
			
			
			globalTask.deleteATaskByTask(mergeSub);
			globalTask.deleteATaskByTask(subTask);
			subTask.setAmontOfTimeByTime(time);
			subTask.addAmountOfTimeByTime(mergeSub.getAmountOfTime());
			otherGlobal.addASubtask(subTask);
			otherGlobal.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}else if(mergeSub!=null && !mergeSub.equals(subTask)) {
			//Problème a la deuxieme suppression
			System.out.println("merge + noChangedRoot");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			globalTask.removeAmountOfTimeByTime(mergeSub.getAmountOfTime());
			subTask.setAmontOfTimeByTime(time);
			subTask.addAmountOfTimeByTime(mergeSub.getAmountOfTime());
			globalTask.deleteATaskByTask(mergeSub);
			globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}else if(!changedRoot) {
			System.out.println("no changed Root+ no merge");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			subTask.setAmontOfTimeByTime(time);
			globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}else {
			System.out.println("changed root + no merge");
			subTask.setName(text);
			globalTask.removeAmountOfTimeByTime(subTask.getAmountOfTime());
			globalTask.deleteATaskByTask(subTask);
			subTask.setAmontOfTimeByTime(time);
			otherGlobal.addASubtask(subTask);
			otherGlobal.addAmountOfTimeByTime(subTask.getAmountOfTime());
		}
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refreshContent(primaryStage,allGlobalTask,aChrono);
		//Remplacer ici par la méthode de refresh globale
		/*view.getChildren().clear();
		HBox modify = (HBox)hboxglobale.getChildren().get(0);
		modify.getChildren().set(4,new Label(HBoxGlobalTask.createTimeLabel(globalTask)) );
		view.getChildren().add(hboxglobale);
		System.out.println(globalTask.getListOfSubTaskAttach());
		for(SubTask s: globalTask.getListOfSubTaskAttach()) {
			System.out.println(s.getName());
			HBox anHBox = HBoxSubTask.createHBoxSub(primaryStage,view,hboxglobale,allGlobalTask,globalTask, s);
			view.getChildren().add(anHBox);
		}
		Button addOne = new Button("add a Subtask", new ImageView(plus));
		view.getChildren().add(addOne);*/
	}
	protected List<Long> compteurInList(int begin ,int stop){
		List<Long> aList = new ArrayList<Long>();
		for(long i = begin; i<stop;i++) {
			aList.add(i);
		}
		return aList;
	}
}
