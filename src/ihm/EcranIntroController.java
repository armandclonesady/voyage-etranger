package ihm;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import devoo.Country;
import devoo.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EcranIntroController {
    
    @FXML Label selectedLabel;
    @FXML ComboBox<Country> hostComboBox;
    @FXML ComboBox<Country> guestComboBox;

    static File selectedFile;
    static Country selectedHost;
    static Country selectedGuest;

    public void initialize() {
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());
        hostComboBox.getSelectionModel().selectedItemProperty().addListener(this::onHostComboBoxChange);
        guestComboBox.getSelectionModel().selectedItemProperty().addListener(this::onGuestComboBoxChange);
    }

    public void onImportAction() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(Platform.ressourcesPath);
        fc.setSelectedExtensionFilter(new ExtensionFilter("csv", "CSV File"));
        selectedFile = fc.showOpenDialog(null);
        while (selectedFile == null || !(selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")).equals(".csv"))) {
            Alert alertImport = new Alert(AlertType.ERROR, "Vous n'avez pas importer de fichier CSV");
            alertImport.showAndWait();
            if (!alertImport.isShowing()) {
                selectedFile = fc.showOpenDialog(null);
            }
        }
        selectedLabel.setText(selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
        hostComboBox.setDisable(false);
        guestComboBox.setDisable(false);
        
    }

    public void onSettingsAction() throws IOException {
        EcranIntro.paramModalStage = new Stage();
        EcranIntro.paramModalStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("fxml/ParamModal.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        EcranIntro.paramModalStage.initOwner(EcranIntro.mainStage);
        EcranIntro.paramModalStage.initModality(Modality.WINDOW_MODAL);
        EcranIntro.paramModalStage.setScene(scene);
        EcranIntro.paramModalStage.setTitle("Paramètres");
        EcranIntro.paramModalStage.show();
    }

    public void onStartAction() {
        if (selectedFile != null) {
            if (hostComboBox.getSelectionModel().getSelectedItem() != null && guestComboBox.getSelectionModel().getSelectedItem() != null) {
                EcranIntro.ecranIntroStage.hide();
                try {
                    initMainStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertCountry = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Vous n'avez pas choisis les pays nécessaires !");
                alertCountry.showAndWait();
            }
        } else {
            Alert alertImport = new Alert(AlertType.ERROR, "Vous n'avez pas importer de fichier CSV");
            alertImport.showAndWait();
        }
    }

    public void initMainStage() throws IOException {
        EcranIntro.mainStage = new Stage();
        EcranIntro.mainStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("fxml/Main.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        EcranIntro.mainStage.setScene(scene);
        EcranIntro.mainStage.setTitle("Programme Principal");
        EcranIntro.mainStage.show();
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
        EcranIntroController.selectedGuest = guestComboBox.getSelectionModel().getSelectedItem();;
    }
}
