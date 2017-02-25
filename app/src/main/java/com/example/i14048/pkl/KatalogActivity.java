package com.example.i14048.pkl;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i14048.pkl.db.KatalogDBHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class KatalogActivity extends AppCompatActivity {
    private TextView welcomeText;
    private ListView katalogList;

    private Button addProductBtn;
    private Button transactionBtn;
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
        setContentView(R.layout.activity_katalog);

        welcomeText = (TextView) findViewById(R.id.textViewWelcome);
        katalogList = (ListView) findViewById(R.id.katalogListView);

        /*
            ListView Handler
         */
        final KatalogList katalogListAdapter = createListAdapter();
        this.katalogList.setAdapter(katalogListAdapter);
        katalogList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
            Button Listner
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

        ContentValues accountInfo = SessionHandler.getActiveSession(this);
        welcomeText.setText("Selamat datang: " + accountInfo.getAsString("email"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.katalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(KatalogActivity.this, "Menu Setting", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_sort) {
            Toast.makeText(KatalogActivity.this, "Sort By (Not Implemented Yet)", Toast.LENGTH_SHORT).show();
            //Do Sort
            return true;
        } else if (id == R.id.action_logout) {
            Toast.makeText(KatalogActivity.this, "Anda berhasil Logout", Toast.LENGTH_SHORT).show();
            //Do Logout
            Intent intent = new Intent(KatalogActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_exit) {
            Toast.makeText(KatalogActivity.this, "Exit", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private KatalogList createListAdapter() {
        ContentValues accountInfo = SessionHandler.getActiveSession(this);
        ArrayList<ContentValues> katalogInfo = new KatalogDBHandler(this).getKatalog(accountInfo.getAsString("email"));
        int sizeKatalogList = katalogInfo.size();
        int[] productId = new int[sizeKatalogList];
        String[] productName = new String[sizeKatalogList];
        int[] basePrice = new int[sizeKatalogList];
        int[] sellPrice = new int[sizeKatalogList];
        for (int i = 0; i < sizeKatalogList; i++) {
            ContentValues curCV = katalogInfo.get(i);
            productId[i] = curCV.getAsInteger("product_id");
            productName[i] = curCV.getAsString("product_name");
            basePrice[i] = curCV.getAsInteger("base_price");
            sellPrice[i] = curCV.getAsInteger("sell_price");
        }
        String[] productIdStr;
        if(productId.length!=0) {
            productIdStr = Arrays.toString(productId).split("[\\[\\]]")[1].split(", ");
        }else{
            productIdStr = new String[sizeKatalogList];
        }
        return new KatalogList(getLayoutInflater(), this, productId, productName, basePrice, sellPrice, productIdStr);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }


    public void addProductBtnOnclick(View view) {

    }
}
