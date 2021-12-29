package com.mycompany.easybuy.servlets; /* Package Name */

/* Importing necessary Packages and Classes */
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SellerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* Fetching the values from the seller form to respective variables */
            String company_name = request.getParameter("company_name");
            String account_no = request.getParameter("account_no");
            String address = request.getParameter("warehouse_address");
            
            HttpSession httpsession = request.getSession();
            HttpSession session = request.getSession();
            ResultSet rs16 = (ResultSet)httpsession.getAttribute("current-user");
            
            try {
                /* Connecting MySQL DataBase with JAVA application */
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
                
                /* Inserting values into the seller table - QUERY 1 */
                PreparedStatement stmt11 = myCon.prepareStatement("insert into sellers(member_id,account_details,company_name,warehouse_address) values(?,?,?,?);");
                stmt11.setInt(1, rs16.getInt("member_id"));
                stmt11.setString(2, account_no);
                stmt11.setString(3, company_name);
                stmt11.setString(4, address);
                int rowEff = stmt11.executeUpdate();
                
                session.setAttribute("success", "Registeration Successful!!");
                response.sendRedirect("sell.jsp");
            } catch(ClassNotFoundException | SQLException e){
                out.println(e);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
