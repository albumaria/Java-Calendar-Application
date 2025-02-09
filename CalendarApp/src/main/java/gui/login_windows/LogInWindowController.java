package gui.login_windows;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import login_window.*;

public class LogInWindowController {
    private ILoginDBHandler loginHandler;

    public LogInWindowController() {
        this.loginHandler = new LoginDBHandler();
    }

    public TextField usernameTextField;
    public TextField passwordTextField;

    @FXML
    private void logInButtonHandler() {
        String username = this.usernameTextField.getText();
        String password = this.passwordTextField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if(this.loginHandler.correctLogin(username, password)) {
                System.out.println("Connecting user..");
            }
            else {
                System.out.println("Incorrect login");
            }

        }
    }

    @FXML
    private void signUpButtonHandler() {
        String username = this.usernameTextField.getText();
        String password = this.passwordTextField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (!this.loginHandler.userExists(username))
                this.loginHandler.signUpUser(username, password);
            else {
                System.out.println("User already exists");
            }

        }
    }

    public void closeConnection() {
        this.loginHandler.closeConnection();
    }

}
