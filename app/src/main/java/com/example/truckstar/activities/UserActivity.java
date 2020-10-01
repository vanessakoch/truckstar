package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.R;
import com.example.truckstar.adapters.TouchHelpUser;
import com.example.truckstar.adapters.UserAdapter;
import com.example.truckstar.entities.User;

public class UserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        adapter = new UserAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelpUser(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == HomeActivity.REQUEST_EDIT_USER){
            Bundle bundle = data.getExtras();
            User user = (User) bundle.getParcelable("user");
            int position = bundle.getInt("position");
            adapter.edit(user, position);
        }

        if(resultCode == Activity.RESULT_OK && requestCode == HomeActivity.REQUEST_ADD_USER){
            Bundle bundle = data.getExtras();
            User user = (User) bundle.getParcelable("user");
            adapter.insert(user);
        }

    }

    public void onClickRegisterUser(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", HomeActivity.REQUEST_ADD_USER);
        Intent intent = new Intent(this, EditUserActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, HomeActivity.REQUEST_ADD_USER);
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}