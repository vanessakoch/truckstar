package com.example.truckstar.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.R;
import com.example.truckstar.activities.EditProviderActivity;
import com.example.truckstar.activities.MainActivity;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter {

    List<Provider> providerList;
    private int recently_removed_position;
    private Provider recently_removed_provider;
    AppCompatActivity activity;
    AppDatabase db;

    public ProviderAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.providerList = new ArrayList<Provider>();
        db = AppDatabase.getDatabase(activity);


        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Provider> list = db.providerDao().getAll();
                for(Provider p : list)
                    providerList.add(p);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ProviderViewHolder viewHolder = new ProviderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ProviderViewHolder viewHolder = (ProviderViewHolder) holder;
        viewHolder.txtNameProvider.setText(providerList.get(position).getName());
        viewHolder.txtCnpj.setText(providerList.get(position).getCnpj());
        viewHolder.txtLocation.setText(providerList.get(position).getCityUf());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), EditProviderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("provider", providerList.get(holder.getAdapterPosition()));
                bundle.putInt("position", holder.getAdapterPosition());
                bundle.putInt("request_code", MainActivity.REQUEST_EDITPROVIDER);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, MainActivity.REQUEST_EDITPROVIDER);
            }
        });

    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    public void remove(final int position) {
        recently_removed_position = position;
        recently_removed_provider = providerList.get(position);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().deleteProvider(db.providerDao().findById(recently_removed_position));
            }
        });

        providerList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.getItemCount());

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.dialog_remove, null);

        Button btnSubmit = (Button) v.findViewById(R.id.btnConfirma);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancela);

        mBuilder.setView(v);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Removido com sucesso! ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.providerDao().insertProvider(recently_removed_provider);
                    }
                });

                providerList.add(recently_removed_position, recently_removed_provider);
                notifyItemInserted(recently_removed_position);
                dialog.dismiss();
            }
        });
    }

    public void insert(final Provider provider){

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().insertProvider(provider);
                providerList.add(provider);
            }
        });

        notifyItemInserted(getItemCount());
    }

    public void edit(final Provider provider, final int position) {

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().updateProvider(provider);
                providerList.get(position).setName(provider.getName());
                providerList.get(position).setCnpj(provider.getCnpj());
                providerList.get(position).setCity(provider.getCity());
                providerList.get(position).setUf(provider.getUf());
            }
        });

        notifyItemChanged(position);
    }

    public static class ProviderViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameProvider;
        TextView txtCnpj;
        TextView txtLocation;
        ImageView btnRemoveProvider;

        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            txtNameProvider = (TextView) itemView.findViewById(R.id.txtNameProvider);
            txtCnpj = (TextView) itemView.findViewById(R.id.txtCnpj);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            btnRemoveProvider = (ImageView) itemView.findViewById(R.id.btnRemoveProvider);
        }
    }
}