package com.example.i14048.pkl;

import android.app.DatePickerDialog;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i14048.pkl.db.AccountDBHandler;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText nameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText birthEditText;
    private EditText productEditText;

    private Calendar birthCalendar;
    private DatePickerDialog birthDatePickerDialog;
    private Button btnDate;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat fmtDate = new SimpleDateFormat("yyyyMMdd");
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthEditText.setText(fmtDate.format(c.getTime()));
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.emailEditText = (EditText) findViewById(R.id.emailText);
        this.nameEditText = (EditText) findViewById(R.id.nameText);
        this.addressEditText = (EditText) findViewById(R.id.addressText);
        this.phoneEditText = (EditText) findViewById(R.id.phoneText);
        this.birthEditText = (EditText) findViewById(R.id.birthText);
        this.productEditText = (EditText) findViewById(R.id.productText);

        Button btnCancel = (Button) findViewById(R.id.cancelBtn);
        Button btnSave = (Button) findViewById(R.id.saveBtn);
        this.btnDate = (Button) findViewById(R.id.dateButtonRegister);
        this.birthEditText.setEnabled(false);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new DatePickerDialog(RegisterActivity.this, d, c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                Bundle b = new Bundle();
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(registerAccount()){
                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Register Failed, Please Contact Administrator!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean registerAccount() {

        boolean isRegistered = new AccountDBHandler(this).insertNewAccoount(getEmailText(), getNameText(), getAddressText(), getPhoneText(), getBirthText(), getProductText());
        return isRegistered;
    }


    private String getEmailText() {
        return this.emailEditText.getText().toString().trim();
    }

    private String getNameText() {
        return this.nameEditText.getText().toString().trim();
    }

    private String getAddressText() {
        return this.addressEditText.getText().toString().trim();
    }

    private String getPhoneText() {
        return this.phoneEditText.getText().toString().trim();
    }

    private String getBirthText() {
        return this.birthEditText.getText().toString().trim();
    }

    private String getProductText() {
        return this.productEditText.getText().toString().trim();
    }

}
