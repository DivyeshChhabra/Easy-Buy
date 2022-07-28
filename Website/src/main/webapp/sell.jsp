<%@ page import="com.mycompany.easybuy.model.Member" %>

<%@ page import="java.sql.*" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    if(currentMember == null){
        session.setAttribute("error", "Login before Selling");
        response.sendRedirect("login.jsp");

        return;
    }
    
    pageContext.setAttribute("member", currentMember);
    
    if(currentMember.getSellerId() == -1){
        response.sendRedirect("seller-register.jsp");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Sell on Easy-Buy</title>
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body>
        <%@include file="Components/navbar.jsp"%>
        <%@include file="Components/message/success.jsp" %>
        <%@include file="Components/message/error.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5 px-5">
                <div class="col-md-6">
                    <div class="card-body text-center" style="background-color:white;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)">
                        <div class="container" style="padding: 15px;margin-bottom: 35px;">
                            <img style="height: 200px" class="img-fluid" src="Image\Icons\category.png">
                        </div>
                        
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-category-modal">
                            Add Category
                        </button>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="card-body text-center" style="background-color:white;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)">
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
