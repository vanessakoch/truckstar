package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.adapters.ProviderAdapter;
import com.example.truckstar.R;
import com.example.truckstar.adapters.TouchHelpProvider;
import com.example.truckstar.entities.Provider;

public class ProviderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        adapter = new ProviderAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProvider);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelpProvider(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == HomeActivity.REQUEST_EDIT_PROVIDER){
            Bundle bundle = data.getExtras();
            Provider provider = (Provider) bundle.getParcelable("provider");
            int position = bundle.getInt("position");
            adapter.edit(provider, position);
        }

        if(resultCode == Activity.RESULT_OK && requestCode == HomeActivity.REQUEST_ADD_PROVIDER){
            Bundle bundle = data.getExtras();
            Provider provider = (Provider) bundle.getParcelable("provider");
            adapter.insert(provider);
        }
        
    }

    public void onClickOpenRegister(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", HomeActivity.REQUEST_ADD_PROVIDER);
        Intent intent = new Intent(this, EditProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, HomeActivity.REQUEST_ADD_PROVIDER);
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}