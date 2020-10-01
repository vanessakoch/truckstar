package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.entities.Provider;
import com.example.truckstar.R;

public class EditProviderActivity extends AppCompatActivity {

    EditText inputCompanyName;
    EditText inputCnpj;
    EditText inputCity;
    EditText inputNBales;
    EditText inputBalesCash;
    Spinner spinnerUf;
    Button btnRegisterProvider;
    Provider provider;
    int position;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_provider);

        inputCompanyName = (EditText) findViewById(R.id.inputCompanyName);
        inputCnpj = (EditText) findViewById(R.id.inputCnpj);
        inputCity = (EditText) findViewById(R.id.inputCity);
        inputNBales = (EditText) findViewById(R.id.inputNBales);
        inputBalesCash = (EditText) findViewById(R.id.inputBalesCash);
        spinnerUf = (Spinner) findViewById(R.id.spinnerUf);
        btnRegisterProvider = (Button) findViewById(R.id.btnRegisterProvider);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

        if (requestCode == HomeActivity.REQUEST_EDIT_PROVIDER) {
            provider = (Provider) bundle.getParcelable("provider");
            position = bundle.getInt("position");

            inputCompanyName.setText(provider.getName());
            inputCnpj.setText(provider.getCnpj());
            inputCity.setText(provider.getCity());
            inputNBales.setText(String.valueOf(provider.getNBales()));
            inputBalesCash.setText(String.valueOf(provider.getCashBales()));

            Resources res = getResources();
            String[] states = res.getStringArray(R.array.lista_uf);

            for(int i = 1; i < states.length -1 ; i++){
                if(states[i].equals(provider.getUf())) {
                    spinnerUf.setSelection(i);
                }
            }

        } else {
            provider = new Provider();
        }
    }

    public void onClickConclude(View view){
        Bundle bundle = new Bundle();

        if (requestCode == 3)
            bundle.putInt("position", position);

        provider.setName(inputCompanyName.getText().toString());
        provider.setCnpj(inputCnpj.getText().toString());
        provider.setCity(inputCity.getText().toString());
        provider.setUf(spinnerUf.getSelectedItem().toString());
        provider.setNBales(Integer.parseInt(inputNBales.getText().toString()));
        provider.setCashBales(Float.parseFloat(inputBalesCash.getText().toString()));

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