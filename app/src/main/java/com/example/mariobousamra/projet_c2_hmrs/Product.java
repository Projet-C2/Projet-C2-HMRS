package com.example.mariobousamra.projet_c2_hmrs;

import java.text.Format;
import java.text.NumberFormat;

public class Product {

String product_name;
String product_category;
Float product_price;
String product_status;
String product_description;

public Product (String product_name, String product_category,String product_status, String product_description)
{

    this.product_name = product_name;
    this.product_category = product_category;
    //this.product_price = product_price;
    this.product_status = product_status;
    this.product_description = product_description;

}
}
