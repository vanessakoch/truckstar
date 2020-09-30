package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.truckstar.R;

public class HomeActivity extends AppCompatActivity {
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

    }

    public void onClickProvider(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", requestCode);
        Intent intent = new Intent(this, ProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
