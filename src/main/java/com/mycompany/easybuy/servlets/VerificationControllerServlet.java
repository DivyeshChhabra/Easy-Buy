package com.mycompany.easybuy.servlets;

import com.mycompany.easybuy.model.Member;
import com.mycompany.easybuy.utility.MemberUtility;
import com.mycompany.easybuy.utility.OTPUtility;
import com.mycompany.easybuy.utility.MailUtility;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.annotation.Resource;
import javax.sql.DataSource;


@MultipartConfig(maxFileSize = 16777215)
public class VerificationControllerServlet extends HttpServlet {
    
    MemberUtility memberUtility;
    OTPUtility otpUtility;
    
    @Resource(name="jdbc/easy_buy")
    DataSource dataSource;
    
    @Override
    public void init(){
        try{
            memberUtility = new MemberUtility(dataSource);
            otpUtility = new OTPUtility();
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
            if(member != null){
                session.setAttribute("error", "User Already Exists");
                response.sendRedirect("register.jsp");

                return;
            }
            
            String command = request.getParameter("command");
            
            switch(command){
                case "mail":
                    mailOTP(request,response,session);
                    break;
                case "verify":
                    verifyOTP(request,response,session);
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
            Logger.getLogger(VerificationControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VerificationControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void mailOTP(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        otpUtility.setOtp();
        int otp = otpUtility.getOtp();

        String fullName = request.getParameter("first_name") + " " + request.getParameter("last_name");
        String emailID = request.getParameter("e_mail");
        String message = "Hi " + fullName + "!!\n" +
                         "Welcome to Easy Buy, use " + otp + " as One Time Password (OTP) for your E-Mail Verification\n\n" + 
                         "Please donot share this OTP with anyone for security reasons";
        String subject = "EMail Verification";

        MailUtility mailUtility = new MailUtility(emailID, message, subject);
        mailUtility.sendMail();
        
        setAttributes(request,session);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("verify.jsp");
        dispatcher.forward(request, response);
    }

    private void verifyOTP(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        
        int realOTP = otpUtility.getOtp();
        int enteredOTP = Integer.parseInt(request.getParameter("otp"));
        
        if(realOTP != enteredOTP){
            session.setAttribute("error", "Incorrect OTP\nRegister Again");
            response.sendRedirect("register.jsp");
        }else{
            request.setAttribute("operation", "register");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("welcome");
            dispatcher.forward(request, response);
        }
    }

    private void setAttributes(HttpServletRequest request, HttpSession session) throws IOException, ServletException {
        Part profilePhoto = request.getPart("profile_image");
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String profilePhotoPath = profilePhoto.getSubmittedFileName();
        
        if(profilePhoto != null){
            inputStream = profilePhoto.getInputStream();
        }
        
        if(profilePhotoPath.length() != 0){

            String path = request.getRealPath("Image") + File.separator + "Members" + File.separator + profilePhotoPath;
            outputStream = new FileOutputStream(path);
            byte[] data = new byte[inputStream.available()];

            inputStream.read(data);

            outputStream.write(data);
            outputStream.close();
        }else{
            profilePhotoPath = null;
        }
        
        request.setAttribute("profile_image", profilePhotoPath);
        session.setAttribute("success", "OTP is sent to entered EMail ID");
    }
}
