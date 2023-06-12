package ihm;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import devoo.Country;
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
        System.out.println("Initialisation...");
        hostComboBox.getItems().setAll(Country.values());
        guestComboBox.getItems().setAll(Country.values());
        /*hostComboBox.getSelectionModel().selectedItemProperty().addListener(this::onHostComboBoxChange);
        guestComboBox.getSelectionModel().selectedItemProperty().addListener(this::onGuestComboBoxChange);*/
    }

    public void onImportAction() {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new ExtensionFilter("csv", "CSV File"));
        selectedFile = fc.showOpenDialog(null);
        while (!(selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")).equals(".csv"))) {
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

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("ParamModal.fxml");
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
        EcranIntro.paramModalStage.setTitle("FXML demo");
        EcranIntro.paramModalStage.show();
    }

    public void onStartAction() {
        if (selectedFile != null) {
            if (hostComboBox.getSelectionModel().getSelectedItem() != null && guestComboBox.getSelectionModel().getSelectedItem() != null) {
                EcranIntro.ecranIntroStage.hide();
                try {
                    initMainStage();
                    EcranIntro.mainStage.show();
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

        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("Main.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        EcranIntro.mainStage.setScene(scene);
        EcranIntro.mainStage.setTitle("Main");
        EcranIntro.mainStage.hide();

        EcranIntro.platform.affectation(hostComboBox.getSelectionModel().getSelectedItem(), guestComboBox.getSelectionModel().getSelectedItem());
    }
}
