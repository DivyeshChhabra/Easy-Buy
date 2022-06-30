/*
 * Cart is handled on the Client-Side (in the Browser)
 * - localStorage object is used to handle the Cart on the client-side
 */

function addToCart(id,name,price,discount,category){ /* Function to add Products to Cart */
    
    // Retrieving the existing Cart
    let cart = localStorage.getItem("cart");
    
    if(cart === null){ /* No Cart Exists */
        
        let products = [];
        let product = {productID:id,productName:name,productPrice:price,productQuantity:1,productDiscount:discount,productCategory:category};
        
        products.push(product);
        
        // Creating the new Cart
        localStorage.setItem("cart",JSON.stringify(products));
        
        // Showing the Toast (Notifying the User - about creating new cart)
        showToast(product.productName + " is added to Cart");
    }else{ /* Cart Exists */
        
        // Retrieving the product details
        let pcart = JSON.parse(cart);
        let oldProduct = pcart.find((items)=> items.productID === id);
        
        if(oldProduct){ /* Product exists in the Cart */
            console.log("Hello");
            showToast(oldProduct.productName + " is already added to Cart");
        }else{ /* Product doesn't exists in the Cart */
            
            let product = {productID:id,productName:name,productPrice:price,productQuantity:1,productDiscount:discount,productCategory:category};
            
            pcart.push(product);
            
            // Updating the Cart (Adding new product in the Cart)
            localStorage.setItem("cart",JSON.stringify(pcart));
            
            // Showing the Toast (Notifying the User - about updating the cart (adding new product))
            showToast(product.productName + " is added to Cart");
        }
    }
    
    // Display Cart in Tabular Form
    updateCart();
}

function updateCart(){ /* Function to display the Cart in tabular form (to USer) */
    
    // Retrieving the existing Cart
    let cartString = localStorage.getItem("cart");
    let cart = JSON.parse(cartString);
    
    if(cart === null || cart.length === 0){ /* If no Cart exists or no Products exists in Cart */
        
        $(".cart-items").html("(0)");
        
        // Displaying the message to user that Cart doesn't exists or no products exists in cart
        let message=`<div class="text-center"><h3>Your Cart is Empty!!</h3></div>`;
        $(".cart-body").html(message);
        
    }else{ /* Products exists in the Cart */
        
        $(".cart-items").html(`(${cart.length})`);
        
        // Displaying cart in tabular form
        // Table Head
        let table = `
            <form action="checkout" method="GET" name="cart-checkout-form" id="cart-checkout-form">
                <input type="hidden" name="complete_cart" id="complete_cart" />
                <input type="hidden" name="cart_product_id" id="cart_product_id" />
                <input type="hidden" name="cart_quantity" id="cart_quantity" />
            
                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th class="text-center">Product Name</th>
                            <th class="text-center">Unit Price</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-center">Category</th>
                            <th class="text-center">Total Price</th>
                            <th class="text-center" colspan="2" id="action">Action</th>
                        </tr>
                    </thead>
        `;
        
        let totalPrice = 0;
        
        // Table Body
        cart.map((items)=>{
            table += `    
                <tr>
                    <td class="text-center">${items.productName}</td>
                    <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(items.productPrice * (1 - (items.productDiscount / 100)))}</td>
                    <td class="text-center"><i class="fa fa-minus-square danger" style="color:red" onclick="decreaseQuantity(${items.productID})"></i> ${items.productQuantity} <i class="fa fa-plus-square" style="color:green" onclick="increaseQuantity(${items.productID})"></i> </td>
                    <td class="text-center">${items.productCategory}</td>
                    <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(items.productQuantity * items.productPrice * (1 - (items.productDiscount / 100)))}</td>
                    <td class="text-center"><button class="btn-danger" onclick="deleteItem(${items.productID})">Remove</button></td>
                    <td class="text-center"><input type="submit" class="btn-success" value="Place Order" onclick="checkOut(${items.productID},${items.productQuantity})"/></td>
                </tr>
            `;
    
            totalPrice += Math.ceil(items.productQuantity * items.productPrice * (1 - (items.productDiscount / 100))); // Updating the total price of products in the cart
        });
        
        // Displaying total price of products in the cart
        table += `
                    <tr>
                        <td colspan='8' class="text-right">
                            <b>Total Price: <i class="fa fa-inr" aria-hidden="true"></i>${totalPrice}</b>
                        </td>
                    </tr>
                </table>
            </form>`;
        
        $(".cart-body").html(table);
    }
}

function deleteItem(id){ /* Function to delete product from Cart */
    
    let cart = JSON.parse(localStorage.getItem("cart"));
    let newCart = cart.filter((items)=> items.productID!==id);
    
    // Updating the Cart (deleting the product from Cart)
    localStorage.setItem('cart',JSON.stringify(newCart));
    
    updateCart();
    
    // Showing the Toast (Notifying the User - about updating the cart)
    showToast("Product is Removed from Cart");
}

function increaseQuantity(id) {
    let cart = localStorage.getItem("cart");
    
    let pcart = JSON.parse(cart);
    let product = pcart.find((items)=> items.productID === id);

    product.productQuantity += 1;

    pcart.map((items) => {
        if(items.productID === product.productID){
            items.productQuantity = product.productQuantity;
        }
    });

    // Updating the Cart
    localStorage.setItem("cart",JSON.stringify(pcart));

    updateCart();
}

function decreaseQuantity(id){
    let cart = localStorage.getItem("cart");
    
    let pcart = JSON.parse(cart);
    let product = pcart.find((items)=> items.productID === id);

    product.productQuantity -= 1;
    if(product.productQuantity === 0){
        deleteItem(product.productID);
        return;
    }

    pcart.map((items) => {
        if(items.productID === product.productID){
            items.productQuantity = product.productQuantity;
        }
    });

    // Updating the Cart
    localStorage.setItem("cart",JSON.stringify(pcart));

    updateCart();
}

$(document).ready(function(){
    updateCart();
});

function showToast(content){ /* Function to Display the Toast */
    
    $("#toast").addClass("display");
    
    $("#toast").html(content);
    
    // Toast will be displayed for 2 secs
    setTimeout(() => {
            $("#toast").removeClass("display");
        },2000);
}

function checkOut(id,qty){
    
    console.log("Hello World");
    
    if(id === -1){
        let cart = JSON.parse(localStorage.getItem("cart"));
        let products = [];
        let quantity = [];

        cart.map((items) => {
            products.push(items.productID);
            quantity.push(items.productQuantity);
        });
        
        $("#cart_product_id").val(products);
        $("#cart_quantity").val(quantity);
        $("#complete_cart").val(true);
    }else{
        $("#cart_product_id").val(id);
        $("#cart_quantity").val(qty);
        $("#complete_cart").val(false);
    }
}