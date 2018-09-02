package com.example.mariobousamra.projet_c2_hmrs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HotelsPage extends AppCompatActivity {

    private TextView Title;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_page);

        Title = (TextView)findViewById(R.id.tvTitle);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());//"cCyQM76KQ3gaWusydXY1HjkrKFB3");
        DatabaseReference databaseReference = firebaseDatabase.getReference();//.child("zSlSpnH1aXaHkNcT0XhNTBKoePJ2");//"cCyQM76KQ3gaWusydXY1HjkrKFB3");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
//                    try {
//                        // Toast.makeText(HotelsPage.this, "item id " + item_snapshot.child("product_category").getRef().addValueEventListener().toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(HotelsPage.this, "item id " + item_snapshot.child("product_category").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    }catch (Exception ex){
//
//                    }

                   try{
                       //UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                       Toast.makeText(HotelsPage.this,""+item_snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
                   }catch (Exception ex){
                       Toast.makeText(HotelsPage.this,""+ex, Toast.LENGTH_LONG).show();
                   }
               }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HotelsPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
