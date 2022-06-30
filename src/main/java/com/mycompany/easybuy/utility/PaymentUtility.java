package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Payment;

import java.sql.*;

import javax.sql.*;

public class PaymentUtility {
    
    private final DataSource dataSource;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public PaymentUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public int addPayment(Payment payment) throws SQLException{
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO payments(payment_type,payment_date,amount) VALUES(?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,payment.getPaymentType());
            statement.setString(2,payment.getPaymentDate());
            statement.setDouble(3,payment.getAmount());
            
            statement.execute();
            
            return getLastPaymentId(connection);
        } finally {
            close();
        }
    }
    
    private int getLastPaymentId(Connection connection) throws SQLException {
        
        try{
            
            String sql = "SELECT payment_id FROM payments ORDER BY payment_id DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                return resultSet.getInt("payment_id");
            }
            
            return 0;
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
