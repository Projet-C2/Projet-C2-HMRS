package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView ForgotPassword;
    private TextView SignUp;
    private Button Login;
    private Button BackToMain;
    private LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPass);
        ForgotPassword = (TextView)findViewById(R.id.tvForgotPass);

        Login = (Button)findViewById(R.id.btnLogin);
        SignUp = (TextView)findViewById(R.id.tvSignUp);

        BackToMain = (Button)findViewById(R.id.btnBackToMain);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();//Name.getText().toString(), Password.getText().toString());
            }
        });

        BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void validate(){//String username, String Password){
        //if( (username == "admin") && (Password == "1234") ){
        Intent intent = new Intent(LoginPage.this, SignupPage.class);
        startActivity(intent);
        //}
    }


    public void myMethod(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
