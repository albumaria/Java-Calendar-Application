package login_window;


import db_connection.DatabaseConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDBHandler implements ILoginDBHandler {
    private Connection conn;

    public LoginDBHandler() {
        this.conn = DatabaseConnection.connect();
    }

    public void closeConnection() {
        try {
            if (this.conn != null)
                this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }
}
