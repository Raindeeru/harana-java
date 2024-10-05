module com.harana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.net.http;
    requires javafx.media;
    requires se.michaelthelin.spotify;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires nv.i18n;
    requires java.desktop;

    opens com.harana to javafx.fxml, com.google.gson, javafx.media, se.michaelthelin.spotify, nv.i18n, org.apache.httpcomponents.core5.httpcore5;
    opens com.harana.users to com.google.gson;
    exports com.harana;
}
