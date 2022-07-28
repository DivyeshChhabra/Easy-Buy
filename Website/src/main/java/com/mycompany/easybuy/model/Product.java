package com.mycompany.easybuy.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private int sellerId;
    private double unitPrice;
    private double discount;
    private int inventory;
    private String productImage;
    private double rating;
    private int reviews;
    private String sellerName;
    private String categoryName;
    private int[] ratingDistribution;

    public Product(int id, String name, String description, int categoryId, int sellerId, double unitPrice, double discount, int inventory, String productImage, double rating, int reviews, String sellerName, String categoryName, int[] ratingDistribution) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.inventory = inventory;
        this.productImage = productImage;
        this.rating = rating;
        this.reviews = reviews;
        this.sellerName = sellerName;
        this.categoryName = categoryName;
        this.ratingDistribution = ratingDistribution;
    }

    public Product(int id, String name, String description, int categoryId, int sellerId, double unitPrice, double discount, int inventory, String productImage, double rating, int reviews, String sellerName, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.inventory = inventory;
        this.productImage = productImage;
        this.rating = rating;
        this.reviews = reviews;
        this.sellerName = sellerName;
        this.categoryName = categoryName;
    }
    
    public Product(String name, String description, int categoryId, int sellerId, double unitPrice, double discount, int inventory, String productImage, double rating, int reviews, String sellerName, String categoryName, int[] ratingDistribution) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.inventory = inventory;
        this.productImage = productImage;
        this.rating = rating;
        this.reviews = reviews;
        this.sellerName = sellerName;
        this.categoryName = categoryName;
        this.ratingDistribution = ratingDistribution;
    }

    public Product(String name, String description, int categoryId, int sellerId, double unitPrice, double discount, int inventory, String productImage, double rating, int reviews, String sellerName, String categoryName) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.inventory = inventory;
        this.productImage = productImage;
        this.rating = rating;
        this.reviews = reviews;
        this.sellerName = sellerName;
        this.categoryName = categoryName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int[] getRatingDistribution() {
        return ratingDistribution;
    }

    public void setRatingDistribution(int[] ratingDistribution) {
        this.ratingDistribution = ratingDistribution;
    }
    
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", categoryId=" + categoryId + ", sellerId=" + sellerId + ", unitPrice=" + unitPrice + ", discount=" + discount + ", inventory=" + inventory + ", productImage=" + productImage + ", rating=" + rating + ", reviews=" + reviews + '}';
    }
}
