module ec.edu.espol.proyecto_toma_decisiones {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ec.edu.espol.proyecto_toma_decisiones to javafx.fxml;
    exports ec.edu.espol.proyecto_toma_decisiones;
}
