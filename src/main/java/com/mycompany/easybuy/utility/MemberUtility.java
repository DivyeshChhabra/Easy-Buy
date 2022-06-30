package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Member;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

public class MemberUtility {
    
    private final DataSource dataSource;
    
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public MemberUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void addMember(Member member) throws SQLException {
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO members(first_name,last_name,gender,phone,e_mail,password,seller_id,customer_id,profile_picture) VALUES(?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getGender());
            statement.setString(4, member.getPhoneNumber());
            statement.setString(5, member.getEMailAddress());
            statement.setString(6, member.getPassword());
            statement.setInt(7, member.getSellerId());
            statement.setInt(8, member.getCustomerId());
            statement.setString(9, member.getProfilePhoto());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    @SuppressWarnings("empty-statement")
    public List<Member> getMembers() throws SQLException{
        
        List<Member> members = new ArrayList<>();
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM members";
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int id = resultSet.getInt("member_id");
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
                
                Member member = new Member(id,firstName,lastName,gender,phoneNumber,EMailAddress,Password,sellerId,customerId,profilePhoto,type);
                members.add(member);
            }
            
            return members;
        } finally {
            close();
        }
    }
    
    public Member getMember(String emailID) throws SQLException{
        
        Member member = null;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM members WHERE e_mail = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,emailID);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                
                int id = resultSet.getInt("member_id");
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
            
            return member;
        } finally {
            close();
        }
    }
    
    @SuppressWarnings("empty-statement")
    public Member login(String emailID, String password) throws SQLException{
        
        Member member = null;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM members WHERE e_mail = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,emailID);
            statement.setString(2,password);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                
                int id = resultSet.getInt("member_id");
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
            
            return member;
        } finally {
            close();
        }
    }
    
    public void updateMember(Member member) throws SQLException {
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "UPDATE members SET first_name = ?, last_name = ?, gender = ?, phone = ?, e_mail = ?, password = ?, seller_id = ?, customer_id = ?, profile_picture = ? WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getGender());
            statement.setString(4, member.getPhoneNumber());
            statement.setString(5, member.getEMailAddress());
            statement.setString(6, member.getPassword());
            statement.setInt(7, member.getSellerId());
            statement.setInt(8, member.getCustomerId());
            statement.setString(9, member.getProfilePhoto());
            statement.setInt(10, member.getId());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    public void deleteMember(String emailID,String password) throws SQLException {
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "DELETE FROM members WHERE e_mail = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, emailID);
            statement.setString(2, password);
            
            statement.executeUpdate();
        } finally {
            close();
        }
    }
    
    private void close() throws SQLException{
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
}
