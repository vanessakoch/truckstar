package com.example.truckstar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truckstar.R;
import com.example.truckstar.entities.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<User> userList;
    public final static int REQUEST_HOME = 1;
    public final static int REQUEST_PROVIDER = 2;
    public final static int REQUEST_ADDPROVIDER = 3;
    public final static int REQUEST_TRIP = 4;
    private boolean loginExists;

    EditText inputLogin;
    EditText inputPassword;
    CheckBox checkRemember;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLogin = (EditText) findViewById(R.id.inputLogin);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        checkRemember = (CheckBox) findViewById(R.id.checkRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        userList = new ArrayList<User>();
        User user = new User("Vanessa", "vanessa", "123");
        userList.add(user);

    }

    public void onClickLogin(View v) {
        if (inputLogin.getText().length() == 0 || inputPassword.getText().length() == 0) {
            Toast.makeText(getApplication(), "É necessário uma conta para entrar!", Toast.LENGTH_LONG).show();
        }else{
            for(User user: userList) {
                if(inputLogin.getText().toString().equals(user.getNickname()) &&
                        inputPassword.getText().toString().equals(user.getPassword())) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("request_code", REQUEST_HOME);
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_HOME);
                    loginExists = true;

                    Toast.makeText(getApplication(),
                            "Seja bem vindo(a) " + user.getName(),
                            Toast.LENGTH_LONG).show();
                }
            }

            if(!loginExists){
                Toast.makeText(getApplication(),
                        "Usuário e/ou Senha incorretos!",
                        Toast.LENGTH_LONG).show();
            }

        }
    }




}