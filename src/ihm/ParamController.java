package ihm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ParamController implements EventHandler<ActionEvent> {
    @FXML
    Label histWeight;
    @FXML
    Label allergyWeight;
    @FXML
    Label dietWeight;
    @FXML
    Label countryWeight;
    @FXML
    Label genderWeight;
    @FXML
    Label hobbyWeight;


    final int DEFAULT_HISTORY_VALUE = 100;
    final int DEFAULT_ALLERGY_VALUE = 100;
    final int DEFAULT_FOOD_VALUE = 100;
    final int DEFAULT_COUNTRY_VALUE = 100;
    final int DEFAULT_GENDER_VALUE = 1;
    final int DEFAULT_HOBBIES_VALUE = 1;

    @Override
    public void handle(ActionEvent arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
    public void initialize() {
        System.out.println("Initialisation...");
        histWeight.setText(""+DEFAULT_HISTORY_VALUE);
        

    }
}
