package com.coffee.model;

public class CartItem {
	 private String productName;
	    private String description; 
	    private double price;      
	    private String category;  
	    private String imageUrl;    
	    private int quantity;       

	    public CartItem() {}

	    public CartItem(String productName, String description, double price, String category, String imageUrl, int quantity) {
	        this.productName = productName;
	        this.description = description;
	        this.price = price;
	        this.category = category;
	        this.imageUrl = imageUrl;
	        this.quantity = quantity;
	    }
	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public String getImageUrl() {
	        return imageUrl;
	    }

	    public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
}
