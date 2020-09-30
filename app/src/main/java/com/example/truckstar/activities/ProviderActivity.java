package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.R;
import com.example.truckstar.adapters.ProviderAdapter;

public class ProviderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");
        adapter = new ProviderAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProvider);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void onClickAddProvider(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", requestCode);
        Intent intent = new Intent(this, AddProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}