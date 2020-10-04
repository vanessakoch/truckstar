package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

        String companyName = inputCompanyName.getText().toString();
        String cnpj = inputCnpj.getText().toString();
        String city = inputCity.getText().toString();
        String uf = spinnerUf.getSelectedItem().toString();
        String nBales = inputNBales.getText().toString();
        String cashBales = inputBalesCash.getText().toString();

        if (!companyName.equals("") && !cnpj.equals("") && !city.equals("") && !uf.equals("UF") && !nBales.equals("") && !cashBales.equals("")) {
            provider = new Provider(companyName, cnpj, city, uf, Integer.parseInt(nBales), Float.parseFloat(cashBales));

            bundle.putParcelable("provider", provider);

            Intent returnIntent = new Intent();
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            if (companyName.equals("")){
                inputCompanyName.setHintTextColor(ColorStateList.valueOf(Color.RED));
            }
            if (cnpj.equals("")){
                inputCnpj.setHintTextColor(ColorStateList.valueOf(Color.RED));
            }
            if (city.equals("")){
                inputCity.setHintTextColor(ColorStateList.valueOf(Color.RED));
            }
            if (spinnerUf.getSelectedItem().toString().equals("UF")){
                spinnerUf.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
            }
            if (nBales.equals("")){
                inputNBales.setHintTextColor(ColorStateList.valueOf(Color.RED));
            }
            if (cashBales.equals("")){
                inputBalesCash.setHintTextColor(ColorStateList.valueOf(Color.RED));
            }

            Toast.makeText(getApplication(),
                "Preencha todos os dados antes de concluir!",
                Toast.LENGTH_LONG).show();
        }
    }



    public void onClickReturn(View view){
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}