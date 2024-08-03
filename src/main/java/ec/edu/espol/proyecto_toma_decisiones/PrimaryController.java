/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.proyecto_toma_decisiones;

import arboles.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Utilidades;

/**
 * FXML Controller class
 *
 * @author JAVIER
 */
public class PrimaryController implements Initializable {

    @FXML
    private Label pregunta;
    @FXML
    private TextField numField;
    @FXML
    private Label errorMessage;
    private List<String> preguntas;
    private List<String> respuestas;
    private StringBuilder respuesta;
    
    private int numPreguntas = 0;
    BinaryTree<String> arbolPreguntas;
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preguntas = Utilidades.leerTxt("src/main/resources/files/preguntas.txt");
        respuestas = Utilidades.leerTxt("src/main/resources/files/respuestas.txt");
        arbolPreguntas = Utilidades.crearArbol(preguntas);
        arbolPreguntas.recorrerPreOrden();
    }    

    @FXML
    private void submitNumQ(ActionEvent event) {
        try{
            numPreguntas = Integer.parseInt(numField.getText());
            if(!preguntas.isEmpty()){
                numPreguntas--;
                errorMessage.setText("Preguntas restantes: "+ numPreguntas);
                pregunta.setText(preguntas.remove(0));
        }
        } catch(NumberFormatException E){
            errorMessage.setText("Error! Solo se aceptan valores enteros");
        }
    }

    @FXML
    private void yesAnswer(ActionEvent event) {
        if(!preguntas.isEmpty() && numPreguntas > 0){
            numPreguntas--;
            errorMessage.setText("Preguntas restantes: "+ numPreguntas);
            pregunta.setText(preguntas.remove(0));
            respuesta.append("1");

        }
    }

    @FXML
    private void NoAnswer(ActionEvent event) {
        if(!preguntas.isEmpty() && numPreguntas > 0){
            numPreguntas--;
            errorMessage.setText("Preguntas restantes: "+ numPreguntas);
            pregunta.setText(preguntas.remove(0));
            
       
        }
    }
    
}
