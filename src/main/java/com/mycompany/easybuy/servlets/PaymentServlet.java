package com.mycompany.easybuy.servlets; /*  */
/*  */
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

public class PaymentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* Connecting MySQL DataBase with JAVA application */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myCon1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
            
            /*  */
            int product_id = Integer.parseInt(request.getParameter("product"));
            int quantity = Integer.parseInt(request.getParameter("productQty"));
            Double price = Double.parseDouble(request.getParameter("totalPrice"));
            String delivery_address = request.getParameter("delivery_address");
            String billing_address = request.getParameter("billing_address");
            String payment_type = request.getParameter("payment_type");
            String orderPlaced = request.getParameter("orderPlaced");
            String orderShipped = request.getParameter("orderShipped");
            String orderDelivered = request.getParameter("orderDelivered");
            
            String[] shipped = orderShipped.split(" ");
            String[] delivered = orderDelivered.split(" ");
            
            /*  */
            PreparedStatement stmt = myCon1.prepareStatement("select * from products where product_id = ?");
            stmt.setInt(1, product_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            /*  */
            HttpSession httpsession = request.getSession();
            HttpSession session = request.getSession();
            ResultSet rs36 = (ResultSet)httpsession.getAttribute("current-user");
            
            /*  */
            stmt = myCon1.prepareStatement("select * from customers where member_id = ?");
            stmt.setInt(1,rs36.getInt("member_id"));
            ResultSet rs1 = stmt.executeQuery();
            if(rs1.next()){ /*  */
                /*  */
                PreparedStatement stmt36 = myCon1.prepareStatement("update customers set delivery_address=?, billing_address=?");
                stmt36.setString(1, delivery_address);
                stmt36.setString(2, billing_address);
                int rowEff = stmt36.executeUpdate();
            }else{/*  */
                /*  */
                PreparedStatement stmt36 = myCon1.prepareStatement("insert into customers(member_id,delivery_address,billing_address) values(?,?,?)");
                stmt36.setInt(1, rs36.getInt("member_id"));
                stmt36.setString(2, delivery_address);
                stmt36.setString(3, billing_address);
                int rowEff = stmt36.executeUpdate();
            }
            
            /*  */
            PreparedStatement stmt36 = myCon1.prepareStatement("select * from customers where member_id = ?");
            stmt36.setInt(1,rs36.getInt("member_id"));
            rs1 = stmt.executeQuery();
            rs1.next();
            
            /*  */
            stmt36 = myCon1.prepareStatement("insert into payments(payment_type,payment_date,amount) values(?,?,?)");
            stmt36.setString(1, payment_type);
            stmt36.setString(2, "Expected "+delivered[0]);
            stmt36.setDouble(3, Math.ceil(price*(1.05)));
            int rowEff1 = stmt36.executeUpdate();
            
            stmt36 = myCon1.prepareStatement("SELECT * FROM payments ORDER BY payment_id DESC LIMIT 1");
            ResultSet rs2 = stmt36.executeQuery();
            rs2.next();
            
            /*  */
            stmt36 = myCon1.prepareStatement("insert into orders(customer_id,seller_id,product_id,placing_date,shipping_date,delivery_date,quantity,actual_price,tax,total_price,payment_id) values(?,?,?,?,?,?,?,?,?,?,?)");
            stmt36.setInt(1, rs1.getInt("customer_id"));
            stmt36.setInt(2, rs.getInt("seller_id"));
            stmt36.setInt(3, rs.getInt("product_id"));
            stmt36.setString(4, orderPlaced);
            stmt36.setString(5, "Expected "+shipped[0]);
            stmt36.setString(6, "Expected "+delivered[0]);
            if(quantity>rs.getInt("units_left")){
                quantity=rs.getInt("units_left");
            }
            stmt36.setInt(7, quantity);
            stmt36.setDouble(8, price);
            stmt36.setFloat(9, (float) 0.05);
            stmt36.setDouble(10, Math.ceil(price*(1.05)));
            stmt36.setInt(11, rs2.getInt("payment_id"));
            int rowEff2 = stmt36.executeUpdate();
            
            PreparedStatement stmt40 = myCon1.prepareStatement("update products set units_left = units_left - ? where product_id = ?");
            stmt40.setInt(1,quantity);
            stmt40.setInt(2,rs.getInt("product_id"));
            int rowEff3 = stmt40.executeUpdate();
            
            session.setAttribute("success", "Order Placed Successfully!!");
            response.sendRedirect("index.jsp");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
