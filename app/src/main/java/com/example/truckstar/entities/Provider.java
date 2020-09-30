package com.example.truckstar.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "provider")
public class Provider implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "cnpj")
    private String cnpj;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "uf")
    private String uf;

    public Provider(){}

    public Provider(String name, String cnpj, String city, String uf) {
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
        this.uf = uf;
    }

    public Provider(long id, String name, String cnpj, String city, String uf) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
        this.uf = uf;
    }

    protected Provider(Parcel in) {
        id = in.readLong();
        name = in.readString();
        cnpj = in.readString();
        city = in.readString();
        uf = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(cnpj);
        dest.writeString(city);
        dest.writeString(uf);
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {

        @Override
        public Provider createFromParcel(Parcel in) {
            Provider p = new Provider();
            p.setId(in.readLong());
            p.setName(in.readString());
            p.setCnpj(in.readString());
            p.setCity(in.readString());
            p.setUf(in.readString());
            return p;
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCityUf() {
        return "Localização: " + city + "-" + uf;
    }
}
