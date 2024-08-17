package ec.edu.espol.proyectosegundop;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private boolean questionsLoaded = false;
    private boolean answersLoaded = false;
    private List<String> preguntas;
    private Map<String, List<String>> respuestas;
    private ArbolDesicion<String> arbolDesicion;

    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.setTitle("Adivina el Animal");
        Label welcomeLabel=new Label("Bienvenido");
        welcomeLabel.setStyle("-fx-font-size: 24px;");
        
        // Crea los botones "Cargar preguntas" y "Cargar respuestas"
        Button loadQuestionsButton=new Button("Cargar preguntas");
        Button loadAnswersButton=new Button("Cargar respuestas");

        // Configura el FileChooser
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo de Texto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));

        loadQuestionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        preguntas = CargadorArchivos.cargarPreguntas(file.getAbsolutePath());                       
                        questionsLoaded=true;
                        checkFilesLoaded(primaryStage);
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Manejador de eventos para el botón "Cargar respuestas"
        loadAnswersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent event) {
                File file= fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        respuestas=CargadorArchivos.cargarRespuestas(file.getAbsolutePath());
                        answersLoaded=true;
                        checkFilesLoaded(primaryStage);
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Crea un VBox para organizar el contenido
        VBox root=new VBox();
        root.setStyle("-fx-padding: 20; -fx-alignment: top-center;");
        root.getChildren().addAll(welcomeLabel, loadQuestionsButton, loadAnswersButton);

        // Crea una escena con el contenedor y un tamaño mayor
        Scene scene=new Scene(root, 400, 300);

        // Configura la escena en el escenario
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    // Verifica si ambos archivos fueron cargados
    private void checkFilesLoaded(Stage primaryStage) {
        if (questionsLoaded && answersLoaded) {
            arbolDesicion = CargadorArchivos.construirArbol(preguntas, respuestas);
            showQuestionInputScene(primaryStage);
        }
    }

    // Muestra la nueva escena para ingresar el número de preguntas
    private void showQuestionInputScene(Stage primaryStage) {
        Label questionCountLabel = new Label("¿Cuántas preguntas desea responder?");
        TextField questionCountField = new TextField();
        Button startGameButton = new Button("Comenzar juego");
        
        startGameButton.setOnAction(event -> {
        int numQuestions = Integer.parseInt(questionCountField.getText());
        ArbolDesicion<String> decisionTree = arbolDesicion;
        startGame(primaryStage, decisionTree.getRoot(), numQuestions);
        });
        
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
        layout.getChildren().addAll(questionCountLabel, questionCountField, startGameButton);
        
        Scene inputScene = new Scene(layout, 400, 300);
        primaryStage.setScene(inputScene);
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }  
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void startGame(Stage primaryStage, Nodo<String> currentNode, int remainingQuestions) {
        if (currentNode == null || remainingQuestions == 0) {
            return;
        }

        Label questionLabel = new Label(currentNode.getPregunta());
        Button yesButton = new Button("si");
        Button noButton = new Button("no");

        yesButton.setOnAction(event -> startGame(primaryStage, currentNode.getSi(), remainingQuestions - 1));
        noButton.setOnAction(event -> startGame(primaryStage, currentNode.getNo(), remainingQuestions - 1));

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
        layout.getChildren().addAll(questionLabel, yesButton, noButton);

        Scene gameScene = new Scene(layout, 400, 300);
        primaryStage.setScene(gameScene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
//    private void preguntar() {
//        if (contadorPreguntas < maxPreguntas && actual != null) {
//            if (actual.getSi() == null && actual.getNo() == null) {
//                // Llegamos a una hoja
//                Label lblPregunta = new Label("El animal que pensaste es: " + actual.getPregunta());
//            } else {
//                // Hacer la pregunta
//                Label lblPregunta = new Label(actual.getPregunta());
//            }
//        } else {
//            Label lblPregunta = new Label("No se puede determinar el animal con las respuestas dadas.");
//        }
//    }

}