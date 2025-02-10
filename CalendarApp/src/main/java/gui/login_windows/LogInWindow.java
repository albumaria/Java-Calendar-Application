package gui.login_windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInWindow extends Application {
    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/LogInWindow.fxml"));

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Please Log In");
        stage.setScene(scene);


        LogInWindowController controller = fxmlLoader.getController();

        controller.setStage(stage);

        stage.show();
    }

    @Override
    public void stop() {
        LogInWindowController controller = fxmlLoader.getController();
        controller.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}
