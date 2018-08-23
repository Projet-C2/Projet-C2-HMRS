package com.example.mariobousamra.projet_c2_hmrs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView ForgotPassword;
    private TextView SignUp;
    private Button Login;
    private LinearLayout llayout;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPass);
        ForgotPassword = (TextView)findViewById(R.id.tvForgotPass);

        Login = (Button)findViewById(R.id.btnLogin);
        SignUp = (TextView)findViewById(R.id.tvSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog  =new ProgressDialog(this);

        //Checking if a user is already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            //finish this activity
            //finish();
            Toast.makeText(LoginPage.this,"User already logged in", Toast.LENGTH_SHORT).show();
            //and direct him to the activity
            //startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(Email.getText().toString(), Password.getText().toString());
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();//Name.getText().toString(), Password.getText().toString());
            }
        });


    }


    private void validate(){//String username, String Password){
        //if( (username == "admin") && (Password == "1234") ){
        Intent intent = new Intent(LoginPage.this, SignupPage.class);
        startActivity(intent);
        //}
    }

    private void login(String user_email, String user_password){

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(LoginPage.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginPage.this,"Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void myMethod(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            Toast.makeText(LoginPage.this,"Login Successful", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }else{
            Toast.makeText(this, "kindly verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }




}
