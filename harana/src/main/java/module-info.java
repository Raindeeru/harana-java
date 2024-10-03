module com.harana {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.harana to javafx.fxml;
    exports com.harana;
}
