package com.mycompany.easybuy.model;

public class Customer {
    private int id;
    private int memberId;
    private String billingAddress;
    private String deliveryAddress;

    public Customer(int id, int memberId, String billingAddress, String deliveryAddress) {
        this.id = id;
        this.memberId = memberId;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
    }

    public Customer(int memberId, String billingAddress, String deliveryAddress) {
        this.memberId = memberId;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", memberId=" + memberId + ", billingAddress=" + billingAddress + ", deliveryAddress=" + deliveryAddress + '}';
    }
}
