package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupPage extends AppCompatActivity {


    private EditText Name;
    private EditText LastName;
    private EditText PhoneNumber;
    private EditText EmailAddress;
    private EditText Password;
    private TextView Location;
    private Button Create;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        //referencing
        Name = (EditText)findViewById(R.id.etName);
        LastName = (EditText)findViewById(R.id.etLastName);
        PhoneNumber = (EditText)findViewById(R.id.etPhone);
        EmailAddress = (EditText)findViewById(R.id.etEmailAddress);
        Password = (EditText)findViewById(R.id.etPassword);
        Location = (TextView)findViewById(R.id.tvLocation);

        Location Coor = Globals.Coordinates;
        if (Coor != null){
            Location.setText("Latitude:"+Coor.getLatitude()+" \n Longitude: "+Coor.getLongitude());
        }


        Create = (Button)findViewById(R.id.btnCreate);
        Cancel = (Button)findViewById(R.id.btnCancel);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    //upload data to database
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validate(){
        boolean result = false;

        String name = Name.getText().toString();
        String lastname = LastName.getText().toString();
        String phone = PhoneNumber.getText().toString();
        String email = EmailAddress.getText().toString();
        String password = Password.getText().toString();
        String location = Location.getText().toString();


        if(name.isEmpty() || lastname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty()){
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }


    //Hide keyboard when you click anywhere on the screen
    //Note: I added this line 'android:onClick="myMethod"' in the respective .xml file.
    public void myMethod(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
