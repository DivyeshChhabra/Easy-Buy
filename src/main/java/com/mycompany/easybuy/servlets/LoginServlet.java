package com.mycompany.easybuy.servlets; /* Package Name */

/* Importing necessary Packages and Classes */
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* Fetching the values from the login form to respective variables */
            String e_mail = request.getParameter("e_mail");
            String password = request.getParameter("password");
            
            try{
                /* Connecting MySQL DataBase with JAVA application */
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
                
                /* Checking if a member exists */
                PreparedStatement stmt4 = myCon.prepareStatement("select * from members where e_mail = ?;");
                stmt4.setString(1, e_mail);
                ResultSet rs3 = stmt4.executeQuery();
                
                /* Creating Session for Login Member details */
                HttpSession httpsession = request.getSession();
                HttpSession session = request.getSession();
                
                if(rs3.next()){ /* Member Found */
                    /* Verifying the password */
                    stmt4 = myCon.prepareStatement("select * from members where e_mail = ? and password = ?;");
                    stmt4.setString(1, e_mail);
                    stmt4.setString(2, password);
                    ResultSet rs4 = stmt4.executeQuery();
                    
                    if(rs4.next()){ /* Correct Password */
                        /* Entering the details of login member in the session created */
                        httpsession.setAttribute("current-user", rs4);
                        
                        /* Getting the type of login member */
                        String type = rs4.getString("type");
                        
                        if(type.equals("Normal")){
                            /* Normal Member */
                            session.setAttribute("success", "Welcome "+rs4.getString("first_name")+" "+rs4.getString("last_name")+"!!");
                            response.sendRedirect("index.jsp");
                        }else{
                            /* Admin Member */
                            session.setAttribute("success", "Welcome Admin!!");
                            response.sendRedirect("admin.jsp");
                        }
                    }else{
                        /* Incorrect Password */
                        session.setAttribute("error", "Incorrect Password");
                        response.sendRedirect("login.jsp");
                    }
                }else{
                    /* Member not found */
                    session.setAttribute("error", "No Such User!! Enter another Mail-ID");
                    response.sendRedirect("login.jsp");
                }
            }catch(Exception e){
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
