package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import devoo.Country;
import devoo.IdComparator;
import devoo.Teenager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements EventHandler<ActionEvent>{

    @FXML ComboBox<Country> countryComboBox;
    @FXML ListView<Teenager> teenagerList;
    @FXML Label name;
    @FXML Label forename;
    @FXML TextField search;
    @FXML Button reaffect;
    @FXML ComboBox<Country> hostComboBox;
    @FXML ComboBox<Country> guestComboBox;
    @FXML ListView<Map.Entry<Teenager,Teenager>> list;
    @FXML ListView<Map.Entry<Teenager,Teenager>> pairList;
    @FXML Button suppButton;

    public void initialize() {
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());

        countryComboBox.setOnAction(this);
        teenagerList.getSelectionModel().selectionModeProperty().set(SelectionMode.SINGLE);
        teenagerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onStudentsListChange();
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });

        hostComboBox.getSelectionModel().selectedItemProperty().addListener(this::onHostComboBoxChange);
        guestComboBox.getSelectionModel().selectedItemProperty().addListener(this::onGuestComboBoxChange);

        hostComboBox.getSelectionModel().selectFirst();
        guestComboBox.getSelectionModel().selectFirst();

        EcranIntro.platform.importCSV("adosAléatoires.csv");

        teenagerList.getItems().setAll(EcranIntro.platform.getStudents());
        teenagerList.getItems().sort(new IdComparator());

        EcranIntro.platform.affectation(EcranIntroController.selectedHost, EcranIntroController.selectedGuest);

        list.setCellFactory(new AffectListFactory());
        list.getItems().setAll(EcranIntro.platform.getAffectation().entrySet());

        pairList.setCellFactory(new AffectListFactory());
        pairList.getItems().setAll(EcranIntro.platform.getPairFixed().entrySet());
        pairList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onPairListChange();
        });
    }

    private void onPairListChange() {
        if (pairList.getSelectionModel().getSelectedItem() != null) {
            suppButton.setDisable(false);
        }
    }

    public void handle(ActionEvent event) {
        if (event.getTarget() == countryComboBox) {
            onCountryComboBoxChange();
        }
        else if (event.getTarget() == teenagerList) {
            onStudentsListChange();
        }
    }
    
    public void onCountryComboBoxChange() {
        teenagerList.getItems().clear();
        if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
            teenagerList.getItems().setAll(EcranIntro.platform.getStudents());
        }
        for (Teenager student : EcranIntro.platform.getStudents()) {
            if (student.getCountry() == countryComboBox.getSelectionModel().getSelectedItem()) {
                teenagerList.getItems().add(student);
            }
        }
        teenagerList.getItems().sort(new IdComparator());
        teenagerList.getSelectionModel().selectFirst();
    }

    public void onStudentsListChange() {
        if (teenagerList.getSelectionModel().getSelectedItem() == null) {
            name.setText("");
            forename.setText("");
            return;
        }
        name.setText(teenagerList.getSelectionModel().getSelectedItem().getName());
        forename.setText(teenagerList.getSelectionModel().getSelectedItem().getForename());
    }

    public void onSearch() {
        onCountryComboBoxChange();
        Iterator<Teenager> it = teenagerList.getItems().iterator();
        while (it.hasNext()) {
            Teenager student = it.next();
            if (!student.toString().toLowerCase().contains(search.getText().toLowerCase())) {
                it.remove();
            }
        }
        teenagerList.getItems().sort(new IdComparator());
        teenagerList.getSelectionModel().selectFirst();
    }

    public void onHostComboBoxChange(ObservableValue<? extends Country> observable, Country oldCountry, Country newCountry) {
        if (oldCountry != null) {
            guestComboBox.getItems().add(oldCountry);
        }
        guestComboBox.getItems().remove(newCountry);
        EcranIntroController.selectedHost = hostComboBox.getSelectionModel().getSelectedItem();
    }

    public void onGuestComboBoxChange(ObservableValue<? extends Country> observable, Country oldCountry, Country newCountry) {
        if (oldCountry != null) {
            hostComboBox.getItems().add(oldCountry);
        }
        hostComboBox.getItems().remove(newCountry);
        EcranIntroController.selectedGuest = guestComboBox.getSelectionModel().getSelectedItem();
    }

    public void onReaffect() {
        list.getItems().clear();
        EcranIntro.platform.affectation(hostComboBox.getSelectionModel().getSelectedItem(), guestComboBox.getSelectionModel().getSelectedItem());
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry : EcranIntro.platform.getAffectation().entrySet()) {
            items.add(entry);
        }
        list.setItems(items);
    }

    public void openPairModal() throws IOException {
        pairList.getItems().clear();
        EcranIntro.pairModalStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("FixationModal.fxml");
        if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        EcranIntro.pairModalStage.initModality(Modality.WINDOW_MODAL);
        EcranIntro.pairModalStage.initOwner(EcranIntro.mainStage);
        EcranIntro.pairModalStage.setScene(scene);
        EcranIntro.pairModalStage.setTitle("Fixation Modal");
        EcranIntro.pairModalStage.show();
        EcranIntro.pairModalStage.setOnCloseRequest(this::onClosePairModal);
    }

    public void onClosePairModal(WindowEvent event) {
        if (EcranIntro.pairModalStage != null) {
            EcranIntro.pairModalStage.close();
        }
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry : EcranIntro.platform.getPairFixed().entrySet()) {
            items.add(entry);
        }
        pairList.setItems(items);
    }

    public void suppressPair() {
        Map.Entry<Teenager, Teenager> entry = pairList.getSelectionModel().getSelectedItem();
        if (entry == null) {
            return;
        }
        EcranIntro.platform.getPairFixed().remove(entry.getKey());
        pairList.getItems().remove(entry);
    }
}
