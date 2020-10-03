package com.example.truckstar.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "provider")
public class Provider implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_provider")
    private long id_provider;
    @ColumnInfo(name = "provider_name")
    private String name;
    @ColumnInfo(name = "cnpj")
    private String cnpj;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "uf")
    private String uf;
    @ColumnInfo(name = "nBales")
    private int nBales;
    @ColumnInfo(name = "cashBales")
    private float cashBales;

    public Provider(){}

    @Ignore
    public Provider(String name, String cnpj, String city, String uf, int nBales, float cashBales) {
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
        this.uf = uf;
        this.nBales = nBales;
        this.cashBales = cashBales;
    }

    public Provider(long id, String name, String cnpj, String city, String uf, int nBales, float cashBales) {
        this.id_provider = id;
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
        this.uf = uf;
        this.nBales = nBales;
        this.cashBales = cashBales;
    }

    protected Provider(Parcel in) {
        id_provider = in.readLong();
        name = in.readString();
        cnpj = in.readString();
        city = in.readString();
        uf = in.readString();
        nBales = in.readInt();
        cashBales = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id_provider);
        dest.writeString(name);
        dest.writeString(cnpj);
        dest.writeString(city);
        dest.writeString(uf);
        dest.writeInt(nBales);
        dest.writeFloat(cashBales);
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {

        @Override
        public Provider createFromParcel(Parcel in) {
            Provider p = new Provider();
            p.setId_provider(in.readLong());
            p.setName(in.readString());
            p.setCnpj(in.readString());
            p.setCity(in.readString());
            p.setUf(in.readString());
            p.setNBales(in.readInt());
            p.setCashBales(in.readFloat());

            return p;
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    public long getId_provider() {
        return id_provider;
    }

    public void setId_provider(long id_provider) {
        this.id_provider = id_provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getNBales() {
        return nBales;
    }

    public void setNBales(int nBales) {
        this.nBales = nBales;
    }

    public float getCashBales() {
        return cashBales;
    }

    public void setCashBales(float cashBales) {
        this.cashBales = cashBales;
    }

    public String getCityUf() {
        return "Localização: " + city + " - " + uf;
    }

    public String getBalesNCash() {
        return "No de fardos: " + nBales + " / Valor R$: " + cashBales;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id_provider +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", city='" + city + '\'' +
                ", uf='" + uf + '\'' +
                ", nBales=" + nBales +
                ", cashBales=" + cashBales +
                '}';
    }
}
