module com.example.proba {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires Modell;
    requires java.desktop;

    opens Classes to javafx.fxml;
    exports Classes;
}