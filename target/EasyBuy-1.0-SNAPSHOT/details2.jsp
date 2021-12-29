<%@page import="java.sql.*"%>

<%
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn10 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
    
    String details2 = request.getParameter("details2");
    int id;
    PreparedStatement stmt101 = null;
    ResultSet rs101 = null, rs102 = null;
    String title = null;
    
    if(details2.equals("customer")){
        id = Integer.parseInt(request.getParameter("customer"));
        stmt101 = myConn10.prepareStatement("select * from products join orders on products.product_id = orders.product_id join payments on orders.payment_id = payments.payment_id where customer_id = ?");
        stmt101.setInt(1, id);
        rs101 = stmt101.executeQuery();
        PreparedStatement stmt102 = myConn10.prepareStatement("select * from customers join members on members.member_id = customers.member_id where customer_id = ?");
        stmt102.setInt(1, id);
        rs102 = stmt102.executeQuery();
        rs102.next();
        title = rs102.getString("first_name") + " " + rs102.getString("last_name") + " | Total Orders";
    }else if(details2.equals("category")){
        id = Integer.parseInt(request.getParameter("category"));
        stmt101 = myConn10.prepareStatement("select * from products join category on category.category_id = products.category_id join sellers on sellers.seller_id = products.product_id join members on members.member_id = sellers.member_id where products.category_id = ?");
        stmt101.setInt(1, id);
        rs101 = stmt101.executeQuery();
        PreparedStatement stmt102 = myConn10.prepareStatement("select * from category where category_id = ?");
        stmt102.setInt(1, id);
        rs102 = stmt102.executeQuery();
        rs102.next();
        title = rs102.getString("category_name") + " | Products Quantity";
    }else if(details2.equals("products")){
        id = Integer.parseInt(request.getParameter("products"));
        stmt101 = myConn10.prepareStatement("select * from customers join orders on orders.customer_id = customers.customer_id join payments on orders.payment_id = payments.payment_id join members on members.member_id = customers.member_id where product_id = ?");
        stmt101.setInt(1, id);
        rs101 = stmt101.executeQuery();
        PreparedStatement stmt102 = myConn10.prepareStatement("select * from products where product_id = ?");
        stmt102.setInt(1, id);
        rs102 = stmt102.executeQuery();
        rs102.next();
        title = rs102.getString("product_name") + " | Purchase History";
    }else if(details2.equals("sellers")){
        id = Integer.parseInt(request.getParameter("sellers"));
        stmt101 = myConn10.prepareStatement("select * from products join sellers on sellers.seller_id = products.seller_id join category on category.category_id = products.category_id where products.seller_id = ?");
        stmt101.setInt(1, id);
        rs101 = stmt101.executeQuery();
        PreparedStatement stmt102 = myConn10.prepareStatement("select * from sellers join members on members.member_id = sellers.member_id where seller_id = ?");
        stmt102.setInt(1, id);
        rs102 = stmt102.executeQuery();
        rs102.next();
        title = rs102.getString("first_name") + " " + rs102.getString("last_name") + " | Total Products";
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=title%></title>
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
        <div class="container-fluid py-5 px-5">
            <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                <%
                    if(details2.equals("customer")){
                        %>
                            <h3 class="card-header text-center"><%=rs102.getString("first_name")%> <%=rs102.getString("last_name")%>'s Orders</h3>
                        <%
                        while(rs101.next()){
                        %>
                        <div class="card-body px-0 pb-0">
                            <table class="table my-0">
                                <thead class="thead-light">
                                    <tr>
                                        <th class="text-center">Order_ID</th>
                                        <th class="text-center">Product_ID</th>
                                        <th class="text-center">Product Name</th>
                                        <th class="text-center">Quantity</th>
                                        <th class="text-center">Total Price</th>
                                        <th class="text-center">Order Date</th>
                                        <th class="text-center">Delivery Date</th>
                                        <th class="text-center">Payment Method</th>
                                        <th class="text-center">Payment Date</th>
                                    </tr>
                                </thead>
                                <tr>
                                    <td class="text-center"><%=rs101.getInt("order_id")%></td>
                                    <td class="text-center"><%=rs101.getString("product_id")%></td>
                                    <td class="text-center"><%=rs101.getString("product_name")%></td>
                                    <td class="text-center"><%=rs101.getString("quantity")%></td>
                                    <td class="text-center"><%=rs101.getString("amount")%></td>
                                    <td class="text-center"><%=rs101.getString("placing_date")%></td>
                                    <td class="text-center"><%=rs101.getString("delivery_date")%></td>
                                    <td class="text-center"><%=rs101.getString("payment_type")%></td>
                                    <td class="text-center"><%=rs101.getString("payment_date")%></td>
                                </tr>
                            </table>    
                        </div>
                        <%
                        }
                    }else if(details2.equals("category")){
                        %>
                            <h3 class="card-header text-center"><%=rs102.getString("category_name")%> Products List</h3>
                        <%
                        while(rs101.next()){
                            %>
                                <div class="card-body px-0 pb-0">
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Product_ID</th>
                                                <th class="text-center">Product Name</th>
                                                <th class="text-center">Unit Price</th>
                                                <th class="text-center">Quantity</th>
                                                <th class="text-center">Discount</th>
                                                <th class="text-center">Seller</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td class="text-center"><%=rs101.getString("product_id")%></td>
                                            <td class="text-center"><%=rs101.getString("product_name")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="false"></i><%=Math.ceil(rs101.getDouble("unit_price"))%></td>
                                            <td class="text-center"><%=rs101.getString("units_left")%></td>
                                            <td class="text-center"><%=Math.floor(rs101.getFloat("discount")*100)%>%</td>
                                            <td class="text-center"><%=rs101.getString("first_name")%> <%=rs101.getString("last_name")%></td>
                                        </tr>
                                    </table>    
                                </div>
                            <%
                        }
                    }else if(details2.equals("products")){
                        %>
                            <h3 class="card-header text-center"><%=rs102.getString("product_name")%> Purchase History</h3>
                        <%
                        while(rs101.next()){
                            %>
                                <div class="card-body px-0 pb-0">
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Order_ID</th>
                                                <th class="text-center">Customer_ID</th>
                                                <th class="text-center">Member_ID</th>
                                                <th class="text-center">Customer Name</th>
                                                <th class="text-center">Quantity</th>
                                                <th class="text-center">Order Date</th>
                                                <th class="text-center">Delivery Date</th>
                                                <th class="text-center">Payment Method</th>
                                                <th class="text-center">Payment Date</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td class="text-center"><%=rs101.getString("order_id")%></td>
                                            <td class="text-center"><%=rs101.getString("customer_id")%></td>
                                            <td class="text-center"><%=rs101.getString("member_id")%></td>
                                            <td class="text-center"><%=rs101.getString("first_name")%> <%=rs101.getString("last_name")%></td>
                                            <td class="text-center"><%=rs101.getString("quantity")%></td>
                                            <td class="text-center"><%=rs101.getString("placing_date")%></td>
                                            <td class="text-center"><%=rs101.getString("delivery_date")%></td>
                                            <td class="text-center"><%=rs101.getString("payment_type")%></td>
                                            <td class="text-center"><%=rs101.getString("payment_date")%></td>
                                        </tr>
                                    </table>    
                                </div>
                            <%
                        }
                    }else if(details2.equals("sellers")){
                        %>
                            <h3 class="card-header text-center"><%=rs102.getString("first_name")%> <%=rs102.getString("last_name")%>'s Products Sold</h3>
                        <%
                        while(rs101.next()){
                            %>
                                <div class="card-body px-0 pb-0">
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Product_ID</th>
                                                <th class="text-center">Product Name</th>
                                                <th class="text-center">Unit Price</th>
                                                <th class="text-center">Units Left</th>
                                                <th class="text-center">Discount</th>
                                                <th class="text-center">Category</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td class="text-center"><%=rs101.getString("product_id")%></td>
                                            <td class="text-center"><%=rs101.getString("product_name")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="false"></i><%=Math.ceil(rs101.getDouble("unit_price"))%></td>
                                            <td class="text-center"><%=rs101.getString("units_left")%></td>
                                            <td class="text-center"><%=Math.floor(rs101.getFloat("discount")*100)%>%</td>
                                            <td class="text-center"><%=rs101.getString("category_name")%></td>
                                        </tr>
                                    </table>    
                                </div>
                            <%
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
