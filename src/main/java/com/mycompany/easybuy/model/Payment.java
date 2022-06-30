package com.mycompany.easybuy.model;

public class Payment {
    private int id;
    private String paymentType;
    private String paymentDate;
    private double amount;

    public Payment(int id, String paymentType, String paymentDate, double amount) {
        this.id = id;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public Payment(String paymentType, String paymentDate, double amount) {
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", paymentType=" + paymentType + ", paymentDate=" + paymentDate + ", amount=" + amount + '}';
    }
}
