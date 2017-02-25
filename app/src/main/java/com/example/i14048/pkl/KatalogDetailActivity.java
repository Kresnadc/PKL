package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class KatalogDetailActivity extends AppCompatActivity {

    private Button addProductBtn;
    private Button saveBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionHandler.checkSession(this)) {
            ContentValues accountInfo = SessionHandler.getActiveSession(this);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_katalog_detail);

        /*
            Button
         */
        this.addProductBtn = (Button) findViewById(R.id.addProductButtonKatalogDetail);
        this.saveBtn = (Button) findViewById(R.id.saveButtonKatalogDetail);
        this.backBtn = (Button) findViewById(R.id.backButtonKatalogDetail);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KatalogDetailActivity.this, KatalogDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KatalogDetailActivity.this, TransactionActivity.class);
                startActivity(i);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KatalogDetailActivity.this, KatalogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    
}
