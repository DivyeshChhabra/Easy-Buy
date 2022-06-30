<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.mycompany.easybuy.model.Member" %>
<%@ page import="com.mycompany.easybuy.model.Product" %>

<%@ page import="java.sql.*" %>
<%@ page import="java.lang.*" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Member currentMember = (Member) session.getAttribute("current-member");
    pageContext.setAttribute("member", currentMember);
    
    Product product = (Product) request.getAttribute("product");
    pageContext.setAttribute("product", product);
%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" href="CSS/style.css">
        
        <title>${product.getName()}</title>
        
        <style>
            .body{
                overflow-x: hidden;
            }
            
            .side {
                float: left;
                width: 15%;
                margin-top:10px;
            }

            .middle {
                margin-top:10px;
                float: left;
                width: 70%;
            }

            .right {
                text-align: right;
            }
            
            .row:after {
              content: "";
              display: table;
              clear: both;
            }

            .bar-container {
              background-color: #f1f1f1;
              text-align: center;
              color: white;
            }

            .bar-5 {width: 0%; height: 18px; background-color: #04AA6D;}
            .bar-4 {width: 0%; height: 18px; background-color: #2196F3;}
            .bar-3 {width: 0%; height: 18px; background-color: #00bcd4;}
            .bar-2 {width: 0%; height: 18px; background-color: #ff9800;}
            .bar-1 {width: 0%; height: 18px; background-color: #f44336;}
            
            @media (max-width: 100%) {
                .side {
                  width: 100%;
                }
                
                .right {
                  display: none;
                }
            }
        </style>
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body>
        <%@include file="Components/navbar.jsp"%>
        
        <div class="container-fluid p-5">
            <div class="row">
                <div class="col-md-3">
                    <div class="card product" style="width: 18rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); position: fixed">
                        
                        <div class="card-header">
                            <img class="card-img-top p-4" src="Image/Products/${product.getProductImage()}" alt="Card image cap">
                        
                            <div class="text-center">
                                <h3 class="card-title" style="color: #0080ff"><b>${product.getName()}</b></h3>
                            </div>
                        </div>
                        
                        <div class="card-body">
                            <div class="text-center message text-danger">
                                <c:if test="${product.getInventory() <= 10}">
                                    <h6>
                                        <c:if test="${product.getInventory() == 1}">
                                            HURRY UP!! Only 1 unit left!!
                                        </c:if>
                                        <c:if test="${product.getInventory() == 0}">
                                            Out of Stock
                                        </c:if>
                                        <c:if test="${product.getInventory() > 1}">
                                           HURRY UP!! Only few units left!!
                                        </c:if>
                                    </h6>
                                </c:if>
                            </div>
                        
                            <form action="checkout" method="GET" name="checkout-form" id="checkout-form">
                                <input type="hidden" name="product_id" value="${product.getId()}">
                                <input type="hidden" name="quantity" value="1">

                                <button data-toggle="toottip" title="Delivery is scheduled one week after the order has been placed" class="btn btn-primary mb-2" style="color:white;background-color:#ff4d01;font-weight: bold;width: 100%" <c:if test="${products.getInventory() == 0}"> disabled </c:if> onclick="document.forms['checkout-form'].submit()"><i class="fa fa-bolt" aria-hidden="true"></i> Order Now</button>
                            </form>
                        
                            <button class="btn custom_bg text-white mt-2" style="color:white;background-color:#ff4d01;font-weight: bold;width: 100%" onclick="addToCart(${product.getId()},'${product.getName()}',${product.getUnitPrice()},${product.getDiscount()},'${product.getCategoryName()}')" <c:if test="${products.getInventory() == 0}"> disabled </c:if>><i class="fa fa-cart-plus"></i> Add to Cart</button>
                        </div>
                        
                    
                        <div class="card-footer">
                            <p class="m-0" style="text-align: center; vertical-align: middle">
                                Delivery is scheduled one week after the order has been placed
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-9">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h1 class="card-header text-center"><b>${product.getName()}</b></h1>
                        
                        <div class="card-body">
                            <h2 class="card-title">
                                <b>
                                    <span class="<c:if test="${product.getReviews() == 0}">text-secondary</c:if> 
                                                 <c:if test="${product.getReviews() > 0 && product.getRating() <= 2}">text-danger</c:if> 
                                                 <c:if test="${product.getReviews() > 0 && product.getRating() > 2 && product.getRating() <= 3.5}">text-warning</c:if> 
                                                 <c:if test="${product.getReviews() > 0 && product.getRating() > 3.5}">text-success</c:if> text-center mb-1">
                                        ${product.getRating()} <i class="fa fa-star" aria-hidden="true"></i>
                                    </span>
                                </b>
                            </h2>      
                    
                            <h4>${product.getReviews()} Rating(s)</h4>
                            
                            <hr style="border:3px solid #f1f1f1">
                                
                            <div class="side">
                                <div>5 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-5" style="width: ${((product.getRatingDistribution()[4] * 100) / product.getReviews())}%"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>${product.getRatingDistribution()[4]}</div>
                            </div>

                            <div class="side">
                                <div>4 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-4" style="width: ${((product.getRatingDistribution()[3] * 100) / product.getReviews())}%"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>${product.getRatingDistribution()[3]}</div>
                            </div>

                            <div class="side">
                                <div>3 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-3" style="width: ${((product.getRatingDistribution()[2] * 100) / product.getReviews())}%"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>${product.getRatingDistribution()[2]}</div>
                            </div>

                            <div class="side">
                                <div>2 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-2" style="width: ${((product.getRatingDistribution()[1] * 100) / product.getReviews())}%"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>${product.getRatingDistribution()[1]}</div>
                            </div>

                            <div class="side">
                                <div>1 star</div>
                            </div>
                            <div class="middle">
                                <div class="bar-container">
                                    <div class="bar-1" style="width: ${((product.getRatingDistribution()[0] * 100) / product.getReviews())}%"></div>
                                </div>
                            </div>
                            <div class="side right">
                                <div>${product.getRatingDistribution()[0]}</div>
                            </div>
                        </div>
                        
                        <div class="dropdown-divider my-0"></div>
                        
                        <div class="card-body">
                            <h3 class="card-title"><b>M.R.P. - </b> <span class="text-danger"> <i class="fa fa-inr" aria-hidden="true"></i> <s>${product.getUnitPrice()}</s> </span> </h3>
                            
                            <br/>
                            
                            <h3 class="card-title"><b>Deal of the day - </b> <span class="text-primary"> <i class="fa fa-inr" aria-hidden="true"></i> ${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount() / 100)))} </span> </h3>
                            
                            <br/>
                            
                            <h3 class="card-title""><b>You Saved - </b> <span class="text-success"> <i class="fa fa-inr" aria-hidden="true"></i> ${Math.floor(product.getUnitPrice() * (product.getDiscount() / 100))}<small> (${(product.getDiscount())}%)</small> </span> </h3>
                        </div>
                        
                        <div class="dropdown-divider my-0"></div>
                        
                        <div class="card-body">
                            <h2 class="card-title"><b>Description: </b></h2>
                            <h4 class="text-justify text-primary mb-4">${product.getDescription()}</h4>
                        </div>
                        
                        <div class="dropdown-divider my-0"></div>
                        
                        <div class="card-body">
                            <h2 class="card-title"><b>Seller: </b></h2>
                            <h3 class="text-justify">${product.getSellerName()}</h3>
                        </div>
                    </div>
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
