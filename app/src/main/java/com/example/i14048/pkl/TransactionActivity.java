package com.example.i14048.pkl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionActivity extends AppCompatActivity {
    private TextView titleText;
    private Button recapBtn;
    private Button katalogBtn;
    private Button exitBtn;

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
        setContentView(R.layout.activity_transaction);

         /*
            ListView Handler
         */
        final KatalogList katalogListAdapter = createListAdapter();
        this.katalogList.setAdapter(katalogListAdapter);
        katalogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idSelectedKatalog = (String) katalogList.getItemAtPosition(position);
                SharedPreferences sharedPreferences = getSharedPreferences("SelectedKatalogSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("idSelectedKatalog", idSelectedKatalog);
                editor.commit();
                Intent intent = new Intent(KatalogActivity.this, KatalogDetailActivity.class);
                Toast.makeText(getApplicationContext(), idSelectedKatalog, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        /*
            ListView End here!
        */
        /*
            Button Listener
         */
        this.addProductBtn = (Button) findViewById(R.id.addProductButtonKatalog);
        this.transactionBtn = (Button) findViewById(R.id.transactionButtonKatalog);
        this.exitBtn = (Button) findViewById(R.id.exitButtonKatalog);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KatalogActivity.this, KatalogDetailActivity.class);
                startActivity(intent);
            }
        });
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KatalogActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(KatalogActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        titleText = findViewById(R.id.)
        ContentValues accountInfo = SessionHandler.getActiveSession(this);
        titleText.setText("Selamat datang: " + accountInfo.getAsString("email"));


    }
}
