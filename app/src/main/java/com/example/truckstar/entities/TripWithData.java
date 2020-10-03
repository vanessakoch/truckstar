package com.example.truckstar.entities;

import androidx.room.Embedded;

public class TripWithData {
    @Embedded
    public User user;

    @Embedded
    public Provider provider;

    @Override
    public String toString() {
        return "TripWithData{" +
                ", user=" + user.getName() +
                ", provider=" + provider +
                '}';
    }
}
