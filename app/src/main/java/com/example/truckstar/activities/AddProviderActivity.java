package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;

public class AddProviderActivity extends AppCompatActivity {
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprovider);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

    }

    public void onClickReturn(View view){
        Bundle bundle = new Bundle();

        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
