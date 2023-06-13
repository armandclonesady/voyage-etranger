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
                    setStyle("-fx-background-color: rgba(220, 220, 220, 0.8)"); 
                    if (EcranIntro.platform.getPairFixed().entrySet().contains(item)) {
                        setStyle("-fx-background-color: rgba(124, 252, 138, 0.8)");
                    } else if (EcranIntro.platform.getPairAvoided().entrySet().contains(item)) {
                        setStyle("-fx-background-color: rgba(255, 81, 81, 0.8)");
                    }
                    setText(item.getKey().toString() + " -> " + item.getValue().toString());
                }
            }
        };
    }
}