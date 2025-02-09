package login_window;

public interface ILoginDBHandler {
    void signUpUser(String username, String password);
    boolean userExists(String username);
    boolean correctLogin(String username, String password);
    void closeConnection();
}
