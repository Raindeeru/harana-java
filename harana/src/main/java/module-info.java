module com.harana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.harana to javafx.fxml, com.google.gson;
    exports com.harana;
}
