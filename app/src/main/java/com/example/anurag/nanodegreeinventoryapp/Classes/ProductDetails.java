package com.example.anurag.nanodegreeinventoryapp.Classes;

import android.graphics.Bitmap;

/**
 * Created by anurag on 8/21/2016.
 */
public class ProductDetails {
    String productName;
    int quantity;
    int price;
    Bitmap image;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
