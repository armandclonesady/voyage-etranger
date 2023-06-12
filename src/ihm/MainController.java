package ihm;

import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

    static Country selectedHost;
    static Country selectedGuest;

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

        App.platform.importCSV("adosAléatoires.csv");

        teenagerList.getItems().setAll(App.platform.getStudents());
        teenagerList.getItems().sort(new IdComparator());

        list.setCellFactory(new AffectListFactory());

        pairList.setCellFactory(new AffectListFactory());
        pairList.getItems().setAll(App.platform.getAffectation().entrySet());
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
            teenagerList.getItems().setAll(App.platform.getStudents());
        }
        for (Teenager student : App.platform.getStudents()) {
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
        selectedHost = hostComboBox.getSelectionModel().getSelectedItem();
    }

    public void onGuestComboBoxChange(ObservableValue<? extends Country> observable, Country oldCountry, Country newCountry) {
        if (oldCountry != null) {
            hostComboBox.getItems().add(oldCountry);
        }
        hostComboBox.getItems().remove(newCountry);
        selectedGuest = guestComboBox.getSelectionModel().getSelectedItem();
    }

    public void onReaffect() {
        list.getItems().clear();
        App.platform.affectation(hostComboBox.getSelectionModel().getSelectedItem(), guestComboBox.getSelectionModel().getSelectedItem());
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry : App.platform.getAffectation().entrySet()) {
            items.add(entry);
        }
        list.setItems(items);
    }
    
}
