module org.example.javalabup {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires java.sql;
    //requires org.slf4j.simple;
    requires org.slf4j;
    //requires org.slf4j.simple;

    opens org.example.javalabup to javafx.fxml, com.google.gson,javafx.base;
    opens org.example.javalabup.Forwarding to javafx.fxml, com.google.gson,javafx.base;
    opens org.example.javalabup.model to javafx.fxml, com.google.gson,javafx.base;
    opens org.example.javalabup.Objects to javafx.fxml, com.google.gson,javafx.base;
    opens org.example.javalabup.enums to javafx.fxml, com.google.gson;

    exports org.example.javalabup;

}