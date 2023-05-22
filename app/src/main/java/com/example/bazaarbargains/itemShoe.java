package com.example.bazaarbargains;

public class itemShoe {
    String name;
    String price;
    String image;
    String description;
    String size;

    public itemShoe()
    {

    }




    public itemShoe(String name, String price, String description, String image, String size) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.size=size;

    }
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
