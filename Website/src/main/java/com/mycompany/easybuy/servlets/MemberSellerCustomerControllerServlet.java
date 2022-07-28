package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.model.Seller;
import com.mycompany.easybuy.model.Customer;
import com.mycompany.easybuy.utility.MemberUtility;
import com.mycompany.easybuy.utility.SellerUtility;
import com.mycompany.easybuy.utility.CustomerUtility;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class MemberSellerCustomerControllerServlet extends HttpServlet {
    
    MemberUtility memberUtility;
    SellerUtility sellerUtility;
    CustomerUtility customerUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            memberUtility = new MemberUtility(dataSource);
            sellerUtility = new SellerUtility(dataSource);
            customerUtility = new CustomerUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try ( PrintWriter out = response.getWriter()) {

            String demand = request.getParameter("demand");
            if(demand == null) demand = (String) request.getAttribute("demand");
            
            if(demand != null){
                
                HttpSession session = request.getSession();
                
                String command = request.getParameter("command");
                if(command == null) command = (String) request.getAttribute("command");
                
                switch (demand) {
                    case "member":
                        switch(command){
                            case "update":
                                updateMember(request,response);
                                break;
                            case "delete":
                                deleteMember(request,response);
                                break;
                            default:
                                break;
                        }
                        
                        break;
                    case "seller":
                        switch(command){
                            case "add":
                                addSeller(request,response,session);
                                break;
                            default:
                                break;
                        }
                        
                        break;
                    case "customer":
                        switch(command){
                            case "add":
                                addCustomer(request,response,session);
                                break;
                            case "get":
                                getCustomer(request,response,session);
                                break;
                            default:
                                break;
                        }

                        break;
                    default:
                        break;
                }
                return;
            }
            response.sendRedirect("login.jsp");
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
            Logger.getLogger(MemberSellerCustomerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MemberSellerCustomerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void addSeller(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException, ServletException, IOException {
        
        Member member = (Member) session.getAttribute("current-member");
        
        int id = member.getId();
        String gstin = request.getParameter("gstin");
        boolean verified = Boolean.parseBoolean(request.getParameter("verified"));
        String businessName = request.getParameter("business_name");
        String storeDesc = request.getParameter("store_address");
        
        Seller seller = new Seller(id,gstin,verified,businessName,storeDesc);  
        sellerUtility.addSeller(seller);
        
        member.setsellerId(getSellerId(member.getId()));
        memberUtility.updateMember(member);
        session.setAttribute("current-member", member);
        
        session.setAttribute("success", "Congratulations!! You are a Seller now!");
        response.sendRedirect("sell.jsp");
    }
    
    private void addCustomer(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException, ServletException, IOException {
        
        Member member = (Member) session.getAttribute("current-member");
        if(member.getCustomerId() == -1){
            int id = member.getId();
            String deliveryAddress = request.getParameter("delivery_address");;
            String billingAddress = request.getParameter("billing_address");

            Customer customer = new Customer(id,billingAddress,deliveryAddress);
            customerUtility.addCustomer(customer);

            member.setcustomerId(getCustomerId(member.getId()));
            memberUtility.updateMember(member);
            session.setAttribute("current-member", member);
        }else{
            updateCustomer(request,response,session);
        }
        
        if(request.getParameter("info") != null){
            response.getWriter().write("");
            return;
        }
        
        request.setAttribute("payment_id", request.getAttribute("payment_id"));
        request.setAttribute("demand", "product");
        request.setAttribute("command", "update");

        RequestDispatcher dispatcher = request.getRequestDispatcher("update");
        dispatcher.forward(request, response);
    }
    
    private int getSellerId(int memberID) throws SQLException {
        return sellerUtility.getSellerId(memberID);
    }

    private void getCustomer(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        
        Member member = (Member) session.getAttribute("current-member");
        if(member == null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        Customer customer = customerUtility.getCustomer(member.getId());
        
        request.setAttribute("customer", customer);
        request.setAttribute("visit", "manage_address");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
    
    private int getCustomerId(int memberID) throws SQLException {
        return customerUtility.getCustomerId(memberID);
    }

    private void updateMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("current-member");
        
        String operation = request.getParameter("operation");
        
        switch(operation){
            case "name":
                String newFirstName = request.getParameter("first_name");
                String newLastName = request.getParameter("last_name");
                
                member.setFirstName(newFirstName);
                member.setLastName(newLastName);
                
                session.setAttribute("success", "Name Updated Successfully!!");
                break;
            case "phone":
                String newPhoneNumber = request.getParameter("phone");
                member.setPhoneNumber(newPhoneNumber);
                
                session.setAttribute("success", "Phone Number Updated Successfully!!");
                break;
            case "e_mail":
                String newEMailAddress = request.getParameter("e_mail");
                member.setPhoneNumber(newEMailAddress);
                
                session.setAttribute("success", "EMail-ID Updated Successfully!!");
                break;
            case "password":
                String newPassword = request.getParameter("password");
                member.setPassword(newPassword);
                
                session.setAttribute("success", "Password Updated Successfully!!");
                break;
            default:
                break;
        }
        
        memberUtility.updateMember(member);
        session.setAttribute("current-member", member);
        
        response.sendRedirect("dashboard.jsp");
    }
    
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        
        Member member = (Member) session.getAttribute("current-member");
        
        String billingAddress = request.getParameter("billing_address");
        String deliveryAddress = request.getParameter("delivery_address");
        
        Customer customer = new Customer(member.getId(),billingAddress,deliveryAddress);
        customerUtility.updateCustomer(customer);
    }

    private void deleteMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("current-member");
        
        String emailID = request.getParameter("curr_email");
        String password = request.getParameter("curr_password");
        
        if(member.getEMailAddress().equals(emailID) && member.getPassword().equals(password)){
            
            memberUtility.deleteMember(emailID, password);
            
            session.setAttribute("success", "Account Deleted Successfully!!");
            response.sendRedirect("login.jsp");
        }else{
        
            session.setAttribute("error", "Incorred EMail or Password");
            response.sendRedirect("dashboard.jsp");
        }
    }
}
