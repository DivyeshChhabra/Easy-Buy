package com.mycompany.easybuy.utility;

import com.mycompany.easybuy.model.Category;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

public class CategoryUtility {
    
    private final DataSource dataSource;
    
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public CategoryUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<Category> getCategories() throws SQLException{
        
        List<Category> categories = new ArrayList<>();
        
        try{
            
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM category";
            statement = connection.prepareStatement(sql);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process the ResultSet
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                
                Category category = new Category(id,name,description);
                categories.add(category);
            }
            
            return categories;
        } finally {
            // Close the Connection
            close();
        }
    }
    
    public Category getSelectedCategory(int productID) throws SQLException{
        
        Category category = null;
        
        try{
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM category JOIN product ON category.id = product.category_id WHERE product.id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, productID);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process the ResultSet
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                
                category = new Category(id,name,description);
            }
            
            return category;
        } finally {
            // Close the Connection
            close();
        }
    }
    
    public Category getCategory(int categoryID) throws SQLException{
        
        Category category = null;
        
        try{
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "SELECT * FROM category WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            
            // Execute SQL Query
            resultSet = statement.executeQuery();
            
            // Process the ResultSet
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                
                category = new Category(id,name,description);
            }
            
            return category;
        } finally {
            // Close the Connection
            close();
        }
    }

    public void addCategory(Category category) throws SQLException{
        try{
            // Get a Connection
            connection = dataSource.getConnection();
            
            // Create SQL Statement
            String sql = "INSERT INTO category(name,description) VALUES(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            
            // Execute SQL Query
            statement.execute();
        } finally {
            // Close the Connection
            close();
        }
    }
    
    private void close() throws SQLException {
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }
    
}
