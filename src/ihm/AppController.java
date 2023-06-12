package ihm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import devoo.Country;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AppController implements EventHandler<ActionEvent> {
    
    @FXML Label selectedLabel;
    @FXML ComboBox<Country> hostComboBox;
    @FXML ComboBox<Country> guestComboBox;

    File selectedFile;
    static Country selectedHost;
    static Country selectedGuest;

    public void initialize() {
        System.out.println("Initialisation...");
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());
        /*hostComboBox.getSelectionModel().selectedItemProperty().addListener(this::onHostComboBoxChange);
        guestComboBox.getSelectionModel().selectedItemProperty().addListener(this::onGuestComboBoxChange);*/
    }

    public void onImportAction() {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new ExtensionFilter("csv", "CSV File"));
        fc.setInitialDirectory(Platform.ressourcesPath);
        selectedFile = fc.showOpenDialog(null);
        while (!(selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")).equals(".csv"))) {
            Alert alertImport = new Alert(AlertType.ERROR, "Vous n'avez pas importer de fichier CSV");
            alertImport.showAndWait();
            selectedFile = fc.showOpenDialog(null);
        }
        selectedLabel.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
        hostComboBox.setDisable(false);
        guestComboBox.setDisable(false);
        
    }

    public void onSettingsAction() throws IOException {
        App.paramModalStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("Param.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        App.paramModalStage.initOwner(App.mainStage);
        App.paramModalStage.initModality(Modality.WINDOW_MODAL);
        App.paramModalStage.setScene(scene);
        App.paramModalStage.setTitle("FXML demo");
        App.paramModalStage.show();
    }

    public void addNewPair() throws IOException {
        App.pairModalStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("FixationModal.fxml");
        if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        App.pairModalStage.initModality(Modality.WINDOW_MODAL);
        App.pairModalStage.initOwner(App.mainStage);
        App.pairModalStage.setScene(scene);
        App.pairModalStage.setTitle("Fixation Modal");
        App.pairModalStage.show();
    }

    public void updatePair() {
        /*pairlist.getItems().clear();
        list.getItems().setAll(App.platform.getAffectation().entrySet());*/
    }

    public void onStartAction() {
        if (selectedFile != null) {
            if (hostComboBox.getSelectionModel().getSelectedItem() != null && guestComboBox.getSelectionModel().getSelectedItem() != null) {
                App.ecranIntroStage.hide();
                App.mainStage.show();
            } else {
                Alert alertCountry = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Vous n'avez pas choisis les pays nécessaires !");
                alertCountry.show();
            }
        } else {
            Alert alertImport = new Alert(AlertType.ERROR, "Vous n'avez pas importer de fichier CSV");
            alertImport.show();
        }
    }
}
