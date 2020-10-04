package com.example.truckstar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.truckstar.entities.Helper;
import com.example.truckstar.entities.Provider;
import com.example.truckstar.entities.Trip;
import com.example.truckstar.entities.TripWithData;
import com.example.truckstar.entities.TripWithHelpers;

import java.util.List;

@Dao
public interface TripDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select * from trip t join user u on id_user = userId join provider p on id_provider = providerId order by userId asc ")
    List<TripWithData> getTripWithData();

    @Transaction
    @Query("SELECT * FROM trip")
    List<TripWithHelpers> getTripWithHelpers();

    @Query("SELECT * FROM trip ORDER BY id_trip DESC")
    List<Trip> getAll();

    @Query("SELECT * FROM trip WHERE id_trip IN (:tripIds)")
    List<Trip> loadAllByIds(int[] tripIds);

    @Query("SELECT * FROM trip WHERE mileage LIKE :mileage")
    Trip findByMileage(String mileage);

    @Query("SELECT id_trip FROM trip ORDER BY id_trip DESC LIMIT 1")
    long idOfLastTrip();

    @Query("SELECT * FROM trip WHERE id_trip LIKE :id")
    Trip findTripById(long id);

    @Query("SELECT * FROM trip WHERE `date` LIKE :date ")
    List<Trip> getAllByDate(String date);

    @Query("SELECT COUNT(*) FROM trip ")
    int countTrip();

    @Query("SELECT id_trip FROM trip WHERE date LIKE :date AND hour LIKE :hour")
    long getId(String date, String hour);

    @Insert
    void insertTrip(Trip trip);

    @Insert
    void insertAll(List<Trip> trips);

    @Update
    void updateTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query("DELETE FROM trip")
    void deleteAll();
}
