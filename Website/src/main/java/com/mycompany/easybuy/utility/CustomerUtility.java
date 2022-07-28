package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Customer;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

public class CustomerUtility {
    
    private final DataSource dataSource;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    
    public CustomerUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void addCustomer(Customer customer) throws SQLException{
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO customer(member_id,billing_address,delivery_address) VALUES(?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getMemberId());
            statement.setString(2, customer.getBillingAddress());
            statement.setString(3, customer.getDeliveryAddress());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    public List<Customer> getCustomers() throws SQLException{
        
        List<Customer> customers = new ArrayList<>();
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM customer";
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int memberId = resultSet.getInt("member_id");
                String billingAddress = resultSet.getString("billing_address");
                String deliveryAddress = resultSet.getString("delivery_address");
                
                Customer customer = new Customer(id,memberId,billingAddress,deliveryAddress);
                customers.add(customer);
            }
            
            return customers;
        }  finally {
            close();
        }
    }
    
    public Customer getCustomer(int memberID) throws SQLException {
        
        Customer customer = null;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM customer WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, memberID);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int memberId = resultSet.getInt("member_id");
                String billingAddress = resultSet.getString("billing_address");
                String deliveryAddress = resultSet.getString("delivery_address");
                
                customer = new Customer(id,memberId,billingAddress,deliveryAddress);
            }
            
            return customer;
        }  finally {
            close();
        }
    }
    
    public int getCustomerId(int memberID) throws SQLException {
        
        int id = 0;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT id FROM customer WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, memberID);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) id = resultSet.getInt("id");
            return id;
        } finally {
            close();
        }
    }
    
    public void updateCustomer(Customer customer) throws SQLException {
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "UPDATE customer SET billing_address = ?, delivery_address = ? WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getBillingAddress());
            statement.setString(2, customer.getDeliveryAddress());
            statement.setInt(3, customer.getMemberId());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    private void close() throws SQLException {
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
}
