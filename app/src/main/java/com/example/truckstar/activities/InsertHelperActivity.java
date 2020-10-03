package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Helper;
import com.example.truckstar.entities.Trip;
import com.example.truckstar.entities.TripWithHelpers;

public class InsertHelperActivity extends AppCompatActivity {
    EditText inputNameHelper;
    EditText inputCpfHelper;
    EditText inputHelperValue;
    Button btnRegistraHelper;
    Helper helper;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_helper);

        inputNameHelper = (EditText) findViewById(R.id.inputNameHelper);
        inputCpfHelper = (EditText) findViewById(R.id.inputCpfHelper);
        inputHelperValue = (EditText) findViewById(R.id.inputHelperValue);
        btnRegistraHelper = (Button) findViewById(R.id.btnRegistraHelper);

        db = AppDatabase.getDatabase(this);

    }

    public void onClickRegisterHelper(View view){
        String nameHelper = inputNameHelper.getText().toString();
        String cpfHelper = inputCpfHelper.getText().toString();
        float helperValue = Float.parseFloat(inputHelperValue.getText().toString());

        helper = new Helper(nameHelper, 1, cpfHelper, helperValue);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.helperDao().insertHelper(helper);

            }
        });

        TripWithHelpers twh = new TripWithHelpers();
        twh.helpersList.add(helper);

        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    }
