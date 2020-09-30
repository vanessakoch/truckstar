package com.example.truckstar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.truckstar.entities.Provider;

import java.util.List;

@Dao
public interface ProviderDAO {

    @Query("SELECT * FROM provider")
    List<Provider> getAll();

    @Query("SELECT * FROM provider WHERE id IN (:eventIds)")
    List<Provider> loadAllByIds(int[] eventIds);

    @Query("SELECT * FROM provider WHERE id = :id")
    Provider findById(int id);

    @Query("SELECT * FROM provider ORDER BY id DESC LIMIT 1")
    Provider findLastProvider();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProvider(Provider provider);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Provider> providers);

    @Update
    void updateProvider(Provider provider);

    @Delete
    void deleteProvider(Provider provider);

    @Query("DELETE FROM provider")
    void deleteAll();
}