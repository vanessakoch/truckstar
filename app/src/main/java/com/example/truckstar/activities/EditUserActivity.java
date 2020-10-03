package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.entities.User;

public class EditUserActivity extends AppCompatActivity {
    EditText inputNameUser;
    EditText inputNickName;
    EditText inputPassUser;
    Button btnSalvarUser;

    User user;
    int position;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        inputNameUser = (EditText) findViewById(R.id.inputNameUser);
        inputNickName = (EditText) findViewById(R.id.inputNickName);
        inputPassUser = (EditText) findViewById(R.id.inputPassUser);
        btnSalvarUser = (Button) findViewById(R.id.btnSalvarUser);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

        if (requestCode == HomeActivity.REQUEST_EDIT_USER) {
            user = (User) bundle.getParcelable("user");
            position = bundle.getInt("position");

            inputNameUser.setText(user.getName());
            inputNickName.setText(user.getNickname());
            inputPassUser.setText(user.getPassword());

        } else {
            user = new User();
        }
    }

    public void onClickSaveUser(View view){
        Bundle bundle = new Bundle();

        if (requestCode == 7)
            bundle.putInt("position", position);

        user.setName(inputNameUser.getText().toString());
        user.setNickname(inputNickName.getText().toString());
        user.setPassword(inputPassUser.getText().toString());

        bundle.putParcelable("user", user);

        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }



    public void onClickReturn(View view){
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}