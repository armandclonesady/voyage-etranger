package ihm;

import java.util.Map;

import devoo.Teenager;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AffectListFactory implements Callback<ListView<Map.Entry<Teenager, Teenager>>, ListCell<Map.Entry<Teenager, Teenager>>> {
    @Override
    public ListCell<Map.Entry<Teenager, Teenager>> call(ListView<Map.Entry<Teenager, Teenager>> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Map.Entry<Teenager, Teenager> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getKey().toString() + " -> " + item.getValue().toString());
                }
            }
        };
    }
}