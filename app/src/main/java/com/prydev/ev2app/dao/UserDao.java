package com.prydev.ev2app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prydev.ev2app.models.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM USER WHERE ID = :id")
    User getByID(int id);

    @Query("SELECT * FROM USER WHERE username = :user ")
    boolean testLoginUsername(String user);

    @Query("SELECT * FROM USER WHERE username = :user AND password = :pass")
    User testLogin(String user, String pass);

    @Insert
    void insert(User... users);

    @Update
    void updateByObject(User... users);
}
