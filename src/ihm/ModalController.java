package ihm;

import devoo.Country;

public class ModalController extends AppController {

    public void initialize() {
        System.out.println(hostComboBox.getSelectionModel().getSelectedItem());
        Country host = hostComboBox.getSelectionModel().getSelectedItem();
        Country guest = guestComboBox.getSelectionModel().getSelectedItem();
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());
        hostComboBox.getSelectionModel().select(host);
        guestComboBox.getSelectionModel().select(guest);
    }
}
