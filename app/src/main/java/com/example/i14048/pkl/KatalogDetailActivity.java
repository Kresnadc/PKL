package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i14048.pkl.db.KatalogDBHandler;

public class KatalogDetailActivity extends AppCompatActivity {
    private String idSelectedKatalog;
    private String accountSelectedKatalog;
    private final String keySelectedPreference = "idSelectedKatalog";
    private KatalogDBHandler dbHandler = new KatalogDBHandler(this);

    EditText namaProdukText;
    EditText hargaPokokText;
    EditText hargaJualText;
    Button addBtn;
    Button saveBtn;
    Button backBtn;

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
        SharedPreferences selectedKatalog = getSharedPreferences("SelectedKatalogSession", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_katalog_detail);
        if (selectedKatalog != null) {
            this.idSelectedKatalog = selectedKatalog.getString(keySelectedPreference, "");
        } else {
            this.idSelectedKatalog = "";
        }
        prepareLayourKatalogDetail();
    }

    private void prepareLayourKatalogDetail() {
        this.namaProdukText = (EditText) findViewById(R.id.namaProdukEditTextKatalogDetail);
        this.hargaPokokText = (EditText) findViewById(R.id.hargaPokokEditTextKatalogDetail);
        this.hargaJualText = (EditText) findViewById(R.id.hargaJualEditTextKatalogDetail);
        this.addBtn = (Button) findViewById(R.id.addProductButtonKatalogDetail);
        this.saveBtn = (Button) findViewById(R.id.saveButtonKatalogDetail);
        this.backBtn = (Button) findViewById(R.id.backButtonKatalogDetail);
        ContentValues selectedKatalog = this.dbHandler.getKatalogById(this.idSelectedKatalog);
        if (!idSelectedKatalog.equalsIgnoreCase("")) {
            this.namaProdukText.setText(selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_PRODUCTNAME));
            this.hargaPokokText.setText(selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_BASEPRICE));
            this.hargaJualText.setText(selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_SELLPRICE));
            this.accountSelectedKatalog = selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_ACCOUNTNAME);
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idSelectedKatalog.equals("")) {
                    if (saveActionBtn() > 0) {
                        Toast.makeText(getApplicationContext(), "Product Updated...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Save Failed, Please contact administrator.", Toast.LENGTH_SHORT).show();
                    }
                }
                SharedPreferences preferences = getSharedPreferences("SelectedKatalogSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(KatalogDetailActivity.this, KatalogDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idSelectedKatalog.equals("")) {//Add Produk
                    if (addActionBtn()) {
                        Toast.makeText(getApplicationContext(), "Product added...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Adding Failed, Please contact administrator.", Toast.LENGTH_SHORT).show();
                    }
                } else { //Update Produk
                    if (saveActionBtn() > 0) {
                        Toast.makeText(getApplicationContext(), "Product Updated...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KatalogDetailActivity.this, KatalogActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Save Failed, Please contact administrator.", Toast.LENGTH_SHORT).show();
                    }
                }


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

    private int saveActionBtn() {
        String namaStr = namaProdukText.getText().toString();
        String pokokStr = hargaPokokText.getText().toString();
        String jualStr = hargaJualText.getText().toString();
        return this.dbHandler.updateKatalog(Integer.parseInt(this.idSelectedKatalog),
                this.accountSelectedKatalog, namaStr,
                Integer.parseInt(pokokStr), Integer.parseInt(jualStr));
    }

    private boolean addActionBtn() {
        String namaStr = namaProdukText.getText().toString();
        String pokokStr = hargaPokokText.getText().toString();
        String jualStr = hargaJualText.getText().toString();
        ContentValues accountInfo = SessionHandler.getActiveSession(this);
        return this.dbHandler.insertKatalog(accountInfo.getAsString("email"), namaStr,
                Integer.parseInt(pokokStr), Integer.parseInt(jualStr));
    }
}
