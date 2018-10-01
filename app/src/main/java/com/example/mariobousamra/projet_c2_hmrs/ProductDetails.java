package com.example.mariobousamra.projet_c2_hmrs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        String product_name = getIntent().getExtras().getString("product_name");

        if(product_name != null ){
            Toast.makeText(ProductDetails.this, "product_name : " + product_name, Toast.LENGTH_SHORT).show();
        }

    }
}
