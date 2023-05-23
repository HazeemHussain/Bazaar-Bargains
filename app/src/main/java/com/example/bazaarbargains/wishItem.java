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

    public String getItemPrice() {
        return itemPrice;
    }

    public String getUrl() {
        return url;
    }
}

