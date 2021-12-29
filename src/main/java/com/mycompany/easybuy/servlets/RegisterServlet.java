package com.mycompany.easybuy.servlets; /* Package Name */

/* Importing necessary Packages and Classes */
import java.sql.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 16777215)
public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* Fetching the values from the register form to respective variables */
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String e_mail = request.getParameter("e_mail");
            String password = request.getParameter("password");
            
            /* Insert the image and uploading it in the target folder */
            InputStream inputstream = null;
            Part profile_image = request.getPart("profile_image");
            
            if(profile_image!=null){
                inputstream = profile_image.getInputStream();
            }
            
            String name = profile_image.getSubmittedFileName();
            if(name!=""){
                String path = request.getRealPath("Image")+File.separator+"Members"+File.separator+name;
                FileOutputStream outputstream = new FileOutputStream(path);
                byte[] data = new byte[inputstream.available()];
                inputstream.read(data);
                outputstream.write(data);
                outputstream.close();
            }else{
                name = null;
            }
            
            
            try{
                /* Connecting MySQL DataBase with JAVA application */
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy","root","root");
                
                /* Inserting values into the member table - QUERY 1 (main query) */
                PreparedStatement stmt1 = myCon.prepareStatement("insert into members(first_name,last_name,gender,phone,e_mail,password,profile_picture) values(?,?,?,?,?,?,?);");
                stmt1.setString(1, first_name);
                stmt1.setString(2, last_name);
                stmt1.setString(3, gender);
                stmt1.setString(4, phone);
                stmt1.setString(5, e_mail);
                stmt1.setString(6, password);
                stmt1.setString(7, name);
                
                /* Verifying if there is a person with the same e-mail ID */
                PreparedStatement stmt2 = myCon.prepareStatement("select * from members where e_mail = ?;");
                stmt2.setString(1, e_mail);
                ResultSet rs1 = stmt2.executeQuery();
                
                /* Creating Session */
                HttpSession session = request.getSession();
                
                if(rs1.next()){
                    /* Alerting the user that there is already another user with the same e-mail ID */
                    session.setAttribute("error", "User already exist!! Enter New Mail-ID");
                    response.sendRedirect("register.jsp");
                }
                else{
                    /* Verifying if there is a person with the same phone number */
                    PreparedStatement stmt3 = myCon.prepareStatement("select * from members where phone = ?;");
                    stmt3.setString(1, phone);
                    ResultSet rs2 = stmt3.executeQuery();
                    
                    if(rs2.next()){
                        /* Alerting the user that there is already another user with the same phone number */
                        session.setAttribute("error", "Enter new Phone Number");
                        response.sendRedirect("register.jsp");
                    }else{
                        /* Executing QUERY 1 */
                        int rowEff = stmt1.executeUpdate();
                        if(rowEff>0){
                            /* SuccessFull Login */
                            
                            PreparedStatement stmt = myCon.prepareStatement("update members set type = ? where member_id = 1");
                            stmt.setString(1, "Admin");
                            int row = stmt.executeUpdate();
                            
                            session.setAttribute("success", "Registration done successfully!!");
                            response.sendRedirect("login.jsp");
                        }else{
                            /* Unsuccessfull Login */
                            out.println("ERROR!!");
                        }
                    }
                }
            }catch(ClassNotFoundException | SQLException e){   
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
