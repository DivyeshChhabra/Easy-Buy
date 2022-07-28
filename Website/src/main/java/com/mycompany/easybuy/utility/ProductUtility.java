package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Product;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

public class ProductUtility {
    private final DataSource dataSource;
    
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public ProductUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void addProduct(Product product) throws SQLException{
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "INSERT INTO product(name,description,category_id,seller_id,unit_price,discount,units_left,product_image,rating,review_count) VALUES(?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getCategoryId());
            statement.setInt(4, product.getSellerId());
            statement.setDouble(5, product.getUnitPrice());
            statement.setDouble(6, product.getDiscount());
            statement.setInt(7, product.getInventory());
            statement.setString(8, product.getProductImage());
            statement.setDouble(9, product.getRating());
            statement.setInt(10, product.getReviews());
            
            statement.execute();
            
        } finally {
            close();
        }
    }
    
    public List<Product> getProducts() throws SQLException{
        
        List<Product> products = new ArrayList<>();
        
        try{
            
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM product";
            statement = connection.prepareStatement(sql);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process ResultSet
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int categoryId = resultSet.getInt("category_id");
                int sellerId = resultSet.getInt("seller_id");
                double unitPrice = resultSet.getDouble("unit_price");
                double discount = resultSet.getDouble("discount");
                int inventory = resultSet.getInt("units_left");
                String productImage = resultSet.getString("product_image");
                double rating = resultSet.getDouble("rating");
                int reviews = resultSet.getInt("review_count");
                
                String sellerName = null;
                String sellerSQL = "SELECT first_name, last_name FROM members JOIN seller on members.id = seller.member_id WHERE seller.id = ?";
                statement = connection.prepareStatement(sellerSQL);
                statement.setInt(1, sellerId);
                
                ResultSet sellerResultSet = statement.executeQuery();
                if(sellerResultSet.next()){
                    sellerName = sellerResultSet.getString("first_name") + " " + sellerResultSet.getString("last_name");
                }
                
                String categoryName = null;
                String categorySQL = "SELECT name FROM category WHERE id = ?";
                statement = connection.prepareStatement(categorySQL);
                statement.setInt(1, categoryId);
                
                ResultSet categoryResultSet = statement.executeQuery();
                if(categoryResultSet.next()){
                    categoryName = categoryResultSet.getString("name");
                }
                
                Product product = new Product(id,name,description,categoryId,sellerId,unitPrice,discount,inventory,productImage,rating,reviews,sellerName,categoryName);
                products.add(product);
            }
            
            return products;
        } finally{
            // Close the Connection
            close();
        }
    }
    
    public List<Product> getProductsCategoryWise(int categoryID) throws SQLException {
        
        List<Product> products = new ArrayList<>();
        
        try{
            
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM product WHERE category_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process ResultSet
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int categoryId = categoryID;
                int sellerId = resultSet.getInt("seller_id");
                double unitPrice = resultSet.getDouble("unit_price");
                double discount = resultSet.getDouble("discount");
                int inventory = resultSet.getInt("units_left");
                String productImage = resultSet.getString("product_image");
                double rating = resultSet.getDouble("rating");
                int reviews = resultSet.getInt("review_count");
                
                String sellerName = null;
                String sellerSQL = "SELECT first_name, last_name FROM members JOIN seller on members.id = seller.member_id WHERE seller.id = ?";
                statement = connection.prepareStatement(sellerSQL);
                statement.setInt(1, sellerId);
                
                ResultSet sellerResultSet = statement.executeQuery();
                if(sellerResultSet.next()){
                    sellerName = sellerResultSet.getString("first_name") + " " + sellerResultSet.getString("last_name");
                }
                
                String categoryName = null;
                String categorySQL = "SELECT name FROM category WHERE id = ?";
                statement = connection.prepareStatement(categorySQL);
                statement.setInt(1, categoryID);
                
                ResultSet categoryResultSet = statement.executeQuery();
                if(categoryResultSet.next()){
                    categoryName = categoryResultSet.getString("name");
                }
                
                Product product = new Product(id,name,description,categoryId,sellerId,unitPrice,discount,inventory,productImage,rating,reviews,sellerName,categoryName);
                products.add(product);
            }
            
            return products;
        } finally {
            // Close the Connection
            close();
        }
    }
    
    public List<Product> getProductsSellerWise(int sellerID) throws SQLException {
        
        List<Product> products = new ArrayList<>();
        
        try{
            
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM product WHERE seller_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sellerID);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process ResultSet
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int categoryId = resultSet.getInt("category_id");
                int sellerId = sellerID;
                double unitPrice = resultSet.getDouble("unit_price");
                double discount = resultSet.getDouble("discount");
                int inventory = resultSet.getInt("units_left");
                String productImage = resultSet.getString("product_image");
                double rating = resultSet.getDouble("rating");
                int reviews = resultSet.getInt("review_count");
                
                String sellerName = null;
                String sellerSQL = "SELECT first_name, last_name FROM members JOIN seller on members.id = seller.member_id WHERE seller.id = ?";
                statement = connection.prepareStatement(sellerSQL);
                statement.setInt(1, sellerID);
                
                ResultSet sellerResultSet = statement.executeQuery();
                if(sellerResultSet.next()){
                    sellerName = sellerResultSet.getString("first_name") + " " + sellerResultSet.getString("last_name");
                }
                
                String categoryName = null;
                String categorySQL = "SELECT name FROM category WHERE id = ?";
                statement = connection.prepareStatement(categorySQL);
                statement.setInt(1, categoryId);
                
                ResultSet categoryResultSet = statement.executeQuery();
                if(categoryResultSet.next()){
                    categoryName = categoryResultSet.getString("name");
                }
                
                Product product = new Product(id,name,description,categoryId,sellerId,unitPrice,discount,inventory,productImage,rating,reviews,sellerName,categoryName);
                products.add(product);
            }
            
            return products;
        } finally {
            // Close the Connection
            close();
        }
    }
    
    @SuppressWarnings("empty-statement")
    public Product getProduct(int productID) throws SQLException{
        
        Product product = null;
        
        try{
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM product WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,productID);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process the ResultSet
            if(resultSet.next()){
                int id = productID;
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int categoryId = resultSet.getInt("category_id");
                int sellerId = resultSet.getInt("seller_id");
                double unitPrice = resultSet.getDouble("unit_price");
                double discount = resultSet.getDouble("discount");
                int inventory = resultSet.getInt("units_left");
                String productImage = resultSet.getString("product_image");
                double rating = resultSet.getDouble("rating");
                int reviews = resultSet.getInt("review_count");
                
                String sellerName = null;
                String sellerSQL = "SELECT first_name, last_name FROM members JOIN seller on members.id = seller.member_id WHERE seller.id = ?";
                statement = connection.prepareStatement(sellerSQL);
                statement.setInt(1, sellerId);
                
                ResultSet sellerResultSet = statement.executeQuery();
                if(sellerResultSet.next()){
                    sellerName = sellerResultSet.getString("first_name") + " " + sellerResultSet.getString("last_name");
                }
                
                String categoryName = null;
                String categorySQL = "SELECT name FROM category WHERE id = ?";
                statement = connection.prepareStatement(categorySQL);
                statement.setInt(1, categoryId);
                
                ResultSet categoryResultSet = statement.executeQuery();
                if(categoryResultSet.next()){
                    categoryName = categoryResultSet.getString("name");
                }
                
                int[] ratingDistribution = {0,0,0,0,0};
                
                String ratingSQL = null;
                ResultSet ratingResultSet = null;
                
                for(int i=0; i<ratingDistribution.length; i++){
                    ratingSQL = "SELECT count(rating) FROM orders WHERE product_id = ? AND rating = ?";
                    statement = connection.prepareStatement(ratingSQL);
                    statement.setInt(1, id);
                    statement.setInt(2, i+1);

                    ratingResultSet = statement.executeQuery();
                    if(ratingResultSet.next()){
                        ratingDistribution[i] = ratingResultSet.getInt("count(rating)");
                    }
                }
                
                product = new Product(id,name,description,categoryId,sellerId,unitPrice,discount,inventory,productImage,rating,reviews,sellerName,categoryName,ratingDistribution);
            }
            
            return product;
        } finally {
            // Close the Connection
            close();
        }
    }
    
    public void updateProduct(Product product) throws SQLException {
        
        try{
            
            connection = dataSource.getConnection();
            
            String sql = "UPDATE product SET name = ?, description = ?, category_id = ?, seller_id = ?, unit_price = ?, discount = ?, units_left = ?, product_image = ?, rating = ?, review_count = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getCategoryId());
            statement.setInt(4, product.getSellerId());
            statement.setDouble(5, product.getUnitPrice());
            statement.setDouble(6, product.getDiscount());
            statement.setInt(7, product.getInventory());
            statement.setString(8, product.getProductImage());
            statement.setDouble(9, product.getRating());
            statement.setInt(10, product.getReviews());
            statement.setInt(11, product.getId());
            
            statement.execute();
        } finally {
            // Close the Connection
            close();
        }
    }
    private void close() throws SQLException{
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
}
