package com.example.truckstar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText inputLogin;
    EditText inputPassword;
    CheckBox checkRemember;
    Button btnLogin;
    User user_login;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLogin = (EditText) findViewById(R.id.inputLogin);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        checkRemember = (CheckBox) findViewById(R.id.checkRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        db = AppDatabase.getDatabase(this);
    }

    public void onClickLogin(View v) throws InterruptedException {

        if (inputLogin.getText().length() == 0 || inputPassword.getText().length() == 0) {
            Toast.makeText(getApplication(), "É necessário uma conta para entrar!", Toast.LENGTH_LONG).show();

        } else {
            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.userDao().insertUser(new User("Administrador", "truckstar", "2028"));
                    user_login = db.userDao().getUserAuth(inputLogin.getText().toString(), inputPassword.getText().toString());
                }
            });
            AppDatabase.databaseWriteExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);

            if (user_login != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("request_code", HomeActivity.REQUEST_HOME);
                bundle.putLong("user_id", user_login.getId());
                bundle.putString("user_name", user_login.getName());
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, HomeActivity.REQUEST_HOME);
                Toast.makeText(getApplication(),
                        "Seja bem vindo(a) " + user_login.getName(),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(),
                        "Usuário e/ou Senha incorretos!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}