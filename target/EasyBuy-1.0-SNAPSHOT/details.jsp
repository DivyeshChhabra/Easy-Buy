<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>

<%
    /* Creating Sessions */
    HttpSession httpsession = request.getSession();
    
    /* Verifying whether the member has logged in */
    ResultSet rs80 = (ResultSet)session.getAttribute("current-user");
    String type="";
    
    if(rs80!=null){ /* Member has logged in */
        /* Getting the member type */
        type = rs80.getString("type");
    }
    
    if(rs80 == null || type.equals("Normal")){ /* Member has not logged in or is a normal member */
        /* Alerting member that only admin member can access admin page */
        httpsession.setAttribute("error", "Only Admins Can Access Admin Page!!");
        response.sendRedirect("login.jsp");
        return;
    }
    
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn5 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
    
    PreparedStatement stmt50 = null;
    ResultSet rs50 = null;
    
    String details = request.getParameter("details");
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=details%></title>
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
            a:hover{
                cursor: pointer;
            }
        </style>
        <link rel="stylesheet" href="CSS/style.css">
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body>
        <%@include file="Components/navbar.jsp"%>
        <div class="container-fluid py-5 px-5">
            <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                <h3 class="card-header text-center"><%=details%></h3>
            <%
                if(details.equals("Members")){
                    stmt50 = myConn5.prepareStatement("select * from members;");
                    rs50 = stmt50.executeQuery();
                    while(rs50.next()){
            %>
                    <div class="card-body px-0">
                        <table class="table my-0">
                            <thead class="thead-light">
                                <tr>
                                    <th class="text-center">Member_ID</th>
                                    <th class="text-center">First Name</th>
                                    <th class="text-center">Last Name</th>
                                    <th class="text-center">Gender</th>
                                    <th class="text-center">Phone</th>
                                    <th class="text-center">E-Mail Address</th>
                                    <th class="text-center">Password</th>
                                    <th class="text-center">Type</th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="text-center"><%=rs50.getInt("member_id")%></td>
                                <td class="text-center"><%=rs50.getString("first_name")%></td>
                                <td class="text-center"><%=rs50.getString("last_name")%></td>
                                <td class="text-center"><%=rs50.getString("gender")%></td>
                                <td class="text-center"><%=rs50.getString("phone")%></td>
                                <td class="text-center"><%=rs50.getString("e_mail")%></td>
                                <td class="text-center"><%=rs50.getString("password")%></td>
                                <td class="text-center"><%=rs50.getString("type")%></td>
                            </tr>
                        </table>    
                    </div>
            <%
                    }
                }else if(details.equals("Customers")){
                    stmt50 = myConn5.prepareStatement("select * from customers join members on members.member_id = customers.member_id");
                    rs50 = stmt50.executeQuery();
                    while(rs50.next()){
                        PreparedStatement stmt25 = myConn5.prepareStatement("select count(order_id) as Purchases from orders join customers on customers.customer_id = orders.customer_id where customers.customer_id = ?");
                        stmt25.setInt(1, rs50.getInt("customer_id"));
                        ResultSet rs25 = stmt25.executeQuery();
                        rs25.next();
            %>
                <form action="details2.jsp" method="post" id="purchaseform">
                    <input type="hidden" name="details2" value="customer">
                    <input type="hidden" name="customer" value="<%=rs50.getInt("customer_id")%>">
                    <div class="card-body px-0">
                        <table class="table my-0">
                            <thead class="thead-light">
                                <tr>
                                    <th class="text-center">Customer_ID</th>
                                    <th class="text-center">First Name</th>
                                    <th class="text-center">Last Name</th>
                                    <th class="text-center">Gender</th>
                                    <th class="text-center">Phone</th>
                                    <th class="text-center">E-Mail Address</th>
                                    <th class="text-center"><a onclick="document.getElementById('purchaseform').submit()" style="text-decoration:none">Total Orders</a></th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="text-center"><%=rs50.getInt("customer_id")%></td>
                                <td class="text-center"><%=rs50.getString("first_name")%></td>
                                <td class="text-center"><%=rs50.getString("last_name")%></td>
                                <td class="text-center"><%=rs50.getString("gender")%></td>
                                <td class="text-center"><%=rs50.getString("phone")%></td>
                                <td class="text-center"><%=rs50.getString("e_mail")%></td>
                                <td class="text-center"><%=rs25.getInt("Purchases")%></td>
                            </tr>
                        </table>    
                    </div>
                </form>
            <%
                    }
                } else if(details.equals("Categories")){
                    stmt50 = myConn5.prepareStatement("select * from category");
                    rs50 = stmt50.executeQuery();
                    while(rs50.next()){
                        PreparedStatement stmt25 = myConn5.prepareStatement("select count(product_id) as productsQty from products join category on category.category_id = products.category_id where category.category_id = ?");
                        stmt25.setInt(1, rs50.getInt("category_id"));
                        ResultSet rs25 = stmt25.executeQuery();
                        rs25.next();
            %>
                <form action="details2.jsp" method="post" id="productslistform">
                    <input type="hidden" name="details2" value="category">
                    <input type="hidden" name="category" value="<%=rs50.getInt("category_id")%>">
                    <div class="card-body px-0">
                        <table class="table my-0">
                            <thead class="thead-light">
                                <tr>
                                    <th class="text-center">Category</th>
                                    <th class="text-center">Sale</th>
                                    <th class="text-center"><a onclick="document.getElementById('productslistform').submit()" style="text-decoration:none">Products Quantity</a></th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="text-center"><%=rs50.getString("category_name")%></td>
                                <td class="text-center"><%=Math.floor(rs50.getFloat("sale")*100)%>%</td>
                                <td class="text-center"><%=rs25.getString("productsQty")%></td>
                            </tr>
                        </table>    
                    </div>
                </form>
            <%
                    }
                } else if(details.equals("Products")){
                    stmt50 = myConn5.prepareStatement("select * from products join category on category.category_id = products.category_id join sellers on products.seller_id = sellers.seller_id join members on members.member_id = sellers.member_id");
                    rs50 = stmt50.executeQuery();
                    while(rs50.next()){
                        PreparedStatement stmt25 = myConn5.prepareStatement("select count(order_id) as productsQty from orders join products on orders.product_id = products.product_id where products.product_id = ?");
                        stmt25.setInt(1, rs50.getInt("product_id"));
                        ResultSet rs25 = stmt25.executeQuery();
                        rs25.next();

                        String first_name = rs50.getString("first_name");
                        first_name = first_name.concat(" ");
                        String last_name = rs50.getString("last_name");
                        String full_name = first_name.concat(last_name);
            %>
                <form action="details2.jsp" method="post" id="customerform">
                    <input type="hidden" name="details2" value="products">
                    <input type="hidden" name="products" value="<%=rs50.getInt("product_id")%>">
                    <div class="card-body px-0">
                        <table class="table my-0">
                            <thead class="thead-light">
                                <tr>
                                    <th class="text-center">Product_ID</th>
                                    <th class="text-center">Name</th>
                                    <th class="text-center">Unit Price</th>
                                    <th class="text-center">Units Left</th>
                                    <th class="text-center">Discount</th>
                                    <th class="text-center">Category</th>
                                    <th class="text-center">Seller</th>
                                    <th class="text-center"><a onclick="document.getElementById('customerform').submit()" style="text-decoration:none">Purchase Frequency</a></th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="text-center"><%=rs50.getInt("product_id")%></td>
                                <td class="text-center"><%=rs50.getString("product_name")%></td>
                                <td class="text-center"><i class="fa fa-inr" aria-hidden="false"></i><%=Math.ceil(rs50.getDouble("unit_price"))%></td>
                                <td class="text-center"><%=rs50.getInt("units_left")%></td>
                                <td class="text-center"><%=Math.floor(rs50.getFloat("discount")*100)%>%</td>
                                <td class="text-center"><%=rs50.getString("category_name")%></td>
                                <td class="text-center"><%=full_name%></td>
                                <td class="text-center"><%=rs25.getInt("productsQty")%></td>
                            </tr>
                        </table>    
                    </div>
                </form>
            <%
                    }
                } else if(details.equals("Sellers")){
                    stmt50 = myConn5.prepareStatement("select * from sellers join members on members.member_id=sellers.member_id");
                    rs50 = stmt50.executeQuery();
                    while(rs50.next()){
                        PreparedStatement stmt25 = myConn5.prepareStatement("select count(product_id) as productsQty from products join sellers on sellers.seller_id = products.seller_id where sellers.seller_id = ?");
                        stmt25.setInt(1, rs50.getInt("seller_id"));
                        ResultSet rs25 = stmt25.executeQuery();
                        rs25.next();
            %>
                <form action="details2.jsp" method="post" id="productsform">
                    <input type="hidden" name="details2" value="sellers">
                    <input type="hidden" name="sellers" value="<%=rs50.getInt("seller_id")%>">
                    <div class="card-body px-0">
                        <table class="table my-0">
                            <thead class="thead-light">
                                <tr>
                                    <th class="text-center">Seller_ID</th>
                                    <th class="text-center">First Name</th>
                                    <th class="text-center">Last Name</th>
                                    <th class="text-center">Gender</th>
                                    <th class="text-center">Phone</th>
                                    <th class="text-center">E-Mail Address</th>
                                    <th class="text-center">Company</th>
                                    <th class="text-center">Account Number</th>
                                    <th class="text-center"><a onclick="document.getElementById('productsform').submit()" style="text-decoration:none">Total Products</a></th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="text-center"><%=rs50.getInt("seller_id")%></td>
                                <td class="text-center"><%=rs50.getString("first_name")%></td>
                                <td class="text-center"><%=rs50.getString("last_name")%></td>
                                <td class="text-center"><%=rs50.getString("gender")%></td>
                                <td class="text-center"><%=rs50.getString("phone")%></td>
                                <td class="text-center"><%=rs50.getString("e_mail")%></td>
                                <th class="text-center"><%=rs50.getString("company_name")%></th>
                                <th class="text-center"><%=rs50.getLong("account_details")%></th>
                                <td class="text-center"><%=rs25.getString("productsQty")%></td>
                            </tr>
                        </table>    
                    </div>
                </form>
            <%
                    }
                }
            %>
        </div>
        <%@include file="Components/common_modal.jsp"%>
    </body>
</html>
