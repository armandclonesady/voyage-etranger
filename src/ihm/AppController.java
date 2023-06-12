package ihm;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AppController implements EventHandler<ActionEvent> {
    /**
     * PageTwo ids
     */
    @FXML
    ComboBox<Country> countryComboBox;
    @FXML
    ListView<Teenager> teenagerList;
    @FXML
    Label name;
    @FXML
    Label forename;
    @FXML 
    TextField search;
    @FXML
    Button reaffect;
    @FXML 
    ComboBox<Country> hostComboBox;
    @FXML
    ComboBox<Country> guestComboBox;
    @FXML
    ListView<Map.Entry<Teenager,Teenager>> list;
    /**
     * EcrenIntro ids
     */
    @FXML
    Label selectedLabel;
    @FXML 
    ComboBox<Country> hostComboBoxMenu;
    @FXML
    ComboBox<Country> guestComboBoxMenu;

    public void initialize() {
        System.out.println("Initialisation...");
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());
        hostComboBox.getSelectionModel().selectedItemProperty().addListener(this::onHostComboBoxChange);
        guestComboBox.getSelectionModel().selectedItemProperty().addListener(this::onGuestComboBoxChange);
        
    }

    public void initializePageTwo() {
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());


        countryComboBox.getItems().setAll(Country.values());
        countryComboBox.getItems().add(0, null);

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
    }

    public void onGuestComboBoxChange(ObservableValue<? extends Country> observable, Country oldCountry, Country newCountry) {
        if (oldCountry != null) {
            hostComboBox.getItems().add(oldCountry);
        }
        hostComboBox.getItems().remove(newCountry);
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

    public void onStartAction() {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new ExtensionFilter("csv", "CSV File"));
        File selectedFile = fc.showOpenDialog(null);
        while (!(selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")).equals(".csv"))) {
            selectedFile = fc.showOpenDialog(null);
        }
        selectedLabel.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
        hostComboBox.setDisable(false);
        guestComboBox.setDisable(false);
        
    }

    public void onSettingsAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("Param.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage paramStage = new Stage();
        paramStage.initOwner(stage);
        paramStage.initModality(Modality.WINDOW_MODAL);
        paramStage.setScene(scene);
        paramStage.setTitle("FXML demo");
        paramStage.show();
    }

    public void addNewPair() throws IOException {
        Stage stage2 = new Stage();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("FixationModal.fxml");
        if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(App.stage);
        stage2.setScene(scene);
        stage2.setTitle("Fixation Modal");
        stage2.show();
    }
}
