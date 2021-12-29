<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>About Us</title>
        <style>
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
        <div class="container-fluid px-5 py-5">
            
            <div class="card">
                <div class="card-header text-center">
                    <h2 class="my-0">About Us</h2>
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
                            Chief Executing Officer
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
                            Chief Technology Officer
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
                            Chief Marketing Officer
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
