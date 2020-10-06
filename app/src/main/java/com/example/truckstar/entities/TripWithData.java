package com.example.truckstar.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TripWithData {
    @Embedded
    public User user;

    @Embedded
    public Provider provider;

    @Embedded
    public Trip trip;
    @Relation(
            parentColumn = "id_trip",
            entityColumn = "tripId"
    )
    public List<Helper> helpersList;

    @Override
    public String toString() {
        return "TWD = " + user + " " + provider + " " + trip + " " + helpersList;
    }


    public List<Helper> getHelpersList() {
        return helpersList;
    }

    public String getHelpers() {
        String n = "";
        for(Helper h : helpersList) {
            n += "" + h.getName() + " R$: " + h.getCost() + "\n";
        }

        return n;
    }

    public float getTotalPayHelpers() {
        float total = 0;

        for(Helper h : helpersList) {
            total += h.getCost();
        }

        return total;
    }

}
