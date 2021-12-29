package com.mycompany.easybuy.servlets; /* Package Name */

/* Importing necessary Packages and Classes */
import java.sql.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 16777215)
public class ProductOperationServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* Connecting MySQL DataBase with JAVA application */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
            
            /* Knowing whether to add category or to add products */
            String operation = request.getParameter("operation");
            
            /* Generating Session */
            HttpSession session = request.getSession();
                
            if(operation.equals("category")){ /* Add Category */
                /* Fetching the values from the category form to respective variables */
                float modifiedSale=0;
                String category_name = request.getParameter("category_name");
                String description = request.getParameter("description");
                String sale = request.getParameter("sale");
                if(sale != ""){
                    
                    if(sale.charAt(sale.length()-1)=='%'){
                    String sale1 = sale.substring(0, sale.length()-1);
                    modifiedSale = Float.parseFloat(sale1);
                    modifiedSale /= 100;
                    }else{
                        modifiedSale = Float.parseFloat(sale);
                    }
                }
                
                try{
                    /* Inserting values into the category table - QUERY 1 (main query) */
                    PreparedStatement stmt9 = myCon.prepareStatement("insert into category(category_name,description,sale) values(?,?,?);");
                    stmt9.setString(1, category_name);
                    stmt9.setString(2, description);
                    stmt9.setFloat(3, modifiedSale);
                    int rowEff = stmt9.executeUpdate();
                    
                    session.setAttribute("success", "Category Added Successfully!!");
                    response.sendRedirect("sell.jsp");

                } catch(Exception e){
                    out.println(e);
                }
            }else{ /* Add Products */
                /* Getting the member ID of current login member */
                HttpSession httpsession = request.getSession();
                ResultSet rs14 = (ResultSet)httpsession.getAttribute("current-user");
                int memberID = rs14.getInt("member_id");
                
                /* Fetching the values from the product form to respective variables */
                float modifiedDiscount=0;
                String product_name = request.getParameter("product_name");
                String description = request.getParameter("description");
                int category_id = Integer.parseInt(request.getParameter("category_id"));
                /* Getting the seller ID of current login member */
                PreparedStatement stmt10 = myCon.prepareStatement("select * from sellers where member_id = ?;");
                stmt10.setInt(1, memberID);
                ResultSet rs15 = stmt10.executeQuery();
                rs15.next();
                int seller_id=rs15.getInt("seller_id");
                
                double unit_price = Double.parseDouble(request.getParameter("unit_price"));
                String discount = request.getParameter("discount");
                
                stmt10 = myCon.prepareStatement("select * from category where category_id=?");
                stmt10.setInt(1, category_id);
                rs15 = stmt10.executeQuery();
                rs15.next();
                if(discount.charAt(discount.length()-1)=='%'){
                    String discount1 = discount.substring(0, discount.length()-1);
                    modifiedDiscount = Float.parseFloat(discount1);
                    modifiedDiscount /= 100;
                }else{
                    modifiedDiscount = Float.parseFloat(discount);
                }
                
                if(rs15.getFloat("sale")>modifiedDiscount){
                    modifiedDiscount = rs15.getFloat("sale");
                }
                
                String color = request.getParameter("color");
                String weight = request.getParameter("weight");
                int units_left = Integer.parseInt(request.getParameter("quantities"));
                
                /* Insert the image and uploading it in the target folder */
                InputStream inputstream = null;
                Part product_image = request.getPart("product_image");
                if(product_image!=null){
                    inputstream = product_image.getInputStream();
                }
                String path = request.getRealPath("Image")+File.separator+"Products"+File.separator+product_image.getSubmittedFileName();
                FileOutputStream outputstream = new FileOutputStream(path);
                byte[] data = new byte[inputstream.available()];
                inputstream.read(data);
                outputstream.write(data);
                outputstream.close();
                
                try{
                    /* Inserting values into the product table - QUERY 1 (main query) */
                    PreparedStatement stmt11 = myCon.prepareStatement("insert into products(product_name,product_description,category_id,seller_id,unit_price,discount,color,weight_inGrams,units_left,product_image) values(?,?,?,?,?,?,?,?,?,?);");
                    stmt11.setString(1, product_name);
                    stmt11.setString(2, description);
                    stmt11.setInt(3, category_id);
                    stmt11.setInt(4, seller_id);
                    stmt11.setDouble(5, unit_price);
                    stmt11.setFloat(6, modifiedDiscount);
                    stmt11.setString(7, color);
                    stmt11.setString(8, weight);
                    stmt11.setInt(9, units_left);
                    stmt11.setString(10, product_image.getSubmittedFileName());
                    int rowEff = stmt11.executeUpdate();
                    
                    session.setAttribute("success", "Product Added Successfully!!");
                    response.sendRedirect("sell.jsp");
                }catch(Exception e){
                    out.println(e);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
