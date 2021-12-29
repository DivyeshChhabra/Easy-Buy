<%@page import="java.sql.*"%>
<%@page import="javax.servlet.http.HttpSession" %>

<%
    /* Creating Sessions */
    HttpSession httpsession = request.getSession();
    
    /* Verifying whether the member has logged in */
    ResultSet rs8 = (ResultSet)session.getAttribute("current-user");
    String type="";
    
    if(rs8!=null){ /* Member has logged in */
        /* Getting the member type */
        type = rs8.getString("type");
    }
    
    if(rs8 == null || type.equals("Normal")){ /* Member has not logged in or is a normal member */
        /* Alerting member that only admin member can access admin page */
        httpsession.setAttribute("error", "Only Admins Can Access Admin Page!!");
        response.sendRedirect("login.jsp");
        return;
    }
    
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <form action="details.jsp" method="post" autocomplete="off" id="member-form" name="member-form">
                            <input type="hidden" name="details" id="members" value="Members">
                            <div class="card-body text-center" onclick="document.forms['member-form'].submit();">
                                <div class="container" style="padding: 15px;">
                                    <img style="height: 125px" class="img-fluid" src="Image\Icons\user.png">
                                </div>
                                <h3>
                                    <%
                                        PreparedStatement stmt45 = myConn3.prepareStatement("select count(member_id) as members from members");
                                        ResultSet rs45 = stmt45.executeQuery();
                                        rs45.next();
                                    %>
                                    <%=rs45.getInt("members")%>
                                </h3>
                                <h2>MEMBERS</h2>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3">
                        <form action="details.jsp" method="post" autocomplete="off" id="customer-form" name="customer-form">
                            <input type="hidden" name="details" id="customers" value="Customers">
                            <div class="card-body text-center" onclick="document.forms['customer-form'].submit();">
                                <div class="container" style="padding: 10px;">
                                    <img style="height: 100px" class="img-fluid" src="Image\Icons\customer.png">
                                </div>
                                <h4>
                                    <%
                                        stmt45 = myConn3.prepareStatement("select count(customer_id) as customers from customers");
                                        rs45 = stmt45.executeQuery();
                                        rs45.next();
                                    %>
                                    <%=rs45.getInt("customers")%>
                                </h4>
                                <h3>CUSTOMERS</h3>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3">
                        <form action="details.jsp" method="post" autocomplete="off" id="seller-form" name="seller-form">
                            <input type="hidden" name="details" id="sellers" value="Sellers">
                            <div class="card-body text-center" onclick="document.forms['seller-form'].submit();">
                                <div class="container" style="padding: 10px;">
                                    <img style="height: 100px" class="img-fluid" src="Image\Icons\seller.png">
                                </div>
                                <h4>
                                    <%
                                        stmt45 = myConn3.prepareStatement("select count(seller_id) as sellers from sellers");
                                        rs45 = stmt45.executeQuery();
                                        rs45.next();
                                    %>
                                    <%=rs45.getInt("sellers")%>
                                </h4>
                                <h3>SELLERS</h3>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <form action="details.jsp" method="post" autocomplete="off" id="category-form" name="category-form">
                            <input type="hidden" name="details" id="category" value="Categories">
                            <div class="card-body text-center" onclick="document.forms['category-form'].submit();">
                                <div class="container" style="padding: 15px">
                                    <img style="height: 125px" class="img-fluid" src="Image\Icons\category.png">
                                </div>
                                <h3>
                                    <%
                                        stmt45 = myConn3.prepareStatement("select count(category_id) as categories from category");
                                        rs45 = stmt45.executeQuery();
                                        rs45.next();
                                    %>
                                    <%=rs45.getInt("categories")%>
                                </h3>
                                <h2>CATEGORIES</h2>
                            </div>
                        </form>        
                    </div>
                    <div class="col-md-6">
                        <form action="details.jsp" method="post" autocomplete="off" id="product-form" name="product-form">
                            <input type="hidden" name="details" id="products" value="Products">
                            <div class="card-body text-center" onclick="document.forms['product-form'].submit();">
                                <div class="container" style="padding: 15px">
                                    <img style="height: 125px" class="img-fluid" src="Image\Icons\products.png">
                                </div>
                                <h3>
                                    <%
                                        stmt45 = myConn3.prepareStatement("select count(product_id) as products from products");
                                        rs45 = stmt45.executeQuery();
                                        rs45.next();
                                    %>
                                    <%=rs45.getInt("products")%>
                                </h3>
                                <h2>PRODUCTS</h2>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
