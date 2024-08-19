package ec.edu.espol.proyectosegundop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        PrimaryController controller = new PrimaryController(primaryStage);
        controller.iniciarJuego();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }  
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}