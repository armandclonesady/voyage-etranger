package ihm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import devoo.AffectListComparator;
import devoo.Country;
import devoo.Criterion;
import devoo.CriterionName;
import devoo.IdComparator;
import devoo.Platform;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements EventHandler<ActionEvent>{

    @FXML ComboBox<Country> countryComboBox;
    @FXML ListView<Teenager> teenagerList;
    @FXML TextField search;
    @FXML Button reaffect;
    @FXML ComboBox<Country> hostComboBox;
    @FXML ComboBox<Country> guestComboBox;
    @FXML ListView<Map.Entry<Teenager,Teenager>> list;
    @FXML ListView<Map.Entry<Teenager,Teenager>> fixedPairList;
    @FXML ListView<Map.Entry<Teenager,Teenager>> avoidedPairList;
    @FXML Button suppButton;
    @FXML Button suppButton2;
    @FXML Label hasHistory;

    @FXML Label name;
    @FXML Label forename;
    @FXML Label country;
    @FXML Label gender;
    @FXML Label birthdate;
    @FXML Label hasAnimalLabel;
    @FXML Label allergyAnimalLabel;
    @FXML Label hostFoodLabel;
    @FXML Label guestFoodLabel;
    @FXML Label hobbiesLabel;
    @FXML Label pairGenderLabel;
    @FXML Label historyLabel;

    static File selectedHistory;

    public void initialize() {
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

        hostComboBox.getSelectionModel().select(EcranIntroController.selectedHost);
        guestComboBox.getSelectionModel().select(EcranIntroController.selectedGuest);

        EcranIntro.platform.importCSV(EcranIntroController.selectedFile);

        teenagerList.getItems().setAll(EcranIntro.platform.getStudents());
        teenagerList.getItems().sort(new IdComparator());

        EcranIntro.platform.affectation(EcranIntroController.selectedHost, EcranIntroController.selectedGuest);

        list.setCellFactory(new AffectListFactory());
        list.getItems().setAll(EcranIntro.platform.getAffectation().entrySet());
        list.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    seeAffectation();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        list.getItems().sort(new AffectListComparator());

        fixedPairList.setCellFactory(new AffectListFactory());
        fixedPairList.getItems().setAll(EcranIntro.platform.getPairFixed().entrySet());
        fixedPairList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onFixedPairListChange();
        });

        avoidedPairList.setCellFactory(new AffectListFactory());
        avoidedPairList.getItems().setAll(EcranIntro.platform.getPairAvoided().entrySet());
        avoidedPairList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onAvoidedPairListChange();
        });
    }

    private void onFixedPairListChange() {
        if (fixedPairList.getSelectionModel().getSelectedItem() != null) {
            suppButton.setDisable(false);
        }
    }

    private void onAvoidedPairListChange() {
        if (avoidedPairList.getSelectionModel().getSelectedItem() != null) {
            suppButton2.setDisable(false);
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
        teenagerList.getSelectionModel().clearSelection();
    }

    public void onStudentsListChange() {
        if (teenagerList.getSelectionModel().getSelectedItem() == null) {
            name.setText("");
            forename.setText("");
            country.setText("");
            birthdate.setText("");
            hasAnimalLabel.setText("");
            allergyAnimalLabel.setText("");
            hostFoodLabel.setText("");
            guestFoodLabel.setText("");
            hobbiesLabel.setText("");
            pairGenderLabel.setText("");
            historyLabel.setText("");

            return;
        }
        name.setText(teenagerList.getSelectionModel().getSelectedItem().getName());
        forename.setText(teenagerList.getSelectionModel().getSelectedItem().getForename());
        country.setText(teenagerList.getSelectionModel().getSelectedItem().getCountry().toString());
        birthdate.setText(teenagerList.getSelectionModel().getSelectedItem().getBirth().toString());
        gender.setText(teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.GENDER).getValue() == "male" ? "Homme" : "Femme");
        Criterion criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.HOST_HAS_ANIMAL);
        if (criterion != null) {
            hasAnimalLabel.setText(criterion.getValue() == "true" ? "Oui" : "Non");
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY);
        if (criterion != null) {
            allergyAnimalLabel.setText(criterion.getValue() == "true" ? "Oui" : "Non");
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.HOST_FOOD);
        if (criterion != null) {
            hostFoodLabel.setText(criterion.getValue());
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.GUEST_FOOD);
        if (criterion != null) {
            guestFoodLabel.setText(criterion.getValue());
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.HOBBIES);
        if (criterion != null) {
            hobbiesLabel.setText(criterion.getValue());
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.PAIR_GENDER);
        if (criterion != null) {
            pairGenderLabel.setText(criterion.getValue() == "male" ? "Homme" : "Femme");
        }
        criterion = teenagerList.getSelectionModel().getSelectedItem().getCriterion(CriterionName.HISTORY);
        if (criterion != null) {
            historyLabel.setText(criterion.getValue());
        }
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
        teenagerList.getSelectionModel().clearSelection();
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
        list.getItems().sort(new AffectListComparator());
    }

    public void openFixedPairModal() throws IOException {
        EcranIntro.pairModalStage = new Stage();
        EcranIntro.pairModalStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("fxml/FixedPairModal.fxml");
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
        EcranIntro.pairModalStage.setTitle("Ajouter fixation");
        EcranIntro.pairModalStage.show();
        EcranIntro.pairModalStage.setOnCloseRequest(this::onCloseFixedPairModal);
    }

    public void openAvoidedPairModal() throws IOException {
        EcranIntro.pairModalStage = new Stage();
        EcranIntro.pairModalStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("fxml/AvoidedPairModal.fxml");
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
        EcranIntro.pairModalStage.setTitle("Ajouter fixation");
        EcranIntro.pairModalStage.show();
        EcranIntro.pairModalStage.setOnCloseRequest(this::onCloseAvoidedPairModal);
    }

    public void onCloseFixedPairModal(WindowEvent event) {
        if (EcranIntro.pairModalStage != null) {
            EcranIntro.pairModalStage.close();
        }
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry : EcranIntro.platform.getPairFixed().entrySet()) {
            items.add(entry);
        }
        fixedPairList.setItems(items);
    }

    public void suppressFixedPair() {
        Map.Entry<Teenager,Teenager> entry = fixedPairList.getSelectionModel().getSelectedItem();
        if (entry == null) {
            return;
        }
        EcranIntro.platform.getPairFixed().remove(entry.getKey());
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry2 : EcranIntro.platform.getPairFixed().entrySet()) {
            items.add(entry2);
        }
        fixedPairList.setItems(items);
        fixedPairList.getSelectionModel().clearSelection();
    }

    public void onCloseAvoidedPairModal(WindowEvent event) {
        if (EcranIntro.pairModalStage != null) {
            EcranIntro.pairModalStage.close();
        }
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry : EcranIntro.platform.getPairAvoided().entrySet()) {
            items.add(entry);
        }
        avoidedPairList.setItems(items);
    }

    public void suppressAvoidedPair() {
        Map.Entry<Teenager,Teenager> entry = avoidedPairList.getSelectionModel().getSelectedItem();
        if (entry == null) {
            return;
        }
        EcranIntro.platform.getPairAvoided().remove(entry.getKey());
        ObservableList<Map.Entry<Teenager, Teenager>> items = FXCollections.observableArrayList();
        for (Map.Entry<Teenager, Teenager> entry2 : EcranIntro.platform.getPairAvoided().entrySet()) {
            items.add(entry2);
        }
        avoidedPairList.setItems(items);
        avoidedPairList.getSelectionModel().clearSelection();
    }

    public void seeAffectation() throws IOException {
        AffectationShowModalController.currentEntry = list.getSelectionModel().getSelectedItem();
        if (AffectationShowModalController.currentEntry == null) {
            return;
        }
        EcranIntro.affectationShowModalStage = new Stage();
        EcranIntro.affectationShowModalStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("fxml/SeeAffectation.fxml");
        if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        EcranIntro.affectationShowModalStage.initModality(Modality.WINDOW_MODAL);
        EcranIntro.affectationShowModalStage.initOwner(EcranIntro.mainStage);
        EcranIntro.affectationShowModalStage.setScene(scene);
        EcranIntro.affectationShowModalStage.setTitle("Voir affectation");
        EcranIntro.affectationShowModalStage.show();
    }

    public void loadHistory() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(Platform.historyPath);
        fc.setSelectedExtensionFilter(new ExtensionFilter("bin", "*.bin"));
        MainController.selectedHistory = fc.showOpenDialog(null);
        if (selectedHistory == null) {
            return;
        }
        while (!(selectedHistory.getName().startsWith(EcranIntroController.selectedHost + "-" + EcranIntroController.selectedGuest))) {
            Alert alertImport = new Alert(AlertType.ERROR, "Cette historique ne correspond pas à l'échange sélectionné");
            alertImport.showAndWait();
            if (!alertImport.isShowing()) {
                MainController.selectedHistory = fc.showOpenDialog(null);
            }
        }
        EcranIntro.platform.loadHistory(selectedHistory);
        hasHistory.setText("Oui");
    }

    public void saveHistory() {
        EcranIntro.platform.exportBin();
    }
}
