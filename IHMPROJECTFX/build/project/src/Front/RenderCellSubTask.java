package Front;

import Back.SubTask;
import javafx.scene.control.ListCell;

public class RenderCellSubTask extends ListCell<SubTask> {
	public void updateItem(SubTask task, boolean empty) {
		super.updateItem(task, empty);
		if(empty || task==null) {
			this.setText("loading");
		}else {
			this.setText(task.getName());
		}
	}
}
