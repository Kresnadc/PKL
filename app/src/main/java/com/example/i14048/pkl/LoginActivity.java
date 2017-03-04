package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i14048.pkl.db.AccountDBHandler;
import com.example.i14048.pkl.service.SoapHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        userNameEditText = (EditText) findViewById(R.id.editTextUserName);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        Button btnReg = (Button) findViewById(R.id.buttonRegistrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0){
                login();
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

    private void login(){
        String str = SoapHelper.login(getUserNameText(), getPasswordText());
        if (str.equals("SERVER_ERROR")) {
            Toast.makeText(LoginActivity.this, "Server is unavailable", Toast.LENGTH_SHORT).show();
        } else if (str.equals("CONNECTION_ERROR")) {
            Toast.makeText(LoginActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
        } else{
            Pattern p = Pattern.compile("^\\(\"OK\",\"(.+)\"\\)$");
            Matcher m = p.matcher(str);
            if (m.find()) {
                String sid = m.group(1);
                SharedPreferences pref = this.getSharedPreferences("AccountPKL", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = pref.edit();
                ed.putString("SID", sid);
                ed.commit();
                Bundle b = new Bundle();
                b.putString("UserName", sid);
                Intent intent = new Intent(LoginActivity.this, KatalogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "Cannot Login, Contact Administrator!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getUserNameText() {
        return this.userNameEditText.getText().toString().trim();
    }

    private String getPasswordText() {
        return this.passwordEditText.getText().toString().trim();
    }


}
