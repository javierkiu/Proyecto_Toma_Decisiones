package ec.edu.espol.proyectosegundop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        Label welcomeLabel=new Label("Bienvenido!!!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Crea los botones "Cargar preguntas" y "Cargar respuestas"
        Button loadQuestionsButton=new Button("Cargar preguntas");
        Button loadAnswersButton=new Button("Cargar respuestas");
        loadQuestionsButton.setStyle(
            "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; " +
            "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"
        );
        loadAnswersButton.setStyle(
            "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px; " +
            "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"
        );
        
        // Carga la imagen y crea un ImageView
        Image image = new Image("img/upload.png"); // Cambia la ruta por la ubicación de tu imagen
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80); // Ajusta el ancho de la imagen
        imageView.setFitHeight(80); // Ajusta la altura de la imagen
        imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen

        // Crea un contenedor HBox para organizar los botones y la imagen
        HBox buttonImageBox = new HBox(20);
        buttonImageBox.getChildren().addAll(loadQuestionsButton, loadAnswersButton, imageView);
        buttonImageBox.setPadding(new Insets(20, 0, 0, 0));
        buttonImageBox.setStyle("-fx-alignment: center;");


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
        // Crea un contenedor VBox para organizar la disposición de los elementos
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 50px; -fx-alignment: center;");
        root.getChildren().addAll(welcomeLabel, buttonImageBox);
        root.setSpacing(25);
        root.setAlignment(Pos.CENTER);

        // Crea una escena con el contenedor y un tamaño mayor
        Scene scene=new Scene(root, 600, 400);

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
        questionCountLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #34495e; -fx-padding: 10px;");

        TextField questionCountField = new TextField();
        questionCountField.setPromptText("Ingrese un número");
        questionCountField.setStyle(
            "-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #3498db; " +
            "-fx-border-radius: 5px; -fx-background-radius: 5px;"
        );
        Button startGameButton = new Button("Comenzar juego");
        startGameButton.setStyle(
            "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px; " +
            "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"
        );        
            startGameButton.setOnAction(event -> {
                try{
                    maxQuestions = Integer.parseInt(questionCountField.getText());
                    questionIndex = 0;
                    userResponses.clear();
                    mostrarPregunta(primaryStage);
                }
                catch(Exception ex){
                    Alert a = new Alert(AlertType.ERROR,"Solo numeros");
                    a.show();
                }
            });

            VBox layout = new VBox(15);
            layout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #ecf0f1; " +
                            "-fx-border-color: #bdc3c7; -fx-border-width: 2px; -fx-border-radius: 10px;");
            layout.getChildren().addAll(questionCountLabel, questionCountField, startGameButton);


            Scene inputScene = new Scene(layout, 600, 400);
            primaryStage.setScene(inputScene);      
        


    }
    
    private void mostrarPregunta(Stage primaryStage) {
        if (questionIndex < maxQuestions && questionIndex < preguntas.size()) {
            String currentQuestion = preguntas.get(questionIndex);
            Label questionLabel = new Label(currentQuestion);
            questionLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #2c3e50; -fx-padding: 15px;");

            Button yesButton = new Button("si");
            yesButton.setStyle(
               "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; " +
               "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"
           );           
            Button noButton = new Button("no");
            noButton.setStyle(
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"
            );
            yesButton.setOnAction(e -> respuestaUsuario(primaryStage, "si"));
            noButton.setOnAction(e -> respuestaUsuario(primaryStage, "no"));

            VBox layout = new VBox(15);
            layout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #ecf0f1; " +
                            "-fx-border-color: #bdc3c7; -fx-border-width: 2px; -fx-border-radius: 10px;");
            layout.getChildren().addAll(questionLabel, yesButton, noButton);

            Scene questionScene = new Scene(layout, 600, 400);
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
        List<String> posiblesRespuestas = new ArrayList<>();
        ImageView imgAnimal = null;
        if (foundAnimal != null) {
            resultMessage = "El animal que pensaste es: " + foundAnimal;
            try {
                imgAnimal = new ImageView(new Image("img/" + foundAnimal + ".jpg"));
            } catch (IllegalArgumentException e) {
                imgAnimal = new ImageView(new Image("img/questionMark.jpg"));
                imgAnimal.setFitHeight(50);
                imgAnimal.setFitWidth(50);
            }
                imgAnimal.setFitHeight(200);
                imgAnimal.setFitWidth(200);
        } 
        else if (questionIndex == preguntas.size()) {
            resultMessage = "El animal que pensaste es: " + findMatchingAnimal();
        } 
        else {
            resultMessage = "Fin del juego. No se encontró un animal que coincida con las respuestas.";
            posiblesRespuestas = arregloAnimalesPosibles();
        }
        Label resultLabel = new Label(resultMessage);
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");
        layout.getChildren().addAll(resultLabel);
        if(imgAnimal != null)
        {
            layout.getChildren().addAll(imgAnimal);
        }
        if(!posiblesRespuestas.isEmpty())
        {
            Label lblPosible = new Label("Posibles animales:");
            Label lblPosiblesAnimales = new Label(posiblesRespuestas.toString());
            layout.getChildren().addAll(lblPosible,lblPosiblesAnimales);
        }
        Scene resultScene = new Scene(layout, 600, 400);
        primaryStage.setScene(resultScene);
    }
    
    private List<String> arregloAnimalesPosibles()
    {
        List<String> arregloAnimalesPosibles = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : respuestas.entrySet()) 
        {
            List<String> subListaRespuestas = entry.getValue().subList(0, userResponses.size());
            if (subListaRespuestas.equals(userResponses)) 
            {
                arregloAnimalesPosibles.add(entry.getKey());
            }
        }
        return arregloAnimalesPosibles;
    }
    
}
