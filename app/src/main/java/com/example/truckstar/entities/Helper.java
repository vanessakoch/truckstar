package com.example.truckstar.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(foreignKeys = {
        @ForeignKey(entity = Trip.class, parentColumns = "id_trip", childColumns = "tripId", onDelete = CASCADE),
})
public class Helper implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_helper")
    private long id_helper;
    @ColumnInfo(name = "tripId", index = true)
    private long tripId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "cpf")
    private String cpf;
    @ColumnInfo(name = "cost")
    private float cost;

    public Helper (){}

    @Ignore
    public Helper(String name, long tripId, String cpf, float cost) {
        this.name = name;
        this.tripId = tripId;
        this.cpf = cpf;
        this.cost = cost;
    }

    public Helper(long id , long tripId, String name, String cpf, float cost) {
        this.id_helper = id;
        this.name = name;
        this.tripId = tripId;
        this.cpf = cpf;
        this.cost = cost;
    }

    protected Helper(Parcel in) {
        id_helper = in.readLong();
        name = in.readString();
        tripId = in.readLong();
        cpf = in.readString();
        cost = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id_helper);
        dest.writeString(name);
        dest.writeLong(tripId);
        dest.writeString(cpf);
        dest.writeFloat(cost);
    }

    public static final Creator<Helper> CREATOR = new Creator<Helper>() {

        @Override
        public Helper createFromParcel(Parcel in) {
            Helper h = new Helper();
            h.setId_helper(in.readLong());
            h.setName(in.readString());
            h.setTripId(in.readLong());
            h.setCost(in.readFloat());
            h.setCpf(in.readString());
            return h;
        }

        @Override
        public Helper[] newArray(int size) {
            return new Helper[size];
        }
    };

    public long getId_helper() {
        return id_helper;
    }

    public void setId_helper(long id_helper) {
        this.id_helper = id_helper;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Helper{" +
                "tripId=" + tripId +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cost=" + cost +
                '}';
    }
}
