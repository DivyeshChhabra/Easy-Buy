<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Register</title>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <%@ include file="Components/common_css_js.jsp" %>
    </head>
    
    <body>
        <%@ include file="Components/navbar.jsp" %>
        
        <div class="container-fluid">
            <div class="row py-5">
                <div class="col-md-6 offset-md-3">
                    <div class="card-body px-5 py-5 bg-white text-black" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <%@include file="Components/message/error.jsp" %>
                        
                        <h2 class="text-center my-3"><b>REGISTER HERE</b></h2>
                        
                        <form action="verification" method="POST" autocomplete="on" enctype='multipart/form-data'>
                            <input type="hidden" name="command" value="mail"/>
                            
                            <div class="form-group">
                                <label for="first_name"><b>First Name</b></label>
                                <input type="text" class="form-control" id="first_name" placeholder="First Name" name="first_name" required/>
                            </div>
                            
                            <div class="form-group">
                                <label for="last_name"><b>Last Name</b></label>
                                <input type="text" class="form-control" id="last_name" placeholder="Last Name" name="last_name"/>
                            </div>
                            
                            <div class="form-group">
                                <label for="gender"><b>Gender</b></label>
                                
                                <div>
                                    <label for="male" class="radio-inline">
                                        <input type="radio" name="gender" id="male" value="Male"/>Male 
                                    </label>
                                    
                                    <label for="female" class="radio-inline">
                                        <input type="radio" name="gender" id="female" value="Female"/>Female 
                                    </label>
                                    
                                    <label for="transgender" class="radio-inline">
                                        <input type="radio" name="gender" id="other" value="Transgender"/>Transgender 
                                    </label>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="phone"><b>Phone</b></label>
                                <input type="text" class="form-control" id="phone" placeholder="Phone No." name="phone" required/>
                            </div>
                            
                            <div class="form-group">
                                <label for="e_mail"><b>Email ID</b></label>
                                <input type="email" class="form-control" id="e_mail" aria-describedby="emailHelp" placeholder="E_Mail ID" name="e_mail" required/>
                                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                            </div>
                            
                            <div class="form-group">
                                <label for="password"><b>Password</b></label>
                                <input type="password" class="form-control" id="password" placeholder="Password" name="password" required/>
                            </div>
                            
                            <div class="form-group">
                                <label for="profile_image"><b>Profile Picture</b></label><br>
                                <input type="file" name="profile_image" id="profile_image"/>
                            </div> <br/>
                            
                            <input type="submit" class="btn btn-primary form-control" value="Register"/><br>
                        </form>
                        
                        <div class="text-center">
                            Already had an account? <a href="login.jsp"><b>Login Here</b></a><br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
