package com.example.truckstar.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Trip {
    private float mileage;
    private Provider provider;
    private User user;
    private String date;
    private String hour;
    List<Helper> helpers;

    public Trip(float mileage, Provider provider, User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        Date actually_date = calendar.getTime();

        final String full_date = dateFormat.format(actually_date);
        final String current_time = dateFormat_hora.format(actually_date);

        this.mileage = mileage;
        this.provider = provider;
        this.user = user;
        this.date = full_date;
        this.hour = current_time;
        this.helpers = new ArrayList<Helper>();
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Helper> getHelpers() {
        return helpers;
    }

    public void setHelpers(List<Helper> helpers) {
        this.helpers = helpers;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "mileage=" + mileage +
                ", provider=" + provider +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", helpers=" + helpers +
                '}';
    }
}
