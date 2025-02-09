package gui.login_windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/LogInWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Please Log In");
        stage.setScene(scene);

        LogInWindowController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(_ -> {
            controller.closeConnection();
        });

        stage.show();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch();
    }
}
