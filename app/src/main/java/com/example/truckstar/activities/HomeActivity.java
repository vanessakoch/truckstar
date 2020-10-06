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
        bundle.putInt("request_code", MainActivity.REQUEST_PROVIDER);
        Intent intent = new Intent(this, ProviderActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, MainActivity.REQUEST_PROVIDER);
    }

    public void onClickReport(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", MainActivity.REQUEST_REPORT);
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, MainActivity.REQUEST_REPORT);
    }

    public void onClickUser(View view){
        if(user_name.equals("Admin")) {
            Bundle bundle = new Bundle();
            bundle.putInt("request_code", MainActivity.REQUEST_USER);
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, MainActivity.REQUEST_USER);
        } else {
            makeToast("Você não tem permissão para acessar essa tela!", R.drawable.ic_error_outline);
        }
    }

    public void onClickSpend(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", MainActivity.REQUEST_ADD_SPENDING);
        bundle.putLong("user_id", user_id);
        Intent intent = new Intent(this, SpendActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, MainActivity.REQUEST_ADD_SPENDING);
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
