package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Category;
import com.mycompany.easybuy.model.Product;
import com.mycompany.easybuy.utility.CategoryUtility;
import com.mycompany.easybuy.utility.ProductUtility;

import java.sql.*;
import java.io.*;
import java.util.logging.*;

import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.annotation.Resource;

public class CheckOutServlet extends HttpServlet {
    
    ProductUtility productUtility;
    CategoryUtility categoryUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;

    @Override
    public void init(){
        try{
            productUtility = new ProductUtility(dataSource);
            categoryUtility = new CategoryUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            Product product = null;
            Category category = null;
            int quantity = 0;
            
            if(request.getParameter("product_id") != null){
                
                product = productUtility.getProduct(Integer.parseInt(request.getParameter("product_id")));
                category = categoryUtility.getSelectedCategory(product.getId());
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }else{
                
                boolean completeCart = Boolean.parseBoolean(request.getParameter("complete_cart"));
                if(!completeCart){
                    product = productUtility.getProduct(Integer.parseInt(request.getParameter("cart_product_id")));
                    category = categoryUtility.getSelectedCategory(product.getId());
                    quantity = Integer.parseInt(request.getParameter("cart_quantity"));
                }
            }
            
            request.setAttribute("product", product);
            request.setAttribute("category", category);
            request.setAttribute("quantity", quantity);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);
        }    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /*
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
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
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
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
