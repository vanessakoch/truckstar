package com.example.truckstar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public final static int REQUEST_PROVIDER = 8;
    public final static int REQUEST_USER = 9;

    int requestCode;
    long user_id;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");
        user_id = bundle.getLong("user_id");
        user_name = bundle.getString("user_name");
    }

    public void onClickProvider(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", REQUEST_PROVIDER);
        Intent intent = new Intent(this, ProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PROVIDER);
    }

    public void onClickUser(View view){
        if(user_name.equals("Admin")) {
            Bundle bundle = new Bundle();
            bundle.putInt("request_code", REQUEST_USER);
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_USER);
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
            TextView textToast = layout.findViewById(R.id.toast_text);
            ImageView toastImage = layout.findViewById(R.id.toast_image);
            textToast.setText("Você não tem permissão para acessar essa tela!");
            toastImage.setImageResource(R.drawable.ic_error_outline);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
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
