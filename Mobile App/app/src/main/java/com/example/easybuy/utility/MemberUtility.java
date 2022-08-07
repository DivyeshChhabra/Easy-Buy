package com.example.easybuy.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.example.easybuy.model.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberUtility {

    static class Login extends AsyncTask<String,Void,Member>{

        @Override
        protected Member doInBackground(String... strings) {

            String emailID = strings[0];
            String password = strings[1];

            Member member = null;

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://192.168.137.35:3306/easy_buy","root","root");

                String sql = "SELECT * FROM members WHERE e_mail = ? AND password = ?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,emailID);
                preparedStatement.setString(2,password);

                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){

                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String gender = resultSet.getString("gender");
                    String phoneNumber = resultSet.getString("phone");
                    String EMailAddress = resultSet.getString("e_mail");
                    String Password = resultSet.getString("password");
                    int sellerId = resultSet.getInt("seller_id");
                    int customerId = resultSet.getInt("customer_id");
                    String profilePhoto = resultSet.getString("profile_picture");
                    String type = resultSet.getString("type");

                    member = new Member(id,firstName,lastName,gender,phoneNumber,EMailAddress,Password,sellerId,customerId,profilePhoto,type);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try{
                    if(resultSet != null) resultSet.close();
                    if(preparedStatement != null) preparedStatement.close();
                    if(connection != null) connection.close();
                } catch (Exception exception) { exception.printStackTrace(); }
            }

            return member;
        }
    }

    static class GetPassword extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String emailID = strings[0];

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://192.168.137.35:3306/easy_buy","root","root");

                String sql = "SELECT password FROM members WHERE e_mail = ?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,emailID);

                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){ return resultSet.getString("password"); }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try{
                    if(resultSet != null) resultSet.close();
                    if(preparedStatement != null) preparedStatement.close();
                    if(connection != null) connection.close();
                } catch (Exception exception) { exception.printStackTrace(); }
            }

            return null;
        }
    }

    public AsyncTask<String, Void, Member> login(String emailID, String password){
        try{
            Login login = new Login();
            return login.execute(emailID,password);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }

    public AsyncTask<String, Void, String> recoverPassword(String emailID){
        try{
            GetPassword getPassword = new GetPassword();
            return getPassword.execute(emailID);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }
}
