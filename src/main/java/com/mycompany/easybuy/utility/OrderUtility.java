package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Order;

import java.sql.*;
import java.util.*;

import javax.sql.*;

public class OrderUtility {

    private final DataSource dataSource;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public OrderUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void addOrder(Order order) throws SQLException{
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO orders(customer_id,product_id,payment_id,placing_date,delivery_date,quantity,total_price,rating) VALUES(?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getCustomerId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getPaymentId());
            statement.setString(4, order.getOrderPlacedDate());
            statement.setString(5, order.getOrderDeliveredDate());
            statement.setInt(6, order.getQuantity());
            statement.setDouble(7, order.getTotalPrice());
            statement.setInt(8, order.getRating());
            
            statement.execute();
        } finally {
            close();
        }
    }
    
    public List<Order> getOrders(int customerID) throws SQLException{
        
        List<Order> orders = new ArrayList<>();
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM orders WHERE customer_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int id = resultSet.getInt("order_id");
                int customerId = customerID;
                int productId = resultSet.getInt("product_id");
                int paymentId = resultSet.getInt("payment_id");
                String orderPlacedDate = resultSet.getString("placing_date");
                String orderDeliveredDate = resultSet.getString("delivery_date");
                int quantity = resultSet.getInt("quantity");
                double totalPrice = resultSet.getDouble("total_price");
                int rating = resultSet.getInt("rating");
                
                String paymentType = null;
                String paymentSQL = "SELECT payment_type FROM payments WHERE payment_id = ?";
                statement = connection.prepareStatement(paymentSQL);
                statement.setInt(1, paymentId);
                
                ResultSet paymentResultSet = statement.executeQuery();
                if(paymentResultSet.next()){
                    paymentType = paymentResultSet.getString("payment_type");
                }
                
                String productName = null;
                String productSQL = "SELECT product_name FROM products WHERE product_id = ?";
                statement = connection.prepareStatement(productSQL);
                statement.setInt(1, productId);
                
                ResultSet productResultSet = statement.executeQuery();
                if(productResultSet.next()){
                    productName = productResultSet.getString("product_name");
                }
                
                Order order = new Order(id,customerId,orderPlacedDate,orderDeliveredDate,quantity,totalPrice,productName,paymentType,rating);
                orders.add(order);
            }
            
            return orders;
        } finally {
            close();
        }
    }
    
    public Order getOrder(int orderID) throws SQLException {
        
        Order order = null;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, orderID);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int id = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                int productId = resultSet.getInt("product_id");
                int paymentId = resultSet.getInt("payment_id");
                String orderPlacedDate = resultSet.getString("placing_date");
                String orderDeliveredDate = resultSet.getString("delivery_date");
                int quantity = resultSet.getInt("quantity");
                double totalPrice = resultSet.getDouble("total_price");
                int rating = resultSet.getInt("rating");
            
                order = new Order(id,customerId,productId,paymentId,orderPlacedDate,orderDeliveredDate,quantity,totalPrice,rating);
            }
            
            return order;
        } finally {
            close();
        }
    }
    
    public void updateOrder(Order order) throws SQLException{
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "UPDATE orders SET rating = ? WHERE order_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getRating());
            statement.setInt(2, order.getId());
            
            statement.execute();
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
