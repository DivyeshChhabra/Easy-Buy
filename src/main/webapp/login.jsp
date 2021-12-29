<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            .align-items-center{
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
        <link rel="stylesheet" href="CSS/style.css">
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body class="align-items-center">
        <div class="container-fluid">
            <div class="row mt-5">
                <div class="col-md-6 offset-md-3">
                    <div class="card-body px-5 py-5 bg-white text-black">
                        <%@include file="Components/message/error.jsp" %>
                        <%@include file="Components/message/success.jsp" %>
                        <h2 class="text-center my-3"><b>LOGIN HERE</b></h2>
                        <form autocomplete="on" action="LoginServlet" method="post">
                        <div class="form-group">
                            <label for="e_mail"><b>Email address</b></label>
                            <input type="email" class="form-control" id="e_mail" placeholder="E_Mail ID" name="e_mail" required/>  
                        </div>
                        <div class="form-group">
                            <label for="password"><b>Password</b></label>
                            <input type="password" class="form-control" id="password" placeholder="Password" name="password" required/>
                        </div><br>
                        <input type="submit" class="btn btn-primary form-control" value="Login"><br>
                        </form>
                        <div class="text-center">
                            New User? <a href="register.jsp"><b>Register Here</b></a><br>
                        </div>
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
