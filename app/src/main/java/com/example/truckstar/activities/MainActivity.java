package com.example.truckstar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
            inputLogin.setHintTextColor(ColorStateList.valueOf(Color.RED));
            inputPassword.setHintTextColor(ColorStateList.valueOf(Color.RED));

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
            TextView textToast = layout.findViewById(R.id.toast_text);
            ImageView toastImage = layout.findViewById(R.id.toast_image);
            textToast.setText("É necessário inserir uma conta!");
            toastImage.setImageResource(R.drawable.ic_error_outline);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        } else {
            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.userDao().insertUser(new User("Admin", "truck", "123"));
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

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
                TextView textToast = layout.findViewById(R.id.toast_text);
                ImageView toastImage = layout.findViewById(R.id.toast_image);
                textToast.setText("Seja bem vindo(a) " + user_login.getName());
                toastImage.setImageResource(R.drawable.ic_emoticon_smile);
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
                TextView textToast = layout.findViewById(R.id.toast_text);
                ImageView toastImage = layout.findViewById(R.id.toast_image);
                textToast.setText("Usuário e/ou Senha incorretos!");
                toastImage.setImageResource(R.drawable.ic_error_outline);
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        }
    }
}