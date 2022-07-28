<%@page import="java.sql.*"%>

<%@page import="com.mycompany.easybuy.connector.Connector"%>

<% Connection connect = Connector.connect(); %>    

<div class="modal fade bd-example-modal-lg" id="cart" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="myLargeModalLabel">My Cart</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <div class="modal-body px-0 py-0 my-0">
                <div class="cart-body"></div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="add-category-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLabel">Category Details</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="add" method="post" autocomplete="off">
                <input type="hidden" name="demand" value="category"/>
                <input type="hidden" name="command" value="add"/>
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="category_name">Category Name</label>
                        <input type="text" placeholder="Category Name" class="form-control" name="category_name" id="category_name" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Category Description</label>
                        <textarea placeholder="Category Description" class="form-control" name="description" id="description" required></textarea>
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

<div class="modal fade" id="add-product-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLabel">Product Details</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="add" method="POST" autocomplete="off" enctype='multipart/form-data'>
                <input type="hidden" name="demand" value="product"/>
                <input type="hidden" name="command" value="add"/>
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="product_name">Product Name <small>(MAX - 20 Characters)</small></label>
                        <input type="text" placeholder="Product Name" class="form-control" name="product_name" id="product_name" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Product Description</label>
                        <textarea placeholder="Product Description" class="form-control" name="description" id="description" required/></textarea>
                    </div>
                        
                    <div class="form-group">
                        <label for="unit_price">Unit Price</label>
                        <input type="text" placeholder="Unit Price" class="form-control" name="unit_price" id="unit_price" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="discount">Discount <small>(if any) (FORMAT - xxx.xxx%)</small></label>
                        <input type="text" placeholder="Discount" class="form-control" name="discount" id="discount">
                    </div>
                    
                    <div class="form-group">
                        <label for="quantities">Quantity</label>
                        <input type="text" placeholder="Quantities" class="form-control" name="quantities" id="quantities" required/>
                    </div>
                    
                    <div class="form-group">
                        <label>Category</label>
                        
                        <select name="category_id" class="form-control" required>
                            <%
                                PreparedStatement stmt8 = connect.prepareStatement("select * from category;");
                                ResultSet rs13 = stmt8.executeQuery();
                                while(rs13.next()){
                            %>
                                <option value="<%= rs13.getInt("id") %>"><%= rs13.getString("name") %></option>
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
                <h3 class="modal-title" id="exampleModalLabel">Edit Name</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="edit" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="name">
                <input type="hidden" name="demand" value="member">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <div class="form-group">
                            <label for="first_name"><b>First Name</b></label>
                            <input type="text" class="form-control" id="first_name" placeholder="First Name" name="first_name" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="last_name"><b>Last Name</b></label>
                        <input type="text" class="form-control" id="last_name" placeholder="Last Name" name="last_name" required/>
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
                <h3 class="modal-title" id="exampleModalLabel">Edit Phone Number</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="edit" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="phone">
                <input type="hidden" name="demand" value="member">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="phone"><b>Phone</b></label>
                        <input type="text" class="form-control" id="phone" placeholder="Phone No." name="phone" required>
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
                <h3 class="modal-title" id="exampleModalLabel">Edit E-Mail Address</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="edit" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="e_mail">
                <input type="hidden" name="demand" value="member">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="e_mail"><b>New Email Address</b></label>
                        <input type="email" class="form-control" id="e_mail" aria-describedby="emailHelp" placeholder="New E_Mail ID" name="e_mail" required>
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
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
                <h3 class="modal-title" id="exampleModalLabel">Edit Password</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="edit" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="password">
                <input type="hidden" name="demand" value="member">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="password"><b>New Password</b> <small>(MAX - 20 Characters)</small></label>
                        <input type="password" class="form-control" id="password" placeholder="New Password" name="password" required/>
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
                <h3 class="modal-title" id="exampleModalLabel">Delete Account</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="delete" method="post" autocomplete="off">
                <input type="hidden" name="demand" value="member">
                <input type="hidden" name="command" value="delete">
                
                <div class="modal-body">
                    <div class="form-group">
                        <label for="e_mail"><b>Current Email address</b></label>
                        <input type="email" class="form-control" id="e_mail" aria-describedby="emailHelp" placeholder="Current E_Mail ID" name="curr_email" required/>
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
                <h3 class="modal-title" id="exampleModalLabel">Delivery Address</h3>
                
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
                <h3 class="modal-title" id="exampleModalLabel">Billing Address</h3>
                
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
                <h3 class="modal-title" id="exampleModalLongTitle">Add Inventory</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="update" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="addQuantity">
                <input type="hidden" name="demand" value="product">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <label for="product_id">Product ID </label>
                    <input type="text" name="product_id" placeholder="ProductID" id="product_id" class="form-control">
                    
                    <label for="quantity">Quantity </label>
                    <input type="text" placeholder="Quantity" id="quantity" name="quantity" class="form-control"><br>
                </div>
                
                <div class="modal-footer text-center">
                    <input type="submit" class="btn btn-primary" value="Add">
                </div>
            </form>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="add_discount" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle">Add Discount</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="update" method="post" autocomplete="off">
                <input type="hidden" name="operation" value="discount">
                <input type="hidden" name="demand" value="product">
                <input type="hidden" name="command" value="update">
                
                <div class="modal-body">
                    <label for="product_id">Product ID </label>
                    <input type="text" name="product_id" placeholder="ProductID" id="product_id" class="form-control">
                    
                    <label for="discount">Discount </label>
                    <input type="text" placeholder="Discount" id="discount" name="discount" class="form-control"><br/>
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
                <h3 class="modal-title" id="myLargeModalLabel">Total Orders</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <div class="modal-body px-0"></div>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="e-signature-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle">e - Signature</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <div class="modal-body">
                <div class="digital-signature">
                    <canvas id="canvas" style="border: 2px solid black"></canvas>
                </div>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">Add</button>
            </div>
        </div>
    </div>
</div>
                        
<div class="modal fade" id="rating-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle">Add Rating</h3>
                
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            
            <form action="addRating" id="ratingForm" autocomplete="off">
                <input type="hidden" name="command" value="update"/>
                
                <div class="modal-body">
                    <label for="order_id">Order ID</label>
                    <input type="text" name="order_id" placeholder="OrderID" id="order_id" class="form-control" required/> <br/>

                    <label for="order_id" class="m-0">Rate Your Order</label> <br/>
                    <fieldset class="rating">
                        <input type="radio" id="star5" name="rating" value="5" class="five"/><label for="star5" title="Excellent">5 stars</label>
                        <input type="radio" id="star4" name="rating" value="4" class="four"/><label for="star4" title="Very Good">4 stars</label>
                        <input type="radio" id="star3" name="rating" value="3" class="three"/><label for="star3" title="Good">3 stars</label>
                        <input type="radio" id="star2" name="rating" value="2" class="two"/><label for="star2" title="Bad">2 stars</label>
                        <input type="radio" id="star1" name="rating" value="1" class="one"/><label for="star1" title="Very Bad">1 star</label>
                    </fieldset>
                </div>
                
                <br/> <br/>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary" value="Rate"/>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="toast"></div>