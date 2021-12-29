<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seller</title>
        <link rel="stylesheet" href="CSS/style.css">
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
        <div class="container-fluid">
            <div class="row mt-5">
                <div class="col-md-4 offset-md-4">
                    <div class="card-body px-5 py-5 bg-white text-black">
                        <h2 class="text-center my-3"><b>BECOME A SELLER</b></h2>
                        <form autocomplete="off" action="SellerServlet" method="post">
                            <div class="form-group">
                                <label for="company_name"><b>Company Name</b></label>
                                <input type="text" class="form-control" id="company_name" placeholder="Company Name" name="company_name" required>
                            </div>
                            <div class="dropdown-divider"></div>
                            <div class="text-center">
                                <b>ACCOUNT DETAILS</b><br><br>
                            </div>
                            <div class="form-group">
                                <label for="account_no"><b>Account Number</b></label>
                                <input type="text" class="form-control" id="account_no" placeholder="Account Number" name="account_no" required>
                            </div>
                            <div class="dropdown-divider"></div>
                            <div class="text-center">
                                <b>WARE-HOUSE ADDRESS</b><br><br>
                            </div>
                            <div class="form-group">
                                <label for="address"><b>WareHouse Address</b></label>
                                <textarea class="form-control" id="address" placeholder="WareHouse Address" name="warehouse_address" required></textarea>
                            </div><br>
                            <input type="submit" class="btn btn-primary form-control" value="Submit"><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
