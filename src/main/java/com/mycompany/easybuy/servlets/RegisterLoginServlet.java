package com.mycompany.easybuy.servlets; /* Package */

/* Importing necessary Packages and Classes */
import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.utility.MemberUtility;

import java.sql.*;
import java.io.*;
import java.util.logging.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.annotation.Resource;

public class RegisterLoginServlet extends HttpServlet {
    
    MemberUtility memberUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            memberUtility = new MemberUtility(dataSource);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            Member member = null;
            
            String operation = request.getParameter("operation");
            if(operation == null) operation = (String) request.getAttribute("operation");
            
            String emailID = request.getParameter("e_mail");
            String password = request.getParameter("password");
            
            switch(operation){
                case "register":
                    registerUser(request,response);
                default:
                    member = memberUtility.login(emailID,password);
                    if(member == null){
                        session.setAttribute("error", "Incorrect E-Mail or Password");
                        response.sendRedirect("login.jsp");
                
                        return;
                    }
                    
                    session.setAttribute("current-member", member);
                    
                    if(member.getType().equals("Normal")){
                        session.setAttribute("success", "Welcome " + member.getFirstName() + " " + member.getLastName() + "!!");
                        response.sendRedirect("index");
                    }else{
                        session.setAttribute("success", "Welcome Admin!!");
                        response.sendRedirect("admin");
                    }
                    
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
            Logger.getLogger(RegisterLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisterLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phone");
        String EMailAddress = request.getParameter("e_mail");
        String password = request.getParameter("password");
        int sellerId = -1;
        int customerId = -1;
        String type = "Normal";
        String profilePhotoPath = request.getParameter("profile_image");
        
        Member member = new Member(firstName,lastName,gender,phoneNumber,EMailAddress,password,sellerId,customerId,profilePhotoPath,type);
        memberUtility.addMember(member);
    }
}
