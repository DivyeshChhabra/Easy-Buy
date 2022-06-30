package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Payment;
import com.mycompany.easybuy.utility.PaymentUtility;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import javax.sql.*;
import javax.servlet.*;
import javax.annotation.*;
import javax.servlet.http.*;

public class PaymentControllerServlet extends HttpServlet {
    
    PaymentUtility paymentUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;

    @Override
    public void init(){
        try{
            paymentUtility = new PaymentUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException{
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            String paymentType = request.getParameter("payment_type");
            String paymentDate = paymentType.equals("Pay on Delivery") ? request.getParameter("order_delivered") : request.getParameter("order_placed");
            double amount = Double.parseDouble(request.getParameter("total_price"));
            
            Payment payment = new Payment(paymentType,paymentDate,amount);
            int paymentId = paymentUtility.addPayment(payment);
            
            if(request.getParameter("info") != null){
                out.write(Integer.toString(paymentId));
                return;
            }
            
            request.setAttribute("payment_id", paymentId);
            request.setAttribute("demand", "customer");
            request.setAttribute("command", "add");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("buy");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
