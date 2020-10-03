package com.example.truckstar.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id_user", childColumns = "userId"),
        @ForeignKey(entity = Provider.class, parentColumns = "id_provider", childColumns = "providerId")
})
public class Trip implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_trip")
    private long id_trip;
    @ColumnInfo(name = "mileage")
    private float mileage;
    @ColumnInfo(name = "providerId", index = true)
    private long providerId;
    @ColumnInfo(name = "userId", index = true)
    private long userId;
    @ColumnInfo(name = "cartValue")
    private float cartValue;
    @ColumnInfo(name = "extraValue")
    private float extraValue;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "hour")
    private String hour;

    public Trip() {}

    @Ignore
    public Trip(float mileage, long providerId, long userId, float cartValue, float extraValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        Date actually_date = calendar.getTime();

        final String full_date = dateFormat.format(actually_date);
        final String current_time = dateFormat_hora.format(actually_date);

        this.mileage = mileage;
        this.providerId = providerId;
        this.userId = userId;
        this.cartValue = cartValue;
        this.extraValue = extraValue;
        this.date = full_date;
        this.hour = current_time;
    }

    public Trip(long id, float mileage, long providerId, long userId, float cartValue, float extraValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        Date actually_date = calendar.getTime();

        final String full_date = dateFormat.format(actually_date);
        final String current_time = dateFormat_hora.format(actually_date);

        this.id_trip = id;
        this.mileage = mileage;
        this.providerId = providerId;
        this.userId = userId;
        this.cartValue = cartValue;
        this.extraValue = extraValue;
        this.date = full_date;
        this.hour = current_time;
    }

    protected Trip(Parcel in) {
        id_trip = in.readLong();
        mileage = in.readFloat();
        providerId = in.readLong();
        userId = in.readLong();
        cartValue = in.readFloat();
        extraValue = in.readFloat();
        date = in.readString();
        hour = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id_trip);
        dest.writeFloat(mileage);
        dest.writeLong(providerId);
        dest.writeLong(userId);
        dest.writeFloat(cartValue);
        dest.writeFloat(extraValue);
        dest.writeString(date);
        dest.writeString(hour);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {

        @Override
        public Trip createFromParcel(Parcel in) {
            Trip t = new Trip();
            t.setId_trip(in.readLong());
            t.setMileage(in.readFloat());
            t.setProviderId(in.readLong());
            t.setUserId(in.readLong());
            t.setCartValue(in.readFloat());
            t.setExtraValue(in.readFloat());
            t.setDate(in.readString());
            t.setHour(in.readString());
            return t;
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };


    public long getId_trip() {
        return id_trip;
    }

    public void setId_trip(long id_trip) {
        this.id_trip = id_trip;
    }

    public float getCartValue() {
        return cartValue;
    }

    public void setCartValue(float cartValue) {
        this.cartValue = cartValue;
    }

    public float getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(float extraValue) {
        this.extraValue = extraValue;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id_trip=" + id_trip +
                ", mileage=" + mileage +
                ", providerId=" + providerId +
                ", userId=" + userId +
                ", cartValue=" + cartValue +
                ", extraValue=" + extraValue +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
