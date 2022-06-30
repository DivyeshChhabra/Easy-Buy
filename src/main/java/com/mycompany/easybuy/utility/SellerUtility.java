package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Seller;

import java.sql.*;
import java.util.*;

import javax.sql.*;

public class SellerUtility {
    
    private final DataSource dataSource;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public SellerUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void addSeller(Seller seller) throws SQLException{
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO sellers(member_id,store_description,business_name,gstin,verified) VALUES(?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, seller.getMemberId());
            statement.setString(2, seller.getStoreDesc());
            statement.setString(3, seller.getBusinessName());
            statement.setString(4, seller.getGstId());
            statement.setBoolean(5, seller.isVerified());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    public List<Seller> getSellers() throws SQLException {
        
        List<Seller> sellers = new ArrayList<>();
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM sellers";
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                
                int id = resultSet.getInt("seller_id");
                int memberId = resultSet.getInt("member_id");
                String storeDesc = resultSet.getString("store_description");
                String businessName = resultSet.getString("business_name");
                String gstId = resultSet.getString("gstin");
                boolean verified = resultSet.getBoolean("verified");
                
                Seller seller = new Seller(id,memberId,gstId,verified,businessName,storeDesc);
                sellers.add(seller);
            }
            
            return sellers;
        } finally {
            close();
        }
    }
    
    public Seller getSeller(int memberID) throws SQLException {
        
        Seller seller = null;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT * FROM sellers WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, memberID);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                
                int id = resultSet.getInt("seller_id");
                String storeDesc = resultSet.getString("store_description");
                String businessName = resultSet.getString("business_name");
                String gstId = resultSet.getString("gstin");
                boolean verified = resultSet.getBoolean("verified");
                
                seller = new Seller(id,memberID,gstId,verified,businessName,storeDesc);
            }
            
            return seller;
        } finally {
            close();
        }
    }
    
    public int getSellerId(int memberID) throws SQLException {
        
        int id = 0;
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "SELECT seller_id FROM sellers WHERE member_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, memberID);
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) id = resultSet.getInt("seller_id");
            return id;
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
