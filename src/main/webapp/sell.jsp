<%@page import="java.sql.*"%>

<%
    /* Creating Sessions */
    HttpSession httpsession = request.getSession();
    
    /* Verifying whether the member has logged in */
    ResultSet rs12 = (ResultSet)session.getAttribute("current-user");
    
    if(rs12 == null){
        /* Member  has not logged in */
        httpsession.setAttribute("error", "You have to Login before Selling!!");
        response.sendRedirect("login.jsp");
        return;
    }
    
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sell on Easy-Buy</title>
        <style>
            .card-body{
                border-style: solid;
            }
            .container-fluid{
                background: url('Image/Register-Login Background.jpg') no-repeat center center fixed;
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }
            html{
                background: url('Image/Register-Login Background.jpg') no-repeat center center fixed;
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }
        </style>
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body>
        <%@include file="Components/navbar.jsp"%>
        <%@include file="Components/message/success.jsp" %>
        <%@include file="Components/message/error.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5 px-5">
                <div class="col-md-6">
                    <div class="card-body text-center" style="background-color:white">
                        <div class="container" style="padding: 15px;margin-bottom: 35px;">
                            <img style="height: 200px" class="img-fluid" src="Image\Icons\category.png">
                        </div>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-category-modal">
                            Add Category
                        </button>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card-body text-center" style="background-color:white">
                        <div class="container" style="padding: 15px;margin-bottom: 35px;">
                            <img style="height: 200px" class="img-fluid" src="Image\Icons\products.png">
                        </div>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-product-modal">
                            Add Product
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="Components/common_modal.jsp"%>
    </body>
</html>
