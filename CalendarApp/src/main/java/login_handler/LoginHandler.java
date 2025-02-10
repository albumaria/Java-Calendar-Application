package login_handler;


import db_connection.DatabaseConnection;
import logging.*;
import java.security.*;
import java.sql.*;

public class LoginHandler implements ILoginHandler {
    private Connection conn;
    private ILoggingHandler loggerHandler;

    public LoginHandler(String logFileName) {
        this.conn = DatabaseConnection.connect();
        this.loggerHandler = new LoggingHandler(logFileName);
    }

    public void logToFile(String message) {
        this.loggerHandler.createLogText(message);
    }

    public void closeConnection() {
        try {
            if (this.conn != null)
                this.conn.close();
        } catch (SQLException e) {
            this.loggerHandler.createLogText("Unable to close connection properly");
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1); // The first column is the count
                    return count > 0; // If count is greater than 0, the user exists
                }
            }

        } catch (SQLException e) {
            this.loggerHandler.createLogText("Unable to check whether user with username " + username + " exists");
        }

        return false;
    }

    public boolean correctLogin(String username, String password) {
        String hashPassword = this.hashPassword(password);

        String sql = "SELECT COUNT(*) FROM Users WHERE username = ? AND password_hash = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1); // The first column is the count
                    return count > 0; // If count is greater than 0, the user exists
                }
            }

        } catch (SQLException e) {
            this.loggerHandler.createLogText("Unable to check whether user with username " + username + " logged in correctly");
        }

        return false;
    }

    public void signUpUser(String username, String password) {
        String hashPassword = this.hashPassword(password);

        String sql = "INSERT INTO Users (username, password_hash) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword);
            pstmt.executeUpdate();

            System.out.println("User registered successfully!");

        } catch (SQLException e) {
            this.loggerHandler.createLogText("Unable to sign up user with username " + username);
        }

    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            this.loggerHandler.createLogText("Password hashing error");
            return null;
        }
    }
}
