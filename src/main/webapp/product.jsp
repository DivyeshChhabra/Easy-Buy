<%@page import="java.sql.*"%>
<%@page import="java.lang.*"%>

<%
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myCon1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
    
    int product_id = Integer.parseInt(request.getParameter("product_id"));
    
    PreparedStatement stmt31 = myCon1.prepareStatement("select * from products join category on products.category_id = category.category_id join sellers on products.seller_id = sellers.seller_id join members on members.member_id = sellers.member_id where product_id = ?");
    stmt31.setInt(1, product_id);
    ResultSet rs31 = stmt31.executeQuery();
    rs31.next();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <title><%=rs31.getString("product_name")%></title>
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body>
        <%@include file="Components/navbar.jsp"%>
        <div class="container-fluid px-5 py-5">
            <div class="row">
                <div class="col-md-3">
                    <div class="card product" style="width: 18rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <img class="card-img-top" src="Image/Products/<%=rs31.getString("product_image")%>" alt="Card image cap" style="padding:20px 20px 0px 20px;">
                        <div class="card-body text-center">
                            <h3 class="card-title my-0" style="color: #0080ff"><%=rs31.getString("product_name")%></h3>
                        </div>
                        <div class="text-center message text-danger">
                            <%
                                int flag=1;
                                if(rs31.getInt("units_left")<=10){
                                    %>
                                    <h6>
                                    <%
                                    if(rs31.getInt("units_left")==1){
                                        %>
                                            HURRY UP!! Only 1 unit left!!
                                        <%
                                    } else if(rs31.getInt("units_left")==0){
                                        flag=0;
                                        %>
                                            Out of Stock
                                        <%
                                    }else{
                                        %>
                                            HURRY UP!! Only few units left!!
                                        <%
                                    }
                                    %>
                                    </h6>
                                    <%
                                }
                            %>
                        </div>
                        <form action="checkout.jsp" method="post" name="checkout-form" id="checkout-form">
                            <input type="hidden" name="checkout" id="input" value="<%=rs31.getInt("product_id")%>">
                            <button type="button" class="btn btn-primary mx-3" style="color:white;background-color:#ff4d01;font-weight: bold;width:254px" <%if(flag==0){%>disabled<%}%> onclick="document.forms['checkout-form'].submit()"><i class="fa fa-bolt" aria-hidden="true"></i> Order Now</button>
                        </form>
                        <button class="btn custom_bg text-white my-3 mx-3" style="color:white;background-color:#ff4d01;font-weight: bold" onclick="addToCart(<%=rs31.getInt("product_id")%>,'<%=rs31.getString("product_name")%>',<%=rs31.getDouble("unit_price")%>,<%=rs31.getDouble("discount")%>,'<%=rs31.getString("category_name")%>')" <%if(flag==0){%>disabled<%}%>><i class="fa fa-cart-plus"></i> Add to Cart</button>
                        <p class="card-text" style="font-size:12.5px">
                            <small>
                                Delivery is scheduled one week after the order has been placed.
                            </small>
                        </p>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                        <h3 class="card-header"><%=rs31.getString("product_name")%></h3>
                        <div class="card-body">
                            <h5 class="card-title">M.R.P.: <i class="fa fa-inr" aria-hidden="true"></i><%=rs31.getDouble("unit_price")%></h5>
                            <h5 class="card-title">Deal of the day: <i class="fa fa-inr" aria-hidden="true"></i><%=Math.ceil(rs31.getDouble("unit_price")*(1-rs31.getDouble("discount")))%></h5>
                            <h5 class="card-title">You Saved: <i class="fa fa-inr" aria-hidden="true"></i><%=Math.floor(rs31.getDouble("unit_price")*(rs31.getDouble("discount")))%><small><%=" ("%><%=Math.floor(rs31.getDouble("discount")*100)%>%)</small></h5>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body">
                            <h5 class="card-title">Description: </h5>
                            <h6 class="text-justify mb-4"><%=rs31.getString("product_description")%></h6>
                            <%
                                
                                if(rs31.getString("color")!=null){
                            %>
                            <h5 class="card-title">Color: <%out.println(rs31.getString("color"));%></h5>
                            <%    
                                } if(rs31.getString("weight_inGrams")!=null){
                            %>
                                <h5 class="card-title">Weight: <%out.println(rs31.getString("weight_inGrams"));%> grams</h5>
                            <%
                                }
                            %>
                        </div>
                        <div class="dropdown-divider my-0"></div>
                        <div class="card-body">
                            <h5 class="card-title">Seller: </h5>
                            <h4 class="text-justify"><%=rs31.getString("first_name")%><%=" "%><%=rs31.getString("last_name")%></h4>
                            <h5 class="text-justify"><%=rs31.getString("company_name")%></h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="Components/common_modal.jsp"%>
    </body>
</html>
