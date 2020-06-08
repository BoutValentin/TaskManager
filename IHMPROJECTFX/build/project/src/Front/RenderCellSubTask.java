package Front;

import Back.SubTask;
import javafx.scene.control.ListCell;
/** 
 * Class réalisant la personnalisation des cellules des comboBox pour la liste des sous-taches
 * */
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
