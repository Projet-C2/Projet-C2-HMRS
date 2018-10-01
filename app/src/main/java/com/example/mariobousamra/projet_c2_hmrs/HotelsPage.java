package com.example.mariobousamra.projet_c2_hmrs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelsPage extends AppCompatActivity {

    private TextView Title;
    private ListView listView;
    String[] ListElements = new String[] {};


    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    String category = Globals.Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_page);

        Title = (TextView)findViewById(R.id.HotelList);
        Title.setText("NEARBY " + category.toUpperCase());



        listView = (ListView)findViewById(R.id.databaseListview);
        final List < String > ListElementsArrayList = new ArrayList< String>(Arrays.asList(ListElements));
        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (HotelsPage.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());//"cCyQM76KQ3gaWusydXY1HjkrKFB3");
        DatabaseReference databaseReference = firebaseDatabase.getReference();//.child("zSlSpnH1aXaHkNcT0XhNTBKoePJ2");//"cCyQM76KQ3gaWusydXY1HjkrKFB3");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating users
               for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                   //iterating user fields
                   for(DataSnapshot info_item_snapshot:item_snapshot.getChildren()) {
                       try {
                           //UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                           //Toast.makeText(HotelsPage.this, "" + info_item_snapshot.child("product_name").getValue().toString(), Toast.LENGTH_SHORT).show();
                           String prod_category = info_item_snapshot.child("product_category").getValue().toString();
                           if(prod_category.equals(category)){
                               String productName = info_item_snapshot.child("product_name").getValue().toString();
                               ListElementsArrayList.add(productName);
                               adapter.notifyDataSetChanged();
                           }
                       } catch (Exception ex) {
                           //Toast.makeText(HotelsPage.this, "" + ex, Toast.LENGTH_LONG).show();
                       }
                   }
               }
               if(listView.getAdapter().getCount() != 0){
                   Toast.makeText(HotelsPage.this, "Please select a product", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HotelsPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });



        

    }

}
