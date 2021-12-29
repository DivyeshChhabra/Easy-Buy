package com.mycompany.easybuy.servlets; /* Package Name */

/* Importing necessary Packages and Classes */
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* Connecting MySQL DataBase with JAVA application */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy","root","root");
            
            /* Creating Session*/
            HttpSession httpsession = request.getSession();
            
            String billing_address = request.getParameter("billing_address");
            String delivery_address = request.getParameter("delivery_address");
            
            ResultSet rs20 = (ResultSet)httpsession.getAttribute("current-user");
            
            try{
                /* Adding the values in customer table - QUERY 1 */
                PreparedStatement stmt24 = myCon.prepareStatement("insert into customers(member_id,billing_address,delivery_address) values(?,?,?);");
                stmt24.setInt(1, rs20.getInt("member_id"));
                stmt24.setString(2, rs20.getString("billing_address"));
                stmt24.setString(3, rs20.getString("delivery_address"));
                int rowEff1 = stmt24.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
