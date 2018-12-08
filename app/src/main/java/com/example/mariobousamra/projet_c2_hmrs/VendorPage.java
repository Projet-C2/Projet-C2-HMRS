package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mariobousamra.projet_c2_hmrs.R.id.databaseListview;

public class VendorPage extends AppCompatActivity {


    Button Logout;
    Button btnAddProduct;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;
    private TextView mNameView;

    private ListView mUserList;
    private ArrayList<String> mList = new ArrayList<>();

    private ListView listView;
    String[] ListElements = new String[] {};

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_page);



        listView = (ListView)findViewById(R.id.databaseListview);
        final List< String > ListElementsArrayList = new ArrayList< String>(Arrays.asList(ListElements));
        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (VendorPage.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                        try {
                            String productName = item_snapshot.child("product_name").getValue().toString();
                            ListElementsArrayList.add(productName);
                            adapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            //Toast.makeText(HotelsPage.this, "" + ex, Toast.LENGTH_LONG).show();
                        }
                    //if(listView.getAdapter().getCount() != 0){
                    //  Toast.makeText(VendorPage.this, "Please select a product", Toast.LENGTH_LONG).show();
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VendorPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });




        firebaseAuth = FirebaseAuth.getInstance();

        //Show the Name of logged in user, WELCOME USER.
        mDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getUid());
        mNameView = (TextView) findViewById(R.id.name_view);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                //String selectedFromList = (listView.getItemAtPosition(position).toString());
                String prod = (String) listView.getAdapter().getItem(position);

                Intent intent =new Intent(VendorPage.this,AddProduct.class);

                //passing product name to AddProduct.
                intent.putExtra("prod", prod);
                startActivity(intent);

            }});



    }

//    //ctl + o // ALT + 0 (with NUMLOCK off)
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logoutMenu:{
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user==null){
//                    //finish this activity
//                    //finish();
//                    Toast.makeText(VendorPage.this,"User is not logged in", Toast.LENGTH_SHORT).show();
//                    //and direct him to the activity
//                    //startActivity(new Intent(LoginPage.this, MainActivity.class));
//                }else{
//                    try {
//                        firebaseAuth.signOut();
//                        Toast.makeText(VendorPage.this, "User Sign out!", Toast.LENGTH_SHORT).show();
//                    }catch (Exception e) {
//                        e.getMessage();
//                    }
//                }
//            }
//        }
//        return true;
//    }
}
