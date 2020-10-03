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
public interface HelperDao {

    @Query("SELECT * FROM helper ORDER BY id_helper DESC")
    List<Helper> getAll();

    @Query("SELECT * FROM helper WHERE id_helper IN (:helperIds)")
    List<Helper> loadAllByIds(int[] helperIds);

    @Query("SELECT * FROM helper ORDER BY id_helper DESC LIMIT 1")
    Helper findLastHelper();

    @Query("SELECT COUNT(*) FROM helper ")
    int countHelper();

    @Query("SELECT id_helper FROM helper WHERE cpf LIKE :cpf")
    long getId(String cpf);

    @Insert
    void insertHelper(Helper helper);

    @Insert
    void insertAll(List<Helper> helpers);

    @Update
    void updateHelper(Helper helper);

    @Delete
    void deleteHelper(Helper helper);

    @Query("DELETE FROM helper")
    void deleteAll();
}
