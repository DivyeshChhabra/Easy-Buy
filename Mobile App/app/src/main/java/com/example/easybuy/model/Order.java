package com.example.easybuy.model;

public class Order {
    private int id;
    private int customerId;
    private int productId;
    private int paymentId;
    private String orderPlacedDate;
    private String orderDeliveredDate;
    private int quantity;
    private double totalPrice;
    private int rating;
    private String orderStatus;

    public Order(int id, int customerId, int productId, int paymentId, String orderPlacedDate, String orderDeliveredDate, int quantity, double totalPrice, int rating, String orderStatus) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.paymentId = paymentId;
        this.orderPlacedDate = orderPlacedDate;
        this.orderDeliveredDate = orderDeliveredDate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.rating = rating;
        this.orderStatus = orderStatus;
    }

    public Order(int customerId, int productId, int paymentId, String orderPlacedDate, String orderDeliveredDate, int quantity, double totalPrice, int rating, String orderStatus) {
        this.customerId = customerId;
        this.productId = productId;
        this.paymentId = paymentId;
        this.orderPlacedDate = orderPlacedDate;
        this.orderDeliveredDate = orderDeliveredDate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.rating = rating;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public String getOrderDeliveredDate() {
        return orderDeliveredDate;
    }

    public void setOrderDeliveredDate(String orderDeliveredDate) {
        this.orderDeliveredDate = orderDeliveredDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customerId=" + customerId + ", productId=" + productId + ", paymentId=" + paymentId + ", orderPlacedDate=" + orderPlacedDate + ", orderDeliveredDate=" + orderDeliveredDate + ", quantity=" + quantity + ", totalPrice=" + totalPrice + '}';
    }

    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String productName;
    private String productImage;
    private String paymentType;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Order(int id, int customerId, String orderPlacedDate, String orderDeliveredDate, int quantity, double totalPrice, String customerName, String customerEmail, String customerAddress, String productName, String productImage, String paymentType, int rating, String orderStatus) {
        this.id = id;
        this.customerId = customerId;
        this.orderPlacedDate = orderPlacedDate;
        this.orderDeliveredDate = orderDeliveredDate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.productName = productName;
        this.productImage = productImage;
        this.paymentType = paymentType;
        this.rating = rating;
        this.orderStatus = orderStatus;
    }
}