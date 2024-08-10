module ec.edu.espol.proyectosegundop {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.proyectosegundop to javafx.fxml;
    exports ec.edu.espol.proyectosegundop;
}
