package com.example.truckstar.entities;

public class Trip {
    private int nBurden;
    private float totalBurden;
    private float mileage;
    private Provider provider;
    private Helper helper;
    private User user;
    private String date;
    private String hour;

    public Trip(int nBurden, float totalBurden, float mileage, Provider provider, Helper helper, User user, String date, String hour) {
        this.nBurden = nBurden;
        this.totalBurden = totalBurden;
        this.mileage = mileage;
        this.provider = provider;
        this.helper = helper;
        this.user = user;
        this.date = date;
        this.hour = hour;
    }

    public int getnBurden() {
        return nBurden;
    }

    public void setnBurden(int nBurden) {
        this.nBurden = nBurden;
    }

    public float getTotalBurden() {
        return totalBurden;
    }

    public void setTotalBurden(float totalBurden) {
        this.totalBurden = totalBurden;
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

    public Helper getHelper() {
        return helper;
    }

    public void setHelper(Helper helper) {
        this.helper = helper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
