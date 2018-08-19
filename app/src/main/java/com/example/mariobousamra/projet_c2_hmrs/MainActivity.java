package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainGrid = (GridLayout)findViewById(R.id.mainGrid);

        //set event
        setSingleEvent(mainGrid);

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
