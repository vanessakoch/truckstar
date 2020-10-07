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
    @Query("SELECT * FROM user ORDER BY id_user DESC")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id_user IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :name")
    User findByName(String name);

    @Query("SELECT * FROM user ORDER BY id_user DESC LIMIT 1")
    User findLastUser();

    @Query("SELECT COUNT(*) FROM user ")
    int countUser();

    @Query("SELECT * FROM user WHERE id_user LIKE :id")
    User getById(long id);

    @Query("SELECT id_user FROM user WHERE user_name LIKE :name AND nickname LIKE :nickname")
    long getId(String name, String nickname);

    @Query("SELECT * FROM user WHERE nickname LIKE :nickname AND password LIKE :password")
    User getUserAuth(String nickname, String password);

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