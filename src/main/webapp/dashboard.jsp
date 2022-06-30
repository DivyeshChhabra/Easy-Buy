<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.mycompany.easybuy.model.Member" %>
<%@ page import="com.mycompany.easybuy.model.Product" %>
<%@ page import="com.mycompany.easybuy.model.Order" %>
<%@ page import="com.mycompany.easybuy.model.Customer" %>

<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    if(currentMember == null){
        response.sendRedirect("login.jsp");
        return;
    }
    pageContext.setAttribute("member", currentMember);
    
    List<Order> myOrders = (List<Order>) request.getAttribute("my_orders");
    pageContext.setAttribute("my_orders", myOrders);
    
    Customer customer = (Customer) request.getAttribute("customer");
    pageContext.setAttribute("customer", customer);
    
    List<Product> myProducts = (List<Product>) request.getAttribute("my_products");
    pageContext.setAttribute("my_products", myProducts);
    
    String visit = (String) request.getAttribute("visit");
    pageContext.setAttribute("visit", visit);
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>My Profile</title>
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <style>
            .rating {
                float:left;
            }

            .rating:not(:checked) > input {
                position:absolute;
                top:-9999px;
                clip:rect(0,0,0,0);
            }

            .rating:not(:checked) > label {
                float:right;
                width:1em;
                padding:0 .1em;
                overflow:hidden;
                white-space:nowrap;
                cursor:pointer;
                font-size:200%;
                line-height:1.2;
                color:#ddd;
            }

            .rating:not(:checked) > label:before {
                content: '★ ';
            }
            
            .four:checked ~ label,
            .five:checked ~ label {color: green}
            
            .three:checked ~ label {color: orange}
            
            .one:checked ~ label,
            .two:checked ~ label {color: red}
            
            .rating > label:active {
                position:relative;
                top:2px;
                left:2px;
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
                    <div class="card" data-spy="affix" data-offset-top="197" style="width: 18rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); position: fixed">
                        
                        <c:if test="${member.getProfilePhoto() == null}">
                            <img class="card-img-top" src="Image/Members/user.png" style="padding:20px 20px 0px 20px;border-radius: 45px">
                        </c:if>
                            
                        <c:if test="${member.getProfilePhoto() != null}">
                            <img class="card-img-top" src="Image/Members/${member.getProfilePhoto()}" style="padding:20px 20px 0px 20px;border-radius: 45px">
                        </c:if>
                        
                        <div class="card-body text-center">
                            <h5>HELLO,</h5>
                            <h3 class="card-title my-0" style="color: #0080ff"><b>${member.getFirstName()} ${member.getLastName()}</b></h3>
                        </div>
                        
                        <div class="dropdown-divider my-0"></div>
                        
                        <div class="card-body px-0 py-0">
                            <ul class="list-group list-group-flush">
                                
                                <li class="list-group-item">
                                    <a href="dashboard.jsp" style="text-decoration: none"><i class="fa fa-user" aria-hidden="true"></i><b> PERSONAL INFORMATION</b></a>
                                </li>
                                
                                <li class="list-group-item">
                                    <b>CUSTOMER SETTINGS</b>
                                    
                                    <ul class="list-group list-group-flush">
                                        
                                        <c:if test="${member.getCustomerId() == -1}">
                                            <li class="list-group-item">
                                                <p data-toggle="tooltip" title="Buy something to become a customer">You are not a Customer</p>
                                            </li>
                                        </c:if>
                                        
                                        <c:if test="${member.getCustomerId() != -1}">
                                            <li class="list-group-item">
                                                <form action="myOrders" method="POST" name="orders-form">
                                                    <input type="hidden" name="command" value="get"/>
                                                    <a href="#" style="text-decoration: none" onclick="document.forms['orders-form'].submit()"><i class="fa fa-list" aria-hidden="true"></i><b> My Orders</b></a>
                                                </form>
                                            </li>

                                            <li class="list-group-item">
                                                <form action="manageAddress" method="POST" name="address-form">
                                                    <input type="hidden" name="demand" value="customer"/>
                                                    <input type="hidden" name="command" value="get"/>
                                                    <a href="#" style="text-decoration: none" onclick="document.forms['address-form'].submit()"><i class="fa fa-address-card" aria-hidden="true"></i><b> Manage Address</b></a>
                                                </form>
                                            </li>
                                        </c:if>
                                        
                                    </ul>
                                    
                                </li>
                                
                                <li class="list-group-item">
                                    <b>SELLER SETTINGS</b>
                                    
                                    <ul class="list-group list-group-flush">
                                       
                                        <c:if test="${member.getSellerId() == -1}">
                                            <li class="list-group-item">
                                                <p data-toggle="tooltip" title="Sell something to become a seller">You are not a Seller</p>
                                            </li>
                                        </c:if>
                                        
                                        <c:if test="${member.getSellerId() != -1}">
                                            <li class="list-group-item">
                                                <form action="myProducts" method="POST" name="products-form">
                                                    <input type="hidden" name="demand" value="product"/>
                                                    <input type="hidden" name="command" value="getSellerWise"/>
                                                    <a href="#" style="text-decoration: none" onclick="document.forms['products-form'].submit()"><i class="fa fa-tags" aria-hidden="true"></i><b> My Products</b></a>
                                                </form>
                                            </li>
                                        </c:if>
                                    </ul>
                                </li>
                                
                                <li class="list-group-item">
                                    <a href="visitAgain" style="text-decoration: none"><i class="fa fa-sign-out" aria-hidden="true"></i><b> LOGOUT</b></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-9">
                    
                    <c:if test="${visit == null}">
                        <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <h1 class="card-header text-center"><b>PERSONAL INFORMATION</b></h1>

                            <div class="card-body">
                                <h2 class="card-title"><b>Full Name:</b></h2>
                                <h3 class="card-text">${member.getFirstName()} ${member.getLastName()} <small><a href="#" style="text-decoration: none" data-toggle="modal" data-target="#edit-name-modal">Edit</a></small></h3>
                            </div>

                            <div class="dropdown-divider my-0"></div>

                            <div class="card-body">
                                <h2 class="card-title"><b>Gender:</b></h2>
                                <h3 class="card-text">${member.getGender()}<br></h3>
                            </div>

                            <div class="dropdown-divider my-0"></div>

                            <div class="card-body">
                                <h2 class="card-title"><b>Phone:</b></h2>
                                <h3 class="card-text">${member.getPhoneNumber()} <small><a href="#" style="text-decoration: none" data-toggle="modal" data-target="#edit-phone-modal">Edit</a></small></h3>
                            </div>

                            <div class="dropdown-divider my-0"></div>

                            <div class="card-body">
                                <button class="btn btn-primary mx-3" data-toggle="modal" data-target="#edit-e_mail-modal">
                                    Change E-Mail
                                </button>
                                
                                <br/> <br/>

                                <button class="btn btn-primary mx-3" data-toggle="modal" data-target="#edit-password-modal">
                                    Change Password
                                </button>
                            </div>

                            <div class="dropdown-divider my-0"></div>

                            <div class="card-body text-center">
                                <button class="btn btn-danger" data-toggle="modal" data-target="#delete-account-modal">
                                    Delete Account
                                </button>
                            </div>
                        </div>
                    </c:if>
                    
                    <c:if test="${visit.equals('my_orders')}">
                        <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <h1 class="card-header text-center"><b>MY ORDERS</b></h1>
                            
                            <div class="card-body px-0">
                                <table class="table my-0">
                                    <thead class="thead-light">
                                        <tr>
                                            <th class="text-center">Order ID</th>
                                            <th class="text-center">Product Name</th>
                                            <th class="text-center">Quantity</th>
                                            <th class="text-center">Order Total</th>
                                            <th class="text-center">Payment Method</th>
                                            <th class="text-center">Order Date and Time</th>
                                            <th class="text-center">Delivery Date</th>
                                        </tr>
                                    </thead>

                                    <c:forEach var="order" items="${my_orders}">
                                        <tr>
                                            <td class="text-center">${order.getId()}</td>
                                            <td class="text-center">${order.getProductName()}</td>
                                            <td class="text-center">${order.getQuantity()}</td>
                                            <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${order.getTotalPrice()}</td>
                                            <td class="text-center">${order.getPaymentType()}</td>
                                            <td class="text-center">${order.getOrderPlacedDate()}</td>
                                            <td class="text-center">
                                                <c:if test="${order.getOrderDeliveredDate().equals('Delivered')}">
                                                    ${order.getOrderDeliveredDate()}
                                                    <br/>
                                                    
                                                    <c:if test="${order.getRating() == 0}">
                                                        <a href="#" style="text-decoration: none" data-toggle="modal" data-target="#rating-modal"> 
                                                            <i class="fa fa-star" aria-hidden="true"></i> Rate Product
                                                        </a>
                                                    </c:if>
                                                    
                                                    <c:if test="${order.getRating() != null}">
                                                        <c:if test="${order.getRating() == 1}">
                                                            <i class="fa fa-star" aria-hidden="true" style="color:red"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                        </c:if>
                                                            
                                                        <c:if test="${order.getRating() == 2}">
                                                            <i class="fa fa-star" aria-hidden="true" style="color:red"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:red"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                        </c:if>
                                                        
                                                        <c:if test="${order.getRating() == 3}">
                                                            <i class="fa fa-star" aria-hidden="true" style="color:orange"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:orange"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:orange"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                        </c:if>
                                                        
                                                        <c:if test="${order.getRating() == 4}">
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:#ddd"></i>
                                                        </c:if>
                                                        
                                                        <c:if test="${order.getRating() == 5}">
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                            <i class="fa fa-star" aria-hidden="true" style="color:green"></i>
                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                                    
                                                <c:if test="${!order.getOrderDeliveredDate().equals('Delivered')}">
                                                    Expected ${order.getOrderDeliveredDate()}
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                            
                    <c:if test="${visit.equals('manage_address')}">
                        <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <h1 class="card-header text-center"><b>MANAGE ADDRESS</b></h1>
                            
                            <div class="card-body">
                                <h2 class="card-title"><b>Delivery Address:</b></h2>
                                <h3 class="card-text">${customer.getDeliveryAddress()}</h3>
                            </div>

                            <div class="dropdown-divider my-0"></div>

                            <div class="card-body">
                                <h2 class="card-title"><b>Billing Address:</b></h2>
                                <h3 class="card-text">${customer.getBillingAddress()}<br></h3>
                            </div>
                        </div>
                    </c:if>
                                
                    <c:if test="${visit.equals('my_products')}">
                        <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <h1 class="card-header text-center"><b>MY PRODUCTS</b></h1>
                            
                            <c:if test="${my_products.size() == 0}">
                                <div class="card-body">
                                    <h4 class="card-title text-center my-0">No Products Sold Yet!!</h4>    
                                </div>
                            </c:if>
                            
                            <c:if test="${my_products.size() > 0}">
                                <div class="card-body px-0 pb-0">
                                    <table class="table my-0">
                                        <thead class="thead-light">
                                            <tr>
                                                <th class="text-center">Product ID</th>
                                                <th class="text-center" colspan="2">Product Name</th>
                                                <th class="text-center">Inventory</th>
                                                <th class="text-center">Maximum Retailed Price</th>
                                                <th class="text-center">Discount</th>
                                                <th class="text-center">Rating</th>
                                            </tr>
                                        </thead>
                                        
                                        <c:forEach var="product" items="${my_products}">
                                            <tr>
                                                <td class="text-center" style="text-align: center; vertical-align: middle">${product.getId()}</td>
                                                <td class="text-center" style="text-align: center; vertical-align: middle"><img src="Image/Products/${product.getProductImage()}" alt="${product.getName()}" style="height:75px; width: 75px;"/> </td> 
                                                <td class="text-center" style="text-align: center; vertical-align: middle"><b> ${product.getName()} </b></td> 
                                                <td class="text-center" style="text-align: center; vertical-align: middle">${product.getInventory()} <small><a href="#" data-toggle="modal" data-target="#add_quantity"><i class="fa fa-plus-square"></i></a></small></td>
                                                <td class="text-center" style="text-align: center; vertical-align: middle"><i class="fa fa-inr" aria-hidden="true"></i>${product.getUnitPrice()}</td>
                                                <td class="text-center" style="text-align: center; vertical-align: middle">${product.getDiscount()}% <small><a href="#" data-toggle="modal" data-target="#add_discount">Add</a></small></td>
                                                <td class="text-center" style="text-align: center; vertical-align: middle">${product.getRating()} (${product.getReviews()})</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div> 
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        
        <%@include file="Components/common_modal.jsp"%>
        
        <script>
            $(document).ready(function(){
                $('[data-toggle="tooltip"]').tooltip();   
            });
        </script>
    </body>
</html>
