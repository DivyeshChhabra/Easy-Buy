<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Login</title>
       
        <link rel="stylesheet" href="CSS/style.css">
        
        <%@ include file="Components/common_css_js.jsp" %>
    </head>
    
    <body>
        
        <%@ include file="Components/navbar.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5">
                <div class="col-md-6 offset-md-3">
                    <div class="card-body px-5 py-5 bg-white text-black" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <%@ include file="Components/message/error.jsp" %>
                        
                        <form action="recoverAccount" method="POST" >
                            
                            <div class="form-group">
                                <label for="e_mail"><b>Email address</b></label>
                                <input type="email" class="form-control" id="e_mail" name="e_mail" placeholder="E_Mail ID" required/>  
                            </div>
                            
                            <input type="submit" class="btn btn-primary form-control" value="Recover Your Account"/> <br/>
                        </form>
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
