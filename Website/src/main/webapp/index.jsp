<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.mycompany.easybuy.model.Member" %>
<%@ page import="com.mycompany.easybuy.model.Category" %>
<%@ page import="com.mycompany.easybuy.model.Product" %>
<%@ page import="com.mycompany.easybuy.helper.Helper" %>

<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%  
    Member currentMember = (Member) session.getAttribute("current-member");
    pageContext.setAttribute("member", currentMember);
    
    List<Category> categoryList = (List<Category>) request.getAttribute("categories");
    pageContext.setAttribute("categories", (List<Category>) categoryList);
    
    List<Product> productList = (List<Product>) request.getAttribute("products");
    pageContext.setAttribute("products", (List<Product>) productList);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Easy Buy</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <link rel="stylesheet" href="CSS/style.css">
    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    
    <body style="overflow-x: hidden;">
        <%@include file="Components/navbar.jsp"%>
        <%@include file="Components/message/success.jsp" %>
        
        <div class="container-fluid px-5 py-5 index"> 
            <div class="row">
                
                <div class="col-md-2">
                    <div class="list-group">
                        <form action="index" method="GET" name="category-form">
                            <input type="hidden" name="category_id" id="category_id"/>
                            
                            <a href="#" onclick="getSelectedProducts('all')" class="list-group-item list-group-item-action active">
                                ALL CATEGORIES
                            </a>

                            <c:forEach var="category" items="${categories}">
                                <a href="#" onclick="getSelectedProducts(${category.getId()})" class="list-group-item list-group-item-action">
                                    ${category.getName()}
                                </a>
                            </c:forEach>
                        </form>
                        
                    </div>
                </div>

                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card-columns">
                                
                                <c:forEach var="product" items="${products}">
                                    <div class="card">
                                        <form action="product" method="GET" name="product-form" id="product-form">
                                            <input type="hidden" name="demand" id="demand" value="product"/>
                                            <input type="hidden" name="command" id="command" value="get"/>
                                            <input type="hidden" name="product_id" id="product_id"/>

                                            <div class="container text-center page" onclick="productPage(${product.getId()})">
                                                <img src="Image/Products/${product.getProductImage()}" class="card-img-top mt-4 text-center" alt="${product.getProductImage()}" style="max-height:270px;max-width:100%;width:auto">
                                            </div>

                                            <div class="card-body text-center py-0 mt-2 page" onclick="productPage(${product.getId()})">
                                                <h5 class="card-title">${product.getName()}</h5>
                                            </div>

                                            <p class="card-text text-justify px-4 py-2" onclick="productPage(${product.getId()})">
                                                ${Helper.getSmallContent(product.getDescription())}
                                            </p>
                                        </form>

                                        <div class="card-footer text-center" onclick="productPage(${product.getId()})">
                                            <button class="btn btn-primary text-center"><b><i class="fa fa-inr" aria-hidden="true"></i> ${Math.ceil(product.getUnitPrice() * (1 - (product.getDiscount() / 100)))}/- </b> <small>( <strike><i class="fa fa-inr" aria-hidden="true"></i> ${product.getUnitPrice()}</strike>, ${(product.getDiscount())}% off )</small> </button>
                                            
                                            <button class="btn <c:if test='${product.getReviews() == 0}'>btn-secondary</c:if>
                                                               <c:if test='${product.getReviews() > 0 && product.getRating() <= 2}'>btn-danger</c:if>
                                                               <c:if test='${product.getReviews() > 0 && product.getRating() > 2 && product.getRating() <= 3.5}'>btn-warning</c:if>
                                                               <c:if test='${product.getReviews() > 0 && product.getRating() > 3.5}'>btn-success</c:if> text-center">
                                                <b> 
                                                    ${product.getRating()} <i class="fa fa-star" aria-hidden="true"></i> 
                                                </b> 
                                                
                                                <small>(${product.getReviews()})</small> 
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="Components/common_modal.jsp"%>
        
        <script>
            function productPage(id){
                $("#product_id").val(id);
                document.forms['product-form'].submit();
            }
            
            function getSelectedProducts(id){
                $("#category_id").val(id);
                document.forms['category-form'].submit();
            }
        </script>
    </body>
</html>