package ihm;

import java.io.IOException;
import java.net.URL;

import devoo.Platform;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EcranIntro extends Application {

        public static Platform platform;
        public static Stage ecranIntroStage;
        public static Stage mainStage;
        public static Stage paramModalStage;
        public static Stage pairModalStage;
        public static Stage affectationShowModalStage;
        public static Stage historyStage;

        public void start(Stage stage) throws IOException {
                platform = new Platform();
                EcranIntro.ecranIntroStage = stage;
                EcranIntro.ecranIntroStage.setResizable(false);

                FXMLLoader loader = new FXMLLoader();
                URL fxmlFileUrl = getClass().getResource("fxml/EcranIntro.fxml");
                if (fxmlFileUrl == null) {
                        System.out.println("Impossible de charger le fichier fxml");
                        System.exit(-1);
                }
                loader.setLocation(fxmlFileUrl);
                Parent root = loader.load();

                Scene scene = new Scene(root);
                ecranIntroStage.setScene(scene);
                ecranIntroStage.setTitle("Application SAE");
                ecranIntroStage.show();
        }
        public static void main(String[] args) {
                EcranIntro.launch(args);
        }
}
