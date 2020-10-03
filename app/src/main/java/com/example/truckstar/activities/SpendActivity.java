package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Provider;
import com.example.truckstar.entities.Trip;

import java.util.ArrayList;
import java.util.List;

public class SpendActivity  extends AppCompatActivity {
    Spinner spinnerProvider;
    EditText inputKm;
    EditText inputCart;
    EditText inputCashExtra;
    Button btnRecivied;
    Trip trip;
    AppDatabase db;
    long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        spinnerProvider = (Spinner) findViewById(R.id.spinnerProvider);
        inputKm = (EditText) findViewById(R.id.inputKm);
        inputCart = (EditText) findViewById(R.id.inputCart);
        inputCashExtra = (EditText) findViewById(R.id.inputCashExtra);
        btnRecivied = (Button) findViewById(R.id.btnRecivied);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getLong("user_id");

        db = AppDatabase.getDatabase(this);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();

                for(Provider p : db.providerDao().getAll()) {
                    list.add(p.getId_provider() + " " + p.getName() + " " + p.getCity() + "/" + p.getUf() +
                            " " + p.getNBales() + " fardos" + " Total R$: " + p.getCashBales());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item, list);

                spinnerProvider.setAdapter(adapter);

            }
        });
    }

    public void onClickRegisterSpend(View view){
        String provider = spinnerProvider.getSelectedItem().toString();
        String arrayProvider[] = provider.split(" ");

        float mileage = Float.parseFloat(inputKm.getText().toString());
        float cart = Float.parseFloat(inputCart.getText().toString());
        float extra = Float.parseFloat(inputCashExtra.getText().toString());

        trip = new Trip(mileage, Integer.parseInt(arrayProvider[0]), user_id, cart, extra);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.tripDao().deleteAll();
               // db.tripDao().insertTrip(trip);
                System.out.println(db.tripDao().getAll());
            }
        });

        Bundle bundle = new Bundle();
        bundle.putInt("request_code", HomeActivity.REQUEST_ADD_HELPER);
        Intent intent = new Intent(this, InsertHelperActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, HomeActivity.REQUEST_ADD_HELPER);
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
