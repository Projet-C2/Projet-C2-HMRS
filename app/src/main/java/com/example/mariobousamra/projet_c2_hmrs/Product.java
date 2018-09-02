package com.example.mariobousamra.projet_c2_hmrs;

import java.text.Format;
import java.text.NumberFormat;

public class Product {

String product_name;
String product_category;
Float product_price;
String product_status;
String product_description;

public Product (String product_name, String product_category,Float product_price,String product_status, String product_description)
{

    this.product_name = product_name;
    this.product_category = product_category;
    this.product_price = product_price;
    this.product_status = product_status;
    this.product_description = product_description;

}

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public Float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
}
