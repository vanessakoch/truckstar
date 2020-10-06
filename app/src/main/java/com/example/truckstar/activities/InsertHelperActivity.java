package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Helper;
import com.example.truckstar.entities.Trip;
import com.example.truckstar.entities.TripWithHelpers;

import java.util.concurrent.TimeUnit;

public class InsertHelperActivity extends AppCompatActivity {
    EditText inputNameHelper;
    EditText inputCpfHelper;
    EditText inputHelperValue;
    Button btnRegistraHelper;
    Helper helper;
    long id_trip;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_helper);

        inputNameHelper = (EditText) findViewById(R.id.inputNameHelper);
        inputCpfHelper = (EditText) findViewById(R.id.inputCpfHelper);
        inputHelperValue = (EditText) findViewById(R.id.inputHelperValue);
        btnRegistraHelper = (Button) findViewById(R.id.btnRegistraHelper);

        db = AppDatabase.getDatabase(this);

        Bundle bundle = getIntent().getExtras();
        id_trip = bundle.getLong("id_trip");
    }

    public void onClickRegisterHelper(View view) throws InterruptedException {
        String nameHelper = inputNameHelper.getText().toString();
        String cpfHelper = inputCpfHelper.getText().toString();
        String helperValue = inputHelperValue.getText().toString();

        if(!nameHelper.equals("") && !cpfHelper.equals("") && !helperValue.equals("")) {
            helper = new Helper(nameHelper, id_trip, cpfHelper, Float.parseFloat(helperValue));

            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.helperDao().insertHelper(helper);
                }
            });
            AppDatabase.databaseWriteExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.dialog_layout, null);

            Button btnAddMore = (Button) v.findViewById(R.id.btnAddMore);
            Button btnConfirm = (Button) v.findViewById(R.id.btnConclude);

            mBuilder.setView(v);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeToast("Gastos cadastrados com sucesso!", R.drawable.ic_emoticon_smile);

                    Bundle bundle = new Bundle();
                    Intent returnIntent = new Intent();
                    bundle.putInt("request_code", MainActivity.REQUEST_ADD_HELPER);
                    returnIntent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    dialog.dismiss();
                }
            });

            btnAddMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputNameHelper.setText("");
                    inputCpfHelper.setText("");
                    inputHelperValue.setText("");
                    dialog.dismiss();
                }
            });

        } else {
            inputNameHelper.setHintTextColor(ColorStateList.valueOf(Color.RED));
            inputCpfHelper.setHintTextColor(ColorStateList.valueOf(Color.RED));
            inputHelperValue.setHintTextColor(ColorStateList.valueOf(Color.RED));

            makeToast("Preencha todos os dados antes de concluir!", R.drawable.ic_error_outline);
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
