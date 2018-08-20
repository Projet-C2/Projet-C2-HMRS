package com.example.mariobousamra.projet_c2_hmrs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    }
}
