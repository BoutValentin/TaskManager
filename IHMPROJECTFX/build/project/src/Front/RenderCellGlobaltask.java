package Front;

import Back.GlobalTask;
import javafx.scene.control.ListCell;

public class RenderCellGlobaltask extends ListCell<GlobalTask>{
	public void updateItem(GlobalTask task, boolean empty) {
		super.updateItem(task, empty);
		if(empty || task==null) {
			this.setText("loading");
		}else {
			this.setText(task.getName());
		}
	}
}
