module com.harana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.net.http;

    opens com.harana to javafx.fxml, com.google.gson;
    opens com.harana.users to com.google.gson;
    exports com.harana;
}
