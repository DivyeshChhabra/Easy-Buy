<%@page import="java.sql.*"%>

<%
    /* Verifying whether the member has logged in */
    ResultSet rs10 = (ResultSet)session.getAttribute("current-user");
    
    int alreadySeller=0;
    
    if(rs10 != null){ /* Member has logged in */
        /* Connecting MySQL DataBase with JAVA application */
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy","root","root");
        
        /* Verifying whether the member is also a seller */
        PreparedStatement stmt7 = myCon.prepareStatement("select * from sellers where member_id = ?;");
        stmt7.setInt(1, rs10.getInt("member_id"));
        ResultSet rs11 = stmt7.executeQuery();
        
        if(rs11.next()){
            /* Member is already a seller */
            alreadySeller=1;
        }else{
            /* Member is not a seller */
            alreadySeller=0;
        }
    }
%>

<link rel="stylesheet" href="CSS/style.css">
<nav class="navbar navbar-expand-lg navbar-dark custom_bg">
    <div class="container">
        <a class="navbar-brand" href="<%if(rs10==null || rs10.getString("type").equals("Normal")){%>index.jsp<%}else{%>admin.jsp<%}%>">Easy Buy</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <%
            if(rs10==null || rs10.getString("type").equals("Normal")){
                %>
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
                            <li>
                                <a class="nav-link" href="about.jsp" style="text-decoration: none;color:white;">About Us</a>
                            </li>
                          <%
                                if(rs10 == null){
                                  %>
                                        <a class="nav-link" href="login.jsp" style="text-decoration: none;color:white;">Login</a>
                                  <%
                                } else{
                                    %>
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" style="text-decoration: none;color:white;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <%=rs10.getString("first_name")%>
                                            </a>
                                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                <a class="dropdown-item" href="member.jsp">My Account</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" data-toggle="modal" data-target="#cart" href="#"><i class="fa fa-cart-plus"></i><small class="cart-items"> (0) </small> My Cart</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" <% if(alreadySeller == 0){%> href="seller-register.jsp" <% } else { %> href="sell.jsp" <% } %>>Sell On Easy Buy</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="LogoutServlet">Logout</a>
                                            </div>
                                        </li>
                                    <%
                                }
                          %>
                      </ul>
                    </div>
                <%
            } else{
                %>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" style="color:white;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ADMIN
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="LogoutServlet">Logout</a>
                            </div>
                        </li>
                    </ul>
                <%
            }
        %>
    </div>
</nav>