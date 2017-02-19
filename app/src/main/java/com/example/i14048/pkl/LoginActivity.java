package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.editTextUserName);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        Button btnReg = (Button) findViewById(R.id.buttonRegistrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0){
                if(login()) {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, KatalogActivity.class);
                    Bundle b = new Bundle();
                    b.putString("UserName", userNameEditText.getText().toString());
                    i.putExtras(b);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Toast.makeText(getApplicationContext(), "Please Fill Registration Form", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean login(){
        boolean isLoginSuccess = false;
        ContentValues AccountInfo = new AccountDBHandler(this).getAccount(getUserNameText(), getPasswordText());
        if(AccountInfo.get("email") == null){
            Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
            return isLoginSuccess;
        }else if(AccountInfo.getAsString("email").equalsIgnoreCase(getUserNameText())){
            return true;
        }else{
            return false;
        }
    }

    private String getUserNameText() {
        return this.userNameEditText.getText().toString().trim();
    }

    private String getPasswordText() {
        return this.passwordEditText.getText().toString().trim();
    }

}
