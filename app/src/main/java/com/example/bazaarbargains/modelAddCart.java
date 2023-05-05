package com.example.bazaarbargains;

public class modelAddCart {
    
    private String itemName, itemPrice;
    private String quantity;
    private String url;

    public modelAddCart(String itemName, String quantity,String itemPrice, String url) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.quantity = quantity;
        this.url = url;
    }
    public modelAddCart() {

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


