package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.model.Order;
import com.mycompany.easybuy.utility.OrderUtility;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.annotation.Resource;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class OrderControllerServlet extends HttpServlet {

    OrderUtility orderUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            orderUtility = new OrderUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        try ( PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            
            String command = (String) request.getAttribute("command");
            if(command == null) command = request.getParameter("command");
            
            switch(command){
                case "add":
                    addOrder(request,response,session);
                    break;
                case "get":
                    getOrders(request,response,session);
                    break;
                case "update":
                    updateOrder(request,response,session);
                    break;
                default:
                    break;
            }
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
            Logger.getLogger(OrderControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OrderControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void addOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException {
        
        Member member = (Member) session.getAttribute("current-member");
        
        int customerId = member.getCustomerId();
        int productId = Integer.parseInt(request.getParameter("product_id"));
        
        int paymentId = 0;
        if(request.getParameter("payment_id") != null) paymentId = Integer.parseInt(request.getParameter("payment_id"));
        else if(request.getAttribute("payment_id") != null) paymentId = (int) request.getAttribute("payment_id");
        
        String orderPlacedDate = request.getParameter("order_placed");
        String orderDeliveredDate = request.getParameter("order_delivered");
        int quantity = Integer.parseInt(request.getParameter("product_qty"));
        double totalPrice = Double.parseDouble(request.getParameter("total_price"));
        
        int rating = 0;
        
        Order order = new Order(customerId,productId,paymentId,orderPlacedDate,orderDeliveredDate,quantity,totalPrice,rating);
        orderUtility.addOrder(order);

        session.setAttribute("success", "Order Placed Successfully!!");
        
        if(request.getParameter("info") != null){
            response.getWriter().write("");
            return;
        }
        
        response.sendRedirect("index");
    }

    private void getOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        
        Member member = (Member) session.getAttribute("current-member");
        if(member == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        int customerId = member.getCustomerId();
        List<Order> myOrders = orderUtility.getOrders(customerId);
        
        request.setAttribute("my_orders", myOrders);
        request.setAttribute("visit", "my_orders");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    private Order getOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        
        Order order = orderUtility.getOrder(orderId);
        return order;
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException, ServletException {
        
        Order order = getOrder(request,response);
        if(order == null){
            session.setAttribute("error", "Order Not Found");
            response.sendRedirect("dashboard.jsp");
            return;
        }
        
        int rating = 0;
        if(request.getParameter("rating") != null) rating = Integer.parseInt(request.getParameter("rating"));
        
        order.setRating(rating);
        orderUtility.updateOrder(order);
        
        request.setAttribute("demand", "product");
        request.setAttribute("command", "update");
        request.setAttribute("operation", "addReview");
        
        request.setAttribute("product_id", order.getProductId());
        
        request.setAttribute("rating", rating);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("update");
        dispatcher.forward(request, response);
    }

}
