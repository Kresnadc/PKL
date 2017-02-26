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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i14048.pkl.db.KatalogDBHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionActivity extends AppCompatActivity {
    private TextView titleText;
    private Button recapBtn;
    private Button katalogBtn;
    private Button exitBtn;
    private ContentValues accountInfo;
    private ListView katalogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionHandler.checkSession(this)) {
           this. accountInfo = SessionHandler.getActiveSession(this);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_transaction);

         /*
            ListView Handler
         */
        this.katalogList = (ListView) findViewById(R.id.transactionListView);
        final KatalogList katalogListAdapter = createListAdapter();
        this.katalogList.setAdapter(katalogListAdapter);
        katalogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idSelectedKatalog = (String) katalogList.getItemAtPosition(position);
                SharedPreferences sharedPreferences = getSharedPreferences("SelectedTransactionSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("idSelectedKatalog", idSelectedKatalog);
                editor.commit();
                Intent intent = new Intent(TransactionActivity.this, TransactionDetailActivity.class);
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
        this.recapBtn = (Button) findViewById(R.id.recapButtonTransaction);
        this.katalogBtn = (Button) findViewById(R.id.katalogButtonTransaction);
        this.exitBtn = (Button) findViewById(R.id.exitButtonTransaction);
        recapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(KatalogActivity.this, KatalogDetailActivity.class);
//                startActivity(intent);
            }
        });
        katalogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, KatalogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransactionActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        titleText = (TextView) findViewById(R.id.titleTransactionText);
        ContentValues accountInfo = SessionHandler.getActiveSession(this);
        titleText.setText("Transaksi Penjualan PKL : " + accountInfo.getAsString("email"));
    }

    private KatalogList createListAdapter() {
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
}
