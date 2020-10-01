package com.example.truckstar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;

public class SpendActivity  extends AppCompatActivity {
    Spinner spinnerProvider;
    EditText inputKm;
    EditText inputCart;
    EditText inputCashExtra;
    TextView txtNHelpers;
    Button btnAddHelper;
    Button btnRecivied;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        spinnerProvider = (Spinner) findViewById(R.id.spinnerProvider);
        inputKm = (EditText) findViewById(R.id.inputKm);
        inputCart = (EditText) findViewById(R.id.inputCart);
        inputCashExtra = (EditText) findViewById(R.id.inputCashExtra);
        txtNHelpers = (TextView) findViewById(R.id.txtNHelpers);
        btnAddHelper = (Button) findViewById(R.id.btnAddHelper);
        btnRecivied = (Button) findViewById(R.id.btnRecivied);
    }

    public void onClickHelper(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", HomeActivity.REQUEST_ADD_HELPER);
        Intent intent = new Intent(this, InsertHelperActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, HomeActivity.REQUEST_ADD_HELPER);
    }
}
