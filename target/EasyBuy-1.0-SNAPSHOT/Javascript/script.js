function addToCart(id,name,price,discount,category){
    
    let cart = localStorage.getItem("cart");
    
    if(cart === null){
        let products = [];
        let product = {productID:id,productName:name,productPrice:price,productQuantity:1,productDiscount:discount,productCategory:category};
        products.push(product);
        localStorage.setItem("cart",JSON.stringify(products));
        showToast(product.productName+" is added to Cart");
    }else{
        let pcart = JSON.parse(cart);
        let oldProducts = pcart.find((items)=> items.productID===id);
        if(oldProducts){
            oldProducts.productQuantity=oldProducts.productQuantity+1;
            pcart.map((items)=>{
                if(items.productID===oldProducts.productID){
                    items.productQuantity = oldProducts.productQuantity;
                }
            });
            localStorage.setItem("cart",JSON.stringify(pcart));
            showToast(oldProducts.productName+" quantity is increased");
        }else{
            let product = {productID:id,productName:name,productPrice:price,productQuantity:1,productDiscount:discount,productCategory:category};
            pcart.push(product);
            localStorage.setItem("cart",JSON.stringify(pcart));
            showToast(product.productName+" is added to Cart");
        }
    }
    updateCart();
}

function updateCart(){
    let cartString = localStorage.getItem("cart");
    let cart = JSON.parse(cartString);
    if(cart===null||cart.length===0){
        $(".cart-items").html("(0)");
        let message=`<div class="text-center"><h3>Your Cart is Empty!!</h3></div>`;
        $(".cart-body").html(message);
        $(".checkout-btn").attr('disabled',true);
    }else{
        console.log(cart);
        $(".cart-items").html(`(${cart.length})`);
        let table=`
            <form id="order" action="checkout.jsp" method="post"></form>
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th class="text-center">Product Name</th>
                        <th class="text-center">Unit Price</th>
                        <th class="text-center">Quantity</th>
                        <th class="text-center">Category</th>
                        <th class="text-center">Total Price</th>
                        <th class="text-center" colspan="2">Action</th>
                    </tr>
                </thead>
        `;
        let totalPrice=0;
        cart.map((items)=>{
            table+=`    
                <tr>
                    <input type="hidden" name="checkout" value="${items.productID}" form="order">
                    <input type="hidden" name="qty" value="${items.productQuantity}" form="order">
                    <td class="text-center">${items.productName}</td>
                    <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${items.productPrice * (1 - items.productDiscount)}</td>
                    <td class="text-center">${items.productQuantity}</td>
                    <td class="text-center">${items.productCategory}</td>
                    <td class="text-center"><i class="fa fa-inr" aria-hidden="true"></i>${Math.ceil(items.productQuantity*items.productPrice*(1-items.productDiscount))}</td>
                    <td class="text-center"><button class="btn-danger" onclick="deleteItem(${items.productID})">Remove</button></td>
                    <td class="text-center"><input type="submit" class="btn-success" value="Place Order" form="order"></td>
                </tr>
            `;
            totalPrice+=Math.ceil(items.productQuantity*items.productPrice*(1-items.productDiscount));
        });
        table=table+`
        <tr><td colspan='8' class="text-right"><b>Total Price: <i class="fa fa-inr" aria-hidden="true"></i>${totalPrice}</b></td></tr>
        </table>`;
        $(".cart-body").html(table);
        $(".checkout-btn").attr('disabled',false);
    }
}

function deleteItem(id){
    let cart = JSON.parse(localStorage.getItem("cart"));
    let newCart = cart.filter((items)=> items.productID!==id);
    localStorage.setItem('cart',JSON.stringify(newCart));
    updateCart();
    showToast("Product is Removed from Cart");
}

$(document).ready(function(){
    updateCart();
});

function showToast(content){
    $("#toast").addClass("display");
    $("#toast").html(content);
    setTimeout(() => {
            $("#toast").removeClass("display");
        },2000);
}

function checkout(){
    window.location="checkout.jsp";
}

function checkout2(){
    window.location="checkout.jsp";
}