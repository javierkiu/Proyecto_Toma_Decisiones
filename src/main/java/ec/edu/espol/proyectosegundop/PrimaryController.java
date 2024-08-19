package ec.edu.espol.proyectosegundop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {

    private static Scene scene;
    private boolean questionsLoaded = false;
    private boolean answersLoaded = false;
    private List<String> preguntas;
    private Map<String, List<String>> respuestas;
    private ArbolDesicion<String> arbolDesicion;
    private int questionIndex = 0;
    private List<String> userResponses = new ArrayList<>();
    private int maxQuestions;
    private Stage primaryStage; // Mantén una referencia al primaryStage

    public PrimaryController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void iniciarJuego()throws IOException{ 
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
                        revisionArchivoscargados(primaryStage);
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
                        revisionArchivoscargados(primaryStage);
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
    private void revisionArchivoscargados(Stage primaryStage) {
        if (questionsLoaded && answersLoaded) {
            arbolDesicion = CargadorArchivos.construirArbol(preguntas, respuestas);
            cantidadPreguntas();
        }
    }
    
    private void cantidadPreguntas() {
        Label questionCountLabel = new Label("¿Cuántas preguntas desea responder?");
        TextField questionCountField = new TextField();
        Button startGameButton = new Button("Comenzar juego");

        startGameButton.setOnAction(event -> {
            maxQuestions = Integer.parseInt(questionCountField.getText());
            questionIndex = 0;
            userResponses.clear();
            mostrarPregunta(primaryStage);
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
        layout.getChildren().addAll(questionCountLabel, questionCountField, startGameButton);

        Scene inputScene = new Scene(layout, 400, 300);
        primaryStage.setScene(inputScene);
    }
    
    private void mostrarPregunta(Stage primaryStage) {
        if (questionIndex < maxQuestions && questionIndex < preguntas.size()) {
            String currentQuestion = preguntas.get(questionIndex);
            Label questionLabel = new Label(currentQuestion);
            Button yesButton = new Button("si");
            Button noButton = new Button("no");

            yesButton.setOnAction(e -> respuestaUsuario(primaryStage, "si"));
            noButton.setOnAction(e -> respuestaUsuario(primaryStage, "no"));

            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
            layout.getChildren().addAll(questionLabel, yesButton, noButton);

            Scene questionScene = new Scene(layout, 400, 300);
            primaryStage.setScene(questionScene);
        } else {
            resultadoFinal(primaryStage,null);
        }
    }
    
     // Maneja la respuesta del usuario
    private void respuestaUsuario(Stage primaryStage, String response) {
        userResponses.add(response);
        questionIndex++;

        // Verifica si las respuestas actuales coinciden con un animal en el mapa
        String matchingAnimal = findMatchingAnimal();
        if (!matchingAnimal.equals("No se encontró un animal que coincida con las respuestas.")) {
            resultadoFinal(primaryStage, matchingAnimal);
        } else if (questionIndex < maxQuestions && questionIndex < preguntas.size()) {
            mostrarPregunta(primaryStage);
        } else {
            resultadoFinal(primaryStage, null);
        }
    }
    
    // Encuentra el animal que coincide con las respuestas del usuario
    private String findMatchingAnimal() {
        for (Map.Entry<String, List<String>> entry : respuestas.entrySet()) {
            if (entry.getValue().equals(userResponses)) {
                return entry.getKey();
            }
        }
        return "No se encontró un animal que coincida con las respuestas.";
    }

    // Muestra el resultado final
    private void resultadoFinal(Stage primaryStage, String foundAnimal) {
        String resultMessage;

        if (foundAnimal != null) {
        resultMessage = "El animal que pensaste es: " + foundAnimal;
        } 
        else if (questionIndex == preguntas.size()) {
            resultMessage = "El animal que pensaste es: " + findMatchingAnimal();
        } 
        else {
            resultMessage = "Fin del juego. No se encontró un animal que coincida con las respuestas.";
        }
        Label resultLabel = new Label(resultMessage);
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
        layout.getChildren().addAll(resultLabel);
        Scene resultScene = new Scene(layout, 400, 300);
        primaryStage.setScene(resultScene);
     }
}
