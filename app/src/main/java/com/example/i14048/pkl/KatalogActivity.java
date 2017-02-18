package com.example.i14048.pkl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);

        TextView tv = (TextView) findViewById(R.id.textViewWelcome);
        Bundle b = getIntent().getExtras();
        tv.setText("Selamat datang: " + b.getString("UserName"));

        Button btnTampilKatalog = (Button) findViewById(R.id.buttonTampilKatalog);
        btnTampilKatalog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new AlertDialog.Builder(KatalogActivity.this)
                        .setTitle("KATALOG")
                        .setMessage("Tampilkan Katalog")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int sumthin) {
                                Toast
                                        .makeText(KatalogActivity.this, "Katalog ditampilkan", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });
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
        else if (id==R.id.action_1){
            Toast.makeText(KatalogActivity.this, "Sort By", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_2){
            Toast.makeText(KatalogActivity.this, "Exit", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
            return true;
        }else if (id==R.id.action_3){
            Toast.makeText(KatalogActivity.this, "Anda berhasil Logout", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(KatalogActivity.this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
