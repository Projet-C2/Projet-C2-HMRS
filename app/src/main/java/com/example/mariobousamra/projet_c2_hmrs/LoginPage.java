package com.example.mariobousamra.projet_c2_hmrs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
