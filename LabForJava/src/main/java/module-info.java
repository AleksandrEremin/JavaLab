module org.example.labforjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;

    opens org.example.labforjava to javafx.fxml;
    exports org.example.labforjava;
    exports org.example.labforjava.Objects;
    opens org.example.labforjava.Objects to javafx.fxml;
}