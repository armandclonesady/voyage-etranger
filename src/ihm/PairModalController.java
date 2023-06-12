package ihm;

import java.util.Map;

import devoo.IdComparator;
import devoo.Teenager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PairModalController extends MainController {

    @FXML Label hostLabel;
    @FXML Label guestLabel;

    @FXML ListView<Teenager> hostList;
    @FXML ListView<Teenager> guestList;

    @FXML Button addButton;

    public void initialize() {
        hostLabel.setText(EcranIntroController.selectedHost.name());
        guestLabel.setText(EcranIntroController.selectedGuest.name());
        System.out.println(hostLabel.getText());
        System.out.println(guestLabel.getText());
        for (Teenager t : EcranIntro.platform.getStudents()) {
            if (t.getCountry().equals(EcranIntroController.selectedHost)) {
                hostList.getItems().add(t);
            }
            if (t.getCountry().equals(EcranIntroController.selectedGuest)) {
                guestList.getItems().add(t);
            }
        }
        for (Map.Entry<Teenager,Teenager> e : EcranIntro.platform.getPairFixed().entrySet()) {
            if (e.getKey().getCountry().equals(EcranIntroController.selectedHost)) {
                hostList.getItems().remove(e.getKey());
            }
            if (e.getValue().getCountry().equals(EcranIntroController.selectedGuest)) {
                guestList.getItems().remove(e.getValue());
            }
        }
        hostList.getItems().sort(new IdComparator());
        guestList.getItems().sort(new IdComparator());

        hostList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onListChange();
        });
        guestList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onListChange();
        });

    }

    public void onAddButtonClicked() {
        Teenager host = hostList.getSelectionModel().getSelectedItem();
        Teenager guest = guestList.getSelectionModel().getSelectedItem();
        if (host != null && guest != null) {
            EcranIntro.platform.addPair(host, guest);
            hostList.getItems().remove(host);
            guestList.getItems().remove(guest);
        }
    }

    public void onListChange() {
        addButton.setDisable(hostList.getSelectionModel().getSelectedItem() == null || guestList.getSelectionModel().getSelectedItem() == null);
    }
}
