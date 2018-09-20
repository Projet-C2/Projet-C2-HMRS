package com.example.mariobousamra.projet_c2_hmrs;

public class Model {

    //these names must match with the names in firebase database
    String product_name;
    String product_description;
    String product_image;

    //contructor
    // getter and setter(alt+insert)
    public Model(){}

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
