package com.example.truckstar.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;

public class InsertHelperActivity extends AppCompatActivity {
    EditText inputNameHelper;
    EditText inputCpfHelper;
    EditText inputHelperValue;
    Button btnRegistraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_helper);

        inputNameHelper = (EditText) findViewById(R.id.inputNameHelper);
        inputCpfHelper = (EditText) findViewById(R.id.inputCpfHelper);
        inputHelperValue = (EditText) findViewById(R.id.inputHelperValue);
        btnRegistraHelper = (Button) findViewById(R.id.btnRegistraHelper);


    }

}
