package com.example.mariobousamra.projet_c2_hmrs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
}
