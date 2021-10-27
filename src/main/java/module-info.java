module com.example.randomnumbergenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;


    opens com.example.randomnumbergenerator to javafx.fxml;
    exports com.example.randomnumbergenerator;
}