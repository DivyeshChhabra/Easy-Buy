<%@page import="java.lang.*"%>
<%@page import="com.mycompany.easybuy.helper.Helper"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myCon1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
    
    /* Fetching the details of all categories */
    PreparedStatement stmt5 = myCon1.prepareStatement("select * from category");
    ResultSet rs6 = stmt5.executeQuery();
    
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<Integer> categoryID = new ArrayList<>(); 
    
    while(rs6.next()){
        /* Storing the category name and Id in arraylists */
        categoryList.add(rs6.getString("category_name"));
        categoryID.add(rs6.getInt("category_id"));
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Easy Buy</title>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <%@include file="Components/common_css_js.jsp"%>
    </head>
    <body style="overflow-x: hidden;">
        <%@include file="Components/navbar.jsp"%>
        <%@include file="Components/message/success.jsp"%>
        <div class="container-fluid px-5 py-5 index"> 
            <div class="row">
                <%
                    /* Getting the category id */
                    String category = request.getParameter("category");
                    
                    ArrayList<Integer> productID = new ArrayList<>();
                    ArrayList<String> productName = new ArrayList<>();
                    ArrayList<String> productDescription = new ArrayList<>();
                    ArrayList<Double> productPrice = new ArrayList<>();
                    ArrayList<Double> productDiscount = new ArrayList<>();
                    ArrayList<String> productPhoto = new ArrayList<>();
                    ArrayList<String> productCategory = new ArrayList<>();
                    ArrayList<Double> categorySale = new ArrayList<>();
                    
                    PreparedStatement stmt6 = null;
                    ResultSet rs7 = null;
                    
                    if(category==null||category.equals("all")){
                        /* Fetching the details of all products */
                        stmt6 = myCon1.prepareStatement("select * from products join category on category.category_id = products.category_id");
                        rs7 = stmt6.executeQuery();
                        
                        while(rs7.next()){
                            /* Storing the details of products in arraylists */
                            productID.add(rs7.getInt("product_id"));
                            productName.add(rs7.getString("product_name"));
                            productDescription.add(rs7.getString("description"));
                            productPrice.add(rs7.getDouble("unit_price"));
                            productDiscount.add(rs7.getDouble("discount"));
                            productPhoto.add(rs7.getString("product_image"));
                            productCategory.add(rs7.getString("category_name"));
                        }
                    }else{
                        /* Fetching the details of all products */
                        stmt6 = myCon1.prepareStatement("select * from products join category on category.category_id = products.category_id where category.category_id=?;");
                        stmt6.setString(1, category);
                        rs7 = stmt6.executeQuery();
                        
                        while(rs7.next()){
                            /* Storing the details of products in arraylists */
                            productID.add(rs7.getInt("product_id"));
                            productName.add(rs7.getString("product_name"));
                            productDescription.add(rs7.getString("description"));
                            productPrice.add(rs7.getDouble("unit_price"));
                            productDiscount.add(rs7.getDouble("discount"));
                            productPhoto.add(rs7.getString("product_image"));
                            productCategory.add(rs7.getString("category_name"));
                        }
                    }
                %>
                <div class="col-md-2">
                    <div class="list-group">
                        <a href="index.jsp?category=all" class="list-group-item list-group-item-action active">
                          ALL CATEGORIES
                        </a>
                        <%
                            for(int i=0;i<categoryList.size();i++){
                                %><a href="index.jsp?category=<%=categoryID.get(i)%>" class="list-group-item list-group-item-action"><%=categoryList.get(i)%></a><%
                            }
                        %>
                    </div>
                </div>

                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card-columns">
                                <%
                                    for(int i=0;i<productID.size();i++){
                                        %>
                                            <div class="card">
                                                <form action="product.jsp" name="product-form" id="product-form">
                                                    <input type="hidden" name="product_id" id="input">
                                                    <div class="container text-center page" onclick="productPage(<%=productID.get(i)%>)">
                                                        <img src="Image/Products/<%= productPhoto.get(i)%>" class="card-img-top mt-4 text-center" alt="<%= productPhoto.get(i)%>" style="max-height:270px;max-width:100%;width:auto">
                                                    </div>
                                                    <div class="card-body text-center py-0 mt-2 page" onclick="productPage(<%=productID.get(i)%>)">
                                                        <h5 class="card-title"><%=productName.get(i)%></h5>
                                                    </div>
                                                    <p class="card-text text-justify px-4" onclick="productPage(<%=productID.get(i)%>)">
                                                        <%=Helper.getSmallContent(productDescription.get(i))%>
                                                    </p>
                                                </form>
                                                <div class="card-footer" onclick="productPage(<%=productID.get(i)%>)">
                                                    <button class="btn btn-outline-primary text-right" onclick="productPage(<%=productID.get(i)%>)"><i class="fa fa-inr" aria-hidden="true"></i><%=Math.ceil(productPrice.get(i)*(1-productDiscount.get(i)))%>/-<%=" "%><span class="text-secondary discount_label"><%=productPrice.get(i)%>, <%=Math.floor(productDiscount.get(i)*100)%>% off</span></button>
                                                </div>
                                            </div>
                                        <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="Components/common_modal.jsp"%>
        <script>
            function productPage(id){
                $("#input").val(id);
                document.forms['product-form'].submit();
            }    
        </script>
    </body>
</html>