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
    public final static int REQUEST_HOME = 1;
    public final static int REQUEST_ADD_PROVIDER = 2;
    public final static int REQUEST_EDIT_PROVIDER = 3;
    public final static int REQUEST_ADD_SPENDING = 4;
    public final static int REQUEST_ADD_HELPER = 5;
    public final static int REQUEST_ADD_USER = 6;
    public final static int REQUEST_EDIT_USER = 7;
    public final static int REQUEST_PROVIDER = 8;
    public final static int REQUEST_USER = 9;
    public final static int REQUEST_REPORT = 10;
    public final static int REQUEST_RETURN_PDF = 11;

    EditText inputLogin;
    EditText inputPassword;
    Button btnLogin;
    User user_login;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLogin = (EditText) findViewById(R.id.inputLogin);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        db = AppDatabase.getDatabase(this);
    }

    public void onClickLogin(View v) throws InterruptedException {

        if (inputLogin.getText().length() == 0 || inputPassword.getText().length() == 0) {
            inputLogin.setHintTextColor(ColorStateList.valueOf(Color.RED));
            inputPassword.setHintTextColor(ColorStateList.valueOf(Color.RED));

            makeToast("É necessário inserir uma conta!", R.drawable.ic_error_outline);
        } else {
            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if(db.userDao().findByName("Admin") == null) {
                        db.userDao().insertUser(new User("Admin", "truckstar", "2028"));
                    }
                    user_login = db.userDao().getUserAuth(inputLogin.getText().toString(), inputPassword.getText().toString());
                }
            });
            AppDatabase.databaseWriteExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);

            if (user_login != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("request_code", REQUEST_HOME);
                bundle.putLong("user_id", user_login.getId());
                bundle.putString("user_name", user_login.getName());
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_HOME);

                makeToast("Seja bem vindo(a) " + user_login.getName(), R.drawable.ic_emoticon_smile);
            } else {
                makeToast("Usuário e/ou Senha incorretos!", R.drawable.ic_error_outline);
            }
        }
    }

    public void makeToast(String text, int image) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        TextView textToast = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        textToast.setText(text);
        toastImage.setImageResource(image);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}