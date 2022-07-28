<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.mycompany.easybuy.model.*" %>

<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    if(currentMember == null || currentMember.getType().equals("Normal")){
        session.setAttribute("error", "Unauthorised");
        response.sendRedirect("login.jsp");
        
        return;
    }
    pageContext.setAttribute("member", currentMember);
    
    List<Member> memberList = (List<Member>) request.getAttribute("members");
    pageContext.setAttribute("members", memberList);
    
    List<Customer> customerList = (List<Customer>) request.getAttribute("customers");
    pageContext.setAttribute("customers", customerList);
    
    List<Seller> sellerList = (List<Seller>) request.getAttribute("sellers");
    pageContext.setAttribute("sellers", sellerList);
    
    List<Category> categoryList = (List<Category>) request.getAttribute("categories");
    pageContext.setAttribute("categories", categoryList);
    
    List<Product> productList = (List<Product>) request.getAttribute("products");
    pageContext.setAttribute("products", productList);
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Admin</title>
        
        <style>
            .card-body{
                border-style: solid;
                background-color:white
            }
            .card-body:hover {
                background-color: #e2e2e2;
                cursor: pointer;
            }
        </style>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body>
        <%@include file="Components/navbar.jsp"%>
        
        <%@include file="Components/message/success.jsp"%>
        
        <div class="container-fluid">
            <div class="container py-5">
                <div class="row">
                    <div class="col-md-6" style="margin-bottom:15px;">
                        <div class="card-body text-center" onclick="document.forms['member-form'].submit();">
                            <div class="container" style="padding: 15px;">
                                <img style="height: 125px" class="img-fluid" src="Image\Icons\user.png">
                            </div>

                            <h3>${members.size()}</h3>
                            <h2>MEMBERS</h2>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card-body text-center" onclick="document.forms['customer-form'].submit();">
                            <div class="container" style="padding: 10px;">
                                <img style="height: 100px" class="img-fluid" src="Image\Icons\customer.png">
                            </div>

                            <h4>${customers.size()}</h4>
                            <h3>CUSTOMERS</h3>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card-body text-center" onclick="document.forms['seller-form'].submit();">
                            <div class="container" style="padding: 10px;">
                                <img style="height: 100px" class="img-fluid" src="Image\Icons\seller.png">
                            </div>

                            <h4>${sellers.size()}</h4>
                            <h3>SELLERS</h3>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-body text-center" onclick="document.forms['category-form'].submit();">
                            <div class="container" style="padding: 15px">
                                <img style="height: 125px" class="img-fluid" src="Image\Icons\category.png">
                            </div>

                            <h3>${categories.size()}</h3>
                            <h2>CATEGORIES</h2>
                        </div>    
                    </div>
                    
                    <div class="col-md-6">
                        <div class="card-body text-center" onclick="document.forms['product-form'].submit();">
                            <div class="container" style="padding: 15px">
                                <img style="height: 125px" class="img-fluid" src="Image\Icons\products.png">
                            </div>

                            <h3>${products.size()}</h3>
                            <h2>PRODUCTS</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
