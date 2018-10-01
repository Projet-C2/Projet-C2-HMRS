package com.example.mariobousamra.projet_c2_hmrs;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {


    TextView tvName;
    TextView tvPrice;
    TextView tvdescription;
    TextView tvStatus;




    ImageView ivProdImage;

    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvName = (TextView) findViewById(R.id.tvName2);
        tvPrice = (TextView)findViewById(R.id.tvPrice2);
        tvdescription = (TextView)findViewById(R.id.tvDescription2);
        tvStatus = (TextView)findViewById(R.id.tvStatus2);

        ivProdImage = findViewById(R.id.ivProdImage);


        String product_name = getIntent().getExtras().getString("product_name");

        //if(product_name != null ){
          //  Toast.makeText(ProductDetails.this, "product_name : " + product_name, Toast.LENGTH_SHORT).show();
        //}


        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();
/*        storageReference.child().child(product_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(ivProdImage);
            }
        });*/
    }
}
