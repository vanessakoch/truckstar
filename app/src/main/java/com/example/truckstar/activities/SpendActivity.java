package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Provider;
import com.example.truckstar.entities.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpendActivity  extends AppCompatActivity {
    Spinner spinnerProvider;
    EditText inputKm;
    EditText inputCart;
    EditText inputCashExtra;
    Button btnRecivied;
    Button btnTripConclude;
    Trip trip;
    long id_trip;
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
        btnTripConclude = (Button) findViewById(R.id.btnTripConclude);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getLong("user_id");

        db = AppDatabase.getDatabase(this);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();

                for(Provider p : db.providerDao().getAll()) {
                    list.add(p.getId_provider() + " - " + p.getName() + " " + p.getCity() + "/" + p.getUf() +
                           " Total R$: " + p.getCashBales());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item, list);

                spinnerProvider.setAdapter(adapter);

            }
        });
    }

    public void onClickRegisterSpendWithHelper(View view) throws InterruptedException {
        if(spinnerProvider.getSelectedItem() != null) {
            String provider = spinnerProvider.getSelectedItem().toString();
            String arrayProvider[] = provider.split(" ");

            String mileage = inputKm.getText().toString();
            String cart = inputCart.getText().toString();
            String extra = inputCashExtra.getText().toString();

            if (!mileage.equals("") && !cart.equals("") && !extra.equals("")) {
                trip = new Trip(Float.parseFloat(mileage), Long.parseLong(arrayProvider[0]), user_id, Float.parseFloat(cart), Float.parseFloat(extra));

                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.tripDao().insertTrip(trip);
                        id_trip = db.tripDao().idOfLastTrip();
                    }
                });
                AppDatabase.databaseWriteExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);

                Bundle bundle = new Bundle();
                bundle.putInt("request_code", HomeActivity.REQUEST_ADD_HELPER);
                bundle.putLong("id_trip", id_trip);
                Intent intent = new Intent(this, InsertHelperActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, HomeActivity.REQUEST_ADD_HELPER);
            } else {
                colorError(mileage, cart, extra);
            }
        } else {
            spinnerProvider.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

            Toast.makeText(getApplication(),
                    "Volte e cadastre uma empresa destino antes!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRegisterSpend(View view) {
        if(spinnerProvider.getSelectedItem() != null) {
            String provider = spinnerProvider.getSelectedItem().toString();
            String arrayProvider[] = provider.split(" ");

            String mileage = inputKm.getText().toString();
            String cart = inputCart.getText().toString();
            String extra = inputCashExtra.getText().toString();

            if (!mileage.equals("") && !cart.equals("") && !extra.equals("")) {
                trip = new Trip(Float.parseFloat(mileage), Long.parseLong(arrayProvider[0]), user_id, Float.parseFloat(cart), Float.parseFloat(extra));

                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.tripDao().insertTrip(trip);
                        id_trip = db.tripDao().idOfLastTrip();
                    }
                });

                Toast.makeText(view.getContext(), "Viagem cadastrada com sucesso! ", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                Intent returnIntent = new Intent();
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                colorError(mileage, cart, extra);
            }
        } else {
            spinnerProvider.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

            Toast.makeText(getApplication(),
                    "Volte e cadastre uma empresa destino antes!",
                    Toast.LENGTH_LONG).show();
        }

    }


    public void colorError(String mileage, String cart, String extra) {
        if (mileage.equals("")){
            inputKm.setHintTextColor(ColorStateList.valueOf(Color.RED));
        }
        if (cart.equals("")) {
            inputCart.setHintTextColor(ColorStateList.valueOf(Color.RED));
        }

        if (spinnerProvider.getSelectedItem().toString().equals("")){
            spinnerProvider.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        }

        if (extra.equals("")) {
            inputCashExtra.setHintTextColor(ColorStateList.valueOf(Color.RED));
        }

        Toast.makeText(getApplication(),
                "Preencha todos os dados antes de concluir!",
                Toast.LENGTH_LONG).show();
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == HomeActivity.REQUEST_ADD_HELPER) {
            Bundle bundle = new Bundle();
            Intent returnIntent = new Intent();
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
