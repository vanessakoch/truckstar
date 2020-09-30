package com.example.truckstar.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.R;
import com.example.truckstar.entities.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter {

    AppCompatActivity activity;
    List<Provider> providerList;

    public ProviderAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.providerList = new ArrayList<Provider>();

        providerList.add(new Provider("aa", "a", "a", "a"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIAaaaaaaaaaaaaaaaaaaaaaaaaaaa", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Canoinhas", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "Porto União", "SC"));
        providerList.add(new Provider("AAAA BBBBBBB EEEE E CIA", "001003000-19", "União da Vitória", "PR"));

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ProviderViewHolder viewHolder = new ProviderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ProviderViewHolder viewHolder = (ProviderViewHolder) holder;
        viewHolder.txtNameProvider.setText(providerList.get(position).getName());
        viewHolder.txtCnpj.setText(providerList.get(position).getCnpj());
        viewHolder.txtLocation.setText(providerList.get(position).getCityUf());

        //viewHolder.btnEditProvider
        //viewHolder.btnRemoveProvider

    }

    @Override
    public int getItemCount() {
        return providerList.size();
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