package Front;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;

import Back.Chrono;
import Back.DataSaver;
import Back.GlobalTask;
import Back.SubTask;
import Back.TaskSaver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MyApp extends Application{
	private static final String iconPath = "/task.png";
	private Stage stage;
	private Timer notificationTimer = new Timer();
	private TaskSaver allTask;
	
	//Ajoutez le chrono et saverGlobalOrSUb pour display notif
	public void start(Stage stage) {
		this.stage=stage;
		this.stage.getIcons().add(new Image(iconPath));
		//Idée de GridPane avec trois colonnes et celle du milieu mon scrooll pane
		//On resize wondwos calcule taille - taille hbox
		//si pplus grand alors divise par deux et taille des autres conlonnes
		//sinon autr colonne taile a zero et milieu colonne a x
		Platform.setImplicitExit(false);
		//Platform.runLater(this::addAppToTray);
		javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
		    @Override public Void call() {
		        addAppToTray();
		        return null;
		    }
		};

		
		new Thread(task).start();
		//javafx.concurrent.Worker<V>
		//SwingUtilities.invokeLater(this::addAppToTray);
		List<HBoxGlobalTask> containALl = new ArrayList<>();
		
		
		Chrono aChrono = new Chrono();

		//GlobalTask task = new GlobalTask("my firs task2",922337200);
		GlobalTask task2 = new GlobalTask("my firs task3",85);
		
		//TaskSaver allTask =  new TaskSaver();
		TaskSaver allTask =  loadData();
		this.allTask=allTask;
		//Quand tout sera pret
		int idx =0;
		ScrollPane scrollView = new ScrollPane();
		VBox root = new VBox();
		
		
		/*SubTask aSubTask1 = new SubTask("toto");
		SubTask aSubTask2 = new SubTask("michel");
		SubTask aSubTask3 = new SubTask("aandre");
		SubTask aSubTask4 = new SubTask("bernard");

		SubTask aSubTask5 = new SubTask("beloche");
		task.addASubtask(aSubTask1);task.addASubtask(aSubTask2);
		task.addASubtask(aSubTask3);task.addASubtask(aSubTask4);
		task2.addASubtask(aSubTask5);
		allTask.addAGlobalTask(task);allTask.addAGlobalTask(task2);*/
		System.out.println(allTask.getListAllTask());
		for(GlobalTask t :allTask.getListAllTask()) {
			HBoxGlobalTask anHBoxGlobal = new HBoxGlobalTask(stage,root,aChrono,idx);
			anHBoxGlobal.setVBox(allTask,t,containALl);
			containALl.add(anHBoxGlobal);
		}
		
		for(HBoxGlobalTask h : containALl) {
			root.getChildren().add(h.getVboxAttach());
		}
		/*int i=0;
		for(GlobalTask gbtask:allTask.getListAllTask()) {
			System.out.println(gbtask);
			root.getChildren().add(VBoxForATask.createVBoxTask(stage,allTask, gbtask,aChrono,i));
			System.out.println(i);

			i++;
		}*/
		Button addOne = new Button("add a Task", new ImageView(new Image(HBoxGlobalTask.class.getResourceAsStream(File.separator+"plus.png"),35,35,false,false)));
		root.getChildren().add(addOne);
		addOne.setOnMouseClicked(e->WindowCreateTask.createAGlobalTask(containALl, root, stage, allTask, aChrono));
		//for(GlobalTask tasks: allTask.getListAllTask()) {
			//HBox anHbox = HBoxGlobalTask.createHBox(allTask,tasks);
			//root.getChildren().add(anHbox);
		//}
		scrollView.setContent(root);
		
		
		
		Scene  scene = new  Scene(scrollView);
		scene.getStylesheets().add(getClass().getResource("/style/styling.css").toExternalForm());
		stage.setTitle("TaskManager");
		stage.setScene(scene );
		stage.show();
		//Permet de faire la sauvegarde lorsque l'on quitte
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				System.out.println(allTask);
				//stop(stage, "resources/saveData",allTask);
				stage.hide();
				
			}
		});
		
	}
	private void addAppToTray() {
		try {
			System.out.println("hello");
			Toolkit.getDefaultToolkit();
			if(!SystemTray.isSupported()) {
				System.out.println("pas de systeme tray suport");
				Platform.exit();
			}
			SystemTray systemTray = SystemTray.getSystemTray();
			URL imageURl = this.getClass().getResource(iconPath);
			java.awt.Image image = ImageIO.read(imageURl);
			TrayIcon trayIcon = new TrayIcon(image);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(e->Platform.runLater(this::showStage));
			System.out.println("lol");
			java.awt.MenuItem openItem = new java.awt.MenuItem("Task Manager");
			openItem.addActionListener(e->Platform.runLater(this::showStage));
			openItem.setFont(Font.decode(null).deriveFont(Font.BOLD));
			java.awt.MenuItem closeItem = new java.awt.MenuItem("Exit the app");
			closeItem.addActionListener(e->{
				stop(this.stage,"resources/saveData",this.allTask);
				systemTray.remove(trayIcon);
			});
			PopupMenu popup = new PopupMenu();
			popup.add(openItem);
			popup.addSeparator();
				popup.add(closeItem);
			trayIcon.setPopupMenu(popup);
			systemTray.add(trayIcon);
		}catch(AWTException | IOException e) {
			System.out.println("system tray not init");
			e.printStackTrace();
		}
	}
	public  void stop(Stage stage, String pathname, TaskSaver data) {
		for(GlobalTask g:this.allTask.getListAllTask()) {
			for(SubTask s:g.getListOfSubTaskAttach()) {
				s.setIsChronoOn(false);
			}
			g.setIsChronoOn(false);
		}
		System.out.println("not Stopping bro");
		this.saveData(data,0);
		System.out.println("Im gone");
		Platform.exit();
	}
	public TaskSaver loadData() {
		DataSaver loader = new DataSaver();
		TaskSaver task = loader.loadData();
		return task;
	}
	public void saveData(TaskSaver dataSave, int numberExecution) {
		DataSaver saver = new DataSaver();
		saver.saveData(dataSave, numberExecution);
	}
	private void showStage() {
		if(this.stage!=null) {
			stage.show();
			stage.toFront();
		}
	}
	public static void main(String[] args) throws IOException,AWTException{
		Application.launch(args);
	}
}
