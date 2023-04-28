package Server_Java;

import Client_Java.myConnection;
import Server_Java.CORBA_IDL.wordyPOA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WordyServer extends wordyPOA {
    @Override
    public String login(String username, String password) {
        try {
            boolean check = verifyUsername(username);
            if (!check) {
                System.out.println(username + " : " + "Account not found");
                return "NotFound";
            } else {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username = ? AND user_password = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                        System.out.println(username + " : " + "Successfully Logged In");
                        return "Found";
                } else {
                    return "Invalid";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public boolean verifyUsername(String username) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement;
            preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username =?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("user_username");
                if (username.equals(user)) {
                    System.out.println(user + " : " + "Successfully Logged In -Verify");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {

    }
}


