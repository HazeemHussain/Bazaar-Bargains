package com.example.bazaarbargains;

public class wishItem {

    private String itemName, itemPrice;
    private String url;


    public wishItem(String itemName, String itemPrice, String url) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.url = url;

    }
    public wishItem() {

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

