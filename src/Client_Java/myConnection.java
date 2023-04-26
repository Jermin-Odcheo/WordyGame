package Client_Java;

import java.sql.Connection;
import java.sql.DriverManager;
//Should be accessed by the Server
public class myConnection {
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/wordy_accounts","root","");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
