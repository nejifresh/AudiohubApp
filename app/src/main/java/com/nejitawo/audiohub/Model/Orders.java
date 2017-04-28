package com.nejitawo.audiohub.Model;

/**
 * Thinking of adding purchases to this app in future realeases
 * This class is not used in this project yet
 */

public class Orders {
    private String item;
    private Integer price;
    private Integer qty;
    private Integer total;
private String imageLink;
    public Orders(){

    }

    public Orders(String item, Integer price, Integer total, Integer qty){
        this.item = item;
        this.price = price;
        this.total = total;
        this.qty = qty;

    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
