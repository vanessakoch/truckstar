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
import com.example.truckstar.activities.HomeActivity;
import com.example.truckstar.activities.MainActivity;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.Provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        viewHolder.txtBales.setText(providerList.get(position).getBalesNCash());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), EditProviderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("provider", providerList.get(holder.getAdapterPosition()));
                bundle.putInt("position", holder.getAdapterPosition());
                bundle.putInt("request_code", MainActivity.REQUEST_EDIT_PROVIDER);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, MainActivity.REQUEST_EDIT_PROVIDER);
            }
        });

        viewHolder.btnRemoveProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(holder.getAdapterPosition());
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
                db.providerDao().deleteProvider(recently_removed_provider);
            }
        });

        providerList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.getItemCount());

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.dialog_remove, null);

        Button btnConfirm = (Button) v.findViewById(R.id.btnConfirm);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);

        mBuilder.setView(v);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("Item removido com sucesso", R.drawable.ic_emoticon_smile);
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

    public void insert(final Provider provider) {

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().insertProvider(provider);
            }
        });

        providerList.add(provider);
        notifyItemInserted(getItemCount());
    }

    public void move(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(providerList, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(providerList, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    public void edit(final Provider provider, final int position) {
        providerList.get(position).setName(provider.getName());
        providerList.get(position).setCnpj(provider.getCnpj());
        providerList.get(position).setCity(provider.getCity());
        providerList.get(position).setUf(provider.getUf());
        providerList.get(position).setNBales(provider.getNBales());
        providerList.get(position).setCashBales(provider.getCashBales());

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().updateProvider(provider);
            }
        });
        notifyItemChanged(position);
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.providerDao().deleteAll();
            }
        });
        while (!providerList.isEmpty()){
            providerList.remove(0);
            notifyItemRemoved(0);
            notifyItemRangeChanged(0, this.getItemCount());
        }
    }

    public void makeToast(String text, int image) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) activity.findViewById(R.id.toast_root));
        TextView textToast = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        textToast.setText(text);
        toastImage.setImageResource(image);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static class ProviderViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameProvider;
        TextView txtCnpj;
        TextView txtLocation;
        TextView txtBales;
        ImageView btnRemoveProvider;

        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            txtNameProvider = (TextView) itemView.findViewById(R.id.txtNameProvider);
            txtCnpj = (TextView) itemView.findViewById(R.id.txtCnpj);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            txtBales = (TextView) itemView.findViewById(R.id.txtBales);

            btnRemoveProvider = (ImageView) itemView.findViewById(R.id.btnRemoveProvider);
        }
    }
}