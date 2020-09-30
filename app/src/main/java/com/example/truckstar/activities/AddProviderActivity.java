package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.entities.Provider;

public class AddProviderActivity extends AppCompatActivity {
    EditText inputCompanyName;
    EditText inputCnpj;
    EditText inputCity;
    EditText inputUf;
    Button btnRegisterProvider;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprovider);

        inputCompanyName = (EditText) findViewById(R.id.inputCompanyName);
        inputCnpj = (EditText) findViewById(R.id.inputCnpj);
        inputCity = (EditText) findViewById(R.id.inputCity);
        inputUf = (EditText) findViewById(R.id.inputUf);
        btnRegisterProvider = (Button) findViewById(R.id.btnRegisterProvider);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

    }

    public void onClickInputProvider(View view){
        Bundle bundle = new Bundle();

        String name = inputCompanyName.getText().toString();
        String cnpj = inputCnpj.getText().toString();
        String city = inputCity.getText().toString();
        String uf = inputUf.getText().toString();

        Provider provider = new Provider(name,cnpj,city,uf);
        bundle.putParcelable("provider", provider);

        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onClickReturn(View view){
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
