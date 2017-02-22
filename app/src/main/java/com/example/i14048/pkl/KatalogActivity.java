package com.example.i14048.pkl;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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

public class KatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SessionHandler.checkSession(this)){
            ContentValues accountInfo = SessionHandler.getActiveSession(this);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_katalog);

        TextView welcomeText = (TextView) findViewById(R.id.textViewWelcome);
        ListView katalogList = (ListView) findViewById(R.id.katalogListView);

        Bundle b = getIntent().getExtras();
        welcomeText.setText("Selamat datang: " + b.getString("UserName"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.katalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.action_settings){
            Toast.makeText(KatalogActivity.this, "Menu Setting", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_sort){
            Toast.makeText(KatalogActivity.this, "Sort By (Not Implemented Yet)", Toast.LENGTH_SHORT).show();
            //Do Sort
            return true;
        }else if (id==R.id.action_logout){
            Toast.makeText(KatalogActivity.this, "Anda berhasil Logout", Toast.LENGTH_SHORT).show();
            //Do Logout
            Intent i = new Intent(KatalogActivity.this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        else if (id==R.id.action_exit){
            Toast.makeText(KatalogActivity.this, "Exit", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
