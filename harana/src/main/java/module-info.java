module com.harana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.net.http;
    requires se.michaelthelin.spotify;
    requires org.apache.httpcomponents.core5.httpcore5;

    opens com.harana to javafx.fxml, com.google.gson, se.michaelthelin.spotify, org.apache.httpcomponents.core5.httpcore5;
    opens com.harana.users to com.google.gson;
    exports com.harana;
}
