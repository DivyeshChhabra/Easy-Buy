package com.example.easybuy.custom;

import android.graphics.drawable.Drawable;

public class OrdersView {

    private Drawable imageSource;
    private String productName;
    private String orderStatus;

    public OrdersView(Drawable imageSource, String productName, String orderStatus) {
        this.imageSource = imageSource;
        this.productName = productName;
        this.orderStatus = orderStatus;
    }

    public Drawable getImageSource() {
        return imageSource;
    }

    public void setImageSourceId(Drawable imageSourceId) {
        this.imageSource = imageSourceId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
