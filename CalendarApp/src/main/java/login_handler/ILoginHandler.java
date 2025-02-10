package login_handler;

public interface ILoginHandler {
    void signUpUser(String username, String password);
    boolean userExists(String username);
    boolean correctLogin(String username, String password);
    void logToFile(String message);
    void closeConnection();
}
