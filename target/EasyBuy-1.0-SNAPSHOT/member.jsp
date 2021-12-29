<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>

<%
    /* Creating Sessions */
    HttpSession httpsession = request.getSession();
    
    /* Verifying whether the member has logged in */
    ResultSet rs18 = (ResultSet)session.getAttribute("current-user");
    
    if(rs18 == null){ /* Member has not logged in */
        /* Alerting member that only logged in  member can access account page */
        httpsession.setAttribute("error", "You Are Not Logged In!!");
        response.sendRedirect("login.jsp");
        return;
    }
    
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Profile</title>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body style="overflow-x: hidden;">
        <%@include file="Components/navbar.jsp"%>
        <%@include file="Components/message/success.jsp"%>
        <%@include file="Components/message/error.jsp"%>
        <div class="container-fluid px-5 py-5">
            <div class="row">
                <div class="col-md-3">
                    <div class="card" data-spy="affix" data-offset-top="197" style="width: 18rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <%
                            if(rs18.getString("profile_picture")==null){
                        %>
                        <img class="card-img-top" src="Image/Icons/user.png" style="padding:20px 20px 0px 20px;border-radius: 45px">
                        <%
                            }else{
                        %>
                        <img class="card-img-top" src="Image/Members/<%=rs18.getString("profile_picture")%>" style="padding:20px 20px 0px 20px;border-radius: 45px">
                        <%
                            }
                        %>
                        <div class="card-body text-center">
                            <h6>Hello,</h6>
                            <h3 class="card-title my-0" style="color: #0080ff"><%=rs18.getString("first_name")%><%=" "%><%=rs18.getString("last_name")%></h3>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body px-0 py-0">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <a href="member.jsp?visit=my_orders" style="text-decoration: none"><i class="fa fa-list" aria-hidden="true"></i><b> My Orders</b></a>
                                </li>
                                <%
                                    PreparedStatement stmt = myConn2.prepareStatement("select * from sellers where member_id = ?");
                                    stmt.setInt(1,rs18.getInt("member_id"));
                                    ResultSet rs = stmt.executeQuery();
                                    if(rs.next()){
                                    %>
                                        <li class="list-group-item">
                                            <a href="member.jsp?visit=my_products" style="text-decoration: none"><i class="fa fa-tags" aria-hidden="true"></i><b> My Products</b></a>
                                        </li>
                                    <%
                                    }
                                %>
                                
                                <li class="list-group-item">
                                    <i class="fa fa-user-circle" aria-hidden="true"></i><b> Account Settings</b>
                                    <ul>
                                        <li class="list-group-item">
                                            <a href="member.jsp?visit=personal_information" style="text-decoration: none"><b>Personal Information</b></a>
                                        </li>
                                        <%
                                            PreparedStatement stmt10 = myConn2.prepareStatement("select * from customers where member_id = ?");
                                            stmt10.setInt(1,rs18.getInt("member_id"));
                                            ResultSet rs20 = stmt10.executeQuery();
                                            if(rs20.next()){
                                                %>
                                                    <li class="list-group-item">
                                                        <a href="member.jsp?visit=manage_address" style="text-decoration: none"><b>Manage Address</b></a>
                                                    </li>
                                                <%
                                            }
                                        %>
                                    </ul>
                                </li>
                                <li class="list-group-item">
                                    <a href="LogoutServlet" style="text-decoration: none"><i class="fa fa-sign-out" aria-hidden="true"></i><b> Logout</b></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <%
                        String visit = request.getParameter("visit");
                    
                        if(visit==null||visit.equals("personal_information")){
                    %>
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h3 class="card-header">Personal Information</h3>
                        <div class="card-body">
                            <h5 class="card-title">Full Name:</h5>
                            <h4 class="card-text"><%=rs18.getString("first_name")%><%=" "%><%=rs18.getString("last_name")%><%=" "%><small><a href="#" style="text-decoration: none" data-toggle="modal" data-target="#edit-name-modal">Edit</a></small></h4>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body">
                            <h5 class="card-title">Gender:</h5>
                            <h4 class="card-text"><%=rs18.getString("gender")%><br></h4>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body">
                            <h5 class="card-title">Phone:</h5>
                            <h4 class="card-text"><%=rs18.getString("phone")%><%=" "%><small><a href="#" style="text-decoration: none" data-toggle="modal" data-target="#edit-phone-modal">Edit</a></small></h4>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body text-center">
                            <button type="button" class="btn btn-primary mx-3" data-toggle="modal" data-target="#edit-e_mail-modal">
                                Change E-Mail
                            </button>
                            <button type="button" class="btn btn-primary mx-3" data-toggle="modal" data-target="#edit-password-modal">
                                Change Password
                            </button>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body text-center">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#delete-account-modal">
                                Delete Account
                            </button>
                        </div>
                    </div>
                    <%
                        } else if(visit.equals("my_orders")){        
                    %>
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h3 class="card-header">My Orders</h3>
                    <%
                        PreparedStatement stmt13 = myConn2.prepareStatement("select * from orders join customers on customers.customer_id = orders.customer_id join members on members.member_id = customers.member_id join products on products.product_id=orders.product_id join payments on payments.payment_id = orders.order_id where members.member_id=?");
                        stmt13.setInt(1,rs18.getInt("member_id"));
                        ResultSet rs19 = stmt13.executeQuery();
                        if(!rs19.next()){
                            %>
                                <div class="card-body">
                                    <h4 class="card-title text-center my-0">No Orders Placed Yet!!</h4>    
                                </div>
                            <%
                        }else{
                            rs19 = stmt13.executeQuery();
                            while(rs19.next()){
                            %>
                                <div class="card-body px-0">
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Order_ID</th>
                                                <th class="text-center">Product Name</th>
                                                <th class="text-center">Quantity</th>
                                                <th class="text-center">Product Price</th>
                                                <th class="text-center">Order Total<small> (5% tax)</small></th>
                                                <th class="text-center">Payment Method</th>
                                                <th class="text-center">Order Date</th>
                                                <th class="text-center">Delivery Date</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td class="text-center"><%=rs19.getInt("order_id")%></td>
                                            <td class="text-center"><%=rs19.getString("product_name")%></td>
                                            <td class="text-center"><%=rs19.getInt("quantity")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i><%=rs19.getDouble("actual_price")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i><%=rs19.getDouble("total_price")%></td>
                                            <td class="text-center"><%=rs19.getString("payment_type")%></td>
                                            <td class="text-center"><%=rs19.getString("placing_date")%></td>
                                            <td class="text-center"><%=rs19.getString("delivery_date")%></td>
                                        </tr>
                                    </table>    
                                </div>
                            <%
                            }
                        }
                    %>
                    </div>
                    <%
                        } else if(visit.equals("manage_address")){
                            
                            PreparedStatement stmt1 = myConn2.prepareStatement("select * from customers where member_id = ?");
                            stmt1.setInt(1,rs18.getInt("member_id"));
                            ResultSet rs2 = stmt1.executeQuery();
                            rs2.next();
                    %>
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h3 class="card-header">Manage Address</h3>
                        <div class="card-body">
                            <h5 class="card-title">Delivery Address:</h5>
                            <h4 class="card-text"><%=rs2.getString("delivery_address")%></h4>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body">
                            <h5 class="card-title">Billing Address:</h5>
                            <h4 class="card-text"><%=rs2.getString("billing_address")%><br></h4>
                        </div>
                    </div>
                    <%
                        } else if(visit.equals("my_products")){
                            %>
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h3 class="card-header">My Products</h3>
                        <%
                            PreparedStatement stmt100 = myConn2.prepareStatement("select * from products where seller_id = ?");
                            stmt100.setInt(1,rs.getInt("seller_id"));
                            ResultSet rs100 = stmt100.executeQuery();
                            if(!rs100.next()){
                                %>
                                    <div class="card-body">
                                        <h4 class="card-title text-center my-0">No Products Sold Yet!!</h4>    
                                    </div>
                                <%
                            }else{
                                rs100 = stmt100.executeQuery();
                                while(rs100.next()){
                                    %>
                                    <div class="card-body px-0 pb-0">
                                        <table class="table my-0">
                                            <thead class="thead-light">
                                                <tr>
                                                    <th class="text-center">Product_ID</th>
                                                    <th class="text-center">Product Name</th>
                                                    <th class="text-center">Product Price</th>
                                                    <th class="text-center">Discount</th>
                                                    <th class="text-center">Units Left <small><a href="#" data-toggle="modal" data-target="#add_quantity"><i class="fa fa-plus-square"></i></a></small></th>
                                                </tr>
                                            </thead>
                                            <tr>
                                                <td class="text-center"><%=rs100.getInt("product_id")%></td>
                                                <td class="text-center"><%=rs100.getString("product_name")%></td>
                                                <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i><%=rs100.getDouble("unit_price")%></td>
                                                <td class="text-center"><%=rs100.getFloat("discount")*100%>%</td>
                                                <td class="text-center"><%=rs100.getInt("units_left")%></td>
                                            </tr>
                                        </table>
                                    </div>  
                                    <%
                                }
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <%@include file="Components/common_modal.jsp"%>
    </body>
</html>
