module com.example.CalendarApp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires jdk.jdi;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;

    opens gui to javafx.fxml;
    exports gui.login_windows;
    exports gui.calendar_windows;
    opens gui.login_windows to javafx.fxml;
}