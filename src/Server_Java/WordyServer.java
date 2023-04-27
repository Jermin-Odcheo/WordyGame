package Server_Java;

import Client_Java.myConnection;
import Server_Java.CORBA_IDL.wordyPOA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WordyServer extends wordyPOA {
    @Override
    public Object login(String username, String password) {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            try {
                preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username =? AND user_password =?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            return false;
        }
    public boolean verifyUsername(String username){
        ResultSet resultSet;
        try {
            resultSet = myConnection.getConnection().createStatement().executeQuery("SELECT * FROM `users`");
         while (resultSet.next()){
             if (username.equals(resultSet.getString("user_username"))){
                 return true;
             }
         }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
