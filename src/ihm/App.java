package ihm;

import java.io.IOException;
import java.net.URL;

import devoo.Platform;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

        public static Platform platform;
        public static Stage ecranIntroStage;
        public static Stage mainStage;
        public static Stage paramModalStage;
        public static Stage pairModalStage;

        public void start(Stage stage) throws IOException {
                platform = new Platform();
                App.ecranIntroStage = stage;
                App.ecranIntroStage.setResizable(false);

                FXMLLoader loader = new FXMLLoader();
                URL fxmlFileUrl = getClass().getResource("EcranIntro.fxml");
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
                App.launch(args);
        }
}
