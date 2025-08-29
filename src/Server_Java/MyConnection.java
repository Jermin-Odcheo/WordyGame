package Server_Java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection getConnection(){
        Connection connection = null;
        String url = System.getenv().getOrDefault("WORDY_DB_URL", "jdbc:mysql://localhost/wordy_accounts");
        String user = System.getenv().getOrDefault("WORDY_DB_USER", "root");
        String pass = System.getenv().getOrDefault("WORDY_DB_PASS", "");
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e){
            System.out.println("DB connection error: " + e.getMessage());
        }
        return connection;
    }
}
