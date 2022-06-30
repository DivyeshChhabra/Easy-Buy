<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.sql.*" %>

<link rel="stylesheet" href="CSS/style.css">

<nav class="navbar navbar-expand-lg navbar-dark custom_bg">
    
    <div class="container">
        <form method="POST" id="main-form">
            <b> <a class="navbar-brand" href="#" onclick="moveToMain('${member.getType()}')">Easy Buy</a> </b>
            
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </form>
        
        <c:if test="${member == null || member.getType().equals('Normal')}">
            <form class="form-inline">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="basic-addon1" style="width:400px;">

                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1"><i class="fa fa-search" aria-hidden="true"></i></span>
                        <span class="input-group-text" id="basic-addon1"><i class="fa fa-microphone" aria-hidden="true"></i></span>
                    </div>
                </div>
            </form>
            
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <c:if test="${member == null}">
                        <a class="nav-link mr-3" href="login.jsp" style="text-decoration: none;color:white;">
                            <b>Login</b>
                        </a>
                    </c:if>
                        
                    <c:if test="${member != null}">
                        <li class="nav-item dropdown mr-3">
                            <a class="nav-link dropdown-toggle" style="text-decoration: none;color:white;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <b> ${member.getFirstName()} ${member.getLastName()} </b>
                            </a>
                            
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                                <a class="dropdown-item" href="dashboard.jsp">My Account</a>
                                <div class="dropdown-divider"></div>

                                <a class="dropdown-item" data-toggle="modal" data-target="#cart" href="#"><i class="fa fa-cart-plus"></i><small class="cart-items"> (0) </small> My Cart</a>
                                <div class="dropdown-divider"></div>

                                <a class="dropdown-item" <c:if test="${member.getSellerId() == -1}"> href="seller-register.jsp" </c:if> <c:if test="${member.getSellerId() != -1}"> href="sell.jsp" </c:if>>Sell On Easy Buy</a>
                                <div class="dropdown-divider"></div>

                                <a class="dropdown-item" href="visitAgain">Logout</a>
                            </div>
                        </li>
                    </c:if>
                        
                    <li>
                        <b> <a class="nav-link" href="about.jsp" style="text-decoration: none;color:white;">About Us</a> </b>
                    </li>
                </ul>
            </div>
        </c:if>
        
        <c:if test="${member != null && member.getType().equals('Admin')}">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" style="color:white;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <b> ADMIN </b>
                        </a>
                        
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="visitAgain">Logout</a>
                        </div>
                    </li>
                </ul>
            </div>
        </c:if>
                    
    </div>
</nav>
                    
<script>
    function moveToMain(type){
        
        var form = document.getElementById("main-form");
        
        if(type.length === 0 || type === "Normal") form.setAttribute("action","index");
        else form.setAttribute("action","admin");
        
        form.submit();
    }
</script>