<%@ page import="com.mycompany.easybuy.model.Member" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    pageContext.setAttribute("member", currentMember);
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>About Us</title>
        
        <style>
            .container-fluid{
                background-color: #ADD8E6;
            }
            html{
                background-color: #ADD8E6;
            }
        </style>
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body>
        
        <%@include file="Components/navbar.jsp"%>
        
        <div class="container-fluid px-5 py-5">
            
            <div class="card">
                <div class="card-header text-center">
                    <h1 class="my-0"><b>ABOUT US</b></h1>
                </div>
            </div>
            
            <div class="row mt-5 mx-0">
                <div class="col-md-4">
                    <div class="card container text-center">
                        <img src="Image/Employees/Divyesh.jpg" class="card-img-top pt-5 px-5 pb-3 text-center" alt="Divyesh Chhabra">
                    
                        <div class="card-body text-center py-0">
                            <h3 class="card-title">Divyesh Chhabra</h3>
                        </div>
                        
                        <p class="card-text text-center pb-5">
                            19ucs208 | Computer Science and Engineering
                        </p>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class=" card container text-center">
                        <img src="Image/Employees/Raghav.jpg" class="card-img-top pt-5 px-5 pb-3 text-center" alt="Raghav Sharma">
                    
                        <div class="card-body text-center py-0">
                            <h3 class="card-title">Raghav Sharma</h3>
                        </div>
                        
                        <p class="card-text text-center pb-5">
                            19ucs204 | Computer Science and Engineering
                        </p>
                    </div>
                  
                </div>

                <div class="col-md-4">
                    <div class=" card container text-center">
                        <img src="Image/Employees/Shail.jpg" class="card-img-top pt-5 px-5 pb-3 text-center" alt="Shail Kardani">
                    
                        <div class="card-body text-center py-0">
                            <h3 class="card-title">Shail Kardani</h3>
                        </div>
                        
                        <p class="card-text text-center pb-5">
                            19ucs217 | Computer Science and Engineering
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
