<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.mycompany.easybuy.model.Member" %>
<%@ page import="com.mycompany.easybuy.model.Category" %>
<%@ page import="com.mycompany.easybuy.model.Product" %>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    
    if(currentMember == null){ /* Member has not logged in */
        session.setAttribute("error", "Login Before Shopping");
        response.sendRedirect("login.jsp");
        
        return;
    }
    
    if(currentMember.getType().equals("Admin")){ /* Admin has logged in */
        session.setAttribute("error", "Admin can't Shop");
        response.sendRedirect("admin.jsp");
        
        return;
    }
    
    pageContext.setAttribute("member", currentMember);
    
    Product product = (Product) request.getAttribute("product");
    pageContext.setAttribute("product", product);
    
    Category category = (Category) request.getAttribute("category");
    pageContext.setAttribute("category", category);
    
    int quantity = (Integer) request.getAttribute("quantity");
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>CheckOut</title>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <style>
            .place-order-btn:hover{
                background-color: green;
            }
        </style>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body style="overflow-x: hidden;">
        <%@include file="Components/navbar.jsp"%>
        
        <div class="container-fluid px-5 py-5">
            <form action="completePayment" method="POST" autocomplete="off" name="checkout-form">
                <div class="col-md-11 mx-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center"><b>ORDER SUMMARY</b></h1>
                        
                        <div class="my-3">
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
                                        <td class="text-center">${product.getName()}</td>
                                        <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount()) / 100)) * quantity}</td>
                                        <td class="text-center"><%= quantity %></td>
                                        <td class="text-center">${category.getName()}</td>
                                        <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount()) / 100)) * quantity}</td>
                                    </tr>

                                    <tr><td colspan='7' class="text-right"><b>Delivery Charges: <span style="color:#0f0;">FREE</span></b></td></tr>

                                    <tr><td colspan='7' class="text-right"><b>Total Price: <i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount()) / 100)) * quantity}<small><i> (includes all taxes)</i></small></b></td></tr>
                                </table>
                            </div>

                            <input type="hidden" name="operation" value="delQuantity">
                            <input type="hidden" name="product_id" value="${product.getId()}">
                            <input type="hidden" name="product_qty" value="<%= quantity %>">
                            <input type="hidden" name="total_price" value="${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount()) / 100)) * quantity}">
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6 offset-md-3 my-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center"><b>MANAGE ADDRESS</b></h1>
                        
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
            
                <div class="col-md-6 offset-md-3 mt-5">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center"><b>PAYMENT OPTIONS</b></h1>

                        <div class="card-body">
                            <div class="form-group">
                                <label><b>SELECT A PAYMENT OPTION:</b></label>

                                <div>
                                    <label>
                                        <input type="radio" name="payment_type" value="Pay on Delivery"/> Pay on Delivery
                                    </label><br>

                                    <label>
                                        <input type="radio" name="payment_type" value="Other Payment Options"/> Other Payment Options
                                    </label><br>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%
                        Random random = new Random();
                        int deliveredDay = random.nextInt(7)+1;

                        SimpleDateFormat orderPlacedDF = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                        SimpleDateFormat orderDeliveredDF = new SimpleDateFormat("E, dd MMM yyyy");

                        long time = System.currentTimeMillis();
                        java.sql.Date date = new java.sql.Date(time);

                        Calendar orderPlacedDate = Calendar.getInstance();
                        Calendar orderDeliveredDate = Calendar.getInstance();

                        orderDeliveredDate.setTime(date);
                        orderDeliveredDate.add(Calendar.DATE, deliveredDay);
                        
                        String orderPlaced = orderPlacedDF.format(orderPlacedDate.getTime());
                        String orderDelivered =  orderDeliveredDF.format(orderDeliveredDate.getTimeInMillis());

                    %>

                    <input type="hidden" name="order_placed" value="<%= orderPlaced %>">
                    <input type="hidden" name="order_delivered" value="<%= orderDelivered %>">

                    <input class="btn form-control my-3 place-order-btn" 
                           value="Place Orders" 
                           style="color:white;background-color:#ff4d01;font-weight: bold"
                           onclick="placeOrder()">

                </div>
            </form>
                    
            <form action="index" method="POST">
                <input type="submit" class="btn btn-primary form-control mt-5" value="Continue Shopping" style="color:white;font-weight:bold">
            </form>
        </div>
        
        <%@include file="Components/common_modal.jsp"%>
        
        <script>
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();   
            });
            
            function placeOrder(){
                let billing_address = $('#billing_address').val();
                let delivery_address = $('#delivery_address').val();
                
                if(billing_address.length > 0 && delivery_address.length > 0){
                    let options = document.querySelectorAll('input[name="payment_type"]');
                    let payment_type;
                    
                    for(const option of options){
                        if(option.checked){
                            payment_type = option.value;
                            break;
                        }
                    }
                    
                    if(payment_type === 'Pay on Delivery') document.forms['checkout-form'].submit();
                    else if(payment_type === 'Other Payment Options'){
                        paymentStart(${product.getId()}, '${product.getName()}', <%=quantity%>, ${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount()) / 100)) * quantity}, '${member.getFirstName()} ${member.getLastName()}', '${member.getPhoneNumber()}', '${member.getEMailAddress()}', billing_address, delivery_address, '<%=orderPlaced%>', '<%=orderDelivered%>', 'Online Payment');
                    }
                }
            }
                
        </script>
        
        <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    </body>
</html>
