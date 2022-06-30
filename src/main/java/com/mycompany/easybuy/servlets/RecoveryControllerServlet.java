package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.utility.MailUtility;
import com.mycompany.easybuy.utility.MemberUtility;

import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.annotation.Resource;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class RecoveryControllerServlet extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            
            String emailID = request.getParameter("e_mail");
            
            Member member = memberUtility.getMember(emailID);
            if(member == null){
                session.setAttribute("error", "No User Found");
                response.sendRedirect("recover.jsp");
                
                return;
            }
            
            String message = "Hi Dear!!\n" +
                             "Below is your Easy-Buy Login details\n\n" + 
                             "EMail ID: " + emailID + "\n" +
                             "Password: " + member.getPassword();
            String subject = "Account Recovery";
            
            MailUtility mailUtility = new MailUtility(emailID,message,subject);
            mailUtility.sendMail();
            
            session.setAttribute("success", "Login Details are sent to E-Mail");
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
            Logger.getLogger(RecoveryControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RecoveryControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
