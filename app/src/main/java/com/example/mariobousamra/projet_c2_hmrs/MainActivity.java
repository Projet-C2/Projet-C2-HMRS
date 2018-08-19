package com.example.mariobousamra.projet_c2_hmrs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        //set event
        setSingleEvent(mainGrid);

        button = (Button) findViewById(R.id.buttonlocation);
        textView = (TextView) findViewById(R.id.textViewlocation);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPStracker g = new GPStracker(getApplicationContext());
                Location l =g.getLocation();
                if(l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    LatLng myCoordinates = new LatLng(lat,lon);
                    Toast.makeText(getApplicationContext(),"Latitude:"+lat+" \n Longitude: "+lon, Toast.LENGTH_LONG).show();
                    String cityName = getCityName(myCoordinates);
                    textView.setText(cityName);

                }

            }
        });


    }

    private String getCityName(LatLng myCoordinates) {
        String myCity ="";
        Geocoder geocoder =new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();

        }catch (IOException e){
            e.printStackTrace();
        }
            return myCity;
    }


    public void Signin(View view){
        startActivity(new Intent(MainActivity.this, LoginPage.class));
    }

    public void Signup(View view){
        startActivity(new Intent(MainActivity.this, SignupPage.class));
    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop All Child item of Main Grid
        for(int i=0;i<mainGrid.getChildCount();i++)
        {
            //you Can see, all child item is cardView, so we just cast objet to cardView
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view){
                                                //Here to start the activities
                                                if(finalI==0)//Open activity one-->hotels page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,HotelsPage.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                if(finalI==1)//Open activity two-->Resorts page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,ResortsPage.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                if(finalI==2)//Open activity three-->Fashion page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,FashionPage.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                if(finalI==3)//Open activity four-->Night Life page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,NightlifePage.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                if(finalI==4)//Open activity five-->Restaurants page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,RestaurantsPage.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                if(finalI==5)//Open activity six-->Services page
                                                {
                                                    Intent intent =new Intent(MainActivity.this,ServicesPage.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
            );
        }
    }


}
