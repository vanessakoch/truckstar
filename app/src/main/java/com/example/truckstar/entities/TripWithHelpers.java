package com.example.truckstar.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TripWithHelpers {
    @Embedded
    public Trip trip;
    @Relation(
            parentColumn = "id_trip",
            entityColumn = "tripId"
    )
    public List<Helper> helpersList;

    @Override
    public String toString() {
        return "TripWithHelpers{" +
                "trip=" + trip +
                ", helpersList=" + helpersList +
                '}';
    }
}
