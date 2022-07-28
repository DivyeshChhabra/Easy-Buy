<%@page import="java.util.Enumeration"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Verify</title>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <%@ include file="Components/common_css_js.jsp" %>
    </head>
    
    <body>
        <%@ include file="Components/navbar.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5">
                <div class="col-md-6 offset-md-3">
                    <div class="card-body px-5 py-5 bg-white text-black" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <%@ include file="Components/message/success.jsp" %>
                        <%@ include file="Components/message/error.jsp" %>
                        
                        <br/><br/>
                        
                        <form action="verification" method="POST" autocomplete="off" enctype='multipart/form-data'>
                            
                            <input type="hidden" name="command" value="verify"/>
                            <input type="hidden" name="first_name" value="${param.first_name}"/>
                            <input type="hidden" name="last_name" value="${param.last_name}"/>
                            <input type="hidden" name="gender" value="${param.gender}"/>
                            <input type="hidden" name="phone" value="${param.phone}"/>
                            <input type="hidden" name="e_mail" value="${param.e_mail}"/>
                            <input type="hidden" name="password" value="${param.password}"/>
                            <input type="hidden" name="profile_image" value="<%= request.getAttribute("profile_image") %>"/>
                            
                            <div class="form-group">
                                <label for="otp"><b>E-Mail Verification</b></label>
                                <input type="text" class="form-control" id="otp" placeholder="Enter OTP" name="otp" required/>
                            </div>
                            
                            <input type="submit" class="btn btn-primary form-control" value="Submit"/><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
