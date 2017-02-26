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

import com.example.i14048.pkl.db.AccountDBHandler;
import com.example.i14048.pkl.db.KatalogDBHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class RecapActivity extends AppCompatActivity {
    private ContentValues accountInfoSession;
    private ListView recapList;
    private TextView totalRecap;
    private Button transactionBtn;
    private Button katalogBtn;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionHandler.checkSession(this)) {
            accountInfoSession = SessionHandler.getActiveSession(this);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_recap);
         /*
            ListView Handler
         */
        this.recapList = (ListView) findViewById(R.id.recapListView);
        final RecapList katalogListAdapter = createListAdapter();
        this.recapList.setAdapter(katalogListAdapter);
        /*
            ListView End here!
        */

        this.totalRecap = (TextView) findViewById(R.id.totalCountTransaction);
        this.totalRecap.setText(katalogListAdapter.totalHarga()+"");

        /*
        Button Listener
        */
        this.transactionBtn = (Button) findViewById(R.id.transactionButtonRecap);
        this.katalogBtn = (Button) findViewById(R.id.katalogButtonRecap);
        this.exitBtn = (Button) findViewById(R.id.exitButtonRecap);
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecapActivity.this, TransactionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        katalogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecapActivity.this, KatalogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecapActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
    }
    private RecapList createListAdapter() {
        ArrayList<ContentValues> katalogInfo = new KatalogDBHandler(this).getKatalog(accountInfoSession.getAsString("email"));
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

        ArrayList<Integer> productIdRecap = new ArrayList<Integer>();
        AccountDBHandler dbTransaksi = new AccountDBHandler(this);

        for (int i = 0; i < productId.length; i++) {
            if(dbTransaksi.getTransactionById(""+productId[i]) != null){
                productIdRecap.add(productId[i]);
            }
        }

        KatalogDBHandler dbKatalog = new KatalogDBHandler(this);
        productId = new int[productIdRecap.size()];
        productName = new String[productIdRecap.size()];
        basePrice = new int[productIdRecap.size()];
        sellPrice = new int[productIdRecap.size()];
        int[] quantity = new int[productIdRecap.size()];
        for (int i = 0; i < productIdRecap.size(); i++) {
            ContentValues curCV = dbKatalog.getKatalogById(productIdRecap.get(i)+"");
            productId[i] = curCV.getAsInteger("product_id");
            productName[i] = curCV.getAsString("product_name");
            basePrice[i] = curCV.getAsInteger("base_price");
            sellPrice[i] = curCV.getAsInteger("sell_price");
            quantity[i] = dbTransaksi.getTransactionById(""+productIdRecap.get(i)).getAsInteger("kuantitas");
        }

        return new RecapList(getLayoutInflater(), this, productId, productName, basePrice, sellPrice, productIdStr, quantity);
    }
}
