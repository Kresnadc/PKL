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

import com.example.i14048.pkl.db.AccountDBHandler;
import com.example.i14048.pkl.db.KatalogDBHandler;

public class TransactionDetailActivity extends AppCompatActivity {
    private String idSelectedKatalog;
    private String accountSelectedKatalog;
    private final String keySelectedPreference = "idSelectedKatalog";
    private KatalogDBHandler dbHandler = new KatalogDBHandler(this);
    private AccountDBHandler dbHandlerTransaction = new AccountDBHandler(this);

    EditText namaProdukText;
    EditText hargaJualText;
    EditText kuantitasText;
    Button proceedBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionHandler.checkSession(this)) {
            ContentValues accountInfo = SessionHandler.getActiveSession(this);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        SharedPreferences selectedKatalog = getSharedPreferences("SelectedTransactionSession", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_transaction_detail);
        if (selectedKatalog != null) {
            this.idSelectedKatalog = selectedKatalog.getString(this.keySelectedPreference, "");
            if(idSelectedKatalog == null ){
                Toast.makeText(getApplicationContext(), "No preference Found", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Katalog Detail Found", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        prepareLayoutTransactionDetail();

    }

    private void prepareLayoutTransactionDetail() {
        this.namaProdukText = (EditText) findViewById(R.id.namaProdukEditTextTransactionDetail);
        this.hargaJualText = (EditText) findViewById(R.id.hargaJualEditTextTransactionDetail);
        this.kuantitasText = (EditText) findViewById(R.id.quantityEditTextTransactionDetail);
        this.proceedBtn = (Button) findViewById(R.id.proceedButtonTransactionDetail);
        this.cancelBtn = (Button) findViewById(R.id.cancelButtonTransactionDetail);

        ContentValues selectedKatalog = this.dbHandler.getKatalogById(this.idSelectedKatalog);
        final ContentValues selectedKatalogTransaction = this.dbHandlerTransaction.getTransactionById(this.idSelectedKatalog);
        if (namaProdukText == null) {
            Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_SHORT).show();
        }

        this.namaProdukText.setText(selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_PRODUCTNAME));
        this.hargaJualText.setText(selectedKatalog.getAsString(this.dbHandler.COLUMN_NAME_SELLPRICE));

        if (selectedKatalogTransaction == null ) {
            this.kuantitasText.setText("0");
        }else{
            this.kuantitasText.setText(selectedKatalogTransaction.getAsString(this.dbHandlerTransaction.COLUMN_NAME_QUANTITY));
        }

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedKatalogTransaction == null) { //Add Transaction
                    if (addTransaction()) {
                        Toast.makeText(getApplicationContext(), "Transaction added...", Toast.LENGTH_SHORT).show();
                        clearProductPreferences();
                        Intent intent = new Intent(TransactionDetailActivity.this, TransactionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Adding Failed, Please contact administrator.", Toast.LENGTH_SHORT).show();
                    }
                } else { //Update Transaction
                    if (updateTransaction() > 0) {
                        Toast.makeText(getApplicationContext(), "Transaction Quantity Updated...", Toast.LENGTH_SHORT).show();
                        clearProductPreferences();
                        Intent intent = new Intent(TransactionDetailActivity.this, TransactionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Update Quantity Failed, Please contact administrator.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearProductPreferences();
                Intent intent = new Intent(TransactionDetailActivity.this, TransactionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private int updateTransaction() {
        String quantityStr = kuantitasText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedTransactionSession", Context.MODE_PRIVATE);
        return this.dbHandlerTransaction.updateTransactionById(sharedPreferences.getString("idSelectedKatalog", ""),
                Integer.parseInt(quantityStr));
    }

    private boolean addTransaction() {
        String quantityStr = kuantitasText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedTransactionSession", Context.MODE_PRIVATE);
        return this.dbHandlerTransaction.insertTransaction(sharedPreferences.getString("idSelectedKatalog", ""),
                Integer.parseInt(quantityStr));
    }

    private void clearProductPreferences(){
        SharedPreferences preferences = getSharedPreferences("SelectedTransactionSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}