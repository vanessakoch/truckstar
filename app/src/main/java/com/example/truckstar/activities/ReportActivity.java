package com.example.truckstar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.truckstar.R;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.TripWithData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReportActivity extends AppCompatActivity {
    EditText inputReport;
    EditText inputReport2;
    Button btnReport;
    List<TripWithData> tripList;
    AppDatabase db;

    private String[]header={"Data/Hora", "Motorista", "Destino", "Localização", "No Fardos",
            "Valor Fardos", "Total Km", "Carrinho", "Extra", "Chapas", "Total"};
    private TemplatePDF templatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        inputReport = (EditText) findViewById(R.id.inputReport);
        inputReport2 = (EditText) findViewById(R.id.inputReport2);
        btnReport = (Button) findViewById(R.id.btnReport);

        db = AppDatabase.getDatabase(this);
        tripList = new ArrayList<TripWithData>();

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<TripWithData> list = db.tripDao().getTripWithData();
                for(TripWithData t : list) {
                    tripList.add(t);
                }
            }
        });


        try {
            AppDatabase.databaseWriteExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClickCreatePDF(View view) {
        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("Viagens", "Gastos", "Vanessa");
        templatePDF.addTitles("Relatório de Gastos das Viagens", "Truck Star Equipamentos");
        templatePDF.addParagraph("Dados gerados apartir de um banco de dados");
        templatePDF.createTable(header, getTrips());
        templatePDF.closeDocument();

        Bundle bundle = new Bundle();
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private ArrayList<String[]> getTrips(){
        ArrayList<String[]> rows = new ArrayList<>();

        for(TripWithData t : tripList) {
            float total = t.provider.getCashBales() + t.trip.getCartValue() + t.trip.getExtraValue() + t.getTotalPayHelpers();
                    rows.add(new String[]{
                    t.trip.getDate() + " " + t.trip.getHour(), t.user.getName(), t.provider.getName() + "\n\n" + t.provider.getCnpj(),
                    t.provider.getCity() + " / " + t.provider.getUf(), String.valueOf(t.provider.getNBales()), "R$ " + t.provider.getCashBales(),
                    String.valueOf(t.trip.getMileage()) + "km", "R$ " + t.trip.getCartValue(), "R$ " + t.trip.getExtraValue(), t.getHelpers(),
                    "R$ " + total
            });

        }
        return rows;
    }

}