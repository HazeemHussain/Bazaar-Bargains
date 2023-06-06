package com.example.bazaarbargains;

import java.io.Serializable;

public class modelAddCart implements Serializable {
    
    private String itemName, itemPrice;
    private String quantity;
    private String url;
    private String sizec;

    private String perItemCost;



    public modelAddCart(String itemName, String quantity, String itemPrice, String url, String perItemCost,String sizec) {
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.quantity = quantity;
        this.url = url;
        this.perItemCost = perItemCost;
        this.sizec = sizec;
    }




    public String getSizec() {
        return sizec;
    }

    public void setSizec(String sizec) {
        this.sizec = sizec;
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


