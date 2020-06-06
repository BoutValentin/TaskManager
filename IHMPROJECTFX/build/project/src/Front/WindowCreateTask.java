package Front;


import java.util.List;

import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SpecficTime;
import Back.SubTask;
import Back.TaskSaver;
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

public class WindowCreateTask {
	private static final int size = 35;
	private static final String resLink = "/";
	private static Image start = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"interface.png"),size,size,false,false);
	private static Image plus = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"plus.png"),size,size,false,false);
	private static Image delete = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"delete.png"),size,size,false,false);
	private static Image parameter = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"symbol.png"),size,size,false,false);
	private static Image pause = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"pause.png"),size,size,false,false);
	private static Image help = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"help.png"),size,size,false,false);
	private static Image error = new Image(HBoxGlobalTask.class.getResourceAsStream(resLink+"error.png"),size,size,false,false);
		
	public static void createASubTask(HBoxGlobalTask hboxGlobal,List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,Stage prStage,VBox root,Chrono aChrono) {
		/*
		 * Create un nouveau subTask a ajouter a la list de subtask puis refresh
		 *  */
		
		long[] dateValue = new long[] {0, 0,0,0,0,0};
		GlobalTask[] taskToChanged = new GlobalTask[] {globalTask};
		HBoxGlobalTask[] hboxtoChanged = new HBoxGlobalTask[] {hboxGlobal};
		Label changeNamelabel = new Label("Nommez votre tache");
		TextField textFielName = new TextField("");
		textFielName.setPromptText("250 caractere maximum");
		Label errorLabel = new Label();
		errorLabel.setText("Le nom de la tache ne peut etre vide");
		errorLabel.setGraphic(new ImageView(error));
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
		secondsComboBox.setOnAction((e)->{
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		minutesComboBox.setOnAction((e)->{
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		hoursComboBox.setOnAction((e)->{
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().add(dateValue[2]);
		daysComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		daysComboBox.setOnAction((e)->{
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		monthComboBox.setOnAction((e)->{
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
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
		Label changeGlobalRootLabel = new Label("Choississez la tache globale d'appartenance de la sous tache");
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
			taskToChanged[0] = globalTaskComboBox.getSelectionModel().getSelectedItem().equals(globalTask) ? globalTask:globalTaskComboBox.getSelectionModel().getSelectedItem();
			hboxtoChanged[0] = allHbox.get(allGlobalTask.getPositionOfGlobalTask(taskToChanged[0]));
		});
		Button saveButton = new Button("save changes");
		saveButton.setDisable(true);
		Button cancelButton = new Button("cancel");
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox rootWindows = new VBox();
		rootWindows.getChildren().addAll(changeNamelabel,textFielName,errorLabel,changeAmountOfTimeBox,timeBox,changeGlobalRootLabel,globalTaskComboBox,hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue)->{
			System.out.println(oldvalue+""+newValue);
			if(newValue==null || newValue.length()==0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				rootWindows.getChildren().add(2, errorLabel);
				
			}else if(newValue.length()>255){
				textFielName.setText(oldvalue);
			}
			else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour creez la Tache"));
				if(rootWindows.getChildren().contains(errorLabel))rootWindows.getChildren().remove(errorLabel);
			}
		});
		Scene secondScene = new Scene(rootWindows, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Creez une sous tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(prStage);
        // Set position of second window, related to primary window.
        newWindow.setX(prStage.getX() + 200);
        newWindow.setY(prStage.getY() + 100);
        newWindow.show();
        saveButton.setOnMouseClicked(e->{
        	SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	saveCreatingSub(hboxtoChanged[0], allHbox, allGlobalTask, taskToChanged[0], prStage, root, textFielName.getText(), timeCreate, aChrono);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	private static void saveCreatingSub(HBoxGlobalTask hboxGlobal,List<HBoxGlobalTask> allHbox,TaskSaver allGlobalTask,GlobalTask globalTask,Stage prStage,VBox root, String text, SpecficTime aTime,Chrono aChrono) {
		SubTask subTask = new SubTask(text);
		subTask.setAmontOfTimeByTime(aTime);
		globalTask.addASubtask(subTask);
		globalTask.addAmountOfTimeByTime(subTask.getAmountOfTime());
		if(globalTask.getPositionInList(subTask)!=-1) {
			HBoxSubTask anHbox = new HBoxSubTask(prStage, root, hboxGlobal, aChrono, hboxGlobal.getListHBoxSubAttach().size());
			anHbox.setHBox(allHbox, hboxGlobal.getListHBoxSubAttach(), allGlobalTask, globalTask, subTask, aChrono);
			hboxGlobal.getListHBoxSubAttach().add(anHbox);
		}
		new DataSaver().saveData(allGlobalTask, 0);
		Refresh.refresContent(prStage, root, allHbox, allGlobalTask, aChrono);
	}
	
	
	public static void createAGlobalTask(List<HBoxGlobalTask> allHbox,VBox rootOfAll,Stage primStage, TaskSaver allTask,Chrono aChrono) {
		
		/* 
		 * Creer une GlobalTask a ajouter a la root
		 * */
		long[] dateValue = new long[] {0,0,0,0,0,0};

		Label changeNamelabel = new Label("Nommez votre tache");
		TextField textFielName = new TextField("");
		textFielName.setPromptText("250 caractere maximum");
		Label errorLabel = new Label();
		errorLabel.setText("Le nom de la tache ne peut etre vide");
		errorLabel.setGraphic(new ImageView(error));
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
		secondsComboBox.setOnAction((e)->{
			dateValue[5] = secondsComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> minutesComboBox = new ComboBox<>();
		minutesComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 60));
		minutesComboBox.setTooltip(new Tooltip("Choissisez les minutes dans cette area"));
		minutesComboBox.setPromptText(""+dateValue[4]);
		minutesComboBox.setOnAction((e)->{
			dateValue[4] = minutesComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> hoursComboBox = new ComboBox<>();
		hoursComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 24));
		hoursComboBox.setTooltip(new Tooltip("Choississez les heures dans cette area"));
		hoursComboBox.setPromptText(""+dateValue[3]);
		hoursComboBox.setOnAction((e)->{
			dateValue[3] = hoursComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> daysComboBox = new ComboBox<>();
		daysComboBox.getItems().add(dateValue[2]);
		daysComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0, 31));
		daysComboBox.setTooltip(new Tooltip("Choississez le nombre de jour dans cette area"));
		daysComboBox.setPromptText(""+dateValue[2]);
		daysComboBox.setOnAction((e)->{
			dateValue[2] = daysComboBox.getSelectionModel().getSelectedItem();
		});
		ComboBox<Long> monthComboBox = new ComboBox<>();
		monthComboBox.getItems().addAll(new HBoxSubTask().compteurInList(0,13));
		monthComboBox.setTooltip(new Tooltip("Choississez le nombre de mois dans cette area"));
		monthComboBox.setPromptText(""+dateValue[1]);
		monthComboBox.setOnAction((e)->{
			dateValue[1] = monthComboBox.getSelectionModel().getSelectedItem();
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
		
		Button saveButton = new Button("save changes");
		saveButton.setDisable(true);
		Button cancelButton = new Button("cancel");
		
		
		
		HBox hboxButton = new HBox();
		hboxButton.getChildren().addAll(saveButton,cancelButton);
		
		VBox root = new VBox();
		root.getChildren().addAll(changeNamelabel,textFielName,errorLabel,changeAmountOfTimeBox,timeBox,hboxButton);

		textFielName.textProperty().addListener((observable, oldvalue, newValue)->{
			System.out.println(oldvalue+""+newValue);
			if(newValue==null || newValue.length()==0) {
				saveButton.setDisable(true);
				saveButton.setTooltip(new Tooltip("Le nom de la tache ne peut pas etre null"));
				root.getChildren().add(2, errorLabel);
				
			}else if(newValue.length()>255){
				textFielName.setText(oldvalue);
			}
			else {
				saveButton.setDisable(false);
				saveButton.setTooltip(new Tooltip("appuyer ici pour creez la Tache"));
				if(root.getChildren().contains(errorLabel))root.getChildren().remove(errorLabel);
			}
		});
		Scene secondScene = new Scene(root, 1000, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Creez une tache");
        newWindow.setScene(secondScene);
        // Specifies the modality for new window.
        newWindow.initModality(Modality.APPLICATION_MODAL);
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primStage);
        // Set position of second window, related to primary window.
        newWindow.setX(primStage.getX() + 200);
        newWindow.setY(primStage.getY() + 100);
        newWindow.show();
        saveButton.setOnMouseClicked(e->{
        	SpecficTime timeCreate = new SpecficTime(dateValue[0],dateValue[1],dateValue[2],dateValue[3],dateValue[4],dateValue[5]);
        	System.out.println(timeCreate);
        	saveCreatingGlobal(allHbox,rootOfAll,primStage, allTask, textFielName.getText(),timeCreate, aChrono);
        	newWindow.close();});
        cancelButton.setOnMouseClicked(e->newWindow.close());
	}
	public static void saveCreatingGlobal(List<HBoxGlobalTask> allHbox,VBox rootOfAll,Stage prStage, TaskSaver allTask,String text, SpecficTime aTime,Chrono aChrono) {
		GlobalTask newGlobalTask = new GlobalTask(text);
		newGlobalTask.setAmontOfTimeByTime(aTime);
		allTask.addAGlobalTask(newGlobalTask);
		HBoxGlobalTask anHbox = new HBoxGlobalTask(prStage, rootOfAll, aChrono, allHbox.size());
		anHbox.setVBox(allTask, newGlobalTask, allHbox);
		allHbox.add(anHbox);
		new DataSaver().saveData(allTask, 0);
		Refresh.refresContent(prStage, rootOfAll, allHbox, allTask, aChrono);
	}
}