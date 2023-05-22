package com.example.bazaarbargains;

public class categoryModel {

    String catName;
        int catImage;

    public categoryModel(String catName, int catImage) {
        this.catName = catName;
        this.catImage = catImage;
    }

    public categoryModel() {
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }
}
