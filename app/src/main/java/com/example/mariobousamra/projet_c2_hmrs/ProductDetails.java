package com.example.mariobousamra.projet_c2_hmrs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;

public class ProductDetails extends AppCompatActivity {


    TextView tvName;
    TextView tvPrice;
    TextView tvdescription;
    TextView tvStatus;

    ImageView ivProdImage;

    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;

    public String Uidwithimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvName = (TextView) findViewById(R.id.tvName2);
        tvPrice = (TextView)findViewById(R.id.tvPrice2);
        tvdescription = (TextView)findViewById(R.id.tvDescription2);
        tvStatus = (TextView)findViewById(R.id.tvStatus2);

        ivProdImage = findViewById(R.id.ivProdImage);


        final String product_name = getIntent().getExtras().getString("product_name");

        //if(product_name != null ){
          //  Toast.makeText(ProductDetails.this, "product_name : " + product_name, Toast.LENGTH_SHORT).show();
        //}


        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating users
                outerloop:
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    //iterating user fields
                    for(DataSnapshot info_item_snapshot:item_snapshot.getChildren()) {
                        try {
                            String prod_category = info_item_snapshot.child("product_name").getValue().toString();
                            if(prod_category.equals(product_name)) {
                                //get the Uid ex: QNqWIXGKySORpTcWpv0HLXV03Ax2
                                Uidwithimage = item_snapshot.getRef().getKey().toString();
/*                              if(Uidwithimage != null ){
                                  Toast.makeText(ProductDetails.this, "Uid : " + Uidwithimage + product_name, Toast.LENGTH_LONG).show();
                                }*/
                                tvName.setText(info_item_snapshot.child("product_name").getValue().toString());
                                tvPrice.setText(info_item_snapshot.child("product_price").getValue().toString());
                                tvdescription.setText(info_item_snapshot.child("product_description").getValue().toString());
                                tvStatus.setText(info_item_snapshot.child("product_status").getValue().toString());

                                break outerloop;
                            }
                        } catch (Exception ex) {

                        }
                    }
                }


                //load image from firebase to imageview
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();
                storageReference.child(Uidwithimage).child(product_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(ivProdImage);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
