package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorPage extends AppCompatActivity {


    Button Logout;
    Button btnAddProduct;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;
    private TextView mNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_page);

        firebaseAuth = FirebaseAuth.getInstance();

        //Show the Name of logged in user, WELCOME USER.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("User1").child("Name");
        mNameView = (TextView) findViewById(R.id.name_view);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getValue().toString();
                mNameView.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Logout = (Button)findViewById(R.id.btnLogout);
        btnAddProduct = (Button) findViewById(R.id.btn_add_product);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    //finish this activity
                    //finish();
                    Toast.makeText(VendorPage.this,"User is not logged in", Toast.LENGTH_SHORT).show();
                    //and direct him to the activity
                    //startActivity(new Intent(LoginPage.this, MainActivity.class));
                }else{
                    try {
                        firebaseAuth.signOut();
                        Toast.makeText(VendorPage.this, "Signed Out!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VendorPage.this, MainActivity.class));
                    }catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(VendorPage.this,AddProduct.class);
                startActivity(intent);

            }
        });
    }



    //ctl + o // ALT + 0 (with NUMLOCK off)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    //finish this activity
                    //finish();
                    Toast.makeText(VendorPage.this,"User is not logged in", Toast.LENGTH_SHORT).show();
                    //and direct him to the activity
                    //startActivity(new Intent(LoginPage.this, MainActivity.class));
                }else{
                    try {
                        firebaseAuth.signOut();
                        Toast.makeText(VendorPage.this, "User Sign out!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        }
        return true;
    }
}
