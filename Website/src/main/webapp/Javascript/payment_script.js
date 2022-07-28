function paymentStart(product_id, product, quantity, total_price, fullName, phone, email, billing_address, delivery_address, order_placed, order_delivered, payment_type){
    $.ajax({
        url: 'createOrder',
        data: {
            amount: total_price,
            info: 'order_request'
        },
        type: 'POST',
        success: function(response){ /* Invoked when success */
            response = JSON.parse(response);
            
            if(response.status === "created"){
                let options = {
                    key: "rzp_test_OdBrs6pW8uKVAq",
                    amount: response.amount,
                    currency: "INR",
                    name: product,
                    description: "Quantity: " + quantity,
                    image: "",
                    order_id: response.id,
                    prefill: {
                        name: fullName,
                        email: email,
                        contact: phone
                    },
                    config: {
                        display: {
                            blocks: {
                                offer: {
                                    name: 'Methods with Offers',
                                    instruments: [
                                        {
                                            method: 'wallet',
                                            wallets: ['olamoney']
                                        },
                                        {
                                            method: 'wallet',
                                            wallets: ['amazonpay']
                                        }
                                    ]
                                },
                                other: {
                                    name: 'All Payment Options',          
                                    instruments: [
                                        {method: 'upi'},            
                                        {method: 'card'},            
                                        {method: 'wallet'},            
                                        {method: 'netbanking'},            
                                        {method: 'emi'}          
                                    ]    
                                }   
                            },     
                            sequence: ['block.offer', 'block.other'],      
                            preferences: {        
                                show_default_blocks: false
                            }  
                        }  
                    },
                    handler: function(response) {
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);
                        
                        console.log("Payment Successfull");
                        
                        updatePayment(product_id, quantity, total_price, billing_address, delivery_address, order_placed, order_delivered, payment_type);
                    },
                    notes: {
                        address: "Easy Buy"
                    },
                    theme: {
                        color: "#0000ff"
                    }
                };
                
                let rzp = new Razorpay(options);
                
                rzp.on('payment.failed', function (response){
                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);
                    
                    alert("OOPS!!! Payment Failed");
                });
                
                rzp.open();
            }
        },
        error: function(error) { /* Invoked when error */
            console.log("error",error);
        }
    });
}

function updatePayment(product_id, quantity, total_price, billing_address, delivery_address, order_placed, order_delivered, payment_type){
    $.ajax({
        url: 'completePayment',
        data: {
            total_price: total_price,
            order_placed: order_placed,
            payment_type: payment_type,
            info: 'complete_payment'
        },
        type: 'POST',
        success: function(payment_id){
            updateCustomer(payment_id,product_id,quantity,total_price,billing_address,delivery_address,order_placed,order_delivered);
        }
    });
}

function updateCustomer(payment_id,product_id,quantity,total_price,billing_address,delivery_address,order_placed, order_delivered){
    $.ajax({
        url: 'buy',
        data: {
            demand: "customer",
            command: "add",
            billing_address: billing_address,
            delivery_address: delivery_address,
            info: 'update_customer'
        },
        type: 'POST',
        success: function(response){
            updateProduct(payment_id,product_id,quantity,total_price,order_placed, order_delivered);
        }
    });
}

function updateProduct(payment_id,product_id,quantity,total_price,order_placed, order_delivered){
    $.ajax({
        url: 'update',
        data: {
            demand: "product",
            command: "update",
            product_id: product_id,
            operation: "delQuantity",
            product_qty: quantity,
            info: 'update_product'
        },
        type: 'POST',
        success: function(response){
            finalOrder(payment_id,product_id,quantity,total_price,order_placed, order_delivered);
        }
    });
}

function finalOrder(payment_id,product_id,quantity,total_price,order_placed, order_delivered){
    $.ajax({
        url: 'orderPlaced',
        data: {
            command: "add",
            payment_id: payment_id,
            product_id: product_id,
            product_qty: quantity,
            total_price: total_price,
            order_placed: order_placed,
            order_delivered: order_delivered,
            info: 'place_order'
        },
        type: 'POST',
        success: function(response){
            window.location = "index";
        }
    });
}