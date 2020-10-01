package com.example.truckstar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.truckstar.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:providerIds)")
    List<User> loadAllByIds(int[] providerIds);

    @Query("SELECT * FROM user WHERE name = :name")
    User findByName(String name);

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    User findLastUser();

    @Query("SELECT COUNT(*) FROM user ")
    int countUser();

    @Query("SELECT id FROM user WHERE name LIKE :name AND nickname LIKE :nickname")
    long getId(String name, String nickname);

    @Insert
    void insertUser(User user);

    @Insert
    void insertAll(List<User> users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}