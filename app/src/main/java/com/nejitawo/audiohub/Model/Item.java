package com.nejitawo.audiohub.Model;

import android.content.Context;

import com.parse.ParseObject;

/**
 * Created by devthehomes on 11/16/16.
 */

public class Item {
    private String name;
    private Integer cost;
    private String imageUrl;
    private String category;
    private int restaurantID;
    private String productInfo;
private String description;
    private int minSize;
    private int maxSize;
    public Item(){

    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public static Item giveFullDetails(ParseObject revision, Context context) {
        Item t = new Item();
        ParseObject docs = revision;

        try{
            t.setName((String) docs.get("name"));
            t.setCategory((String) docs.get("category"));
            t.setImageUrl((String) docs.get("imageurl"));
            t.setCost((Integer) docs.get("price"));
            t.setProductInfo((String) docs.get("productinfo"));
            t.setMaxSize((Integer) docs.get("maxsize"));
            t.setMinSize((Integer) docs.get("minsize"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return t;
    }
}
