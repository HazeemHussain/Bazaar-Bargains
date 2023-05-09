package com.example.bazaarbargains;

public class modelAddCart {
    
    private String itemName, itemPrice;
    private String quantity;
    private String url;

    private String perItemCost;



    public modelAddCart(String itemName, String quantity, String itemPrice, String url, String perItemCost) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.quantity = quantity;
        this.url = url;
        this.perItemCost = perItemCost;
    }
    public modelAddCart() {

    }

    public String getPerItemCost() {
        return perItemCost;
    }

    public String getItemName() {
        return itemName;
    }

    public String getitemPrice() {
        return itemPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUrl() {
        return url;
    }
}


