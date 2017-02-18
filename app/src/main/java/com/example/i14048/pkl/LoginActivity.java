package com.example.i14048.pkl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = (EditText) findViewById(R.id.editTextUserName);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        Button btnReg = (Button) findViewById(R.id.buttonRegistrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0){
                Toast.makeText(getApplicationContext(), "Login berhasil ...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, KatalogActivity.class);
                Bundle b = new Bundle();
                b.putString("UserName", edUserName.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0){
                Toast.makeText(getApplicationContext(), "Silahkan isi form Registrasi", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, KatalogActivity.class);
                Bundle b = new Bundle();
                startActivity(i);
            }
        });
    }
}
