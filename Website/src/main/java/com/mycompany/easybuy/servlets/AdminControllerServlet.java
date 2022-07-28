package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Category;
import com.mycompany.easybuy.model.Customer;
import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.model.Product;
import com.mycompany.easybuy.model.Seller;
import com.mycompany.easybuy.utility.CategoryUtility;
import com.mycompany.easybuy.utility.CustomerUtility;
import com.mycompany.easybuy.utility.MemberUtility;
import com.mycompany.easybuy.utility.ProductUtility;
import com.mycompany.easybuy.utility.SellerUtility;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

public class AdminControllerServlet extends HttpServlet {

    MemberUtility memberUtility;
    CustomerUtility customerUtility;
    SellerUtility sellerUtility;
    CategoryUtility categoryUtility;
    ProductUtility productUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            memberUtility = new MemberUtility(dataSource);
            customerUtility = new CustomerUtility(dataSource);
            sellerUtility = new SellerUtility(dataSource);
            categoryUtility = new CategoryUtility(dataSource);
            productUtility = new ProductUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
        
            List<Member> members = memberUtility.getMembers();
            List<Customer> customers = customerUtility.getCustomers();
            List<Seller> sellers = sellerUtility.getSellers();
            List<Category> categories = categoryUtility.getCategories();
            List<Product> products = productUtility.getProducts();
            
            request.setAttribute("members", members);
            request.setAttribute("customers", customers);
            request.setAttribute("sellers", sellers);
            request.setAttribute("categories", categories);
            request.setAttribute("products", products);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
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
            Logger.getLogger(AdminControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
