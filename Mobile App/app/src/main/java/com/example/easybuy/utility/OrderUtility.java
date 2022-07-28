package com.example.easybuy.utility;

import android.os.AsyncTask;

import com.example.easybuy.model.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderUtility {

    static class TotalOrders extends AsyncTask<Void,Void,List<Order>>{

        @Override
        protected List<Order> doInBackground(Void... voids) {

            List<Order> orders = new ArrayList<>();

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://192.168.29.68:3306/easy_buy","root","root");

                String sql = "SELECT * FROM orders";

                preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    int customerId = resultSet.getInt("customer_id");
                    int productId = resultSet.getInt("product_id");
                    int paymentId = resultSet.getInt("payment_id");
                    String orderPlacedDate = resultSet.getString("placing_date");
                    String orderDeliveredDate = resultSet.getString("delivery_date");
                    int quantity = resultSet.getInt("quantity");
                    double totalPrice = resultSet.getDouble("total_price");
                    int rating = resultSet.getInt("rating");
                    String orderStatus = resultSet.getString("status");

                    String paymentType = null;
                    String paymentSQL = "SELECT type FROM payment WHERE id = ?";
                    preparedStatement = connection.prepareStatement(paymentSQL);
                    preparedStatement.setInt(1, paymentId);

                    ResultSet paymentResultSet = preparedStatement.executeQuery();
                    if(paymentResultSet.next()){
                        paymentType = paymentResultSet.getString("type");
                    }

                    String productName = null;
                    String productImage = null;
                    String productSQL = "SELECT name,product_image FROM product WHERE id = ?";
                    preparedStatement = connection.prepareStatement(productSQL);
                    preparedStatement.setInt(1, productId);

                    ResultSet productResultSet = preparedStatement.executeQuery();
                    if(productResultSet.next()){
                        productName = productResultSet.getString("name");
                        productImage = productResultSet.getString("product_image");
                    }

                    String customerName = null;
                    String customerAddress = null;
                    String customerEmail = null;
                    String customerSQL = "SELECT first_name,last_name,e_mail,delivery_address FROM customer JOIN members ON customer.member_id = members.id WHERE customer.id = ?";
                    preparedStatement = connection.prepareStatement(customerSQL);
                    preparedStatement.setInt(1,customerId);

                    ResultSet customerResultSet = preparedStatement.executeQuery();
                    if(customerResultSet.next()){
                        customerName = customerResultSet.getString("first_name") + " " + customerResultSet.getString("last_name");
                        customerEmail = customerResultSet.getString("e_mail");
                        customerAddress = customerResultSet.getString("delivery_address");
                    }

                    Order order = new Order(id,customerId,orderPlacedDate,orderDeliveredDate,quantity,totalPrice,customerName,customerEmail,customerAddress,productName,productImage,paymentType,rating,orderStatus);
                    orders.add(order);
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

            return orders;
        }
    }

    static class UpdateOrder extends AsyncTask<Order,Void,Void>{

        @Override
        protected Void doInBackground(Order... orders) {

            Order order = orders[0];

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://192.168.29.68:3306/easy_buy","root","root");

                String sql = "UPDATE orders SET status = ?, delivery_date = ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,order.getOrderStatus());
                preparedStatement.setString(2,order.getOrderDeliveredDate());
                preparedStatement.setInt(3, order.getId());

                preparedStatement.execute();
            } catch (Exception exception){
                exception.printStackTrace();
            } finally {
                try{
                    if(preparedStatement != null) preparedStatement.close();
                    if(connection != null) connection.close();
                } catch (Exception exception) { exception.printStackTrace(); }
            }

            return null;
        }
    }

    public AsyncTask<Void,Void,List<Order>> getTotalOrders() {
        try{
            TotalOrders totalOrders = new TotalOrders();
            return totalOrders.execute();
        } catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }

    public void deliverOrder(Order order) {
        try{
            UpdateOrder updateOrder = new UpdateOrder();
            updateOrder.execute(order);
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
