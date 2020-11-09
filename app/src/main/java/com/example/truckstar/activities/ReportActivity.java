package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.TripWithData;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ReportActivity extends AppCompatActivity {
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Button btnReport;
    private Button btnReportDay;
    private List<TripWithData> tripList;
    private AppDatabase db;
    private TextView txtTitleToolbar;

    private static final int STORAGE_CODE = 1000;

    private String[]header={"Data/Hora", "Motorista", "Destino", "Localização", "No Fardos",
            "R$ Fardos", "Total Km", "Carrinho", "Extra", "Chapas", "Total"};
    private TemplatePDF templatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnReportDay = (Button) findViewById(R.id.btnReportDay);
        txtTitleToolbar = (TextView) findViewById(R.id.txtTitleToolbar);

        txtTitleToolbar.setText("Emitir Relatórios");

        db = AppDatabase.getDatabase(this);
        tripList = new ArrayList<TripWithData>();

        if(tripList.size() > 0) {
            for (TripWithData t : tripList) {
                tripList.remove(t);
            }
        }
    }

    public void onClickCreateReportWithAll(View view) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<TripWithData> list = db.tripDao().getTripWithData();
                for(TripWithData t : list) {
                    tripList.add(t);
                }
            }
        });

        checkPermission("Relatório gerado com todos os dados da aplicação");
    }

    public void onClickCreateReportByDay(View view) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<TripWithData> list = db.tripDao().getTripWithDataByDate(
                        spinnerDay.getSelectedItem().toString() + "-" + spinnerMonth.getSelectedItem().toString()
                                + "-" + spinnerYear.getSelectedItem().toString());
                for(TripWithData t : list) {
                    tripList.add(t);
                }
            }
        });

        checkPermission("Relatório gerado com dados do dia " +
                spinnerDay.getSelectedItem().toString() + "/" + spinnerMonth.getSelectedItem().toString() + "/" + spinnerYear.getSelectedItem().toString());
    }

    public void checkPermission(String typeReport) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permissions = {WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
                finish();
            }
            else {
                onCreateDocumentPdf(typeReport);
            }
        }
        else {
            onCreateDocumentPdf(typeReport);
        }
    }

    public void onCreateDocumentPdf(String typeReport) {
        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("Viagens", "Gastos", "Vanessa");
        templatePDF.addTitles("Relatório de Gastos das Viagens", "Truck Star Equipamentos");
        templatePDF.addParagraph(typeReport);
        templatePDF.createTable(header, getTrips());
        templatePDF.closeDocument();

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_pdf_view, null);

        Button btnLocal = (Button) v.findViewById(R.id.btnLocal);
        Button btnAdobe = (Button) v.findViewById(R.id.btnAdobe);

        mBuilder.setView(v);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLocal();
                finish();
                dialog.dismiss();
            }
        });

        btnAdobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAdobe();
                finish();
                dialog.dismiss();
            }
        });
    }

    public void viewLocal() {
        templatePDF.viewPDF();
    }

    public void viewAdobe() {
        templatePDF.viewApp();
    }

    private ArrayList<String[]> getTrips(){
        ArrayList<String[]> rows = new ArrayList<>();

        for(TripWithData t : tripList) {
            float total = t.provider.getCashBales() + t.trip.getCartValue() + t.trip.getExtraValue() + t.getTotalPayHelpers();
                    rows.add(new String[]{
                    t.trip.getDate() + " " + t.trip.getHour(), t.user.getName(), t.provider.getName() + "\n\n" + t.provider.getCnpj(),
                    t.provider.getCity() + " / " + t.provider.getUf(), String.valueOf(t.provider.getNBales()), "R$ " + t.provider.getCashBales(),
                    t.trip.getMileage() + "km", "R$ " + t.trip.getCartValue(), "R$ " + t.trip.getExtraValue(), t.getHelpers(),
                    "R$ " + total
            });

        }

        return rows;
    }

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {
                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }
            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
    }

    public void onClickReturn(View view) {
        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}