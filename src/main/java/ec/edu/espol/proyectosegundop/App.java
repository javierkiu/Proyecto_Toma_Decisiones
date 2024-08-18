package ec.edu.espol.proyectosegundop;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private ArbolDecision arbol;
    private Nodo actual;
    private int maxPreguntas;
    private int contadorPreguntas;

    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.setTitle("Adivina el Animal");

        Button btnCargar = new Button("Cargar Archivos");
        btnCargar.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selecciona el archivo de preguntas");
                File archivoPreguntas = fileChooser.showOpenDialog(primaryStage);

                fileChooser.setTitle("Selecciona el archivo de respuestas");
                File archivoRespuestas = fileChooser.showOpenDialog(primaryStage);

                List<String> preguntas = CargadorArchivos.cargarPreguntas(archivoPreguntas.getAbsolutePath());
                Map<String, List<String>> respuestas = CargadorArchivos.cargarRespuestas(archivoRespuestas.getAbsolutePath());
                arbol = CargadorArchivos.construirArbol(preguntas, respuestas);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        TextField txtPreguntas = new TextField();
        txtPreguntas.setPromptText("Número máximo de preguntas");

        Button btnIniciar = new Button("Iniciar Juego");
        btnIniciar.setOnAction(e -> {
            maxPreguntas = Integer.parseInt(txtPreguntas.getText());
            contadorPreguntas = 0;
            actual = arbol.getRaiz();
            preguntar();
        });

        Label lblPregunta = new Label();
        Button btnSi = new Button("Sí");
        btnSi.setPrefWidth(100);
        Button btnNo = new Button("No");
        btnNo.setPrefWidth(100);
        
        btnSi.setOnAction(e -> {
            contadorPreguntas++;
            if (actual.getSi() != null) {
                actual = actual.getSi();
                preguntar();
            } else {
                lblPregunta.setText("El animal que pensaste es: " + actual.getPregunta());
            }
        });

        btnNo.setOnAction(e -> {
            contadorPreguntas++;
            if (actual.getNo() != null) {
                actual = actual.getNo();
                preguntar();
            } else {
                lblPregunta.setText("El animal que pensaste es: " + actual.getPregunta());
            }
        });

        VBox layout = new VBox(10);
        HBox cajaSino = new HBox(100);
        cajaSino.getChildren().addAll(btnSi,btnNo);
        cajaSino.setSpacing(50);
        cajaSino.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(btnCargar, txtPreguntas, btnIniciar, lblPregunta, cajaSino);
        layout.setSpacing(20.0);
        layout.setAlignment(Pos.CENTER);
        
        
        
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void preguntar() {
        if (contadorPreguntas < maxPreguntas && actual != null) {
            if (actual.getSi() == null && actual.getNo() == null) {
                // Llegamos a una hoja
                Label lblPregunta = new Label("El animal que pensaste es: " + actual.getPregunta());
            } else {
                // Hacer la pregunta
                Label lblPregunta = new Label(actual.getPregunta());
            }
        } else {
            Label lblPregunta = new Label("No se puede determinar el animal con las respuestas dadas.");
        }
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}

//Si se presenta problemas con el compilador de javafx, verificar que module-info y pom.xml tengan la misma ruta como clase main