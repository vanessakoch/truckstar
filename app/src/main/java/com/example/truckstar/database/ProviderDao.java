package com.example.truckstar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.truckstar.entities.Provider;

import java.util.List;

@Dao
public interface ProviderDao {
    @Query("SELECT * FROM provider")
    List<Provider> getAll();

    @Query("SELECT * FROM provider WHERE id IN (:providerIds)")
    List<Provider> loadAllByIds(int[] providerIds);

    @Query("SELECT * FROM provider WHERE name = :name")
    Provider findByName(String name);

    @Query("SELECT * FROM provider ORDER BY id DESC LIMIT 1")
    Provider findLastProvider();

    @Query("SELECT * FROM provider WHERE `city` LIKE :city ")
    List<Provider> getAllByCity(String city);

    @Query("SELECT COUNT(*) FROM provider ")
    int countProvider();

    @Query("SELECT id FROM provider WHERE name LIKE :name AND cnpj LIKE :cnpj")
    long getId(String name, String cnpj);

    @Insert
    void insertProvider(Provider provider);

    @Insert
    void insertAll(List<Provider> providers);

    @Update
    void updateProvider(Provider provider);

    @Delete
    void deleteProvider(Provider provider);

    @Query("DELETE FROM provider")
    void deleteAll();
}