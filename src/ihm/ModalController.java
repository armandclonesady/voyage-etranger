package ihm;

import devoo.IdComparator;
import devoo.Teenager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ModalController extends AppController {

    @FXML Label hostLabel;
    @FXML Label guestLabel;

    @FXML ListView<Teenager> hostList;
    @FXML ListView<Teenager> guestList;

    @FXML Button addButton;

    public void initialize() {
        hostLabel.setText(AppController.selectedHost.name());
        guestLabel.setText(AppController.selectedGuest.name());
        System.out.println(hostLabel.getText());
        System.out.println(guestLabel.getText());
        for (Teenager t : App.platform.getStudents()) {
            if (t.getCountry().equals(AppController.selectedHost)) {
                hostList.getItems().add(t);
            }
            if (t.getCountry().equals(AppController.selectedGuest)) {
                guestList.getItems().add(t);
            }
        }
        hostList.getItems().sort(new IdComparator());
        guestList.getItems().sort(new IdComparator());

        hostList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                addButton.setDisable(false);
            }
        });


    }

    public void onAddButtonClicked() {
        Teenager host = hostList.getSelectionModel().getSelectedItem();
        Teenager guest = guestList.getSelectionModel().getSelectedItem();
        if (host != null && guest != null) {
            App.platform.addPair(host, guest);
            hostList.getItems().remove(host);
            guestList.getItems().remove(guest);
        }
        updatePair();
    }
}
