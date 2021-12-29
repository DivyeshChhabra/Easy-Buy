package com.mycompany.easybuy.servlets;

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

public class EditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* Connecting MySQL DataBase with JAVA application */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
            
            /* Knowing which operation to perform */
            String operation = request.getParameter("operation");
            
            /* Generating Session */
            HttpSession session = request.getSession();
            HttpSession httpsession = request.getSession();
            
            /* Getting the details of current logged in member */
            ResultSet rs19 = (ResultSet)httpsession.getAttribute("current-user");
            
            if(operation.equals("name")){ /* Modify Name */
                /* Fetching the values from the form to respective variables */
                String first_name = request.getParameter("first_name");
                String last_name = request.getParameter("last_name");
                String password = request.getParameter("curr_password");
                
                /* Verfying the current password */
                if(rs19.getString("password").equals(password)){ /* Correct Password */
                    /* Updating the values of member table - QUERY 1 */
                    PreparedStatement stmt15 = myCon.prepareStatement("update members set first_name = ?, last_name = ? where member_id = ?;");
                    stmt15.setString(1, first_name);
                    stmt15.setString(2, last_name);
                    stmt15.setInt(3, rs19.getInt("member_id"));
                    int rowEff = stmt15.executeUpdate();
                    if(rowEff>0){
                        /* Name Changed Successfully */
                        stmt15 = myCon.prepareStatement("select * from members where member_id=?");
                        stmt15.setInt(1, rs19.getInt("member_id"));
                        ResultSet rs30 = stmt15.executeQuery();
                        if(rs30.next()){
                            httpsession.setAttribute("current-user", rs30);
                            session.setAttribute("success", "Name Changed Successfully");
                            response.sendRedirect("member.jsp");
                        }
                    }
                }else{
                    /* Alerting the user that password is incorrect */
                    session.setAttribute("error", "Incorrect Password");
                    response.sendRedirect("member.jsp");
                }
            } else if(operation.equals("phone")){ /* Modify Phone */
                /* Fetching the values from the form to respective variables */
                String phone = request.getParameter("phone");
                String password = request.getParameter("curr_password");
                
                /* Verfying the current password */
                if(rs19.getString("password").equals(password)){ /* Correct Password */
                    /* Verifying if there is a person with the same phone number */
                    PreparedStatement stmt16 = myCon.prepareStatement("select * from members where phone = ?;");
                    stmt16.setString(1, phone);
                    ResultSet rs20 = stmt16.executeQuery();

                    if(rs20.next()){
                        /* Alerting the user that there is already another user with the same phone number */
                        session.setAttribute("error", "Enter new Phone Number!! User with same phone number already exist!!");
                        response.sendRedirect("member.jsp");
                    }else{
                        /* Updating the values of member table - QUERY 2 */
                        PreparedStatement stmt17 = myCon.prepareStatement("update members set phone = ? where member_id = ?;");
                        stmt17.setString(1, phone);
                        stmt17.setInt(2, rs19.getInt("member_id"));
                        int rowEff = stmt17.executeUpdate();
                        if(rowEff>0){
                            /* Phone Number Changed Successfully */
                            stmt17 = myCon.prepareStatement("select * from members where member_id=?");
                            stmt17.setInt(1, rs19.getInt("member_id"));
                            ResultSet rs30 = stmt17.executeQuery();
                            if(rs30.next()){
                                httpsession.setAttribute("current-user", rs30);
                                session.setAttribute("success", "Phone Number Changed Successfully");
                                response.sendRedirect("member.jsp");
                            }
                        }  
                    }
                }else{
                    /* Alerting the user that password is incorrect */
                    session.setAttribute("error", "Incorrect Password");
                    response.sendRedirect("member.jsp");
                }
            } else if(operation.equals("e_mail")){ /* Modify E-Mail Address */
                /* Fetching the values from the form to respective variables */
                String e_mail = request.getParameter("e_mail");
                String password = request.getParameter("curr_password");
                
                /* Verfying the current password */
                if(rs19.getString("password").equals(password)){ /* Correct Password */
                    /* Verifying if there is a person with the same e_mail address */
                    PreparedStatement stmt18 = myCon.prepareStatement("select * from members where e_mail = ?;");
                    stmt18.setString(1, e_mail);
                    ResultSet rs21 = stmt18.executeQuery();

                    if(rs21.next()){
                        /* Alerting the user that there is already another user with the same e_mail address */
                        session.setAttribute("error", "Enter new E-Mail Address!! User with same e-mail address already exist!!");
                        response.sendRedirect("member.jsp");
                    }else{
                        /* Updating the values of member table - QUERY 3 */
                        PreparedStatement stmt19 = myCon.prepareStatement("update members set e_mail = ? where member_id = ?;");
                        stmt19.setString(1, e_mail);
                        stmt19.setInt(2, rs19.getInt("member_id"));
                        int rowEff = stmt19.executeUpdate();
                        if(rowEff>0){
                            /* E_Mail Changed Successfully */
                            stmt19 = myCon.prepareStatement("select * from members where member_id=?");
                            stmt19.setInt(1, rs19.getInt("member_id"));
                            ResultSet rs30 = stmt19.executeQuery();
                            if(rs30.next()){
                                httpsession.setAttribute("current-user", rs30);
                                session.setAttribute("success", "E-Mail Address Changed Successfully");
                                response.sendRedirect("member.jsp");
                            }
                        }
                    }
                }else{
                    /* Alerting the user that password is incorrect */
                    session.setAttribute("error", "Incorrect Password");
                    response.sendRedirect("member.jsp");
                }
            } else if(operation.equals("delete")){ /* Delete Account */
                /* Fetching the values from the form to respective variables */
                String e_mail = request.getParameter("e_mail");
                String password = request.getParameter("curr_password");
                
                /* Checking if a member exists */
                PreparedStatement stmt20 = myCon.prepareStatement("select * from members where e_mail = ?;");
                stmt20.setString(1, e_mail);
                ResultSet rs22 = stmt20.executeQuery();
                
                if(rs22.next()){ /* Member Found */
                    /* Verifying the password */
                    stmt20 = myCon.prepareStatement("select * from members where e_mail = ? and password = ?;");
                    stmt20.setString(1, e_mail);
                    stmt20.setString(2, password);
                    ResultSet rs23 = stmt20.executeQuery();
                    
                    if(rs23.next()){ /* Correct Password */
                        /* Deleting the values of member table - QUERY 4 */
                        PreparedStatement stmt21 = myCon.prepareStatement("delete from members where e_mail = ?;");
                        stmt21.setString(1, e_mail);
                        int rowEff = stmt21.executeUpdate();
                        if(rowEff>0){
                            session.setAttribute("success", "Account Deleted Successfully");
                            response.sendRedirect("login.jsp");
                        }
                    }else{
                        /* Incorrect Password */
                        session.setAttribute("error", "Incorrect Password");
                        response.sendRedirect("member.jsp");
                    }
                }else{
                    /* Incorrect E-Mail */
                    session.setAttribute("error", "Incorrect E-Mail Address");
                    response.sendRedirect("member.jsp");
                }
            } else if(operation.equals("password")){ /* Modify Password */
                /* Fetching the values from the form to respective variables */
                String curr_password = request.getParameter("curr_password");
                String password = request.getParameter("password");
                String new_password = request.getParameter("new_password");
                
                /* Verfying the current password */
                if(rs19.getString("password").equals(curr_password)){ /* Correct Password */
                    /* Verifying whether the passwords are matched */
                    if(password.equals(new_password)){ /* Password Matched */
                        /* Updating the values of member table - QUERY 5 */
                        PreparedStatement stmt22 = myCon.prepareStatement("update members set password = ? where member_id = ?;");
                        stmt22.setString(1,password);
                        stmt22.setInt(2, rs19.getInt("member_id"));
                        int rowEff = stmt22.executeUpdate();
                        if(rowEff>0){
                            /* Password Changed Successfully */
                            stmt22 = myCon.prepareStatement("select * from members where member_id=?");
                            stmt22.setInt(1, rs19.getInt("member_id"));
                            ResultSet rs30 = stmt22.executeQuery();
                            if(rs30.next()){
                                httpsession.setAttribute("current-user", rs30);
                                session.setAttribute("success", "Password Address Changed Successfully");
                                response.sendRedirect("member.jsp");
                            }
                        }
                    }else{ /* Password doesn't Matched */
                        /* Alerting the user that password doesn't matched */
                        session.setAttribute("error", "Password Doesnot Matched");
                        response.sendRedirect("member.jsp");
                    }
                }else{
                    /* Alerting the user that password is incorrect */
                    session.setAttribute("error", "Incorrect Password");
                    response.sendRedirect("member.jsp");
                }
            } else if(operation.equals("quantity")){ /* Update the quantities of product */
                /* Fetching the values from the form to respective variables */
                int quantities = Integer.parseInt(request.getParameter("quantities"));
                int productID = Integer.parseInt(request.getParameter("productID"));
                
                PreparedStatement stmt23 = myCon.prepareStatement("update products set units_left = units_left + ? where product_id = ?");
                stmt23.setInt(1, quantities);
                stmt23.setInt(2, productID);
                int rowEff = stmt23.executeUpdate();
                if(rowEff>0){
                    session.setAttribute("success", "Products Added");
                    response.sendRedirect("member.jsp");
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
