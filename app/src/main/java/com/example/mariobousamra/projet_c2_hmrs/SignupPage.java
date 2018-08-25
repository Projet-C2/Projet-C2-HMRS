package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {


    private EditText Name, LastName, PhoneNumber, EmailAddress, Password;
    private TextView Location;
    private Button Create, Cancel;


    private FirebaseAuth firebaseAuth;

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

        firebaseAuth = FirebaseAuth.getInstance();

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(validate()){
                    //upload data to database
                    String user_email =  EmailAddress.getText().toString().trim();
                    String user_password = Password.getText().toString().trim();

                    //OnCopmlete is used to do something when the process of adding data is completed
                    /*firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });*/
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(SignupPage.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                                //Toast.makeText(SignupPage.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(SignupPage.this, MainActivity.class));
                            }else{
                                Toast.makeText(SignupPage.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                //}
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


    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupPage.this, MainActivity.class));
                        Toast.makeText(SignupPage.this, "Successfully Registered, Verification email sent", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SignupPage.this,"Verification email hasn't been sent", Toast.LENGTH_SHORT);
                    }
                }
            });
        }
    }


    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //Users may have similar Names, so instead we use a unique UUid for each user
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        //provide the values
        myRef.setValue();
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
