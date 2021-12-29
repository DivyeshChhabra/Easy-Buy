<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="javax.servlet.http.HttpSession" %>

<%
    /* Creating Sessions */
    HttpSession httpsession = request.getSession();
    
    /* Verifying whether the member has logged in */
    ResultSet rs9 = (ResultSet)session.getAttribute("current-user");
    String type="";
    
    if(rs9!=null){ /* Member has logged in */
        /* Getting the member type */
        type = rs9.getString("type");
    }
    
    if(rs9 == null || type.equals("Admin")){ /* Member has not logged in or is a normal member */
        /* Alerting member that only admin member can access admin page */
        session.setAttribute("error", "You need to login before shopping!!");
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
        <title>CheckOut</title>
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
            .place-order-btn:hover{
                background-color: green;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body style="overflow-x: hidden;">
        <div class="container-fluid px-5 py-5">
            <form autocomplete="off" action="PaymentServlet" method="post">
                <div class="col-md-11 mx-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center">Order Summary</h1>
                        <div class="my-3">
                            <%
                                int quantity = 1;
                                if(request.getParameter("qty")!=null){
                                    quantity = Integer.parseInt(request.getParameter("qty"));
                                }
                                
                                int product_id = Integer.parseInt(request.getParameter("checkout"));

                                PreparedStatement stmt35 = myConn3.prepareStatement("select * from products join category on products.category_id = category.category_id where products.product_id = ?");
                                stmt35.setInt(1, product_id);
                                ResultSet rs35 = stmt35.executeQuery();
                                rs35.next();
                            %>
                                <div>
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Product Name</th>
                                                <th class="text-center">Unit Price</th>
                                                <th class="text-center">Quantity</th>
                                                <th class="text-center">Category</th>
                                                <th class="text-center">Total Price</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td class="text-center"><%=rs35.getString("product_name")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i><%=Math.ceil(rs35.getInt("unit_price")*(1-rs35.getDouble("discount")))%></td>
                                            <td class="text-center"><%=quantity%></td>
                                            <td class="text-center"><%=rs35.getString("category_name")%></td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i><%=Math.ceil(rs35.getInt("unit_price")*quantity*(1-rs35.getDouble("discount")))%></td>
                                        </tr>
                                        <tr><td colspan='7' class="text-right"><b>Delivery Charges: <span style="color:#0f0;">FREE</span></b></td></tr>
                                        <tr><td colspan='7' class="text-right"><b>Total Price: <i class="fa fa-inr" aria-hidden="true"></i><%=Math.ceil(rs35.getInt("unit_price")*quantity*(1-rs35.getDouble("discount")))%> <small><i>(includes all taxes)</i></small></b></td></tr>
                                    </table>
                                </div>
                                <input name="product" type="hidden" value="<%=rs35.getInt("product_id")%>">
                                <input name="productQty" type="hidden" value="<%=quantity%>">
                                <input name="totalPrice" type="hidden" value="<%=Math.ceil(rs35.getInt("unit_price")*quantity*(1-rs35.getDouble("discount")))%>">
                        </div>
                    </div>
                </div>
                <div class="col-md-6 offset-md-3 my-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center">Manage Address</h1>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="billing_address"><b>BILLING ADDRESS</b></label>
                                <textarea class="form-control" id="billing_address" placeholder="BILLING ADDRESS" name="billing_address" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="delivery_address"><b>DELIVERY ADDRESS</b></label>
                                <textarea class="form-control" id="delivery_address" placeholder="DELIVERY ADDRESS" name="delivery_address" required></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 offset-md-4 mt-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center">Payment Method</h1>
                        <div class="card-body">
                            <div class="form-group">
                                <label><b>Select Payment Options:</b></label>
                                <div>
                                    <label data-toggle="tooltip" title="Disabled" data-placement="right">
                                        <input type="checkbox" name="payment_type" value="Credit Card/Debit Card/Other ATM Card" disabled> Credit Card/Debit Card/Other ATM Card
                                    </label><br>
                                    <label>
                                        <input type="checkbox" name="payment_type" value="Pay on Delivery" required> Pay on Delivery 
                                    </label><br>
                                    <label data-toggle="tooltip" title="Disabled" data-placement="right">
                                        <input type="checkbox" name="payment_type" value="EMI" disabled> EMI 
                                    </label><br>
                                    <label data-toggle="tooltip" title="Disabled" data-placement="right">
                                        <input type="checkbox" name="payment_type" value="Wallet" disabled> Wallet
                                    </label><br>
                                    <label data-toggle="tooltip" title="Disabled" data-placement="right">
                                        <input type="checkbox" name="payment_type" value="Net Banking" disabled> Net Banking 
                                    </label><br>
                                    <label data-toggle="tooltip" title="Disabled" data-placement="right">
                                        <input type="checkbox" name="payment_type" value="Other UPI Methods" disabled> Other UPI Methods
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        int week1 = 3;
                        int week2 = 7;
                        
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        
                        long time = System.currentTimeMillis();
                        java.sql.Date date=new java.sql.Date(time);
                        
                        Calendar calobj = Calendar.getInstance();
                        Calendar calobj1 = Calendar.getInstance();
                        Calendar calobj2 = Calendar.getInstance();
                        
                        calobj1.setTime(date);
                        calobj1.add(Calendar.DATE, week1);
                                
                        calobj2.setTime(date);
                        calobj2.add(Calendar.DATE, week2);
                        
                    %>
                    <input type="hidden" name="orderPlaced" value="<%=df.format(calobj.getTime())%>">
                    <input type="hidden" name="orderShipped" value="<%=df.format(calobj1.getTimeInMillis())%>">
                    <input type="hidden" name="orderDelivered" value="<%=df.format(calobj2.getTimeInMillis())%>">
                    <input type="submit" class="btn btn-primary form-control mt-3 place-order-btn" value="Place Order" style="color:white;background-color:#ff4d01;font-weight: bold" onclick="deleteItem(<%=Integer.parseInt(request.getParameter("checkout"))%>)">
                    <a href="index.jsp" class="btn btn-primary form-control mt-3" style="color:white;background-color:#ff4d01;font-weight: bold">Continue Shopping</a>
                </div>
            </form>
        </div>
        <%@include file="Components/common_modal.jsp"%>
        <script>
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();   
            });
        </script>
    </body>
</html>
