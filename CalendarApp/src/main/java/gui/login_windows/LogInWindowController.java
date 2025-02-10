package gui.login_windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login_handler.*;

import java.io.IOException;

public class LogInWindowController {
    private ILoginHandler loginHandler;
    private Stage stage;

    public LogInWindowController() {
        this.loginHandler = new LoginHandler("log_files/login.log");
    }

    public TextField usernameTextField;
    public PasswordField passwordTextField;


    @FXML
    private void logInButtonHandler() throws IOException {
        String username = this.usernameTextField.getText();
        String password = this.passwordTextField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if(this.loginHandler.correctLogin(username, password)) {
                this.openCalendarWindow();
                this.stage.close();
                this.loginHandler.logToFile("User " + username + " connected to the app");
            }
            else {
                this.showErrorWindow("Login Error", "Username or password incorrect!");
            }

        }
    }

    @FXML
    private void signUpButtonHandler() {
        String username = this.usernameTextField.getText();
        String password = this.passwordTextField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (!this.loginHandler.userExists(username)) {
                this.loginHandler.signUpUser(username, password);
                this.showConfirmationWindow("Success", "User account created!");
                this.loginHandler.logToFile("User " + username + " signed up to the app");
            }
            else {
                this.showErrorWindow("Sign Up Error", "User with this username already exists!");
            }

        }
    }

    private void openCalendarWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CalendarWindow.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calendar Window");
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorWindow(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationWindow(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void closeConnection() {
        this.loginHandler.closeConnection();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
