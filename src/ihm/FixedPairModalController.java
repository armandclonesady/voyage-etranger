package ihm;

import java.util.Iterator;

import devoo.IdComparator;
import devoo.Teenager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FixedPairModalController extends MainController {

    @FXML Label hostLabel;
    @FXML Label guestLabel;

    @FXML ListView<Teenager> hostList;
    @FXML ListView<Teenager> guestList;

    @FXML TextField searchHost;
    @FXML TextField searchGuest;

    @FXML Button addButton;

    public void initialize() {
        hostLabel.setText(EcranIntroController.selectedHost.name());
        guestLabel.setText(EcranIntroController.selectedGuest.name());
        for (Teenager t : EcranIntro.platform.getStudents()) {
            if (!EcranIntro.platform.getPairFixed().containsKey(t) && !EcranIntro.platform.getPairFixed().containsValue(t)) {
                if (t.getCountry().equals(EcranIntroController.selectedHost) && t.getRegistered()) {
                hostList.getItems().add(t);
                }
                if (t.getCountry().equals(EcranIntroController.selectedGuest) && t.getRegistered()) {
                guestList.getItems().add(t);
                }
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

        searchHost.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearchHost();
        });

        searchGuest.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearchGuest();
        });

    }

    public void onAddButtonClicked() {
        Teenager host = hostList.getSelectionModel().getSelectedItem();
        Teenager guest = guestList.getSelectionModel().getSelectedItem();
        if (host != null && guest != null) {
            EcranIntro.platform.addPairFixed(host, guest);
            hostList.getItems().remove(host);
            guestList.getItems().remove(guest);
        }
    }

    public void onListChange() {
        addButton.setDisable(hostList.getSelectionModel().getSelectedItem() == null || guestList.getSelectionModel().getSelectedItem() == null);
    }

    public void onSearchHost() {
        hostList.getItems().clear();
        for (Teenager t : EcranIntro.platform.getStudents()) {
            if (!EcranIntro.platform.getPairFixed().containsKey(t) && t.getCountry().equals(EcranIntroController.selectedHost) && t.getRegistered()) {
                hostList.getItems().add(t);
            }
        }
        Iterator<Teenager> it = hostList.getItems().iterator();
        while (it.hasNext()) {
            Teenager student = it.next();
            if (!student.toString().toLowerCase().contains(searchHost.getText().toLowerCase())) {
                it.remove();
            }
        }
        hostList.getItems().sort(new IdComparator());
        hostList.getSelectionModel().clearSelection();
    }

    public void onSearchGuest() {
        guestList.getItems().clear();
        for (Teenager t : EcranIntro.platform.getStudents()) {
            if (!EcranIntro.platform.getPairFixed().containsValue(t) && t.getCountry().equals(EcranIntroController.selectedGuest) && t.getRegistered()) {
                guestList.getItems().add(t);
            }
        }
        Iterator<Teenager> it = guestList.getItems().iterator();
        while (it.hasNext()) {
            Teenager student = it.next();
            if (!student.toString().toLowerCase().contains(searchGuest.getText().toLowerCase())) {
                it.remove();
            }
        }
        guestList.getItems().sort(new IdComparator());
        guestList.getSelectionModel().clearSelection();
    }
}
