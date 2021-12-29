<%@page import="java.sql.*"%>

<%
    /* Connecting MySQL DataBase with JAVA application */
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection myConn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_buy", "root", "root");
%>    

<div class="modal fade bd-example-modal-lg" id="cart" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myLargeModalLabel">My Cart</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body px-0">
                <div class="cart-body"></div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="add-category-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Category Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="ProductOperationServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="category">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="category_name">Category Name</label>
                        <input type="text" placeholder="Category Name" class="form-control" name="category_name" id="category_name" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Category Description</label>
                        <textarea placeholder="Category Description" class="form-control" name="description" id="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="sale">Sale <small>(if any) (FORMAT - xxx.xxx%)</small></label>
                        <input type="text" placeholder="Sale" class="form-control" name="sale" id="sale">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Add Category">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="add-product-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Product Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="ProductOperationServlet" method="post" autocomplete="off" enctype='multipart/form-data'>
                <input type="hidden" name="operation" value="product">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="product_name">Product Name</label>
                        <input type="text" placeholder="Product Name" class="form-control" name="product_name" id="product_name" required/>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                            <label for="description">Product Description</label>
                            <textarea placeholder="Product Description" class="form-control" name="description" id="description" required/></textarea>
                        </div>
                        <label for="unit_price">Unit Price</label>
                        <input type="text" placeholder="Unit Price" class="form-control" name="unit_price" id="unit_price" required/>
                    </div>
                    <div class="form-group">
                        <label for="discount">Discount <small>(if any) (FORMAT - xxx.xxx%)</small></label>
                        <input type="text" placeholder="Discount" class="form-control" name="discount" id="discount">
                    </div>
                    <div class="form-group">
                        <label for="color">Color</label>
                        <input type="text" placeholder="Color" class="form-control" name="color" id="color">
                    </div>
                    <div class="form-group">
                        <label for="weight">Weight <small>(in grams)</small></label>
                        <input type="text" placeholder="Weight" class="form-control" name="weight" id="weight">
                    </div>
                    <div class="form-group">
                        <label for="quantities">Quantities</label>
                        <input type="text" placeholder="Quantities" class="form-control" name="quantities" id="quantities" required/>
                    </div>
                    <div class="form-group">
                        <label>Category</label>
                        <select name="category_id" class="form-control" required>
                            <%
                                PreparedStatement stmt8 = myConn1.prepareStatement("select * from category;");
                                ResultSet rs13 = stmt8.executeQuery();
                                while(rs13.next()){
                            %>
                                    <option value="<%= rs13.getInt("category_id") %>"><%= rs13.getString("category_name") %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="product_image">Select a Picture of Product <small>MAX :- 16MB</small></label>
                        <input type="file" name="product_image" id="product_image" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Add Product">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="edit-name-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Edit Name</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="name">
                <div class="modal-body">
                    <div class="form-group">
                            <label for="first_name"><b>First Name</b></label>
                            <input type="text" class="form-control" id="first_name" placeholder="First Name" name="first_name" required>
                    </div>
                    <div class="form-group">
                        <label for="last_name"><b>Last Name</b></label>
                        <input type="text" class="form-control" id="last_name" placeholder="Last Name" name="last_name" required>
                    </div>
                    <div class="form-group">
                        <label for="curr_password"><b>Current Password</b></label>
                        <input type="password" class="form-control" id="curr_password" placeholder="Current Password" name="curr_password" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Modify">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="edit-phone-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Edit Phone Number</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="phone">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="phone"><b>Phone</b></label>
                        <input type="text" class="form-control" id="phone" placeholder="Phone No." name="phone" required>
                    </div>
                    <div class="form-group">
                        <label for="curr_password"><b>Current Password</b></label>
                        <input type="password" class="form-control" id="curr_password" placeholder="Current Password" name="curr_password" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Modify">
                </div>
            </form>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="edit-e_mail-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Edit E-Mail Address</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="e_mail">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="e_mail"><b>New Email Address</b></label>
                        <input type="email" class="form-control" id="e_mail" aria-describedby="emailHelp" placeholder="New E_Mail ID" name="e_mail" required>
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                    </div>
                    <div class="form-group">
                        <label for="curr_password"><b>Current Password</b></label>
                        <input type="password" class="form-control" id="curr_password" placeholder="Current Password" name="curr_password" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Modify">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="edit-password-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Edit Password</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="password">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="curr_password"><b>Current Password</b></label>
                        <input type="password" class="form-control" id="curr_password" placeholder="Current Password" name="curr_password" required/>
                    </div>
                    <div class="form-group">
                        <label for="password"><b>New Password</b></label>
                        <input type="password" class="form-control" id="password" placeholder="New Password" name="password" required/>
                    </div>
                    <div class="form-group">
                        <label for="new_password"><b>Retype New Password</b></label>
                        <input type="password" class="form-control" id="new_password" placeholder="Retype New Password" name="new_password" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Modify">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="delete-account-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Account</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="delete">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="e_mail"><b>Current Email address</b></label>
                        <input type="email" class="form-control" id="e_mail" aria-describedby="emailHelp" placeholder="Current E_Mail ID" name="e_mail" required/>
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                    </div>
                    <div class="form-group">
                        <label for="curr_password"><b>Current Password</b></label>
                        <input type="password" class="form-control" id="curr_password" placeholder="Current Password" name="curr_password" required/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Delete">
                </div>
            </form>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="delivery-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delivery Address</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="deivery_address"><b>Delivery Address</b></label>
                    <textarea class="form-control" id="deivery_address" placeholder="Delivery Address" name="delivery_address" required></textarea>
                </div><br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-primary" value="Add">
            </div>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="billing-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Billing Address</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="billing_address"><b>Billing Address</b></label>
                    <textarea class="form-control" id="billing_address" placeholder="Billing Address" name="billing_address" required></textarea>
                </div><br> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-primary" value="Add">
            </div>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="add_quantity" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Add Quantities</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="EditServlet" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="quantity">
                <div class="modal-body">
                    <label for="quantities">Product ID </label>
                    <input type="text" name="productID" placeholder="ProductID" id="productID" class="form-control">
                    <label for="quantities">Quantities </label>
                    <input type="text" placeholder="Quantities" id="quantities" name="quantities" class="form-control"><br>
                </div>
                <div class="modal-footer text-center">
                    <input type="submit" class="btn btn-primary" value="Add">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade bd-example-modal-lg" id="orders" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myLargeModalLabel">Total Orders</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body px-0">
                
            </div>
        </div>
    </div>
</div>

<div id="toast"></div>