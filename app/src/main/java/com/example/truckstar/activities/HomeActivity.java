package com.example.truckstar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;

public class HomeActivity extends AppCompatActivity {
    public final static int REQUEST_HOME = 1;
    public final static int REQUEST_ADD_PROVIDER = 2;
    public final static int REQUEST_EDIT_PROVIDER = 3;
    public final static int REQUEST_ADD_SPENDING = 4;
    public final static int REQUEST_ADD_HELPER = 5;
    public final static int REQUEST_ADD_USER = 6;
    public final static int REQUEST_EDIT_USER = 7;
    public final static int REQUEST_USER = 8;
    public final static int REQUEST_PROVIDER = 9;

    int requestCode;
    long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");
        user_id = bundle.getLong("user_id");
    }

    public void onClickProvider(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", requestCode);
        Intent intent = new Intent(this, ProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void onClickUser(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", requestCode);
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void onClickSpend(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", REQUEST_ADD_SPENDING);
        bundle.putLong("user_id", user_id);
        Intent intent = new Intent(this, SpendActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_ADD_SPENDING);
    }
}
